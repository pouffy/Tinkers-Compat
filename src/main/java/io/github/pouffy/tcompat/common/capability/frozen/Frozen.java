package io.github.pouffy.tcompat.common.capability.frozen;

import io.github.pouffy.tcompat.common.capability.TCompatCapabilities;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;

public interface Frozen extends INBTSynchable<CompoundTag> {

    LivingEntity getEntity();

    static LazyOptional<Frozen> get(LivingEntity entity) {
        return entity.getCapability(TCompatCapabilities.FROZEN_CAPABILITY);
    }

    void onUpdate();
    void onJoinLevel();
    void onLogin();

    boolean isFrozen();
    int getFrozenTicks();

    void setFrozen(boolean frozen);
    void setFrozenTicks(int frozenTicks);

    void freeze(int ticks);
}
