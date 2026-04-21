package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.capability.compatible.LightningOwner;
import io.github.pouffy.tcompat.common.capability.vampire_healing.VampireHealing;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import io.github.pouffy.tcompat.common.network.base.SyncEntityPacket;
import io.github.pouffy.tcompat.common.network.base.SyncLivingEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Quartet;

public class VampireHealingSyncPacket extends SyncLivingEntityPacket<VampireHealing> {
    public VampireHealingSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public VampireHealingSyncPacket(int entityID, String key, INBTSynchable.Type type, Object value) {
        super(entityID, key, type, value);
    }

    public static VampireHealingSyncPacket decode(FriendlyByteBuf buf) {
        return new VampireHealingSyncPacket(SyncLivingEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<VampireHealing> getCapability(LivingEntity entity) {
        return VampireHealing.get(entity);
    }
}
