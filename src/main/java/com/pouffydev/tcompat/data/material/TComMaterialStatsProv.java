package com.pouffydev.tcompat.data.material;

import com.pouffydev.tcompat.material.TComMaterialIds;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import slimeknights.tconstruct.tools.stats.*;

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
        addMaterialStats(TComMaterialIds.aetherWood,
                new HeadMaterialStats(60, 2f, WOOD, 0f),
                HandleMaterialStats.percents().build(), // flat all around
                StatlessMaterialStats.BINDING);
        addMaterialStats(TComMaterialIds.aetherRock,
                new HeadMaterialStats(130, 4f, STONE, 1f),
                HandleMaterialStats.multipliers().durability(0.9f).miningSpeed(1.05f).build(),
                StatlessMaterialStats.BINDING);
    }

    private void addRanged() {
        addMaterialStats(TComMaterialIds.aetherWood,
                new LimbMaterialStats(60, 0, 0, 0),
                new GripMaterialStats(0f, 0, 0));
    }

    private void addArmor() {
        addMaterialStats(TComMaterialIds.aetherWood, StatlessMaterialStats.SHIELD_CORE);

    }

    private void addMisc() {


    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Stats";
    }
}
