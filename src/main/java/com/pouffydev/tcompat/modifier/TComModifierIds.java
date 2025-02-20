package com.pouffydev.tcompat.modifier;

import com.pouffydev.tcompat.TCompat;
import slimeknights.tconstruct.library.modifiers.ModifierId;

public class TComModifierIds {

    public static final ModifierId aetherForged = id("aether_forged");

    private TComModifierIds() {}

    private static ModifierId id(String name) {
        return new ModifierId(TCompat.MOD_ID, name);
    }
}
