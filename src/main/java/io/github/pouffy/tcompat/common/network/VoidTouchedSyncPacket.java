package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.capability.void_touched.VoidTouched;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import io.github.pouffy.tcompat.common.network.base.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Quartet;

public class VoidTouchedSyncPacket extends SyncEntityPacket<VoidTouched> {
    public VoidTouchedSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public VoidTouchedSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    public static VoidTouchedSyncPacket decode(FriendlyByteBuf buf) {
        return new VoidTouchedSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<VoidTouched> getCapability(Entity entity) {
        return VoidTouched.get((LivingEntity) entity);
    }
}
