package com.pouffydev.tcompat.data.material;

import com.pouffydev.tcompat.data.builder.TComBaseRecipeProvider;
import com.pouffydev.tcompat.material.TComMaterialIds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TComMaterialRecipeProv extends TComBaseRecipeProvider implements IMaterialRecipeHelper {
    public TComMaterialRecipeProv(PackOutput generator, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(generator, lookupProvider);
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Recipes";
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        addMaterialItems(consumer);
        addMaterialSmeltery(consumer);
    }

    private void addMaterialItems(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";
        Consumer<FinishedRecipe> otbwgConsumer = withCondition(consumer, modLoaded("biomeswevegone"));
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, modLoaded("aether"));
        Consumer<FinishedRecipe> deepAetherConsumer = withCondition(consumer, modLoaded("deep_aether"));
        List<MaterialVariantId> noPlanks = List.of(TComMaterialIds.paloVerde);


        for (MaterialVariantId materialVariantId : TComMaterialIds.otbwgVariantWoods) {
            if (!noPlanks.contains(materialVariantId))
                planksVariantRecipe(otbwgConsumer, materialVariantId, "biomeswevegone");
            logVariantRecipe(otbwgConsumer, materialVariantId, "biomeswevegone");
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.otbwgVariantRocks) {
            rockVariantRecipe(otbwgConsumer, materialVariantId, "biomeswevegone");
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.aetherVariantWoods) {
            if (!noPlanks.contains(materialVariantId))
                planksVariantRecipe(aetherConsumer, materialVariantId, "aether");
            logVariantRecipe(aetherConsumer, materialVariantId, "aether");
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.aetherVariantRocks) {
            rockVariantRecipe(aetherConsumer, materialVariantId, "aether");
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.deepAetherVariantWoods) {
            if (!noPlanks.contains(materialVariantId))
                planksVariantRecipe(deepAetherConsumer, materialVariantId, "deep_aether");
            logVariantRecipe(deepAetherConsumer, materialVariantId, "deep_aether");
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.deepAetherVariantRocks) {
            rockVariantRecipe(deepAetherConsumer, materialVariantId, "deep_aether");
        }
    }

    private void addMaterialSmeltery(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";

        //materialMeltingCasting(consumer, GTCMaterialIds.sterlingSilver, GTMaterials.SterlingSilver.getFluid(),    folder);

    }

    private void planksVariantRecipe(Consumer<FinishedRecipe> consumer, MaterialVariantId material, String namespace) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material,      Ingredient.of(itemTag("%s:%s_planks".formatted(namespace, material.getVariant()))),      1, 1, folder + "wood/planks/%s".formatted(material.getVariant()));
    }

    private void logVariantRecipe(Consumer<FinishedRecipe> consumer, MaterialVariantId material, String namespace) {
        String folder = "tools/materials/";
        TagKey<Item> logs = itemTag("%s:%s_logs".formatted(namespace, material.getVariant()));
        materialRecipe(consumer, material,      Ingredient.of(logs),      4, 1, ItemOutput.fromTag(itemTag("%s:%s_planks".formatted(namespace, material.getVariant()))),      folder + "wood/logs/%s".formatted(material.getVariant()));
    }

    private void rockVariantRecipe(Consumer<FinishedRecipe> consumer, MaterialVariantId material, String namespace) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material,      Ingredient.of(itemTag("%s:%s".formatted(namespace, material.getVariant()))),      1, 1, folder + "rock/%s".formatted(material.getVariant()));

    }

    private static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, new ResourceLocation(name));
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(name));
    }
}
