package io.github.pouffy.tcompat.common.data.recipe;

import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.caverns_chasms.CavernsChasmsMaterials;
import lombok.Getter;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.recipe.RecipeResult;
import slimeknights.tconstruct.library.recipe.material.MaterialRecipe;
import slimeknights.tconstruct.library.recipe.tinkerstation.IMutableTinkerStationContainer;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationContainer;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationRecipe;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.definition.module.material.MaterialRepairToolHook;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.LazyToolStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.part.IRepairKitItem;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import java.util.function.IntConsumer;

@Getter
public class TinkerStationZirconiaRepairRecipe implements ITinkerStationRecipe {
    protected static final RecipeResult<LazyToolStack> FULLY_REPAIRED = RecipeResult.failure(TConstruct.makeTranslationKey("recipe", "tool_repair.fully_repaired"), new Object[0]);
    private static final IntConsumer NO_ACTION = (i) -> {};
    private final ResourceLocation id;

    protected static MaterialId getMaterialFrom(ITinkerStationContainer inv, int slot) {
        ItemStack item = inv.getInput(slot);
        Item var4 = item.getItem();
        if (var4 instanceof IRepairKitItem kit) {
            return kit.getMaterial(item).getId();
        } else {
            MaterialRecipe recipe = inv.getInputMaterial(slot);
            return recipe != null ? recipe.getMaterial().getId() : IMaterial.UNKNOWN_ID;
        }
    }

    protected float getRepairAmount(IToolStackView tool, MaterialId repairMaterial) {
        float amount = MaterialRepairToolHook.repairAmount(tool, tool.getMaterial(0).getId());
        if (amount <= 0.0) {
            amount = MaterialRepairToolHook.repairAmount(tool, MaterialIds.slimewood);
        }
        return amount;
    }

    protected float getRepairPerItem(ToolStack tool, ITinkerStationContainer inv, int slot, MaterialId repairMaterial) {
        // repair stat may be null in the modifier repair recipe
        float amount = getRepairAmount(tool, repairMaterial);
        if (amount > 0) {
            ItemStack stack = inv.getInput(slot);
            // repair kit first
            if (stack.getItem() instanceof IRepairKitItem kit) {
                // multiply by repair kit value, divide again by the repair factor to get the final percent
                return amount * kit.getRepairAmount() / MaterialRecipe.INGOTS_PER_REPAIR;
            } else {
                // material recipe fallback
                MaterialRecipe recipe = inv.getInputMaterial(slot);
                if (recipe != null) {
                    return recipe.scaleRepair(amount);
                }
            }
        }
        return 0;
    }

    public boolean matches(ITinkerStationContainer inv, Level world) {
        ItemStack tinkerable = inv.getTinkerableStack();
        if (!tinkerable.isEmpty() && tinkerable.is(TinkerTags.Items.DURABILITY)) {
            for(int i = 0; i < inv.getInputCount(); ++i) {
                ItemStack stack = inv.getInput(i);
                if (!stack.isEmpty()) {
                    MaterialId inputMaterial = getMaterialFrom(inv, i);
                    return CavernsChasmsMaterials.zirconia.matches(MaterialRegistry.getMaterial(inputMaterial));
                }
            }
        }
        return false;
    }

    public int shrinkToolSlotBy() {
        return 1;
    }

    public RecipeResult<LazyToolStack> getValidatedResult(ITinkerStationContainer inv, RegistryAccess access) {
        ToolStack tool = inv.getTinkerable();
        if (tool.getDefinition() == ToolDefinition.EMPTY) {
            return RecipeResult.pass();
        } else if (!tool.isBroken() && tool.getDamage() == 0) {
            return FULLY_REPAIRED;
        } else {
            int repairNeeded = tool.getDamage();
            int repairRemaining = repairNeeded;

            for(int i = 0; i < inv.getInputCount() && repairRemaining > 0; ++i) {
                repairRemaining -= this.repairFromSlot(tool, inv, repairRemaining, i, NO_ACTION);
            }

            if (repairRemaining < repairNeeded) {
                tool = tool.copy();
                ToolDamageUtil.repair(tool, repairNeeded - repairRemaining);
                return LazyToolStack.successCopy(tool, 1, inv.getTinkerableStack());
            } else {
                return RecipeResult.pass();
            }
        }
    }

    public void updateInputs(LazyToolStack result, IMutableTinkerStationContainer inv, boolean isServer) {
        ToolStack inputTool = ToolStack.from(inv.getTinkerableStack());
        int repairRemaining = inputTool.getDamage() - result.getTool().getDamage();

        for(int i = 0; i < inv.getInputCount() && repairRemaining > 0; ++i) {
            int slot = i;
            repairRemaining -= this.repairFromSlot(inputTool, inv, repairRemaining, i, (count) -> inv.shrinkInput(slot, count));
        }

        if (repairRemaining > 0) {
            TConstruct.LOG.error("Recipe repair on {} consumed too few items. {} durability unaccounted for", result, repairRemaining);
        }

    }

    protected int repairFromSlot(ToolStack tool, ITinkerStationContainer inv, int repairNeeded, int slot, IntConsumer amountConsumer) {
        ItemStack stack = inv.getInput(slot);
        if (!stack.isEmpty()) {
            MaterialId repairMaterial = getMaterialFrom(inv, slot);
            if (!repairMaterial.equals(IMaterial.UNKNOWN_ID)) {
                float durabilityPerItem = this.getRepairPerItem(tool, inv, slot, repairMaterial);
                if (durabilityPerItem > 0.0F) {
                    for(ModifierEntry entry : tool.getModifierList()) {
                        durabilityPerItem = entry.getHook(ModifierHooks.REPAIR_FACTOR).getRepairFactor(tool, entry, durabilityPerItem);
                        if (durabilityPerItem <= 0.0F) {
                            return 0;
                        }
                    }

                    int applied = Math.min(stack.getCount(), (int)Math.ceil((float)repairNeeded / durabilityPerItem));
                    amountConsumer.accept(applied);
                    return (int)((float)applied * durabilityPerItem);
                }
            }
        }

        return 0;
    }

    public RecipeSerializer<?> getSerializer() {
        return GlobalInit.tinkerStationZirconiaRepairSerializer.get();
    }

    public TinkerStationZirconiaRepairRecipe(ResourceLocation id) {
        this.id = id;
    }

}
