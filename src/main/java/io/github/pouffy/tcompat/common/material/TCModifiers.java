package io.github.pouffy.tcompat.common.material;

import io.github.pouffy.tcompat.TCompat;
import slimeknights.tconstruct.library.modifiers.ModifierId;

public class TCModifiers {
    public static final ModifierId aetherForged = id("aether_forged");
    public static final ModifierId blighted = id("blighted");
    public static final ModifierId escarstay = id("escarstay");

    public static final ModifierId zanite = id("zanite");
    public static final ModifierId skyjade = id("skyjade");

    public static final ModifierId dreadbane = id("dreadbane");
    public static final ModifierId dampening = id("dampening");
    public static final ModifierId scorchborn = id("scorchborn");
    public static final ModifierId frostborn = id("frostborn");
    public static final ModifierId voltborn = id("voltborn");

    private TCModifiers() {}

    private static ModifierId id(String name) {
        return new ModifierId(TCompat.MOD_ID, name);
    }
}
