package io.github.pouffy.tcompat.common.util;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.ITCSmelteryRecipeHelper;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCSmelteryRecipeBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import slimeknights.mantle.recipe.data.ICommonRecipeHelper;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.registration.object.FluidObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static io.github.pouffy.tcompat.TCompat.getResource;

public interface CompatSmeltery extends ITCSmelteryRecipeHelper, ICommonRecipeHelper {

    void addRecipes(Consumer<FinishedRecipe> consumer, String folder);

    default String getModId() {
        return TCompat.MOD_ID;
    }

    String compatModId();

    default ResourceLocation compatId(String path) {
        return getResource(compatModId(), path);
    }

    default TagKey<Item> compatTag(String path) {
        return TagKey.create(Registries.ITEM, getResource(compatModId(), path));
    }

    default Ingredient listedInput(String... inputs) {
        List<ResourceLocation> list = new ArrayList<>();
        for (String s : inputs) {
            if (s.contains(":")) {
                list.add(ResourceLocation.tryParse(s));
            } else list.add(compatId(s));
        }
        return ItemNameIngredient.from(list);
    }

    default Consumer<FinishedRecipe> compatConsumer(Consumer<FinishedRecipe> consumer) {
        return withCondition(consumer, new ModLoadedCondition(compatModId()));
    }

    default String compatFolderFunction(String path) {
        return path.formatted(compatModId());
    }

    default String alloyFolder(String path) { return compatFolderFunction("smeltery/alloys/%s/" + path); }
    default String metalFolder(String path) { return compatFolderFunction("smeltery/" + path + "/metal/%s/"); }
    default String gemFolder(String path) { return compatFolderFunction("smeltery/" + path + "/gem/%s/"); }
    default String miscFolder(String path) { return compatFolderFunction("smeltery/" + path + "/misc/%s/"); }

    default TCSmelteryRecipeBuilder metal(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String modId) {
        return molten(consumer, fluid).castingFolder("smeltery/casting/metal/" + modId).meltingFolder("smeltery/melting/metal/" + modId);
    }

    default TCSmelteryRecipeBuilder gem(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String modId) {
        return molten(consumer, fluid).castingFolder("smeltery/casting/gem/" + modId).meltingFolder("smeltery/melting/gem/" + modId);
    }
}
