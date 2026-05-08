package io.github.pouffy.tcompat.compat.aether_redux;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCMaterials;
import io.github.pouffy.tcompat.common.material.TCRocks;
import io.github.pouffy.tcompat.common.material.TCWoods;
import io.github.pouffy.tcompat.compat.aether.AetherMaterials;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

public class AetherReduxMaterials {

    public static final MaterialVariantId cloudcap = aetherWoodVariant(TCWoods.CLOUDCAP, 0xFF191A2B, 0xFF212338, 0xFF292C45, 0xFF323654, 0xFF3B3F60, 0xFF444A72, 0xFF4B537D);
    public static final MaterialVariantId jellyshroom = aetherWoodVariant(TCWoods.JELLYSHROOM, 0xFF373E4F, 0xFF414959, 0xFF4B5364, 0xFF555D6B, 0xFF646B76, 0xFF737982, 0xFF81868E);
    public static final MaterialVariantId blightwillow = aetherWoodVariant(TCWoods.BLIGHTWILLOW, 0xFF4B694E, 0xFF5E805C, 0xFF6C8C63, 0xFF7F9F74, 0xFF8FAC82, 0xFF9FBA90, 0xFFA8C198);
    public static final MaterialVariantId fieldsproot = aetherWoodVariant(TCWoods.FIELDSPROOT, 0xFF824241, 0xFF9A514E, 0xFFAF615B, 0xFFBF7069, 0xFFC97B72, 0xFFD4847B, 0xFFDB8B82);
    public static final MaterialVariantId crystal = aetherWoodVariant(TCWoods.CRYSTAL, 0xFF50607A, 0xFF5F728E, 0xFF6D84A3, 0xFF788FAB, 0xFF839CBA, 0xFF8DA6C1, 0xFF95AFC6);
    public static final MaterialVariantId glacia = aetherWoodVariant(TCWoods.GLACIA, 0xFF524D45, 0xFF645F54, 0xFF787365, 0xFF848072, 0xFF938F7E, 0xFF9E9A89, 0xFFA7A18F);

    public static final MaterialVariantId divinite = aetherRockVariant(TCRocks.DIVINITE, 0xFF8B7149, 0xFF987E55, 0xFFAA8F65, 0xFFBAA170, 0xFFCDB285, 0xFFD8C699);
    public static final MaterialVariantId driftshale = aetherRockVariant(TCRocks.DRIFTSHALE, 0xFFAA9A5D, 0xFFBEB06C, 0xFFCEC277, 0xFFD9CE81, 0xFFDFDA95, 0xFFE3E2A8);

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
