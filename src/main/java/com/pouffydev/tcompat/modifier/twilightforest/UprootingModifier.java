package com.pouffydev.tcompat.modifier.twilightforest;

import com.aetherteam.aether.item.AetherItems;
import com.pouffydev.tcompat.TCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import twilightforest.init.TFItems;

public class UprootingModifier extends Modifier implements BlockBreakModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BLOCK_BREAK);
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        dropLiveroots(context.getPlayer(), context.getWorld(), context.getPos(), context.canHarvest(), context.getState(), modifier.getLevel());
    }

    void dropLiveroots(Player player, Level level, BlockPos pos, boolean canHarvest, BlockState state, int modLevel) {
        int randDecreasePerLevel = 10;
        int rand = 60 - (modLevel * randDecreasePerLevel);
        if (!level.isClientSide() && state.getDestroySpeed(level, pos) > 0 && canHarvest && player.getRandom().nextInt(rand) == 0) {
            if (TCompat.isClassFound("twilightforest.init.TFItems")) {
                ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(TFItems.LIVEROOT.get(), player.getRandom().nextIntBetweenInclusive(1, modLevel)));
                level.addFreshEntity(itemEntity);
            }
        }
    }
}
