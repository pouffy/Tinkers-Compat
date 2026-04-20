package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;
import slimeknights.tconstruct.library.recipe.partbuilder.PartRecipeBuilder;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerToolParts;

import java.util.function.Consumer;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class TCToolRecipeProv extends TCBaseRecipeProvider implements IMaterialRecipeHelper, IToolRecipeHelper {
    public TCToolRecipeProv(PackOutput generator) {
        super(generator);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/building/";
        String partFolder = "tools/parts/";

        Consumer<FinishedRecipe> iceandfireConsumer = withCondition(consumer, modLoaded("iceandfire"));

        PartRecipeBuilder.partRecipe(TinkerToolParts.plating.get(ArmorItem.Type.HELMET))
                .setPattern(getResource("tconstruct", "helmet_plating"))
                .setPatternItem(CompoundIngredient.of(Ingredient.of(TinkerTags.Items.DEFAULT_PATTERNS), Ingredient.of(TinkerSmeltery.helmetPlatingCast.get())))
                .setCost(3)
                .save(iceandfireConsumer, location(partFolder + "builder/" + getResource("tconstruct", "helmet_plating").getPath()));
        PartRecipeBuilder.partRecipe(TinkerToolParts.plating.get(ArmorItem.Type.CHESTPLATE))
                .setPattern(getResource("tconstruct", "chestplate_plating"))
                .setPatternItem(CompoundIngredient.of(Ingredient.of(TinkerTags.Items.DEFAULT_PATTERNS), Ingredient.of(TinkerSmeltery.chestplatePlatingCast.get())))
                .setCost(6)
                .save(iceandfireConsumer, location(partFolder + "builder/" + getResource("tconstruct", "chestplate_plating").getPath()));
        PartRecipeBuilder.partRecipe(TinkerToolParts.plating.get(ArmorItem.Type.LEGGINGS))
                .setPattern(getResource("tconstruct", "leggings_plating"))
                .setPatternItem(CompoundIngredient.of(Ingredient.of(TinkerTags.Items.DEFAULT_PATTERNS), Ingredient.of(TinkerSmeltery.leggingsPlatingCast.get())))
                .setCost(5)
                .save(iceandfireConsumer, location(partFolder + "builder/" + getResource("tconstruct", "leggings_plating").getPath()));
        PartRecipeBuilder.partRecipe(TinkerToolParts.plating.get(ArmorItem.Type.BOOTS))
                .setPattern(getResource("tconstruct", "boots_plating"))
                .setPatternItem(CompoundIngredient.of(Ingredient.of(TinkerTags.Items.DEFAULT_PATTERNS), Ingredient.of(TinkerSmeltery.bootsPlatingCast.get())))
                .setCost(2)
                .save(iceandfireConsumer, location(partFolder + "builder/" + getResource("tconstruct", "boots_plating").getPath()));

        toolBuilding(consumer, GlobalInit.glaive, folder);
    }

    @Override
    public String getName() {
        return "Tinkers' Compatability Tool Recipes";
    }
}
