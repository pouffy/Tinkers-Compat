package io.github.pouffy.tcompat.compat.quark;

import io.github.pouffy.tcompat.common.material.MaterialCreator;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.stats.RepairStats;

public class QuarkMaterials {

    public static final MaterialId crab = MaterialCreator.material("quark", "crab")
            .flavor("Resilient")
            .data(d -> d.tier(1).order(2).craftable(true))
            .traits(t -> t.trait(ModifierIds.knockbackResistance))
            .stats(s -> s.stat(RepairStats.shell(100)))
            .renderInfo(r -> r.color(0xbc441a).fallbacks("bone", "metal"))
            .spriteInfo(s -> s.fallbacks("bone", "metal").repairKit().shell().sixColor(0xFF640700, 0xFF8e1202, 0xFFa72f13, 0xFFbc441a, 0xFFe86436, 0xFFffa889))
            .buildMaterial();

    public static void staticInit() {}
}
