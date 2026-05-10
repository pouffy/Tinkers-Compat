package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.tools.stats.*;

@MethodsReturnNonnullByDefault
public class TCMaterialStatsProv extends AbstractMaterialStatsDataProvider {
    public TCMaterialStatsProv(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    @Override
    protected void addMaterialStats() {
        for (MaterialBuilder builder : MaterialBuilder.materialBuilders) {
            if (builder.isVariant()) continue;
            addMaterialStats(builder.materialId(), builder.getStats().stats().values().toArray(new IMaterialStats[0]));
            addOptionalStats(builder.materialId(), builder.getStats().optionalStats().values().toArray(new IMaterialStats[0]));
        }
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Stats";
    }
}
