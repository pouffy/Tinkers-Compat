package io.github.pouffy.tcompat.datagen.tinkers.modifier;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.data.variable.GameEventEntry;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.common.modifier.module.*;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.malum.MalumInit;
import io.github.pouffy.tcompat.datagen.tag.TCEntityTagProv;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.crafting.conditions.AndCondition;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import slimeknights.mantle.data.predicate.IJsonPredicate;
import slimeknights.mantle.data.predicate.damage.DamageTypePredicate;
import slimeknights.mantle.data.predicate.entity.LivingEntityPredicate;
import slimeknights.mantle.data.predicate.entity.MobTypePredicate;
import slimeknights.mantle.data.predicate.item.ItemPredicate;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.json.RandomLevelingValue;
import slimeknights.tconstruct.library.json.predicate.tool.ToolStackPredicate;
import slimeknights.tconstruct.library.json.variable.tool.StatMultiplierVariable;
import slimeknights.tconstruct.library.json.variable.tool.ToolVariable;
import slimeknights.tconstruct.library.modifiers.modules.armor.ProtectionModule;
import slimeknights.tconstruct.library.modifiers.modules.behavior.AttributeModule;
import slimeknights.tconstruct.library.modifiers.modules.behavior.ConditionalStatModule;
import slimeknights.tconstruct.library.modifiers.modules.behavior.ReduceToolDamageModule;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierRequirementsModule;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierSlotModule;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.ConditionalMeleeDamageModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.ConditionalPowerModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.KnockbackModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.MobEffectModule;
import slimeknights.tconstruct.library.modifiers.modules.display.DurabilityBarColorModule;
import slimeknights.tconstruct.library.modifiers.modules.mining.ConditionalMiningSpeedModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.data.ModifierIds;

import java.util.ArrayList;
import java.util.List;

import static slimeknights.tconstruct.library.tools.definition.ModifiableArmorMaterial.ARMOR_SLOTS;

public class TCModifierProv extends AbstractModifierProvider implements IConditionBuilder {
    public TCModifierProv(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addModifiers() {
        EquipmentSlot[] armorSlots = ARMOR_SLOTS;

        buildModifier(TCModifiers.aetherForged, modLoaded("aether"))
                .addModule(AetherForgedModule.INSTANCE).priority(200)
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.escarstay, modLoaded("aether_redux"))
                .addModule(StatBoostModule.add(ToolStats.KNOCKBACK_RESISTANCE).flat(2))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);

        buildModifier(TCModifiers.zanite, modLoaded("aether"))
                .addModule(ConditionalMeleeDamageModule.builder().tool(ToolStackPredicate.tag(TinkerTags.Items.MELEE)).percent().formula()
                        .customVariable("lost", ToolVariable.CURRENT_DAMAGE)
                        .customVariable("max", new StatMultiplierVariable(ToolStats.DURABILITY))
                        .divide()
                        .sqrt()
                        .variable(0).multiply()
                        .constant(0.005F).multiply()
                        .constant(1.0F).add()
                        .variable(1).multiply()
                        .build()
                )
                .addModule(ConditionalStatModule.stat(ToolStats.PROJECTILE_DAMAGE).tool(ToolStackPredicate.tag(TinkerTags.Items.RANGED)).percent().formula()
                        .customVariable("lost", ToolVariable.CURRENT_DAMAGE)
                        .customVariable("max", new StatMultiplierVariable(ToolStats.DURABILITY))
                        .divide()
                        .sqrt()
                        .variable(0).multiply()
                        .constant(0.005F).multiply()
                        .constant(1.0F).add()
                        .variable(1).multiply()
                        .build()
                )
                .addModule(ConditionalMiningSpeedModule.builder().tool(ToolStackPredicate.tag(TinkerTags.Items.HARVEST)).percent().formula()
                        .customVariable("lost", ToolVariable.CURRENT_DAMAGE)
                        .customVariable("max", new StatMultiplierVariable(ToolStats.DURABILITY))
                        .divide()
                        .sqrt()
                        .variable(0).multiply()
                        .constant(0.005F).multiply()
                        .constant(1.0F).add()
                        .variable(1).multiply()
                        .build()
                )
                .addModule(ConditionalStatModule.stat(ToolStats.ARMOR).tool(ToolStackPredicate.tag(TinkerTags.Items.ARMOR)).percent().formula()
                        .customVariable("lost", ToolVariable.CURRENT_DAMAGE)
                        .customVariable("max", new StatMultiplierVariable(ToolStats.DURABILITY))
                        .divide()
                        .sqrt()
                        .variable(0).multiply()
                        .constant(0.005F).multiply()
                        .constant(1.0F).add()
                        .variable(1).multiply()
                        .build()
                )
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);

        buildModifier(TCModifiers.skyjade, modLoaded("deep_aether"))
                .addModule(ConditionalMeleeDamageModule.builder().tool(ToolStackPredicate.tag(TinkerTags.Items.MELEE)).percent().formula()
                        .customVariable("lost", ToolVariable.CURRENT_DAMAGE)
                        .customVariable("max", new StatMultiplierVariable(ToolStats.DURABILITY))
                        .divide()
                        .sqrt()
                        .variable(0).multiply()
                        .constant(-0.01F).multiply()
                        .constant(1.0F).add()
                        .variable(1).multiply()
                        .build()
                )
                .addModule(ConditionalStatModule.stat(ToolStats.PROJECTILE_DAMAGE).tool(ToolStackPredicate.tag(TinkerTags.Items.RANGED)).percent().formula()
                        .customVariable("lost", ToolVariable.CURRENT_DAMAGE)
                        .customVariable("max", new StatMultiplierVariable(ToolStats.DURABILITY))
                        .divide()
                        .sqrt()
                        .variable(0).multiply()
                        .constant(-0.01F).multiply()
                        .constant(1.0F).add()
                        .variable(1).multiply()
                        .build()
                )
                .addModule(ConditionalMiningSpeedModule.builder().tool(ToolStackPredicate.tag(TinkerTags.Items.HARVEST)).percent().formula()
                        .customVariable("lost", ToolVariable.CURRENT_DAMAGE)
                        .customVariable("max", new StatMultiplierVariable(ToolStats.DURABILITY))
                        .divide()
                        .sqrt()
                        .variable(0).multiply()
                        .constant(-0.01F).multiply()
                        .constant(1.0F).add()
                        .variable(1).multiply()
                        .build()
                )
                .addModule(ConditionalStatModule.stat(ToolStats.ARMOR).tool(ToolStackPredicate.tag(TinkerTags.Items.ARMOR)).percent().formula()
                        .customVariable("lost", ToolVariable.CURRENT_DAMAGE)
                        .customVariable("max", new StatMultiplierVariable(ToolStats.DURABILITY))
                        .divide()
                        .sqrt()
                        .variable(0).multiply()
                        .constant(-0.01F).multiply()
                        .constant(1.0F).add()
                        .variable(1).multiply()
                        .build()
                )
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);


        IJsonPredicate<LivingEntity> dreadPredicate = LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:dread"));
        buildModifier(TCModifiers.dreadbane, modLoaded("iceandfire"))
                .addModule(ConditionalMeleeDamageModule.builder().target(dreadPredicate).eachLevel(1.5f))
                .addModule(ConditionalPowerModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.RANGED)).target(dreadPredicate).eachLevel(1.75f))
                .addModule(MobEffectModule.builder(MobEffects.WITHER).target(dreadPredicate).level(RandomLevelingValue.flat(1)).time(RandomLevelingValue.random(20, 10)).chance(LevelingValue.flat(1)).build())
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        IJsonPredicate<LivingEntity> dampeningPredicate = LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:mythical"));
        buildModifier(TCModifiers.dampening, modLoaded("iceandfire"))
                .addModule(ConditionalMeleeDamageModule.builder().target(dampeningPredicate).eachLevel(2.5f))
                .addModule(ConditionalPowerModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.RANGED)).target(dampeningPredicate).eachLevel(1.75f))
                .addModule(MobEffectModule.builder(MobEffects.WEAKNESS).target(dampeningPredicate).level(RandomLevelingValue.flat(4)).time(RandomLevelingValue.random(20, 10)).chance(LevelingValue.flat(1)).build())
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        IJsonPredicate<LivingEntity> scorchbornPredicate = LivingEntityPredicate.or(LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:lightning_dragons")), LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:ice_dragons")));
        buildModifier(TCModifiers.scorchborn, modLoaded("iceandfire"))
                .addModule(ConditionalMeleeDamageModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.MELEE)).target(scorchbornPredicate).percent().eachLevel(0.45f))
                .addModule(ConditionalPowerModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.RANGED)).target(scorchbornPredicate).percent().eachLevel(0.65f))
                .addModule(ProtectionModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.ARMOR)).source(new DamageTypePredicate(ObjectRetriever.damageKey("iceandfire:dragon_fire"))).eachLevel(2.5f));
        IJsonPredicate<LivingEntity> frostbornPredicate = LivingEntityPredicate.or(LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:lightning_dragons")), LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:fire_dragons")));
        buildModifier(TCModifiers.frostborn, modLoaded("iceandfire"))
                .addModule(ConditionalMeleeDamageModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.MELEE)).target(frostbornPredicate).percent().eachLevel(0.45f))
                .addModule(ConditionalPowerModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.RANGED)).target(frostbornPredicate).percent().eachLevel(0.65f))
                .addModule(ProtectionModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.ARMOR)).source(new DamageTypePredicate(ObjectRetriever.damageKey("iceandfire:dragon_ice"))).eachLevel(2.5f));
        IJsonPredicate<LivingEntity> voltbornPredicate = LivingEntityPredicate.or(LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:ice_dragons")), LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:fire_dragons")));
        buildModifier(TCModifiers.voltborn, modLoaded("iceandfire"))
                .addModule(ConditionalMeleeDamageModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.MELEE)).target(voltbornPredicate).percent().eachLevel(0.45f))
                .addModule(ConditionalPowerModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.RANGED)).target(voltbornPredicate).percent().eachLevel(0.65f))
                .addModule(ProtectionModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.ARMOR)).source(new DamageTypePredicate(ObjectRetriever.damageKey("iceandfire:dragon_lightning"))).eachLevel(2.5f));
        IJsonPredicate<LivingEntity> wetPredicate = LivingEntityPredicate.or(LivingEntityPredicate.UNDERWATER, LivingEntityPredicate.RAINING, LivingEntityPredicate.FEET_IN_WATER, LivingEntityPredicate.EYES_IN_WATER);
        buildModifier(TCModifiers.tideGuardian, modLoaded("iceandfire"))
                .addModule(MobEffectUserModule.builder(MobEffects.WATER_BREATHING).toolItem(ItemPredicate.tag(TinkerTags.Items.HELMETS)).time(RandomLevelingValue.flat(50)).user(wetPredicate).build())
                .addModule(ConditionalMeleeDamageModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.CHESTPLATES)).attacker(wetPredicate).eachLevel(2.0f))
                .addModule(AttributeModule.builder(ForgeMod.SWIM_SPEED, AttributeModifier.Operation.ADDITION).toolItem(ItemPredicate.or(ItemPredicate.tag(TinkerTags.Items.LEGGINGS), ItemPredicate.tag(TinkerTags.Items.BOOTS))).uniqueFrom(TCModifiers.tideGuardian).amount(1.5f, 0.75f));
        buildModifier(TCModifiers.aquaShot, modLoaded("iceandfire"))
                .addModule(ConditionalStatModule.stat(ToolStats.PROJECTILE_DAMAGE).holder(wetPredicate).eachLevel(1.0f));
        buildModifier(TCModifiers.petrifying, modLoaded("iceandfire"))
                .addModule(ConditionalMeleeDamageModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.MELEE)).attacker(GlobalInit.SUN_EXPOSED).percent().amount(0.25F, 0.05F))
                .addModule(ConditionalStatModule.stat(ToolStats.PROJECTILE_DAMAGE).toolItem(ItemPredicate.tag(TinkerTags.Items.RANGED)).holder(GlobalInit.SUN_EXPOSED).percent().amount(0.25F, 0.05F))
                .addModule(ProtectionModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.ARMOR)).entity(GlobalInit.SUN_EXPOSED).amount(0.25F, 0.05F));
        IJsonPredicate<LivingEntity> allythropodPredicate = LivingEntityPredicate.or(new MobTypePredicate(MobType.ARTHROPOD).inverted(), LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:death_worms")));
        buildModifier(TCModifiers.allythropod, modLoaded("iceandfire"))
                .addModule(ConditionalMeleeDamageModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.MELEE)).target(allythropodPredicate).percent().amount(0.2f, 0.1F))
                .addModule(ConditionalPowerModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.RANGED)).target(allythropodPredicate).percent().amount(0.2f, 0.1F))
                .addModule(ProtectionModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.ARMOR)).attacker(allythropodPredicate.inverted()).eachLevel(3.5f));

        buildModifier(TCModifiers.cataclysmic, modLoaded("cataclysm"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS).priority(125)
                .addModule(new DurabilityBarColorModule(0xfb6b1d))
                .addModule(ReduceToolDamageModule.builder().flat(0.85F));
        buildModifier(TCModifiers.ghostly, modLoaded("cataclysm"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        IJsonPredicate<LivingEntity> ancientRemnant = LivingEntityPredicate.tag(TCEntityTagProv.create("cataclysm:team_ancient_remnant"));
        buildModifier(TCModifiers.archaeologist, modLoaded("cataclysm"))
                .addModule(ConditionalMeleeDamageModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.MELEE)).target(ancientRemnant).percent().eachLevel(0.25f))
                .addModule(ConditionalPowerModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.RANGED)).target(ancientRemnant).percent().eachLevel(0.35f))
                .addModule(ProtectionModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.ARMOR)).entity(ancientRemnant).amount(0.75f, 0.25f));
        buildModifier(TCModifiers.standstill, modLoaded("cataclysm"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS).priority(300)
                .addModule(KnockbackModule.builder().formula().constant(0.0f).build());
        buildModifier(TCModifiers.ignitium, modLoaded("cataclysm"))
                .addModule(ModifierRequirementsModule.builder().requireModifier(ModifierIds.netherite, 1).translationKey("recipe.tcompat.requirement.ignitium").build())
                .addModule(ReduceToolDamageModule.builder().flat(1))
                .addModule(StatBoostModule.add(ToolStats.ARMOR).toolTag(TinkerTags.Items.WORN_ARMOR).flat(3))
                .addModule(StatBoostModule.add(ToolStats.ARMOR_TOUGHNESS).toolTag(TinkerTags.Items.WORN_ARMOR).flat(1))
                .addModule(StatBoostModule.add(ToolStats.KNOCKBACK_RESISTANCE).toolTag(TinkerTags.Items.WORN_ARMOR).flat(0.05f));

        // Some of these only use attributes provided by lodestone so we can just require lodestone there.
        buildModifier(TCModifiers.stained, modLoaded("lodestone"))
                .addModule(OptionalAttributeModule.builder(TCompat.getResource("lodestone:magic_damage"), AttributeModifier.Operation.ADDITION).amount(1, 0.75f));
        buildModifier(TCModifiers.warded, modLoaded("malum"))
                .addModule(OptionalAttributeModule.builder(TCompat.getResource("malum:soul_ward_capacity"), AttributeModifier.Operation.ADDITION).eachLevel(1.5f))
                .addModule(OptionalAttributeModule.builder(TCompat.getResource("malum:soul_ward_recovery_rate"), AttributeModifier.Operation.MULTIPLY_BASE).amount(0.05f, 0.05f));
        buildModifier(TCModifiers.hallowed, modLoaded("malum"))
                .addModule(ModifierSlotModule.slot(MalumInit.RUNE_SLOT).flat(1)).levelDisplay(ModifierLevelDisplay.NO_LEVELS).build();
        buildModifier(TCModifiers.stronghold, modLoaded("malum"))
                .addModule(OptionalAttributeModule.builder(TCompat.getResource("malum:malignant_conversion"), AttributeModifier.Operation.ADDITION).amount(0.15f, 0.05f));
        buildModifier(TCModifiers.magicProficiency, modLoaded("lodestone"))
                .addModule(OptionalAttributeModule.builder(TCompat.getResource("lodestone:magic_proficiency"), AttributeModifier.Operation.ADDITION).amount(0.1f, 0.05f));
        buildModifier(TCModifiers.cloaking, modLoaded("malum"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.arcaneResonance, modLoaded("malum"))
                .addModule(OptionalAttributeModule.builder(TCompat.getResource("malum:arcane_resonance"), AttributeModifier.Operation.MULTIPLY_BASE).amount(0.1f, 0.1f));
        buildModifier(TCModifiers.spiritHarvester, modLoaded("malum"))
                .addModule(SoulExposureModule.INSTANCE)
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.runic, modLoaded("malum"))
                .addModule(ModifierSlotModule.slot(MalumInit.RUNE_SLOT).flat(1)).levelDisplay(ModifierLevelDisplay.NO_LEVELS).build();
        buildModifier(TCModifiers.spoiled, modLoaded("malum"))
                .addModule(OptionalAttributeModule.builder(TCompat.getResource("malum:spirit_spoils"), AttributeModifier.Operation.ADDITION).slots(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND).unique("spoiled").amount(0, 1));
        buildModifier(TCModifiers.integral, modLoaded("malum"))
                .addModule(OptionalAttributeModule.builder(TCompat.getResource("malum:soul_ward_integrity"), AttributeModifier.Operation.MULTIPLY_BASE).slots(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET).unique("integral").amount(0f, 0.05f));
        buildModifier(TCModifiers.runeOfIdleRestoration, modLoaded("malum"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.runeOfVolatileDistortion, modLoaded("malum"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.runeOfAlimentCleansing, modLoaded("malum"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.runeOfReactiveShielding, modLoaded("malum"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.runeOfFervor, modLoaded("malum"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.runeOfTheHeretic, modLoaded("malum"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.runeOfIgneousSolace, modLoaded("malum"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.runeOfSacrificialEmpowerment, modLoaded("malum"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.runeOfTwinnedDuration, modLoaded("malum"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);

        buildModifier(TCModifiers.warden, modLoaded("deeperdarker"))
                .addModule(StatBoostModule.multiplyBase(ToolStats.DURABILITY).flat(0.24f))
                .addModule(StatBoostModule.add(ToolStats.ATTACK_DAMAGE).toolTag(TinkerTags.Items.HELD).flat(1))
                .addModule(StatBoostModule.add(ToolStats.ARMOR).toolTag(TinkerTags.Items.WORN_ARMOR).flat(1))
                .addModule(StatBoostModule.add(ToolStats.ARMOR_TOUGHNESS).toolTag(TinkerTags.Items.WORN_ARMOR).flat(1))
                .addModule(VibrationDampeningModule.event(GameEventEntry.builder().tagged(TCTags.GameEvents.FEET_VIBRATION).build()).toolTag(TinkerTags.Items.BOOTS).build())
                .addModule(VibrationDampeningModule.event(GameEventEntry.builder().tagged(TCTags.GameEvents.LEGS_VIBRATION).build()).toolTag(TinkerTags.Items.LEGGINGS).build())
                .addModule(VibrationDampeningModule.event(GameEventEntry.builder().tagged(TCTags.GameEvents.CHEST_VIBRATION).build()).toolTag(TinkerTags.Items.CHESTPLATES).build())
                .addModule(VibrationDampeningModule.event(GameEventEntry.builder().tagged(TCTags.GameEvents.WINGS_VIBRATION).build()).tool(GlobalInit.HAS_WINGS).build())
                .addModule(VibrationDampeningModule.event(GameEventEntry.builder().tagged(TCTags.GameEvents.HEAD_VIBRATION).build()).toolTag(TinkerTags.Items.HELMETS).build())
                .addModule(VibrationDampeningModule.event(GameEventEntry.builder().tagged(TCTags.GameEvents.RANGED_VIBRATION).build()).toolTag(TinkerTags.Items.RANGED).build())
                .addModule(VibrationDampeningModule.event(GameEventEntry.builder().tagged(TCTags.GameEvents.COMBAT_VIBRATION).build()).toolTag(TinkerTags.Items.MELEE_PRIMARY).build())
                .addModule(VibrationDampeningModule.event(GameEventEntry.builder().tagged(TCTags.GameEvents.HARVEST_VIBRATION).build()).toolTag(TinkerTags.Items.HARVEST_PRIMARY).build())
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.brightness, modLoaded("deeperdarker"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.heartbeat, modLoaded("deeperdarker"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Modifiers";
    }

    private ICondition allModsLoaded(String... modid) {
        List<ICondition> conditions = new ArrayList<>();
        for (var id : modid) {
            conditions.add(new ModLoadedCondition(id));
        }
        return new AndCondition(conditions.toArray(new ICondition[0]));
    }
}
