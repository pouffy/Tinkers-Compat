package io.github.pouffy.tcompat.common.capability.living.status.types;

import io.github.pouffy.tcompat.common.TCSounds;
import io.github.pouffy.tcompat.common.capability.living.status.LivingStatusHooks;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class VoidTouchedStatus extends AbstractLivingStatus {

    @Getter @Setter
    private int duration;
    @Getter @Setter
    private int amplifier;
    @Getter @Setter
    private int timeLeft;

    @Override
    String serializedName() {
        return LivingStatusHooks.VOID_TOUCHED;
    }

    public void voidTarget(LivingEntity entity, boolean isVoided) {
        var sound = isVoided ? TCSounds.VOID_TOUCHED_ACTIVATE : TCSounds.VOID_TOUCHED_DEACTIVATE;
        entity.level().playSound(null, entity.blockPosition(), sound.getSound(), SoundSource.PLAYERS);
        this.setActive(isVoided);
    }

    public void tick(LivingEntity entity, LivingEntity innerEntity) {
        if (getTimeLeft() > 0) {
            if (!entity.isAlive()) {
                setTimeLeft(0);
                voidTarget(entity, false);
            }
            createParticles(entity);
            setTimeLeft(getTimeLeft() - 1);
        } else {
            if (isActive()) {
                voidTarget(entity, false);
            }
            setAmplifier(0);
            setDuration(0);
        }
    }

    public void createParticles(LivingEntity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        var clientLevel = entity.level();
        for (int j = 0; j < 5; ++j) {
            float f1 = (entity.getRandom().nextFloat() * 2.0F - 1.0F) * entity.getBbWidth() * 0.5F;
            float f2 = (entity.getRandom().nextFloat() * 2.0F - 1.0F) * entity.getBbWidth() * 0.5F;
            clientLevel.addParticle(ParticleTypes.FALLING_OBSIDIAN_TEAR, entity.getX() + (double) f1, entity.getY() + 0.8F, entity.getZ() + (double) f2, vec3.x, vec3.y, vec3.z);
        }
    }

    public void hurtEvent(LivingHurtEvent event, LivingEntity innerEntity) {
        float voidAmount = event.getAmount();
        if (this.isActive()) {
            float multiplier = ((this.getAmplifier()) * 0.05f);
            voidAmount += (voidAmount * multiplier);
        }
        if (voidAmount != event.getAmount()) {
            event.setAmount(voidAmount);
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("duration", this.getDuration());
        tag.putInt("amplifier", this.getAmplifier());
        tag.putInt("timeLeft", this.getTimeLeft());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.setDuration(tag.getInt("duration"));
        this.setAmplifier(tag.getInt("amplifier"));
        this.setTimeLeft(tag.getInt("timeLeft"));
    }
}
