package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.common.capability.projectile.ability.ProjectileAbility;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {
    @Shadow
    protected boolean inGround;
    @Shadow
    protected int inGroundTime;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/Projectile;tick()V", shift = At.Shift.AFTER), method = "tick")
    private void tick(CallbackInfo ci) {
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        ProjectileAbility.get(arrow).ifPresent(projectileAbility -> {
            Projectile innerProjectile = projectileAbility.projectile();
            projectileAbility.getActiveAbilities().forEach((name, ability) -> {
                ability.tickArrow(arrow, innerProjectile, this.inGround, this.inGroundTime);
                projectileAbility.sendClient(ability);
            });
        });
    }
}
