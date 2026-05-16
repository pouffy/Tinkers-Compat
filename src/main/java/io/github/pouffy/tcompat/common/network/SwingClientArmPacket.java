package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.network.base.BasePacket;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;

public class SwingClientArmPacket implements BasePacket {
    private final int entityId;
    private final InteractionHand hand;

    public SwingClientArmPacket(int entityId, InteractionHand hand) {
        this.entityId = entityId;
        this.hand = hand;
    }

    public SwingClientArmPacket(Entity entity, InteractionHand hand) {
        this(entity.getId(), hand);
    }

    public static SwingClientArmPacket decode(FriendlyByteBuf buf) {
        var entityId = buf.readVarInt();
        var hand = buf.readEnum(InteractionHand.class);
        return new SwingClientArmPacket(entityId, hand);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeVarInt(this.entityId);
        buf.writeEnum(this.hand);
    }

    @Override
    public void execute(Player player) {
        if (player != null) {
            ItemStack leftItem = player.getItemInHand(InteractionHand.OFF_HAND);
            ItemStack rightItem = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompatHelper.asTool(leftItem, (tool) -> {
                for (ModifierEntry entry : tool.getModifiers()) {
                    entry.getHook(GlobalInit.TOOL_SWING).swingOffHand(tool, entry, leftItem, player);
                }
            });
            CompatHelper.asTool(rightItem, (tool) -> {
                for (ModifierEntry entry : tool.getModifiers()) {
                    entry.getHook(GlobalInit.TOOL_SWING).swingMainHand(tool, entry, rightItem, player);
                }
            });
        }
    }
}
