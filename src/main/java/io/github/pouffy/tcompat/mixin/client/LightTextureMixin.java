package io.github.pouffy.tcompat.mixin.client;

import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightTexture.class)
public class LightTextureMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "getDarknessGamma(F)F", at = @At("HEAD"), cancellable = true)
    public void getDarknessGamma(float partialTick, CallbackInfoReturnable<Float> cir) {
        var player = this.minecraft.player;
        if (player != null && player.hasEffect(MobEffects.DARKNESS)) {
            if (EquipmentHelper.getEffectiveLevel(player, TCModifiers.brightness) > 0) cir.setReturnValue(0.0F);
        }
    }
}
