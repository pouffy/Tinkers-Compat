package com.pouffydev.tcompat.modifier.aether;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.material.TComMaterialIds;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.definition.MaterialVariant;
import slimeknights.tconstruct.library.modifiers.IncrementalModifierEntry;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class AmbrofusionModifier extends Modifier implements ToolDamageModifierHook, ModifierRemovalHook, ToolStatsModifierHook, ValidateModifierHook, DurabilityDisplayModifierHook, InventoryTickModifierHook {
    public static final ResourceLocation shouldAddStatsKey = TCompat.getResource("should_add_stats");
    public static boolean shouldAddStats = false;
    /** Stat for the overslime cap, copies the durability global multiplier on build */
    public static final FloatToolStat AMBROFUSION_STAT = ToolStats.register(new FloatToolStat(new ToolStatId(TCompat.MOD_ID, "ambrofusion"), 0xFFf9ff6a, 0, 0, Short.MAX_VALUE, TinkerTags.Items.DURABILITY) {
        @Override
        public Float build(ModifierStatsBuilder parent, Object builderObj) {
            return super.build(parent, builderObj) * parent.getMultiplier(ToolStats.DURABILITY);
        }
    });

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,
                ModifierHooks.REMOVE,
                ModifierHooks.TOOL_STATS,
                ModifierHooks.TOOL_DAMAGE,
                ModifierHooks.VALIDATE,
                ModifierHooks.DURABILITY_DISPLAY,
                ModifierHooks.INVENTORY_TICK
        );
    }

    @Override
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry, @javax.annotation.Nullable RegistryAccess access) {
        return IncrementalModifierEntry.addAmountToName(getDisplayName(entry.getLevel()), getAmbrosium(tool), getAmbrosiumCapacity(tool, entry));
    }

    @Override
    public Component getDisplayName(int level) {
        // display name without the level
        return super.getDisplayName();
    }

    @javax.annotation.Nullable
    @Override
    public Boolean showDurabilityBar(IToolStackView tool, ModifierEntry modifier) {
        return tool.getCurrentDurability() < tool.getStats().getInt(ToolStats.DURABILITY) ? true : null;
    }

    @Override
    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifier) {
        int ambrosium = getAmbrosium(tool);
        if (ambrosium > 0) {
            return DurabilityDisplayModifierHook.getWidthFor(tool.getCurrentDurability(), tool.getStats().getInt(ToolStats.DURABILITY));
        }
        return 0;
    }

    @Override
    public int getDurabilityRGB(IToolStackView tool, ModifierEntry modifier) {
        if (getAmbrosium(tool) > 0) {
            // just always display light blue, not much point in color changing really
            return 0xf9ff6a;
        }
        return -1;
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        AMBROFUSION_STAT.add(builder, 12);
        boolean shouldAdd = context.getPersistentData().getBoolean(shouldAddStatsKey);
        if (shouldAdd) {
            if (context.hasTag(TinkerTags.Items.MELEE)) {
                ToolStats.ATTACK_DAMAGE.multiply(builder, 1.5f);
            }
            if (context.hasTag(TinkerTags.Items.HARVEST)) {
                ToolStats.MINING_SPEED.multiply(builder, 1.5f);
            }
            if (context.hasTag(TinkerTags.Items.ARMOR)) {
                ToolStats.ARMOR.add(builder, 1.5f);
            }
            if (context.hasTag(TinkerTags.Items.RANGED)) {
                ToolStats.VELOCITY.multiply(builder, 1.5f);
            }
        }
    }

    @Override
    public int getPriority() {
        return 150;
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        int ambrosium = getAmbrosium(tool);
        if (ambrosium > 0) {
            if (ambrosium >= amount) {
                setAmbrosium(tool, modifier, ambrosium - amount);
                return amount * 4;
            }
            setAmbrosium(tool, modifier, 0);
            return amount;
        }
        return amount;
    }

    protected ResourceLocation getShieldKey() {
        return getId();
    }

    public int getAmbrosiumCapacity(IToolStackView tool, ModifierEntry modifier) {
        return tool.getStats().getInt(AMBROFUSION_STAT);
    }

    @javax.annotation.Nullable
    @Override
    public Component validate(IToolStackView tool, ModifierEntry modifier) {
        int cap = getAmbrosiumCapacity(tool, modifier);
        if (getAmbrosium(tool) > cap) {
            setAmbrosium(tool.getPersistentData(), cap);
        }
        return null;
    }

    public int getAmbrosium(IToolStackView tool) {
        return tool.getPersistentData().getInt(getShieldKey());
    }

    public void setAmbrosium(ModDataNBT persistentData, int amount) {
        persistentData.putInt(getShieldKey(), Math.max(amount, 0));
    }

    public void setAmbrosium(IToolStackView tool, ModifierEntry modifier, int amount) {
        setAmbrosium(tool.getPersistentData(), Math.min(amount, getAmbrosiumCapacity(tool, modifier)));
    }

    protected void addAmbrosium(IToolStackView tool, ModifierEntry modifier, int amount) {
        setAmbrosium(tool, modifier, amount + getAmbrosium(tool));
    }

    public void addAmbrofusion(IToolStackView tool, ModifierEntry entry, int amount) {
        int veridiumCoverage = 0;
        for (MaterialVariant material : tool.getMaterials().getList()) {
            if (material.matchesVariant(TComMaterialIds.veridium)) veridiumCoverage += 1;
        }
        addAmbrosium(tool, entry, amount * (1 + veridiumCoverage));
    }

    @javax.annotation.Nullable
    @Override
    public Component onRemoved(IToolStackView tool, Modifier modifier) {
        // remove all ambrofusion on removal
        tool.getPersistentData().remove(getShieldKey());
        return null;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        tool.getPersistentData().putBoolean(shouldAddStatsKey, getAmbrosium(tool) > 0);
    }


}
