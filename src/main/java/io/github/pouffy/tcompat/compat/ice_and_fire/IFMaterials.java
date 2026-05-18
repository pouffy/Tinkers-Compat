package io.github.pouffy.tcompat.compat.ice_and_fire;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.compat.CompatToolStats;
import io.github.pouffy.tcompat.compat.constructs_casting.MagicBaseMaterialStats;
import io.github.pouffy.tcompat.compat.constructs_casting.MagicClothMaterialStats;
import io.github.pouffy.tcompat.compat.tinkersjewelry.PlainRingMaterialStats;
import io.github.pouffy.tcompat.datagen.lang.TCLangProv;
import slimeknights.tconstruct.library.client.data.spritetransformer.ISpriteTransformer;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.shared.command.subcommand.GeneratePartTexturesCommand;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.stats.*;

import java.util.Objects;

import static io.github.pouffy.tcompat.datagen.tinkers.material.TCMaterialSpriteProv.complexTransformer;
import static net.minecraft.world.item.Tiers.*;
import static slimeknights.tconstruct.library.materials.MaterialRegistry.ARMOR;

public class IFMaterials {

    public static final MaterialId dragonScaleFire = dragonScales("fire", 0xFF301511, 0xFF3e1d18, 0xFF532f2a, 0xFF82522e, 0xFF996944, 0xFFb78d6e);
    public static final MaterialId dragonScaleIce = dragonScales("ice", 0xFF002970, 0xFF06279e, 0xFF123ece, 0xFF246fe8, 0xFF5c99e8, 0xFF92d1e8);
    public static final MaterialId dragonScaleLightning = dragonScales("lightning", 0xFF090909, 0xFF151515, 0xFF292929, 0xFF2f2f2f, 0xFF484848, 0xFF636363);

    public static final MaterialId seaSerpentScale = MaterialBuilder.material("iceandfire", "sea_serpent_scale")
            .data(d -> d.tier(4).order(5).craftable(true))
            .traits(t -> t.trait(ARMOR, TCModifiers.tideGuardian).trait(StatlessMaterialStats.FLETCHING.getIdentifier(), ModifierIds.finsAmmo))
            .stats(s ->
                    s.stat(
                            StatlessMaterialStats.FLETCHING
                    ).armorStats(
                            PlatingMaterialStats.builder().durabilityFactor(30).armor(4, 8, 7, 4).toughness(2.5F), StatlessMaterialStats.MAILLE
                    )
            )
            .renderInfo(r -> r.color(0x228eda).fallbacks("scales", "metal"))
            .spriteInfo(s -> s.fallbacks("scales", "metal").fletching().repairKit().armor().sixColor(0xFF053755, 0xFF0f5a80, 0xFF1e74b5, 0xFF228eda, 0xFF2aa6fd, 0xFF39cfff))
            .buildMaterial();

    public static final MaterialId trollLeather = MaterialBuilder.material("iceandfire", "troll_leather")
            .data(d -> d.tier(2).order(4).craftable(true))
            .traits(t -> t
                    .trait(TCModifiers.petrifying)
                    .trait(StatlessMaterialStats.MAILLE.getIdentifier(), ModifierIds.projectileProtection)
                    .trait(MagicClothMaterialStats.ID, TCModifiers.conserving)
            )
            .stats(s -> s
                    .stat(StatlessMaterialStats.MAILLE, StatlessMaterialStats.BINDING)
                    .statOptional(CompatToolStats.magicCloth(8, -0.05f))
            )
            .renderInfo(r -> r.color(0x50606b).fallbacks("cloth"))
            .spriteInfo(s -> s.maille().binding().statType(MagicClothMaterialStats.ID).fallbacks("cloth").sixColor(0xFF0f1717, 0xFF1b2529, 0xFF3b474d, 0xFF485961, 0xFF50606b, 0xFF66737a))
            .buildMaterial();

    public static final MaterialId myrmexChitin = MaterialBuilder.material("iceandfire", "myrmex_chitin")
            .data(d -> d.tier(3).order(4).craftable(true))
            .traits(t -> t.trait(TCModifiers.allythropod))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(600, 6.0F, DIAMOND, 1.0F), StatlessMaterialStats.BINDING
                    ).armorStats(
                            PlatingMaterialStats.builder().durabilityFactor(20).armor(3, 5, 8, 4)
                    )
            )
            .renderInfo(r -> r.color(0x904a10).fallbacks("chitin", "wood"))
            .spriteInfo(s -> s.plating().head().binding().fallbacks("chitin", "wood").sevenColor(0xFF2c322a, 0xFF4c3f06, 0xFF663f27, 0xFF724f06, 0xFF904a10, 0xFFac6111, 0xFFd06e33))
            .buildMaterial();

    public static final MaterialId deathwormChitin = MaterialBuilder.material("iceandfire", "deathworm_chitin")
            .lang("Death Worm Chitin")
            .data(d -> d.tier(3).order(4).craftable(true))
            .stats(s -> s.armorStats(PlatingMaterialStats.builder().durabilityFactor(15).armor(2, 5, 7, 3).toughness(1.5F)))
            .renderInfo(r -> r.color(0xd5c3b8).fallbacks("chitin", "wood"))
            .spriteInfo(s -> s.plating().fallbacks("chitin", "wood").sevenColor(0xFF6d5f54, 0xFF89746c, 0xFFb39a8f, 0xFFb1ae97, 0xFFd5c3b8, 0xFFe5dec9, 0xFFfdf7e4))
            .buildMaterial();

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
    public static final MaterialVariantId
            serpentBlue = seaSerpentScaleVariant("blue",          0xFF053755, 0xFF0f5a80, 0xFF1e74b5, 0xFF228eda, 0xFF2aa6fd, 0xFF39cfff),
            serpentBronze = seaSerpentScaleVariant("bronze",      0xFF4d2c02, 0xFF76430b, 0xFFa16911, 0xFFb87219, 0xFFd57c26, 0xFFffa05a),
            serpentDeepBlue = seaSerpentScaleVariant("deep_blue", 0xFF031d4f, 0xFF0f3a80, 0xFF1e49b5, 0xFF2459de, 0xFF2a6afd, 0xFF3997ff),
            serpentGreen = seaSerpentScaleVariant("green",        0xFF002d1e, 0xFF025d3e, 0xFF05874b, 0xFF089655, 0xFF0ea761, 0xFF0dd65d),
            serpentPurple = seaSerpentScaleVariant("purple",      0xFF190354, 0xFF361990, 0xFF501eb5, 0xFF5a1fdc, 0xFF6824fd, 0xFF8b45ff),
            serpentRed = seaSerpentScaleVariant("red",            0xFF5d0922, 0xFF871425, 0xFFab1a24, 0xFFd71e31, 0xFFf7243a, 0xFFfb6149),
            serpentTeal = seaSerpentScaleVariant("teal",          0xFF004544, 0xFF06857f, 0xFF08aa8e, 0xFF03c6ba, 0xFF03d4db, 0xFF72ffdc);
    public static final MaterialVariantId
            trollFrost = trollLeatherVariant("frost",       0xFF0f1717, 0xFF1b2529, 0xFF3b474d, 0xFF485961, 0xFF50606b, 0xFF66737a),
            trollMountain = trollLeatherVariant("mountain", 0xFF0e0d12, 0xFF1b1c1f, 0xFF3b3f45, 0xFF4e5359, 0xFF60636b, 0xFF6d717a),
            trollForest = trollLeatherVariant("forest",     0xFF1b1f1b, 0xFF272e2e, 0xFF343d3d, 0xFF49574e, 0xFF5a6961, 0xFF6a7a73);
    public static final MaterialVariantId
            myrmexDesert = myrmexChitinVariant("desert", 0xFF2c322a, 0xFF4c3f06, 0xFF663f27, 0xFF724f06, 0xFF904a10, 0xFFac6111, 0xFFd06e33),
            myrmexJungle = myrmexChitinVariant("jungle", 0xFF283a47, 0xFF2e4f57, 0xFF1d6c57, 0xFF1b7f7c, 0xFF369281, 0xFF3da09d, 0xFF1bbaa8);
    public static final MaterialVariantId
            deathwormWhite = deathwormChitinVariant("white", "White",       0xFF6d5f54, 0xFF89746c, 0xFFb39a8f, 0xFFb1ae97, 0xFFd5c3b8, 0xFFe5dec9, 0xFFfdf7e4),
            deathwormRed = deathwormChitinVariant("red", "Red",             0xFF4a1920, 0xFF582020, 0xFF6f1d1d, 0xFF892f2f, 0xFFa1453d, 0xFFae523d, 0xFFc96539),
            deathwormYellow = deathwormChitinVariant("yellow", "Tan",       0xFF63462e, 0xFF7d5a4c, 0xFFb68773, 0xFFc2a58e, 0xFFdec0af, 0xFFe5dbbb, 0xFFffefc3);

    public static final MaterialId dragonBone = MaterialBuilder.material("iceandfire", "dragon_bone")
            .data(d -> d.tier(4).order(5).craftable(true))
            .traits(t -> t
                    .trait(TinkerModifiers.firestarter)
                    .trait(StatlessMaterialStats.ARROW_SHAFT.getIdentifier(), ModifierIds.fiery)
                    .trait(CompatToolStats.Statless.CUT_GEM.getIdentifier(), new ModifierEntry(TCModifiers.flame_gem, 1))
                    .trait(MagicBaseMaterialStats.ID, TCModifiers.puncturing)
            )
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(200, 2.5f, IRON, 1.2f),
                            HandleMaterialStats.multipliers().durability(1.05f).attackSpeed(1.25f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(200, 0.07f, -0.07f, 0.07f),
                            new GripMaterialStats(1.0f, 0.15f, 1.5f), StatlessMaterialStats.ARROW_SHAFT
                    ).statOptional(
                            CompatToolStats.Statless.CUT_GEM,
                            CompatToolStats.magicBase(90, -0.05f)
                    )
            )
            .renderInfo(r -> r.color(0xc7b899).fallbacks("bone", "rock"))
            .spriteInfo(s -> s.meleeHarvest().statType(CompatToolStats.Statless.CUT_GEM.getIdentifier()).statType(MagicBaseMaterialStats.ID).arrowShaft().ranged().fallbacks("bone", "rock").sixColor(0xFF52513f, 0xFF646050, 0xFF7d7866, 0xFF9b9883, 0xFFc7b899, 0xFFdfceb7))
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

    public static final MaterialId seaSerpentFang = MaterialBuilder.material("iceandfire", "sea_serpent_fang")
            .data(d -> d.tier(2).order(4).craftable(true))
            .traits(t -> t.trait(TCModifiers.aquaShot))
            .stats(s -> s.stat(StatlessMaterialStats.ARROW_HEAD))
            .renderInfo(r -> r.color(0xdbccb5).fallbacks("tooth", "bone"))
            .spriteInfo(s -> s.arrowHead().fallbacks("tooth", "bone").sixColor(0xFF4b414c, 0xFF665d5a, 0xFF91817a, 0xFFb7a796, 0xFFdbccb5, 0xFFffe7c4))
            .buildMaterial();

    public static final MaterialId hydraFang = MaterialBuilder.material("iceandfire", "hydra_fang")
            .data(d -> d.tier(2).order(4).craftable(true))
            .traits(t -> t.trait(IFInit.leeching))
            .stats(s -> s.stat(StatlessMaterialStats.ARROW_HEAD))
            .renderInfo(r -> r.color(0xb2bea0).fallbacks("hydra", "bone"))
            .spriteInfo(s -> s.arrowHead().fallbacks("hydra", "bone").sixColor(0xFF4f424b, 0xFF5f5d52, 0xFF81847c, 0xFF989a87, 0xFFb2bea0, 0xFFd6decb))
            .buildMaterial();

    public static final MaterialId amphithereFeather = MaterialBuilder.material("iceandfire", "amphithere_feather")
            .data(d -> d.tier(2).order(4).craftable(true))
            .traits(t -> t.trait(IFInit.amphitheric))
            .stats(s -> s.stat(StatlessMaterialStats.FLETCHING))
            .renderInfo(r -> r.color(0x1a8b80).fallbacks("amphithere"))
            .spriteInfo(s -> s.fletching().fallbacks("amphithere").sixColor(0xFF00361f, 0xFF105a37, 0xFF197956, 0xFF0d7f68, 0xFF1a8b80, 0xFF1c9b86))
            .buildMaterial();

    public static final MaterialId stymphalianBirdFeather = MaterialBuilder.material("iceandfire", "stymphalian_bird_feather")
            .data(d -> d.tier(2).order(4).craftable(true))
            .traits(t -> t.trait(IFInit.stymphalian))
            .stats(s -> s.stat(StatlessMaterialStats.FLETCHING))
            .renderInfo(r -> r.color(0x896a55).fallbacks("stymphalian"))
            .spriteInfo(s -> s.fletching().fallbacks("stymphalian").sixColor(0xFF1f160f, 0xFF3d2b1e, 0xFF513a2a, 0xFF68503f, 0xFF896a55, 0xFFa08776))
            .buildMaterial();

    private static MaterialId dragonScales(String type, int c63, int c102, int c140, int c178, int c216, int c255) {
        var builder = MaterialBuilder.material("iceandfire", type+"_dragon_scale")
                .data(d -> d.tier(4).order(5).craftable(true))
                .traits(t -> {
                    t.trait(ARMOR, IFInit.breathless);
                    if (Objects.equals(type, "fire"))
                        t.trait(MagicBaseMaterialStats.ID, TCModifiers.fireUpgrade);
                    else if (Objects.equals(type, "ice"))
                        t.trait(MagicBaseMaterialStats.ID, TCModifiers.iceUpgrade);
                    else if (Objects.equals(type, "lightning"))
                        t.trait(MagicBaseMaterialStats.ID, TCModifiers.lightningUpgrade);
                    return t;
                })
                .stats(s ->
                        s.armorStats(
                                PlatingMaterialStats.builder().durabilityFactor(36).armor(5, 7, 9, 5).toughness(2.0f), StatlessMaterialStats.MAILLE
                        ).statOptional(
                                CompatToolStats.magicBase(150, 0.15f)
                        )
                )
                .renderInfo(r -> r.color(c178).fallbacks("scales", "metal"))
                .spriteInfo(s -> s.fallbacks("scales", "metal").repairKit().armor().statType(MagicBaseMaterialStats.ID).sixColor(c63, c102, c140, c178, c216, c255));
        return builder.buildMaterial();
    }

    private static MaterialVariantId deathwormChitinVariant(String type, String name, int c63, int c102, int c140, int c178, int c216, int c234, int c255) {
        var builder = MaterialBuilder.variant("iceandfire", type, deathwormChitin)
                .lang(name + " Death Worm Chitin")
                .renderInfo(r -> r.color(c178).fallbacks("chitin", "wood"))
                .spriteInfo(s -> s.repairKit().plating().fallbacks("chitin", "wood").sevenColor(c63, c102, c140, c178, c216, c234, c255));
        return builder.buildVariant();
    }

    private static MaterialVariantId myrmexChitinVariant(String type, int c63, int c102, int c140, int c178, int c216, int c234, int c255) {
        var builder = MaterialBuilder.variant("iceandfire", type, myrmexChitin)
                .lang(TCLangProv.toEngStr(type) + " Myrmex Chitin")
                .renderInfo(r -> r.color(c178).fallbacks("chitin", "wood"))
                .spriteInfo(s -> s.repairKit().binding().head().plating().fallbacks("chitin", "wood").sevenColor(c63, c102, c140, c178, c216, c234, c255));
        return builder.buildVariant();
    }

    private static MaterialVariantId trollLeatherVariant(String type, int c63, int c102, int c140, int c178, int c216, int c255) {
        var builder = MaterialBuilder.variant("iceandfire", type, trollLeather)
                .lang(TCLangProv.toEngStr(type) + " Troll Leather")
                .renderInfo(r -> r.color(c178).fallbacks("cloth"))
                .spriteInfo(s -> s.repairKit().binding().maille().fallbacks("cloth").sixColor(c63, c102, c140, c178, c216, c255));
        return builder.buildVariant();
    }

    private static MaterialVariantId seaSerpentScaleVariant(String type, int c63, int c102, int c140, int c178, int c216, int c255) {
        var builder = MaterialBuilder.variant("iceandfire", type, seaSerpentScale)
                .lang(TCLangProv.toEngStr(type) + " Sea Serpent Scale")
                .renderInfo(r -> r.color(c178).fallbacks("scales", "metal"))
                .spriteInfo(s -> s.repairKit().fletching().armor().fallbacks("scales", "metal").sixColor(c63, c102, c140, c178, c216, c255));
        return builder.buildVariant();
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

    private static MaterialVariantId scalesVariant(String type, String suffix, MaterialId parent, int c63, int c102, int c140, int c178, int c216, int c255) {
        var builder = MaterialBuilder.variant("iceandfire", type, parent)
                .lang(TCLangProv.toEngStr(type) + " " + suffix)
                .renderInfo(r -> r.color(c178).fallbacks("scales", "metal"))
                .spriteInfo(s -> s.repairKit().armor().fallbacks("scales", "metal").sixColor(c63, c102, c140, c178, c216, c255));
        return builder.buildVariant();
    }

    private static MaterialVariantId scalesVariant(String type, String suffix, MaterialId parent, ISpriteTransformer spriteTransformer) {
        var builder = MaterialBuilder.variant("iceandfire", type, parent)
                .lang(TCLangProv.toEngStr(type) + " " + suffix)
                .renderInfo(r -> r.fallbacks("scales", "metal"))
                .spriteInfo(s -> s.repairKit().armor().fallbacks("scales", "metal").transformer(spriteTransformer));
        return builder.buildVariant();
    }

    public static void staticInit() {}
}
