package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.compat.ad_astra.AdAstraSmeltery;
import io.github.pouffy.tcompat.compat.aether.AetherSmeltery;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxSmeltery;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRSmeltery;
import io.github.pouffy.tcompat.compat.ancient_aether.AncientAetherSmeltery;
import io.github.pouffy.tcompat.compat.betterend.BetterEndSmeltery;
import io.github.pouffy.tcompat.compat.betternether.BetterNetherSmeltery;
import io.github.pouffy.tcompat.compat.bwg.BWGSmeltery;
import io.github.pouffy.tcompat.compat.cataclysm.CataclysmSmeltery;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherSmeltery;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFSmeltery;
import io.github.pouffy.tcompat.compat.malum.MalumSmeltery;
import io.github.pouffy.tcompat.compat.species.SpeciesSmeltery;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.recipe.data.ICommonRecipeHelper;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.material.MaterialRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import java.util.function.Consumer;

import static slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe.getTemperature;

public class TCSmelteryRecipeProv extends TCBaseRecipeProvider implements ITCSmelteryRecipeHelper, ICommonRecipeHelper {
    public TCSmelteryRecipeProv(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public @NotNull String getName() {
        return "Tinkers' Compatibility Smeltery Recipes";
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        new AetherSmeltery().toData(consumer);
        new DeepAetherSmeltery().toData(consumer);
        new AetherReduxSmeltery().toData(consumer);
        new AetherTRSmeltery().toData(consumer);
        new AncientAetherSmeltery().toData(consumer);
        new SpeciesSmeltery().toData(consumer);
        new AdAstraSmeltery().toData(consumer);
        new BetterEndSmeltery().toData(consumer);
        new BetterNetherSmeltery().toData(consumer);
        new IFSmeltery().toData(consumer);
        new BWGSmeltery().toData(consumer);
        new CataclysmSmeltery().toData(consumer);
        new MalumSmeltery().toData(consumer);
        recipeOverwrites(consumer);
    }

    private Ingredient toList(Ingredient... ingredients) {
        return CompoundIngredient.of(ingredients);
    }

    /**
     * Overwrites some base tinkers recipes to accommodate for other tagged items not fitting the output.
     */
    protected void recipeOverwrites(Consumer<FinishedRecipe> consumer) {
        ItemCastingRecipeBuilder.tableDuplication()
                .setCast(toList(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(TCTags.Items.DIAMOND_SMITHING_TEMPLATES)), false)
                .setCoolingTime(186)
                .setFluid(TinkerFluids.moltenDiamond.ingredient(500))
                .save(consumer, TCompat.getResource("tconstruct:smeltery/casting/diamond/smithing_template"));
        MeltingRecipeBuilder.melting(toList(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(TCTags.Items.DIAMOND_SMITHING_TEMPLATES)), TinkerFluids.moltenDiamond.result(500), getTemperature(TinkerFluids.moltenDiamond), IMeltingRecipe.calcTimeFactor(500))
                .save(consumer, TCompat.getResource("tconstruct:smeltery/melting/diamond/smithing_template"));
        MaterialRecipeBuilder.materialRecipe(MaterialIds.wood)
                .setIngredient(TCTags.Items.NON_AETHER_WOODEN_RODS)
                .setNeeded(2).setValue(1)
                .save(consumer, TCompat.getResource("tconstruct:tools/materials/wood/sticks"));
    }
}
