package io.github.pouffy.tcompat.compat.cataclysm.modifier;

import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class VoidScatterModifier extends NoLevelsModifier implements ProjectileHitModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT);
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target, boolean notBlocked) {
        hit(projectile, hit);
        return notBlocked;
    }

    @Override
    public boolean onProjectileHitsBlock(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, BlockHitResult hit, @Nullable LivingEntity owner) {
        hit(projectile, hit);
        return false;
    }

    private <T extends HitResult> void hit(Projectile projectile, T hit) {
        if (projectile.getPersistentData().getBoolean("VoidScattered")) return;
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
                Direction dir = Direction.UP;
                if (hit.getType() == HitResult.Type.ENTITY) {
                    target = ((EntityHitResult)hit).getEntity();
                } else if (hit.getType() == HitResult.Type.BLOCK) {
                    dir = ((BlockHitResult)hit).getDirection();
                }
                vec = vec.scale(0.35F);
                vec = mulPoseVector(vec, dir);
                if (CompatHelper.isLoaded("cataclysm")) {
                    var shard = CataclysmHandler.createVoidShard(projectile.level(), x, y, z, vec, projectile.getOwner(), target);
                    if (shard != null) projectile.level().addFreshEntity(shard);
                }
            }
            projectile.playSound(SoundEvents.GLASS_BREAK, 1.1F, 0.8F);
        }
        projectile.getPersistentData().putBoolean("VoidScattered", true);
    }

    public List<Vec3> getShootVectors(RandomSource random, float uncertainty) {
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

    private Vec3 mulPoseVector(Vec3 v, Direction dir) {
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
