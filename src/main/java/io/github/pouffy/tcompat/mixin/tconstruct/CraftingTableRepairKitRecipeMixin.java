package io.github.pouffy.tcompat.mixin.tconstruct;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.pouffy.tcompat.compat.caverns_chasms.CavernsChasmsMaterials;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.tools.definition.module.material.MaterialRepairToolHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;
import slimeknights.tconstruct.tables.recipe.CraftingTableRepairKitRecipe;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

@Mixin(value = CraftingTableRepairKitRecipe.class, remap = false)
public abstract class CraftingTableRepairKitRecipeMixin {

    @Shadow
    protected abstract boolean toolMatches(ItemStack stack);

    @WrapOperation(method = "matches(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/world/level/Level;)Z", at = @At(value = "INVOKE", target = "Lslimeknights/tconstruct/library/tools/definition/module/material/MaterialRepairToolHook;canRepairWith(Lslimeknights/tconstruct/library/tools/nbt/IToolStackView;Lslimeknights/tconstruct/library/materials/definition/MaterialId;)Z"))
    public boolean canRepairWith(IToolStackView tool, MaterialId material, Operation<Boolean> original) {
        if (material.getId().equals(CavernsChasmsMaterials.zirconia)) {
            return true;
        }
        return original.call(tool, material);
    }

    @WrapOperation(method = "assemble(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/core/RegistryAccess;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lslimeknights/tconstruct/tables/recipe/CraftingTableRepairKitRecipe;getRepairAmount(Lslimeknights/tconstruct/library/tools/nbt/IToolStackView;Lnet/minecraft/world/item/ItemStack;)F"))
    public float getRepairAmount(CraftingTableRepairKitRecipe instance, IToolStackView tool, ItemStack repairStack, Operation<Float> original) {
        var kitMaterial = IMaterialItem.getMaterialFromStack(repairStack).getId();
        if (kitMaterial.equals(CavernsChasmsMaterials.zirconia)) {
            float amount = MaterialRepairToolHook.repairAmount(tool, tool.getMaterial(0).getId());
            if (amount <= 0.0) {
                amount = MaterialRepairToolHook.repairAmount(tool, MaterialIds.slimewood);
            }
            return amount;
        }
        return original.call(instance, tool, repairStack);
    }
}
