package com.pouffydev.tcompat.data.tag;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.data.TComTags;
import com.pouffydev.tcompat.material.TComMaterialIds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class TComItemTagProv extends ItemTagsProvider {
    public TComItemTagProv(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagProvider, TCompat.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        addPlanks();
        addMetals();
        addMisc();
        addForge();
        addSalvage();
    }

    private void addMisc() {
        this.tag(TComTags.Items.NAGA_SCALE).addOptional(new ResourceLocation("twilightforest", "naga_scale"));

        this.tag(TComTags.Items.ARMOR_SHARD).addOptional(new ResourceLocation("twilightforest", "armor_shard"));
        this.tag(TComTags.Items.ARMOR_SHARD_CLUSTER).addOptional(new ResourceLocation("twilightforest", "armor_shard_cluster"));
        this.tag(TComTags.Items.KNIGHTMETAL_LOOP).addOptional(new ResourceLocation("twilightforest", "knightmetal_ring"));


    }

    private void addSalvage() {
        //Zanite
        this.tag(TComTags.Items.Salvaging.ZANITE.tag(1)).addOptional(new ResourceLocation("aether", "zanite_shovel"));
        this.tag(TComTags.Items.Salvaging.ZANITE.tag(2)).addOptional(new ResourceLocation("aether", "zanite_sword")).addOptional(new ResourceLocation("aether", "zanite_hoe"));
        this.tag(TComTags.Items.Salvaging.ZANITE.tag(3)).addOptional(new ResourceLocation("aether", "zanite_axe")).addOptional(new ResourceLocation("aether", "zanite_pickaxe"));
        this.tag(TComTags.Items.Salvaging.ZANITE.tag(4)).addOptional(new ResourceLocation("aether", "zanite_boots"));
        this.tag(TComTags.Items.Salvaging.ZANITE.tag(5)).addOptional(new ResourceLocation("aether", "zanite_helmet"));
        this.tag(TComTags.Items.Salvaging.ZANITE.tag(7)).addOptional(new ResourceLocation("aether", "zanite_leggings"));
        this.tag(TComTags.Items.Salvaging.ZANITE.tag(8)).addOptional(new ResourceLocation("aether", "zanite_chestplate"));
        //Gravitite
        this.tag(TComTags.Items.Salvaging.GRAVITITE.tag(1)).addOptional(new ResourceLocation("aether", "gravitite_shovel"));
        this.tag(TComTags.Items.Salvaging.GRAVITITE.tag(2)).addOptional(new ResourceLocation("aether", "gravitite_sword")).addOptional(new ResourceLocation("aether", "gravitite_hoe"));
        this.tag(TComTags.Items.Salvaging.GRAVITITE.tag(3)).addOptional(new ResourceLocation("aether", "gravitite_axe")).addOptional(new ResourceLocation("aether", "gravitite_pickaxe"));
        this.tag(TComTags.Items.Salvaging.GRAVITITE.tag(4)).addOptional(new ResourceLocation("aether", "gravitite_boots"));
        this.tag(TComTags.Items.Salvaging.GRAVITITE.tag(5)).addOptional(new ResourceLocation("aether", "gravitite_helmet"));
        this.tag(TComTags.Items.Salvaging.GRAVITITE.tag(7)).addOptional(new ResourceLocation("aether", "gravitite_leggings"));
        this.tag(TComTags.Items.Salvaging.GRAVITITE.tag(8)).addOptional(new ResourceLocation("aether", "gravitite_chestplate"));
        //Skyjade
        this.tag(TComTags.Items.Salvaging.SKYJADE.tag(1)).addOptional(new ResourceLocation("deep_aether", "skyjade_shovel"));
        this.tag(TComTags.Items.Salvaging.SKYJADE.tag(2)).addOptional(new ResourceLocation("deep_aether", "skyjade_sword")).addOptional(new ResourceLocation("deep_aether", "skyjade_hoe"));
        this.tag(TComTags.Items.Salvaging.SKYJADE.tag(3)).addOptional(new ResourceLocation("deep_aether", "skyjade_axe")).addOptional(new ResourceLocation("deep_aether", "skyjade_pickaxe"));
        this.tag(TComTags.Items.Salvaging.SKYJADE.tag(4)).addOptional(new ResourceLocation("deep_aether", "skyjade_boots"));
        this.tag(TComTags.Items.Salvaging.SKYJADE.tag(5)).addOptional(new ResourceLocation("deep_aether", "skyjade_helmet"));
        this.tag(TComTags.Items.Salvaging.SKYJADE.tag(7)).addOptional(new ResourceLocation("deep_aether", "skyjade_leggings"));
        this.tag(TComTags.Items.Salvaging.SKYJADE.tag(8)).addOptional(new ResourceLocation("deep_aether", "skyjade_chestplate"));
        //Stratus
        this.tag(TComTags.Items.Salvaging.STRATUS.tag(1)).addOptional(new ResourceLocation("deep_aether", "stratus_shovel"));
        this.tag(TComTags.Items.Salvaging.STRATUS.tag(2)).addOptional(new ResourceLocation("deep_aether", "stratus_sword")).addOptional(new ResourceLocation("deep_aether", "stratus_hoe"));
        this.tag(TComTags.Items.Salvaging.STRATUS.tag(3)).addOptional(new ResourceLocation("deep_aether", "stratus_axe")).addOptional(new ResourceLocation("deep_aether", "stratus_pickaxe"));
        this.tag(TComTags.Items.Salvaging.STRATUS.tag(4)).addOptional(new ResourceLocation("deep_aether", "stratus_boots"));
        this.tag(TComTags.Items.Salvaging.STRATUS.tag(5)).addOptional(new ResourceLocation("deep_aether", "stratus_helmet"));
        this.tag(TComTags.Items.Salvaging.STRATUS.tag(7)).addOptional(new ResourceLocation("deep_aether", "stratus_leggings"));
        this.tag(TComTags.Items.Salvaging.STRATUS.tag(8)).addOptional(new ResourceLocation("deep_aether", "stratus_chestplate"));
        //Veridium
        this.tag(TComTags.Items.Salvaging.VERIDIUM.tag(1)).addOptional(new ResourceLocation("aether_redux", "veridium_shovel")).addOptional(new ResourceLocation("aether_redux", "infused_veridium_shovel"));
        this.tag(TComTags.Items.Salvaging.VERIDIUM.tag(2)).addOptional(new ResourceLocation("aether_redux", "veridium_sword")).addOptional(new ResourceLocation("aether_redux", "veridium_hoe")).addOptional(new ResourceLocation("aether_redux", "infused_veridium_sword")).addOptional(new ResourceLocation("aether_redux", "infused_veridium_hoe"));
        this.tag(TComTags.Items.Salvaging.VERIDIUM.tag(3)).addOptional(new ResourceLocation("aether_redux", "veridium_axe")).addOptional(new ResourceLocation("aether_redux", "veridium_pickaxe")).addOptional(new ResourceLocation("aether_redux", "infused_veridium_axe")).addOptional(new ResourceLocation("aether_redux", "infused_veridium_pickaxe"));
        //Obsidian
        this.tag(TComTags.Items.Salvaging.OBSIDIAN.tag(4)).addOptional(new ResourceLocation("aether", "obsidian_boots"));
        this.tag(TComTags.Items.Salvaging.OBSIDIAN.tag(5)).addOptional(new ResourceLocation("aether", "obsidian_helmet"));
        this.tag(TComTags.Items.Salvaging.OBSIDIAN.tag(7)).addOptional(new ResourceLocation("aether", "obsidian_leggings"));
        this.tag(TComTags.Items.Salvaging.OBSIDIAN.tag(8)).addOptional(new ResourceLocation("aether", "obsidian_chestplate"));
        //Steeleaf
        this.tag(TComTags.Items.Salvaging.STEELEAF.tag(1)).addOptional(new ResourceLocation("twilightforest", "steeleaf_shovel"));
        this.tag(TComTags.Items.Salvaging.STEELEAF.tag(2)).addOptional(new ResourceLocation("twilightforest", "steeleaf_sword")).addOptional(new ResourceLocation("twilightforest", "steeleaf_hoe"));
        this.tag(TComTags.Items.Salvaging.STEELEAF.tag(3)).addOptional(new ResourceLocation("twilightforest", "steeleaf_axe")).addOptional(new ResourceLocation("twilightforest", "steeleaf_pickaxe"));
        this.tag(TComTags.Items.Salvaging.STEELEAF.tag(4)).addOptional(new ResourceLocation("twilightforest", "steeleaf_boots"));
        this.tag(TComTags.Items.Salvaging.STEELEAF.tag(5)).addOptional(new ResourceLocation("twilightforest", "steeleaf_helmet"));
        this.tag(TComTags.Items.Salvaging.STEELEAF.tag(7)).addOptional(new ResourceLocation("twilightforest", "steeleaf_leggings"));
        this.tag(TComTags.Items.Salvaging.STEELEAF.tag(8)).addOptional(new ResourceLocation("twilightforest", "steeleaf_chestplate"));
        //Knightmetal
        this.tag(TComTags.Items.Salvaging.KNIGHTMETAL.tag(2)).addOptional(new ResourceLocation("twilightforest", "knightmetal_sword"));
        this.tag(TComTags.Items.Salvaging.KNIGHTMETAL.tag(3)).addOptional(new ResourceLocation("twilightforest", "knightmetal_axe")).addOptional(new ResourceLocation("twilightforest", "knightmetal_pickaxe"));
        this.tag(TComTags.Items.Salvaging.KNIGHTMETAL.tag(4)).addOptional(new ResourceLocation("twilightforest", "knightmetal_boots"));
        this.tag(TComTags.Items.Salvaging.KNIGHTMETAL.tag(5)).addOptional(new ResourceLocation("twilightforest", "knightmetal_helmet"));
        this.tag(TComTags.Items.Salvaging.KNIGHTMETAL.tag(7)).addOptional(new ResourceLocation("twilightforest", "knightmetal_leggings")).addOptional(new ResourceLocation("twilightforest", "knightmetal_shield"));
        this.tag(TComTags.Items.Salvaging.KNIGHTMETAL.tag(8)).addOptional(new ResourceLocation("twilightforest", "knightmetal_chestplate"));
        this.tag(TComTags.Items.Salvaging.KNIGHTMETAL.tag(16)).addOptional(new ResourceLocation("twilightforest", "block_and_chain"));
        //Fiery
        this.tag(TComTags.Items.Salvaging.FIERY.tag(2)).addOptional(new ResourceLocation("twilightforest", "fiery_sword"));
        this.tag(TComTags.Items.Salvaging.FIERY.tag(3)).addOptional(new ResourceLocation("twilightforest", "fiery_pickaxe"));
        this.tag(TComTags.Items.Salvaging.FIERY.tag(4)).addOptional(new ResourceLocation("twilightforest", "fiery_boots"));
        this.tag(TComTags.Items.Salvaging.FIERY.tag(5)).addOptional(new ResourceLocation("twilightforest", "fiery_helmet"));
        this.tag(TComTags.Items.Salvaging.FIERY.tag(7)).addOptional(new ResourceLocation("twilightforest", "fiery_leggings"));
        this.tag(TComTags.Items.Salvaging.FIERY.tag(8)).addOptional(new ResourceLocation("twilightforest", "fiery_chestplate"));
    }

    private void addForge() {
        for (TComTags.Items.Oreberries oreberry : TComTags.Items.Oreberries.values()) {
            this.tag(oreberry.tag()).addOptional(oreberry.item());
        }
        for (TComTags.Items.Gloves glove : TComTags.Items.Gloves.values()) {
            this.tag(glove.tag()).addOptional(glove.item());
        }
        for (TComTags.Items.Rings ring : TComTags.Items.Rings.values()) {
            this.tag(ring.tag()).addOptional(ring.item());
        }
        for (TComTags.Items.Pendants pendant : TComTags.Items.Pendants.values()) {
            this.tag(pendant.tag()).addOptional(pendant.item());
        }
        this.tag(TComTags.Items.WISDOM_RING).addOptional(new ResourceLocation("aether_redux", "ring_of_wisdom"));
        this.tag(TComTags.Items.SENTRY_RING).addOptional(new ResourceLocation("aether_redux", "sentry_ring"));
        this.tag(TComTags.Items.STEELEAF_INGOTS).addOptional(new ResourceLocation("twilightforest", "steeleaf_ingot"));
        this.tag(TComTags.Items.STEELEAF_BLOCKS).addOptional(new ResourceLocation("twilightforest", "steeleaf_block"));
        this.tag(TComTags.Items.FIERY_INGOTS).addOptional(new ResourceLocation("twilightforest", "fiery_ingot"));
        this.tag(TComTags.Items.FIERY_BLOCKS).addOptional(new ResourceLocation("twilightforest", "fiery_block"));
        this.tag(TComTags.Items.KNIGHTMETAL_INGOTS).addOptional(new ResourceLocation("twilightforest", "knightmetal_ingot"));
        this.tag(TComTags.Items.KNIGHTMETAL_BLOCKS).addOptional(new ResourceLocation("twilightforest", "knightmetal_block"));

        this.tag(TComTags.Items.ZANITE_GEMS).addOptional(new ResourceLocation("aether", "zanite_gemstone"));
        this.tag(TComTags.Items.ZANITE_BLOCKS).addOptional(new ResourceLocation("aether", "zanite_block"));
        this.tag(TComTags.Items.ZANITE_ORES).addOptional(new ResourceLocation("aether", "zanite_ore"));
        this.tag(TComTags.Items.SKYJADE_GEMS).addOptional(new ResourceLocation("deep_aether", "skyjade"));
        this.tag(TComTags.Items.SKYJADE_BLOCKS).addOptional(new ResourceLocation("deep_aether", "skyjade_block"));
        this.tag(TComTags.Items.SKYJADE_ORES).addOptional(new ResourceLocation("deep_aether", "skyjade_ore"));
        this.tag(TComTags.Items.GRAVITITE_INGOTS).addOptional(new ResourceLocation("aether_redux", "gravitite_ingot")).addOptional(new ResourceLocation("aether", "enchanted_gravitite"));
        this.tag(TComTags.Items.GRAVITITE_BLOCKS).addOptional(new ResourceLocation("aether_redux", "gravitite_block"));
        this.tag(TComTags.Items.GRAVITITE_ORES).addOptional(new ResourceLocation("aether", "gravitite_ore"));
        this.tag(TComTags.Items.GRAVITITE_RAW_BLOCKS).addOptional(new ResourceLocation("aether_redux", "raw_gravitite_block"));
        this.tag(TComTags.Items.GRAVITITE_RAW_ORES).addOptional(new ResourceLocation("aether_redux", "raw_gravitite"));
        this.tag(TComTags.Items.VERIDIUM_INGOTS).addOptional(new ResourceLocation("aether_redux", "veridium_ingot"));
        this.tag(TComTags.Items.VERIDIUM_NUGGETS).addOptional(new ResourceLocation("aether_redux", "veridium_nugget"));
        this.tag(TComTags.Items.VERIDIUM_BLOCKS).addOptional(new ResourceLocation("aether_redux", "veridium_block"));
        this.tag(TComTags.Items.VERIDIUM_ORES).addOptional(new ResourceLocation("aether_redux", "veridium_ore"));
        this.tag(TComTags.Items.VERIDIUM_RAW_BLOCKS).addOptional(new ResourceLocation("aether_redux", "raw_veridium_block"));
        this.tag(TComTags.Items.VERIDIUM_RAW_ORES).addOptional(new ResourceLocation("aether_redux", "raw_veridium"));
        this.tag(TComTags.Items.REFINED_SENTRITE_INGOTS).addOptional(new ResourceLocation("aether_redux", "refined_sentrite"));
        this.tag(TComTags.Items.REFINED_SENTRITE_NUGGETS).addOptional(new ResourceLocation("aether_redux", "sentrite_chunk"));
        this.tag(TComTags.Items.REFINED_SENTRITE_BLOCKS).addOptional(new ResourceLocation("aether_redux", "refined_sentrite_block"));
        this.tag(TComTags.Items.REFINED_SENTRITE_ORES).addOptional(new ResourceLocation("aether_redux", "sentrite"));

    }

    private void addMetals() {
        this.tag(TinkerTags.Items.ANVIL_METAL)
                .addOptional(new ResourceLocation("%s:%s".formatted("deep_aether", "stratus_block")));
    }

    private void addPlanks() {
        List<MaterialVariantId> noPlanks = List.of(TComMaterialIds.paloVerde);
        // handle otbwg plank tags
        //for (MaterialVariantId materialVariantId : TComMaterialIds.otbwgVariantWoods) {
        //    String woodName = materialVariantId.getVariant();
        //    woodTagging(woodName, "biomeswevegone", noPlanks.contains(materialVariantId));
        //}
        for (MaterialVariantId materialVariantId : TComMaterialIds.otbwgVariantRocks) {
            rockTagging(materialVariantId.getVariant(), "biomeswevegone");
        }
        //for (MaterialVariantId materialVariantId : TComMaterialIds.aetherVariantWoods) {
        //    String woodName = materialVariantId.getVariant();
        //    woodTagging(woodName, "aether", noPlanks.contains(materialVariantId));
        //}
        //for (MaterialVariantId materialVariantId : TComMaterialIds.aetherVariantRocks) {
        //    rockTagging(materialVariantId.getVariant(), "aether");
        //}
        //for (MaterialVariantId materialVariantId : TComMaterialIds.deepAetherVariantWoods) {
        //    String woodName = materialVariantId.getVariant();
        //    woodTagging(woodName, "deep_aether", noPlanks.contains(materialVariantId));
        //}
        for (MaterialVariantId materialVariantId : TComMaterialIds.deepAetherVariantRocks) {
            rockTagging(materialVariantId.getVariant(), "deep_aether");
        }

        for (TComTags.Items.Woods wood : TComTags.Items.Woods.values()) {
            String woodName = wood.name;
            for (String namespace : wood.getNamespaces()) {
                if (!wood.hasPlankRedirect()) {
                    ResourceLocation plankId = wood.getSpecialPlankId(namespace) != null ? wood.getSpecialPlankId(namespace) : new ResourceLocation("%s:%s_planks".formatted(namespace, woodName));
                    this.tag(wood.plankTag()).addOptional(plankId);
                }

                this.tag(TinkerTags.Items.VARIANT_PLANKS)
                        .addOptionalTag(wood.plankTag());
                this.tag(wood.logTag())
                        .addOptionalTag(wood.externalLogTag(namespace));
                this.tag(TinkerTags.Items.VARIANT_LOGS)
                        .addOptionalTag(wood.logTag());
            }
        }
    }

    private void woodTagging(String woodName, String namespace, boolean noPlanks) {
        if (!noPlanks) {
            this.tag(TComTags.Items.Woods.plankTag(woodName))
                    .addOptional(new ResourceLocation("%s:%s_planks".formatted(namespace, woodName)));
            this.tag(TinkerTags.Items.VARIANT_PLANKS)
                    .addOptionalTag(itemTag("%s:%s_planks".formatted(namespace, woodName)));
        }
        this.tag(TComTags.Items.Woods.logTag(woodName))
                .addOptionalTag(itemTag("%s:%s_logs".formatted(namespace, woodName)));
        this.tag(TinkerTags.Items.VARIANT_LOGS)
                .addOptionalTag(itemTag("%s:%s_logs".formatted(namespace, woodName)));
    }

    private void rockTagging(String rockName, String namespace) {
        this.tag(itemTag("tcompat:%s".formatted(rockName)))
                .addOptional(new ResourceLocation("%s:%s".formatted(namespace, rockName)));
        this.tag(TinkerTags.Items.WORKSTATION_ROCK)
                .addOptional(new ResourceLocation("tcompat:%s".formatted(rockName)));
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(name));
    }
}
