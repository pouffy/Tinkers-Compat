package io.github.pouffy.tcompat.common;

import io.github.pouffy.tcompat.TCompat;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.mantle.registration.object.BuildingBlockObject;
import slimeknights.mantle.registration.object.EnumObject;
import slimeknights.tconstruct.common.registration.BlockDeferredRegisterExtension;
import slimeknights.tconstruct.common.registration.FluidDeferredRegisterExtension;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import java.util.Optional;

public class CompatModule {
    protected static final BlockDeferredRegisterExtension BLOCKS = new BlockDeferredRegisterExtension(TCompat.MOD_ID);
    protected static final ItemDeferredRegisterExtension ITEMS = new ItemDeferredRegisterExtension(TCompat.MOD_ID);
    protected static final FluidDeferredRegisterExtension FLUIDS = new FluidDeferredRegisterExtension(TCompat.MOD_ID);

    public static void initRegisters(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        FLUIDS.register(bus);
    }

    protected static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String name) {
        return ResourceKey.create(registry, TCompat.getResource(name));
    }

    protected static void accept(CreativeModeTab.Output output, EnumObject<?, ? extends ItemLike> items, CreativeModeTab.TabVisibility visibility) {
        items.forEach((item) -> output.accept(item, visibility));
    }

    protected static void accept(CreativeModeTab.Output output, EnumObject<?, ? extends ItemLike> items) {
        accept(output, items, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    protected static void accept(CreativeModeTab.Output output, BuildingBlockObject object, CreativeModeTab.TabVisibility visibility) {
        object.forEach((item) -> output.accept(item, visibility));
    }

    protected static void accept(CreativeModeTab.Output output, BuildingBlockObject object) {
        accept(output, object, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    protected static boolean acceptIfTag(CreativeModeTab.Output output, ItemLike item, CreativeModeTab.TabVisibility visibility, TagKey<Item> tagCondition) {
        Optional<HolderSet.Named<Item>> tag = BuiltInRegistries.ITEM.getTag(tagCondition);
        if (tag.isPresent() && tag.get().size() > 0) {
            output.accept(item, visibility);
            return true;
        } else {
            return false;
        }
    }

    protected static boolean acceptIfTag(CreativeModeTab.Output output, ItemLike item, TagKey<Item> tagCondition) {
        return acceptIfTag(output, item, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tagCondition);
    }

    protected static boolean acceptIfMaterial(CreativeModeTab.Output output, ItemLike item, MaterialId material) {
        if (MaterialRegistry.getMaterial(material) != IMaterial.UNKNOWN) {
            output.accept(item);
            return true;
        } else {
            return false;
        }
    }

    protected static boolean acceptIfItem(CreativeModeTab.Output output, ItemLike item, ResourceLocation owner) {
        if (BuiltInRegistries.ITEM.containsKey(owner)) {
            output.accept(item);
            return true;
        } else {
            return false;
        }
    }
}
