package io.github.pouffy.tcompat.common.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.Optional;

//Basically just getting registered objects, so I don't have to risk refencing non-existing classes
@SuppressWarnings("deprecation")
public class ObjectRetriever {

    public static Optional<Item> getItem(String id) {
        return Optional.of(BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(id)));
    }

    public static Optional<Block> getBlock(String id) {
        return Optional.of(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(id)));
    }

    public static Optional<Fluid> getFluid(String id) {
        return Optional.of(BuiltInRegistries.FLUID.get(ResourceLocation.tryParse(id)));
    }

    public static boolean itemExists(String id) {
        return getItem(id).isPresent();
    }

    public static boolean blockExists(String id) {
        return getBlock(id).isPresent();
    }

    public static boolean fluidExists(String id) {
        return getFluid(id).isPresent();
    }
}
