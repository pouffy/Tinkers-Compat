package io.github.pouffy.tcompat.common.cooldown;

public class ClientModifierCooldowns {

    public static final ModifierCooldowns playerCooldowns = new ModifierCooldowns();

    public static ModifierCooldowns getCooldowns() {
        return playerCooldowns;
    }
}
