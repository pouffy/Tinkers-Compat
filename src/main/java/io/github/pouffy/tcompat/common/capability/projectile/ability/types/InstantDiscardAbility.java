package io.github.pouffy.tcompat.common.capability.projectile.ability.types;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;

public class InstantDiscardAbility extends AbstractProjectileAbility {
    @Override
    String serializedName() {
        return "instant_discard";
    }

    @Override
    public void tickProjectile(Projectile projectile, Projectile innerProjectile) {
        projectile.discard();
    }

    @Override
    public void tickArrow(AbstractArrow arrow, Projectile innerProjectile, boolean inGround, int inGroundTime) {
        arrow.discard();
    }
}
