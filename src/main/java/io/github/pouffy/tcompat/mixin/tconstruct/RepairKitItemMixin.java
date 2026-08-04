package io.github.pouffy.tcompat.mixin.tconstruct;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.pouffy.tcompat.compat.caverns_chasms.CavernsChasmsMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.tools.definition.module.material.MaterialRepairToolHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import slimeknights.tconstruct.tools.item.RepairKitItem;

@Mixin(value = RepairKitItem.class, remap = false)
public class RepairKitItemMixin {

    @WrapOperation(method = "overrideStackedOnOther(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/inventory/Slot;Lnet/minecraft/world/inventory/ClickAction;Lnet/minecraft/world/entity/player/Player;)Z", at = @At(value = "INVOKE", target = "Lslimeknights/tconstruct/library/tools/definition/module/material/MaterialRepairToolHook;repairAmount(Lslimeknights/tconstruct/library/tools/nbt/IToolStackView;Lslimeknights/tconstruct/library/materials/definition/MaterialId;)F"))
    public float getRepairAmount(IToolStackView tool, MaterialId material, Operation<Float> original) {
        if (CavernsChasmsMaterials.zirconia.matches(MaterialRegistry.getMaterial(material))) {
            float amount = MaterialRepairToolHook.repairAmount(tool, tool.getMaterial(0).getId());
            if (amount <= 0.0) {
                amount = MaterialRepairToolHook.repairAmount(tool, MaterialIds.slimewood);
            }
            return amount;
        }
        return original.call(tool, material);
    }
}
