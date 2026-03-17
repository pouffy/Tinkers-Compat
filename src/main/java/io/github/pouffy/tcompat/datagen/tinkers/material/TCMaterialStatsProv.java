package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.common.material.TCMaterials;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ArmorMaterial;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import slimeknights.tconstruct.tools.stats.*;

import static net.minecraft.world.item.Tiers.*;

public class TCMaterialStatsProv extends AbstractMaterialStatsDataProvider {
    public TCMaterialStatsProv(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    @Override
    protected void addMaterialStats() {
        addMeleeHarvest();
        addRanged();
        addAmmo();
        addArmor();
        addMisc();
    }

    private void addMeleeHarvest() {
        addMaterialStats(TCMaterials.aetherWood,
                new HeadMaterialStats(60, 2f, WOOD, 0f),
                HandleMaterialStats.percents().build(), // flat all around
                StatlessMaterialStats.BINDING);
        addMaterialStats(TCMaterials.hellbark,
                new HeadMaterialStats(60, 2f, WOOD, 0f),
                HandleMaterialStats.percents().build(), // flat all around
                StatlessMaterialStats.BINDING);
        addMaterialStats(TCMaterials.aetherRock,
                new HeadMaterialStats(130, 4f, STONE, 1f),
                HandleMaterialStats.multipliers().durability(0.9f).miningSpeed(1.05f).build(),
                StatlessMaterialStats.BINDING);
        addMaterialStats(TCMaterials.zanite,
                new HeadMaterialStats(251, 6f, IRON, 2f));
        addMaterialStats(TCMaterials.skyjade,
                new HeadMaterialStats(150, 10f, IRON, 2f));
        addMaterialStats(TCMaterials.gravitite,
                new HeadMaterialStats(1562, 3f, DIAMOND, 3f),
                HandleMaterialStats.multipliers().durability(0.95f).miningSpeed(1.12f).build(),
                StatlessMaterialStats.BINDING);
        addMaterialStats(TCMaterials.veridium,
                new HeadMaterialStats(750, 2.25f, IRON, 3.5f),
                HandleMaterialStats.multipliers().durability(0.85f).miningSpeed(0.95f).build(),
                StatlessMaterialStats.BINDING);
        addMaterialStats(TCMaterials.refinedSentrite,
                new HeadMaterialStats(1126, 5f, DIAMOND, 3.5f));
        addMaterialStats(TCMaterials.valkyrum, new HeadMaterialStats(1562, 4f, DIAMOND, 3f));
        addMaterialStats(TCMaterials.desh,
                new HeadMaterialStats(940, 4f, DIAMOND, 2.6f),
                HandleMaterialStats.multipliers().durability(0.65f).miningSpeed(-0.15f).attackDamage(0.75f).build(),
                StatlessMaterialStats.BINDING);
        addMaterialStats(TCMaterials.calorite,
                new HeadMaterialStats(1250, 5f, DIAMOND, 3.8f),
                HandleMaterialStats.multipliers().durability(1.05f).miningSpeed(1.05f).attackDamage(1.05f).build(),
                StatlessMaterialStats.BINDING);
        addMaterialStats(TCMaterials.ostrum,
                new HeadMaterialStats(1840, 6f, NETHERITE, 4.5f),
                HandleMaterialStats.multipliers().durability(0.85f).miningSpeed(0.25f).attackDamage(1.5f).attackSpeed(-0.2f).build(),
                StatlessMaterialStats.BINDING);
    }

    private void addRanged() {
        addMaterialStats(TCMaterials.aetherWood,
                new LimbMaterialStats(60, 0, 0, 0),
                new GripMaterialStats(0f, 0, 0));
        addMaterialStats(TCMaterials.hellbark,
                new LimbMaterialStats(60, 0, 0, 0),
                new GripMaterialStats(0f, 0, 0));
        addMaterialStats(TCMaterials.pyral,
                new LimbMaterialStats(80, -0.1f, 0.1f, -0.15f),
                new GripMaterialStats(-0.2f, -0.35f, 0));
        addMaterialStats(TCMaterials.desh,
                new LimbMaterialStats(940, 0.15f, 0.09f, 0.03f),
                new GripMaterialStats(0.65f, 0.06f, 2.6f));
        addMaterialStats(TCMaterials.calorite,
                new LimbMaterialStats(1250, 0.1f, 0.13f, -0.05f),
                new GripMaterialStats(1.05f, 0.35f, 3.8f));
        addMaterialStats(TCMaterials.ostrum,
                new LimbMaterialStats(1840, 0.1f, 0.2f, 0.05f),
                new GripMaterialStats(0.85f, -0.1f, 4.5f));
    }

    private void addAmmo() {
        addMaterialStats(TCMaterials.aetherWood, StatlessMaterialStats.ARROW_SHAFT);
        addMaterialStats(TCMaterials.hellbark, StatlessMaterialStats.ARROW_SHAFT);

        addMaterialStats(TCMaterials.skyjade, StatlessMaterialStats.ARROW_HEAD);
        addMaterialStats(TCMaterials.zanite, StatlessMaterialStats.ARROW_HEAD);
        addMaterialStats(TCMaterials.blightbunnyFang, StatlessMaterialStats.ARROW_HEAD);

        addMaterialStats(TCMaterials.zanite, StatlessMaterialStats.FLETCHING);
    }

    private void addArmor() {
        addMaterialStats(TCMaterials.aetherWood, StatlessMaterialStats.SHIELD_CORE);
        addMaterialStats(TCMaterials.hellbark, StatlessMaterialStats.SHIELD_CORE);
        addMaterialStats(TCMaterials.mykapodShell, StatlessMaterialStats.SHIELD_CORE);
        addArmorShieldStats(TCMaterials.zanite, PlatingMaterialStats.builder().durabilityFactor(15).armor(2, 5, 6, 2), StatlessMaterialStats.MAILLE);
        addArmorShieldStats(TCMaterials.skyjade, PlatingMaterialStats.builder().durabilityFactor(3).armor(3, 6, 8, 3), StatlessMaterialStats.MAILLE);
        addArmorShieldStats(TCMaterials.gravitite, PlatingMaterialStats.builder().durabilityFactor(33).armor(3, 6, 8, 3).toughness(2f), StatlessMaterialStats.MAILLE);
        addArmorShieldStats(TCMaterials.veridium, PlatingMaterialStats.builder().durabilityFactor(19).armor(4, 6, 7, 4).toughness(0.5f), StatlessMaterialStats.MAILLE);
        addArmorShieldStats(TCMaterials.refinedSentrite, PlatingMaterialStats.builder().durabilityFactor(26).armor(5.2f, 7.2f, 8.2f, 5.2f).knockbackResistance(1).toughness(0.8f), StatlessMaterialStats.MAILLE);
        addArmorShieldStats(TCMaterials.pyral, PlatingMaterialStats.builder().durabilityFactor(33).armor(3, 6, 8, 3).toughness(2f), StatlessMaterialStats.MAILLE);
        addArmorShieldStats(TCMaterials.valkyrum, PlatingMaterialStats.builder().durabilityFactor(33).armor(3, 6, 8, 3).toughness(2f), StatlessMaterialStats.MAILLE);
        addMaterialStats(TCMaterials.neptune, StatlessMaterialStats.MAILLE);
        addArmorShieldStats(TCMaterials.desh, PlatingMaterialStats.builder().durabilityFactor(35).armor(4, 9, 7, 4).toughness(2f), StatlessMaterialStats.MAILLE);
        addArmorShieldStats(TCMaterials.calorite, PlatingMaterialStats.builder().durabilityFactor(36).armor(4, 9, 7, 4).toughness(2.5f).knockbackResistance(0.1f), StatlessMaterialStats.MAILLE);
        addArmorShieldStats(TCMaterials.ostrum, PlatingMaterialStats.builder().durabilityFactor(37).armor(4, 9, 7, 4).toughness(3f).knockbackResistance(0.1f), StatlessMaterialStats.MAILLE);
    }

    private void addMisc() {
        addMaterialStats(TCMaterials.wickedWax, StatlessMaterialStats.BINDING);
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Stats";
    }
}
