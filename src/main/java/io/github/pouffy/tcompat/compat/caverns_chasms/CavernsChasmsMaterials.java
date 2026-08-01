package io.github.pouffy.tcompat.compat.caverns_chasms;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.stats.*;

public class CavernsChasmsMaterials {

    public static final MaterialId zirconia = MaterialBuilder.material("caverns_and_chasms", "zirconia")
            .flavor("Can it fix it? Yes it can!")
            .data(d -> d.tier(1).order(1).craftable(false))
            .stats(s -> s.stat(StatlessMaterialStats.REPAIR_KIT))
            .renderInfo(r -> r.color(0xb0d18d).fallbacks("gem"))
            .spriteInfo(s -> s.fallbacks("gem").repairKit().sixColor(0xFF769be5, 0xFFb898c3, 0xFFe1a48d, 0xFFb0d18d, 0xFFfff1a9, 0xFFffffff))
            .buildMaterial();

    public static void staticInit() {}
}
