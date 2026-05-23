package io.github.pouffy.tcompat.client;

import net.minecraftforge.common.ForgeConfigSpec;

public class TComClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.ConfigValue<Integer> MODIFIER_COOLDOWNS_Y_OFFSET;
    public static final ForgeConfigSpec.ConfigValue<Integer> MODIFIER_COOLDOWNS_X_OFFSET;
    public static final ForgeConfigSpec SPEC;

    public TComClientConfig() {
    }

    static {
        BUILDER.push("UI");
        BUILDER.push("Modifier Cooldowns");
        BUILDER.comment("Modifier cooldowns only display if a modifier is on cooldown");
        MODIFIER_COOLDOWNS_X_OFFSET = BUILDER.define("modifierCooldownsXOffset", 0);
        MODIFIER_COOLDOWNS_Y_OFFSET = BUILDER.define("modifierCooldownsYOffset", 0);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
