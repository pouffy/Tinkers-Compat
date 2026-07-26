package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.capability.living.cryogenic.Cryogenic;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import io.github.pouffy.tcompat.common.network.base.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Quartet;

public class CryogenicSyncPacket extends SyncEntityPacket<Cryogenic> {
    public CryogenicSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public CryogenicSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    public static CryogenicSyncPacket decode(FriendlyByteBuf buf) {
        return new CryogenicSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<Cryogenic> getCapability(Entity entity) {
        return Cryogenic.get((LivingEntity) entity);
    }
}
