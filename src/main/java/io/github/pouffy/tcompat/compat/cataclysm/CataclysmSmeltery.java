package io.github.pouffy.tcompat.compat.cataclysm;

import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class CataclysmSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);

    }

    @Override
    public String compatModId() {
        return "cataclysm";
    }
}
