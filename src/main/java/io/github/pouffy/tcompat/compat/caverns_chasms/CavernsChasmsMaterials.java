package io.github.pouffy.tcompat.compat.caverns_chasms;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.compat.CompatToolStats;
import io.github.pouffy.tcompat.compat.constructs_casting.MagicBaseMaterialStats;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.stats.*;

public class CavernsChasmsMaterials {

    public static final MaterialId spinel = MaterialBuilder.material("caverns_and_chasms", "spinel")
            .flavor("Leave no trace.")
            .data(d -> d.tier(1).order(4).craftable(true))
            .traits(t -> t
                    .trait(StatlessMaterialStats.ARROW_HEAD.getIdentifier(), CavernsChasmsInit.blunt)
                    .trait(CompatToolStats.Statless.CUT_GEM.getIdentifier(), TCModifiers.bluntHands)
                    .trait(CompatToolStats.Statless.ADORNMENT.getIdentifier(), CavernsChasmsInit.nullification)
            )
            .stats(s -> s
                    .stat(
                            StatlessMaterialStats.ARROW_HEAD
                    ).statOptional(
                            CompatToolStats.Statless.CUT_GEM,
                            CompatToolStats.Statless.ADORNMENT
                    ))
            .renderInfo(r -> r.color(0xac6cb2).fallbacks("gem", "spinel"))
            .spriteInfo(s -> s.arrowHead().statType(CompatToolStats.Statless.CUT_GEM.getIdentifier()).statType(CompatToolStats.Statless.ADORNMENT.getIdentifier()).fallbacks("gem", "spinel").sixColor(0xFF6b302e, 0xFF822e66, 0xFFbc5186, 0xFFac6cb2, 0xFFd684ac, 0xFFe6d2f7))
            .buildMaterial();

    public static final MaterialId zirconia = MaterialBuilder.material("caverns_and_chasms", "zirconia")
            .flavor("Can it fix it? Yes it can!")
            .data(d -> d.tier(1).order(1).craftable(false))
            .stats(s -> s.stat(StatlessMaterialStats.REPAIR_KIT))
            .renderInfo(r -> r.color(0xf6a181).fallbacks("gem"))
            .spriteInfo(s -> s.fallbacks("gem").repairKit().sixColor(0xFF134341, 0xFF63618e, 0xFF9e55a0, 0xFFf6a181, 0xFFfff1a9, 0xFFffffff))
            .buildMaterial();

    public static void staticInit() {}
}
