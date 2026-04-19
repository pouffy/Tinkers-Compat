package io.github.pouffy.tcompat.datagen.tag;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.material.TCRocks;
import io.github.pouffy.tcompat.common.material.TCWoods;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.common.TinkerTags;

import java.util.concurrent.CompletableFuture;

import static io.github.pouffy.tcompat.TCompat.getResource;

@SuppressWarnings("SameParameterValue")
public class TCItemTagProv extends ItemTagsProvider {
    public TCItemTagProv(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagProvider, TCompat.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        addPlanks();
        addMetals();
        addMisc();
        addForge();
    }

    private void addMisc() {
        this.tag(TCTags.Items.SENTRITE_STONE_BLOCKS)
                .addOptional(getResource("aether_redux", "sentrite"))
                .addOptional(getResource("aether_redux", "sentrite_wall"))
                .addOptional(getResource("aether_redux", "sentrite_bricks"))
                .addOptional(getResource("aether_redux", "sentrite_brick_wall"));
        this.tag(TCTags.Items.SENTRITE_STONE_SLABS)
                .addOptional(getResource("aether_redux", "sentrite_slab"))
                .addOptional(getResource("aether_redux", "sentrite_brick_slab"));

        this.tag(TCTags.Items.AUTOCHANT_LEFT)
                .addOptional(getResource("aether", "holystone"))
                .addOptional(getResource("aether", "mossy_holystone"))
                .addOptional(getResource("aether", "cold_aercloud"))
                .addOptional(getResource("aether", "quicksoil"))
                .addOptional(getResource("deep_aether", "raw_clorite"))
                .addOptional(getResource("aether_redux", "divinite"));
        this.tag(TCTags.Items.AUTOCHANT_RIGHT)
                .addOptional(getResource("aether", "healing_stone"))
                .addOptional(getResource("aether_redux", "gilded_holystone"))
                .addOptional(getResource("aether", "golden_aercloud"))
                .addOptional(getResource("aether", "quicksoil_glass"))
                .addOptional(getResource("deep_aether", "clorite"))
                .addOptional(getResource("minecraft", "glowstone"));
        this.tag(TCTags.Items.AUTOFREEZE_LEFT)
                .addOptional(getResource("aether", "music_disc_chinchilla"))
                .addOptional(getResource("aether", "cold_aercloud"))
                .addOptional(getResource("aether", "skyroot_leaves"));
        this.tag(TCTags.Items.AUTOFREEZE_RIGHT)
                .addOptional(getResource("aether", "music_disc_high"))
                .addOptional(getResource("aether", "blue_aercloud"))
                .addOptional(getResource("aether", "crystal_leaves"));

        this.tag(TCTags.Items.FIRE_DRAGON_SCALES)
                .addOptional(getResource("iceandfire", "dragonscales_bronze"))
                .addOptional(getResource("iceandfire", "dragonscales_green"))
                .addOptional(getResource("iceandfire", "dragonscales_gray"))
                .addOptional(getResource("iceandfire", "dragonscales_red"));
        this.tag(TCTags.Items.ICE_DRAGON_SCALES)
                .addOptional(getResource("iceandfire", "dragonscales_blue"))
                .addOptional(getResource("iceandfire", "dragonscales_sapphire"))
                .addOptional(getResource("iceandfire", "dragonscales_silver"))
                .addOptional(getResource("iceandfire", "dragonscales_white"));
        this.tag(TCTags.Items.LIGHTNING_DRAGON_SCALES)
                .addOptional(getResource("iceandfire", "dragonscales_amythest"))
                .addOptional(getResource("iceandfire", "dragonscales_black"))
                .addOptional(getResource("iceandfire", "dragonscales_copper"))
                .addOptional(getResource("iceandfire", "dragonscales_electric"));

        var thallasiumLanterns = this.tag(TCTags.Items.THALLASIUM_BULB_LANTERNS).addOptional(getResource("betterend", "thallasium_bulb_lantern"));
        var terminiteLanterns = this.tag(TCTags.Items.TERMINITE_BULB_LANTERNS).addOptional(getResource("betterend", "terminite_bulb_lantern"));
        var ironLanterns = this.tag(TCTags.Items.IRON_BULB_LANTERNS).addOptional(getResource("betterend", "iron_bulb_lantern"));

        var quartzGlass = this.tag(TCTags.Items.QUARTZ_GLASS).addOptional(getResource("betternether", "quartz_glass"));
        var quartzGlassPane = this.tag(TCTags.Items.QUARTZ_GLASS_PANES).addOptional(getResource("betternether", "quartz_glass_pane"));
        var framedQuartzGlass = this.tag(TCTags.Items.FRAMED_QUARTZ_GLASS).addOptional(getResource("betternether", "quartz_glass_framed"));
        var framedQuartzGlassPane = this.tag(TCTags.Items.FRAMED_QUARTZ_GLASS_PANES).addOptional(getResource("betternether", "quartz_glass_framed_pane"));

        for (DyeColor color : DyeColor.values()) {
            String thallasium = "thallasium_bulb_lantern_" + color.getSerializedName();
            String terminite = "terminite_bulb_lantern_" + color.getSerializedName();
            String iron = "iron_bulb_lantern_" + color.getSerializedName();

            String quartz = "quartz_glass_" + color.getSerializedName();
            String quartzPane = "quartz_glass_pane_" + color.getSerializedName();
            String framedQuartz = "quartz_glass_framed_" + color.getSerializedName();
            String framedQuartzPane = "quartz_glass_framed_pane_" + color.getSerializedName();

            thallasiumLanterns.addOptional(getResource("betterend", thallasium));
            terminiteLanterns.addOptional(getResource("betterend", terminite));
            ironLanterns.addOptional(getResource("betterend", iron));

            quartzGlass.addOptional(getResource("betternether", quartz));
            quartzGlassPane.addOptional(getResource("betternether", quartzPane));
            framedQuartzGlass.addOptional(getResource("betternether", framedQuartz));
            framedQuartzGlassPane.addOptional(getResource("betternether", framedQuartzPane));
        }

        var glaive = getResource("glaive");
        this.tag(TinkerTags.Items.MODIFIABLE).addOptional(glaive);
        this.tag(TinkerTags.Items.HELD).addOptional(glaive);
        this.tag(TinkerTags.Items.MELEE).addOptional(glaive);
        this.tag(TinkerTags.Items.MELEE_PRIMARY).addOptional(glaive);
        this.tag(TinkerTags.Items.MELEE_WEAPON).addOptional(glaive);
        this.tag(TinkerTags.Items.AOE).addOptional(glaive);
        this.tag(TinkerTags.Items.HARVEST).addOptional(glaive);
        this.tag(TinkerTags.Items.DURABILITY).addOptional(glaive);
        this.tag(TinkerTags.Items.BALLISTA_AMMO).addOptional(glaive);
        this.tag(TinkerTags.Items.MULTIPART_TOOL).addOptional(glaive);
        this.tag(TinkerTags.Items.BONUS_SLOTS).addOptional(glaive);
        this.tag(ItemTags.create(getResource("forge:tools"))).addOptional(glaive);
        this.tag(ItemTags.create(getResource("minecraft:tools"))).addOptional(glaive);
    }

    private void addForge() {
        this.tag(TCTags.Items.ZANITE_GEMS).addOptional(getResource("aether", "zanite_gemstone"));
        this.tag(TCTags.Items.ZANITE_BLOCKS).addOptional(getResource("aether", "zanite_block"));
        this.tag(TCTags.Items.ZANITE_ORES).addOptional(getResource("aether", "zanite_ore"));
        this.tag(TCTags.Items.SKYJADE_GEMS).addOptional(getResource("deep_aether", "skyjade"));
        this.tag(TCTags.Items.SKYJADE_NUGGETS).addOptional(getResource("deep_aether", "skyjade_nugget"));
        this.tag(TCTags.Items.SKYJADE_BLOCKS).addOptional(getResource("deep_aether", "skyjade_block"));
        this.tag(TCTags.Items.SKYJADE_ORES).addOptional(getResource("deep_aether", "skyjade_ore"));
        this.tag(TCTags.Items.GRAVITITE_INGOTS).addOptional(getResource("aether_redux", "gravitite_ingot"));
        this.tag(TCTags.Items.GRAVITITE_BLOCKS).addOptional(getResource("aether_redux", "gravitite_block"));
        this.tag(TCTags.Items.GRAVITITE_ORES).addOptional(getResource("aether", "gravitite_ore"));
        this.tag(TCTags.Items.GRAVITITE_RAW_BLOCKS).addOptional(getResource("aether_redux", "raw_gravitite_block"));
        this.tag(TCTags.Items.GRAVITITE_RAW_ORES).addOptional(getResource("aether_redux", "raw_gravitite"));
        this.tag(TCTags.Items.VERIDIUM_INGOTS).addOptional(getResource("aether_redux", "veridium_ingot"));
        this.tag(TCTags.Items.VERIDIUM_NUGGETS).addOptional(getResource("aether_redux", "veridium_nugget"));
        this.tag(TCTags.Items.VERIDIUM_BLOCKS).addOptional(getResource("aether_redux", "veridium_block"));
        this.tag(TCTags.Items.VERIDIUM_ORES).addOptional(getResource("aether_redux", "veridium_ore"));
        this.tag(TCTags.Items.VERIDIUM_RAW_BLOCKS).addOptional(getResource("aether_redux", "raw_veridium_block"));
        this.tag(TCTags.Items.VERIDIUM_RAW_ORES).addOptional(getResource("aether_redux", "raw_veridium"));
        this.tag(TCTags.Items.REFINED_SENTRITE_INGOTS).addOptional(getResource("aether_redux", "refined_sentrite"));
        this.tag(TCTags.Items.REFINED_SENTRITE_NUGGETS).addOptional(getResource("aether_redux", "sentrite_chunk"));
        this.tag(TCTags.Items.REFINED_SENTRITE_BLOCKS).addOptional(getResource("aether_redux", "refined_sentrite_block"));
        this.tag(TCTags.Items.REFINED_SENTRITE_ORES).addOptional(getResource("aether_redux", "sentrite"));
        this.tag(TCTags.Items.STRATUS_BLOCKS).addOptional(getResource("deep_aether", "stratus_block"));
        this.tag(TCTags.Items.STRATUS_INGOTS).addOptional(getResource("deep_aether", "stratus_ingot"));
        this.tag(TCTags.Items.NEPTUNE_MESH).addOptional(getResource("aether_treasure_reforging", "neptune_mesh"));
        this.tag(TCTags.Items.PYRAL_INGOTS).addOptional(getResource("aether_treasure_reforging", "pyral_ingot"));
        this.tag(TCTags.Items.PYRAL_BLOCKS).addOptional(getResource("aether_treasure_reforging", "pyral_block"));
        this.tag(TCTags.Items.VALKYRUM_INGOTS).addOptional(getResource("aether_treasure_reforging", "valkyrum_ingot"));
        this.tag(TCTags.Items.VALKYRUM_BLOCKS).addOptional(getResource("aether_treasure_reforging", "valkyrum_block"));
        this.tag(TCTags.Items.THALLASIUM_INGOTS).addOptional(getResource("betterend", "thallasium_ingot"));
        this.tag(TCTags.Items.THALLASIUM_PLATES).addOptional(getResource("betterend", "thallasium_forged_plate"));
        this.tag(TCTags.Items.THALLASIUM_NUGGETS).addOptional(getResource("betterend", "thallasium_nugget"));
        this.tag(TCTags.Items.THALLASIUM_BLOCKS).addOptional(getResource("betterend", "thallasium_block"));
        this.tag(TCTags.Items.THALLASIUM_ORES).addOptional(getResource("betterend", "thallasium_ore"));
        this.tag(TCTags.Items.RAW_THALLASIUM).addOptional(getResource("betterend", "thallasium_raw"));
        this.tag(TCTags.Items.TERMINITE_INGOTS).addOptional(getResource("betterend", "terminite_ingot"));
        this.tag(TCTags.Items.TERMINITE_PLATES).addOptional(getResource("betterend", "terminite_forged_plate"));
        this.tag(TCTags.Items.TERMINITE_NUGGETS).addOptional(getResource("betterend", "terminite_nugget"));
        this.tag(TCTags.Items.TERMINITE_BLOCKS).addOptional(getResource("betterend", "terminite_block"));
        this.tag(TCTags.Items.AETERNIUM_INGOTS).addOptional(getResource("betterend", "aeternium_ingot"));
        this.tag(TCTags.Items.AETERNIUM_PLATES).addOptional(getResource("betterend", "aeternium_forged_plate"));
        this.tag(TCTags.Items.AETERNIUM_BLOCKS).addOptional(getResource("betterend", "aeternium_block"));

        this.tag(TCTags.Items.CINCINNASITE_RAW_ORES).addOptional(getResource("betternether", "cincinnasite"));
        this.tag(TCTags.Items.CINCINNASITE_RAW_BLOCKS).addOptional(getResource("betternether", "cincinnasite_block"));
        this.tag(TCTags.Items.CINCINNASITE_ORES).addOptional(getResource("betternether", "cincinnasite_ore"));
        this.tag(TCTags.Items.CINCINNASITE_INGOTS).addOptional(getResource("betternether", "cincinnasite_ingot"));
        this.tag(TCTags.Items.CINCINNASITE_BLOCKS).addOptional(getResource("betternether", "cincinnasite_forged"));

        this.tag(TCTags.Items.NETHER_RUBY_GEMS).addOptional(getResource("betternether", "nether_ruby"));
        this.tag(TCTags.Items.NETHER_RUBY_BLOCKS).addOptional(getResource("betternether", "nether_ruby_block"));
        this.tag(TCTags.Items.NETHER_RUBY_ORES).addOptional(getResource("betternether", "nether_ruby_ore"));

        this.tag(TCTags.Items.LIGHTNUM_INGOTS).addOptional(getResource("tcompat", "lightnum_ingot"));
        this.tag(TCTags.Items.DRACULITE_INGOTS).addOptional(getResource("tcompat", "draculite_ingot"));
        this.tag(TCTags.Items.STORMFORGED_STEEL_INGOTS).addOptional(getResource("tcompat", "stormforged_steel_ingot"));

        this.tag(TCTags.Items.FIRE_DRAGONSTEEL_INGOTS).addOptional(getResource("iceandfire", "dragonsteel_fire_ingot"));
        this.tag(TCTags.Items.FIRE_DRAGONSTEEL_NUGGETS).addOptional(getResource("tcompat", "fire_dragonsteel_nugget"));
        this.tag(TCTags.Items.FIRE_DRAGONSTEEL_BLOCKS).addOptional(getResource("iceandfire", "dragonsteel_fire_block"));
        this.tag(TCTags.Items.ICE_DRAGONSTEEL_INGOTS).addOptional(getResource("iceandfire", "dragonsteel_ice_ingot"));
        this.tag(TCTags.Items.ICE_DRAGONSTEEL_NUGGETS).addOptional(getResource("tcompat", "ice_dragonsteel_nugget"));
        this.tag(TCTags.Items.ICE_DRAGONSTEEL_BLOCKS).addOptional(getResource("iceandfire", "dragonsteel_ice_block"));
        this.tag(TCTags.Items.LIGHTNING_DRAGONSTEEL_INGOTS).addOptional(getResource("iceandfire", "dragonsteel_lightning_ingot"));
        this.tag(TCTags.Items.LIGHTNING_DRAGONSTEEL_NUGGETS).addOptional(getResource("tcompat", "lightning_dragonsteel_nugget"));
        this.tag(TCTags.Items.LIGHTNING_DRAGONSTEEL_BLOCKS).addOptional(getResource("iceandfire", "dragonsteel_lightning_block"));
    }

    private void addMetals() {
        this.tag(TinkerTags.Items.ANVIL_METAL).addOptional(getResource("deep_aether", "stratus_block"));
    }

    private void addPlanks() {
        for (TCWoods wood : TCWoods.values()) {
            String woodName = wood.name;
            for (String namespace : wood.getNamespaces()) {
                if (wood.hasPlanks()) {
                    if (!wood.hasPlankRedirect()) {
                        ResourceLocation plankId = wood.getSpecialPlankId(namespace) != null ? wood.getSpecialPlankId(namespace) : getResource(namespace, "%s_planks".formatted(woodName));
                        this.tag(wood.plankTag()).addOptional(plankId);
                    }
                    this.tag(TinkerTags.Items.VARIANT_PLANKS)
                            .addOptionalTag(wood.plankTag());
                }
                if (wood.hasLogs()) {
                    this.tag(wood.logTag())
                            .addOptionalTag(wood.externalLogTag(namespace));
                    this.tag(TinkerTags.Items.VARIANT_LOGS)
                            .addOptionalTag(wood.logTag());
                }
            }
        }
        for (TCRocks rock : TCRocks.values()) {
            String rockName = rock.name;
            for (String namespace : rock.getNamespaces()) {
                if (!rock.hasRockRedirect()) {
                    ResourceLocation plankId = rock.getSpecialRockId(namespace) != null ? rock.getSpecialRockId(namespace) : getResource(namespace, "%s".formatted(rockName));
                    this.tag(rock.rockTag()).addOptional(plankId);
                }
                this.tag(rock.rockTag())
                        .addOptionalTag(rock.externalRockTag(namespace));
                this.tag(TinkerTags.Items.WORKSTATION_ROCK)
                        .addOptional(getResource(namespace, rockName));
            }
        }
        this.tag(TinkerTags.Items.PLANKLIKE).addOptionalTag(itemTag("aether:planks_crafting"));
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, getResource(name));
    }
}
