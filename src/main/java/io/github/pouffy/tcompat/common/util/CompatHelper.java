package io.github.pouffy.tcompat.common.util;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.capability.cooldown.ModifierCooldowns;
import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.ad_astra.AdAstraInit;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRInit;
import io.github.pouffy.tcompat.compat.betterend.BetterendInit;
import io.github.pouffy.tcompat.compat.betternether.BetternetherInit;
import io.github.pouffy.tcompat.compat.cataclysm.CataclysmInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import io.github.pouffy.tcompat.compat.species.SpeciesInit;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.tools.stats.*;

import java.util.Map;
import java.util.function.Consumer;

import static net.minecraft.world.item.Tiers.STONE;
import static net.minecraft.world.item.Tiers.WOOD;

public class CompatHelper {

    private static final Map<String, Consumer<IEventBus>> compatInitializers = Map.of(
            "aether", AetherInit::init,
            "aether_redux", AetherReduxInit::init,
            "deep_aether", DeepAetherInit::init,
            "aether_treasure_reforging", AetherTRInit::init,
            "species", SpeciesInit::init,
            "ad_astra", AdAstraInit::init,
            "betterend", BetterendInit::init,
            "betternether", BetternetherInit::init,
            "iceandfire", IFInit::init,
            "cataclysm", CataclysmInit::init
    );
    private static final Map<String, Consumer<IEventBus>> compatEvents = Map.of(
            "aether", (bus) -> bus.register(new AetherInit()),
            "aether_redux", (bus) -> bus.register(new AetherReduxInit()),
            "deep_aether", (bus) -> bus.register(new DeepAetherInit()),
            "aether_treasure_reforging", (bus) -> bus.register(new AetherTRInit()),
            "ad_astra", (bus) -> bus.register(new AdAstraInit()),
            "betterend", (bus) -> bus.register(new BetterendInit()),
            "betternether", (bus) -> bus.register(new BetternetherInit()),
            "iceandfire", (bus) -> bus.register(new IFInit()),
            "cataclysm", (bus) -> bus.register(new CataclysmInit())
    );

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

    /**
     * Only run if the stack is a tool.
     * @param stack the ItemStack being used.
     * @param consumer the method  to run.
     */
    public static void asTool(ItemStack stack, Consumer<ToolStack> consumer) {
        if (stack.getItem() instanceof IModifiable) {
            consumer.accept(ToolStack.from(stack));
        }
    }

    public static void sendCooldownMessage(Player player, ModifierEntry modifierEntry) {
        if (ModifierCooldowns.isOnCooldown(modifierEntry.getId(), player)) {
            Component message = Component.translatable("notification.tcompat.modifier_cooldown", modifierEntry.getModifier().getDisplayName());
            player.displayClientMessage(message, true);
        }
    }

    //Never datagenerated. Just used as fallbacks. Will always fallback to wood if the parent isn't defined here
    public static MaterialBuilder woodStatsBuilder = MaterialBuilder.material("tconstruct", "wood")
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(60, 2f, WOOD, 0f),
                            HandleMaterialStats.percents().build(), // flat all around
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(60, 0, 0, 0),
                            new GripMaterialStats(0f, 0, 0),
                            StatlessMaterialStats.ARROW_SHAFT,
                            StatlessMaterialStats.SHIELD_CORE
                    )
            );

    public static MaterialBuilder rockStatsBuilder = MaterialBuilder.material("tconstruct", "rock")
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(130, 4f, STONE, 1f),
                            HandleMaterialStats.multipliers().durability(0.9f).miningSpeed(1.05f).build(),
                            StatlessMaterialStats.BINDING
                    )
            );

    public static MaterialBuilder whitestoneStatsBuilder = MaterialBuilder.material("tconstruct", "whitestone")
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(275, 6.0F, Tiers.IRON, 1.25F),
                            HandleMaterialStats.multipliers().durability(0.95F).miningSpeed(1.1F).attackSpeed(0.95F).build(),
                            StatlessMaterialStats.BINDING
                    )
            );
}
