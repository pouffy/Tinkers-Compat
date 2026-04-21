package io.github.pouffy.tcompat.common.capability.vampire_healing;

import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.VampireHealingSyncPacket;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class VampireHealingCapability implements VampireHealing {
    private final LivingEntity entity;

    private boolean performVampireHealing; //Used for: Draculite Material. Copy of Aether's vampire healing.

    public VampireHealingCapability(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public LivingEntity getEntity() {
        return this.entity;
    }

    @Override
    public void onUpdate() {
        if (!this.getEntity().level().isClientSide() && this.performVampireHealing()) {
            this.getEntity().heal(1.0F);
            this.setVampireHealing(false);
        }
    }

    @Override
    public void setVampireHealing(boolean performVampireHealing) {
        this.performVampireHealing = performVampireHealing;
    }

    @Override
    public boolean performVampireHealing() {
        return this.performVampireHealing;
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return Map.of();
    }

    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new VampireHealingSyncPacket(this.getEntity().getId(), key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return TCompatNetworking.INSTANCE;
    }

    @Override
    public CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {

    }
}
