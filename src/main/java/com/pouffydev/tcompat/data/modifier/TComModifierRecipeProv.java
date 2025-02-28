package com.pouffydev.tcompat.data.modifier;

import com.pouffydev.tcompat.data.TComTags;
import com.pouffydev.tcompat.data.builder.TComBaseRecipeProvider;
import com.pouffydev.tcompat.material.TComMaterialIds;
import com.pouffydev.tcompat.modifier.TComModifierIds;
import com.pouffydev.tcompat.modifier.TComModifiers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import slimeknights.mantle.recipe.helper.SimpleFinishedRecipe;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.util.LazyModifier;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.SwappableModifierRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.adding.SwappableModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.tables.TinkerTables;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TComModifierRecipeProv extends TComBaseRecipeProvider {

    public TComModifierRecipeProv(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Modifier Recipes";
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        //addItemRecipes(consumer);
        addModifierRecipes(consumer);
        addTextureRecipes(consumer);
        //addHeadRecipes(consumer);
    }

    private void addModifierRecipes(Consumer<FinishedRecipe> consumer) {
        String abilityFolder = "tools/modifiers/ability/";
        String abilitySalvage = "tools/modifiers/salvage/ability/";
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, modLoaded("aether"));

        ModifierRecipeBuilder.modifier(TComModifierIds.aetherForged)
                .setTools(TinkerTags.Items.HARVEST)
                .addInput(TComTags.Items.ZANITE_BLOCKS)
                .addInput(itemTag("aether:golden_oak_logs"))
                .addInput(TComTags.Items.ZANITE_BLOCKS)
                .addInput(Items.GLOWSTONE)
                .addInput(Items.GLOWSTONE)
                .setMaxLevel(1).checkTraitLevel()
                .save(aetherConsumer, prefix(TComModifierIds.aetherForged, abilityFolder));
    }

    private void addTextureRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/modifiers/slotless/";
        Consumer<FinishedRecipe> otbwgConsumer = withCondition(consumer, modLoaded("biomeswevegone"));
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, modLoaded("aether"));
        Consumer<FinishedRecipe> deepAetherConsumer = withCondition(consumer, modLoaded("deep_aether"));


        for (MaterialVariantId materialVariantId : TComMaterialIds.otbwgVariantWoods) {
            woodTexture(otbwgConsumer, materialVariantId);
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.aetherVariantWoods) {
            woodTexture(aetherConsumer, materialVariantId);
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.deepAetherVariantWoods) {
            woodTexture(deepAetherConsumer, materialVariantId);
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.tfVariantWoods) {
            woodTexture(deepAetherConsumer, materialVariantId);
        }
    }

    private void woodTexture(Consumer<FinishedRecipe> consumer, MaterialVariantId material) {
        String folder = "tools/modifiers/slotless/";
        woodTexture(consumer, material, TComTags.Items.Woods.plankTag(material.getVariant()), folder);
    }

    private void woodTexture(Consumer<FinishedRecipe> consumer, MaterialVariantId material, TagKey<Item> planks, String folder) {
        SwappableModifierRecipeBuilder.modifier(TinkerModifiers.embellishment, material.toString())
                .variantFormatter(SwappableModifierRecipe.VariantFormatter.MATERIAL)
                .setTools(TinkerTags.Items.EMBELLISHMENT_WOOD)
                .addInput(planks).addInput(TinkerTables.pattern).addInput(planks)
                .save(consumer, wrap(TinkerModifiers.embellishment, folder, "/wood/" + material.getLocation('_').getPath()));
    }

    public ResourceLocation wrap(LazyModifier modifier, String prefix, String suffix) {
        return wrap(modifier.getId(), prefix, suffix);
    }

    private static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, new ResourceLocation(name));
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(name));
    }
}
