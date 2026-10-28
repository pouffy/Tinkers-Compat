package io.github.pouffy.tcompat.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {

    public final ForgeConfigSpec.BooleanValue forceEnableDartShooters;

    public final ForgeConfigSpec.IntValue voidTouchedAmpCap;

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Optional Features");
        forceEnableDartShooters = builder
                .comment("Enables the Dart Shooter regardless of whether The Aether is present.")
                .define("forceEnableDartShooters", false);
        builder.pop();
        builder.push("Modifiers");
        voidTouchedAmpCap = builder
                .comment("Maximum amplifier Void Touched can reach.")
                .comment("Set to 0 for no limit.")
                .defineInRange("voidTouchedAmpCap", 0, 0, 256);
        builder.pop();
    }
}
