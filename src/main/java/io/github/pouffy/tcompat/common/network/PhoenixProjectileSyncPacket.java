package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.capability.phoenix.PhoenixTouched;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import io.github.pouffy.tcompat.common.network.base.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Quartet;

public class PhoenixProjectileSyncPacket extends SyncEntityPacket<PhoenixTouched> {
    public PhoenixProjectileSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public PhoenixProjectileSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    public static PhoenixProjectileSyncPacket decode(FriendlyByteBuf buf) {
        return new PhoenixProjectileSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<PhoenixTouched> getCapability(Entity entity) {
        return PhoenixTouched.get((Projectile) entity);
    }
}
