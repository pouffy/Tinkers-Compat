package io.github.pouffy.tcompat.common.capability.compatible;

import io.github.pouffy.tcompat.common.capability.TCompatCapabilities;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;

public interface LightningOwner extends INBTSynchable<CompoundTag> {
    Entity getEntity();

    static LazyOptional<LightningOwner> get(Entity entity) {
        return entity.getCapability(TCompatCapabilities.LIGHTNING_OWNER_CAPABILITY);
    }

    void setLightningOwner(Entity owner);
    Entity getLightningOwner();
}
