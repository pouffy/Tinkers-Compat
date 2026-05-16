package io.github.pouffy.tcompat.compat.cataclysm.modifier;

import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.effect.Wave_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.*;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class CataclysmHandler {

    public static Entity createSandstorm(LivingEntity owner, double x, double y, double z, double X, double Z) {
        if (!CompatHelper.isLoaded("cataclysm")) return null;
        Sandstorm_Projectile sandstorm = new Sandstorm_Projectile(owner, x, y, z, owner.level(), 6.0F);
        sandstorm.setState(1);
        sandstorm.setPos(X, owner.getEyeY() - (double)0.5F, Z);
        return sandstorm;
    }

    public static Entity createVoidShard(Level level, double x, double y, double z, Vec3 vec, Entity owner, Entity hitEntity) {
        if (!CompatHelper.isLoaded("cataclysm")) return null;
        Void_Shard_Entity shard = new Void_Shard_Entity(level, (LivingEntity)owner, x + vec.x, y + vec.y + (double)0.25F, vec.z + z, vec, hitEntity);
        return shard;
    }

    public static AbstractArrow createPhantomArrow(Level level, LivingEntity shooter, @Nullable LivingEntity target) {
        if (!CompatHelper.isLoaded("cataclysm")) return null;
        Phantom_Arrow_Entity homingArrowEntity = new Phantom_Arrow_Entity(level, shooter);
        if (target != null) {
            homingArrowEntity = new Phantom_Arrow_Entity(level, shooter, target);
        }
        return homingArrowEntity;
    }

    public static void tidalWave(Level level, LivingEntity shooter) {
        float yawRadians = (float)Math.toRadians(90.0F + shooter.getYRot());
        double vecX = Math.cos(yawRadians);
        double vecZ = Math.sin(yawRadians);
        double vec = 2.0F;
        double spawnX = shooter.getX() + vecX * vec;
        double spawnY = shooter.getY();
        double spawnZ = shooter.getZ() + vecZ * vec;
        int numberOfWaves = 4;
        float angleStep = 25.0F;
        double firstAngleOffset = (double)(numberOfWaves - 1) / (double)2.0F * (double)angleStep;
        level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), ModSounds.HEAVY_SMASH.get(), SoundSource.PLAYERS, 0.6F, 1.0F);
        for(int k = 0; k < numberOfWaves; ++k) {
            double angle = (double)shooter.getYRot() - firstAngleOffset + (double)((float)k * angleStep);
            double rad = Math.toRadians(angle);
            double dx = -Math.sin(rad);
            double dz = Math.cos(rad);
            Wave_Entity WaveEntity = new Wave_Entity(level, shooter, 60, 6.0F);
            WaveEntity.setPos(spawnX, spawnY, spawnZ);
            WaveEntity.setState(1);
            WaveEntity.setYRot(-((float)(Mth.atan2(dx, dz) * (180D / Math.PI))));
            shooter.level().addFreshEntity(WaveEntity);
        }
    }

    public static Projectile createLaser(LivingEntity shooter, Vec3 vec) {
        if (!CompatHelper.isLoaded("cataclysm")) return null;
        Laser_Beam_Entity laser = new Laser_Beam_Entity(shooter, vec, shooter.level(), (float) CMCommonConfig.LaserGatling.damage);
        float yRot = (float)(Mth.atan2(vec.z, vec.x) * (180D / Math.PI)) + 90.0F;
        float xRot = (float)(-(Mth.atan2(vec.y, Math.sqrt(vec.x * vec.x + vec.z * vec.z)) * (180D / Math.PI)));
        laser.setYRot(yRot);
        laser.setXRot(xRot);
        laser.setPosRaw(shooter.getX(), shooter.getY() + (double)(shooter.getEyeHeight() * 0.8F), shooter.getZ());
        RandomSource rand = shooter.level().getRandom();
        shooter.gameEvent(GameEvent.ITEM_INTERACT_START);
        shooter.playSound(ModSounds.HARBINGER_LASER.get(), 0.2F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
        return laser;
    }

    public static ThrowableProjectile createHowitzer(LivingEntity shooter, float power) {
        if (!CompatHelper.isLoaded("cataclysm")) return null;
        Wither_Howitzer_Entity howitzer = new Wither_Howitzer_Entity(ModEntities.WITHER_HOWITZER.get(), shooter.level(), shooter);
        howitzer.setRadius(3.5F);
        howitzer.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, power, 1.0F);
        return howitzer;
    }

    public static Projectile createWitherMissile(LivingEntity entity, Vec3 vec3, double x, double z, float yRot, float xRot) {
        if (!CompatHelper.isLoaded("cataclysm")) return null;
        Wither_Missile_Entity witherMissile = new Wither_Missile_Entity(entity, vec3.normalize(), entity.level(), 16.0F);
        witherMissile.setYRot(yRot);
        witherMissile.setXRot(xRot);
        witherMissile.setPosRaw(x, entity.getEyeY(), z);
        return witherMissile;
    }
}
