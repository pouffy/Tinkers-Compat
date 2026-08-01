package io.github.pouffy.tcompat.mixin.tconstruct;

import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.caverns_chasms.CavernsChasmsMaterials;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.tools.definition.module.material.StatlessPartRepairModule;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

@Mixin(value = StatlessPartRepairModule.class, remap = false)
public class StatlessPartRepairModuleMixin {

    @Shadow
    @Final
    private int repairAmount;

    @Inject(method = "isRepairMaterial(Lslimeknights/tconstruct/library/tools/nbt/IToolStackView;Lslimeknights/tconstruct/library/materials/definition/MaterialId;)Z", at = @At("HEAD"), cancellable = true)
    private void isRepairMaterial(IToolStackView tool, MaterialId material, CallbackInfoReturnable<Boolean> cir) {
        if (CompatHelper.isLoaded("caverns_and_chasms")) {
            if (material == CavernsChasmsMaterials.zirconia) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "getRepairAmount(Lslimeknights/tconstruct/library/tools/nbt/IToolStackView;Lslimeknights/tconstruct/library/materials/definition/MaterialId;)F", at = @At("HEAD"), cancellable = true)
    private void getRepairAmount(IToolStackView tool, MaterialId material, CallbackInfoReturnable<Float> cir) {
        if (CompatHelper.isLoaded("caverns_and_chasms")) {
            if (material.getId().equals(CavernsChasmsMaterials.zirconia)) {
                cir.setReturnValue((float) this.repairAmount);
            }
        }
    }
}
