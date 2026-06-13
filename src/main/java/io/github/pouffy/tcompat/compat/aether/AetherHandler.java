package io.github.pouffy.tcompat.compat.aether;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.entity.block.FloatingBlockEntity;
import com.aetherteam.aether.item.EquipmentUtil;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class AetherHandler {

    public static boolean floatBlock(UseOnContext context) {
        if (!CompatHelper.isLoaded("aether")) return false;
        return LoadedOnly.floatBlock(context);
    }

    public static void launchEntity(LivingEntity target, LivingEntity attacker) {
        if (!CompatHelper.isLoaded("aether")) return;
        LoadedOnly.launchEntity(target, attacker);
    }

    public static void vampireHealing(LivingEntity attacker) {
        if (!CompatHelper.isLoaded("aether")) return;
        LoadedOnly.vampireHealing(attacker);
    }

    public static boolean isFree(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.liquid() || state.canBeReplaced();
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
            if (EquipmentUtil.isFullStrength(attacker) && !target.getType().is(AetherTags.Entities.UNLAUNCHABLE) && (target.onGround() || target.isInFluidType())) {
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
    }
}
