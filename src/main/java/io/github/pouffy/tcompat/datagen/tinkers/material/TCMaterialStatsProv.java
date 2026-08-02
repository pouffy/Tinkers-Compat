package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.compat.aether.item.DartBarrelMaterialStats;
import io.github.pouffy.tcompat.compat.aether.item.LipGuardMaterialStats;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import static io.github.pouffy.tcompat.TCompat.getResource;

@MethodsReturnNonnullByDefault
public class TCMaterialStatsProv extends AbstractMaterialStatsDataProvider {
    public TCMaterialStatsProv(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    @Override
    protected void addMaterialStats() {
        int count = 0;
        int optionalCount = 0;
        for (MaterialBuilder builder : MaterialBuilder.materialBuilders) {
            if (builder.isVariant()) continue;
            addMaterialStats(builder.materialId(), builder.getStats().stats().values().toArray(new IMaterialStats[0]));
            addOptionalStats(builder.materialId(), builder.getStats().optionalStats().values().toArray(new IMaterialStats[0]));
            count++;
            if (!builder.getStats().optionalStats().isEmpty()) {
                optionalCount++;
            }
        }
        TCompat.LOGGER.info("Added stats for {} materials", count);
        TCompat.LOGGER.info("Added optional (integration) stats for {} materials", optionalCount);

        //Existing materials
        //Pull requests for these would be considered, but I'd have to examine them carefully.
        addOptionalStats(MaterialIds.wood, new DartBarrelMaterialStats(60, 1f, 1f));
        addOptionalStats(MaterialIds.treatedWood, new DartBarrelMaterialStats(300, -0.15f, 1.5f));
        addOptionalStats(MaterialIds.ironwood, new DartBarrelMaterialStats(512, -0.15f, 2));
        addOptionalStats(MaterialIds.platedSlimewood, new DartBarrelMaterialStats(595, -0.1f, 2));
        addOptionalStats(MaterialIds.slimewood, new DartBarrelMaterialStats(375, -0.2f, 1));
        addOptionalStats(MaterialIds.rock, new DartBarrelMaterialStats(130, -0.15f, 2f));
        addOptionalStats(MaterialIds.scorchedStone, new DartBarrelMaterialStats(120, -0.2f, 2.5f));
        addOptionalStats(MaterialIds.searedStone, new DartBarrelMaterialStats(225, 1f, 1.5f));
        addOptionalStats(MaterialIds.whitestone, new DartBarrelMaterialStats(275, -0.05f, 1.25f));
        addOptionalStats(MaterialIds.bone, new DartBarrelMaterialStats(100, 0.05f, 1.25f));
        addOptionalStats(MaterialIds.blazingBone, new DartBarrelMaterialStats(530, -0.1f, 3f));
        addOptionalStats(MaterialIds.necroticBone, new DartBarrelMaterialStats(125, 0.1f, 2.25f));
        addOptionalStats(MaterialIds.venombone, new DartBarrelMaterialStats(175, -0.1f, 2.25f));

        //Integration (might add more here?)
        //Use this as a template for any non-tinkers existing materials.
        addOptionalStats(new MaterialId(getResource("constructs_casting", "pyrium")), new LipGuardMaterialStats(-0.1f, -0.2f, 0.2f));
        addOptionalStats(new MaterialId(getResource("constructs_casting", "mithril")), new LipGuardMaterialStats(0.2f, 0.1f, -0.1f));
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Stats";
    }
}
