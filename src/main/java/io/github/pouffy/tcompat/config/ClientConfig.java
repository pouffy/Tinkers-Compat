package io.github.pouffy.tcompat.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public final ForgeConfigSpec.ConfigValue<Integer> modifierCooldown_xOffset;
    public final ForgeConfigSpec.ConfigValue<Integer> modifierCooldown_yOffset;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.push("UI");
        builder.push("Modifier Cooldowns");
        builder.comment("Modifier cooldowns only display if a modifier is on cooldown");
        modifierCooldown_xOffset = builder.define("modifierCooldownXOffset", 0);
        modifierCooldown_yOffset = builder.define("modifierCooldownYOffset", 0);
        builder.pop();
    }
}
