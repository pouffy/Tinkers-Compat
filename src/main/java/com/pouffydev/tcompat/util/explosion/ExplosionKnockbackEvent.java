package com.pouffydev.tcompat.util.explosion;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.level.ExplosionEvent;

import java.util.List;

@SuppressWarnings({"LombokGetterMayBeUsed", "LombokSetterMayBeUsed"})
public class ExplosionKnockbackEvent extends ExplosionEvent {
    private final Entity entity;
    private Vec3 knockbackVelocity;

    public ExplosionKnockbackEvent(Level level, Explosion explosion, Entity entity, Vec3 knockbackVelocity) {
        super(level, explosion);
        this.entity = entity;
        this.knockbackVelocity = knockbackVelocity;
    }

    public List<BlockPos> getAffectedBlocks() {
        return this.getExplosion().getToBlow();
    }

    public Entity getAffectedEntity() {
        return this.entity;
    }

    public Vec3 getKnockbackVelocity() {
        return this.knockbackVelocity;
    }

    public void setKnockbackVelocity(Vec3 newKnockbackVelocity) {
        this.knockbackVelocity = newKnockbackVelocity;
    }
}
