package io.github.pouffy.tcompat.compat.malum.modifier.general;

import io.github.pouffy.tcompat.common.module.CollectSpiritModifierHook;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class SpiritRepairModifier extends NoLevelsModifier implements CollectSpiritModifierHook {

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, GlobalInit.COLLECT_SPIRIT);
        super.registerHooks(hookBuilder);
    }

    @Override
    public boolean canTarget(IToolStackView toolStack, ModifierEntry modifierEntry, LivingEntity collector, double arcaneResonance) {
        return !toolStack.isBroken() && toolStack.getCurrentDurability() < toolStack.getStats().getInt(ToolStats.DURABILITY);
    }

    @Override
    public void pickupSpirit(IToolStackView toolStack, ModifierEntry modifierEntry, LivingEntity collector, double arcaneResonance, float effectiveness) {
        Level level = collector.level();
        if (toolStack.isBroken() || level.random.nextFloat() > 0.25f) return;
        float amount = (float) ((toolStack.getStats().getInt(ToolStats.DURABILITY) * 0.005) * arcaneResonance) * effectiveness;
        ToolDamageUtil.repair(toolStack, Math.round(amount));
    }
}
