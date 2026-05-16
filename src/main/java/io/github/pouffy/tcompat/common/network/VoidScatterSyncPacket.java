package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.capability.projectile.void_scatter.VoidScatter;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import io.github.pouffy.tcompat.common.network.base.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Quartet;

public class VoidScatterSyncPacket extends SyncEntityPacket<VoidScatter> {
    public VoidScatterSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public VoidScatterSyncPacket(int entityID, String key, INBTSynchable.Type type, Object value) {
        super(entityID, key, type, value);
    }

    public static VoidScatterSyncPacket decode(FriendlyByteBuf buf) {
        return new VoidScatterSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<VoidScatter> getCapability(Entity entity) {
        return VoidScatter.get((Projectile) entity);
    }
}
