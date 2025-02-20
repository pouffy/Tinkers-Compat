package com.pouffydev.tcompat.modifier;

import com.pouffydev.tcompat.TCompat;
import slimeknights.tconstruct.library.modifiers.ModifierId;

public class TComModifierIds {

    public static final ModifierId aetherForged = id("aether_forged");
    //public static final ModifierId magicProficiency = id("magic_proficiency");
    //public static final ModifierId soulWarding = id("soul_warding");
    //public static final ModifierId blissfulHarmony = id("blissful_harmony");
    //public static final ModifierId reaping = id("reaping");

    private TComModifierIds() {}

    private static ModifierId id(String name) {
        return new ModifierId(TCompat.MOD_ID, name);
    }
}
