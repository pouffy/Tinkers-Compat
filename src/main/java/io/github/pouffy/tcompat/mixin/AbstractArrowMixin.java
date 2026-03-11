package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.common.capability.phoenix.PhoenixTouched;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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
        PhoenixTouched.get(arrow).ifPresent(phoenixTouched -> {
            Projectile innerProjectile = phoenixTouched.getProjectile();
            if (phoenixTouched.isPhoenixProjectile()) {
                if (!innerProjectile.level().isClientSide()) {
                    phoenixTouched.setSynched(INBTSynchable.Direction.CLIENT, "setPhoenixProjectile", true); // Sync Phoenix Projectile variable to client.
                    if (this.inGround) { // Spawn less particles when the arrow is in the ground.
                        if (this.inGroundTime % 5 == 0) {
                            for (int i = 0; i < 1; i++) {
                                this.spawnParticles(innerProjectile);
                            }
                        }
                    } else {
                        for (int i = 0; i < 2; i++) {
                            this.spawnParticles(innerProjectile);
                        }
                    }
                }
            }
        });
    }

    private void spawnParticles(Projectile projectile) {
        if (projectile.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    projectile.getX() + (serverLevel.getRandom().nextGaussian() / 5.0),
                    projectile.getY() + (serverLevel.getRandom().nextGaussian() / 3.0),
                    projectile.getZ() + (serverLevel.getRandom().nextGaussian() / 5.0),
                    1, 0.0, 0.0, 0.0, 0.0F);
        }
    }
}
