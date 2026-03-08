package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.common.material.TCMaterials;
import io.github.pouffy.tcompat.common.material.TCWoods;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class TCMaterialRecipeProv extends TCBaseRecipeProvider implements IMaterialRecipeHelper {
    public TCMaterialRecipeProv(PackOutput generator) {
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
        Consumer<FinishedRecipe> bopConsumer = withCondition(consumer, modLoaded("biomesoplenty"));
        Consumer<FinishedRecipe> otbwgConsumer = withCondition(consumer, modLoaded("biomeswevegone"));
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, modLoaded("aether"));
        Consumer<FinishedRecipe> deepAetherConsumer = withCondition(consumer, modLoaded("deep_aether"));
        Consumer<FinishedRecipe> aetherReduxConsumer = withCondition(consumer, modLoaded("aether_redux"));
        List<MaterialVariantId> noPlanks = List.of(TCMaterials.paloVerde);
        List<MaterialVariantId> stems = List.of(TCMaterials.cloudcap, TCMaterials.jellyshroom);

        for (MaterialVariantId materialVariantId : TCMaterials.otbwgVariantWoods) {
            if (!noPlanks.contains(materialVariantId))
                planksVariantRecipe(otbwgConsumer, materialVariantId);
            logVariantRecipe(otbwgConsumer, materialVariantId);
        }
        for (MaterialVariantId materialVariantId : TCMaterials.otbwgVariantRocks) {
            rockVariantRecipe(otbwgConsumer, materialVariantId, "biomeswevegone");
        }
        for (MaterialVariantId materialVariantId : TCMaterials.aetherVariantWoods) {
            if (!noPlanks.contains(materialVariantId))
                planksVariantRecipe(aetherConsumer, materialVariantId);
            logVariantRecipe(aetherConsumer, materialVariantId);
        }
        for (MaterialVariantId materialVariantId : TCMaterials.aetherVariantRocks) {
            rockVariantRecipe(aetherConsumer, materialVariantId, "aether");
        }
        for (MaterialVariantId materialVariantId : TCMaterials.deepAetherVariantWoods) {
            if (!noPlanks.contains(materialVariantId))
                planksVariantRecipe(deepAetherConsumer, materialVariantId);
            logVariantRecipe(deepAetherConsumer, materialVariantId);
        }
        for (MaterialVariantId materialVariantId : TCMaterials.deepAetherVariantRocks) {
            rockVariantRecipe(deepAetherConsumer, materialVariantId, "deep_aether");
        }
        for (MaterialVariantId materialVariantId : TCMaterials.aetherReduxVariantWoods) {
            if (!noPlanks.contains(materialVariantId))
                planksVariantRecipe(aetherReduxConsumer, materialVariantId);
            if (!stems.contains(materialVariantId))
                logVariantRecipe(aetherReduxConsumer, materialVariantId);
            else
                stemVariantRecipe(aetherReduxConsumer, materialVariantId);
        }
        for (MaterialVariantId materialVariantId : TCMaterials.aetherReduxVariantRocks) {
            rockVariantRecipe(deepAetherConsumer, materialVariantId, "aether_redux");
        }
        planksRecipe(bopConsumer, TCWoods.HELLBARK, TCMaterials.hellbark);
        logRecipe(bopConsumer, TCWoods.HELLBARK, TCMaterials.hellbark);

        materialRecipe(aetherReduxConsumer, TCMaterials.blightbunnyFang, ItemNameIngredient.from(TCompat.getResource("aether_redux:blightbunny_fang")), 1, 1, folder + "blightbunny_fang");
        materialRecipe(aetherConsumer, TCMaterials.zanite, Ingredient.of(TCTags.Items.ZANITE_GEMS), 1, 1, folder + "zanite");
        materialRecipe(deepAetherConsumer, TCMaterials.skyjade, Ingredient.of(TCTags.Items.SKYJADE_GEMS), 1, 1, folder + "skyjade");
    }

    private void addMaterialSmeltery(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, modLoaded("aether"));
        Consumer<FinishedRecipe> aetherReduxConsumer = withCondition(consumer, modLoaded("aether_redux"));
        Consumer<FinishedRecipe> deepAetherConsumer = withCondition(consumer, modLoaded("deep_aether"));

        materialMeltingCasting(aetherConsumer, TCMaterials.zanite, AetherInit.moltenZanite, FluidValues.GEM, folder);
        materialMeltingCasting(aetherConsumer, TCMaterials.gravitite, AetherInit.moltenGravitite, folder);
        materialMeltingCasting(deepAetherConsumer, TCMaterials.skyjade, DeepAetherInit.moltenSkyjade, FluidValues.GEM, folder);
        materialMeltingCasting(aetherReduxConsumer, TCMaterials.veridium, AetherReduxInit.moltenVeridium, folder);
        materialMeltingCasting(aetherReduxConsumer, TCMaterials.refinedSentrite, AetherReduxInit.moltenRefinedSentrite, folder);
    }

    private void planksVariantRecipe(Consumer<FinishedRecipe> consumer, MaterialVariantId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material, Ingredient.of(TCWoods.plankTag(material.getVariant())), 1, 1, folder + "wood/planks/%s".formatted(material.getVariant()));
    }

    private void logVariantRecipe(Consumer<FinishedRecipe> consumer, MaterialVariantId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material, Ingredient.of(TCWoods.logTag(material.getVariant())), 4, 1, ItemOutput.fromTag(TCWoods.plankTag(material.getVariant())), folder + "wood/logs/%s".formatted(material.getVariant()));
    }

    private void stemVariantRecipe(Consumer<FinishedRecipe> consumer, MaterialVariantId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material, Ingredient.of(TCWoods.stemTag(material.getVariant())), 4, 1, ItemOutput.fromTag(TCWoods.plankTag(material.getVariant())), folder + "wood/logs/%s".formatted(material.getVariant()));
    }

    private void rockVariantRecipe(Consumer<FinishedRecipe> consumer, MaterialVariantId material, String namespace) {
        String folder = "tools/materials/";
        Function<String, ResourceLocation> namespaceFunction = name -> getResource(namespace, name);
        materialRecipe(consumer, material, ItemNameIngredient.from(namespaceFunction.apply(material.getVariant())), 1, 1, folder + "rock/%s".formatted(material.getVariant()));
    }

    private void planksRecipe(Consumer<FinishedRecipe> consumer, TCWoods wood, MaterialId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material, Ingredient.of(wood.plankTag()), 1, 1, folder + "wood/planks/%s".formatted(material.getPath()));
    }

    private void logRecipe(Consumer<FinishedRecipe> consumer, TCWoods wood, MaterialId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material, Ingredient.of(wood.logTag()), 4, 1, ItemOutput.fromTag(wood.plankTag()), folder + "wood/logs/%s".formatted(material.getPath()));
    }

    private void stemRecipe(Consumer<FinishedRecipe> consumer, TCWoods wood, MaterialId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material, Ingredient.of(wood.logTag()), 4, 1, ItemOutput.fromTag(wood.plankTag()), folder + "wood/logs/%s".formatted(material.getPath()));
    }

    private void rockRecipe(Consumer<FinishedRecipe> consumer, MaterialId material, String namespace) {
        String folder = "tools/materials/";
        Function<String, ResourceLocation> namespaceFunction = name -> getResource(namespace, name);
        materialRecipe(consumer, material, ItemNameIngredient.from(namespaceFunction.apply(material.getPath())), 1, 1, folder + "rock/%s".formatted(material.getPath()));
    }

    private static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.parse(name));
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.parse(name));
    }
}
