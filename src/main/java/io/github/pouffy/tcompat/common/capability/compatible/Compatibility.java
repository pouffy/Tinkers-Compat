package io.github.pouffy.tcompat.common.capability.compatible;

import io.github.pouffy.tcompat.common.capability.TCompatCapabilities;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;

public interface Compatibility extends INBTSynchable<CompoundTag> {
    Entity getEntity();

    static LazyOptional<Compatibility> get(Entity entity) {
        return entity.getCapability(TCompatCapabilities.COMPATIBILITY_CAPABILITY);
    }

    void tick();

    void setVampireHealing(boolean performVampireHealing);

    boolean performVampireHealing();

    void setLightningOwner(Entity owner);
    Entity getLightningOwner();
}
