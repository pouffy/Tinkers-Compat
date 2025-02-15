package com.pouffydev.tcompat.data.material;

import com.pouffydev.tcompat.data.builder.TComBaseRecipeProvider;
import com.pouffydev.tcompat.material.TComMaterialIds;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
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


        // planks
        for (MaterialVariantId materialVariantId : TComMaterialIds.otbwgVariants) {
            planksVariantRecipe(consumer, materialVariantId, "biomeswevegone");
            logVariantRecipe(consumer, materialVariantId, "biomeswevegone");
        }

        //materialRecipe(consumer, GTCMaterialIds.siliconeRubber,      Ingredient.of(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.ingot, GTMaterials.SiliconeRubber)),      1, 1, folder + "silicone_rubber/ingot");
        //materialRecipe(consumer, GTCMaterialIds.siliconeRubber,      Ingredient.of(GTMaterialItems.MATERIAL_ITEMS.get(TagPrefix.nugget, GTMaterials.SiliconeRubber)),      1, 9, folder + "silicone_rubber/nugget");
        //materialRecipe(consumer, GTCMaterialIds.siliconeRubber,      Ingredient.of(ChemicalHelper.getBlock(TagPrefix.block, GTMaterials.SiliconeRubber)),      9, 1, folder + "silicone_rubber/block");

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

    private static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, new ResourceLocation(name));
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(name));
    }
}
