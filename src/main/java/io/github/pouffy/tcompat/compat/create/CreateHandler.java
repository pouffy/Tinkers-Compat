package io.github.pouffy.tcompat.compat.create;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.Create;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;

public class CreateHandler {

    public static void wrenchSound(Level level, BlockPos pos) {
        if (!CompatHelper.isLoaded("create")) return;
        LoadedOnly.wrenchSound(level, pos);
    }

    public static InteractionResult wrenchWrenchable(UseOnContext context, Player player, BlockState state, Block block) {
        if (!CompatHelper.isLoaded("create")) return InteractionResult.PASS;
        return LoadedOnly.wrenchWrenchable(context, player, state, block);
    }

    public static boolean canWrenchPickup(BlockState state) {
        return state.is(TCTags.Blocks.named("create", "wrench_pickup"));
    }

    public static void wrenchInstaKillsMinecarts(AttackEntityEvent event) {
        if (!CompatHelper.isLoaded("create")) return;
        Entity target = event.getTarget();
        if (target instanceof AbstractMinecart minecart) {
            Player player = event.getEntity();
            ItemStack heldItem = player.getMainHandItem();
            if (!player.isCreative()) {
                EquipmentHelper.asTool(heldItem, (tool) -> {
                    if (tool.getModifier(CreateInit.wrenching.getId()) != ModifierEntry.EMPTY) {
                        minecart.hurt(minecart.damageSources().playerAttack(player), 100.0F);
                    }
                });
            }
        }
    }

    public static class LoadedOnly {

        public static void wrenchSound(Level level, BlockPos pos) {
            AllSoundEvents.WRENCH_REMOVE.playOnServer(level, pos, 1.0F, Create.RANDOM.nextFloat() * 0.5F + 0.5F);
        }

        public static InteractionResult wrenchWrenchable(UseOnContext context, Player player, BlockState state, Block block) {
            if (block instanceof IWrenchable actor) {
                return player.isShiftKeyDown() ? actor.onSneakWrenched(state, context) : actor.onWrenched(state, context);
            } else {
                return player.isShiftKeyDown() && canWrenchPickup(state) ? onItemUseOnOther(context) : InteractionResult.PASS;
            }
        }

        public static InteractionResult onItemUseOnOther(UseOnContext context) {
            Player player = context.getPlayer();
            Level world = context.getLevel();
            BlockPos pos = context.getClickedPos();
            BlockState state = world.getBlockState(pos);
            if (!(world instanceof ServerLevel)) {
                return InteractionResult.SUCCESS;
            } else {
                if (player != null && !player.isCreative()) {
                    Block.getDrops(state, (ServerLevel)world, pos, world.getBlockEntity(pos), player, context.getItemInHand()).forEach((itemStack) -> player.getInventory().placeItemBackInInventory(itemStack));
                }

                state.spawnAfterBreak((ServerLevel)world, pos, ItemStack.EMPTY, true);
                AllSoundEvents.WRENCH_REMOVE.playOnServer(world, pos, 1.0F, Create.RANDOM.nextFloat() * 0.5F + 0.5F);
                world.destroyBlock(pos, false);
                return InteractionResult.SUCCESS;
            }
        }
    }
}
