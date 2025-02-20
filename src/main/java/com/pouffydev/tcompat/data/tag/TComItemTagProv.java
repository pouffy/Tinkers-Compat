package com.pouffydev.tcompat.data.tag;

import com.pouffydev.tcompat.TCompat;
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
        //this.tag(itemTag("malum:spirit_fabric")).addOptional(new ResourceLocation("malum:spirit_fabric"));
        //this.tag(itemTag("malum:astral_weave")).addOptional(new ResourceLocation("malum:astral_weave"));
        //this.tag(itemTag("malum:alchemical_calx")).addOptional(new ResourceLocation("malum:alchemical_calx"));
        //this.tag(itemTag("malum:processed_soulstone")).addOptional(new ResourceLocation("malum:processed_soulstone"));
        //this.tag(itemTag("malum:chunk_of_brilliance")).addOptional(new ResourceLocation("malum:chunk_of_brilliance"));
        //this.tag(itemTag("malum:cthonic_gold")).addOptional(new ResourceLocation("malum:cthonic_gold"));
        //this.tag(itemTag("malum:totemic_runes"))
        //        .addOptional(new ResourceLocation("malum:rune_of_motion"))
        //        .addOptional(new ResourceLocation("malum:rune_of_loyalty"))
        //        .addOptional(new ResourceLocation("malum:rune_of_warding"))
        //        .addOptional(new ResourceLocation("malum:rune_of_haste"))
        //        .addOptional(new ResourceLocation("malum:rune_of_the_aether"))
        //        .addOptional(new ResourceLocation("malum:rune_of_the_seas"))
        //        .addOptional(new ResourceLocation("malum:rune_of_the_arena"))
        //        .addOptional(new ResourceLocation("malum:rune_of_the_hells"));
    }

    private void addForge() {
        //this.tag(itemTag("forge:storage_blocks/processed_soulstone")).addOptional(new ResourceLocation("malum:block_of_soulstone"));
        //this.tag(itemTag("forge:storage_blocks/brilliance")).addOptional(new ResourceLocation("malum:block_of_brilliance"));
        //this.tag(itemTag("forge:plating/soul_stained_steel")).addOptional(new ResourceLocation("malum:soul_stained_steel_plating"));
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
    }

    private void woodTagging(String woodName, String namespace, boolean noPlanks) {
        if (!noPlanks) {
            this.tag(itemTag("%s:%s_planks".formatted(namespace, woodName)))
                    .addOptional(new ResourceLocation("%s:%s_planks".formatted(namespace, woodName)));
            this.tag(itemTag("tconstruct:wood_variants/planks"))
                    .addOptionalTag(itemTag("%s:%s_planks".formatted(namespace, woodName)));
        }
        this.tag(itemTag("tconstruct:wood_variants/logs"))
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
