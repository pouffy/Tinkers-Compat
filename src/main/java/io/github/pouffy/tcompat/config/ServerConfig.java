package io.github.pouffy.tcompat.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {

    public final ForgeConfigSpec.BooleanValue forceEnableDartShooters;

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Optional Features");
        forceEnableDartShooters = builder
                .comment("Enables the Dart Shooter regardless of whether The Aether is present.")
                .define("forceEnableDartShooters", false);
        builder.pop();
    }
}
