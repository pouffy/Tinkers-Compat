package io.github.pouffy.tcompat.compat.aether;

import io.github.pouffy.tcompat.common.module.AutosmeltModule;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import slimeknights.tconstruct.common.recipe.RecipeCacheInvalidator;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.module.ModuleHookMap;

public class AutofreezeModifier extends Modifier {
    @Override
    public Component getDisplayName(int level) {
        return ModifierLevelDisplay.PLUSES.nameForLevel(this, level);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        var retrieved = ObjectRetriever.getRecipe("aether:freezing");
        if (retrieved.isPresent()) {
            var freezing = (RecipeType<? extends AbstractCookingRecipe>) retrieved.get();
            AutosmeltModule autosmelt = new AutosmeltModule(0.2f, freezing);
            hookBuilder.addModule(autosmelt);
            RecipeCacheInvalidator.addReloadListener(client -> {
                if (!client) {
                    autosmelt.clearCache();
                }
            });
        }
    }
}
