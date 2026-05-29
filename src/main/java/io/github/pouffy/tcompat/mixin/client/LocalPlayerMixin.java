package io.github.pouffy.tcompat.mixin.client;

import io.github.pouffy.tcompat.compat.malum.MalumHandler;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    public LocalPlayerMixin() {
    }

    @Inject(
            method = {"hasEnoughFoodToStartSprinting"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void tcompat$hasEnoughFoodToStartSprinting(CallbackInfoReturnable<Boolean> cir) {
        if (MalumHandler.forceSprint((LocalPlayer)(Object)this)) {
            cir.setReturnValue(true);
        }
    }
}
