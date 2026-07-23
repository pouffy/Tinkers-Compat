package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.fluid.TCFluids;
import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCRocks;
import io.github.pouffy.tcompat.common.material.TCWoods;
import io.github.pouffy.tcompat.compat.RockMaterials;
import io.github.pouffy.tcompat.compat.WoodMaterials;
import io.github.pouffy.tcompat.compat.ad_astra.AdAstraMaterials;
import io.github.pouffy.tcompat.compat.aether.AetherMaterials;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxMaterials;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRMaterials;
import io.github.pouffy.tcompat.compat.betterend.BetterendMaterials;
import io.github.pouffy.tcompat.compat.betternether.BetternetherMaterials;
import io.github.pouffy.tcompat.compat.bwg.BWGMaterials;
import io.github.pouffy.tcompat.compat.cataclysm.CataclysmMaterials;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherMaterials;
import io.github.pouffy.tcompat.compat.deeperdarker.DarkerMaterials;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFMaterials;
import io.github.pouffy.tcompat.compat.malum.MalumMaterials;
import io.github.pouffy.tcompat.compat.species.SpeciesMaterials;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ICondition;
import slimeknights.mantle.recipe.condition.TagFilledCondition;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

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
        Consumer<FinishedRecipe> cataclysm = withCondition(consumer, modLoaded("cataclysm"));
        Consumer<FinishedRecipe> malum = withCondition(consumer, modLoaded("malum"));

        // Streamline variant recipes
        MaterialBuilder.woodMaterials.forEach((builder, woodType) -> {
            if (woodType.hasPlanks())
                planksVariantRecipe(woodType.makeConsumer(consumer), woodType, builder.variantId());
            if (woodType.hasLogs())
                logVariantRecipe(woodType.makeConsumer(consumer), woodType, builder.variantId());
        });

        planksRecipe(TCWoods.RUNEWOOD.makeConsumer(consumer), TCWoods.RUNEWOOD, MalumMaterials.runewood);
        logRecipe(TCWoods.RUNEWOOD.makeConsumer(consumer), TCWoods.RUNEWOOD, MalumMaterials.runewood);
        planksVariantRecipe(TCWoods.SOULWOOD.makeConsumer(consumer), TCWoods.SOULWOOD, MalumMaterials.soulwood);
        logVariantRecipe(TCWoods.SOULWOOD.makeConsumer(consumer), TCWoods.SOULWOOD, MalumMaterials.soulwood);
        rockRecipe(TCRocks.TAINTED_ROCK.makeConsumer(consumer), TCRocks.TAINTED_ROCK, MalumMaterials.taintedRock);
        rockVariantRecipe(TCRocks.TWISTED_ROCK.makeConsumer(consumer), TCRocks.TWISTED_ROCK, MalumMaterials.twistedRock);

        MaterialBuilder.rockMaterials.forEach((builder, rockType) -> rockVariantRecipe(rockType.makeConsumer(consumer), rockType, builder.variantId()));

        planksRecipe(TCWoods.HELLBARK.makeConsumer(consumer), TCWoods.HELLBARK, WoodMaterials.hellbark);
        logRecipe(TCWoods.HELLBARK.makeConsumer(consumer), TCWoods.HELLBARK, WoodMaterials.hellbark);
        materialRecipe(consumer, RockMaterials.dripstone, ItemNameIngredient.from(getResource("minecraft:pointed_dripstone")), 1, 1, folder + "rock/%s".formatted("pointed_dripstone"));

        // Cacti
        materialRecipe(withCondition(consumer, modLoaded("quark")), MaterialIds.cactus, ItemNameIngredient.from(getResource("quark:cactus_block")), 9, 1, folder + "cactus/block");
        materialRecipe(withCondition(consumer, modLoaded("regions_unexplored")), MaterialIds.cactus, ItemNameIngredient.from(getResource("regions_unexplored:barrel_cactus"), getResource("regions_unexplored:saguaro_cactus")), 1, 1, folder + "cactus/regions_unexplored");
        materialRecipe(withCondition(consumer, modLoaded("biomeswevegone")), MaterialIds.cactus, ItemNameIngredient.from(getResource("biomeswevegone:barrel_cactus"), getResource("biomeswevegone:flowering_barrel_cactus")), 1, 1, folder + "cactus/biomeswevegone");
        materialRecipe(withCondition(consumer, modLoaded("biomeswevegone")), MaterialIds.cactus, ItemNameIngredient.from(getResource("biomeswevegone:mini_cactus"), getResource("biomeswevegone:prickly_pear_cactus"), getResource("biomeswevegone:golden_spined_cactus")), 1, 2, folder + "cactus/small/biomeswevegone");
        materialRecipe(withCondition(consumer, modLoaded("biomesoplenty")), MaterialIds.cactus, ItemNameIngredient.from(getResource("biomesoplenty:tiny_cactus")), 1, 2, folder + "cactus/small/biomesoplenty");

        // Vines
        materialRecipe(withCondition(consumer, tagFilled(TCTags.Items.ALTERNATIVE_VINES)), MaterialIds.vine, Ingredient.of(TCTags.Items.ALTERNATIVE_VINES), 1, 1, folder + "vine/alternative");
        materialRecipe(withCondition(consumer, modLoaded("biomeswevegone")), BWGMaterials.skyrisVine, ItemNameIngredient.from(TCompat.getResource("biomeswevegone:skyris_vine")), 1, 1, folder + "vine/skyris");
        materialRecipe(withCondition(consumer, modLoaded("deep_aether")), DeepAetherMaterials.yagrootVine, ItemNameIngredient.from(TCompat.getResource("deep_aether:yagroot_vine")), 1, 1, folder + "vine/yagroot");
        materialRecipe(withCondition(consumer, modLoaded("betternether")), BetternetherMaterials.blackVine, ItemNameIngredient.from(TCompat.getResource("betternether:black_vine")), 1, 1, folder + "weeping_vine/black_vine");
        materialRecipe(withCondition(consumer, modLoaded("betternether")), BetternetherMaterials.goldenVine, ItemNameIngredient.from(TCompat.getResource("betternether:golden_vine")), 1, 1, folder + "golden_vine");
        materialRecipe(withCondition(consumer, modLoaded("betternether")), BetternetherMaterials.bloomingVine, ItemNameIngredient.from(TCompat.getResource("betternether:blooming_vine")), 1, 1, folder + "blooming_vine");
        materialRecipe(withCondition(consumer, modLoaded("betterend")), BetterendMaterials.twistedVine, ItemNameIngredient.from(TCompat.getResource("betterend:twisted_vine")), 1, 1, folder + "twisting_vine/twisted_vine");
        materialRecipe(withCondition(consumer, modLoaded("betterend")), BetterendMaterials.jungleVine, ItemNameIngredient.from(TCompat.getResource("betterend:jungle_vine")), 1, 1, folder + "twisting_vine/jungle_vine");
        materialRecipe(withCondition(consumer, modLoaded("betterend")), BetterendMaterials.bulbVine, ItemNameIngredient.from(TCompat.getResource("betterend:bulb_vine")), 1, 1, folder + "bulb_vine");
        materialRecipe(withCondition(consumer, modLoaded("betterend")), BetterendMaterials.denseVine, ItemNameIngredient.from(TCompat.getResource("betterend:dense_vine")), 1, 1, folder + "dense_vine");
        materialRecipe(withCondition(consumer, modLoaded("aether_redux")), AetherReduxMaterials.corruptedVine, ItemNameIngredient.from(TCompat.getResource("aether_redux:corrupted_vines")), 1, 1, folder + "corrupted_vine");
        materialRecipe(withCondition(consumer, modLoaded("aether_redux")), AetherReduxMaterials.goldVine, ItemNameIngredient.from(TCompat.getResource("aether_redux:golden_vines")), 1, 1, folder + "gold_vine");
        materialRecipe(withCondition(consumer, modLoaded("deeperdarker")), DarkerMaterials.sculkVine, ItemNameIngredient.from(TCompat.getResource("deeperdarker:sculk_vines"), TCompat.getResource("deeperdarker:sculk_tendrils")), 1, 1, folder + "sculk_vine");


        materialRecipe(aetherConsumer, AetherMaterials.skyroot, ItemNameIngredient.from(TCompat.getResource("aether:skyroot_stick")), 1, 2, folder + "wood/skyroot_stick");

        //Material Parts
        gemMaterialRecipe(aetherConsumer, AetherMaterials.zanite, folder, "zanite", true, false, true);
        metalMaterialRecipe(aetherConsumer, AetherMaterials.gravitite, folder, "gravitite", true);
        metalMaterialRecipe(aetherConsumer, AetherMaterials.lightnum, folder, "lightnum", true);
        metalMaterialRecipe(aetherConsumer, AetherMaterials.draculite, folder, "draculite", true);

        metalMaterialRecipe(iceandfire, IFMaterials.fireDragonsteel, folder, "fire_dragonsteel", true);
        metalMaterialRecipe(iceandfire, IFMaterials.iceDragonsteel, folder, "ice_dragonsteel", true);
        metalMaterialRecipe(iceandfire, IFMaterials.lightningDragonsteel, folder, "lightning_dragonsteel", true);
        materialRecipe(iceandfire, IFMaterials.dragonBronze,            ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_bronze"                )), 1, 1, folder + "dragon_scales/fire/bronze");
        materialRecipe(iceandfire, IFMaterials.dragonGreen,             ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_green"                 )), 1, 1, folder + "dragon_scales/fire/green");
        materialRecipe(iceandfire, IFMaterials.dragonGray,              ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_gray"                  )), 1, 1, folder + "dragon_scales/fire/gray");
        materialRecipe(iceandfire, IFMaterials.dragonRed,               ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_red"                   )), 1, 1, folder + "dragon_scales/fire/red");
        materialRecipe(iceandfire, IFMaterials.dragonBlue,              ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_blue"                  )), 1, 1, folder + "dragon_scales/ice/blue");
        materialRecipe(iceandfire, IFMaterials.dragonSapphire,          ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_sapphire"              )), 1, 1, folder + "dragon_scales/ice/sapphire");
        materialRecipe(iceandfire, IFMaterials.dragonSilver,            ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_silver"                )), 1, 1, folder + "dragon_scales/ice/silver");
        materialRecipe(iceandfire, IFMaterials.dragonWhite,             ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_white"                 )), 1, 1, folder + "dragon_scales/ice/white");
        materialRecipe(iceandfire, IFMaterials.dragonAmethyst,          ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_amythest"              )), 1, 1, folder + "dragon_scales/lightning/amethyst");
        materialRecipe(iceandfire, IFMaterials.dragonBlack,             ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_black"                 )), 1, 1, folder + "dragon_scales/lightning/black");
        materialRecipe(iceandfire, IFMaterials.dragonCopper,            ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_copper"                )), 1, 1, folder + "dragon_scales/lightning/copper");
        materialRecipe(iceandfire, IFMaterials.dragonElectric,          ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscales_electric"              )), 1, 1, folder + "dragon_scales/lightning/electric");
        materialRecipe(iceandfire, IFMaterials.dragonBronze,            ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscale_bronze"                 )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonscales_bronze"  )), folder + "dragon_scales/fire/bronze_block");
        materialRecipe(iceandfire, IFMaterials.dragonGreen,             ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscale_green"                  )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonscales_green"   )), folder + "dragon_scales/fire/green_block");
        materialRecipe(iceandfire, IFMaterials.dragonGray,              ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscale_gray"                   )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonscales_gray"    )), folder + "dragon_scales/fire/gray_block");
        materialRecipe(iceandfire, IFMaterials.dragonRed,               ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscale_red"                    )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonscales_red"     )), folder + "dragon_scales/fire/red_block");
        materialRecipe(iceandfire, IFMaterials.dragonBlue,              ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscale_blue"                   )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonscales_blue"    )), folder + "dragon_scales/ice/blue_block");
        materialRecipe(iceandfire, IFMaterials.dragonSapphire,          ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscale_sapphire"               )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonscales_sapphire")), folder + "dragon_scales/ice/sapphire_block");
        materialRecipe(iceandfire, IFMaterials.dragonSilver,            ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscale_silver"                 )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonscales_silver"  )), folder + "dragon_scales/ice/silver_block");
        materialRecipe(iceandfire, IFMaterials.dragonWhite,             ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscale_white"                  )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonscales_white"   )), folder + "dragon_scales/ice/white_block");
        materialRecipe(iceandfire, IFMaterials.dragonAmethyst,          ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscale_amythest"               )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonscales_amythest")), folder + "dragon_scales/lightning/amethyst_block");
        materialRecipe(iceandfire, IFMaterials.dragonBlack,             ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscale_black"                  )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonscales_black"   )), folder + "dragon_scales/lightning/black_block");
        materialRecipe(iceandfire, IFMaterials.dragonCopper,            ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscale_copper"                 )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonscales_copper"  )), folder + "dragon_scales/lightning/copper_block");
        materialRecipe(iceandfire, IFMaterials.dragonElectric,          ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonscale_electric"               )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonscales_electric")), folder + "dragon_scales/lightning/electric_block");
        materialRecipe(iceandfire, IFMaterials.dragonBone,              ItemNameIngredient.from(TCompat.getResource("iceandfire:dragonbone"                         )), 1, 1, folder + "dragon_bone/bone");
        materialRecipe(iceandfire, IFMaterials.dragonBone,              ItemNameIngredient.from(TCompat.getResource("iceandfire:dragon_skull_fire"                  )), 4, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonbone")), folder + "dragon_bone/fire_skull");
        materialRecipe(iceandfire, IFMaterials.dragonBone,              ItemNameIngredient.from(TCompat.getResource("iceandfire:dragon_skull_ice"                   )), 4, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonbone")), folder + "dragon_bone/ice_skull");
        materialRecipe(iceandfire, IFMaterials.dragonBone,              ItemNameIngredient.from(TCompat.getResource("iceandfire:dragon_skull_lightning"             )), 4, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonbone")), folder + "dragon_bone/lightning_skull");
        materialRecipe(iceandfire, IFMaterials.dragonBone,              ItemNameIngredient.from(TCompat.getResource("iceandfire:dragon_bone_block"                  )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:dragonbone")), folder + "dragon_bone/block");
        materialRecipe(iceandfire, MaterialIds.necroticBone,            ItemNameIngredient.from(TCompat.getResource("iceandfire:witherbone"                         )), 1, 1, folder + "necrotic_bone/wither_bone");
        materialRecipe(iceandfire, MaterialIds.necroticBone,            ItemNameIngredient.from(TCompat.getResource("iceandfire:wither_shard"                       )), 1, 3, folder + "necrotic_bone/wither_shard");
        materialRecipe(iceandfire, IFMaterials.serpentBlue,             ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_scales_blue"            )), 1, 1, folder + "sea_serpent_scales/blue");
        materialRecipe(iceandfire, IFMaterials.serpentBronze,           ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_scales_bronze"          )), 1, 1, folder + "sea_serpent_scales/bronze");
        materialRecipe(iceandfire, IFMaterials.serpentDeepBlue,         ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_scales_deepblue"        )), 1, 1, folder + "sea_serpent_scales/deep_blue");
        materialRecipe(iceandfire, IFMaterials.serpentGreen,            ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_scales_green"           )), 1, 1, folder + "sea_serpent_scales/green");
        materialRecipe(iceandfire, IFMaterials.serpentPurple,           ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_scales_purple"          )), 1, 1, folder + "sea_serpent_scales/purple");
        materialRecipe(iceandfire, IFMaterials.serpentRed,              ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_scales_red"             )), 1, 1, folder + "sea_serpent_scales/red");
        materialRecipe(iceandfire, IFMaterials.serpentTeal,             ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_scales_teal"            )), 1, 1, folder + "sea_serpent_scales/teal");
        materialRecipe(iceandfire, IFMaterials.serpentBlue,             ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_scale_block_blue"       )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:sea_serpent_scales_blue"      )), folder + "sea_serpent_scales/blue_block");
        materialRecipe(iceandfire, IFMaterials.serpentBronze,           ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_scale_block_bronze"     )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:sea_serpent_scales_bronze"    )), folder + "sea_serpent_scales/bronze_block");
        materialRecipe(iceandfire, IFMaterials.serpentDeepBlue,         ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_scale_block_deepblue"   )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:sea_serpent_scales_deepblue"  )), folder + "sea_serpent_scales/deep_blue_block");
        materialRecipe(iceandfire, IFMaterials.serpentGreen,            ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_scale_block_green"      )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:sea_serpent_scales_green"     )), folder + "sea_serpent_scales/green_block");
        materialRecipe(iceandfire, IFMaterials.serpentPurple,           ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_scale_block_purple"     )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:sea_serpent_scales_purple"    )), folder + "sea_serpent_scales/purple_block");
        materialRecipe(iceandfire, IFMaterials.serpentRed,              ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_scale_block_red"        )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:sea_serpent_scales_red"       )), folder + "sea_serpent_scales/red_block");
        materialRecipe(iceandfire, IFMaterials.serpentTeal,             ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_scale_block_teal"       )), 9, 1, ItemNameOutput.fromName(TCompat.getResource("iceandfire:sea_serpent_scales_teal"      )), folder + "sea_serpent_scales/teal_block");
        materialRecipe(iceandfire, IFMaterials.seaSerpentFang,          ItemNameIngredient.from(TCompat.getResource("iceandfire:sea_serpent_fang"                   )), 1, 1, folder + "sea_serpent_fang");
        materialRecipe(iceandfire, IFMaterials.hydraFang,               ItemNameIngredient.from(TCompat.getResource("iceandfire:hydra_fang"                         )), 1, 1, folder + "hydra_fang");
        materialRecipe(iceandfire, IFMaterials.amphithereFeather,       ItemNameIngredient.from(TCompat.getResource("iceandfire:amphithere_feather"                 )), 1, 1, folder + "amphithere_feather");
        materialRecipe(iceandfire, IFMaterials.stymphalianBirdFeather,  ItemNameIngredient.from(TCompat.getResource("iceandfire:stymphalian_bird_feather"           )), 1, 1, folder + "stymphalian_bird_feather");
        materialRecipe(iceandfire, IFMaterials.trollFrost,              ItemNameIngredient.from(TCompat.getResource("iceandfire:troll_leather_frost"                )), 1, 1, folder + "troll_leather/frost");
        materialRecipe(iceandfire, IFMaterials.trollMountain,           ItemNameIngredient.from(TCompat.getResource("iceandfire:troll_leather_mountain"             )), 1, 1, folder + "troll_leather/mountain");
        materialRecipe(iceandfire, IFMaterials.trollForest,             ItemNameIngredient.from(TCompat.getResource("iceandfire:troll_leather_forest"               )), 1, 1, folder + "troll_leather/forest");
        materialRecipe(iceandfire, IFMaterials.myrmexDesert,            ItemNameIngredient.from(TCompat.getResource("iceandfire:myrmex_desert_chitin"               )), 1, 1, folder + "myrmex_chitin/desert");
        materialRecipe(iceandfire, IFMaterials.myrmexJungle,            ItemNameIngredient.from(TCompat.getResource("iceandfire:myrmex_jungle_chitin"               )), 1, 1, folder + "myrmex_chitin/jungle");
        materialRecipe(iceandfire, IFMaterials.deathwormWhite,          ItemNameIngredient.from(TCompat.getResource("iceandfire:deathworm_chitin_white"             )), 1, 1, folder + "deathworm_chitin/white");
        materialRecipe(iceandfire, IFMaterials.deathwormRed,            ItemNameIngredient.from(TCompat.getResource("iceandfire:deathworm_chitin_red"               )), 1, 1, folder + "deathworm_chitin/red");
        materialRecipe(iceandfire, IFMaterials.deathwormYellow,         ItemNameIngredient.from(TCompat.getResource("iceandfire:deathworm_chitin_yellow"            )), 1, 1, folder + "deathworm_chitin/yellow");
        materialRecipe(iceandfire, DarkerMaterials.sculkBone,           ItemNameIngredient.from(TCompat.getResource("deeperdarker:sculk_bone"                       )), 1, 1, folder + "sculk_bone/bone");



        metalMaterialRecipe(cataclysm, CataclysmMaterials.ancientMetal, folder, "ancient_metal", true);
        metalMaterialRecipe(cataclysm, CataclysmMaterials.blackSteel, folder, "black_steel", true);
        metalMaterialRecipe(cataclysm, CataclysmMaterials.cursium, folder, "cursium", true);
        metalMaterialRecipe(cataclysm, CataclysmMaterials.witherite, folder, "witherite", true);
        materialRecipe(cataclysm, CataclysmMaterials.lacrima, ItemNameIngredient.from(TCompat.getResource("cataclysm:lacrima")), 1, 1, folder + "lacrima");
        materialRecipe(cataclysm, CataclysmMaterials.essenceOfTheStorm, ItemNameIngredient.from(TCompat.getResource("cataclysm:essence_of_the_storm")), 1, 1, folder + "essence_of_the_storm");
        materialRecipe(cataclysm, CataclysmMaterials.coral, ItemNameIngredient.from(TCompat.getResource("cataclysm:coral_chunk")), 1, 1, folder + "coral");
        materialRecipe(cataclysm, CataclysmMaterials.voidJaw, ItemNameIngredient.from(TCompat.getResource("cataclysm:void_jaw")), 1, 1, folder + "void_jaw");
        materialRecipe(cataclysm, CataclysmMaterials.koboletonBone, ItemNameIngredient.from(TCompat.getResource("cataclysm:koboleton_bone")), 1, 1, folder + "koboleton_bone/bone");
        materialRecipe(cataclysm, CataclysmMaterials.koboletonBone, ItemNameIngredient.from(TCompat.getResource("cataclysm:kobolediator_skull")), 8, 1, folder + "koboleton_bone/skull");

        metalMaterialRecipe(malum, MalumMaterials.soulStainedSteel, folder, "soul_stained_steel", true);
        metalMaterialRecipe(malum, MalumMaterials.hallowedGold, folder, "hallowed_gold", true);
        metalMaterialRecipe(malum, MalumMaterials.malignantPewter, folder, "malignant_pewter", true);
        materialRecipe(malum, MalumMaterials.spiritFabric, ItemNameIngredient.from(TCompat.getResource("malum:spirit_fabric")), 1, 1, folder + "spirit_fabric");
        materialRecipe(malum, MalumMaterials.astralWeave, ItemNameIngredient.from(TCompat.getResource("malum:astral_weave")), 1, 1, folder + "astral_weave");
        materialRecipe(malum, MalumMaterials.alchemicalCalx, ItemNameIngredient.from(TCompat.getResource("malum:alchemical_calx")), 1, 1, folder + "alchemical_calx");
        materialRecipe(malum, MalumMaterials.soulstone, ItemNameIngredient.from(TCompat.getResource("malum:processed_soulstone")), 1, 1, folder + "soulstone");
        materialRecipe(malum, MalumMaterials.nullSlate, ItemNameIngredient.from(TCompat.getResource("malum:null_slate")), 1, 1, folder + "null_slate");

        gemMaterialRecipe(deepAetherConsumer, DeepAetherMaterials.skyjade, folder, "skyjade", true, true, true);
        metalMaterialRecipe(deepAetherConsumer, DeepAetherMaterials.stormforgedSteel, folder, "stormforged_steel", true);

        metalMaterialRecipe(aetherReduxConsumer, AetherReduxMaterials.veridium, folder, "veridium", true);
        metalMaterialRecipe(aetherReduxConsumer, AetherReduxMaterials.refinedSentrite, folder, "refined_sentrite", true);
        materialRecipe(aetherReduxConsumer, AetherReduxMaterials.blightbunnyFang, ItemNameIngredient.from(TCompat.getResource("aether_redux:blightbunny_fang")), 1, 1, folder + "blightbunny_fang");
        materialRecipe(aetherReduxConsumer, AetherReduxMaterials.mykapodShell, ItemNameIngredient.from(TCompat.getResource("aether_redux:mykapod_shell_chunk")), 1, 1, folder + "mykapod_shell_chunk");

        metalMaterialRecipe(aetherTreasureConsumer, AetherTRMaterials.pyral, folder, "pyral", true);
        metalMaterialRecipe(aetherTreasureConsumer, AetherTRMaterials.valkyrum, folder, "valkyrum", true);
        materialRecipe(aetherTreasureConsumer, AetherTRMaterials.neptune, ItemNameIngredient.from(TCompat.getResource("aether_treasure_reforging:neptune_mesh")), 1, 1, folder + "neptune/mesh");

        metalMaterialRecipe(adAstraConsumer, AdAstraMaterials.desh, folder, "desh", true);
        metalMaterialRecipe(adAstraConsumer, AdAstraMaterials.calorite, folder, "calorite", true);
        metalMaterialRecipe(adAstraConsumer, AdAstraMaterials.ostrum, folder, "ostrum", true);

        metalMaterialRecipe(betterend, BetterendMaterials.thallasium, folder, "thallasium", true);
        metalMaterialRecipe(betterend, BetterendMaterials.terminite, folder, "terminite", true);
        metalMaterialRecipe(betterend, BetterendMaterials.aeternium, folder, "aeternium", true);

        metalMaterialRecipe(betterend, BetternetherMaterials.cincinnasite, folder, "cincinnasite", true);
        gemMaterialRecipe(betterend, BetternetherMaterials.netherRuby, folder, "nether_ruby", true, false, true);

        materialRecipe(speciesConsumer, SpeciesMaterials.wickedWax, ItemNameIngredient.from(TCompat.getResource("species:wicked_wax")), 1, 1, folder + "wicked_wax");
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
        Consumer<FinishedRecipe> cataclysm = withCondition(consumer, modLoaded("cataclysm"));
        Consumer<FinishedRecipe> malum = withCondition(consumer, modLoaded("malum"));

        //materialMeltingCasting(aetherConsumer, TCMaterials.zanite, TCFluids.moltenZanite, FluidValues.INGOT, folder);
        materialMelting(aetherConsumer, AetherMaterials.zanite, TCFluids.moltenZanite, FluidValues.INGOT, folder);
        materialMeltingCasting(aetherConsumer, AetherMaterials.gravitite, TCFluids.moltenGravitite, folder);
        materialMeltingCasting(aetherConsumer, AetherMaterials.lightnum, TCFluids.moltenLightnum, folder);
        materialMeltingCasting(aetherConsumer, AetherMaterials.draculite, TCFluids.moltenDraculite, folder);
        //materialMeltingCasting(deepAetherConsumer, TCMaterials.skyjade, TCFluids.moltenSkyjade, FluidValues.INGOT, folder);
        materialMelting(deepAetherConsumer, DeepAetherMaterials.skyjade, TCFluids.moltenSkyjade, FluidValues.INGOT, folder);
        materialMeltingCasting(deepAetherConsumer, DeepAetherMaterials.stormforgedSteel, TCFluids.moltenStormforgedSteel, folder);
        materialMeltingCasting(aetherReduxConsumer, AetherReduxMaterials.veridium, TCFluids.moltenVeridium, folder);
        materialMeltingCasting(aetherReduxConsumer, AetherReduxMaterials.refinedSentrite, TCFluids.moltenRefinedSentrite, folder);

        materialMeltingCasting(adAstraConsumer, AdAstraMaterials.desh, TCFluids.moltenDesh, FluidValues.INGOT, folder);
        materialMeltingCasting(adAstraConsumer, AdAstraMaterials.calorite, TCFluids.moltenCalorite, FluidValues.INGOT, folder);
        materialMeltingCasting(adAstraConsumer, AdAstraMaterials.ostrum, TCFluids.moltenOstrum, FluidValues.INGOT, folder);

        materialMeltingCasting(betterend, BetterendMaterials.thallasium, TCFluids.moltenThallasium, FluidValues.INGOT, folder);
        materialMeltingCasting(betterend, BetterendMaterials.terminite, TCFluids.moltenTerminite, FluidValues.INGOT, folder);
        materialMeltingCasting(betterend, BetterendMaterials.aeternium, TCFluids.moltenAeternium, FluidValues.INGOT, folder);

        materialMeltingCasting(betternether, BetternetherMaterials.cincinnasite, TCFluids.moltenCincinnasite, FluidValues.INGOT, folder);
        materialMeltingCasting(betternether, BetternetherMaterials.netherRuby, TCFluids.moltenNetherRuby, FluidValues.GEM, folder);

        materialMeltingCasting(iceandfire, IFMaterials.fireDragonsteel, TCFluids.moltenFireDragonsteel, FluidValues.INGOT, folder);
        materialMeltingCasting(iceandfire, IFMaterials.iceDragonsteel, TCFluids.moltenIceDragonsteel, FluidValues.INGOT, folder);
        materialMeltingCasting(iceandfire, IFMaterials.lightningDragonsteel, TCFluids.moltenLightningDragonsteel, FluidValues.INGOT, folder);

        materialMeltingComposite(aetherTreasureConsumer, AetherMaterials.gravitite, AetherTRMaterials.valkyrum, TCFluids.moltenValkyrum, FluidValues.INGOT, folder);
        materialMeltingComposite(aetherTreasureConsumer, AetherMaterials.gravitite, AetherTRMaterials.pyral, TCFluids.moltenPyral, FluidValues.INGOT, folder);
        materialMeltingComposite(aetherTreasureConsumer, AetherMaterials.zanite, AetherTRMaterials.neptune, TCFluids.moltenNeptune, FluidValues.INGOT, folder);

        materialMeltingCasting(cataclysm, CataclysmMaterials.ancientMetal, TCFluids.moltenAncientMetal, folder);
        materialMeltingCasting(cataclysm, CataclysmMaterials.blackSteel, TCFluids.moltenBlackSteel, folder);
        materialMeltingCasting(cataclysm, CataclysmMaterials.cursium, TCFluids.moltenCursium, folder);
        materialMeltingCasting(cataclysm, CataclysmMaterials.witherite, TCFluids.moltenWitherite, folder);

        materialMeltingCasting(malum, MalumMaterials.soulStainedSteel, TCFluids.moltenSoulStainedSteel, folder);
        materialMeltingCasting(malum, MalumMaterials.hallowedGold, TCFluids.moltenHallowedGold, folder);
        materialMeltingCasting(malum, MalumMaterials.malignantPewter, TCFluids.moltenMalignantPewter, folder);

        materialComposite(cataclysm, MaterialIds.string, CataclysmMaterials.cursium, TCFluids.moltenCursium, FluidValues.INGOT, folder);
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

    private void rockRecipe(Consumer<FinishedRecipe> consumer, TCRocks rockType, MaterialId material) {
        String folder = "tools/materials/";
        materialRecipe(consumer, material, Ingredient.of(rockType.rockTag()), 1, 1, folder + material.getId().getPath() + "/%s".formatted(material.getPath()));
    }

    private static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.parse(name));
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.parse(name));
    }

    private static <T> ICondition tagFilled(TagKey<T> tag) {
        return new TagFilledCondition<>(tag);
    }
}
