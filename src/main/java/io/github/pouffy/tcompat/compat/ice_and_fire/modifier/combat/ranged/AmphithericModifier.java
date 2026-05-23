package io.github.pouffy.tcompat.compat.ice_and_fire.modifier.combat.ranged;

import io.github.pouffy.tcompat.common.capability.projectile.ability.ProjectileAbility;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import java.util.function.Predicate;

public class AmphithericModifier extends NoLevelsModifier implements ProjectileLaunchModifierHook, ProjectileHitModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_LAUNCH, ModifierHooks.PROJECTILE_SHOT, ModifierHooks.PROJECTILE_THROWN, ModifierHooks.PROJECTILE_HIT);
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        ProjectileAbility.activate(projectile, "amphithere");
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @javax.annotation.Nullable LivingEntity attacker, @javax.annotation.Nullable LivingEntity target) {
        launch(projectile, target);
        return false;
    }

    @Override
    public void onProjectileHitBlock(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, BlockHitResult hit, @javax.annotation.Nullable LivingEntity attacker) {
        launch(projectile, null);
    }

    public static void launch(Projectile projectile, @Nullable Entity hitEntity) {
        Predicate<Entity> selector = (entity) -> !(entity instanceof Projectile) && entity != hitEntity;
        RandomSource random = projectile.level().random;
        double xRatio = projectile.getDeltaMovement().x;
        double zRatio = projectile.getDeltaMovement().z;
        AABB bb = new AABB(projectile.blockPosition()).inflate(2.5f);
        Entity ignored = projectile;
        if (hitEntity != null) {
            ignored = hitEntity;
            launchEntity(hitEntity, xRatio, zRatio);
        }
        projectile.level().getEntities(ignored, bb, selector).forEach(entity -> {
            launchEntity(entity, xRatio, zRatio);
        });
        if (projectile.level().isClientSide) {
            for(int height = 0; height < 1 + random.nextInt(2); ++height) {
                for(int i = 0; i < 20; ++i) {
                    double d0 = random.nextGaussian() * 0.02;
                    double d1 = random.nextGaussian() * 0.02;
                    double d2 = random.nextGaussian() * 0.02;
                    double d3 = 10.0F;
                    double xRatio1 = projectile.getDeltaMovement().x * (double)projectile.getBbWidth();
                    double zRatio1 = projectile.getDeltaMovement().z * (double)projectile.getBbWidth();
                    projectile.level().addParticle(ParticleTypes.CLOUD, projectile.getX() + xRatio1 + (double)(random.nextFloat() * projectile.getBbWidth() * 1.0F) - (double)projectile.getBbWidth() - d0 * d3, projectile.getY() + (double)(random.nextFloat() * projectile.getBbHeight()) - d1 * d3, projectile.getZ() + zRatio1 + (double)(random.nextFloat() * projectile.getBbWidth() * 1.0F) - (double)projectile.getBbWidth() - d2 * d3, d0, d1, d2);
                }
            }
        } else {
            projectile.level().broadcastEntityEvent(projectile, (byte)20);
        }
        ProjectileAbility.deactivate(projectile, "amphithere");
    }

    public static void launchEntity(Entity entity, double xRatio, double zRatio) {
        float f = Mth.sqrt((float)(xRatio * xRatio + zRatio * zRatio));
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.5F, 1.0F, 0.5F).subtract(xRatio / (double)f * (double)-1.4F, 0.0F, zRatio / (double)f * (double)-1.4F).add(0.0F, 0.6, 0.0F));
        entity.hasImpulse = true;
    }
}
