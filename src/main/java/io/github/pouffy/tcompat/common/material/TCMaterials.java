package io.github.pouffy.tcompat.common.material;

import io.github.pouffy.tcompat.TCompat;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import java.util.ArrayList;
import java.util.List;

public class TCMaterials {
    public static final List<MaterialVariantId> allVariants = new ArrayList<>();
    public static final List<MaterialVariantId> aetherVariantWoods = new ArrayList<>();
    public static final List<MaterialVariantId> aetherVariantRocks = new ArrayList<>();
    public static final List<MaterialVariantId> deepAetherVariantWoods = new ArrayList<>();
    public static final List<MaterialVariantId> deepAetherVariantRocks = new ArrayList<>();
    public static final List<MaterialVariantId> aetherReduxVariantWoods = new ArrayList<>();
    public static final List<MaterialVariantId> aetherReduxVariantRocks = new ArrayList<>();
    public static final List<MaterialVariantId> otbwgVariantWoods = new ArrayList<>();
    public static final List<MaterialVariantId> otbwgVariantRocks = new ArrayList<>();

    // Base Materials
    public static final MaterialId aetherWood = id("aether_wood");
    public static final MaterialId aetherRock = id("aether_rock");
    public static final MaterialId zanite = id("zanite");
    public static final MaterialId skyjade = id("skyjade");
    public static final MaterialId gravitite = id("gravitite");
    public static final MaterialId veridium = id("veridium");
    public static final MaterialId refinedSentrite = id("refined_sentrite");
    public static final MaterialId blightbunnyFang = id("blightbunny_fang");
    public static final MaterialId hellbark = id("hellbark");

    public static final MaterialId pyral = id("pyral");
    public static final MaterialId valkyrum = id("valkyrum");
    public static final MaterialId neptune = id("neptune");

    public static final MaterialId brass = id("brass");
    public static final MaterialId zinc = id("zinc");

    // Variants
    public static final MaterialVariantId
            //Aether Wood
            skyroot = aetherWoodVariant("skyroot", aetherVariantWoods),
            roseroot = aetherWoodVariant("roseroot", deepAetherVariantWoods),
            yagroot = aetherWoodVariant("yagroot", deepAetherVariantWoods),
            cruderoot = aetherWoodVariant("cruderoot", deepAetherVariantWoods),
            conberry = aetherWoodVariant("conberry", deepAetherVariantWoods),
            sunroot = aetherWoodVariant("sunroot", deepAetherVariantWoods),
            blightwillow = aetherWoodVariant("blightwillow", aetherReduxVariantWoods),
            fieldsproot = aetherWoodVariant("fieldsproot", aetherReduxVariantWoods),
            cloudcap = aetherWoodVariant("cloudcap", aetherReduxVariantWoods),
            jellyshroom = aetherWoodVariant("jellyshroom", aetherReduxVariantWoods),
            crystal = aetherWoodVariant("crystal", aetherReduxVariantWoods),
            glacia = aetherWoodVariant("glacia", aetherReduxVariantWoods),
            //Aether Rock
            holystone = aetherRockVariant("holystone", aetherVariantRocks),
            aseterite = aetherRockVariant("aseterite", deepAetherVariantRocks),
            clorite = aetherRockVariant("clorite", deepAetherVariantRocks),
            divinite = aetherRockVariant("divinite", aetherReduxVariantRocks),
            driftshale = aetherRockVariant("driftshale", aetherReduxVariantRocks),
            //OTBWG Wood
            aspen = woodVariant("aspen", otbwgVariantWoods),
            baobab = woodVariant("baobab", otbwgVariantWoods),
            blueEnchanted = woodVariant("blue_enchanted", otbwgVariantWoods),
            cika = woodVariant("cika", otbwgVariantWoods),
            cypress = woodVariant("cypress", otbwgVariantWoods),
            ebony = woodVariant("ebony", otbwgVariantWoods),
            fir = woodVariant("fir", otbwgVariantWoods),
            florus = woodVariant("florus", otbwgVariantWoods),
            greenEnchanted = woodVariant("green_enchanted", otbwgVariantWoods),
            holly = woodVariant("holly", otbwgVariantWoods),
            ironwood = woodVariant("ironwood", otbwgVariantWoods),
            jacaranda = woodVariant("jacaranda", otbwgVariantWoods),
            mahogany = woodVariant("mahogany", otbwgVariantWoods),
            maple = woodVariant("maple", otbwgVariantWoods),
            palm = woodVariant("palm", otbwgVariantWoods),
            paloVerde = woodVariant("palo_verde", otbwgVariantWoods),
            pine = woodVariant("pine", otbwgVariantWoods),
            rainbowEucalyptus = woodVariant("rainbow_eucalyptus", otbwgVariantWoods),
            redwood = woodVariant("redwood", otbwgVariantWoods),
            sakura = woodVariant("sakura", otbwgVariantWoods),
            skyris = woodVariant("skyris", otbwgVariantWoods),
            whiteMangrove = woodVariant("white_mangrove", otbwgVariantWoods),
            willow = woodVariant("willow", otbwgVariantWoods),
            witchHazel = woodVariant("witch_hazel", otbwgVariantWoods),
            zelkova = woodVariant("zelkova", otbwgVariantWoods),
            //OTBWG Rock
            redRock = rockVariant("red_rock", otbwgVariantRocks),
            dacite = rockVariant("dacite", otbwgVariantRocks)


                    ;

    private static MaterialVariantId woodVariant(String name, List<MaterialVariantId> list) {
        MaterialVariantId id = MaterialVariantId.create(MaterialIds.wood, name);
        list.add(id);
        allVariants.add(id);
        return id;
    }

    private static MaterialVariantId aetherWoodVariant(String name, List<MaterialVariantId> list) {
        MaterialVariantId id = MaterialVariantId.create(aetherWood, name);
        list.add(id);
        allVariants.add(id);
        return id;
    }

    private static MaterialVariantId rockVariant(String name, List<MaterialVariantId> list) {
        MaterialVariantId id = MaterialVariantId.create(MaterialIds.rock, name);
        list.add(id);
        allVariants.add(id);
        return id;
    }

    private static MaterialVariantId aetherRockVariant(String name, List<MaterialVariantId> list) {
        MaterialVariantId id = MaterialVariantId.create(aetherRock, name);
        list.add(id);
        allVariants.add(id);
        return id;
    }

    private static MaterialId id(String name) {
        return new MaterialId(TCompat.MOD_ID, name);
    }
}
