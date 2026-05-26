package io.github.pouffy.tcompat.common.capability.projectile.ability.types;

import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.cataclysm.CataclysmHandler;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

import java.util.ArrayList;
import java.util.List;

public class VoidScatterAbility extends AbstractProjectileAbility {

    @Override
    public String serializedName() {
        return "void_scatter";
    }

    @Override
    public void impactEvent(ProjectileImpactEvent event, Projectile projectile) {
        var hitResult = event.getRayTraceResult();
        double x = projectile.getX();
        double y = projectile.getY();
        double z = projectile.getZ();
        var random = projectile.level().random;
        if (projectile.level().isClientSide) {
            for(int l2 = 0; l2 < 8; ++l2) {
                Item scatterArrow = ObjectRetriever.getItem("cataclysm:void_scatter_arrow").orElse(Items.AIR);
                projectile.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(scatterArrow)), x, y, z, random.nextGaussian() * 0.1, random.nextDouble() * 0.15, random.nextGaussian() * 0.1);
            }
        } else {
            for (Vec3 vec : getShootVectors(random, 0.0F)) {
                Entity target = null;
                net.minecraft.core.Direction dir = net.minecraft.core.Direction.UP;
                if (hitResult.getType() == HitResult.Type.ENTITY) {
                    target = ((EntityHitResult)hitResult).getEntity();
                } else if (hitResult.getType() == HitResult.Type.BLOCK) {
                    dir = ((BlockHitResult)hitResult).getDirection();
                }
                vec = vec.scale(0.35F);
                vec = mulPoseVector(vec, dir);
                var shard = CataclysmHandler.createVoidShard(projectile.level(), x, y, z, vec, projectile.getOwner(), target);
                if (shard != null) projectile.level().addFreshEntity(shard);
            }
            projectile.playSound(SoundEvents.GLASS_BREAK, 1.1F, 0.8F);
        }
        this.setActive(false);
    }

    private List<Vec3> getShootVectors(RandomSource random, float uncertainty) {
        List<Vec3> vectors = new ArrayList<>();
        float turnFraction = (1.0F + Mth.sqrt(5.0F)) / 2.0F;
        int numPoints = 17;
        double fullness = 0.8;

        for(int i = 1; i <= numPoints; ++i) {
            float dst = (float)i / (float)numPoints;
            float inclination = (random.nextFloat() - 0.5F) * uncertainty + (float)Math.acos((double)1.0F - fullness * (double)dst);
            float azimuth = (float)((double)((random.nextFloat() - 0.5F) * uncertainty) + (Math.PI * 2D) * (double)(random.nextFloat() + turnFraction * (float)i));
            double x = Math.sin(inclination) * Math.cos(azimuth);
            double z = Math.sin(inclination) * Math.sin(azimuth);
            double y = Math.cos(inclination);
            Vec3 vec = new Vec3(x, y, z);
            if (i == 1) {
                vec = vec.add(0.0F, 1.0F, 0.0F);
                vec = vec.scale(0.5F);
            }

            vectors.add(vec);
        }

        return vectors;
    }

    private Vec3 mulPoseVector(Vec3 v, net.minecraft.core.Direction dir) {
        return switch (dir) {
            case DOWN -> v.multiply(0.0F, -1.0F, 0.0F);
            case NORTH -> new Vec3(v.z, v.x, -v.y);
            case SOUTH -> new Vec3(v.z, v.x, v.y);
            case WEST -> new Vec3(-v.y, v.z, v.x);
            case EAST -> new Vec3(v.y, v.z, v.x);
            default -> v;
        };
    }
}
