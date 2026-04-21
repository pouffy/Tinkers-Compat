package io.github.pouffy.tcompat.common.capability.vampire_healing;

import io.github.pouffy.tcompat.common.capability.TCompatCapabilities;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;

public interface VampireHealing extends INBTSynchable<CompoundTag> {
    LivingEntity getEntity();

    static LazyOptional<VampireHealing> get(LivingEntity entity) {
        return entity.getCapability(TCompatCapabilities.VAMPIRE_HEALING_CAPABILITY);
    }

    void onUpdate();

    void setVampireHealing(boolean performVampireHealing);

    boolean performVampireHealing();
}
