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
    }

    private void addMisc() {
        this.tag(TComTags.Items.NAGA_SCALE).addOptional(new ResourceLocation("twilightforest", "naga_scale"));

        this.tag(TComTags.Items.ARMOR_SHARD).addOptional(new ResourceLocation("twilightforest", "armor_shard"));
        this.tag(TComTags.Items.ARMOR_SHARD_CLUSTER).addOptional(new ResourceLocation("twilightforest", "armor_shard_cluster"));
        this.tag(TComTags.Items.KNIGHTMETAL_LOOP).addOptional(new ResourceLocation("twilightforest", "knightmetal_ring"));

        this.tag(TComTags.Items.ZANITE_BLOCKS).addOptional(new ResourceLocation("aether", "zanite_block"));
    }

    private void addForge() {
        for (TComTags.Items.Oreberries oreberry : TComTags.Items.Oreberries.values()) {
            this.tag(oreberry.tag()).addOptional(oreberry.item());
        }
        this.tag(TComTags.Items.STEELEAF_INGOTS).addOptional(new ResourceLocation("twilightforest", "steeleaf_ingot"));
        this.tag(TComTags.Items.STEELEAF_BLOCKS).addOptional(new ResourceLocation("twilightforest", "steeleaf_block"));
        this.tag(TComTags.Items.FIERY_INGOTS).addOptional(new ResourceLocation("twilightforest", "fiery_ingot"));
        this.tag(TComTags.Items.FIERY_BLOCKS).addOptional(new ResourceLocation("twilightforest", "fiery_block"));
        this.tag(TComTags.Items.KNIGHTMETAL_INGOTS).addOptional(new ResourceLocation("twilightforest", "knightmetal_ingot"));
        this.tag(TComTags.Items.KNIGHTMETAL_BLOCKS).addOptional(new ResourceLocation("twilightforest", "knightmetal_block"));

    }

    private void addMetals() {
        this.tag(TinkerTags.Items.ANVIL_METAL)
                .addOptional(new ResourceLocation("%s:%s".formatted("deep_aether", "stratus_block")));
    }

    private void addPlanks() {
        List<MaterialVariantId> noPlanks = List.of(TComMaterialIds.paloVerde);
        // handle otbwg plank tags
        for (MaterialVariantId materialVariantId : TComMaterialIds.otbwgVariantWoods) {
            String woodName = materialVariantId.getVariant();
            woodTagging(woodName, "biomeswevegone", noPlanks.contains(materialVariantId));
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.otbwgVariantRocks) {
            rockTagging(materialVariantId.getVariant(), "biomeswevegone");
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.aetherVariantWoods) {
            String woodName = materialVariantId.getVariant();
            woodTagging(woodName, "aether", noPlanks.contains(materialVariantId));
        }
        //for (MaterialVariantId materialVariantId : TComMaterialIds.aetherVariantRocks) {
        //    rockTagging(materialVariantId.getVariant(), "aether");
        //}
        for (MaterialVariantId materialVariantId : TComMaterialIds.deepAetherVariantWoods) {
            String woodName = materialVariantId.getVariant();
            woodTagging(woodName, "deep_aether", noPlanks.contains(materialVariantId));
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.deepAetherVariantRocks) {
            rockTagging(materialVariantId.getVariant(), "deep_aether");
        }

        woodTagging("fir", "biomesoplenty", false);
        woodTagging("pine", "biomesoplenty", false);
        woodTagging("maple", "biomesoplenty", false);
        woodTagging("redwood", "biomesoplenty", false);
        woodTagging("mahogany", "biomesoplenty", false);
        woodTagging("jacaranda", "biomesoplenty", false);
        woodTagging("palm", "biomesoplenty", false);
        woodTagging("willow", "biomesoplenty", false);

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
        this.tag(itemTag("%s:%s".formatted(namespace, rockName)))
                .addOptional(new ResourceLocation("%s:%s".formatted(namespace, rockName)));
        this.tag(TinkerTags.Items.WORKSTATION_ROCK)
                .addOptional(new ResourceLocation("%s:%s".formatted(namespace, rockName)));
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(name));
    }
}
