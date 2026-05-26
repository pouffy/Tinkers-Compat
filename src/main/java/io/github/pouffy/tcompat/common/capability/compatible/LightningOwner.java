package io.github.pouffy.tcompat.common.capability.compatible;

import io.github.pouffy.tcompat.common.capability.TCompatCapabilities;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Optional;

public interface LightningOwner extends INBTSynchable<CompoundTag> {
    Entity getEntity();

    static LazyOptional<LightningOwner> get(Entity entity) {
        return entity.getCapability(TCompatCapabilities.LIGHTNING_OWNER_CAPABILITY);
    }

    void setLightningOwner(Entity owner);
    Entity getLightningOwner();

    static boolean lightningTracking(Entity entity, LightningBolt lightning) {
        if (entity instanceof LivingEntity livingEntity) {
            Optional<LightningOwner> compatibilityOptional = LightningOwner.get(lightning).resolve();
            if (compatibilityOptional.isPresent()) {
                LightningOwner lightningTracker = compatibilityOptional.get();
                if (lightningTracker.getLightningOwner() != null) {
                    return livingEntity == lightningTracker.getLightningOwner() || livingEntity == lightningTracker.getLightningOwner().getVehicle() || lightningTracker.getLightningOwner().getPassengers().contains(livingEntity);
                }
            }
        }
        return false;
    }
}
