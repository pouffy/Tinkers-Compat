package io.github.pouffy.tcompat.datagen.tinkers.modifier;

import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.datagen.tag.TCEntityTagProv;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import slimeknights.mantle.data.predicate.IJsonPredicate;
import slimeknights.mantle.data.predicate.damage.DamageTypePredicate;
import slimeknights.mantle.data.predicate.entity.LivingEntityPredicate;
import slimeknights.mantle.data.predicate.item.ItemPredicate;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.json.RandomLevelingValue;
import slimeknights.tconstruct.library.json.predicate.tool.ToolStackPredicate;
import slimeknights.tconstruct.library.json.variable.VariableFormula;
import slimeknights.tconstruct.library.modifiers.modules.armor.ProtectionModule;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.ConditionalMeleeDamageModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.ConditionalPowerModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.MobEffectModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

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

        IJsonPredicate<LivingEntity> scorchbornPredicate = LivingEntityPredicate.or(LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:lightning_dragons")), LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:ice_dragons")));
        buildModifier(TCModifiers.scorchborn, modLoaded("iceandfire"))
                .addModule(ConditionalMeleeDamageModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.MELEE_WEAPON)).target(scorchbornPredicate).percent().eachLevel(0.45f))
                .addModule(ConditionalPowerModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.RANGED)).target(scorchbornPredicate).percent().eachLevel(0.65f))
                .addModule(ProtectionModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.ARMOR)).source(new DamageTypePredicate(ObjectRetriever.damageKey("iceandfire:dragon_fire"))).eachLevel(2.5f));
        IJsonPredicate<LivingEntity> frostbornPredicate = LivingEntityPredicate.or(LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:lightning_dragons")), LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:fire_dragons")));
        buildModifier(TCModifiers.frostborn, modLoaded("iceandfire"))
                .addModule(ConditionalMeleeDamageModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.MELEE_WEAPON)).target(frostbornPredicate).percent().eachLevel(0.45f))
                .addModule(ConditionalPowerModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.RANGED)).target(frostbornPredicate).percent().eachLevel(0.65f))
                .addModule(ProtectionModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.ARMOR)).source(new DamageTypePredicate(ObjectRetriever.damageKey("iceandfire:dragon_ice"))).eachLevel(2.5f));
        IJsonPredicate<LivingEntity> voltbornPredicate = LivingEntityPredicate.or(LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:ice_dragons")), LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:fire_dragons")));
        buildModifier(TCModifiers.voltborn, modLoaded("iceandfire"))
                .addModule(ConditionalMeleeDamageModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.MELEE_WEAPON)).target(voltbornPredicate).percent().eachLevel(0.45f))
                .addModule(ConditionalPowerModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.RANGED)).target(voltbornPredicate).percent().eachLevel(0.65f))
                .addModule(ProtectionModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.ARMOR)).source(new DamageTypePredicate(ObjectRetriever.damageKey("iceandfire:dragon_lightning"))).eachLevel(2.5f));
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Modifiers";
    }
}
