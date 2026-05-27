package io.github.pouffy.tcompat.common.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.gameevent.GameEvent;
import slimeknights.mantle.data.loadable.common.RegistryLoadable;
import slimeknights.mantle.data.loadable.primitive.ResourceLocationLoadable;
import slimeknights.mantle.data.loadable.primitive.StringLoadable;

import static slimeknights.mantle.data.loadable.Loadables.tagKey;

public class TCLoadables {
    public static final ResourceLocationLoadable<GameEvent> GAME_EVENT = new RegistryLoadable<>(BuiltInRegistries.GAME_EVENT);
    public static final StringLoadable<TagKey<GameEvent>> GAME_EVENT_TAG = tagKey(Registries.GAME_EVENT);
}
