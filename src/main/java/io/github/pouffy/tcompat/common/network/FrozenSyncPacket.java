package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.capability.frozen.Frozen;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import io.github.pouffy.tcompat.common.network.base.SyncLivingEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Quartet;

public class FrozenSyncPacket extends SyncLivingEntityPacket<Frozen> {
    public FrozenSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public FrozenSyncPacket(int entityID, String key, INBTSynchable.Type type, Object value) {
        super(entityID, key, type, value);
    }

    public static FrozenSyncPacket decode(FriendlyByteBuf buf) {
        return new FrozenSyncPacket(SyncLivingEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<Frozen> getCapability(LivingEntity entity) {
        return Frozen.get(entity);
    }
}
