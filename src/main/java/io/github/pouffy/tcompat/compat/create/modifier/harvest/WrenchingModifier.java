package io.github.pouffy.tcompat.compat.create.modifier.harvest;

import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import io.github.pouffy.tcompat.compat.create.CreateHandler;
import io.github.pouffy.tcompat.compat.create.CreateInit;
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
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.BlockInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WrenchingModifier extends NoLevelsModifier implements BlockInteractionModifierHook {


    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.BLOCK_INTERACT);
    }

    @Override
    public InteractionResult beforeBlockUse(IToolStackView tool, ModifierEntry modifier, UseOnContext context, InteractionSource source) {
        Player player = context.getPlayer();
        if (player != null && player.mayBuild()) {
            BlockState state = context.getLevel().getBlockState(context.getClickedPos());
            Block block = state.getBlock();
            return CreateHandler.wrenchWrenchable(context, player, state, block);
        } else {
            return InteractionResult.PASS;
        }
    }

    public static boolean canWrenchPickup(BlockState state) {
        return state.is(TCTags.Blocks.named("create", "wrench_pickup"));
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
            CreateHandler.wrenchSound(world, pos);
            world.destroyBlock(pos, false);
            return InteractionResult.SUCCESS;
        }
    }

    public static void wrenchInstaKillsMinecarts(AttackEntityEvent event) {
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
}
