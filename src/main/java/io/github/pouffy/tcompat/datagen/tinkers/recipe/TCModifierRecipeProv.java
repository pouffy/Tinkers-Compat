package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.material.TCMaterials;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.common.material.TCWoods;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.recipe.AmbrofusionModifierRecipeBuilder;
import io.github.pouffy.tcompat.compat.species.SpeciesInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.CompoundIngredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.util.LazyModifier;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.SwappableModifierRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.adding.SwappableModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.tables.TinkerTables;
import slimeknights.tconstruct.tools.TinkerModifiers;

import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class TCModifierRecipeProv extends TCBaseRecipeProvider {

    public TCModifierRecipeProv(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Modifier Recipes";
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        addModifierRecipes(consumer);
        addTextureRecipes(consumer);
    }

    private void addModifierRecipes(Consumer<FinishedRecipe> consumer) {
        String abilityFolder = "tools/modifiers/ability/";
        String abilitySalvage = "tools/modifiers/salvage/ability/";
        String slotlessFolder = "tools/modifiers/slotless/";
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, modLoaded("aether"));
        Consumer<FinishedRecipe> speciesConsumer = withCondition(consumer, modLoaded("species"));

        Function<String, ResourceLocation> speciesId = name -> getResource("species", name);
        Function<String, ResourceLocation> aetherId = name -> getResource("aether", name);

        ModifierRecipeBuilder.modifier(SpeciesInit.ricoshield)
                .setTools(TinkerTags.Items.SHIELDS)
                .addInput(ItemNameIngredient.from(speciesId.apply("broken_links")))
                .addInput(ItemNameIngredient.from(speciesId.apply("kinetic_core")))
                .addInput(ItemNameIngredient.from(speciesId.apply("broken_links")))
                .addInput(ItemNameIngredient.from(speciesId.apply("broken_links")))
                .addInput(ItemNameIngredient.from(speciesId.apply("broken_links")))
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.DEFENSE, 1)
                .save(speciesConsumer, prefix(SpeciesInit.ricoshield, abilityFolder));

        ModifierRecipeBuilder.modifier(SpeciesInit.swapping)
                .setTools(TinkerTags.Items.RANGED)
                .addInput(ItemNameIngredient.from(speciesId.apply("wicked_swapper")))
                .addInput(Items.ENDER_PEARL)
                .addInput(ItemNameIngredient.from(speciesId.apply("wicked_swapper")))
                .addInput(ItemNameIngredient.from(speciesId.apply("wicked_wax")))
                .addInput(ItemNameIngredient.from(speciesId.apply("wicked_wax")))
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.ABILITY, 1)
                .save(speciesConsumer, prefix(SpeciesInit.swapping, abilityFolder));

        ModifierRecipeBuilder.modifier(SpeciesInit.birt)
                .setTools(TinkerTags.Items.MELEE_WEAPON)
                .addInput(ItemNameIngredient.from(speciesId.apply("birt_egg")))
                .addInput(Items.REDSTONE)
                .addInput(ItemNameIngredient.from(speciesId.apply("birt_egg")))
                .addInput(Tags.Items.INGOTS_IRON)
                .addInput(Tags.Items.INGOTS_IRON)
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.ABILITY, 1)
                .save(speciesConsumer, prefix(SpeciesInit.birt, abilityFolder));

        ModifierRecipeBuilder.modifier(AetherInit.autochant)
                .setTools(ingredientFromTags(TinkerTags.Items.HARVEST, TinkerTags.Items.FISHING_RODS))
                .addInput(TCTags.Items.AUTOCHANT_LEFT)
                .addInput(ItemNameIngredient.from(aetherId.apply("altar")))
                .addInput(TCTags.Items.AUTOCHANT_RIGHT)
                .addInput(ItemNameIngredient.from(aetherId.apply("ambrosium_block")))
                .addInput(ItemNameIngredient.from(aetherId.apply("ambrosium_block")))
                .setMaxLevel(1).setSlots(SlotType.ABILITY, 1)
                .saveSalvage(consumer, prefix(AetherInit.autochant, abilitySalvage))
                .save(aetherConsumer, prefix(AetherInit.autochant, abilityFolder));

        ModifierRecipeBuilder.modifier(AetherInit.autofreeze)
                .setTools(ingredientFromTags(TinkerTags.Items.HARVEST, TinkerTags.Items.FISHING_RODS))
                .addInput(TCTags.Items.AUTOFREEZE_LEFT)
                .addInput(ItemNameIngredient.from(aetherId.apply("freezer")))
                .addInput(TCTags.Items.AUTOFREEZE_RIGHT)
                .addInput(ItemNameIngredient.from(aetherId.apply("icestone")))
                .addInput(ItemNameIngredient.from(aetherId.apply("icestone")))
                .setMaxLevel(1).setSlots(SlotType.ABILITY, 1)
                .saveSalvage(consumer, prefix(AetherInit.autofreeze, abilitySalvage))
                .save(aetherConsumer, prefix(AetherInit.autofreeze, abilityFolder));

        AmbrofusionModifierRecipeBuilder.modifier(ItemNameIngredient.from(aetherId.apply("ambrosium_shard")), 4)
                .save(consumer, location(slotlessFolder + "ambrofusion/ambrosium_shard"));
        AmbrofusionModifierRecipeBuilder.modifier(ItemNameIngredient.from(aetherId.apply("ambrosium_block")), 36)
                .save(consumer, location(slotlessFolder + "ambrofusion/ambrosium_block"));
    }

    private void addTextureRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/modifiers/slotless/";
        Consumer<FinishedRecipe> otbwgConsumer = withCondition(consumer, modLoaded("biomeswevegone"));
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, modLoaded("aether"));
        Consumer<FinishedRecipe> deepAetherConsumer = withCondition(consumer, modLoaded("deep_aether"));

        ModifierRecipeBuilder.modifier(TCModifiers.aetherForged)
                .setTools(TinkerTags.Items.HARVEST)
                .addInput(TCTags.Items.ZANITE_BLOCKS)
                .addInput(itemTag("aether:golden_oak_logs"))
                .addInput(TCTags.Items.ZANITE_BLOCKS)
                .addInput(Items.GLOWSTONE)
                .addInput(Items.GLOWSTONE)
                .setMaxLevel(1).checkTraitLevel()
                .save(aetherConsumer, prefix(TCModifiers.aetherForged, folder));

        for (MaterialVariantId materialVariantId : TCMaterials.otbwgVariantWoods) {
            woodTexture(otbwgConsumer, materialVariantId);
        }
        for (MaterialVariantId materialVariantId : TCMaterials.aetherVariantWoods) {
            woodTexture(aetherConsumer, materialVariantId);
        }
        for (MaterialVariantId materialVariantId : TCMaterials.deepAetherVariantWoods) {
            woodTexture(deepAetherConsumer, materialVariantId);
        }
    }

    private void woodTexture(Consumer<FinishedRecipe> consumer, MaterialVariantId material) {
        String folder = "tools/modifiers/slotless/";
        woodTexture(consumer, material, TCWoods.plankTag(material.getVariant()), folder);
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
        return ResourceKey.create(Registries.ITEM, ResourceLocation.parse(name));
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.parse(name));
    }

    @SafeVarargs
    private static Ingredient ingredientFromTags(TagKey<Item>... tags) {
        Ingredient[] tagIngredients = new Ingredient[tags.length];
        for (int i = 0; i < tags.length; i++) {
            tagIngredients[i] = Ingredient.of(tags[i]);
        }
        return CompoundIngredient.of(tagIngredients);
    }
}
