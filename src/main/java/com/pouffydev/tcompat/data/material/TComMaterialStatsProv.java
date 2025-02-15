package com.pouffydev.tcompat.data.material;

import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;

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
        //addMaterialStats(GTCMaterialIds.bismuth,
        //        new HeadMaterialStats(270, 6.5f, IRON, 1.5f),
        //        HandleMaterialStats.multipliers().durability(1.05f).build(),
        //        StatlessMaterialStats.BINDING);

    }

    private void addRanged() {
        //addMaterialStats(GTCMaterialIds.bismuth,
        //        new LimbMaterialStats(270, -0.05f, 0.15f, 0),
        //        new GripMaterialStats(1.05f, 0f, 1.5f));
    }

    private void addArmor() {
        //addArmorShieldStats(GTCMaterialIds.blackSteel,      PlatingMaterialStats.builder().durabilityFactor(29).armor(2, 5, 7, 2).toughness(2), StatlessMaterialStats.MAILLE);
    }

    private void addMisc() {

    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Stats";
    }
}
