package io.github.pouffy.tcompat.compat.aether_redux.recipe;

import com.aetherteam.aether.Aether;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_redux.AmbrofusionModifier;
import lombok.Getter;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import slimeknights.mantle.data.loadable.common.IngredientLoadable;
import slimeknights.mantle.data.loadable.field.ContextKey;
import slimeknights.mantle.data.loadable.primitive.IntLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.util.RegistryHelper;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.recipe.RecipeResult;
import slimeknights.tconstruct.library.recipe.modifiers.ModifierRecipeLookup;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IDisplayModifierRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IncrementalModifierRecipe;
import slimeknights.tconstruct.library.recipe.tinkerstation.IMutableTinkerStationContainer;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationContainer;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationRecipe;
import slimeknights.tconstruct.library.tools.nbt.LazyToolStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static slimeknights.tconstruct.library.recipe.modifiers.adding.IDisplayModifierRecipe.withModifiers;

public class AmbrofusionModifierRecipe implements ITinkerStationRecipe, IDisplayModifierRecipe {
    private static final RecipeResult<LazyToolStack> AT_CAPACITY = RecipeResult.failure(TConstruct.makeTranslationKey("recipe", "ambrofusion.at_capacity"));
    public static final RecordLoadable<AmbrofusionModifierRecipe> LOADER = RecordLoadable.create(
            ContextKey.ID.requiredField(),
            IngredientLoadable.DISALLOW_EMPTY.requiredField("ingredient", r -> r.ingredient),
            IntLoadable.FROM_ONE.requiredField("restore_amount", r -> r.restoreAmount),
            AmbrofusionModifierRecipe::new);

    @Getter
    private final ResourceLocation id;
    private final Ingredient ingredient;
    private final int restoreAmount;

    public AmbrofusionModifierRecipe(ResourceLocation id, Ingredient ingredient, int restoreAmount) {
        this.id = id;
        this.ingredient = ingredient;
        this.restoreAmount = restoreAmount;
        ModifierRecipeLookup.addRecipeModifier(null, AetherReduxInit.ambrofusion);
    }

    @Override
    public boolean matches(ITinkerStationContainer inv, Level world) {
        if (!inv.getTinkerableStack().is(TinkerTags.Items.DURABILITY)) {
            return false;
        }
        return IncrementalModifierRecipe.containsOnlyIngredient(inv, ingredient);
    }

    @Override
    public RecipeResult<LazyToolStack> getValidatedResult(ITinkerStationContainer inv, RegistryAccess access) {
        ToolStack tool = inv.getTinkerable();
        AmbrofusionModifier ambrofusion = AetherReduxInit.ambrofusion.get();
        ModifierId ambrofusionId = AetherReduxInit.ambrofusion.getId();
        ModifierEntry entry = tool.getModifier(ambrofusionId);
        if (tool.getUpgrades().getLevel(ambrofusionId) == 0) {
            if (entry.getLevel() > 0 && ambrofusion.getAmbrosium(tool) >= ambrofusion.getAmbrosiumCapacity(tool, entry)) {
                return AT_CAPACITY;
            }
            tool = tool.copy();
            tool.addModifier(AetherReduxInit.ambrofusion.getId(), 1);
        } else {
            if (ambrofusion.getAmbrosium(tool) >= ambrofusion.getAmbrosiumCapacity(tool, entry)) {
                return AT_CAPACITY;
            }
            tool = tool.copy();
        }
        int available = IncrementalModifierRecipe.getAvailableAmount(inv, ingredient, restoreAmount);
        ambrofusion.addAmbrofusion(tool, entry, available);
        return ITinkerStationRecipe.success(tool, inv);
    }

    @Override
    public void updateInputs(LazyToolStack result, IMutableTinkerStationContainer inv, boolean isServer) {
        ToolStack tool = inv.getTinkerable();
        int current = 0;
        AmbrofusionModifier ambrofusion = AetherReduxInit.ambrofusion.get();
        if (tool.getModifierLevel(ambrofusion) != 0) {
            current = ambrofusion.getAmbrosium(tool);
        }
        int maxNeeded = ambrofusion.getAmbrosium(result.getTool()) - current;
        IncrementalModifierRecipe.updateInputs(inv, ingredient, maxNeeded, restoreAmount, ItemStack.EMPTY);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherReduxInit.ambrofusionSerializer.get();
    }

    private static final ModifierEntry RESULT = new ModifierEntry(AetherReduxInit.ambrofusion, 1);
    private List<ItemStack> toolWithoutModifier, toolWithModifier = null;

    @Override
    public int getInputCount() {
        return 1;
    }

    @Override
    public List<ItemStack> getDisplayItems(int slot) {
        if (slot == 0) {
            return Arrays.asList(ingredient.getItems());
        }
        return Collections.emptyList();
    }
    @Override
    public List<ItemStack> getToolWithoutModifier() {
        if (toolWithoutModifier == null) {
            toolWithoutModifier = RegistryHelper.getTagValueStream(BuiltInRegistries.ITEM, TinkerTags.Items.DURABILITY).map(MAP_TOOL_FOR_RENDERING).toList();
        }
        return toolWithoutModifier;
    }

    @Override
    public List<ItemStack> getToolWithModifier() {
        if (toolWithModifier == null) {
            AmbrofusionModifier ambrofusion = AetherReduxInit.ambrofusion.get();
            List<ModifierEntry> result = List.of(RESULT);
            toolWithModifier = RegistryHelper.getTagValueStream(BuiltInRegistries.ITEM, TinkerTags.Items.DURABILITY)
                    .map(MAP_TOOL_FOR_RENDERING)
                    .map(stack -> withModifiers(stack, result, data -> ambrofusion.setAmbrosium(data, restoreAmount)))
                    .toList();
        }
        return toolWithModifier;
    }

    @Override
    public ModifierEntry getDisplayResult() {
        return RESULT;
    }
}
