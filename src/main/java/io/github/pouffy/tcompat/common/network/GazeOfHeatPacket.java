package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.network.base.BasePacket;
import io.github.pouffy.tcompat.compat.cataclysm.CataclysmHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class GazeOfHeatPacket implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {

    }

    @Override
    public void execute(Player player) {
        CataclysmHandler.gazeOfHeatUse(player);
    }
}
