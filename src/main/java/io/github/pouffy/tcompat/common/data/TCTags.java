package io.github.pouffy.tcompat.common.data;


import lombok.Getter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialManager;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierManager;

import static io.github.pouffy.tcompat.TCompat.getResource;
import static slimeknights.mantle.Mantle.commonResource;

public class TCTags {
    @Getter
    static boolean tagsLoaded = false;

    public static void init() {
        Blocks.init();
        Items.init();
        Fluids.init();
        Materials.init();
        Modifiers.init();

        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, TagsUpdatedEvent.class, event -> tagsLoaded = true);
    }

    @SuppressWarnings("SameParameterValue")
    private static <R> TagKey<R> hiddenFromRecipeViewers(ResourceKey<? extends Registry<R>> registry) {
        return TagKey.create(registry, ResourceLocation.fromNamespaceAndPath("c", "hidden_from_recipe_viewers"));
    }

    public static class Blocks {
        private static void init() {
        }

        public enum Woods {
            //BWG, BOP, TC
            ASPEN, BAOBAB, BLUE_ENCHANTED, CIKA, CYPRESS,
            EBONY, FIR, FLORUS, GREEN_ENCHANTED, HOLLY, IRONWOOD,
            JACARANDA, MAHOGANY, MAPLE, PALM, PALO_VERDE,
            PINE, RAINBOW_EUCALYPTUS, REDWOOD, SAKURA, SKYRIS,
            WHITE_MANGROVE, WILLOW, WITCH_HAZEL, ZELKOVA,

            //Aether
            SKYROOT, ROSEROOT, YAGROOT,
            CRUDEROOT, CONBERRY, SUNROOT,
            ;

            public TagKey<Block> plankTag() {
                return local(this.name().toLowerCase() + "_planks");
            }
            public TagKey<Block> logTag() {
                return local(this.name().toLowerCase() + "_logs");
            }
            public static TagKey<Block> plankTag(String name) {
                return local(name + "_planks");
            }
            public static TagKey<Block> logTag(String name) {
                return local(name + "_logs");
            }
        }

        private static TagKey<Block> local(String name) {
            return TagKey.create(Registries.BLOCK, getResource(name));
        }

        private static TagKey<Block> common(String name) {
            return TagKey.create(Registries.BLOCK, commonResource(name));
        }
    }

    public static class Items {
        private static void init() {
        }

        public static TagKey<Item> ZANITE_GEMS  = common("gems/zanite");
        public static TagKey<Item> ZANITE_BLOCKS  = common("storage_blocks/zanite");
        public static TagKey<Item> ZANITE_ORES  = common("ores/zanite");
        public static TagKey<Item> SKYJADE_GEMS  = common("gems/skyjade");
        public static TagKey<Item> SKYJADE_BLOCKS  = common("storage_blocks/skyjade");
        public static TagKey<Item> SKYJADE_ORES  = common("ores/skyjade");
        public static TagKey<Item> GRAVITITE_INGOTS  = common("ingots/gravitite");
        public static TagKey<Item> GRAVITITE_BLOCKS  = common("storage_blocks/gravitite");
        public static TagKey<Item> GRAVITITE_ORES  = common("ores/gravitite");
        public static TagKey<Item> GRAVITITE_RAW_ORES  = common("raw_materials/gravitite");
        public static TagKey<Item> GRAVITITE_RAW_BLOCKS  = common("storage_blocks/raw_gravitite");
        public static TagKey<Item> VERIDIUM_INGOTS  = common("ingots/veridium");
        public static TagKey<Item> VERIDIUM_NUGGETS  = common("nuggets/veridium");
        public static TagKey<Item> VERIDIUM_BLOCKS  = common("storage_blocks/veridium");
        public static TagKey<Item> VERIDIUM_ORES  = common("ores/veridium");
        public static TagKey<Item> VERIDIUM_RAW_ORES  = common("raw_materials/veridium");
        public static TagKey<Item> VERIDIUM_RAW_BLOCKS  = common("storage_blocks/raw_veridium");
        public static TagKey<Item> REFINED_SENTRITE_INGOTS  = common("ingots/refined_sentrite");
        public static TagKey<Item> REFINED_SENTRITE_NUGGETS  = common("nuggets/refined_sentrite");
        public static TagKey<Item> REFINED_SENTRITE_BLOCKS  = common("storage_blocks/refined_sentrite");
        public static TagKey<Item> REFINED_SENTRITE_ORES  = common("ores/refined_sentrite");
        public static TagKey<Item> STRATUS_INGOTS  = common("ingots/stratus");
        public static TagKey<Item> STRATUS_BLOCKS  = common("storage_blocks/stratus");

        public static TagKey<Item> local(String name) {
            return TagKey.create(Registries.ITEM, getResource(name));
        }

        private static TagKey<Item> common(String name) {
            return TagKey.create(Registries.ITEM, commonResource(name));
        }

        public static TagKey<Item> named(String namespace, String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, name));
        }
    }

    public static class Fluids {
        private static void init() {
        }

        private static TagKey<Fluid> local(String name) {
            return TagKey.create(Registries.FLUID, getResource(name));
        }
    }

    public static class Materials {
        private static void init() {}
        /** Materials available in aether */
        public static final TagKey<IMaterial> AETHER = local("aether");

        @SuppressWarnings("SameParameterValue")  // may want more tags later
        private static TagKey<IMaterial> local(String name) {
            return MaterialManager.getTag(getResource(name));
        }
    }

    public static class Modifiers {
        private static void init() {
        }

        private static TagKey<Modifier> local(String name) {
            return ModifierManager.getTag(getResource(name));
        }
    }
}
