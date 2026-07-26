package io.github.pouffy.tcompat.compat.aether;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.block.FloatingBlockEntity;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.combat.DartItem;
import com.mojang.datafixers.util.Either;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.aether.item.ModifiableDartItem;
import io.github.pouffy.tcompat.compat.aether.item.ModifiableDartShooter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.BowAmmoModifierHook;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.capability.PersistentDataCapability;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.item.ranged.ModifiableLauncherItem;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.Optional;
import java.util.function.Predicate;

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

    public static ItemStack handleDartShooterFinished(ModifiableDartShooter item, ItemStack dartShooter, Level level, LivingEntity living) {
        if (!CompatHelper.isLoaded("aether")) return dartShooter;
        return LoadedOnly.handleDartShooterFinished(item, dartShooter, level, living);
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

        public static ItemStack handleDartShooterFinished(ModifiableDartShooter item, ItemStack dartShooter, Level level, LivingEntity living) {
            ToolStack tool = ToolStack.from(dartShooter);
            int duration = item.getUseDuration(dartShooter);
            for (ModifierEntry entry : tool.getModifiers()) {
                entry.getHook(ModifierHooks.TOOL_USING).beforeReleaseUsing(tool, entry, living, duration, 0, ModifierEntry.EMPTY);
            }

            if (!tool.isBroken()) {
                Player player;
                if (living instanceof Player p) {
                    player = p;
                } else {
                    player = null;
                }

                boolean creative = player != null && player.getAbilities().instabuild;
                Predicate<ItemStack> ammoPredicate = item.getAllSupportedProjectiles();
                ItemStack foundAmmo = BowAmmoModifierHook.getAmmo(tool, dartShooter, living, ammoPredicate);
                boolean hasAmmo = !foundAmmo.isEmpty() || creative && !tool.getVolatileData().getBoolean(BowAmmoModifierHook.SKIP_INVENTORY_AMMO);

                if (hasAmmo && duration >= 0) {
                    float charge = GeneralInteractionModifierHook.getToolCharge(tool, (float)duration);
                    float velocity = ConditionalStatModifierHook.getModifiedStat(tool, living, ToolStats.VELOCITY);
                    float power = charge * velocity;
                    if (!(power < 0.1F)) {
                        if (!level.isClientSide) {
                            int desiredProjectiles = 1;
                            desiredProjectiles = BowAmmoModifierHook.getDesiredProjectiles(tool);
                            ItemStack ammo = BowAmmoModifierHook.consumeAmmo(tool, dartShooter, living, player, ammoPredicate, desiredProjectiles);
                            if (ammo.isEmpty()) {
                                Item fallbackDart = AetherItems.GOLDEN_DART.get();
                                ammo = new ItemStack(fallbackDart);
                            }

                            Item ammoItem = ammo.getItem();
                            Either<ModifiableDartItem, DartItem> dartItem = null;
                            if (ammoItem instanceof ModifiableDartItem dart) {
                                dartItem = Either.left(dart);
                            } else if (ammoItem instanceof DartItem dart) {
                                dartItem = Either.right(dart);
                            }

                            SoundEvent sound = AetherSoundEvents.ITEM_DART_SHOOTER_SHOOT.get();

                            float inaccuracy = ModifierUtil.getInaccuracy(tool, living);
                            float startAngle = ModifiableLauncherItem.getAngleStart(ammo.getCount());
                            int primaryIndex = ammo.getCount() / 2;

                            if (dartItem == null) return dartShooter;

                            for(int arrowIndex = 0; arrowIndex < ammo.getCount(); ++arrowIndex) {
                                AbstractArrow dart = makeFromDarts(dartItem, level, ammo, living);

                                float angle = startAngle + (float)(10 * arrowIndex);
                                dart.shootFromRotation(living, living.getXRot() + angle, living.getYRot(), 0.0F, power * 3.0F, inaccuracy);
                                if (charge == 1.0F) {
                                    dart.setCritArrow(true);
                                }

                                float baseArrowDamage = (float)(dart.getBaseDamage() - (double)2.0F + (double) tool.getStats().get(ToolStats.PROJECTILE_DAMAGE));
                                dart.setBaseDamage(ConditionalStatModifierHook.getModifiedStat(tool, living, ToolStats.PROJECTILE_DAMAGE, baseArrowDamage));
                                ModifierNBT modifiers = tool.getModifiers();
                                EntityModifierCapability.getCapability(dart).addModifiers(modifiers);
                                ModDataNBT arrowData = PersistentDataCapability.getOrWarn(dart);
                                if (creative) {
                                    dart.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                                }

                                dart.setNoGravity(true);
                                for(ModifierEntry entry : modifiers.getModifiers()) {
                                    entry.getHook(ModifierHooks.PROJECTILE_LAUNCH).onProjectileLaunch(tool, entry, living, ammo, dart, dart, arrowData, arrowIndex == primaryIndex);
                                }

                                level.addFreshEntity(dart);
                                level.playSound(null, living.getX(), living.getY(), living.getZ(), sound, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + charge * 0.5F + angle / 10.0F);
                            }

                            int damage = ammo.getCount();
                            ToolDamageUtil.damageAnimated(tool, damage, living, living.getUsedItemHand());
                        }

                        if (player != null) {
                            player.awardStat(Stats.ITEM_USED.get(item));
                        }
                    }
                }
            }
            return dartShooter;
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
