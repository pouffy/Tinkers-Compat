package com.pouffydev.tcompat.util.explosion;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class SimpleExplosionDamageCalculator extends ExplosionDamageCalculator {
    private final boolean explodesBlocks;
    private final boolean damagesEntities;
    private final Optional<Float> knockbackMultiplier;
    private final Optional<HolderSet<Block>> immuneBlocks;

    public SimpleExplosionDamageCalculator(boolean explodesBlocks, boolean damagesEntities, Optional<Float> knockbackMultiplier, Optional<HolderSet<Block>> immuneBlocks) {
        this.explodesBlocks = explodesBlocks;
        this.damagesEntities = damagesEntities;
        this.knockbackMultiplier = knockbackMultiplier;
        this.immuneBlocks = immuneBlocks;
    }

    public Optional<Float> getBlockExplosionResistance(KnockbackExplosion explosion, BlockGetter reader, BlockPos pos, BlockState state, FluidState fluid) {
        if (this.immuneBlocks.isPresent()) {
            return state.is((HolderSet)this.immuneBlocks.get()) ? Optional.of(3600000.0F) : Optional.empty();
        } else {
            return super.getBlockExplosionResistance(explosion, reader, pos, state, fluid);
        }
    }

    public boolean shouldBlockExplode(KnockbackExplosion explosion, BlockGetter reader, BlockPos pos, BlockState state, float power) {
        return this.explodesBlocks;
    }

    public boolean shouldDamageEntity(KnockbackExplosion explosion, Entity entity) {
        return this.damagesEntities;
    }

    public float getEntityDamageAmount(KnockbackExplosion explosion, Entity entity) {
        float f = explosion.radius() * 2.0F;
        Vec3 vec3 = explosion.getPosition();
        double d0 = Math.sqrt(entity.distanceToSqr(vec3)) / (double)f;
        double d1 = ((double)1.0F - d0) * (double)Explosion.getSeenPercent(vec3, entity);
        return (float)((d1 * d1 + d1) / (double)2.0F * (double)7.0F * (double)f + (double)1.0F);
    }

    public float getKnockbackMultiplier(Entity entity) {
        boolean flag1;
        if (entity instanceof Player player) {
            if (player.getAbilities().flying) {
                flag1 = true;
                return flag1 ? 0.0F : (Float)this.knockbackMultiplier.orElse(1.0F);
            }
        }

        flag1 = false;
        return flag1 ? 0.0F : (Float)this.knockbackMultiplier.orElse(1.0F);
    }
}
