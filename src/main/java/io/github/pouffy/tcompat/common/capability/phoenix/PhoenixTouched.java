package io.github.pouffy.tcompat.common.capability.phoenix;

import io.github.pouffy.tcompat.common.capability.TCompatCapabilities;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.common.util.LazyOptional;

public interface PhoenixTouched extends INBTSynchable<CompoundTag> {
    Projectile getProjectile();

    static LazyOptional<PhoenixTouched> get(Projectile projectile) {
        return projectile.getCapability(TCompatCapabilities.PHOENIX_TOUCHED_CAPABILITY);
    }

    void setPhoenixProjectile(boolean isPhoenixProjectile);
    boolean isPhoenixProjectile();

    void setFireTime(int time);
    int getFireTime();
}
