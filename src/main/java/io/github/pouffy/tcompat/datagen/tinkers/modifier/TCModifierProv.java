package io.github.pouffy.tcompat.datagen.tinkers.modifier;

import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.datagen.tag.TCEntityTagProv;
import net.minecraft.data.PackOutput;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import slimeknights.mantle.data.predicate.IJsonPredicate;
import slimeknights.mantle.data.predicate.block.BlockPredicate;
import slimeknights.mantle.data.predicate.damage.DamageSourcePredicate;
import slimeknights.mantle.data.predicate.damage.DamageTypePredicate;
import slimeknights.mantle.data.predicate.entity.LivingEntityPredicate;
import slimeknights.mantle.data.predicate.entity.MobTypePredicate;
import slimeknights.mantle.data.predicate.item.ItemPredicate;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.json.RandomLevelingValue;
import slimeknights.tconstruct.library.json.predicate.TinkerPredicate;
import slimeknights.tconstruct.library.json.predicate.modifier.SlotTypeModifierPredicate;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.modules.armor.BlockDamageSourceModule;
import slimeknights.tconstruct.library.modifiers.modules.armor.ProtectionModule;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.ConditionalMeleeDamageModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.MobEffectModule;
import slimeknights.tconstruct.library.modifiers.modules.mining.ConditionalMiningSpeedModule;
import slimeknights.tconstruct.library.modifiers.modules.util.ModifierCondition;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.tools.definition.module.build.ToolSlotsModule;
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
        IJsonPredicate<LivingEntity> dreadPredicate = LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:dread"));
        buildModifier(TCModifiers.dreadbane, modLoaded("iceandfire"))
                .addModule(ConditionalMeleeDamageModule.builder().target(dreadPredicate).eachLevel(1.5f))
                .addModule(MobEffectModule.builder(MobEffects.WITHER).target(dreadPredicate).level(RandomLevelingValue.flat(1)).time(RandomLevelingValue.random(20, 10)).chance(LevelingValue.flat(1)).build())
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        IJsonPredicate<LivingEntity> dampeningPredicate = LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:mythical"));
        buildModifier(TCModifiers.dampening, modLoaded("iceandfire"))
                .addModule(ConditionalMeleeDamageModule.builder().target(dampeningPredicate).eachLevel(3.5f))
                .addModule(MobEffectModule.builder(MobEffects.WEAKNESS).target(dampeningPredicate).level(RandomLevelingValue.flat(4)).time(RandomLevelingValue.random(20, 10)).chance(LevelingValue.flat(1)).build())
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Modifiers";
    }
}
