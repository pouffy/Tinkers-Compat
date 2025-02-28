package com.pouffydev.tcompat.data.material;

import com.aetherteam.aether.item.combat.AetherArmorMaterials;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.holystone.HolystonePickaxeItem;
import com.pouffydev.tcompat.material.TComMaterialIds;
import net.minecraft.data.PackOutput;
import net.zepalesque.redux.item.util.ReduxItemTiers;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import slimeknights.tconstruct.tools.stats.*;
import teamrazor.deepaether.init.DATiers;
import teamrazor.deepaether.item.gear.DaArmorMaterials;

import static net.minecraft.world.item.Tiers.*;

public class TComMaterialStatsProv extends AbstractMaterialStatsDataProvider {
    public TComMaterialStatsProv(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    @Override
    protected void addMaterialStats() {
        addMeleeHarvest();
        addRanged();
        addArmor();
        addMisc();
    }

    private void addMeleeHarvest() {
        //Aether
        addMaterialStats(TComMaterialIds.aetherWood,
                new HeadMaterialStats(60, 2f, WOOD, 0f),
                HandleMaterialStats.percents().build(), // flat all around
                StatlessMaterialStats.BINDING);
        addMaterialStats(TComMaterialIds.aetherRock,
                new HeadMaterialStats(130, 4f, STONE, 1f),
                HandleMaterialStats.multipliers().durability(0.9f).miningSpeed(1.05f).build(),
                StatlessMaterialStats.BINDING);
        addMaterialStats(TComMaterialIds.zanite,
                new HeadMaterialStats(251, 6f, IRON, 2f));
        addMaterialStats(TComMaterialIds.skyjade,
                new HeadMaterialStats(150, 10f, IRON, 2f));
        addMaterialStats(TComMaterialIds.gravitite,
                new HeadMaterialStats(1562, 3f, DIAMOND, 3f),
                HandleMaterialStats.multipliers().durability(0.95f).miningSpeed(1.12f).build(),
                StatlessMaterialStats.BINDING);
        addMaterialStats(TComMaterialIds.veridium,
                new HeadMaterialStats(750, 2.25f, IRON, 1.5f),
                HandleMaterialStats.multipliers().durability(0.85f).miningSpeed(0.95f).build(),
                StatlessMaterialStats.BINDING);
        addMaterialStats(TComMaterialIds.refinedSentrite,
                new HeadMaterialStats(1126, 5f, DIAMOND, 3.5f));

        //Twilight Forest
        addMaterialStats(TComMaterialIds.twilightWood,
                new HeadMaterialStats(60, 2f, WOOD, 0f),
                HandleMaterialStats.percents().build(), // flat all around
                StatlessMaterialStats.BINDING);
        addMaterialStats(TComMaterialIds.fiery,
                new HeadMaterialStats(720, 3f, DIAMOND, 6.5f),
                HandleMaterialStats.multipliers().durability(0.85f).miningSpeed(1.06f).build(),
                StatlessMaterialStats.BINDING);
        addMaterialStats(TComMaterialIds.knightmetal,
                new HeadMaterialStats(900, 1f, NETHERITE, 6f),
                HandleMaterialStats.multipliers().durability(0.95f).miningSpeed(1.26f).build(),
                StatlessMaterialStats.BINDING);
        addMaterialStats(TComMaterialIds.nagascale,
                new HeadMaterialStats(460, 2f, STONE, 9f));
        addMaterialStats(TComMaterialIds.steeleaf,
                new HeadMaterialStats(180, 2.5f, IRON, 6f),
                HandleMaterialStats.multipliers().durability(1.15f).miningSpeed(0.95f).build(),
                StatlessMaterialStats.BINDING);



    }

    private void addRanged() {
        addMaterialStats(TComMaterialIds.aetherWood,
                new LimbMaterialStats(60, 0, 0, 0),
                new GripMaterialStats(0f, 0, 0));
        addMaterialStats(TComMaterialIds.twilightWood,
                new LimbMaterialStats(60, 0, 0, 0),
                new GripMaterialStats(0f, 0, 0));

        addMaterialStats(TComMaterialIds.fiery,
                new LimbMaterialStats(720, 0.15f, 0.2f, 0.2f),
                new GripMaterialStats(-0.1f, -0.15f, 4.5f));
        addMaterialStats(TComMaterialIds.knightmetal,
                new LimbMaterialStats(900, 0.25f, 0.25f, 0.15f),
                new GripMaterialStats(-0.2f, -0.2f, 3f));
        addMaterialStats(TComMaterialIds.steeleaf,
                new LimbMaterialStats(180, 0.05f, 0.15f, 0.25f),
                new GripMaterialStats(-0.05f, 0.25f, 4f));

        addMaterialStats(TComMaterialIds.ravenFeather,
                StatlessMaterialStats.BOWSTRING);
        addMaterialStats(TComMaterialIds.gravitite,
                new LimbMaterialStats(1562, 0.15f, 0.25f, 0.35f),
                new GripMaterialStats(-0.05f, 0.35f, 3f));
        addMaterialStats(TComMaterialIds.refinedSentrite,
                new GripMaterialStats(0.15f, 0.15f, 3.5f));
    }

    private void addArmor() {
        addMaterialStats(TComMaterialIds.aetherWood, StatlessMaterialStats.SHIELD_CORE);
        addMaterialStats(TComMaterialIds.twilightWood, StatlessMaterialStats.SHIELD_CORE);
        addArmorShieldStats(TComMaterialIds.fiery,     PlatingMaterialStats.builder().durabilityFactor(56).armor(4, 7, 9, 4).knockbackResistance(1.5f), StatlessMaterialStats.MAILLE);
        addArmorShieldStats(TComMaterialIds.knightmetal,     PlatingMaterialStats.builder().durabilityFactor(56).armor(3, 6, 8, 3).toughness(1f), StatlessMaterialStats.MAILLE);
        addArmorShieldStats(TComMaterialIds.zanite, PlatingMaterialStats.builder().durabilityFactor(15).armor(2, 5, 6, 2));
        addArmorShieldStats(TComMaterialIds.skyjade, PlatingMaterialStats.builder().durabilityFactor(3).armor(3, 6, 8, 3));
        addArmorShieldStats(TComMaterialIds.gravitite, PlatingMaterialStats.builder().durabilityFactor(33).armor(3, 6, 8, 3).toughness(2f), StatlessMaterialStats.MAILLE);
        addArmorShieldStats(TComMaterialIds.veridium, PlatingMaterialStats.builder().durabilityFactor(19).armor(4, 6, 7, 4).toughness(0.5f), StatlessMaterialStats.MAILLE);
    }

    private void addMisc() {


    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Stats";
    }
}
