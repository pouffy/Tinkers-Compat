package io.github.pouffy.tcompat.compat.aether;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.block.FloatingBlockEntity;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.combat.DartItem;
import com.mojang.datafixers.util.Either;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.aether.item.ModifiableDartItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

public class AetherHandler {

    public static boolean floatBlock(UseOnContext context) {
        if (!CompatHelper.isLoaded("aether")) return false;
        return LoadedOnly.floatBlock(context);
    }

    public static void launchEntity(LivingEntity target, LivingEntity attacker) {
        if (!CompatHelper.isLoaded("aether")) return;
        LoadedOnly.launchEntity(target, attacker);
    }

    public static void launchEntity(LivingEntity target, boolean fullStrength) {
        if (!CompatHelper.isLoaded("aether")) return;
        LoadedOnly.launchEntity(target, fullStrength);
    }

    public static void vampireHealing(LivingEntity attacker) {
        if (!CompatHelper.isLoaded("aether")) return;
        LoadedOnly.vampireHealing(attacker);
    }

    public static boolean isFree(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.liquid() || state.canBeReplaced();
    }

    public static SoundEvent dartHitSound() {
        if (!CompatHelper.isLoaded("aether")) return SoundEvents.ARROW_HIT;
        return LoadedOnly.dartHitSound();
    }

    public static SoundEvent dartShooterFireSound() {
        if (!CompatHelper.isLoaded("aether")) return SoundEvents.ARROW_SHOOT;
        return LoadedOnly.dartShooterFireSound();
    }

    public static AbstractArrow createDart(Item ammoItem, Level level, ItemStack ammo, LivingEntity living) {
        if (!CompatHelper.isLoaded("aether")) {
            if (ammoItem instanceof ModifiableDartItem dart) {
                return dart.createDart(level, ammo, living);
            }
        }
        return LoadedOnly.createDart(ammoItem, level, ammo, living);
    }

    public static class LoadedOnly {

        public static boolean floatBlock(UseOnContext context) {
            Level level = context.getLevel();
            BlockPos blockPos = context.getClickedPos();
            ItemStack itemStack = context.getItemInHand();
            BlockState blockState = level.getBlockState(blockPos);
            Player player = context.getPlayer();
            InteractionHand hand = context.getHand();
            if (player != null && !player.isSpectator()) {
                if ((itemStack.isCorrectToolForDrops(blockState)) && isFree(level.getBlockState(blockPos.above()))) {
                    if (level.getBlockEntity(blockPos) == null && blockState.getDestroySpeed(level, blockPos) >= 0.0F && !blockState.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF) && !blockState.is(AetherTags.Blocks.GRAVITITE_ABILITY_BLACKLIST)) {
                        if (!level.isClientSide()) {
                            FloatingBlockEntity entity = new FloatingBlockEntity(level, (double)blockPos.getX() + (double)0.5F, (double)blockPos.getY(), (double)blockPos.getZ() + (double)0.5F, blockState);
                            entity.setNatural(false);
                            if (blockState.is(BlockTags.ANVIL)) {
                                entity.setHurtsEntities(2.0F, 40);
                            }
                            level.addFreshEntity(entity);
                            level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                            itemStack.hurtAndBreak(4, player, (p) -> p.broadcastBreakEvent(hand));
                        } else {
                            player.swing(hand);
                        }

                        return true;
                    }
                }
            }

            return false;
        }

        public static void launchEntity(LivingEntity target, LivingEntity attacker) {
            launchEntity(target, EquipmentUtil.isFullStrength(attacker));
        }

        public static void launchEntity(LivingEntity target, boolean fullStrength) {
            if (fullStrength && !target.getType().is(AetherTags.Entities.UNLAUNCHABLE) && (target.onGround() || target.isInFluidType())) {
                target.push(0.0F, 1.0F, 0.0F);
                if (target instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
                }
            }
        }

        public static void vampireHealing(LivingEntity attacker) {
            if (EquipmentUtil.isFullStrength(attacker) && attacker.getHealth() < attacker.getMaxHealth()) {
                if (attacker instanceof Player player) {
                    AetherPlayer.get(player).ifPresent((aetherPlayer) -> aetherPlayer.setVampireHealing(true));
                } else {
                    attacker.heal(1.0F);
                }
            }
        }

        public static SoundEvent dartHitSound() {
            return AetherSoundEvents.ENTITY_DART_HIT.get();
        }

        public static SoundEvent dartShooterFireSound() {
            return AetherSoundEvents.ITEM_DART_SHOOTER_SHOOT.get();
        }

        public static AbstractArrow createDart(Item ammoItem, Level level, ItemStack ammo, LivingEntity living) {
            Either<ModifiableDartItem, DartItem> dartItem = null;
            if (ammoItem instanceof ModifiableDartItem dart) {
                dartItem = Either.left(dart);
            } else if (ammoItem instanceof DartItem dart) {
                dartItem = Either.right(dart);
            }
            if (dartItem == null) return null;
            return makeFromDarts(dartItem, level, ammo, living);
        }

        private static AbstractArrow makeFromDarts(Either<ModifiableDartItem, DartItem> dartItem, Level level, ItemStack ammo, LivingEntity living) {
            Optional<DartItem> dart = dartItem.right();
            Optional<ModifiableDartItem> modifiableDart = dartItem.left();
            if (dart.isPresent()) {
                return dart.get().createDart(level, living);
            }
            return modifiableDart.map(modifiableDartItem -> modifiableDartItem.createDart(level, ammo, living)).orElse(null);
        }


    }
}
