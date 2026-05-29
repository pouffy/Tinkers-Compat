package io.github.pouffy.tcompat.mixin.client;

import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(method = "doesMobEffectBlockSky(Lnet/minecraft/client/Camera;)Z", at = @At("HEAD"), cancellable = true)
    public void doesMobEffectBlockSky(Camera camera, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = camera.getEntity();
        if (entity instanceof LivingEntity livingentity) {
            if (livingentity.hasEffect(MobEffects.DARKNESS) && EquipmentHelper.getEffectiveLevel(livingentity, TCModifiers.brightness) > 0) cir.setReturnValue(false);
        }
    }
}
