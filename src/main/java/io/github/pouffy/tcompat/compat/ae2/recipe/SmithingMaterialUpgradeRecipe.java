package io.github.pouffy.tcompat.compat.ae2.recipe;

import io.github.pouffy.tcompat.compat.ae2.AE2Init;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import slimeknights.mantle.data.loadable.common.IngredientLoadable;
import slimeknights.mantle.data.loadable.field.ContextKey;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Smithing table version of {@link MaterialUpgradeRecipe}: template + Tinkers tool with a {@code from} part + addition gives the tool with those parts swapped to {@code to}.
 * Extends {@link SmithingTransformRecipe} so JEI (and other viewers) show it in the smithing category without a plugin.
 */
public class SmithingMaterialUpgradeRecipe extends SmithingTransformRecipe {
    public static final RecordLoadable<SmithingMaterialUpgradeRecipe> LOADER = RecordLoadable.create(
            ContextKey.ID.requiredField(),
            IngredientLoadable.DISALLOW_EMPTY.requiredField("template", r -> r.templateIngredient),
            IngredientLoadable.DISALLOW_EMPTY.requiredField("tools", r -> r.tools),
            MaterialId.PARSER.list(1).requiredField("from", r -> r.from),
            MaterialVariantId.LOADABLE.requiredField("to", r -> r.to),
            IngredientLoadable.DISALLOW_EMPTY.requiredField("addition", r -> r.additionIngredient),
            SmithingMaterialUpgradeRecipe::new);

    // the parent's fields are package private
    private final Ingredient templateIngredient, additionIngredient;
    private final Ingredient tools;
    private final List<MaterialId> from;
    private final MaterialVariantId to;

    public SmithingMaterialUpgradeRecipe(ResourceLocation id, Ingredient template, Ingredient tools, List<MaterialId> from, MaterialVariantId to, Ingredient addition) {
        super(id, template, toolsWith(tools, from, to), addition, ItemStack.EMPTY);
        this.templateIngredient = template;
        this.additionIngredient = addition;
        this.tools = tools;
        this.from = from;
        this.to = to;
    }

    /** Base ingredient: matches any of {@code tools} with a {@code from} part {@code to} can replace; its items are example tools, computed late as they need the material stats */
    private static Ingredient toolsWith(Ingredient tools, List<MaterialId> from, MaterialVariantId to) {
        return new Ingredient(Stream.empty()) {
            @Override
            public boolean test(@Nullable ItemStack stack) {
                return stack != null && tools.test(stack) && MaterialUpgradeRecipe.swap(ToolStack.from(stack), from, to) != null;
            }

            @Override
            public ItemStack[] getItems() {
                return examples(tools, from, to);
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean isSimple() {
                return false;
            }
        };
    }

    /** Example tools assemble actually upgrades, so viewers pair each base with its result */
    private static ItemStack[] examples(Ingredient tools, List<MaterialId> from, MaterialVariantId to) {
        List<ItemStack> without = new ArrayList<>();
        MaterialUpgradeRecipe.displayTools(tools, from, to, without, new ArrayList<>());
        return without.stream().filter(stack -> !upgrade(stack, from, to).isEmpty()).toArray(ItemStack[]::new);
    }

    /** The tool with its {@code from} parts swapped to {@code to}, or empty if none can be swapped or the result is invalid */
    private static ItemStack upgrade(ItemStack stack, List<MaterialId> from, MaterialVariantId to) {
        ToolStack original = ToolStack.from(stack);
        ToolStack copy = MaterialUpgradeRecipe.swap(original, from, to);
        return copy == null || MaterialUpgradeRecipe.validate(original, copy) != null ? ItemStack.EMPTY : copy.createStack();
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess access) {
        ItemStack stack = container.getItem(1);
        return isBaseIngredient(stack) ? upgrade(stack, from, to) : ItemStack.EMPTY;
    }

    /** Example result for viewers (JEI skips recipes without one) */
    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        ItemStack[] examples = examples(tools, from, to);
        return examples.length == 0 ? ItemStack.EMPTY : upgrade(examples[0], from, to);
    }

    /** The base ingredient's items depend on the material stats, which may not be synced yet when this is checked */
    @Override
    public boolean isIncomplete() {
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AE2Init.smithingMaterialUpgradeSerializer.get();
    }
}
