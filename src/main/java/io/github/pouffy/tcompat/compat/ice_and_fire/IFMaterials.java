package io.github.pouffy.tcompat.compat.ice_and_fire;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.compat.CompatToolStats;
import io.github.pouffy.tcompat.compat.tinkersjewelry.PlainRingMaterialStats;
import io.github.pouffy.tcompat.datagen.lang.TCLangProv;
import slimeknights.tconstruct.library.client.data.spritetransformer.ISpriteTransformer;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.stats.*;

import static io.github.pouffy.tcompat.datagen.tinkers.material.TCMaterialSpriteProv.complexTransformer;
import static net.minecraft.world.item.Tiers.IRON;
import static net.minecraft.world.item.Tiers.NETHERITE;
import static slimeknights.tconstruct.library.materials.MaterialRegistry.ARMOR;

public class IFMaterials {

    public static final MaterialId dragonScaleFire = dragonScales("fire", 0xFF301511, 0xFF3e1d18, 0xFF532f2a, 0xFF82522e, 0xFF996944, 0xFFb78d6e);
    public static final MaterialId dragonScaleIce = dragonScales("ice", 0xFF002970, 0xFF06279e, 0xFF123ece, 0xFF246fe8, 0xFF5c99e8, 0xFF92d1e8);
    public static final MaterialId dragonScaleLightning = dragonScales("lightning", 0xFF090909, 0xFF151515, 0xFF292929, 0xFF2f2f2f, 0xFF484848, 0xFF636363);

    public static final MaterialVariantId
            dragonBronze = dragonScalesVariant("bronze", dragonScaleFire,           0xFF301511, 0xFF3e1d18, 0xFF532f2a, 0xFF82522e, 0xFF996944, 0xFFb78d6e),
            dragonGreen = dragonScalesVariant("green", dragonScaleFire,             0xFF1f1d1c, 0xFF222c1a, 0xFF2d3526, 0xFF4d7c3f, 0xFF5a914a, 0xFF5caa5e),
            dragonGray = dragonScalesVariant("gray", dragonScaleFire,               0xFF202121, 0xFF383838, 0xFF454545, 0xFF5d5356, 0xFF686162, 0xFF787474),
            dragonRed = dragonScalesVariant("red", dragonScaleFire,                 0xFF280d19, 0xFF391127, 0xFF4e2039, 0xFF891e3d, 0xFFb23743, 0xFFc94a57);
    public static final MaterialVariantId
            dragonBlue = dragonScalesVariant("blue", dragonScaleIce,                0xFF2f6584, 0xFF3b7ea5, 0xFF4ba1c6, 0xFF7cdeef, 0xFFa9f1fc, 0xFFe2fffd),
            dragonSapphire = dragonScalesVariant("sapphire", dragonScaleIce,        0xFF002970, 0xFF06279e, 0xFF123ece, 0xFF246fe8, 0xFF5c99e8, 0xFF92d1e8),
            dragonSilver = dragonScalesVariant("silver", dragonScaleIce,            0xFF4c596d, 0xFF5e6b7a, 0xFF7e8fa0, 0xFFa8b9c1, 0xFFccd5dd, 0xFFeaf3ff),
            dragonWhite = dragonScalesVariant("white", dragonScaleIce,              0xFF797e7f, 0xFF949b9d, 0xFFa4b2b5, 0xFFc6dbdd, 0xFFe0eaee, 0xFFf6f9fa);
    public static final MaterialVariantId
            dragonAmethyst = dragonScalesVariant("amethyst", dragonScaleLightning,  0xFF1f1628, 0xFF251e33, 0xFF2b2247, 0xFF4f346d, 0xFF7a3991, 0xFFa16097),
            dragonBlack = dragonScalesVariant("black", dragonScaleLightning,        0xFF090909, 0xFF151515, 0xFF292929, 0xFF2f2f2f, 0xFF484848, 0xFF636363),
            dragonCopper = dragonScalesVariant("copper", dragonScaleLightning,      0xFF351923, 0xFF481d28, 0xFF572a23, 0xFF79402c, 0xFFa05e2f, 0xFFd3824c),
            dragonElectric = dragonScalesVariant("electric", dragonScaleLightning,  0xFF14143d, 0xFF1a1e51, 0xFF292989, 0xFF4643e8, 0xFF7068e8, 0xFF9691ff);

    public static final MaterialId dragonBone = MaterialBuilder.material("iceandfire", "dragon_bone")
            .data(d -> d.tier(4).order(5).craftable(true))
            .traits(t -> t.trait(TinkerModifiers.firestarter))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(200, 2.5f, IRON, 1.2f),
                            HandleMaterialStats.multipliers().durability(1.05f).attackSpeed(1.25f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(200, 0.07f, -0.07f, 0.07f),
                            new GripMaterialStats(1.0f, 0.15f, 1.5f)
                    ).statOptional(
                            CompatToolStats.Statless.CUT_GEM
                    )
            )
            .renderInfo(r -> r.color(0xc7b899).fallbacks("bone", "rock"))
            .spriteInfo(s -> s.meleeHarvest().statType(CompatToolStats.Statless.CUT_GEM.getIdentifier()).ranged().fallbacks("bone", "rock").sixColor(0xFF52513f, 0xFF646050, 0xFF7d7866, 0xFF9b9883, 0xFFc7b899, 0xFFdfceb7))
            .buildMaterial();

    public static final MaterialId fireDragonsteel = MaterialBuilder.material("iceandfire", "fire_dragonsteel")
            .data(d -> d.tier(4).order(1).craftable(false))
            .traits(t -> t.trait(TCModifiers.scorchborn))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(1400, 8.0f, NETHERITE, 5.0f),
                            HandleMaterialStats.multipliers().durability(1.2f).miningSpeed(0.85f).attackSpeed(0.85f).attackDamage(1.3f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(1350, 0.5f, 0.35f, 0.3f),
                            new GripMaterialStats(2.0f, 0.35f, 5.3f)
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(84).armor(6, 9, 12, 7).toughness(6),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.plainRing(1400, 1.3f)
                    )
            )
            .renderInfo(r -> r.color(0x774153).fallbacks("bone", "metal").luminosity(9))
            .spriteInfo(s -> s.fallbacks("bone", "metal").repairKit().statType(PlainRingMaterialStats.ID).meleeHarvest().ranged().armor().transformer(complexTransformer("fire_dragonsteel", 1, 0xFFBD9FA7, 0xFFEAD0D0)))
            .buildMaterial();

    public static final MaterialId iceDragonsteel = MaterialBuilder.material("iceandfire", "ice_dragonsteel")
            .data(d -> d.tier(4).order(1).craftable(false))
            .traits(t -> t.trait(TCModifiers.frostborn))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(1250, 11.0f, NETHERITE, 5.3f),
                            HandleMaterialStats.multipliers().durability(1.15f).miningSpeed(0.88f).attackSpeed(0.88f).attackDamage(1.25f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(1300, 0.7f, 0.4f, 0.45f),
                            new GripMaterialStats(1.3f, 0.2f, 4f)
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(81).armor(6, 9, 12, 7).toughness(6),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.plainRing(1250, 1.4f)
                    )
            )
            .renderInfo(r -> r.color(0xBAEAEC).fallbacks("bone", "metal").luminosity(9))
            .spriteInfo(s -> s.fallbacks("bone", "metal").repairKit().statType(PlainRingMaterialStats.ID).meleeHarvest().ranged().armor().transformer(complexTransformer("ice_dragonsteel", 1, 0xFFBAEAEC, 0xFFFFFFFF)))
            .buildMaterial();

    public static final MaterialId lightningDragonsteel = MaterialBuilder.material("iceandfire", "lightning_dragonsteel")
            .data(d -> d.tier(4).order(1).craftable(false))
            .traits(t -> t.trait(TCModifiers.voltborn))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(1600, 7.0f, NETHERITE, 5.8f),
                            HandleMaterialStats.multipliers().durability(1.05f).miningSpeed(0.78f).attackSpeed(0.78f).attackDamage(1.25f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(1100, 0.4f, 0.25f, 0.35f),
                            new GripMaterialStats(1.6f, 0.3f, 4.6f)
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(68).armor(6, 9, 12, 7).toughness(6),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.plainRing(1600, 1.35f)
                    )
            )
            .renderInfo(r -> r.color(0x917DD2).fallbacks("bone", "metal").luminosity(9))
            .spriteInfo(s -> s.fallbacks("bone", "metal").repairKit().statType(PlainRingMaterialStats.ID).meleeHarvest().ranged().armor().transformer(complexTransformer("lightning_dragonsteel", 1, 0xFFCAA4DA, 0xFFE5CBF7)))
            .buildMaterial();

    private static MaterialId dragonScales(String type, int c63, int c102, int c140, int c178, int c216, int c255) {
        var builder = MaterialBuilder.material("iceandfire", type+"_dragon_scale")
                .data(d -> d.tier(4).order(5).craftable(true)).traits(t -> t.trait(ARMOR, IFInit.breathless))
                .stats(s ->
                        s.armorStats(PlatingMaterialStats.builder().durabilityFactor(36).armor(5, 7, 9, 5).toughness(2.0f), StatlessMaterialStats.MAILLE)
                )
                .renderInfo(r -> r.color(c178).fallbacks("scales", "metal"))
                .spriteInfo(s -> s.fallbacks("scales", "metal").repairKit().armor().sixColor(c63, c102, c140, c178, c216, c255));
        return builder.buildMaterial();
    }

    private static MaterialVariantId dragonScalesVariant(String type, MaterialId parent, int c63, int c102, int c140, int c178, int c216, int c255) {
        var builder = MaterialBuilder.variant("iceandfire", type, parent)
                .lang(TCLangProv.toEngStr(type) + " Dragon Scale")
                .renderInfo(r -> r.color(c178).fallbacks("scales", "metal"))
                .spriteInfo(s -> s.repairKit().armor().fallbacks("scales", "metal").sixColor(c63, c102, c140, c178, c216, c255));
        return builder.buildVariant();
    }

    private static MaterialVariantId dragonScalesVariant(String type, MaterialId parent, ISpriteTransformer spriteTransformer) {
        var builder = MaterialBuilder.variant("iceandfire", type, parent)
                .lang(TCLangProv.toEngStr(type) + " Dragon Scale")
                .renderInfo(r -> r.fallbacks("scales", "metal"))
                .spriteInfo(s -> s.repairKit().armor().fallbacks("scales", "metal").transformer(spriteTransformer));
        return builder.buildVariant();
    }

    public static void staticInit() {}
}
