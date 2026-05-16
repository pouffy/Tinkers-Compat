package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.capability.projectile.leeching.Leeching;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import io.github.pouffy.tcompat.common.network.base.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Quartet;

public class LeechingSyncPacket extends SyncEntityPacket<Leeching> {
    public LeechingSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public LeechingSyncPacket(int entityID, String key, INBTSynchable.Type type, Object value) {
        super(entityID, key, type, value);
    }

    public static LeechingSyncPacket decode(FriendlyByteBuf buf) {
        return new LeechingSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<Leeching> getCapability(Entity entity) {
        return Leeching.get((Projectile) entity);
    }
}
