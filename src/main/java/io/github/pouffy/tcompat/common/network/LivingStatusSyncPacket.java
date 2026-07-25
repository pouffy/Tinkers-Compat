package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.capability.living.status.LivingStatus;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import io.github.pouffy.tcompat.common.network.base.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Quartet;

public class LivingStatusSyncPacket extends SyncEntityPacket<LivingStatus> {
    public LivingStatusSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public LivingStatusSyncPacket(int entityID, String key, INBTSynchable.Type type, Object value) {
        super(entityID, key, type, value);
    }

    public static LivingStatusSyncPacket decode(FriendlyByteBuf buf) {
        return new LivingStatusSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<LivingStatus> getCapability(Entity entity) {
        return LivingStatus.get((LivingEntity) entity);
    }
}
