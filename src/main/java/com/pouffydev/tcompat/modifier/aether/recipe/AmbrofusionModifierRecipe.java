package com.pouffydev.tcompat.modifier.aether.recipe;

import com.pouffydev.tcompat.modifier.TComModifiers;
import com.pouffydev.tcompat.modifier.aether.AmbrofusionModifier;
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
        ModifierRecipeLookup.addRecipeModifier(null, TComModifiers.ambrofusion);
    }

    @Override
    public boolean matches(ITinkerStationContainer inv, Level world) {
        if (!inv.getTinkerableStack().is(TinkerTags.Items.DURABILITY)) {
            return false;
        }
        // must find at least one slime, but multiple is fine, as is empty slots
        return IncrementalModifierRecipe.containsOnlyIngredient(inv, ingredient);
    }

    @Override
    public RecipeResult<LazyToolStack> getValidatedResult(ITinkerStationContainer inv, RegistryAccess access) {
        ToolStack tool = inv.getTinkerable();
        AmbrofusionModifier ambrofusion = TComModifiers.ambrofusion.get();
        ModifierId ambrofusionId = TComModifiers.ambrofusion.getId();
        ModifierEntry entry = tool.getModifier(ambrofusionId);
        // if the tool lacks true ambrofusion, add ambrofusion
        if (tool.getUpgrades().getLevel(ambrofusionId) == 0) {
            // however, if we have ambrofusion though a trait and reached our cap, also do nothing
            if (entry.getLevel() > 0 && ambrofusion.getAmbrosium(tool) >= ambrofusion.getAmbrosiumCapacity(tool, entry)) {
                return AT_CAPACITY;
            }
            // truely add ambrofusion, this will cost a slime crystal if full durability
            tool = tool.copy();
            tool.addModifier(TComModifiers.ambrofusion.getId(), 1);
        } else {
            // ensure we are not at the cap already
            if (ambrofusion.getAmbrosium(tool) >= ambrofusion.getAmbrosiumCapacity(tool, entry)) {
                return AT_CAPACITY;
            }
            // copy the tool as we will change it later
            tool = tool.copy();
        }

        // see how much value is available, update ambrofusion to the max possible
        int available = IncrementalModifierRecipe.getAvailableAmount(inv, ingredient, restoreAmount);
        ambrofusion.addAmbrofusion(tool, entry, available);
        return ITinkerStationRecipe.success(tool, inv);
    }

    @Override
    public void updateInputs(LazyToolStack result, IMutableTinkerStationContainer inv, boolean isServer) {
        ToolStack tool = inv.getTinkerable();
        // if the original tool did not have ambrofusion, its treated as having no slime
        int current = 0;
        AmbrofusionModifier ambrofusion = TComModifiers.ambrofusion.get();
        if (tool.getModifierLevel(ambrofusion) != 0) {
            current = ambrofusion.getAmbrosium(tool);
        }

        // how much did we actually consume?
        int maxNeeded = ambrofusion.getAmbrosium(result.getTool()) - current;
        IncrementalModifierRecipe.updateInputs(inv, ingredient, maxNeeded, restoreAmount, ItemStack.EMPTY);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TComModifiers.ambrofusionSerializer.get();
    }

    /* JEI display */
    /** Cache of modifier result, same for all ambrofusion */
    private static final ModifierEntry RESULT = new ModifierEntry(TComModifiers.ambrofusion, 1);
    /** Cache of input and output tools for display */
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
            AmbrofusionModifier ambrofusion = TComModifiers.ambrofusion.get();
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
