package io.github.pouffy.tcompat.compat.deep_aether;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.common.material.TCRocks;
import io.github.pouffy.tcompat.common.material.TCWoods;
import io.github.pouffy.tcompat.compat.CompatToolStats;
import io.github.pouffy.tcompat.compat.tinkersjewelry.PlainRingMaterialStats;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.tools.stats.PlatingMaterialStats;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

import static io.github.pouffy.tcompat.compat.aether.AetherMaterials.aetherRockVariant;
import static io.github.pouffy.tcompat.compat.aether.AetherMaterials.aetherWoodVariant;
import static net.minecraft.world.item.Tiers.IRON;
import static slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider.INGOT;

public class DeepAetherMaterials {

    public static final MaterialVariantId roseroot = aetherWoodVariant(TCWoods.ROSEROOT, 0xFFA37185, 0xFFB48090, 0xFFCC9CA5, 0xFFDFA7AF, 0xFFF4B6B8, 0xFFFDBDBE, 0xFFFED7D0);
    public static final MaterialVariantId yagroot = aetherWoodVariant(TCWoods.YAGROOT, 0xFF313140, 0xFF3C3B4A, 0xFF4B475B, 0xFF564D67, 0xFF615473, 0xFF67587A, 0xFF72637D);
    public static final MaterialVariantId cruderoot = aetherWoodVariant(TCWoods.CRUDEROOT, 0xFF4F5266, 0xFF5E6075, 0xFF71708E, 0xFF7E79A0, 0xFF8E84B3, 0xFF9689BD, 0xFFA99AC1);
    public static final MaterialVariantId conberry = aetherWoodVariant(TCWoods.CONBERRY, 0xFF544338, 0xFF5F4A3F, 0xFF705C4C, 0xFF826653, 0xFF99765D, 0xFFA68064, 0xFFAA886F);
    public static final MaterialVariantId sunroot = aetherWoodVariant(TCWoods.SUNROOT, 0xFF8E6B65, 0xFF9C7F77, 0xFFB39C8D, 0xFFC6AB98, 0xFFD9C2A3, 0xFFE2CCA9, 0xFFE9DABA);

    public static final MaterialVariantId aseterite = aetherRockVariant(TCRocks.ASETERITE, 0xFF746772, 0xFF7F717D, 0xFF897A87, 0xFF938595, 0xFFA296A1, 0xFFB9ADB8);
    public static final MaterialVariantId clorite = aetherRockVariant(TCRocks.CLORITE, 0xFF415964, 0xFF4D6A74, 0xFF5B7C88, 0xFF699AA1, 0xFF7DAFAE, 0xFF95C9CB);

    public static final MaterialId skyjade = MaterialBuilder.material("deep_aether", "skyjade")
            .data(d -> d.tier(2).order(25).deprecate())
            .traits(t -> t
                    .trait(TCModifiers.aetherForged, TCModifiers.skyjade).trait(MaterialRegistry.AMMO, ModifierIds.punch)
                    .trait(CompatToolStats.Statless.CUT_GEM.getIdentifier(), new ModifierEntry(TCModifiers.health_gem, 2))
            )
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(150, 10f, IRON, 2f),
                            StatlessMaterialStats.ARROW_HEAD
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(3).armor(3, 6, 8, 3), StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.Statless.CUT_GEM
                    )
            )
            .renderInfo(r -> r.color(0x729752).fallbacks("gem", "metal"))
            .spriteInfo(s -> s.plating(HeadMaterialStats.ID).statType(CompatToolStats.Statless.CUT_GEM.getIdentifier()).arrowHead().maille().fallbacks("gem", "metal").sevenColor(0xFF434e34, 0xFF4e6741, 0xFF729752, 0xFF8cb955, 0xFF9ada5b, 0xFFb2e865, 0xFFd1f397))
            .buildMaterial();

    public static final MaterialId stormforgedSteel = MaterialBuilder.material("deep_aether", "stormforged_steel")
            .data(d -> d.tier(2).order(2).craftable(false)).excludeFromLoot()
            .traits(t -> t.trait(TCModifiers.aetherForged).trait(HeadMaterialStats.ID, DeepAetherInit.gale))
            .stats(s ->
                    s.stat(new HeadMaterialStats(503, 8.0f, IRON, 3.0f)).statOptional(CompatToolStats.plainRing(503, 1.2f))
            )
            .renderInfo(r -> r.color(0xb8c5d1).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).meleeHarvest().statType(INGOT).sixColor(0xFF353a3f, 0xFF58606c, 0xFF9198a9, 0xFFb8c5d1, 0xFFd6e7f1, 0xFFdeffff))
            .buildMaterial();

    public static void staticInit() {}
}
