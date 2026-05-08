package io.github.pouffy.tcompat.compat.aether;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.material.*;
import io.github.pouffy.tcompat.compat.ad_astra.AdAstraInit;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.stats.*;

import static net.minecraft.world.item.Tiers.*;

public class AetherMaterials {

    public static final MaterialId aetherWood = MaterialBuilder.material("aether", "aether_wood")
            .data(d -> d.tier(1).order(1).craftable(true))
            .traits(t -> t.trait(TCModifiers.aetherForged, ModifierIds.cultivated).trait(MaterialRegistry.AMMO, ModifierIds.economical))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(60, 2f, WOOD, 0f),
                            HandleMaterialStats.percents().build(), // flat all around
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(60, 0, 0, 0),
                            new GripMaterialStats(0f, 0, 0),
                            StatlessMaterialStats.ARROW_SHAFT,
                            StatlessMaterialStats.SHIELD_CORE
                    )
            )
            .renderInfo(r -> r.color(0x5C5B41).fallbacks("wood", "stick", "primitive"))
            .spriteInfo(s -> s.meleeHarvest().ranged().shieldCore().arrowShaft().fallbacks("wood", "stick", "primitive").sixColor(0xFF333122, 0xFF403D2A, 0xFF4F4D36, 0xFF5C5B41, 0xFF63634A, 0xFF7B7E61))
            .buildMaterial();

    public static final MaterialId aetherRock = MaterialBuilder.material("aether", "aether_rock")
            .data(d -> d.tier(1).order(1).craftable(true))
            .traits(t -> t.trait(TCModifiers.aetherForged, AetherInit.ambrogen, ModifierIds.stonebound))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(130, 4f, STONE, 1f),
                            HandleMaterialStats.multipliers().durability(0.9f).miningSpeed(1.05f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(60, 0, 0, 0),
                            new GripMaterialStats(0f, 0, 0),
                            StatlessMaterialStats.ARROW_SHAFT,
                            StatlessMaterialStats.SHIELD_CORE
                    )
            )
            .renderInfo(r -> r.parent(TCompat.getResource("aether_rock/holystone")))
            .buildMaterial();

    public static final MaterialId zanite = MaterialBuilder.material("aether", "zanite")
            .data(d -> d.tier(2).order(1).deprecate())
            .traits(t -> t.trait(TCModifiers.aetherForged, AetherInit.zanite).trait(MaterialRegistry.AMMO, ModifierIds.crystalbound))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(251, 6f, IRON, 2f),
                            StatlessMaterialStats.ARROW_HEAD,
                            StatlessMaterialStats.FLETCHING
                    ).armorShieldStats(PlatingMaterialStats.builder().durabilityFactor(15).armor(2, 5, 6, 2), StatlessMaterialStats.MAILLE)
            )
            .renderInfo(r -> r.color(0x5b22b0).fallbacks("gem"))
            .spriteInfo(s -> s.plating(HeadMaterialStats.ID).fletching().arrowHead().fallbacks("gem", "metal").sevenColor(0xFF1c0d3b, 0xFF350f6c, 0xFF5b22b0, 0xFF8a4ee4, 0xFFaf7ff6, 0xFFbf9cf4, 0xFFe1cdff))
            .buildMaterial();

    public static final MaterialId gravitite = MaterialBuilder.material("aether", "gravitite")
            .data(d -> d.tier(3).order(1).craftable(false))
            .traits(t -> t.trait(TCModifiers.aetherForged).trait(HeadMaterialStats.ID, AetherInit.ascension, TCModifiers.aetherForged))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(1562, 3f, DIAMOND, 3f),
                            HandleMaterialStats.multipliers().durability(0.95f).miningSpeed(1.12f).build(),
                            StatlessMaterialStats.BINDING
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(33).armor(3, 6, 8, 3).toughness(2f),
                            StatlessMaterialStats.MAILLE))
            .renderInfo(r -> r.color(0xb643a0).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().armor().meleeHarvest().ranged().sixColor(0xFF390c35, 0xFF5c1256, 0xFFb643a0, 0xFFda67d0, 0xFFf195ef, 0xFFffcbfd))
            .buildMaterial();

    public static final MaterialVariantId skyroot = aetherWoodVariant(TCWoods.SKYROOT, 0xFF333122, 0xFF403D2A, 0xFF4F4D36, 0xFF5C5B41, 0xFF63634A, 0xFF737558, 0xFF7B7E61);
    public static final MaterialVariantId holystone = aetherRockVariant(TCRocks.HOLYSTONE, 0xFF808080, 0xFF8F8F8F, 0xFF9C9C9C, 0xFFADADAD, 0xFFCCCCCC, 0xFFE2E2E2);

    private static MaterialVariantId aetherWoodVariant(TCWoods woodType, int c63, int c102, int c140, int c178, int c216, int c234, int c255) {
        MaterialVariantId id = MaterialBuilder.variant(woodType.makeCondition(), woodType.getSerializedName(), aetherWood)
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
