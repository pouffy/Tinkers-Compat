package com.pouffydev.tcompat.data.modifier;

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

    public TComModifierRecipeProv(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
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
        Consumer<FinishedRecipe> malumConsumer = withCondition(consumer, modLoaded("malum"));

        ModifierRecipeBuilder.modifier(TComModifierIds.aetherForged)
                .setTools(TinkerTags.Items.HARVEST)
                .addInput(itemTag("aether:gems/zanite"))
                .addInput(itemTag("aether:golden_oak_logs"))
                .addInput(itemTag("aether:gems/zanite"))
                .addInput(Items.GLOWSTONE)
                .addInput(Items.GLOWSTONE)
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.ABILITY, 1)
                .saveSalvage(aetherConsumer, prefix(TComModifierIds.aetherForged, abilitySalvage))
                .save(aetherConsumer, prefix(TComModifierIds.aetherForged, abilityFolder));

        //ModifierRecipeBuilder.modifier(TComModifierIds.magicProficiency)
        //        .setTools(TinkerTags.Items.ARMOR)
        //        .addInput(itemTag("malum:spirit_fabric"))
        //        .addInput(itemTag("malum:processed_soulstone"))
        //        .addInput(itemTag("malum:spirit_fabric"))
        //        .addInput(itemTag("malum:spirit_fabric"))
        //        .addInput(itemTag("malum:spirit_fabric"))
        //        .setMaxLevel(1)
        //        .setSlots(SlotType.ABILITY, 1)
        //        .allowCrystal()
        //        .save(malumConsumer, wrap(TComModifierIds.magicProficiency, abilityFolder, "_level_1"));
        //ModifierRecipeBuilder.modifier(TComModifierIds.magicProficiency)
        //        .setTools(TinkerTags.Items.ARMOR)
        //        .addInput(itemTag("malum:spirit_fabric"))
        //        .addInput(itemTag("malum:chunk_of_brilliance"))
        //        .addInput(itemTag("malum:spirit_fabric"))
        //        .addInput(itemTag("malum:processed_soulstone"))
        //        .addInput(itemTag("malum:processed_soulstone"))
        //        .exactLevel(2)
        //        .disallowCrystal()
        //        .save(malumConsumer, wrap(TComModifierIds.magicProficiency, abilityFolder, "_level_2"));
        //ModifierRecipeBuilder.modifier(TComModifierIds.magicProficiency)
        //        .setTools(TinkerTags.Items.ARMOR)
        //        .addInput(itemTag("malum:spirit_fabric"))
        //        .addInput(itemTag("forge:storage_blocks/brilliance"))
        //        .addInput(itemTag("malum:spirit_fabric"))
        //        .addInput(itemTag("forge:storage_blocks/processed_soulstone"))
        //        .addInput(itemTag("forge:storage_blocks/processed_soulstone"))
        //        .exactLevel(3)
        //        .disallowCrystal()
        //        .save(malumConsumer, wrap(TComModifierIds.magicProficiency, abilityFolder, "_level_3"));

        //ModifierRecipeBuilder.modifier(TComModifierIds.soulWarding)
        //        .setTools(TinkerTags.Items.ARMOR)
        //        .addInput(itemTag("malum:processed_soulstone"))
        //        .addInput(itemTag("malum:twisted_rock_blocks"))
        //        .addInput(itemTag("malum:processed_soulstone"))
        //        .addInput(itemTag("forge:plating/soul_stained_steel"))
        //        .addInput(itemTag("forge:plating/soul_stained_steel"))
        //        .setMaxLevel(1)
        //        .setSlots(SlotType.ABILITY, 1)
        //        .allowCrystal()
        //        .save(malumConsumer, wrap(TComModifierIds.soulWarding, abilityFolder, "_level_1"));
        //ModifierRecipeBuilder.modifier(TComModifierIds.soulWarding)
        //        .setTools(TinkerTags.Items.ARMOR)
        //        .addInput(itemTag("forge:storage_blocks/processed_soulstone"))
        //        .addInput(itemTag("malum:twisted_rock_blocks"))
        //        .addInput(itemTag("forge:storage_blocks/processed_soulstone"))
        //        .addInput(itemTag("forge:plating/soul_stained_steel"))
        //        .addInput(itemTag("forge:plating/soul_stained_steel"))
        //        .exactLevel(2)
        //        .disallowCrystal()
        //        .save(malumConsumer, wrap(TComModifierIds.soulWarding, abilityFolder, "_level_2"));
        //ModifierRecipeBuilder.modifier(TComModifierIds.soulWarding)
        //        .setTools(TinkerTags.Items.ARMOR)
        //        .addInput(itemTag("forge:storage_blocks/processed_soulstone"))
        //        .addInput(itemTag("malum:cthonic_gold"))
        //        .addInput(itemTag("forge:storage_blocks/processed_soulstone"))
        //        .addInput(itemTag("forge:plating/soul_stained_steel"))
        //        .addInput(itemTag("forge:plating/soul_stained_steel"))
        //        .exactLevel(3)
        //        .disallowCrystal()
        //        .save(malumConsumer, wrap(TComModifierIds.soulWarding, abilityFolder, "_level_3"));

        //malumConsumer.accept(new SimpleFinishedRecipe(location(abilityFolder + "totemic_rune"), TComModifiers.totemicRuneSerializer.get()));
    }

    private void addTextureRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/modifiers/slotless/";
        Consumer<FinishedRecipe> otbwgConsumer = withCondition(consumer, modLoaded("biomeswevegone"));
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, modLoaded("aether"));
        Consumer<FinishedRecipe> deepAetherConsumer = withCondition(consumer, modLoaded("deep_aether"));


        for (MaterialVariantId materialVariantId : TComMaterialIds.otbwgVariantWoods) {
            woodTexture(otbwgConsumer, materialVariantId, "biomeswevegone");
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.aetherVariantWoods) {
            woodTexture(aetherConsumer, materialVariantId, "aether");
        }
        for (MaterialVariantId materialVariantId : TComMaterialIds.deepAetherVariantWoods) {
            woodTexture(deepAetherConsumer, materialVariantId, "deep_aether");
        }
    }

    private void woodTexture(Consumer<FinishedRecipe> consumer, MaterialVariantId material, String namespace) {
        String folder = "tools/modifiers/slotless/";
        woodTexture(consumer, material, itemTag("%s:%s_planks".formatted(namespace, material.getVariant())), folder);
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
