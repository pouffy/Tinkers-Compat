package io.github.pouffy.tcompat.mixin.tconstruct;

import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.caverns_chasms.CavernsChasmsMaterials;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.definition.module.material.MaterialRepairModule;
import slimeknights.tconstruct.library.tools.definition.module.material.MaterialStatsModule;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import java.util.List;

@Mixin(value = MaterialStatsModule.class, remap = false)
public abstract class MaterialStatsModuleMixin {

    @Shadow
    protected abstract int[] getRepairIndices();

    @Shadow
    @Final
    private List<MaterialStatsId> statTypes;

    @Inject(method = "isRepairMaterial(Lslimeknights/tconstruct/library/tools/nbt/IToolStackView;Lslimeknights/tconstruct/library/materials/definition/MaterialId;)Z", at = @At("HEAD"), cancellable = true)
    public void isRepairMaterial(IToolStackView tool, MaterialId material, CallbackInfoReturnable<Boolean> cir) {
        if (CompatHelper.isLoaded("caverns_and_chasms")) {
            if (material == CavernsChasmsMaterials.zirconia) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "getRepairAmount(Lslimeknights/tconstruct/library/tools/nbt/IToolStackView;Lslimeknights/tconstruct/library/materials/definition/MaterialId;)F", at = @At("HEAD"), cancellable = true)
    public void getRepairAmount(IToolStackView tool, MaterialId material, CallbackInfoReturnable<Float> cir) {
        ResourceLocation toolId = tool.getDefinition().getId();
        if (CompatHelper.isLoaded("caverns_and_chasms")) {
            for(int i : this.getRepairIndices()) {
                if (material.getId().equals(CavernsChasmsMaterials.zirconia)) {
                    cir.setReturnValue((float) MaterialRepairModule.getDurability(toolId, tool.getMaterial(i).getId(), this.statTypes.get(i)));
                }
            }
        }
    }
}
