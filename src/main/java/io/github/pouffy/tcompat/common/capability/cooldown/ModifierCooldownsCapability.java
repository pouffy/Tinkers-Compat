package io.github.pouffy.tcompat.common.capability.cooldown;

import com.google.common.collect.Maps;
import io.github.pouffy.tcompat.common.network.ModifierCooldownsSyncPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;
import slimeknights.tconstruct.library.modifiers.ModifierId;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModifierCooldownsCapability implements ModifierCooldowns {
    private final Player player;
    private final Map<ModifierId, CooldownInstance> cooldowns = Maps.newHashMap();
    private int tickCount;

    public ModifierCooldownsCapability(Player player) {
        this.player = player;
    }

    @Override
    public Player getEntity() {
        return this.player;
    }

    @Override
    public Map<ModifierId, CooldownInstance> getCooldowns() {
        return this.cooldowns;
    }

    @Override
    public int getTickCount() {
        return this.tickCount;
    }

    @Override
    public void tick() {
        ++this.tickCount;
        if (!this.cooldowns.isEmpty()) {
            this.cooldowns.entrySet().removeIf(entry -> entry.getValue().endTime <= this.tickCount);
        }
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return Map.of();
    }

    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new ModifierCooldownsSyncPacket(this.getEntity().getId(), key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return TCompatNetworking.INSTANCE;
    }

    @Override
    public CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {

    }
}
