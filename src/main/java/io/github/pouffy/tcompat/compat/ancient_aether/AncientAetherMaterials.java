package io.github.pouffy.tcompat.compat.ancient_aether;

import io.github.pouffy.tcompat.common.material.TCWoods;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

import static io.github.pouffy.tcompat.compat.aether.AetherMaterials.aetherWoodVariant;

public class AncientAetherMaterials {


    public static final MaterialVariantId highsproot = aetherWoodVariant(TCWoods.HIGHSPROOT, 0xFF26211c, 0xFF302a23, 0xFF3a332a, 0xFF494036, 0xFF52483b, 0xFF5b5142, 0xFF65594a);
    public static final MaterialVariantId sakura = aetherWoodVariant(TCWoods.AETHER_SAKURA, 0xFF171211, 0xFF211a17, 0xFF2a201e, 0xFF342925, 0xFF3d302c, 0xFF473832, 0xFF4e3c38);

    public static void staticInit() {}
}
