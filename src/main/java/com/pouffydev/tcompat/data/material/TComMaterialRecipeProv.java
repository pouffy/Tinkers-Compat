package com.pouffydev.tcompat.data.material;

import com.pouffydev.tcompat.data.TComTags;
import com.pouffydev.tcompat.data.builder.TComBaseRecipeProvider;
import com.pouffydev.tcompat.fluids.TComFluids;
import com.pouffydev.tcompat.material.TComMaterialIds;
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

import java.util.List;
import java.util.function.Consumer;

public class TComMaterialRecipeProv extends TComBaseRecipeProvider implements IMaterialRecipeHelper {
    public TComMaterialRecipeProv(PackOutput generator) {
        super(generator);
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
        //Consumer<FinishedRecipe> malumConsumer = withCondition(consumer, modLoaded("malum"));
        Consumer<FinishedRecipe> twilightConsumer = withCondition(consumer, modLoaded("twilightforest"));
        List<MaterialVariantId> noPlanks = List.of(TComMaterialIds.paloVerde);


        for (MaterialVariantId materialVariantId : TComMaterialIds.otbwgVariantWoods) {
            if (!noPlanks.contains(materialVariantId))
                planksVariantRecipe(otbwgConsumer, materialVariantId);
            logVariantRecipe(otbwgConsumer, materialVariantId);
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.otbwgVariantRocks) {
            rockVariantRecipe(otbwgConsumer, materialVariantId, "biomeswevegone");
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.aetherVariantWoods) {
            if (!noPlanks.contains(materialVariantId))
                planksVariantRecipe(aetherConsumer, materialVariantId);
            logVariantRecipe(aetherConsumer, materialVariantId);
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.aetherVariantRocks) {
            rockVariantRecipe(aetherConsumer, materialVariantId, "aether");
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.deepAetherVariantWoods) {
            if (!noPlanks.contains(materialVariantId))
                planksVariantRecipe(deepAetherConsumer, materialVariantId);
            logVariantRecipe(deepAetherConsumer, materialVariantId);
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.deepAetherVariantRocks) {
            rockVariantRecipe(deepAetherConsumer, materialVariantId, "deep_aether");
        }

        materialRecipe(twilightConsumer, TComMaterialIds.ravenFeather, Ingredient.of(TComTags.Items.RAVEN_FEATHER),      1, 1, folder + "raven_feather");
        materialRecipe(twilightConsumer, TComMaterialIds.nagascale, Ingredient.of(TComTags.Items.NAGA_SCALE),      1, 1, folder + "nagascale");
        materialRecipe(twilightConsumer, TComMaterialIds.steeleaf, Ingredient.of(TComTags.Items.STEELEAF_INGOTS),      1, 1, folder + "steeleaf/ingot");
        materialRecipe(twilightConsumer, TComMaterialIds.steeleaf, Ingredient.of(TComTags.Items.STEELEAF_BLOCKS),      9, 1, folder + "steeleaf/block");

    }

    private void addMaterialSmeltery(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";
        Consumer<FinishedRecipe> twilightConsumer = withCondition(consumer, modLoaded("twilightforest"));
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, modLoaded("aether"));
        Consumer<FinishedRecipe> aetherReduxConsumer = withCondition(consumer, modLoaded("aether_redux"));
        Consumer<FinishedRecipe> deepAetherConsumer = withCondition(consumer, modLoaded("deep_aether"));

        materialMeltingCasting(twilightConsumer, TComMaterialIds.fiery, TComFluids.moltenFiery,    folder);
        materialMeltingCasting(twilightConsumer, TComMaterialIds.knightmetal, TComFluids.moltenKnightmetal,    folder);

        materialMeltingCasting(aetherConsumer, TComMaterialIds.zanite, TComFluids.moltenZanite, folder);
        materialMeltingCasting(aetherConsumer, TComMaterialIds.gravitite, TComFluids.moltenGravitite, folder);
        materialMeltingCasting(deepAetherConsumer, TComMaterialIds.skyjade, TComFluids.moltenSkyjade, folder);
        materialMeltingCasting(aetherReduxConsumer, TComMaterialIds.veridium, TComFluids.moltenVeridium, folder);
        materialMeltingCasting(aetherReduxConsumer, TComMaterialIds.refinedSentrite, TComFluids.moltenRefinedSentrite, folder);

    }

    private void planksVariantRecipe(Consumer<FinishedRecipe> consumer, MaterialVariantId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material,      Ingredient.of(TComTags.Items.Woods.plankTag(material.getVariant())),      1, 1, folder + "wood/planks/%s".formatted(material.getVariant()));
    }

    private void logVariantRecipe(Consumer<FinishedRecipe> consumer, MaterialVariantId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material,      Ingredient.of(TComTags.Items.Woods.logTag(material.getVariant())),      4, 1, ItemOutput.fromTag(TComTags.Items.Woods.plankTag(material.getVariant())),      folder + "wood/logs/%s".formatted(material.getVariant()));
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
