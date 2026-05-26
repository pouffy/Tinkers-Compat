package io.github.pouffy.tcompat.common.capability.void_touched;

import io.github.pouffy.tcompat.common.capability.TCompatCapabilities;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface VoidTouched extends INBTSynchable<CompoundTag> {
    LivingEntity getEntity();

    static LazyOptional<VoidTouched> get(LivingEntity entity) {
        return entity.getCapability(TCompatCapabilities.VOID_TOUCHED_CAPABILITY);
    }

    default void tick() {
        if (getTimeLeft() > 0) {
            if (!getEntity().isAlive()) {
                setTimeLeft(0);
                voidTarget(false);
            }
            createParticles();
            setTimeLeft(getTimeLeft() - 1);
        } else {
            if (isVoided()) {
                voidTarget(false);
            }
            setAmplifier(0);
            setDuration(0);
        }
    }

    default void createParticles() {
        Vec3 vec3 = getEntity().getDeltaMovement();
        var clientLevel = getEntity().level();
        for (int j = 0; j < 5; ++j) {
            float f1 = (getEntity().getRandom().nextFloat() * 2.0F - 1.0F) * getEntity().getBbWidth() * 0.5F;
            float f2 = (getEntity().getRandom().nextFloat() * 2.0F - 1.0F) * getEntity().getBbWidth() * 0.5F;
            clientLevel.addParticle(ParticleTypes.FALLING_OBSIDIAN_TEAR, getEntity().getX() + (double) f1, getEntity().getY() + 0.8F, getEntity().getZ() + (double) f2, vec3.x, vec3.y, vec3.z);
        }
    }

    default void hurtEvent(LivingHurtEvent event) {
        float voidAmount = event.getAmount();
        if (this.isVoided()) {
            float multiplier = ((this.getAmplifier()) * 0.05f);
            voidAmount += (voidAmount * multiplier);
        }
        if (voidAmount != event.getAmount()) {
            event.setAmount(voidAmount);
        }
    }

    void voidTarget(boolean isVoided);
    void setVoided(boolean isVoided);
    boolean isVoided();

    void setAmplifier(int amplifier);
    int getAmplifier();

    void setDuration(int ticks);
    int getDuration();

    void setTimeLeft(int ticks);
    int getTimeLeft();
}
