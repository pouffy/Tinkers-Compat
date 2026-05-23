package io.github.pouffy.tcompat.common.capability.projectile.ability;

import io.github.pouffy.tcompat.common.capability.TCompatCapabilities;
import io.github.pouffy.tcompat.common.capability.projectile.ability.types.AbstractProjectileAbility;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.common.util.LazyOptional;

import java.util.HashMap;
import java.util.Map;

public interface ProjectileAbility extends INBTSynchable<CompoundTag> {
    Projectile projectile();

    static LazyOptional<ProjectileAbility> get(Projectile projectile) {
        return projectile.getCapability(TCompatCapabilities.PROJECTILE_ABILITY_CAPABILITY);
    }

    /**
     * @return A map of ability names to {@link AbstractProjectileAbility}
     */
    Map<String, AbstractProjectileAbility> getAbilities();

    /**
     * Filters out any abilities that aren't active.
     * @return A map of active ability names to {@link AbstractProjectileAbility}
     */
    default Map<String, AbstractProjectileAbility> getActiveAbilities() {
        Map<String, AbstractProjectileAbility> abilityMap = new HashMap<>();
        for (var entry : getAbilities().entrySet()) {
            if (entry.getValue().isActive())
                abilityMap.put(entry.getKey(), entry.getValue());
        }
        return abilityMap;
    }

    /**
     * @return An instance of {@link AbstractProjectileAbility} regardless of its active status
     */
    default AbstractProjectileAbility getAbility(String name) {
        return getAbilities().get(name);
    }

    /**
     * Syncronises the ability to the client. Automatically run during:
     * {@link AbstractProjectileAbility#tickArrow(AbstractArrow, Projectile, boolean, int)}
     * - {@link AbstractProjectileAbility#tickProjectile(Projectile, Projectile)}
     * - {@link ProjectileAbility#activate(Projectile, String)}
     * - {@link ProjectileAbility#deactivate(Projectile, String)}
     * @param ability the {@link AbstractProjectileAbility} to be synced
     */
    void sendClient(AbstractProjectileAbility ability);

    /**
     * Activates the ability in a given projectile.
     * @param projectile projectile to activate the ability for
     * @param name name of the ability to activate
     */
    static void activate(Projectile projectile, String name) {
        ProjectileAbility.get(projectile).ifPresent(ability -> {
            ability.getAbility(name).setActive(true);
            ability.sendClient(ability.getAbility(name));
        });
    }

    /**
     * Deactivates the ability in a given projectile.
     * @param projectile projectile to deactivate the ability for
     * @param name name of the ability to deactivate
     */
    static void deactivate(Projectile projectile, String name) {
        ProjectileAbility.get(projectile).ifPresent(ability -> {
            ability.getAbility(name).setActive(false);
            ability.sendClient(ability.getAbility(name));
        });
    }
}
