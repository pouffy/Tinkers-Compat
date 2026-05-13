package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.capability.cooldown.ModifierCooldowns;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import io.github.pouffy.tcompat.common.network.base.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Quartet;

public class ModifierCooldownsSyncPacket extends SyncEntityPacket<ModifierCooldowns> {
    public ModifierCooldownsSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public ModifierCooldownsSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    public static ModifierCooldownsSyncPacket decode(FriendlyByteBuf buf) {
        return new ModifierCooldownsSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<ModifierCooldowns> getCapability(Entity entity) {
        return ModifierCooldowns.get((Player) entity);
    }
}
