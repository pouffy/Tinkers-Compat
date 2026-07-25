package io.github.pouffy.tcompat.common.capability.living.status;

import io.github.pouffy.tcompat.common.capability.TCompatCapabilities;
import io.github.pouffy.tcompat.common.capability.living.status.types.AbstractLivingStatus;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public interface LivingStatus extends INBTSynchable<CompoundTag> {
    LivingEntity entity();

    static LazyOptional<LivingStatus> get(LivingEntity entity) {
        return entity.getCapability(TCompatCapabilities.LIVING_STATUS_CAPABILITY);
    }

    /**
     * @return A map of status names to {@link AbstractLivingStatus}
     */
    Map<String, AbstractLivingStatus> getStatuses();

    /**
     * Filters out any statuses that aren't active.
     * @return A map of active status names to {@link AbstractLivingStatus}
     */
    default Map<String, AbstractLivingStatus> getActiveStatuses() {
        Map<String, AbstractLivingStatus> statusMap = new HashMap<>();
        for (var entry : getStatuses().entrySet()) {
            if (entry.getValue().isActive())
                statusMap.put(entry.getKey(), entry.getValue());
        }
        return statusMap;
    }

    /**
     * @return An instance of {@link AbstractLivingStatus} regardless of its active status
     */
    default AbstractLivingStatus getStatus(String name) {
        return getStatuses().get(name);
    }

    /**
     * Syncronises the status to the client. Automatically run during:
     * {@link AbstractLivingStatus#tick(LivingEntity, LivingEntity)}
     * - {@link LivingStatus#trigger(LivingEntity, String, Consumer)}
     * - {@link LivingStatus#remove(LivingEntity, String)}
     * @param status the {@link AbstractLivingStatus} to be synced
     */
    void sendClient(AbstractLivingStatus status);

    /**
     * Activates the status in a given entity.
     * @param entity entity to activate the status for
     * @param name name of the status to activate
     */
    static void trigger(LivingEntity entity, String name, Consumer<AbstractLivingStatus> action) {
        LivingStatus.get(entity).ifPresent(ability -> {
            ability.getStatus(name).setActive(true);
            ability.sendClient(ability.getStatus(name));
            action.accept(ability.getStatus(name));
        });
    }

    /**
     * Deactivates the status in a given entity.
     * @param entity entity to deactivate the status for
     * @param name name of the status to deactivate
     */
    static void remove(LivingEntity entity, String name) {
        LivingStatus.get(entity).ifPresent(ability -> {
            ability.getStatus(name).setActive(false);
            ability.sendClient(ability.getStatus(name));
        });
    }
}
