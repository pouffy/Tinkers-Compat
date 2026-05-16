package io.github.pouffy.tcompat.compat.species;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

public class SpeciesMaterials {

    public static final MaterialId wickedWax = MaterialBuilder.material("species", "wicked_wax")
            .data(d -> d.tier(1).order(20).craftable(true))
            .traits(t -> t.trait(SpeciesInit.wicked))
            .stats(s ->
                    s.stat(StatlessMaterialStats.BINDING))
            .renderInfo(r -> r.color(0xf93985).fallbacks("bone", "metal"))
            .spriteInfo(s -> s.fallbacks("bone", "metal").repairKit().statType(StatlessMaterialStats.BINDING.getIdentifier()).sixColor(0xFF750d83, 0xFFb20e7b, 0xFFf93985, 0xFFff5e3e, 0xFFffa342, 0xFFfde46d))
            .buildMaterial();

    public static void staticInit() {}
}
