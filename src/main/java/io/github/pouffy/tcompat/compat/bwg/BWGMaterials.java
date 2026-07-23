package io.github.pouffy.tcompat.compat.bwg;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

public class BWGMaterials {

    public static final MaterialVariantId skyrisVine = MaterialBuilder.variant("biomeswevegone", "skyris_vine", MaterialIds.vine)
            .renderInfo(r -> r.color(0xFFcf8086).fallbacks("primitive", "cloth"))
            .spriteInfo(s -> s.vines().sixColor(0xFF693f38, 0xFF7c504c, 0xFF9f6963, 0xFFcf8086, 0xFFf8a1a4, 0xFFffccce))
            .buildVariant();

    public static void staticInit() {}
}
