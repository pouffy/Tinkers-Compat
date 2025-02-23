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
        //Aether
        addMaterialStats(TComMaterialIds.aetherWood,
                new HeadMaterialStats(60, 2f, WOOD, 0f),
                HandleMaterialStats.percents().build(), // flat all around
                StatlessMaterialStats.BINDING);
        addMaterialStats(TComMaterialIds.aetherRock,
                new HeadMaterialStats(130, 4f, STONE, 1f),
                HandleMaterialStats.multipliers().durability(0.9f).miningSpeed(1.05f).build(),
                StatlessMaterialStats.BINDING);

        //Twilight Forest
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
    }

    private void addArmor() {
        addMaterialStats(TComMaterialIds.aetherWood, StatlessMaterialStats.SHIELD_CORE);
        addArmorShieldStats(TComMaterialIds.fiery,     PlatingMaterialStats.builder().durabilityFactor(56).armor(4, 7, 9, 4).knockbackResistance(1.5f), StatlessMaterialStats.MAILLE);
        addArmorShieldStats(TComMaterialIds.knightmetal,     PlatingMaterialStats.builder().durabilityFactor(56).armor(3, 6, 8, 3).toughness(1f), StatlessMaterialStats.MAILLE);

    }

    private void addMisc() {


    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Stats";
    }
}
