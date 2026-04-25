package io.github.pouffy.tcompat.common.capability.void_touched;

import io.github.pouffy.tcompat.common.capability.TCSounds;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.VoidTouchedSyncPacket;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class VoidTouchedCapability implements VoidTouched {
    private final LivingEntity entity;

    private boolean isVoided;
    private int amplifier;
    private int duration;

    private int timeLeft;

    //Sync all just in case
    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("voidTarget", Triple.of(Type.BOOLEAN, (object) -> this.voidTarget((boolean) object), this::isVoided)),
            Map.entry("setVoided", Triple.of(Type.BOOLEAN, (object) -> this.setVoided((boolean) object), this::isVoided)),
            Map.entry("setAmplifier", Triple.of(Type.INT, (object) -> this.setAmplifier((int) object), this::getAmplifier)),
            Map.entry("setDuration", Triple.of(Type.INT, (object) -> this.setDuration((int) object), this::getDuration))
    );

    public VoidTouchedCapability(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public LivingEntity getEntity() {
        return this.entity;
    }

    public void voidTarget(boolean isVoided) {
        var sound = isVoided ? TCSounds.VOID_TOUCHED_ACTIVATE : TCSounds.VOID_TOUCHED_DEACTIVATE;
        getEntity().level().playSound(null, getEntity().blockPosition(), sound.getSound(), SoundSource.PLAYERS);
        this.setVoided(isVoided);
    }

    @Override
    public void setVoided(boolean isVoided) {
        this.isVoided = isVoided;
    }

    @Override
    public boolean isVoided() {
        return this.isVoided;
    }

    @Override
    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }

    @Override
    public int getAmplifier() {
        return this.amplifier;
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
        return new VoidTouchedSyncPacket(this.getEntity().getId(), key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return TCompatNetworking.INSTANCE;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("Voided", this.isVoided());
        tag.putInt("Duration", this.getDuration());
        tag.putInt("Amplifier", this.getAmplifier());
        tag.putInt("TimeLeft", this.getTimeLeft());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("Voided")) {
            this.setVoided(tag.getBoolean("Voided"));
        }
        if (tag.contains("Duration")) {
            this.setDuration(tag.getInt("Duration"));
        }
        if (tag.contains("Amplifier")) {
            this.setAmplifier(tag.getInt("Amplifier"));
        }
        if (tag.contains("TimeLeft")) {
            this.setTimeLeft(tag.getInt("TimeLeft"));
        }
    }
}
