package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.material.TCRocks;
import io.github.pouffy.tcompat.compat.ad_astra.AdAstraInit;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRInit;
import io.github.pouffy.tcompat.compat.betterend.BetterendInit;
import io.github.pouffy.tcompat.compat.betternether.BetternetherInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.common.material.TCMaterials;
import io.github.pouffy.tcompat.common.material.TCWoods;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.pouffy.tcompat.TCompat.getResource;

@SuppressWarnings({"unused", "SameParameterValue"})
@MethodsReturnNonnullByDefault
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
        Consumer<FinishedRecipe> iceandfire = withCondition(consumer, modLoaded("iceandfire"));
        List<MaterialVariantId> noPlanks = List.of(TCMaterials.paloVerde);

        // Streamline variant recipes
        TCMaterials.woodVariants.forEach((materialVariantId, woodType) -> {
            if (woodType.hasPlanks())
                planksVariantRecipe(woodType.makeConsumer(consumer), woodType, materialVariantId);
            if (woodType.hasLogs())
                logVariantRecipe(woodType.makeConsumer(consumer), woodType, materialVariantId);
        });

        TCMaterials.rockVariants.forEach((materialVariantId, rockType) -> rockVariantRecipe(rockType.makeConsumer(consumer), rockType, materialVariantId));

        planksRecipe(bopConsumer, TCWoods.HELLBARK, TCMaterials.hellbark);
        logRecipe(bopConsumer, TCWoods.HELLBARK, TCMaterials.hellbark);
        materialRecipe(consumer, TCMaterials.dripstone, ItemNameIngredient.from(getResource("minecraft:pointed_dripstone")), 1, 1, folder + "rock/%s".formatted("pointed_dripstone"));

        materialRecipe(withCondition(consumer, modLoaded("quark")), MaterialIds.cactus, ItemNameIngredient.from(getResource("quark:cactus_block")), 9, 1, folder + "cactus/block");
        materialRecipe(withCondition(consumer, modLoaded("regions_unexplored")), MaterialIds.cactus, ItemNameIngredient.from(getResource("regions_unexplored:barrel_cactus"), getResource("regions_unexplored:saguaro_cactus")), 1, 1, folder + "cactus/regions_unexplored");
        materialRecipe(withCondition(consumer, modLoaded("biomeswevegone")), MaterialIds.cactus, ItemNameIngredient.from(getResource("biomeswevegone:barrel_cactus"), getResource("biomeswevegone:flowering_barrel_cactus")), 1, 1, folder + "cactus/biomeswevegone");
        materialRecipe(withCondition(consumer, modLoaded("biomeswevegone")), MaterialIds.cactus, ItemNameIngredient.from(getResource("biomeswevegone:mini_cactus"), getResource("biomeswevegone:prickly_pear_cactus"), getResource("biomeswevegone:golden_spined_cactus")), 1, 2, folder + "cactus/small/biomeswevegone");
        materialRecipe(withCondition(consumer, modLoaded("biomesoplenty")), MaterialIds.cactus, ItemNameIngredient.from(getResource("biomesoplenty:barrel_cactus")), 1, 2, folder + "cactus/small/biomesoplenty");


        materialRecipe(aetherConsumer, TCMaterials.skyroot, ItemNameIngredient.from(TCompat.getResource("aether:skyroot_stick")), 1, 2, folder + "wood/skyroot_stick");

        //Material Parts
        metalMaterialRecipe(aetherConsumer, TCMaterials.gravitite, folder, "gravitite", true);
        gemMaterialRecipe(aetherConsumer, TCMaterials.zanite, folder, "zanite", true, false, true);
        metalMaterialRecipe(aetherConsumer, TCMaterials.lightnum, folder, "lightnum", true);
        metalMaterialRecipe(aetherConsumer, TCMaterials.draculite, folder, "draculite", true);

        metalMaterialRecipe(iceandfire, TCMaterials.fireDragonsteel, folder, "fire_dragonsteel", true);
        metalMaterialRecipe(iceandfire, TCMaterials.iceDragonsteel, folder, "ice_dragonsteel", true);
        metalMaterialRecipe(iceandfire, TCMaterials.lightningDragonsteel, folder, "lightning_dragonsteel", true);
        materialRecipe(iceandfire, TCMaterials.dragonScaleFire, Ingredient.of(TCTags.Items.FIRE_DRAGON_SCALES), 1, 1, folder + "dragon_scales/fire");
        materialRecipe(iceandfire, TCMaterials.dragonScaleIce, Ingredient.of(TCTags.Items.ICE_DRAGON_SCALES), 1, 1, folder + "dragon_scales/ice");
        materialRecipe(iceandfire, TCMaterials.dragonScaleLightning, Ingredient.of(TCTags.Items.LIGHTNING_DRAGON_SCALES), 1, 1, folder + "dragon_scales/lightning");
        materialRecipe(iceandfire, TCMaterials.dragonBronze, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_bronze")), 1, 1, folder + "dragon_scales/fire/bronze");
        materialRecipe(iceandfire, TCMaterials.dragonGreen, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_green")), 1, 1, folder + "dragon_scales/fire/green");
        materialRecipe(iceandfire, TCMaterials.dragonGray, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_gray")), 1, 1, folder + "dragon_scales/fire/gray");
        materialRecipe(iceandfire, TCMaterials.dragonRed, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_red")), 1, 1, folder + "dragon_scales/fire/red");
        materialRecipe(iceandfire, TCMaterials.dragonBlue, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_blue")), 1, 1, folder + "dragon_scales/ice/blue");
        materialRecipe(iceandfire, TCMaterials.dragonSapphire, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_sapphire")), 1, 1, folder + "dragon_scales/ice/sapphire");
        materialRecipe(iceandfire, TCMaterials.dragonSilver, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_silver")), 1, 1, folder + "dragon_scales/ice/silver");
        materialRecipe(iceandfire, TCMaterials.dragonWhite, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_white")), 1, 1, folder + "dragon_scales/ice/white");
        materialRecipe(iceandfire, TCMaterials.dragonAmethyst, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_amythest")), 1, 1, folder + "dragon_scales/lightning/amethyst");
        materialRecipe(iceandfire, TCMaterials.dragonBlack, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_black")), 1, 1, folder + "dragon_scales/lightning/black");
        materialRecipe(iceandfire, TCMaterials.dragonCopper, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_copper")), 1, 1, folder + "dragon_scales/lightning/copper");
        materialRecipe(iceandfire, TCMaterials.dragonElectric, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_electric")), 1, 1, folder + "dragon_scales/lightning/electric");
        materialRecipe(iceandfire, TCMaterials.dragonBone, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonbone")), 1, 1, folder + "dragon_bone/bone");
        materialRecipe(iceandfire, TCMaterials.dragonBone, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragon_skull_fire")), 4, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragon_bone")), folder + "dragon_bone/fire_skull");
        materialRecipe(iceandfire, TCMaterials.dragonBone, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragon_skull_ice")), 4, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragon_bone")), folder + "dragon_bone/ice_skull");
        materialRecipe(iceandfire, TCMaterials.dragonBone, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragon_skull_lightning")), 4, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragon_bone")), folder + "dragon_bone/lightning_skull");
        materialRecipe(iceandfire, TCMaterials.dragonBone, ItemNameIngredient.from(TCompat.getResource("iceandfire:dragon_bone_block")), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragon_bone")), folder + "dragon_bone/block");
        materialRecipe(iceandfire, MaterialIds.necroticBone, ItemNameIngredient.from(TCompat.getResource("iceandfire:witherbone")), 1, 1, folder + "wither_bone");

        gemMaterialRecipe(deepAetherConsumer, TCMaterials.skyjade, folder, "skyjade", true, true, true);
        metalMaterialRecipe(deepAetherConsumer, TCMaterials.stormforgedSteel, folder, "stormforged_steel", true);

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

        metalMaterialRecipe(betterend, TCMaterials.cincinnasite, folder, "cincinnasite", true);
        gemMaterialRecipe(betterend, TCMaterials.netherRuby, folder, "nether_ruby", true, false, true);

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
        Consumer<FinishedRecipe> betternether = withCondition(consumer, modLoaded("betternether"));
        Consumer<FinishedRecipe> iceandfire = withCondition(consumer, modLoaded("iceandfire"));

        materialMeltingCasting(aetherConsumer, TCMaterials.zanite, AetherInit.moltenZanite, FluidValues.INGOT, folder);
        materialMeltingCasting(aetherConsumer, TCMaterials.gravitite, AetherInit.moltenGravitite, folder);
        materialMeltingCasting(aetherConsumer, TCMaterials.lightnum, AetherInit.moltenLightnum, folder);
        materialMeltingCasting(aetherConsumer, TCMaterials.draculite, AetherInit.moltenDraculite, folder);
        materialMeltingCasting(deepAetherConsumer, TCMaterials.skyjade, DeepAetherInit.moltenSkyjade, FluidValues.INGOT, folder);
        materialMeltingCasting(deepAetherConsumer, TCMaterials.stormforgedSteel, DeepAetherInit.moltenStormforgedSteel, folder);
        materialMeltingCasting(aetherReduxConsumer, TCMaterials.veridium, AetherReduxInit.moltenVeridium, folder);
        materialMeltingCasting(aetherReduxConsumer, TCMaterials.refinedSentrite, AetherReduxInit.moltenRefinedSentrite, folder);

        materialMeltingCasting(adAstraConsumer, TCMaterials.desh, AdAstraInit.moltenDesh, FluidValues.INGOT, folder);
        materialMeltingCasting(adAstraConsumer, TCMaterials.calorite, AdAstraInit.moltenCalorite, FluidValues.INGOT, folder);
        materialMeltingCasting(adAstraConsumer, TCMaterials.ostrum, AdAstraInit.moltenOstrum, FluidValues.INGOT, folder);

        materialMeltingCasting(betterend, TCMaterials.thallasium, BetterendInit.moltenThallasium, FluidValues.INGOT, folder);
        materialMeltingCasting(betterend, TCMaterials.terminite, BetterendInit.moltenTerminite, FluidValues.INGOT, folder);
        materialMeltingCasting(betterend, TCMaterials.aeternium, BetterendInit.moltenAeternium, FluidValues.INGOT, folder);

        materialMeltingCasting(betternether, TCMaterials.cincinnasite, BetternetherInit.moltenCincinnasite, FluidValues.INGOT, folder);
        materialMeltingCasting(betternether, TCMaterials.netherRuby, BetternetherInit.moltenNetherRuby, FluidValues.GEM, folder);

        materialMeltingCasting(iceandfire, TCMaterials.fireDragonsteel, IFInit.moltenFireDragonsteel, FluidValues.INGOT, folder);
        materialMeltingCasting(iceandfire, TCMaterials.iceDragonsteel, IFInit.moltenIceDragonsteel, FluidValues.INGOT, folder);
        materialMeltingCasting(iceandfire, TCMaterials.lightningDragonsteel, IFInit.moltenLightningDragonsteel, FluidValues.INGOT, folder);

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
