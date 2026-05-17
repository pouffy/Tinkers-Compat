package io.github.pouffy.tcompat.datagen.tinkers.modifier;

import io.github.pouffy.tcompat.common.TCCommonEvents;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.common.module.AetherForgedModule;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.datagen.tag.TCEntityTagProv;
import net.minecraft.core.BlockPos;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
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
import slimeknights.tconstruct.library.modifiers.impl.BasicModifier;
import slimeknights.tconstruct.library.modifiers.modules.armor.ProtectionModule;
import slimeknights.tconstruct.library.modifiers.modules.behavior.ConditionalStatModule;
import slimeknights.tconstruct.library.modifiers.modules.behavior.ReduceToolDamageModule;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.ConditionalMeleeDamageModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.ConditionalPowerModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.KnockbackModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.MobEffectModule;
import slimeknights.tconstruct.library.modifiers.modules.display.DurabilityBarColorModule;
import slimeknights.tconstruct.library.modifiers.modules.mining.ConditionalMiningSpeedModule;
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
                .addModule(AetherForgedModule.INSTANCE).priority(200)
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(AetherInit.ambrogen.getId(), modLoaded("aether"))
                .levelDisplay(ModifierLevelDisplay.DEFAULT);
        buildModifier(AetherInit.ascension.getId(), modLoaded("aether"))
                .levelDisplay(ModifierLevelDisplay.DEFAULT);
        buildModifier(AetherReduxInit.ambrofusion.getId(), modLoaded("aether_redux"))
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS);
        buildModifier(TCModifiers.blighted, modLoaded("aether_redux"))
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
                .addModule(MobEffectModule.builder(MobEffects.WATER_BREATHING).toolItem(ItemPredicate.tag(TinkerTags.Items.ARMOR)).target(wetPredicate).build())
                .addModule(ConditionalMeleeDamageModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.ARMOR)).target(wetPredicate).percent().eachLevel(0.65f));
        buildModifier(TCModifiers.aquaShot, modLoaded("iceandfire"))
                .addModule(ConditionalStatModule.stat(ToolStats.PROJECTILE_DAMAGE).holder(wetPredicate).eachLevel(1.0f));
        buildModifier(TCModifiers.petrifying, modLoaded("iceandfire"))
                .addModule(ConditionalMeleeDamageModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.MELEE)).attacker(GlobalInit.SUN_EXPOSED).percent().amount(0.25F, 0.05F))
                .addModule(ConditionalStatModule.stat(ToolStats.PROJECTILE_DAMAGE).toolItem(ItemPredicate.tag(TinkerTags.Items.RANGED)).holder(GlobalInit.SUN_EXPOSED).percent().amount(0.25F, 0.05F))
                .addModule(ProtectionModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.ARMOR)).entity(GlobalInit.SUN_EXPOSED).amount(0.25F, 0.05F));
        IJsonPredicate<LivingEntity> allythropodPredicate = LivingEntityPredicate.or(new MobTypePredicate(MobType.ARTHROPOD).inverted(), LivingEntityPredicate.tag(TCEntityTagProv.create("tcompat:death_worms")));
        buildModifier(TCModifiers.allythropod, modLoaded("iceandfire"))
                .addModule(ConditionalMeleeDamageModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.MELEE)).target(allythropodPredicate).amount(5.0F, 0.25F))
                .addModule(ConditionalPowerModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.RANGED)).target(allythropodPredicate).amount(5.0F, 0.25F))
                .addModule(ProtectionModule.builder().toolItem(ItemPredicate.tag(TinkerTags.Items.ARMOR)).attacker(allythropodPredicate.inverted()).eachLevel(1.5f));

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
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Modifiers";
    }
}
