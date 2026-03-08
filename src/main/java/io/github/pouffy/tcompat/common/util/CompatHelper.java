package io.github.pouffy.tcompat.common.util;

import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.Map;
import java.util.function.Consumer;

public class CompatHelper {

    private static Map<String, Consumer<IEventBus>> compatInitializers = Map.of(
            "aether", AetherInit::init,
            "aether_redux", AetherReduxInit::init,
            "deep_aether", DeepAetherInit::init
    );
    private static Map<String, Consumer<IEventBus>> compatEvents = Map.of(
            "aether", (bus) -> bus.register(new AetherInit()),
            "aether_redux", (bus) -> bus.register(new AetherReduxInit()),
            "deep_aether", (bus) -> bus.register(new DeepAetherInit())
    );

    public static void init(IEventBus eventBus) {
        compatInitializers.forEach((mod, consumer) -> {
            if (CompatHelper.isLoaded(mod) || DatagenModLoader.isRunningDataGen())
                consumer.accept(eventBus);
        });
        compatEvents.forEach((mod, consumer) -> {
            if (CompatHelper.isLoaded(mod))
                consumer.accept(eventBus);
        });
    }

    public static boolean isLoaded(String namespace) {
        return ModList.get().isLoaded(namespace);
    }
}
