package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.capability.compatible.LightningOwner;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import io.github.pouffy.tcompat.common.network.base.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Quartet;

public class LightningOwnerSyncPacket extends SyncEntityPacket<LightningOwner> {
    public LightningOwnerSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public LightningOwnerSyncPacket(int entityID, String key, INBTSynchable.Type type, Object value) {
        super(entityID, key, type, value);
    }

    public static LightningOwnerSyncPacket decode(FriendlyByteBuf buf) {
        return new LightningOwnerSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<LightningOwner> getCapability(Entity entity) {
        return LightningOwner.get(entity);
    }
}
