package io.github.pouffy.tcompat.common.material;

import io.github.pouffy.tcompat.TCompat;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TCMaterials {
    public static final List<MaterialVariantId> allVariants = new ArrayList<>();

    public static final Map<MaterialVariantId, TCWoods> woodVariants = new HashMap<>();
    public static final Map<MaterialVariantId, TCRocks> rockVariants = new HashMap<>();

    public static final List<String> translationKeys = new ArrayList<>();

    // Metals & Gems
    public static final MaterialId aetherWood = id("aether_wood");
    public static final MaterialId aetherRock = id("aether_rock");
    public static final MaterialId zanite = id("zanite");
    public static final MaterialId skyjade = id("skyjade");
    public static final MaterialId gravitite = id("gravitite");
    public static final MaterialId veridium = id("veridium");
    public static final MaterialId refinedSentrite = id("refined_sentrite");
    public static final MaterialId pyral = id("pyral");
    public static final MaterialId valkyrum = id("valkyrum");
    public static final MaterialId neptune = id("neptune");
    public static final MaterialId lightnum = id("lightnum");
    public static final MaterialId stormforgedSteel = id("stormforged_steel");
    public static final MaterialId draculite = id("draculite");

    public static final MaterialId calorite = id("calorite");
    public static final MaterialId desh = id("desh");
    public static final MaterialId ostrum = id("ostrum");

    public static final MaterialId thallasium = id("thallasium");
    public static final MaterialId terminite = id("terminite");
    public static final MaterialId aeternium = id("aeternium");

    public static final MaterialId cincinnasite = id("cincinnasite");
    public static final MaterialId netherRuby = id("nether_ruby");

    public static final MaterialId fireDragonsteel = id("fire_dragonsteel");
    public static final MaterialId iceDragonsteel = id( "ice_dragonsteel");
    public static final MaterialId lightningDragonsteel = id( "lightning_dragonsteel");
    public static final MaterialId dragonBone = id("dragon_bone");
    public static final MaterialId dragonScaleFire = id("fire_dragon_scale");
    public static final MaterialId dragonScaleIce = id("ice_dragon_scale");
    public static final MaterialId dragonScaleLightning = id("lightning_dragon_scale");

    // Special Craftable Materials
    public static final MaterialId blightbunnyFang = id("blightbunny_fang");
    public static final MaterialId mykapodShell = id("mykapod_shell");
    public static final MaterialId wickedWax = id("wicked_wax");

    // Unique Woods
    public static final MaterialId hellbark = id("hellbark");

    // Implement Later (Maybe?)
    //public static final MaterialId brass = id("brass");
    //public static final MaterialId zinc = id("zinc");

    public static final MaterialVariantId
            dragonBronze = variant(dragonScaleFire, "bronze"),
            dragonGreen = variant(dragonScaleFire, "green"),
            dragonGray = variant(dragonScaleFire, "gray"),
            dragonRed = variant(dragonScaleFire, "red");
    public static final MaterialVariantId
            dragonBlue = variant(dragonScaleIce, "blue"),
            dragonSapphire = variant(dragonScaleIce, "sapphire"),
            dragonSilver = variant(dragonScaleIce, "silver"),
            dragonWhite = variant(dragonScaleIce, "white");
    public static final MaterialVariantId
            dragonAmethyst = variant(dragonScaleLightning, "amethyst"),
            dragonBlack = variant(dragonScaleLightning, "black"),
            dragonCopper = variant(dragonScaleLightning, "copper"),
            dragonElectric = variant(dragonScaleLightning, "electric");

    // Wood/Stone Variants
    public static final MaterialVariantId
        // Wood
            // Aether
            skyroot = aetherWoodVariant(TCWoods.SKYROOT),
            roseroot = aetherWoodVariant(TCWoods.ROSEROOT),
            yagroot = aetherWoodVariant(TCWoods.YAGROOT),
            cruderoot = aetherWoodVariant(TCWoods.CRUDEROOT),
            conberry = aetherWoodVariant(TCWoods.CONBERRY),
            sunroot = aetherWoodVariant(TCWoods.SUNROOT),
            blightwillow = aetherWoodVariant(TCWoods.BLIGHTWILLOW),
            fieldsproot = aetherWoodVariant(TCWoods.FIELDSPROOT),
            cloudcap = aetherWoodVariant(TCWoods.CLOUDCAP),
            jellyshroom = aetherWoodVariant(TCWoods.JELLYSHROOM),
            crystal = aetherWoodVariant(TCWoods.CRYSTAL),
            glacia = aetherWoodVariant(TCWoods.GLACIA),
            // Ad Astra
            aeronos = woodVariant(TCWoods.AERONOS),
            strophar = woodVariant(TCWoods.STROPHAR),
            glacian = woodVariant(TCWoods.GLACIAN),
            // OTBWG
            aspen = woodVariant(TCWoods.ASPEN),
            baobab = woodVariant(TCWoods.BAOBAB),
            blueEnchanted = woodVariant(TCWoods.BLUE_ENCHANTED),
            cika = woodVariant(TCWoods.CIKA),
            cypress = woodVariant(TCWoods.CYPRESS),
            ebony = woodVariant(TCWoods.EBONY),
            fir = woodVariant(TCWoods.FIR),
            florus = woodVariant(TCWoods.FLORUS),
            greenEnchanted = woodVariant(TCWoods.GREEN_ENCHANTED),
            holly = woodVariant(TCWoods.HOLLY),
            ironwood = woodVariant(TCWoods.IRONWOOD),
            jacaranda = woodVariant(TCWoods.JACARANDA),
            mahogany = woodVariant(TCWoods.MAHOGANY),
            maple = woodVariant(TCWoods.MAPLE),
            palm = woodVariant(TCWoods.PALM),
            paloVerde = woodVariant(TCWoods.PALO_VERDE),
            pine = woodVariant(TCWoods.PINE),
            rainbowEucalyptus = woodVariant(TCWoods.RAINBOW_EUCALYPTUS),
            redwood = woodVariant(TCWoods.REDWOOD),
            sakura = woodVariant(TCWoods.SAKURA),
            skyris = woodVariant(TCWoods.SKYRIS),
            whiteMangrove = woodVariant(TCWoods.WHITE_MANGROVE),
            willow = woodVariant(TCWoods.WILLOW),
            witchHazel = woodVariant(TCWoods.WITCH_HAZEL),
            zelkova = woodVariant(TCWoods.ZELKOVA),

            // Regions Unexplored
            alpha = woodVariant(TCWoods.ALPHA),
            blackwood = woodVariant(TCWoods.BLACKWOOD),
            blueBioshroom = woodVariant(TCWoods.BLUE_BIOSHROOM),
            brimwood = woodVariant(TCWoods.BRIMWOOD),
            cobalt = woodVariant(TCWoods.COBALT),
            eucalyptus = woodVariant(TCWoods.EUCALYPTUS),
            greenBioshroom = woodVariant(TCWoods.GREEN_BIOSHROOM),
            joshua = woodVariant(TCWoods.JOSHUA),
            kapok = woodVariant(TCWoods.KAPOK),
            larch = woodVariant(TCWoods.LARCH),
            magnolia = woodVariant(TCWoods.MAGNOLIA),
            mauve = woodVariant(TCWoods.MAUVE),
            pinkBioshroom = woodVariant(TCWoods.PINK_BIOSHROOM),
            socotra = woodVariant(TCWoods.SOCOTRA),
            yellowBioshroom = woodVariant(TCWoods.YELLOW_BIOSHROOM),
            // Quark
            ashen = woodVariant(TCWoods.ANCIENT),
            azalea = woodVariant(TCWoods.AZALEA),
            trumpet = woodVariant(TCWoods.BLOSSOM),
            //Betterend
            mossyGlowshroom = woodVariant(TCWoods.MOSSY_GLOWSHROOM),
            pythadendron = woodVariant(TCWoods.PYTHADENDRON),
            endLotus = woodVariant(TCWoods.END_LOTUS),
            lacugrove = woodVariant(TCWoods.LACUGROVE),
            dragonTree = woodVariant(TCWoods.DRAGON_TREE),
            tenanea = woodVariant(TCWoods.TENANEA),
            helixTree = woodVariant(TCWoods.HELIX_TREE),
            umbrellaTree = woodVariant(TCWoods.UMBRELLA_TREE),
            endJellyshroom = woodVariant(TCWoods.END_JELLYSHROOM),
            lucernia = woodVariant(TCWoods.LUCERNIA),
            //Betternether
            netherReed = woodVariant(TCWoods.NETHER_REED),
            stalagnate = woodVariant(TCWoods.STALAGNATE),
            netherWillow = woodVariant(TCWoods.NETHER_WILLOW),
            wart = woodVariant(TCWoods.WART),
            rubeus = woodVariant(TCWoods.RUBEUS),
            mushroomFir = woodVariant(TCWoods.MUSHROOM_FIR),
            netherMushroom = woodVariant(TCWoods.NETHER_MUSHROOM),
            anchorTree = woodVariant(TCWoods.ANCHOR_TREE),
            netherSakura = woodVariant(TCWoods.NETHER_SAKURA),

        // Rocks
            // Aether
            holystone = aetherRockVariant(TCRocks.HOLYSTONE),
            aseterite = aetherRockVariant(TCRocks.ASETERITE),
            clorite = aetherRockVariant(TCRocks.CLORITE),
            divinite = aetherRockVariant(TCRocks.DIVINITE),
            driftshale = aetherRockVariant(TCRocks.DRIFTSHALE),
            // Ad Astra
            moonStone = rockVariant(TCRocks.MOON_STONE),
            conglomerate = rockVariant(TCRocks.CONGLOMERATE),
            marsStone = rockVariant(TCRocks.MARS_STONE),
            venusStone = rockVariant(TCRocks.VENUS_STONE),
            mercuryStone = rockVariant(TCRocks.MERCURY_STONE),
            glacioStone = rockVariant(TCRocks.GLACIO_STONE),
            // OTBWG
            redRock = rockVariant(TCRocks.RED_ROCK),
            dacite = rockVariant(TCRocks.DACITE),
            // Regions Unexplored
            chalk = rockVariant(TCRocks.CHALK),
            // Quark
            limestone = rockVariant(TCRocks.LIMESTONE),
            jasper = rockVariant(TCRocks.JASPER),
            shale = rockVariant(TCRocks.SHALE),
            permafrost = rockVariant(TCRocks.PERMAFROST),
            //Betterend
            flavolite = endStoneVariant(TCRocks.FLAVOLITE),
            violecite = endStoneVariant(TCRocks.VIOLECITE),
            sulphuricRock = endStoneVariant(TCRocks.SULPHURIC_ROCK),
            viridJadestone = endStoneVariant(TCRocks.VIRID_JADESTONE),
            azureJadestone = endStoneVariant(TCRocks.AZURE_JADESTONE),
            sandyJadestone = endStoneVariant(TCRocks.SANDY_JADESTONE),
            umbralith = endStoneVariant(TCRocks.UMBRALITH),
            // Vanilla
            dripstone = rockVariant(TCRocks.DRIPSTONE)
            ;

    private static MaterialVariantId woodVariant(TCWoods woodType) {
        MaterialVariantId id = MaterialVariantId.create(MaterialIds.wood, woodType.getSerializedName());
        woodVariants.put(id, woodType);
        allVariants.add(id);
        translationKeys.add("material.%s.%s.%s".formatted("tconstruct", "wood", woodType.getSerializedName()));
        return id;
    }

    private static MaterialVariantId aetherWoodVariant(TCWoods woodType) {
        MaterialVariantId id = MaterialVariantId.create(aetherWood, woodType.getSerializedName());
        woodVariants.put(id, woodType);
        allVariants.add(id);
        translationKeys.add("material.%s.%s.%s".formatted(TCompat.MOD_ID, "aether_wood", woodType.getSerializedName()));
        return id;
    }

    private static MaterialVariantId rockVariant(TCRocks rockType) {
        MaterialVariantId id = MaterialVariantId.create(MaterialIds.rock, rockType.getSerializedName());
        rockVariants.put(id, rockType);
        allVariants.add(id);
        translationKeys.add("material.%s.%s.%s".formatted("tconstruct", "rock", rockType.getSerializedName()));
        return id;
    }

    private static MaterialVariantId aetherRockVariant(TCRocks rockType) {
        MaterialVariantId id = MaterialVariantId.create(aetherRock, rockType.getSerializedName());
        rockVariants.put(id, rockType);
        allVariants.add(id);
        translationKeys.add("material.%s.%s.%s".formatted(TCompat.MOD_ID, "aether_rock", rockType.getSerializedName()));
        return id;
    }

    private static MaterialVariantId endStoneVariant(TCRocks rockType) {
        MaterialVariantId id = MaterialVariantId.create(MaterialIds.whitestone, rockType.getSerializedName());
        rockVariants.put(id, rockType);
        allVariants.add(id);
        translationKeys.add("material.%s.%s.%s".formatted("tconstruct", "whitestone", rockType.getSerializedName()));
        return id;
    }

    private static MaterialVariantId variant(MaterialId parent, String name) {
        MaterialVariantId id = MaterialVariantId.create(parent, name);
        allVariants.add(id);
        translationKeys.add("material.%s.%s.%s".formatted(parent.getNamespace(), parent.getPath(), name));
        return id;
    }

    private static MaterialId id(String name) {
        translationKeys.add("material.%s.%s".formatted(TCompat.MOD_ID, name));
        return new MaterialId(TCompat.MOD_ID, name);
    }
}
