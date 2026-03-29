package io.github.pouffy.tcompat.compat.ice_and_fire.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

public class ModifiableGlaiveItem extends ModifiableItem {

    public ModifiableGlaiveItem(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }

    public ModifiableGlaiveItem(Properties properties, ToolDefinition toolDefinition, int maxStackSize) {
        super(properties, toolDefinition, maxStackSize);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
        return !player.isCreative();
    }
}
