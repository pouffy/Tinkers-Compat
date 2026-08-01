package io.github.pouffy.tcompat.common.data.recipe;

import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.recipe.RecipeResult;
import slimeknights.tconstruct.library.recipe.material.MaterialRecipe;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationContainer;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationRecipe;
import slimeknights.tconstruct.library.tools.definition.module.material.MaterialRepairToolHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.LazyToolStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.part.IRepairKitItem;

import java.util.function.IntConsumer;

//@RequiredArgsConstructor
//public class TinkerStationZirconiaRepairRecipe implements ITinkerStationRecipe {
//    protected static final RecipeResult<LazyToolStack> FULLY_REPAIRED = RecipeResult.failure(TConstruct.makeTranslationKey("recipe", "tool_repair.fully_repaired"), new Object[0]);
//    private static final IntConsumer NO_ACTION = (i) -> {
//    };
//    private final ResourceLocation id;
//
//    protected static MaterialId getMaterialFrom(ITinkerStationContainer inv, int slot) {
//        ItemStack item = inv.getInput(slot);
//        Item var4 = item.getItem();
//        if (var4 instanceof IRepairKitItem kit) {
//            return kit.getMaterial(item).getId();
//        } else {
//            MaterialRecipe recipe = inv.getInputMaterial(slot);
//            return recipe != null ? recipe.getMaterial().getId() : IMaterial.UNKNOWN_ID;
//        }
//    }
//
//    protected float getRepairAmount(IToolStackView tool, MaterialId repairMaterial) {
//        return MaterialRepairToolHook.repairAmount(tool, repairMaterial);
//    }
//
//    protected float getRepairPerItem(ToolStack tool, ITinkerStationContainer inv, int slot, MaterialId repairMaterial) {
//        // repair stat may be null in the modifier repair recipe
//        float amount = getRepairAmount(tool, repairMaterial);
//        if (amount > 0) {
//            ItemStack stack = inv.getInput(slot);
//            // repair kit first
//            if (stack.getItem() instanceof IRepairKitItem kit) {
//                // multiply by repair kit value, divide again by the repair factor to get the final percent
//                return amount * kit.getRepairAmount() / MaterialRecipe.INGOTS_PER_REPAIR;
//            } else {
//                // material recipe fallback
//                MaterialRecipe recipe = inv.getInputMaterial(slot);
//                if (recipe != null) {
//                    return recipe.scaleRepair(amount);
//                }
//            }
//        }
//        return 0;
//    }
//}
