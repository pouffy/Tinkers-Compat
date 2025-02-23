package com.pouffydev.tcompat.data;

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
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialManager;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierManager;

import static com.pouffydev.tcompat.TCompat.getResource;
import static slimeknights.mantle.Mantle.commonResource;

public class TComTags {
    /** Checks if tags have been loaded on this instance. Used to prevent certain NBT operations that depend on tags from happening client side when tags are missing. */
    @Getter
    static boolean tagsLoaded = false;

    /** Called on mod construct to set up tags */
    public static void init() {
        Blocks.init();
        Items.init();
        Fluids.init();
        Materials.init();
        Modifiers.init();

        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, TagsUpdatedEvent.class, event -> tagsLoaded = true);
    }

    /** Creates a tag that hides things from JEI */
    @SuppressWarnings("SameParameterValue")
    private static <R> TagKey<R> hiddenFromRecipeViewers(ResourceKey<? extends Registry<R>> registry) {
        return TagKey.create(registry, new ResourceLocation("c", "hidden_from_recipe_viewers"));
    }

    public static class Blocks {
        private static void init() {
        }

        /** Contains all compatible wood sets for recipes & tagging */
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



        /** Makes a tag in the tinkers compat domain */
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

        /** Contains all compatible wood sets for recipes & tagging */
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

            public TagKey<Item> plankTag() {
                return local(this.name().toLowerCase() + "_planks");
            }
            public TagKey<Item> logTag() {
                return local(this.name().toLowerCase() + "_logs");
            }
            public static TagKey<Item> plankTag(String name) {
                return local(name + "_planks");
            }
            public static TagKey<Item> logTag(String name) {
                return local(name + "_logs");
            }
        }
        public enum Oreberries {
            ALUMINUM, COPPER, GOLD, IRON,
            LEAD, NICKEL, OSMIUM, SILVER,
            TIN, URANIUM, ZINC
            ;

            public TagKey<Item> tag() {
                return common("oreberries/" + this.name().toLowerCase());
            }

            public ResourceLocation item() {
                return new ResourceLocation("oreberriesreplanted", this.name().toLowerCase() + "_oreberry");
            }
        }
        public static TagKey<Item> STEELEAF_INGOTS  = common("ingots/steeleaf");
        public static TagKey<Item> STEELEAF_BLOCKS  = common("storage_blocks/steeleaf");
        public static TagKey<Item> FIERY_INGOTS     = common("ingots/fiery");
        public static TagKey<Item> FIERY_BLOCKS     = common("storage_blocks/fiery");
        public static TagKey<Item> KNIGHTMETAL_INGOTS     = common("ingots/knightmetal");
        public static TagKey<Item> KNIGHTMETAL_BLOCKS     = common("storage_blocks/knightmetal");

        public static TagKey<Item> ARMOR_SHARD = local("armor_shard");
        public static TagKey<Item> ARMOR_SHARD_CLUSTER = local("armor_shard_cluster");
        public static TagKey<Item> KNIGHTMETAL_LOOP = local("knightmetal_loop");

        public static TagKey<Item> RAVEN_FEATHER = local("raven_feathers");
        public static TagKey<Item> NAGA_SCALE = local("naga_scales");

        public static TagKey<Item> FIERY_VIAL = TagKey.create(Registries.ITEM, new ResourceLocation("twilightforest", "fiery_vial"));


        /** Makes a tag in the tinkers compat domain */
        private static TagKey<Item> local(String name) {
            return TagKey.create(Registries.ITEM, getResource(name));
        }

        private static TagKey<Item> common(String name) {
            return TagKey.create(Registries.ITEM, commonResource(name));
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
