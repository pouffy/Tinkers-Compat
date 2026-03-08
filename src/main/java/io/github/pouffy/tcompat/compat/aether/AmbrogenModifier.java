package io.github.pouffy.tcompat.compat.aether;

import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_redux.AmbrofusionModifier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Optional;

public class AmbrogenModifier extends Modifier implements BlockBreakModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BLOCK_BREAK);
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        dropAmbrosium(tool, context.getPlayer(), context.getWorld(), context.getPos(), context.canHarvest(), context.getState(), modifier.getLevel());
    }

    void dropAmbrosium(IToolStackView tool, Player player, Level level, BlockPos pos, boolean canHarvest, BlockState state, int modLevel) {
        int randDecreasePerLevel = 10;
        int rand = 60 - (modLevel * randDecreasePerLevel);
        Optional<Item> ambrosium = ObjectRetriever.getItem("aether:ambrosium_shard");
        if (ambrosium.isEmpty()) {
            return;
        }
        if (!level.isClientSide() && state.getDestroySpeed(level, pos) > 0 && canHarvest && player.getRandom().nextInt(rand) == 0) {
            if (!chargeAmbrofusion(tool)) {
                ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(ambrosium.get()));
                level.addFreshEntity(itemEntity);
            }
        }
    }

    // If Aether Redux is present and your tool has Ambrofusion, charge that instead of spawning a shard.
    boolean chargeAmbrofusion(IToolStackView tool) {
        if (!CompatHelper.isLoaded("aether_redux")) return false;
        AmbrofusionModifier ambrofusion = AetherReduxInit.ambrofusion.get();
        ModifierId ambrofusionId = AetherReduxInit.ambrofusion.getId();
        ModifierEntry entry = tool.getModifier(ambrofusionId);
        if (tool.getModifiers().getLevel(ambrofusionId) != 0) {
            if (ambrofusion.getAmbrosium(tool) >= ambrofusion.getAmbrosiumCapacity(tool, entry)) {
                return false;
            }
            int available = 4;
            ambrofusion.addAmbrofusion(tool, entry, available);
            return true;
        }
        return false;
    }
}
