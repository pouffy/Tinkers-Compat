package io.github.pouffy.tcompat.common.modifier;

import io.github.pouffy.tcompat.TCompat;
import slimeknights.tconstruct.library.modifiers.ModifierId;

public class TCModifiers {
    //Aether
    public static final ModifierId aetherForged = id("aether_forged");
    public static final ModifierId escarstay = id("escarstay");
    public static final ModifierId zanite = id("zanite");
    public static final ModifierId skyjade = id("skyjade");

    //Ice & Fire
    public static final ModifierId dreadbane = id("dreadbane");
    public static final ModifierId dampening = id("dampening");
    public static final ModifierId scorchborn = id("scorchborn");
    public static final ModifierId frostborn = id("frostborn");
    public static final ModifierId voltborn = id("voltborn");
    public static final ModifierId tideGuardian = id("tide_guardian");
    public static final ModifierId aquaShot = id("aqua_shot");
    public static final ModifierId petrifying = id("petrifying");
    public static final ModifierId allythropod = id("allythropod");

    //Cataclysm
    public static final ModifierId cataclysmic = id("cataclysmic");
    public static final ModifierId ghostly = id("ghostly");
    public static final ModifierId archaeologist = id("archaeologist");
    public static final ModifierId standstill = id("standstill");

    //Malum
    public static final ModifierId stained = id("stained");
    public static final ModifierId warded = id("warded");
    public static final ModifierId hallowed = id("hallowed");
    public static final ModifierId stronghold = id("stronghold");
    public static final ModifierId magicProficiency = id("magic_proficiency");
    public static final ModifierId cloaking = id("cloaking");
    public static final ModifierId arcaneResonance = id("arcane_resonance");
    public static final ModifierId spiritHarvester = id("spirit_harvester");
    public static final ModifierId runic = id("runic");
    public static final ModifierId spoiled = id("spoiled");
    public static final ModifierId integral = id("integral");
    public static final ModifierId runeOfIdleRestoration = id("rune_of_idle_restoration");
    public static final ModifierId runeOfVolatileDistortion = id("rune_of_volatile_distortion");
    public static final ModifierId runeOfAlimentCleansing = id("rune_of_aliment_cleansing");
    public static final ModifierId runeOfReactiveShielding = id("rune_of_reactive_shielding");
    public static final ModifierId runeOfFervor = id("rune_of_fervor");
    public static final ModifierId runeOfTheHeretic = id("rune_of_the_heretic");
    public static final ModifierId runeOfIgneousSolace = id("rune_of_igneous_solace");
    public static final ModifierId runeOfSacrificialEmpowerment = id("rune_of_sacrificial_empowerment");
    public static final ModifierId runeOfTwinnedDuration = id("rune_of_twinned_duration");

    //Deeper and Darker
    public static final ModifierId warden = id("warden");
    public static final ModifierId brightness = id("brightness");
    public static final ModifierId heartbeat = id("heartbeat");

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
    public static final ModifierId darkness_gem = compatId("tinkersjewelry", "darkness_gem");
    public static final ModifierId armorToughness_gem = id("armor_toughness_gem");

    //Construct's Casting
    public static final ModifierId conserving = compatId("constructs_casting", "conserving");
    public static final ModifierId fireUpgrade = compatId("constructs_casting", "fire_upgrade");
    public static final ModifierId iceUpgrade = compatId("constructs_casting", "ice_upgrade");
    public static final ModifierId lightningUpgrade = compatId("constructs_casting", "lightning_upgrade");
    public static final ModifierId puncturing = compatId("constructs_casting", "puncturing");
    public static final ModifierId calorific = compatId("constructs_casting", "calorific");
    public static final ModifierId holyUpgrade = compatId("constructs_casting", "holy_upgrade");
    public static final ModifierId fireDispulsion = compatId("constructs_casting", "fire_dispulsion");
    public static final ModifierId regrowth = compatId("constructs_casting", "regrowth");
    public static final ModifierId eldritchUpgrade = compatId("constructs_casting", "eldritch_upgrade");

    private TCModifiers() {}

    private static ModifierId id(String name) {
        return new ModifierId(TCompat.MOD_ID, name);
    }

    private static ModifierId compatId(String namespace, String path) {
        return new ModifierId(namespace, path);
    }
}
