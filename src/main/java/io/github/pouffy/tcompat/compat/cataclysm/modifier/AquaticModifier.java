package io.github.pouffy.tcompat.compat.cataclysm.modifier;

import io.github.pouffy.tcompat.TCompat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class AquaticModifier extends NoLevelsModifier implements ToolStatsModifierHook, InventoryTickModifierHook {
    private final ResourceLocation fishCount = TCompat.getResource("fish_count");

    //TODO: Look into the stats here. Potentially overpowered
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOL_STATS, ModifierHooks.INVENTORY_TICK);
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifierEntry, ModifierStatsBuilder builder) {
        int fish = context.getPersistentData().getInt(fishCount);
        if(fish <= 0) return;
        float leveledValue = new LevelingValue(0.075f, 0.05f).compute(fish);
        if (ToolStats.DURABILITY.supports(context.getItem())) {
            StatBoostModule.StatOperation.MULTIPLY_BASE.apply(builder, ToolStats.DURABILITY, leveledValue);
        }
        if (ToolStats.ATTACK_DAMAGE.supports(context.getItem())) {
            StatBoostModule.StatOperation.MULTIPLY_BASE.apply(builder, ToolStats.ATTACK_DAMAGE, leveledValue);
        }
        if (ToolStats.ATTACK_SPEED.supports(context.getItem())) {
            StatBoostModule.StatOperation.MULTIPLY_BASE.apply(builder, ToolStats.ATTACK_SPEED, leveledValue);
        }
        if (ToolStats.MINING_SPEED.supports(context.getItem())) {
            StatBoostModule.StatOperation.MULTIPLY_BASE.apply(builder, ToolStats.MINING_SPEED, leveledValue);
        }
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifierEntry, Level level, LivingEntity owner, int i, boolean b, boolean b1, ItemStack itemStack) {
        tool.getPersistentData().putInt(fishCount, level.getEntitiesOfClass(LivingEntity.class, new AABB(owner.blockPosition()).inflate(4), EntitySelector.NO_SPECTATORS.and((entity) ->
                entity.getType().getCategory() == MobCategory.WATER_CREATURE || entity.getType().getCategory() == MobCategory.WATER_AMBIENT || entity.getType().getCategory() == MobCategory.AXOLOTLS || entity.getType().getCategory() == MobCategory.UNDERGROUND_WATER_CREATURE)).size());
    }
}
