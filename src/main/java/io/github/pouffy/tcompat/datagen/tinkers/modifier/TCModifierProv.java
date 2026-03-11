package io.github.pouffy.tcompat.datagen.tinkers.modifier;

import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.MobEffectModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.modules.armor.KnockbackCounterModule;

import static slimeknights.tconstruct.library.tools.definition.ModifiableArmorMaterial.ARMOR_SLOTS;

public class TCModifierProv extends AbstractModifierProvider implements IConditionBuilder {
    public TCModifierProv(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addModifiers() {
        EquipmentSlot[] armorSlots = ARMOR_SLOTS;

        buildModifier(TCModifiers.aetherForged, modLoaded("aether"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(AetherInit.acclimatization.getId(), modLoaded("aether"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(AetherInit.ambrogen.getId(), modLoaded("aether"))
                .levelDisplay(ModifierLevelDisplay.DEFAULT);
        buildModifier(AetherInit.ascension.getId(), modLoaded("aether"))
                .levelDisplay(ModifierLevelDisplay.DEFAULT);
        buildModifier(AetherReduxInit.ambrofusion.getId(), modLoaded("aether_redux"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(DeepAetherInit.dulling.getId(), modLoaded("deep_aether"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.blighted, modLoaded("aether_redux"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.escarstay, modLoaded("aether_redux"))
                .addModule(StatBoostModule.add(ToolStats.KNOCKBACK_RESISTANCE).flat(2))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Modifiers";
    }
}
