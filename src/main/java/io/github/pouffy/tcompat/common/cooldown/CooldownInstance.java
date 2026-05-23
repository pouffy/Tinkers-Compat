package io.github.pouffy.tcompat.common.cooldown;

public class CooldownInstance {
    private int cooldownRemaining;
    private final int startCooldown;

    public CooldownInstance(int startCooldown) {
        this.startCooldown = startCooldown;
        this.cooldownRemaining = startCooldown;
    }

    public CooldownInstance(int startCooldown, int cooldownRemaining) {
        this.startCooldown = startCooldown;
        this.cooldownRemaining = cooldownRemaining;
    }

    public void decrement() {
        cooldownRemaining--;
    }

    public void decrementBy(int amount) {
        cooldownRemaining -= amount;
    }

    public int getCooldownRemaining() {
        return cooldownRemaining;
    }

    public int getStartCooldown() {
        return startCooldown;
    }

    public float getCooldownPercent() {
        if (cooldownRemaining == 0) {
            return 0;
        }

        return cooldownRemaining / (float) startCooldown;
    }
}
