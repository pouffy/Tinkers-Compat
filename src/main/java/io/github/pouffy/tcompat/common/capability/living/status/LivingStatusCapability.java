package io.github.pouffy.tcompat.common.capability.living.status;

import io.github.pouffy.tcompat.common.capability.living.status.types.AbstractLivingStatus;
import io.github.pouffy.tcompat.common.network.LivingStatusSyncPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LivingStatusCapability implements LivingStatus {

    private final LivingEntity entity;

    public final Map<String, AbstractLivingStatus> statuses;

    public LivingStatusCapability(LivingEntity entity) {
        this.entity = entity;
        this.statuses = Map.ofEntries(
        );
    }

    @Override
    public LivingEntity entity() {
        return this.entity;
    }

    @Override
    public Map<String, AbstractLivingStatus> getStatuses() {
        return this.statuses;
    }

    @Override
    public void sendClient(AbstractLivingStatus status) {
        this.setSynched(Direction.CLIENT, status.getSetKey(), status.isActive());
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> functions = new HashMap<>();
        for (var status : this.getStatuses().entrySet()) {
            if (status.getValue() == null) continue;
            functions.put(status.getValue().getSetKey(), Triple.of(Type.BOOLEAN, (object) -> this.getStatus(status.getKey()).setActive((boolean) object), () -> this.getStatus(status.getKey()).isActive()));
        }
        return functions;
    }

    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new LivingStatusSyncPacket(this.entity.getId(), key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return TCompatNetworking.INSTANCE;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        this.getStatuses().forEach((name, ability) -> tag.put(name, ability.serializeTag()));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.getStatuses().forEach((name, ability) -> ability.deserializeTag(tag.getCompound(name)));
    }
}
