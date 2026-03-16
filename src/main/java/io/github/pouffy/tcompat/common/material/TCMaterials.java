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

    public static final MaterialId calorite = id("calorite");
    public static final MaterialId desh = id("desh");
    public static final MaterialId ostrum = id("ostrum");
    // Special Craftable Materials
    public static final MaterialId blightbunnyFang = id("blightbunny_fang");
    public static final MaterialId mykapodShell = id("mykapod_shell");
    public static final MaterialId wickedWax = id("wicked_wax");

    // Unique Woods
    public static final MaterialId hellbark = id("hellbark");

    // Implement Later (Maybe?)
    public static final MaterialId brass = id("brass");
    public static final MaterialId zinc = id("zinc");

    // Variants
    public static final MaterialVariantId
        // Wood
            // Aether
            skyroot = aetherWoodVariant("skyroot", TCWoods.SKYROOT),
            roseroot = aetherWoodVariant("roseroot", TCWoods.ROSEROOT),
            yagroot = aetherWoodVariant("yagroot", TCWoods.YAGROOT),
            cruderoot = aetherWoodVariant("cruderoot", TCWoods.CRUDEROOT),
            conberry = aetherWoodVariant("conberry", TCWoods.CONBERRY),
            sunroot = aetherWoodVariant("sunroot", TCWoods.SUNROOT),
            blightwillow = aetherWoodVariant("blightwillow", TCWoods.BLIGHTWILLOW),
            fieldsproot = aetherWoodVariant("fieldsproot", TCWoods.FIELDSPROOT),
            cloudcap = aetherWoodVariant("cloudcap", TCWoods.CLOUDCAP),
            jellyshroom = aetherWoodVariant("jellyshroom", TCWoods.JELLYSHROOM),
            crystal = aetherWoodVariant("crystal", TCWoods.CRYSTAL),
            glacia = aetherWoodVariant("glacia", TCWoods.GLACIA),
            // OTBWG
            aspen = woodVariant("aspen", TCWoods.ASPEN),
            baobab = woodVariant("baobab", TCWoods.BAOBAB),
            blueEnchanted = woodVariant("blue_enchanted", TCWoods.BLUE_ENCHANTED),
            cika = woodVariant("cika", TCWoods.CIKA),
            cypress = woodVariant("cypress", TCWoods.CYPRESS),
            ebony = woodVariant("ebony", TCWoods.EBONY),
            fir = woodVariant("fir", TCWoods.FIR),
            florus = woodVariant("florus", TCWoods.FLORUS),
            greenEnchanted = woodVariant("green_enchanted", TCWoods.GREEN_ENCHANTED),
            holly = woodVariant("holly", TCWoods.HOLLY),
            ironwood = woodVariant("ironwood", TCWoods.IRONWOOD),
            jacaranda = woodVariant("jacaranda", TCWoods.JACARANDA),
            mahogany = woodVariant("mahogany", TCWoods.MAHOGANY),
            maple = woodVariant("maple", TCWoods.MAPLE),
            palm = woodVariant("palm", TCWoods.PALM),
            paloVerde = woodVariant("palo_verde", TCWoods.PALO_VERDE),
            pine = woodVariant("pine", TCWoods.PINE),
            rainbowEucalyptus = woodVariant("rainbow_eucalyptus", TCWoods.RAINBOW_EUCALYPTUS),
            redwood = woodVariant("redwood", TCWoods.REDWOOD),
            sakura = woodVariant("sakura", TCWoods.SAKURA),
            skyris = woodVariant("skyris", TCWoods.SKYRIS),
            whiteMangrove = woodVariant("white_mangrove", TCWoods.WHITE_MANGROVE),
            willow = woodVariant("willow", TCWoods.WILLOW),
            witchHazel = woodVariant("witch_hazel", TCWoods.WITCH_HAZEL),
            zelkova = woodVariant("zelkova", TCWoods.ZELKOVA),

            // Regions Unexplored
            alpha = woodVariant("alpha", TCWoods.ALPHA),
            blackwood = woodVariant("blackwood", TCWoods.BLACKWOOD),
            blueBioshroom = woodVariant("blue_bioshroom", TCWoods.BLUE_BIOSHROOM),
            brimwood = woodVariant("brimwood", TCWoods.BRIMWOOD),
            cobalt = woodVariant("cobalt", TCWoods.COBALT),
            eucalyptus = woodVariant("eucalyptus", TCWoods.EUCALYPTUS),
            greenBioshroom = woodVariant("green_bioshroom", TCWoods.GREEN_BIOSHROOM),
            joshua = woodVariant("joshua", TCWoods.JOSHUA),
            kapok = woodVariant("kapok", TCWoods.KAPOK),
            larch = woodVariant("larch", TCWoods.LARCH),
            magnolia = woodVariant("magnolia", TCWoods.MAGNOLIA),
            mauve = woodVariant("mauve", TCWoods.MAUVE),
            pinkBioshroom = woodVariant("pink_bioshroom", TCWoods.PINK_BIOSHROOM),
            socotra = woodVariant("socotra", TCWoods.SOCOTRA),
            yellowBioshroom = woodVariant("yellow_bioshroom", TCWoods.YELLOW_BIOSHROOM),
            // Quark
            ashen = woodVariant("ashen", TCWoods.ANCIENT),
            azalea = woodVariant("azalea", TCWoods.AZALEA),
            trumpet = woodVariant("trumpet", TCWoods.BLOSSOM),

        // Rocks
            // Aether
            holystone = aetherRockVariant("holystone", TCRocks.HOLYSTONE),
            aseterite = aetherRockVariant("aseterite", TCRocks.ASETERITE),
            clorite = aetherRockVariant("clorite", TCRocks.CLORITE),
            divinite = aetherRockVariant("divinite", TCRocks.DIVINITE),
            driftshale = aetherRockVariant("driftshale", TCRocks.DRIFTSHALE),
            // OTBWG
            redRock = rockVariant("red_rock", TCRocks.RED_ROCK),
            dacite = rockVariant("dacite", TCRocks.DACITE),
            // Regions Unexplored
            chalk = rockVariant("chalk", TCRocks.CHALK),
            // Quark
            limestone = rockVariant("limestone", TCRocks.LIMESTONE),
            jasper = rockVariant("jasper", TCRocks.JASPER),
            shale = rockVariant("shale", TCRocks.SHALE),
            permafrost = rockVariant("permafrost", TCRocks.PERMAFROST),
            // Vanilla
            dripstone = rockVariant("dripstone", TCRocks.DRIPSTONE)
            ;

    private static MaterialVariantId woodVariant(String name, TCWoods woodType) {
        MaterialVariantId id = MaterialVariantId.create(MaterialIds.wood, name);
        woodVariants.put(id, woodType);
        allVariants.add(id);
        return id;
    }

    private static MaterialVariantId aetherWoodVariant(String name, TCWoods woodType) {
        MaterialVariantId id = MaterialVariantId.create(aetherWood, name);
        woodVariants.put(id, woodType);
        allVariants.add(id);
        return id;
    }

    private static MaterialVariantId rockVariant(String name, TCRocks rockType) {
        MaterialVariantId id = MaterialVariantId.create(MaterialIds.rock, name);
        rockVariants.put(id, rockType);
        allVariants.add(id);
        return id;
    }

    private static MaterialVariantId aetherRockVariant(String name, List<MaterialVariantId> list) {
        MaterialVariantId id = MaterialVariantId.create(aetherRock, name);
        list.add(id);
        allVariants.add(id);
        return id;
    }

    private static MaterialVariantId aetherRockVariant(String name, TCRocks rockType) {
        MaterialVariantId id = MaterialVariantId.create(aetherRock, name);
        rockVariants.put(id, rockType);
        allVariants.add(id);
        return id;
    }

    private static MaterialId id(String name) {
        return new MaterialId(TCompat.MOD_ID, name);
    }
}
