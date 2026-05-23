package io.github.pouffy.tcompat.common.cooldown;

import io.github.pouffy.tcompat.common.network.SyncModifierCooldownPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.base.PacketRelay;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierId;

public class CooldownHandler {

    public void tickPlayers(Level level) {
        level.players().stream().toList().forEach((player) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                ModifierCooldowns.getModifierCooldowns(serverPlayer).tick(1);
            }
        });
    }

    public void tickEntity(LivingEntity living) {
        if (!(living instanceof ServerPlayer)) {
            ModifierCooldowns.getModifierCooldowns(living).tick(1);
        }
    }

    public void addCooldown(LivingEntity living, ModifierId modifierId, int duration) {
        ModifierCooldowns.getModifierCooldowns(living).addCooldown(modifierId, duration);
        if (living instanceof ServerPlayer serverPlayer) {
            PacketRelay.sendToPlayer(TCompatNetworking.INSTANCE, new SyncModifierCooldownPacket(modifierId, duration), serverPlayer);
        }
    }

    public void clearCooldowns(LivingEntity living) {
        ModifierCooldowns.getModifierCooldowns(living).clearCooldowns();
        if (living instanceof ServerPlayer serverPlayer) {
            ModifierCooldowns.getModifierCooldowns(serverPlayer).syncToPlayer(serverPlayer);
        }
    }
}
