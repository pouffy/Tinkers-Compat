package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.cooldown.ClientModifierCooldowns;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.ModifierId;

public class SyncModifierCooldownPacket implements BasePacket {
    private final ModifierId modifierId;
    private final int duration;

    public SyncModifierCooldownPacket(ModifierId modifierId, int duration) {
        this.modifierId = modifierId;
        this.duration = duration;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(this.modifierId.toString());
        buf.writeInt(this.duration);
    }

    public static SyncModifierCooldownPacket decode(FriendlyByteBuf buf) {
        var modifierId = ModifierId.tryParse(buf.readUtf());
        var duration = buf.readInt();
        return new SyncModifierCooldownPacket(modifierId, duration);
    }

    @Override
    public void execute(Player player) {
        ClientModifierCooldowns.getCooldowns().addCooldown(modifierId, duration);
    }
}
