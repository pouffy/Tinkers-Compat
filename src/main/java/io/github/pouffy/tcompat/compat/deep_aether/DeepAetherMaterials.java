package io.github.pouffy.tcompat.compat.deep_aether;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCMaterials;
import io.github.pouffy.tcompat.common.material.TCRocks;
import io.github.pouffy.tcompat.common.material.TCWoods;
import io.github.pouffy.tcompat.compat.aether.AetherMaterials;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

public class DeepAetherMaterials {

    public static final MaterialVariantId roseroot = aetherWoodVariant(TCWoods.ROSEROOT, 0xFFA37185, 0xFFB48090, 0xFFCC9CA5, 0xFFDFA7AF, 0xFFF4B6B8, 0xFFFDBDBE, 0xFFFED7D0);
    public static final MaterialVariantId yagroot = aetherWoodVariant(TCWoods.YAGROOT, 0xFF313140, 0xFF3C3B4A, 0xFF4B475B, 0xFF564D67, 0xFF615473, 0xFF67587A, 0xFF72637D);
    public static final MaterialVariantId cruderoot = aetherWoodVariant(TCWoods.CRUDEROOT, 0xFF4F5266, 0xFF5E6075, 0xFF71708E, 0xFF7E79A0, 0xFF8E84B3, 0xFF9689BD, 0xFFA99AC1);
    public static final MaterialVariantId conberry = aetherWoodVariant(TCWoods.CONBERRY, 0xFF544338, 0xFF5F4A3F, 0xFF705C4C, 0xFF826653, 0xFF99765D, 0xFFA68064, 0xFFAA886F);
    public static final MaterialVariantId sunroot = aetherWoodVariant(TCWoods.SUNROOT, 0xFF8E6B65, 0xFF9C7F77, 0xFFB39C8D, 0xFFC6AB98, 0xFFD9C2A3, 0xFFE2CCA9, 0xFFE9DABA);

    public static final MaterialVariantId aseterite = aetherRockVariant(TCRocks.ASETERITE, 0xFF746772, 0xFF7F717D, 0xFF897A87, 0xFF938595, 0xFFA296A1, 0xFFB9ADB8);
    public static final MaterialVariantId clorite = aetherRockVariant(TCRocks.CLORITE, 0xFF415964, 0xFF4D6A74, 0xFF5B7C88, 0xFF699AA1, 0xFF7DAFAE, 0xFF95C9CB);

    private static MaterialVariantId aetherWoodVariant(TCWoods woodType, int c63, int c102, int c140, int c178, int c216, int c234, int c255) {
        MaterialVariantId id = MaterialBuilder.variant(woodType.makeCondition(), woodType.getSerializedName(), AetherMaterials.aetherWood)
                .spriteInfo(s -> s.planks().sevenColor(c63, c102, c140, c178, c216, c234, c255))
                .buildVariant();
        TCMaterials.woodVariants.put(id, woodType);
        return id;
    }

    private static MaterialVariantId aetherRockVariant(TCRocks rockType, int c63, int c102, int c140, int c178, int c216, int c255) {
        MaterialVariantId id = MaterialBuilder.variant(rockType.makeCondition(), rockType.getSerializedName(), AetherMaterials.aetherRock)
                .spriteInfo(s -> s.planks().sixColor(c63, c102, c140, c178, c216, c255))
                .buildVariant();
        TCMaterials.rockVariants.put(id, rockType);
        return id;
    }

    public static void staticInit() {}
}
