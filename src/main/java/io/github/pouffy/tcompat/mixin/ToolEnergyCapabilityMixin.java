package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.compat.ad_astra.AdAstraInit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.function.Supplier;

@Mixin(value = ToolEnergyCapability.class, remap = false)
public abstract class ToolEnergyCapabilityMixin {

    @Final
    @Shadow
    private Supplier<? extends IToolStackView> tool;

    @Shadow
    public abstract boolean canReceive();

    @Inject(method = "canReceive()Z", at = @At("HEAD"), cancellable = true)
    private void canReceive(CallbackInfoReturnable<Boolean> cir) {
        IToolStackView tool = this.tool.get();
        if (tool.getModifier(AdAstraInit.discharge.getId()) != ModifierEntry.EMPTY) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "receiveEnergy(IZ)I", at = @At("HEAD"), cancellable = true)
    private void receiveEnergy(int amount, boolean simulate, CallbackInfoReturnable<Integer> cir) {
        if (!canReceive()) {
            cir.setReturnValue(0);
        }
    }
}
