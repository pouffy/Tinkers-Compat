package io.github.pouffy.tcompat.common.capability.living.cryogenic;

import io.github.pouffy.tcompat.common.TCSounds;
import io.github.pouffy.tcompat.common.network.CryogenicSyncPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.VoidTouchedSyncPacket;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CryogenicCapability implements Cryogenic {

    private final LivingEntity entity;

    private boolean isFrozen;
    private int duration;

    private int timeLeft;

    //Sync all just in case
    private final Map<String, Triple<INBTSynchable.Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("freezeTarget", Triple.of(INBTSynchable.Type.BOOLEAN, (object) -> this.freezeTarget((boolean) object), this::isFrozen)),
            Map.entry("setFrozen", Triple.of(INBTSynchable.Type.BOOLEAN, (object) -> this.setFrozen((boolean) object), this::isFrozen)),
            Map.entry("setDuration", Triple.of(INBTSynchable.Type.INT, (object) -> this.setDuration((int) object), this::getDuration))
    );

    public CryogenicCapability(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public LivingEntity getEntity() {
        return this.entity;
    }

    @Override
    public void freezeTarget(boolean isFrozen) {
        if (isFrozen) {
            boolean useIceshock = entity.getRandom().nextFloat() < 0.01f;
            SoundEvent sound = useIceshock ? TCSounds.ICESHOCK.getSound() : SoundEvents.PLAYER_HURT_FREEZE;
            entity.level().playSound(null, entity.blockPosition(), sound, SoundSource.PLAYERS);
        } else {
            entity.level().playSound(null, entity.blockPosition(), SoundEvents.POWDER_SNOW_BREAK, SoundSource.PLAYERS);
        }
        this.setFrozen(isFrozen);
    }

    @Override
    public void setFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;
    }

    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override
    public void setDuration(int ticks) {
        this.duration = ticks;
        this.timeLeft = ticks;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }

    @Override
    public void setTimeLeft(int ticks) {
        this.timeLeft = ticks;
    }

    @Override
    public int getTimeLeft() {
        return this.timeLeft;
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new CryogenicSyncPacket(this.getEntity().getId(), key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return TCompatNetworking.INSTANCE;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("Frozen", this.isFrozen());
        tag.putInt("Duration", this.getDuration());
        tag.putInt("TimeLeft", this.getTimeLeft());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("Frozen")) {
            this.setFrozen(tag.getBoolean("Frozen"));
        }
        if (tag.contains("Duration")) {
            this.setDuration(tag.getInt("Duration"));
        }

        if (tag.contains("TimeLeft")) {
            this.setTimeLeft(tag.getInt("TimeLeft"));
        }
    }
}
