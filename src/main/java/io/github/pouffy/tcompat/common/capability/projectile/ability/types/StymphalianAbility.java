package io.github.pouffy.tcompat.common.capability.projectile.ability.types;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;

public class StymphalianAbility implements IProjectileAbility {

    private boolean isActive;

    @Override
    public String serializedName() {
        return "stymphalian";
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public void setActive(boolean active) {
        this.isActive = active;
    }

    @Override
    public void tickArrow(AbstractArrow arrow, Projectile innerProjectile, boolean inGround, int inGroundTime) {
        float sqrt = Mth.sqrt((float)(arrow.getDeltaMovement().x * arrow.getDeltaMovement().x + arrow.getDeltaMovement().z * arrow.getDeltaMovement().z));
        if (sqrt < 0.1F) {
            arrow.setDeltaMovement(arrow.getDeltaMovement().add(0.0F, -0.01F, 0.0F));
        }
        if (!arrow.isNoGravity()) arrow.setNoGravity(true);
    }

    @Override
    public void tickProjectile(Projectile projectile, Projectile innerProjectile) {
        float sqrt = Mth.sqrt((float) (projectile.getDeltaMovement().x * projectile.getDeltaMovement().x + projectile.getDeltaMovement().z * projectile.getDeltaMovement().z));
        if (sqrt < 0.1F) {
            projectile.setDeltaMovement(projectile.getDeltaMovement().add(0.0F, -0.01F, 0.0F));
        }
        if (!projectile.isNoGravity()) projectile.setNoGravity(true);
    }
}
