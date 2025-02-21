package com.pouffydev.tcompat.data.tag;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.data.TComTags;
import com.pouffydev.tcompat.material.TComMaterialIds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

import java.util.concurrent.CompletableFuture;

public class TComBlockTagProv extends BlockTagsProvider {
    public TComBlockTagProv(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TCompat.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addPlanks();
    }

    private void addPlanks() {
        // handle otbwg plank tags
        for (MaterialVariantId materialVariantId : TComMaterialIds.otbwgVariantWoods) {
            String woodName = materialVariantId.getVariant();
            this.tag(TComTags.Blocks.Woods.plankTag(woodName))
                    .addOptional(new ResourceLocation("biomeswevegone:%s_planks".formatted(woodName)));
        }
    }

    private static TagKey<Block> blockTag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(name));
    }
}
