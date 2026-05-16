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
    public static final ModifierId tideGuardian = id("tide_guardian");

    public static final ModifierId cataclysmic = id("cataclysmic");
    public static final ModifierId ghostly = id("ghostly");
    public static final ModifierId archaeologist = id("archaeologist");
    public static final ModifierId standstill = id("standstill");

    //Tinkers' Jewelry
    public static final ModifierId mining_speed_gem = compatId("tinkersjewelry", "mining_speed_gem");
    public static final ModifierId poison_gem = compatId("tinkersjewelry", "poison_gem");
    public static final ModifierId armor_gem = compatId("tinkersjewelry", "armor_gem");
    public static final ModifierId glowing_gem = compatId("tinkersjewelry", "glowing_gem");
    public static final ModifierId swim_gem = compatId("tinkersjewelry", "swim_gem");
    public static final ModifierId health_gem = compatId("tinkersjewelry", "health_gem");
    public static final ModifierId flame_gem = compatId("tinkersjewelry", "flame_gem");
    public static final ModifierId precious = compatId("tinkersjewelry", "precious");
    public static final ModifierId ductile = compatId("tinkersjewelry", "ductile");


    private TCModifiers() {}

    private static ModifierId id(String name) {
        return new ModifierId(TCompat.MOD_ID, name);
    }

    private static ModifierId compatId(String namespace, String path) {
        return new ModifierId(namespace, path);
    }
}
