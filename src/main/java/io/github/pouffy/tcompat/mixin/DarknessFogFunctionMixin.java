package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FogRenderer.DarknessFogFunction.class)
public abstract class DarknessFogFunctionMixin implements FogRenderer.MobEffectFogFunction {

    @Override
    public boolean isEnabled(LivingEntity entity, float partialTicks) {
        if (EquipmentHelper.getEffectiveLevel(entity, TCModifiers.brightness) > 0) return false;
        return entity.hasEffect(this.getMobEffect());
    }
}
