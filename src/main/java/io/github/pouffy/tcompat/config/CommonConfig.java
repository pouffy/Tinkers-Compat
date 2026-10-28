package io.github.pouffy.tcompat.config;

import io.github.pouffy.tcompat.common.util.CompatHelper;
import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    public final ForgeConfigSpec.BooleanValue forceEnableDartShooters;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Optional Features");
        forceEnableDartShooters = builder
                .comment("Enables the Dart Shooter regardless of whether The Aether is present.")
                .define("forceEnableDartShooters", false);
        builder.pop();
    }

    public static boolean dartShootersEnabled() {
        return CompatHelper.isLoaded("aether") || TCompatConfig.COMMON.forceEnableDartShooters.get();
    }
}
