package io.github.pouffy.tcompat.common.capability.living.cryogenic;

import io.github.pouffy.tcompat.common.TCDamageSources;
import io.github.pouffy.tcompat.common.capability.TCompatCapabilities;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface Cryogenic extends INBTSynchable<CompoundTag> {
    LivingEntity getEntity();

    static LazyOptional<Cryogenic> get(LivingEntity entity) {
        return entity.getCapability(TCompatCapabilities.CRYOGENIC_CAPABILITY);
    }

    default void tick() {
        if (getTimeLeft() > 0) {
            if (!getEntity().isAlive()) {
                setTimeLeft(0);
                freezeTarget(false);
            }
            createParticles();
            if (getEntity().level().getGameTime() % 20 == 0) {
                progressFrozen();
            }
            setTimeLeft(getTimeLeft() - 1);
        } else {
            if (isFrozen()) {
                freezeTarget(false);
            }
            setDuration(0);
        }
    }

    default void hurtEvent(LivingHurtEvent event) {
        if (event.getSource().is(DamageTypeTags.IS_FREEZING)) {
            event.setAmount(event.getAmount() * 1.2F);
        }
    }

    default void createParticles() {
        RandomSource random = getEntity().level().getRandom();
        boolean bl = getEntity().xOld != getEntity().getX() || getEntity().zOld != getEntity().getZ();
        if (bl && random.nextBoolean()) {
            getEntity().level().addParticle(ParticleTypes.SNOWFLAKE, getEntity().getX(), getEntity().blockPosition().getY() + 1, getEntity().getZ(), Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F, 0.05F, Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F);
        }
    }

    default void progressFrozen() {
        if (!getEntity().getBlockStateOn().isAir()) {
            getEntity().makeStuckInBlock(getEntity().getBlockStateOn(), new Vec3(0.9F, 1.5F, 0.9F));
        }
        getEntity().setIsInPowderSnow(true);
        getEntity().setTicksFrozen(Math.min(getEntity().getTicksRequiredToFreeze(), getEntity().getTicksFrozen() + 5));
        if (!getEntity().level().isClientSide()) {
            getEntity().setSharedFlagOnFire(false);
            getEntity().hurt(TCDamageSources.create(getEntity().level(), TCDamageSources.CRYOGENIC), (float)(4 * (getEntity().fireImmune() ? 0.25 : 0.125)));
        }
    }

    void freezeTarget(boolean isFrozen);
    void setFrozen(boolean isFrozen);
    boolean isFrozen();

    void setDuration(int ticks);
    int getDuration();

    void setTimeLeft(int ticks);
    int getTimeLeft();
}
