package io.github.pouffy.tcompat.common.util;

import io.github.pouffy.tcompat.TCompat;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.Optional;

//Basically just getting registered objects, so I don't have to risk refencing non-existing classes
@SuppressWarnings("deprecation")
public class ObjectRetriever {

    // Registered Objects
    public static Optional<Item> getItem(String id) {
        return get(BuiltInRegistries.ITEM, id);
    }

    public static Optional<Block> getBlock(String id) {
        return get(BuiltInRegistries.BLOCK, id);
    }

    public static Optional<Fluid> getFluid(String id) {
        return get(BuiltInRegistries.FLUID, id);
    }

    public static Optional<MobEffect> getEffect(String id) {
        return get(BuiltInRegistries.MOB_EFFECT, id);
    }

    public static Optional<SoundEvent> getSound(String id) {
        return get(BuiltInRegistries.SOUND_EVENT, id);
    }

    public static Optional<EntityType<?>> getEntity(String id) {
        return get(BuiltInRegistries.ENTITY_TYPE, id);
    }

    // Resource Keys
    public static ResourceKey<DamageType> damageKey(String location) {
        return resourceKey(Registries.DAMAGE_TYPE, location);
    }

    // Registered Objects Checkers
    public static boolean itemExists(String id) {
        return getItem(id).isPresent();
    }

    public static boolean blockExists(String id) {
        return getBlock(id).isPresent();
    }

    public static boolean fluidExists(String id) {
        return getFluid(id).isPresent();
    }

    public static boolean effectExists(String id) {
        return getEffect(id).isPresent();
    }

    public static boolean soundExists(String id) {
        return getSound(id).isPresent();
    }

    public static boolean entityExists(String id) {
        return getEntity(id).isPresent();
    }

    // Helpers
    public static <T> ResourceKey<T> resourceKey(ResourceKey<? extends Registry<T>> reg, String location) {
        return ResourceKey.create(reg, TCompat.getResource(location));
    }

    public static <T> Optional<T> get(Registry<T> registry, String id) {
        return Optional.ofNullable(registry.get(ResourceLocation.tryParse(id)));
    }
}
