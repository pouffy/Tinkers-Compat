package io.github.pouffy.tcompat.common.data.variable;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.ArrayList;
import java.util.List;

public record GameEventEntry(List<Either<GameEvent, TagKey<GameEvent>>> events) {
    static ExtraCodecs.EitherCodec<GameEvent, TagKey<GameEvent>> FIELD_CODEC = new ExtraCodecs.EitherCodec<>(BuiltInRegistries.GAME_EVENT.byNameCodec(), TagKey.codec(Registries.GAME_EVENT));

    static Codec<GameEventEntry> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            FIELD_CODEC.listOf().fieldOf("event").forGetter(GameEventEntry::events)
    ).apply(instance, GameEventEntry::new));

    public boolean matches(GameEvent event) {
        for (var either : events) {
            boolean tag = either.mapRight(event::is).right().orElse(false);
            if (tag) return true;
            boolean direct = either.mapLeft(event::equals).left().orElse(false);
            if (direct) return true;
        }
        return false;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        final List<Either<GameEvent, TagKey<GameEvent>>> events = new ArrayList<>();

        public Builder direct(GameEvent... events) {
            List<Either<GameEvent, TagKey<GameEvent>>> list = new ArrayList<>();
            for (var event : events) {
                list.add(Either.left(event));
            }
            this.events.addAll(list);
            return this;
        }

        @SafeVarargs
        public final Builder tagged(TagKey<GameEvent>... tags) {
            List<Either<GameEvent, TagKey<GameEvent>>> list = new ArrayList<>();
            for (var event : tags) {
                list.add(Either.right(event));
            }
            this.events.addAll(list);
            return this;
        }

        public GameEventEntry build() {
            return new GameEventEntry(this.events);
        }
    }
}
