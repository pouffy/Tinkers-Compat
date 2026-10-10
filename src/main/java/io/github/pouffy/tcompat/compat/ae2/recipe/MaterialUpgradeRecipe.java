package io.github.pouffy.tcompat.compat.ae2.recipe;

import io.github.pouffy.tcompat.compat.ae2.AE2Init;
import lombok.Getter;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import slimeknights.mantle.data.loadable.common.IngredientLoadable;
import slimeknights.mantle.data.loadable.field.ContextKey;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.recipe.IMultiRecipe;
import slimeknights.mantle.recipe.ingredient.SizedIngredient;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.recipe.RecipeResult;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipe;
import slimeknights.tconstruct.library.recipe.tinkerstation.IMutableTinkerStationContainer;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationContainer;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationRecipe;
import slimeknights.tconstruct.library.recipe.tinkerstation.IDisplayToolTinkering;
import slimeknights.tconstruct.library.tools.definition.module.material.ToolMaterialHook;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.LazyToolStack;
import slimeknights.tconstruct.library.tools.nbt.MaterialNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Tinker Station recipe that swaps every part made of one of the {@code from} materials to {@code to}, consuming {@code inputs}.
 * Purely a material swap: no modifier is added and no slot is used. Used for AE2's certus quartz -> fluix upgrade.
 */
public class MaterialUpgradeRecipe implements ITinkerStationRecipe, IMultiRecipe<IDisplayToolTinkering> {
    public static final RecordLoadable<MaterialUpgradeRecipe> LOADER = RecordLoadable.create(
            ContextKey.ID.requiredField(),
            IngredientLoadable.DISALLOW_EMPTY.requiredField("tools", r -> r.tools),
            MaterialId.PARSER.list(1).requiredField("from", r -> r.from),
            MaterialVariantId.LOADABLE.requiredField("to", r -> r.to),
            SizedIngredient.LOADABLE.list(1).requiredField("inputs", r -> r.inputs),
            MaterialUpgradeRecipe::new);

    @Getter
    private final ResourceLocation id;
    private final Ingredient tools;
    private final List<MaterialId> from;
    private final MaterialVariantId to;
    private final List<SizedIngredient> inputs;

    public MaterialUpgradeRecipe(ResourceLocation id, Ingredient tools, List<MaterialId> from, MaterialVariantId to, List<SizedIngredient> inputs) {
        this.id = id;
        this.tools = tools;
        this.from = from;
        this.to = to;
        this.inputs = inputs;
    }

    @Override
    public boolean matches(ITinkerStationContainer inv, Level level) {
        ItemStack tool = inv.getTinkerableStack();
        return tools.test(tool) && swap(inv.getTinkerable(), from, to) != null && ModifierRecipe.checkMatch(inv, inputs);
    }

    @Override
    public RecipeResult<LazyToolStack> getValidatedResult(ITinkerStationContainer inv, RegistryAccess access) {
        ToolStack original = inv.getTinkerable();
        ToolStack copy = swap(original, from, to); // non-null, checked in matches
        Component error = validate(original, copy);
        return error != null ? RecipeResult.failure(error) : ITinkerStationRecipe.success(copy, inv);
    }

    /** Copy of {@code original} with every {@code from} part {@code to} has stats for swapped, null if no part was swapped, so also the match check. Shared with the smithing table recipe. */
    @Nullable
    static ToolStack swap(ToolStack original, List<MaterialId> from, MaterialVariantId to) {
        List<MaterialStatsId> statTypes = ToolMaterialHook.stats(original.getDefinition());
        ToolStack copy = original.copy();
        boolean swapped = false;
        for (int i = 0; i < statTypes.size(); i++) {
            // only swap parts the target material supports, e.g. a certus binding stays certus as fluix has no binding stats
            if (from.contains(copy.getMaterial(i).getId()) && MaterialRegistry.getInstance().getMaterialStats(to.getId(), statTypes.get(i)).isPresent()) {
                copy.replaceMaterial(i, to);
                swapped = true;
            }
        }
        return swapped ? copy : null;
    }

    /** Error if the swapped tool is invalid, null if fine */
    @Nullable
    static Component validate(ToolStack original, ToolStack swapped) {
        Component error = swapped.tryValidate();
        return error != null ? error : ModifierRemovalHook.onRemoved(original, swapped);
    }

    @Override
    public void updateInputs(LazyToolStack result, IMutableTinkerStationContainer inv, boolean isServer) {
        ModifierRecipe.updateInputs(inv, inputs);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AE2Init.materialUpgradeSerializer.get();
    }

    /* JEI */

    private List<IDisplayToolTinkering> displayRecipes;

    @Override
    public List<IDisplayToolTinkering> getRecipes(RegistryAccess access) {
        if (displayRecipes == null) {
            List<ItemStack> without = new ArrayList<>();
            List<ItemStack> with = new ArrayList<>();
            displayTools(tools, from, to, without, with);
            displayRecipes = without.isEmpty() ? List.of() : List.of(new DisplayRecipe(without, with));
        }
        return displayRecipes;
    }

    /** Adds an example tool per tool and source material, before and after the upgrade. Shared with the smithing table recipe. */
    static void displayTools(Ingredient tools, List<MaterialId> from, MaterialVariantId to, List<ItemStack> without, List<ItemStack> with) {
        for (MaterialId source : from) {
            for (ItemStack stack : tools.getItems()) {
                if (!(stack.getItem() instanceof IModifiable modifiable)) continue;
                List<MaterialStatsId> statTypes = ToolMaterialHook.stats(modifiable.getToolDefinition());
                MaterialNBT.Builder before = MaterialNBT.builder();
                MaterialNBT.Builder after = MaterialNBT.builder();
                boolean any = false;
                for (MaterialStatsId stat : statTypes) {
                    if (MaterialRegistry.getInstance().getMaterialStats(source, stat).isPresent() && MaterialRegistry.getInstance().getMaterialStats(to.getId(), stat).isPresent()) {
                        before.add(source);
                        after.add(to);
                        any = true;
                    } else {
                        before.add(MaterialRegistry.firstWithStatType(stat));
                        after.add(MaterialRegistry.firstWithStatType(stat));
                    }
                }
                if (!any) continue;
                without.add(ToolStack.createTool(stack.getItem(), modifiable.getToolDefinition(), before.build()).createStack());
                with.add(ToolStack.createTool(stack.getItem(), modifiable.getToolDefinition(), after.build()).createStack());
            }
        }
    }

    private class DisplayRecipe implements IDisplayToolTinkering {
        @Getter
        private final List<ItemStack> toolWithoutModifier, toolWithModifier;

        private DisplayRecipe(List<ItemStack> without, List<ItemStack> with) {
            this.toolWithoutModifier = without;
            this.toolWithModifier = with;
        }

        @Override
        public ResourceLocation getRecipeId() {
            return id;
        }

        @Override
        public Component getTitle() {
            return Component.translatable("recipe.tconstruct.material_swapping");
        }

        @Override
        public Component getTooltip() {
            return Component.translatable("recipe.tconstruct.material_swapping.tooltip");
        }

        @Override
        public int getInputCount() {
            return inputs.size();
        }

        @Override
        public List<ItemStack> getDisplayItems(int slot) {
            return slot < inputs.size() ? inputs.get(slot).getMatchingStacks() : List.of();
        }

        @Override
        public boolean isTool(ItemStack check) {
            return tools.test(check);
        }
    }
}
