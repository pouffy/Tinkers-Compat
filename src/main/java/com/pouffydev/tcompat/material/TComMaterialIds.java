package com.pouffydev.tcompat.material;

import com.pouffydev.tcompat.TCompat;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import java.util.ArrayList;
import java.util.List;

public class TComMaterialIds {

    public static final List<MaterialVariantId> otbwgVariants = new ArrayList<>();

    // wood

    // Oh, The Biomes We've Gone
    public static final MaterialVariantId
            aspen = woodVariant("aspen", otbwgVariants),
            baobab = woodVariant("baobab", otbwgVariants),
            blueEnchanted = woodVariant("blue_enchanted", otbwgVariants),
            cika = woodVariant("cika", otbwgVariants),
            cypress = woodVariant("cypress", otbwgVariants),
            ebony = woodVariant("ebony", otbwgVariants),
            fir = woodVariant("fir", otbwgVariants),
            florus = woodVariant("florus", otbwgVariants),
            greenEnchanted = woodVariant("green_enchanted", otbwgVariants),
            holly = woodVariant("holly", otbwgVariants),
            ironwood = woodVariant("ironwood", otbwgVariants),
            jacaranda = woodVariant("jacaranda", otbwgVariants),
            mahogany = woodVariant("mahogany", otbwgVariants),
            maple = woodVariant("maple", otbwgVariants),
            palm = woodVariant("palm", otbwgVariants),
            paloVerde = woodVariant("palo_verde", otbwgVariants),
            pine = woodVariant("pine", otbwgVariants),
            rainbowEucalyptus = woodVariant("rainbow_eucalyptus", otbwgVariants),
            redwood = woodVariant("redwood", otbwgVariants),
            sakura = woodVariant("sakura", otbwgVariants),
            skyris = woodVariant("skyris", otbwgVariants),
            whiteMangrove = woodVariant("white_mangrove", otbwgVariants),
            willow = woodVariant("willow", otbwgVariants),
            witchHazel = woodVariant("witch_hazel", otbwgVariants),
            zelkova = woodVariant("zelkova", otbwgVariants);


    private static MaterialVariantId woodVariant(String name, List<MaterialVariantId> list) {
        MaterialVariantId id = MaterialVariantId.create(MaterialIds.wood, name);
        list.add(id);
        return id;
    }

    private static MaterialId id(String name) {
        return new MaterialId(TCompat.MOD_ID, name);
    }
}
