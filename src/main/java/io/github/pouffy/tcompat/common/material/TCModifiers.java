package io.github.pouffy.tcompat.common.material;

import io.github.pouffy.tcompat.TCompat;
import slimeknights.tconstruct.library.modifiers.ModifierId;

public class TCModifiers {
    public static final ModifierId aetherForged = id("aether_forged");
    public static final ModifierId blighted = id("blighted");
    public static final ModifierId escarstay = id("escarstay");

    private TCModifiers() {}

    private static ModifierId id(String name) {
        return new ModifierId(TCompat.MOD_ID, name);
    }
}
