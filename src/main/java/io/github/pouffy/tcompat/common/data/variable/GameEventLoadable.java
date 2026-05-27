package io.github.pouffy.tcompat.common.data.variable;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.FriendlyByteBuf;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.util.typed.TypedMap;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault @MethodsReturnNonnullByDefault
public enum GameEventLoadable implements RecordLoadable<GameEventEntry> {
    INSTANCE;

    @Override
    public GameEventEntry deserialize(JsonObject jsonObject, TypedMap typedMap) {
        return GameEventEntry.CODEC.decode(JsonOps.INSTANCE, jsonObject.get("events")).result().map(Pair::getFirst).orElse(GameEventEntry.builder().build());
    }

    @Override
    public void serialize(GameEventEntry entries, JsonObject jsonObject) {
        GameEventEntry.CODEC.encodeStart(JsonOps.INSTANCE, entries).result().ifPresent(encoded -> jsonObject.add("events", encoded));
    }

    @Override
    public GameEventEntry decode(FriendlyByteBuf friendlyByteBuf, TypedMap typedMap) {
        return friendlyByteBuf.readJsonWithCodec(GameEventEntry.CODEC);
    }

    @Override
    public void encode(FriendlyByteBuf friendlyByteBuf, GameEventEntry entries) {
        friendlyByteBuf.writeJsonWithCodec(GameEventEntry.CODEC, entries);
    }
}
