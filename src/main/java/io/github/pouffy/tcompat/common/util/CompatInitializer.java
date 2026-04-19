package io.github.pouffy.tcompat.common.util;

import io.github.pouffy.tcompat.TCompat;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

import java.util.Optional;
import java.util.function.Supplier;

import static io.github.pouffy.tcompat.TCompat.getResource;
import static slimeknights.mantle.Mantle.commonResource;

public class CompatInitializer {

    protected static ItemObject<ModifiableItem> toolForMod(String modid, String id, ToolDefinition definition, ItemDeferredRegister register) {
        ItemObject<ModifiableItem> tool;
        if (ModList.get().isLoaded(modid)) {
            tool = register.register(id, () -> new ModifiableItem(new Item.Properties().stacksTo(1), definition));
        } else {
            tool = new ItemObject<>(RegistryObject.create(getResource(id), ForgeRegistries.ITEMS));
        }
        return tool;
    }

    protected static <T extends Item> ItemObject<T> itemForMod(String modid, String id, Supplier<T> constructor, ItemDeferredRegister register) {
        ItemObject<T> item;
        if (ModList.get().isLoaded(modid)) {
            item = register.register(id, constructor);
        } else {
            item = new ItemObject<>(RegistryObject.create(getResource(id), ForgeRegistries.ITEMS));
        }
        return item;
    }

    protected static ItemObject<Item> itemForMod(String modid, String id, Item.Properties properties, ItemDeferredRegister register) {
        return itemForMod(modid, id, () -> new Item(properties), register);
    }

    private static final int MOLTEN_LENGTH = "molten_".length();

    protected static String withoutMolten(FluidObject<?> fluid) {
        return fluid.getId().getPath().substring(MOLTEN_LENGTH);
    }

    /** Accepts the given item if the passed tag has items */
    protected static boolean acceptIfTag(CreativeModeTab.Output output, ItemLike item, CreativeModeTab.TabVisibility visibility, TagKey<Item> tagCondition) {
        Optional<HolderSet.Named<Item>> tag = BuiltInRegistries.ITEM.getTag(tagCondition);
        if (tag.isPresent() && tag.get().size() > 0) {
            output.accept(item, visibility);
            return true;
        }
        return false;
    }

    /** Accepts the given item if the passed tag has items */
    protected static boolean acceptIfTag(CreativeModeTab.Output output, ItemLike item, TagKey<Item> tagCondition) {
        return acceptIfTag(output, item, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tagCondition);
    }

    private static FluidType.Properties cool() {
        return FluidType.Properties.create()
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .motionScale(0.0023333333333333335D)
                .canExtinguish(true);

    }

    private static FluidType.Properties cool(String name) {
        return cool().descriptionId(TCompat.makeDescriptionId("fluid", name))
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY);
    }

    protected static FluidType.Properties slime(String name) {
        return cool(name).density(1600).viscosity(1600);
    }
}
