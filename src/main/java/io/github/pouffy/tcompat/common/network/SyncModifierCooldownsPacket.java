package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.cooldown.ClientModifierCooldowns;
import io.github.pouffy.tcompat.common.cooldown.CooldownInstance;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.ModifierId;

import java.util.Map;

public class SyncModifierCooldownsPacket implements BasePacket {
    private final Map<ModifierId, CooldownInstance> modifierCooldowns;

    public SyncModifierCooldownsPacket(Map<ModifierId, CooldownInstance> modifierCooldowns) {
        this.modifierCooldowns = modifierCooldowns;
    }

    public static ModifierId readModifierID(FriendlyByteBuf buffer) {
        return ModifierId.tryParse(buffer.readUtf());
    }

    public static CooldownInstance readCoolDownInstance(FriendlyByteBuf buffer) {
        int startCooldown = buffer.readInt();
        int spellCooldownRemaining = buffer.readInt();
        return new CooldownInstance(startCooldown, spellCooldownRemaining);
    }

    public static void writeModifierId(FriendlyByteBuf buf, ModifierId modifierId) {
        buf.writeUtf(modifierId.toString());
    }

    public static void writeCoolDownInstance(FriendlyByteBuf buf, CooldownInstance cooldownInstance) {
        buf.writeInt(cooldownInstance.getStartCooldown());
        buf.writeInt(cooldownInstance.getCooldownRemaining());
    }

    public static SyncModifierCooldownsPacket decode(FriendlyByteBuf buf) {
        var modifierCooldowns = buf.readMap(SyncModifierCooldownsPacket::readModifierID, SyncModifierCooldownsPacket::readCoolDownInstance);
        return new SyncModifierCooldownsPacket(modifierCooldowns);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeMap(modifierCooldowns, SyncModifierCooldownsPacket::writeModifierId, SyncModifierCooldownsPacket::writeCoolDownInstance);
    }

    @Override
    public void execute(Player player) {
        var cooldowns = ClientModifierCooldowns.getCooldowns();
        cooldowns.clearCooldowns();
        this.modifierCooldowns.forEach((k, v) -> {
            cooldowns.addCooldown(k, v.getStartCooldown(), v.getCooldownRemaining());
        });
    }
}
