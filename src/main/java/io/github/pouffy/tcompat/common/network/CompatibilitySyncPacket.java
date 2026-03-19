package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.capability.compatible.Compatibility;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import io.github.pouffy.tcompat.common.network.base.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Quartet;

public class CompatibilitySyncPacket extends SyncEntityPacket<Compatibility> {
    public CompatibilitySyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public CompatibilitySyncPacket(int entityID, String key, INBTSynchable.Type type, Object value) {
        super(entityID, key, type, value);
    }

    public static CompatibilitySyncPacket decode(FriendlyByteBuf buf) {
        return new CompatibilitySyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<Compatibility> getCapability(Entity entity) {
        return Compatibility.get(entity);
    }
}
