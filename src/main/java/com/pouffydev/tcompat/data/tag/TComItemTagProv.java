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
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

import java.util.concurrent.CompletableFuture;

public class TComItemTagProv extends ItemTagsProvider {
    public TComItemTagProv(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagProvider, TCompat.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addPlanks();
    }

    private void addPlanks() {
        // handle otbwg plank tags
        for (MaterialVariantId materialVariantId : TComMaterialIds.otbwgVariants) {
            String woodName = materialVariantId.getVariant();
            woodTagging(woodName, "biomeswevegone");
        }
    }

    private void woodTagging(String woodName, String namespace) {
        this.tag(itemTag("%s:%s_planks".formatted(namespace, woodName)))
                .addOptional(new ResourceLocation("%s:%s_planks".formatted(namespace, woodName)));
        this.tag(itemTag("tconstruct:wood_variants/planks"))
                .addOptionalTag(itemTag("%s:%s_planks".formatted(namespace, woodName)));
        this.tag(itemTag("tconstruct:wood_variants/logs"))
                .addOptionalTag(itemTag("%s:%s_logs".formatted(namespace, woodName)));
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(name));
    }
}
