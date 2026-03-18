package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.material.TCRocks;
import io.github.pouffy.tcompat.compat.ad_astra.AdAstraInit;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRInit;
import io.github.pouffy.tcompat.compat.betterend.BetterendInit;
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
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.material.MaterialFluidRecipeBuilder;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.pouffy.tcompat.TCompat.getResource;
import static slimeknights.mantle.Mantle.COMMON;
import static slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe.getTemperature;

public class TCMaterialRecipeProv extends TCBaseRecipeProvider implements ITCMaterialRecipeHelper {
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
        Function<String, String> folders =  s -> folder + s + "/";
        Consumer<FinishedRecipe> bopConsumer = withCondition(consumer, modLoaded("biomesoplenty"));
        Consumer<FinishedRecipe> otbwgConsumer = withCondition(consumer, modLoaded("biomeswevegone"));
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, modLoaded("aether"));
        Consumer<FinishedRecipe> deepAetherConsumer = withCondition(consumer, modLoaded("deep_aether"));
        Consumer<FinishedRecipe> aetherReduxConsumer = withCondition(consumer, modLoaded("aether_redux"));
        Consumer<FinishedRecipe> aetherTreasureConsumer = withCondition(consumer, modLoaded("aether_treasure_reforging"));
        Consumer<FinishedRecipe> speciesConsumer = withCondition(consumer, modLoaded("species"));
        Consumer<FinishedRecipe> adAstraConsumer = withCondition(consumer, modLoaded("ad_astra"));
        Consumer<FinishedRecipe> betterend = withCondition(consumer, modLoaded("betterend"));
        List<MaterialVariantId> noPlanks = List.of(TCMaterials.paloVerde);

        // Streamline variant recipes
        TCMaterials.woodVariants.forEach((materialVariantId, woodType) -> {
            if (!noPlanks.contains(materialVariantId))
                planksVariantRecipe(woodType.makeConsumer(consumer), woodType, materialVariantId);
            logVariantRecipe(woodType.makeConsumer(consumer), woodType, materialVariantId);
        });

        TCMaterials.rockVariants.forEach((materialVariantId, rockType) -> {
            rockVariantRecipe(rockType.makeConsumer(consumer), rockType, materialVariantId);
        });

        planksRecipe(bopConsumer, TCWoods.HELLBARK, TCMaterials.hellbark);
        logRecipe(bopConsumer, TCWoods.HELLBARK, TCMaterials.hellbark);
        materialRecipe(consumer, TCMaterials.dripstone, ItemNameIngredient.from(getResource("minecraft:pointed_dripstone")), 1, 1, folder + "rock/%s".formatted("pointed_dripstone"));

        materialRecipe(aetherConsumer, TCMaterials.skyroot, ItemNameIngredient.from(TCompat.getResource("aether:skyroot_stick")), 1, 2, folder + "wood/skyroot_stick");

        //Material Parts
        metalMaterialRecipe(aetherConsumer, TCMaterials.gravitite, folder, "gravitite", true);
        gemMaterialRecipe(aetherConsumer, TCMaterials.zanite, folder, "zanite", true, false, true);

        gemMaterialRecipe(deepAetherConsumer, TCMaterials.skyjade, folder, "skyjade", true, true, true);

        metalMaterialRecipe(aetherReduxConsumer, TCMaterials.veridium, folder, "veridium", true);
        metalMaterialRecipe(aetherReduxConsumer, TCMaterials.refinedSentrite, folder, "refined_sentrite", true);
        materialRecipe(aetherReduxConsumer, TCMaterials.blightbunnyFang, ItemNameIngredient.from(TCompat.getResource("aether_redux:blightbunny_fang")), 1, 1, folder + "blightbunny_fang");
        materialRecipe(aetherReduxConsumer, TCMaterials.mykapodShell, ItemNameIngredient.from(TCompat.getResource("aether_redux:mykapod_shell_chunk")), 1, 1, folder + "mykapod_shell_chunk");

        metalMaterialRecipe(aetherTreasureConsumer, TCMaterials.pyral, folder, "pyral", true);
        metalMaterialRecipe(aetherTreasureConsumer, TCMaterials.valkyrum, folder, "valkyrum", true);
        materialRecipe(aetherTreasureConsumer, TCMaterials.neptune, ItemNameIngredient.from(TCompat.getResource("aether_treasure_reforging:neptune_mesh")), 1, 1, folder + "neptune/mesh");

        metalMaterialRecipe(adAstraConsumer, TCMaterials.desh, folder, "desh", true);
        metalMaterialRecipe(adAstraConsumer, TCMaterials.calorite, folder, "calorite", true);
        metalMaterialRecipe(adAstraConsumer, TCMaterials.ostrum, folder, "ostrum", true);

        metalMaterialRecipe(betterend, TCMaterials.thallasium, folder, "thallasium", true);
        metalMaterialRecipe(betterend, TCMaterials.terminite, folder, "terminite", true);
        metalMaterialRecipe(betterend, TCMaterials.aeternium, folder, "aeternium", true);

        materialRecipe(speciesConsumer, TCMaterials.wickedWax, ItemNameIngredient.from(TCompat.getResource("species:wicked_wax")), 1, 1, folder + "wicked_wax");
    }

    private void addMaterialSmeltery(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, modLoaded("aether"));
        Consumer<FinishedRecipe> aetherReduxConsumer = withCondition(consumer, modLoaded("aether_redux"));
        Consumer<FinishedRecipe> deepAetherConsumer = withCondition(consumer, modLoaded("deep_aether"));
        Consumer<FinishedRecipe> aetherTreasureConsumer = withCondition(consumer, modLoaded("aether_treasure_reforging"));
        Consumer<FinishedRecipe> adAstraConsumer = withCondition(consumer, modLoaded("ad_astra"));
        Consumer<FinishedRecipe> betterend = withCondition(consumer, modLoaded("betterend"));

        materialMeltingCasting(aetherConsumer, TCMaterials.zanite, AetherInit.moltenZanite, FluidValues.INGOT, folder);
        materialMeltingCasting(aetherConsumer, TCMaterials.gravitite, AetherInit.moltenGravitite, folder);
        materialMeltingCasting(deepAetherConsumer, TCMaterials.skyjade, DeepAetherInit.moltenSkyjade, FluidValues.INGOT, folder);
        materialMeltingCasting(aetherReduxConsumer, TCMaterials.veridium, AetherReduxInit.moltenVeridium, folder);
        materialMeltingCasting(aetherReduxConsumer, TCMaterials.refinedSentrite, AetherReduxInit.moltenRefinedSentrite, folder);

        materialMeltingCasting(adAstraConsumer, TCMaterials.desh, AdAstraInit.moltenDesh, FluidValues.INGOT, folder);
        materialMeltingCasting(adAstraConsumer, TCMaterials.calorite, AdAstraInit.moltenCalorite, FluidValues.INGOT, folder);
        materialMeltingCasting(adAstraConsumer, TCMaterials.ostrum, AdAstraInit.moltenOstrum, FluidValues.INGOT, folder);

        materialMeltingCasting(betterend, TCMaterials.thallasium, BetterendInit.moltenThallasium, FluidValues.INGOT, folder);
        materialMeltingCasting(betterend, TCMaterials.terminite, BetterendInit.moltenTerminite, FluidValues.INGOT, folder);
        materialMeltingCasting(betterend, TCMaterials.aeternium, BetterendInit.moltenAeternium, FluidValues.INGOT, folder);

        materialMeltingComposite(aetherTreasureConsumer, TCMaterials.gravitite, TCMaterials.valkyrum, AetherTRInit.moltenValkyrum, FluidValues.INGOT, folder);
        materialMeltingComposite(aetherTreasureConsumer, TCMaterials.gravitite, TCMaterials.pyral, AetherTRInit.moltenPyral, FluidValues.INGOT, folder);
        materialMeltingComposite(aetherTreasureConsumer, TCMaterials.zanite, TCMaterials.neptune, AetherTRInit.moltenNeptune, FluidValues.INGOT, folder);
    }

    private void planksVariantRecipe(Consumer<FinishedRecipe> consumer, TCWoods woodType, MaterialVariantId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material, Ingredient.of(woodType.plankTag()), 1, 1, folder + material.getId().getPath() + "/planks/%s".formatted(material.getVariant()));
    }

    private void logVariantRecipe(Consumer<FinishedRecipe> consumer, TCWoods woodType, MaterialVariantId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material, Ingredient.of(woodType.logTag()), 4, 1, ItemOutput.fromTag(TCWoods.plankTag(material.getVariant())), folder + material.getId().getPath() + "/logs/%s".formatted(material.getVariant()));
    }

    private void rockVariantRecipe(Consumer<FinishedRecipe> consumer, TCRocks rockType, MaterialVariantId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material, Ingredient.of(rockType.rockTag()), 1, 1, folder + material.getId().getPath() + "/%s".formatted(material.getVariant()));
    }

    private void planksRecipe(Consumer<FinishedRecipe> consumer, TCWoods wood, MaterialId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material, Ingredient.of(wood.plankTag()), 1, 1, folder + material.getId().getPath() + "/planks/%s".formatted(material.getPath()));
    }

    private void logRecipe(Consumer<FinishedRecipe> consumer, TCWoods wood, MaterialId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material, Ingredient.of(wood.logTag()), 4, 1, ItemOutput.fromTag(wood.plankTag()), folder + material.getId().getPath() + "/logs/%s".formatted(material.getPath()));
    }

    private void stemRecipe(Consumer<FinishedRecipe> consumer, TCWoods wood, MaterialId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material, Ingredient.of(wood.logTag()), 4, 1, ItemOutput.fromTag(wood.plankTag()), folder + material.getId().getPath() + "/logs/%s".formatted(material.getPath()));
    }

    private void rockRecipe(Consumer<FinishedRecipe> consumer, MaterialId material, String namespace) {
        String folder = "tools/materials/";
        Function<String, ResourceLocation> namespaceFunction = name -> getResource(namespace, name);
        materialRecipe(consumer, material, ItemNameIngredient.from(namespaceFunction.apply(material.getPath())), 1, 1, folder + material.getId().getPath() + "/%s".formatted(material.getPath()));
    }

    private static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.parse(name));
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.parse(name));
    }
}
