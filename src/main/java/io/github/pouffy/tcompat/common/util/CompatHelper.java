package io.github.pouffy.tcompat.common.util;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.ad_astra.AdAstraInit;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRInit;
import io.github.pouffy.tcompat.compat.ancient_aether.AncientAetherInit;
import io.github.pouffy.tcompat.compat.betterend.BetterendInit;
import io.github.pouffy.tcompat.compat.betternether.BetternetherInit;
import io.github.pouffy.tcompat.compat.cataclysm.CataclysmInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import io.github.pouffy.tcompat.compat.malum.MalumInit;
import io.github.pouffy.tcompat.compat.species.SpeciesInit;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.Level;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CompatHelper {

    private static final Map<String, Consumer<IEventBus>> compatInitializers = new HashMap<>();
    private static final Map<String, Consumer<IEventBus>> compatEvents = new HashMap<>();
    public static final Map<String, BiConsumer<CreativeModeTab.ItemDisplayParameters, CreativeModeTab.Output>> compatItems = new HashMap<>();

    static {
        compatInitializers.put("ad_astra", AdAstraInit::init);
        compatInitializers.put("aether", AetherInit::init);
        compatInitializers.put("aether_redux", AetherReduxInit::init);
        compatInitializers.put("aether_treasure_reforging", AetherTRInit::init);
        compatInitializers.put("ancient_aether", AncientAetherInit::init);
        compatInitializers.put("betterend", BetterendInit::init);
        compatInitializers.put("betternether", BetternetherInit::init);
        compatInitializers.put("cataclysm", CataclysmInit::init);
        compatInitializers.put("deep_aether", DeepAetherInit::init);
        compatInitializers.put("iceandfire", IFInit::init);
        compatInitializers.put("malum", MalumInit::init);
        compatInitializers.put("species", SpeciesInit::init);


        compatEvents.put("ad_astra", (bus) -> bus.register(new AdAstraInit()));
        compatEvents.put("aether", (bus) -> bus.register(new AetherInit()));
        compatEvents.put("aether_redux", (bus) -> bus.register(new AetherReduxInit()));
        compatEvents.put("aether_treasure_reforging", (bus) -> bus.register(new AetherTRInit()));
        compatEvents.put("betterend", (bus) -> bus.register(new BetterendInit()));
        compatEvents.put("betternether", (bus) -> bus.register(new BetternetherInit()));
        compatEvents.put("cataclysm", (bus) -> bus.register(new CataclysmInit()));
        compatEvents.put("deep_aether", (bus) -> bus.register(new DeepAetherInit()));
        compatEvents.put("iceandfire", (bus) -> bus.register(new IFInit()));
        compatEvents.put("malum", (bus) -> bus.register(new MalumInit()));

        compatItems.put("aether", AetherInit::addCommonTabItems);
        compatItems.put("deep_aether", DeepAetherInit::addCommonTabItems);
        compatItems.put("iceandfire", IFInit::addCommonTabItems);
    }

    public static void init(IEventBus eventBus) {
        //Compatibility needs to be initialised during datagen so our fluids are recognised.
        compatInitializers.forEach((mod, consumer) -> {
            if (CompatHelper.isLoaded(mod))
                consumer.accept(eventBus);
        });
        GlobalInit.init(eventBus);
        //Initialising the event bus for these does not matter during datagen.
        compatEvents.forEach((mod, consumer) -> {
            if (CompatHelper.isLoaded(mod))
                consumer.accept(eventBus);
        });
        eventBus.register(new GlobalInit());
    }

    public static boolean isLoaded(String namespace) {
        return ModList.get().isLoaded(namespace) || DatagenModLoader.isRunningDataGen();
    }

    public static ResourceKey<DamageType> damageKey(String location) {
        return resourceKey(Registries.DAMAGE_TYPE, location);
    }

    public static <T> ResourceKey<T> resourceKey(ResourceKey<? extends Registry<T>> reg, String location) {
        return ResourceKey.create(reg, TCompat.getResource(location));
    }

    public static Entity getEntityByUUID(Level level, UUID uuid) {
        if (level instanceof ServerLevel serverLevel) {
            for (var entity : serverLevel.getEntities().getAll()) {
                if (uuid.equals(entity.getUUID())) {
                    return entity;
                }
            }
        }
        return null;
    }
}
