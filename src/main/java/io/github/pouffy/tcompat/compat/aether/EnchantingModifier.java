package io.github.pouffy.tcompat.compat.aether;

import com.aetherteam.aether.recipe.recipes.item.EnchantingRecipe;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.github.pouffy.tcompat.TCompat;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import slimeknights.tconstruct.common.recipe.RecipeCacheInvalidator;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.recipe.SingleItemContainer;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;
import java.util.Optional;

public class EnchantingModifier extends Modifier implements ProcessLootModifierHook {

    private final Cache<Item, Optional<Recipe<?>>> recipeCache = CacheBuilder
            .newBuilder()
            .maximumSize(64)
            .build();

    private final SingleItemContainer inventory = new SingleItemContainer();

    public EnchantingModifier() {
        RecipeCacheInvalidator.addReloadListener(client -> {
            if (!client) {
                recipeCache.invalidateAll();
            }
        });
    }

    @Override
    public Component getDisplayName(int level) {
        return ModifierLevelDisplay.PLUSES.nameForLevel(this, level);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROCESS_LOOT);
    }

    @Override
    public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {

    }
}
