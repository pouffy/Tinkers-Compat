package io.github.pouffy.tcompat.common.capability.cooldown;

import io.github.pouffy.tcompat.common.capability.TCompatCapabilities;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import slimeknights.tconstruct.library.modifiers.ModifierId;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public interface ModifierCooldowns extends INBTSynchable<CompoundTag> {
    Player getEntity();

    static LazyOptional<ModifierCooldowns> get(Player entity) {
        return entity.getCapability(TCompatCapabilities.MODIFIER_COOLDOWNS_CAPABILITY);
    }

    Map<ModifierId, CooldownInstance> getCooldowns();
    int getTickCount();

    default boolean isOnCooldown(ModifierId modifierId) {
        return this.getCooldownPercent(modifierId, 0.0F) > 0.0F;
    }

    default float getCooldownPercent(ModifierId modifierId, float partialTicks) {
        CooldownInstance instance = getCooldowns().get(modifierId);
        if (instance != null) {
            float f = (float)(instance.endTime - instance.startTime);
            float f1 = (float)instance.endTime - (this.getTickCount() + partialTicks);
            return Mth.clamp(f1 / f, 0.0F, 1.0F);
        } else {
            return 0.0F;
        }
    }

    void tick();

    default void addCooldown(ModifierId modifierId, int ticks) {
        getCooldowns().put(modifierId, new CooldownInstance(getTickCount(), getTickCount() + ticks));
    }

    default void removeCooldown(ModifierId modifierId) {
        getCooldowns().remove(modifierId);
    }

    //Static methods for easy access

    static boolean isOnCooldown(ModifierId id, LivingEntity entity) {
        AtomicBoolean onCooldown = new AtomicBoolean(false);
        if (entity instanceof Player player) {
            ModifierCooldowns.get(player).ifPresent(cooldown -> {
                onCooldown.set(cooldown.isOnCooldown(id));
            });
        }
        return onCooldown.get();
    }

    static void addCooldown(ModifierId id, int ticks, LivingEntity entity) {
        if (entity instanceof Player player) {
            ModifierCooldowns.get(player).ifPresent(cooldown -> {
                cooldown.addCooldown(id, ticks);
            });
        }
    }

    static void removeCooldown(ModifierId id, LivingEntity entity) {
        if (entity instanceof Player player) {
            ModifierCooldowns.get(player).ifPresent(cooldown -> {
                cooldown.removeCooldown(id);
            });
        }
    }

    @Getter
    class CooldownInstance {
        final int startTime;
        final int endTime;

        CooldownInstance(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
}
