package io.github.pouffy.tcompat.common.cooldown;

import com.google.common.collect.Maps;
import io.github.pouffy.tcompat.common.network.SyncModifierCooldownsPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.base.PacketRelay;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierId;

import java.util.Map;

public class ModifierCooldowns {
    @Getter
    private final Map<ModifierId, CooldownInstance> modifierCooldowns;
    //This is used to deal with the client and server tickPlayers's not in sync so
    // the client has a little grace period so it's remove doesn't happen before the server's
    @Setter
    private int tickBuffer = 0;

    public ModifierCooldowns() {
        this.modifierCooldowns = Maps.newHashMap();
    }

    public void tick(int actualTicks) {
        var modifiers = modifierCooldowns.entrySet().stream().filter(entry -> decrementCooldown(entry.getValue(), actualTicks)).toList();
        modifiers.forEach(modifier -> modifierCooldowns.remove(modifier.getKey()));
    }

    public boolean hasCooldownsActive() {
        return !modifierCooldowns.isEmpty();
    }

    public boolean removeCooldown(ModifierId modifierId) {
        return modifierCooldowns.remove(modifierId) != null;
    }

    public void clearCooldowns() {
        modifierCooldowns.clear();
    }

    public void addCooldown(ModifierId modifierId, int durationTicks) {
        modifierCooldowns.put(modifierId, new CooldownInstance(durationTicks));
    }

    public void addCooldown(ModifierId modifierId, int durationTicks, int remaining) {
        modifierCooldowns.put(modifierId, new CooldownInstance(durationTicks, remaining));
    }

    public boolean isOnCooldown(ModifierId modifierId) {
        return modifierCooldowns.containsKey(modifierId);
    }

    public float getCooldownPercent(ModifierId modifierId) {
        return modifierCooldowns.getOrDefault(modifierId, new CooldownInstance(0)).getCooldownPercent();
    }

    public boolean decrementCooldown(CooldownInstance c, int amount) {
        c.decrementBy(amount);
        return c.getCooldownRemaining() <= tickBuffer;
    }

    public void syncToPlayer(ServerPlayer serverPlayer) {
        PacketRelay.sendToPlayer(TCompatNetworking.INSTANCE, new SyncModifierCooldownsPacket(this.modifierCooldowns), serverPlayer);
    }

    public static ModifierCooldowns getModifierCooldowns(LivingEntity livingEntity) {
        return ((IModifierCooldownHolder)livingEntity).tcompat$getCooldowns();
    }

    public ListTag saveNBTData() {
        var listTag = new ListTag();
        modifierCooldowns.forEach((modifierId, cooldown) -> {
            if (cooldown.getCooldownRemaining() > 0) {
                CompoundTag ct = new CompoundTag();
                ct.putString("id", modifierId.toString());
                ct.putInt("start", cooldown.getStartCooldown());
                ct.putInt("remaining", cooldown.getCooldownRemaining());
                listTag.add(ct);
            }
        });
        return listTag;
    }

    public void loadNBTData(ListTag listTag) {
        if (listTag != null) {
            listTag.forEach(tag -> {
                CompoundTag t = (CompoundTag) tag;
                ModifierId modifierId = ModifierId.tryParse(t.getString("id"));
                int modifierCooldown = t.getInt("start");
                int cooldownRemaining = t.getInt("remaining");
                modifierCooldowns.put(modifierId, new CooldownInstance(modifierCooldown, cooldownRemaining));
            });
        }
    }
}
