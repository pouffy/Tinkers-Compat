package io.github.pouffy.tcompat.common.data.variable;

import com.google.gson.JsonObject;
import io.github.pouffy.tcompat.common.data.TCLoadables;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.gameevent.GameEvent;
import slimeknights.mantle.data.loadable.mapping.ListLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;

import java.util.ArrayList;
import java.util.List;

public record GameEventEntry(List<Partial> events) {
    public static final GameEventEntry EMPTY = new GameEventEntry(List.of());
    public static final RecordLoadable<GameEventEntry> LOADABLE = RecordLoadable.create(new ListLoadable<>(Partial.LOADER, 0).requiredField("events", GameEventEntry::events), GameEventEntry::new);

    public boolean matches(GameEvent event) {
        for (var partial : events) {
            if (partial.matches(event)) return true;
        }
        return false;
    }

    public JsonObject serialize(JsonObject json) {
        LOADABLE.serialize(this, json);
        return json;
    }

    public static GameEventEntry deserialize(JsonObject json) {
        return LOADABLE.deserialize(json);
    }

    public void toNetwork(FriendlyByteBuf buffer) {
        LOADABLE.encode(buffer, this);
    }

    public static GameEventEntry fromNetwork(FriendlyByteBuf buffer) {
        return LOADABLE.decode(buffer);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        final List<Partial> events = new ArrayList<>();

        public Builder direct(GameEvent... events) {
            List<Partial> list = new ArrayList<>();
            for (var event : events) {
                list.add(new Partial(event, null));
            }
            this.events.addAll(list);
            return this;
        }

        @SafeVarargs
        public final Builder tagged(TagKey<GameEvent>... tags) {
            List<Partial> list = new ArrayList<>();
            for (var tag : tags) {
                list.add(new Partial(null, tag));
            }
            this.events.addAll(list);
            return this;
        }

        public GameEventEntry build() {
            return new GameEventEntry(this.events);
        }
    }

    record Partial(GameEvent event, TagKey<GameEvent> tag) {
        public static final RecordLoadable<Partial> LOADER = RecordLoadable.create(TCLoadables.GAME_EVENT.nullableField("event", Partial::event), TCLoadables.GAME_EVENT_TAG.nullableField("tag", Partial::tag), Partial::new);

        public boolean matches(GameEvent event) {
            if (this.tag != null) {
                return event.is(this.tag);
            }
            if (this.event != null) {
                return event.equals(this.event);
            }
            return false;
        }
    }
}
