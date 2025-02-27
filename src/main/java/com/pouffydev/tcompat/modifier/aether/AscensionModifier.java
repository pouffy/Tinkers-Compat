package com.pouffydev.tcompat.modifier.aether;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.BlockInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class AscensionModifier extends NoLevelsModifier implements BlockInteractionModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BLOCK_INTERACT);
    }

    public InteractionResult afterBlockUse(IToolStackView tool, ModifierEntry modifier, UseOnContext context, InteractionSource source) {
        //TODO: Replace this with a different ability
        //if (!this.floatBlock(context)) {
        //    return InteractionResult.PASS;
        //} else {
        //    return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
        //}
        return InteractionResult.PASS;
    }

    //boolean floatBlock(UseOnContext context) {
    //    Level level = context.getLevel();
    //    BlockPos blockPos = context.getClickedPos();
    //    ItemStack itemStack = context.getItemInHand();
    //    BlockState blockState = level.getBlockState(blockPos);
    //    Player player = context.getPlayer();
    //    InteractionHand hand = context.getHand();
    //    if (itemStack.getItem() instanceof TieredItem tieredItem && TCompat.isClassFound("com.aetherteam.aether.entity.block.FloatingBlockEntity")) {
    //        if (player != null && !player.isShiftKeyDown()) {
    //            if ((itemStack.getDestroySpeed(blockState) == tieredItem.getTier().getSpeed() || itemStack.isCorrectToolForDrops(blockState)) && FloatingBlock.isFree(level.getBlockState(blockPos.above()))) {
    //                if (level.getBlockEntity(blockPos) == null && blockState.getDestroySpeed(level, blockPos) >= 0.0F && !blockState.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF) && !blockState.is(AetherTags.Blocks.GRAVITITE_ABILITY_BLACKLIST)) {
    //                    if (!level.isClientSide()) {
    //                        FloatingBlockEntity entity = new FloatingBlockEntity(level, blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, blockState);
    //                        entity.setNatural(false);
    //                        if (blockState.is(BlockTags.ANVIL)) {
    //                            entity.setHurtsEntities(2.0F, 40);
    //                        }
    //                        level.addFreshEntity(entity);
    //                        level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
    //                        itemStack.hurtAndBreak(4, player, (p) -> p.broadcastBreakEvent(hand));
    //                    } else {
    //                        player.swing(hand);
    //                    }
    //                    return true;
    //                }
    //            }
    //        }
    //    }
    //    return false;
    //}
}
