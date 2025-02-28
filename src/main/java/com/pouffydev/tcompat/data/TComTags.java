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
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialManager;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierManager;

import java.util.*;

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
            ASPEN(builder("biomeswevegone")), BAOBAB(builder("biomeswevegone")), BLUE_ENCHANTED(builder("biomeswevegone")),
            CIKA(builder("biomeswevegone")), CYPRESS(builder("biomeswevegone")), EBONY(builder("biomeswevegone")),
            FIR(builder("biomeswevegone", "biomesoplenty")), FLORUS(builder("biomeswevegone")), GREEN_ENCHANTED(builder("biomeswevegone")),
            HOLLY(builder("biomeswevegone")), IRONWOOD(builder("biomeswevegone")), JACARANDA(builder("biomeswevegone", "biomesoplenty")),
            MAHOGANY(builder("biomeswevegone", "biomesoplenty")), MAPLE(builder("biomeswevegone", "biomesoplenty")), PALM(builder("biomeswevegone", "biomesoplenty")),
            PALO_VERDE(builder("biomeswevegone")), PINE(builder("biomeswevegone", "biomesoplenty")), RAINBOW_EUCALYPTUS(builder("biomeswevegone")), REDWOOD(builder("biomeswevegone", "biomesoplenty")),
            SAKURA(builder("biomeswevegone")), SKYRIS(builder("biomeswevegone")), WHITE_MANGROVE(builder("biomeswevegone")), WILLOW(builder("biomeswevegone", "biomesoplenty")), WITCH_HAZEL(builder("biomeswevegone")),
            ZELKOVA(builder("biomeswevegone")),

            //Aether
            SKYROOT(builder("aether")),
            ROSEROOT(builder("deep_aether")),
            YAGROOT(builder("deep_aether")),
            CRUDEROOT(builder("deep_aether")),
            CONBERRY(builder("deep_aether")),
            SUNROOT(builder("deep_aether")),
            CLOUDCAP(builder("aether_redux").stem()),
            JELLYSHROOM(builder("aether_redux").stem()),
            BLIGHTWILLOW(builder("aether_redux")),
            FIELDSPROOT(builder("aether_redux")),
            CRYSTAL(builder("aether_redux")),
            GLACIA(builder("aether_redux")),

            //Twilight Forest
            TWILIGHT_OAK(builder("twilightforest")), CANOPY(builder("twilightforest")), MANGROVE(builder("twilightforest")),
            DARKWOOD(builder("twilightforest").specialPlankId("twilightforest", "dark_planks")),
            TIMEWOOD(builder("twilightforest").specialLogTag("twilightforest", "timewood_logs").specialPlankId("twilightforest", "time_planks")),
            TRANSWOOD(builder("twilightforest").specialLogTag("twilightforest", "transwood_logs").specialPlankId("twilightforest", "transformation_planks")),
            MINEWOOD(builder("twilightforest").specialLogTag("twilightforest", "mining_logs").specialPlankId("twilightforest", "mining_planks")),
            SORTINGWOOD(builder("twilightforest").specialLogTag("twilightforest", "sortwood_logs").specialPlankId("twilightforest", "sorting_planks")),
            TOWERWOOD(builder("twilightforest").redirectPlankTag("twilightforest", "towerwood"))
            ;

            public final String name;
            public final Builder builder;

            Woods(Builder builder) {
                this.name = this.name().toLowerCase();
                this.builder = builder;
            }

            static Builder builder(String... namespaces) {
                return new Builder(namespaces);
            }

            public boolean isStem() {
                return this.builder.stem;
            }

            public boolean hasPlankRedirect() {
                return this.builder.redirectPlankTag != null;
            }

            public List<String> getNamespaces() {
                return this.builder.namespaces;
            }

            public TagKey<Item> getSpecialLogTag(String namespace) {
                return this.builder.specialLogTag.get(namespace);
            }
            public ResourceLocation getSpecialPlankId(String namespace) {
                return this.builder.specialPlankId.get(namespace);
            }

            static class Builder {
                public boolean stem = false;
                public List<String> namespaces = List.of();
                public Map<String, TagKey<Item>> specialLogTag = new HashMap<>();
                public TagKey<Item> redirectPlankTag = null;
                public Map<String, ResourceLocation> specialPlankId = new HashMap<>();

                public Builder(String... namespaces) {
                    Collection<String> namespaceList = new ArrayList<>(List.of());
                    namespaceList.addAll(Arrays.asList(namespaces));
                    this.namespaces = namespaceList.stream().toList();
                }

                public Builder stem() {
                    this.stem = true;
                    return this;
                }

                public Builder specialLogTag(String namespace, String name) {
                    TagKey<Item> tag = named(namespace, name);
                    this.specialLogTag.put(namespace, tag);
                    return this;
                }

                public Builder redirectPlankTag(String namespace, String name) {
                    this.redirectPlankTag = named(namespace, name);
                    return this;
                }

                public Builder specialPlankId(String namespace, String name) {
                    ResourceLocation id = new ResourceLocation(namespace, name);
                    this.specialPlankId.put(namespace, id);
                    return this;
                }

            }

            public TagKey<Item> externalLogTag(String namespace) {
                String suffix = this.builder.stem ? "_stems" : "_logs";
                return Objects.requireNonNullElseGet(getSpecialLogTag(namespace), () -> named(namespace, this.name().toLowerCase() + suffix));
            }
            public TagKey<Item> plankTag() {
                return Objects.requireNonNullElseGet(this.builder.redirectPlankTag, () -> local(this.name().toLowerCase() + "_planks"));
            }
            public TagKey<Item> logTag() {
                String suffix = this.builder.stem ? "_stems" : "_logs";
                return local(this.name().toLowerCase() + suffix);
            }
            public static TagKey<Item> plankTag(String name) {
                for (Woods wood : values()) {
                    if (wood.name().toLowerCase().equals(name)) {
                        return wood.plankTag();
                    }
                }
                return local(name + "_planks");
            }
            public static TagKey<Item> logTag(String name) {
                for (Woods wood : values()) {
                    if (wood.name().toLowerCase().equals(name)) {
                        return wood.logTag();
                    }
                }
                return local(name + "_logs");
            }
            public static TagKey<Item> stemTag(String name) {
                return local(name + "_stems");
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
        public enum Gloves {
            IRON,GOLD,DIAMOND,NETHERITE,OBSIDIAN,ZANITE,GRAVITITE,SKYJADE("deep_aether"),STRATUS("deep_aether")
            ;
            final String namespace;
            Gloves() {
                this.namespace = "aether";
            }
            Gloves(String namespace) {
                this.namespace = namespace;
            }
            public TagKey<Item> tag() {
                return common("aether_gloves/" + this.name().toLowerCase());
            }

            public ResourceLocation item() {
                String suffix = this == GOLD ? "en_gloves" : "_gloves";
                return new ResourceLocation(namespace, this.name().toLowerCase() + suffix);
            }
        }
        public enum Rings {
            IRON,GOLD,ZANITE,GRAVITITE,SKYJADE("deep_aether"),STRATUS("deep_aether")
            ;
            final String namespace;
            Rings() {
                this.namespace = "aether";
            }
            Rings(String namespace) {
                this.namespace = namespace;
            }
            public TagKey<Item> tag() {
                return common("aether_rings/" + this.name().toLowerCase());
            }

            public ResourceLocation item() {
                String suffix = this == GOLD ? "en_ring" : "_ring";
                return new ResourceLocation(namespace, this.name().toLowerCase() + suffix);
            }
        }
        public enum Pendants {
            IRON,GOLD,ZANITE
            ;
            final String namespace;
            Pendants() {
                this.namespace = "aether";
            }
            Pendants(String namespace) {
                this.namespace = namespace;
            }
            public TagKey<Item> tag() {
                return common("aether_pendants/" + this.name().toLowerCase());
            }

            public ResourceLocation item() {
                String suffix = this == GOLD ? "en_pendant" : "_pendant";
                return new ResourceLocation(namespace, this.name().toLowerCase() + suffix);
            }
        }
        public enum Salvaging {
            ZANITE_1, ZANITE_2, ZANITE_3, ZANITE_4, ZANITE_5, ZANITE_7, ZANITE_8,
            SKYJADE_1, SKYJADE_2, SKYJADE_3, SKYJADE_4, SKYJADE_5, SKYJADE_7, SKYJADE_8,
            GRAVITITE_1, GRAVITITE_2, GRAVITITE_3, GRAVITITE_4, GRAVITITE_5, GRAVITITE_7, GRAVITITE_8,
            STRATUS_1, STRATUS_2, STRATUS_3, STRATUS_4, STRATUS_5, STRATUS_7, STRATUS_8,
            VERIDIUM_1, VERIDIUM_2, VERIDIUM_3,
            OBSIDIAN_4, OBSIDIAN_5, OBSIDIAN_7, OBSIDIAN_8,
            STEELEAF_1, STEELEAF_2, STEELEAF_3, STEELEAF_4, STEELEAF_5, STEELEAF_7, STEELEAF_8,
            KNIGHTMETAL_2, KNIGHTMETAL_3, KNIGHTMETAL_4, KNIGHTMETAL_5, KNIGHTMETAL_7, KNIGHTMETAL_8, KNIGHTMETAL_16,
            FIERY_2, FIERY_3, FIERY_4, FIERY_5, FIERY_7, FIERY_8
            ;

            public TagKey<Item> tag() {
                List<String> split = Arrays.stream(this.name().toLowerCase().split("_", 5)).toList();
                String material = split.get(0);
                int amount = Integer.parseInt(split.get(1));
                return local("salvaging/" + material + "/" + amount);
            }
        }
        public static TagKey<Item> STEELEAF_INGOTS  = common("ingots/steeleaf");
        public static TagKey<Item> STEELEAF_BLOCKS  = common("storage_blocks/steeleaf");
        public static TagKey<Item> FIERY_INGOTS     = common("ingots/fiery");
        public static TagKey<Item> FIERY_BLOCKS     = common("storage_blocks/fiery");
        public static TagKey<Item> KNIGHTMETAL_INGOTS     = common("ingots/knightmetal");
        public static TagKey<Item> KNIGHTMETAL_BLOCKS     = common("storage_blocks/knightmetal");
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


        public static TagKey<Item> AMBROSIUM = local("ambrosium");
        public static TagKey<Item> SENTRY_RING = local("sentry_ring");
        public static TagKey<Item> WISDOM_RING = local("ring_of_wisdom");

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

        private static TagKey<Item> named(String namespace, String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(namespace, name));
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
        /** Materials available in twilight forest */
        public static final TagKey<IMaterial> TWILIGHT_FOREST = local("twilight_forest");

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
