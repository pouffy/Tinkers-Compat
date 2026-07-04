package io.github.pouffy.tcompat.compat.cataclysm;

import com.github.L_Ender.cataclysm.entity.effect.Wave_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.*;
import com.github.L_Ender.cataclysm.init.*;
import com.github.L_Ender.lionfishapi.server.event.StandOnFluidEvent;
import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.cooldown.ModifierCooldowns;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.common.network.GazeOfHeatPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import slimeknights.tconstruct.common.TinkerTags;

import javax.annotation.Nullable;

public class CataclysmHandler {

    public static final CataclysmHandler instance = new CataclysmHandler();

    public void init() {
        if (!CompatHelper.isLoaded("cataclysm")) return;
        MinecraftForge.EVENT_BUS.register(new LoadedOnly());
    }

    public static Entity createSandstorm(LivingEntity owner, double x, double y, double z, double X, double Z) {
        if (!CompatHelper.isLoaded("cataclysm")) return null;
        return LoadedOnly.createSandstorm(owner, x, y, z, X, Z);
    }

    public static Entity createVoidShard(Level level, double x, double y, double z, Vec3 vec, Entity owner, Entity hitEntity) {
        if (!CompatHelper.isLoaded("cataclysm")) return null;
        return LoadedOnly.createVoidShard(level, x, y, z, vec, owner, hitEntity);
    }

    public static AbstractArrow createPhantomArrow(Level level, LivingEntity shooter, @Nullable LivingEntity target) {
        if (!CompatHelper.isLoaded("cataclysm")) return null;
        return LoadedOnly.createPhantomArrow(level, shooter, target);
    }

    public static void tidalWave(Level level, LivingEntity shooter) {
        if (!CompatHelper.isLoaded("cataclysm")) return;
        LoadedOnly.tidalWave(level, shooter);
    }

    public static ThrowableProjectile createHowitzer(LivingEntity shooter, float power) {
        if (!CompatHelper.isLoaded("cataclysm")) return null;
        return LoadedOnly.createHowitzer(shooter, power);
    }

    public static Projectile createWitherMissile(LivingEntity entity, Vec3 vec3, double x, double z, float yRot, float xRot) {
        if (!CompatHelper.isLoaded("cataclysm")) return null;
        return LoadedOnly.createWitherMissile(entity, vec3, x, z, yRot, xRot);
    }

    public static void ghostDodge(LivingHurtEvent event) {
        float dodgeChance;
        boolean projectile = event.getSource().is(DamageTypeTags.IS_PROJECTILE);
        boolean bypass = event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY);
        dodgeChance = EquipmentHelper.getEffectiveLevel(event.getEntity(), TCModifiers.ghostly, (stack) -> stack.is(TCTags.Items.JEWELERY_MODIFIABLE) || stack.is(TinkerTags.Items.WORN_ARMOR)) * (projectile ? 0.12F : 0.06F);
        if ((dodgeChance != 0.0F && event.getEntity().getRandom().nextFloat() < dodgeChance) && !bypass) {
            event.setCanceled(true);
        }
    }

    public static void flameReflex(LivingHurtEvent event, LivingEntity attacker) {
        if (!CompatHelper.isLoaded("cataclysm")) return;
        boolean canUse = EquipmentHelper.hasModifier(event.getEntity(), TCModifiers.ignitium, (stack) -> stack.is(TinkerTags.Items.LEGGINGS));
        if (canUse) {
            TCompat.LOGGER.info("Flame Reflex Success");
            LoadedOnly.flameReflex(event, attacker);
        }
    }

    public static void gazeOfHeatUse(LivingEntity user) {
        if (!CompatHelper.isLoaded("cataclysm")) return;
        LoadedOnly.gazeOfHeatUse(user);
    }

    public static class LoadedOnly {

        @SubscribeEvent
        public void lavaWalker(StandOnFluidEvent event) {
            boolean canUse = EquipmentHelper.hasModifier(event.getEntity(), TCModifiers.ignitium, (stack) -> stack.is(TinkerTags.Items.BOOTS));
            if (canUse && !event.getEntity().isShiftKeyDown() && (event.getFluidState().is(Fluids.LAVA) || event.getFluidState().is(Fluids.FLOWING_LAVA))) {
                event.setCanceled(true);
            }
        }

        public static Entity createSandstorm(LivingEntity owner, double x, double y, double z, double X, double Z) {
            Sandstorm_Projectile sandstorm = new Sandstorm_Projectile(owner, x, y, z, owner.level(), 6.0F);
            sandstorm.setState(1);
            sandstorm.setPos(X, owner.getEyeY() - (double)0.5F, Z);
            return sandstorm;
        }

        public static Entity createVoidShard(Level level, double x, double y, double z, Vec3 vec, Entity owner, Entity hitEntity) {
            return new Void_Shard_Entity(level, (LivingEntity)owner, x + vec.x, y + vec.y + (double)0.25F, vec.z + z, vec, hitEntity);
        }

        public static AbstractArrow createPhantomArrow(Level level, LivingEntity shooter, @Nullable LivingEntity target) {
            Phantom_Arrow_Entity homingArrowEntity = new Phantom_Arrow_Entity(level, shooter);
            if (target != null) {
                homingArrowEntity = new Phantom_Arrow_Entity(level, shooter, target);
            }
            return homingArrowEntity;
        }

        public static ThrowableProjectile createHowitzer(LivingEntity shooter, float power) {
            Wither_Howitzer_Entity howitzer = new Wither_Howitzer_Entity(ModEntities.WITHER_HOWITZER.get(), shooter.level(), shooter);
            howitzer.setRadius(3.5F);
            howitzer.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, power, 1.0F);
            return howitzer;
        }

        public static Projectile createWitherMissile(LivingEntity entity, Vec3 vec3, double x, double z, float yRot, float xRot) {
            Wither_Missile_Entity witherMissile = new Wither_Missile_Entity(entity, vec3.normalize(), entity.level(), 16.0F);
            witherMissile.setYRot(yRot);
            witherMissile.setXRot(xRot);
            witherMissile.setPosRaw(x, entity.getEyeY(), z);
            return witherMissile;
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

        public static void flameReflex(LivingHurtEvent event, LivingEntity attacker) {
            if (event.getSource() != null && attacker != null) {
                if (attacker != event.getEntity() && event.getEntity().getRandom().nextFloat() < 0.5F) {
                    MobEffectInstance existingBrand = attacker.getEffect(ModEffect.EFFECTBLAZING_BRAND.get());
                    int amplifier = 1;
                    if (existingBrand != null) {
                        amplifier += existingBrand.getAmplifier();
                        attacker.removeEffectNoUpdate(ModEffect.EFFECTBLAZING_BRAND.get());
                    } else {
                        --amplifier;
                    }

                    amplifier = Mth.clamp(amplifier, 0, 4);
                    MobEffectInstance blazingBrand = new MobEffectInstance(ModEffect.EFFECTBLAZING_BRAND.get(), 100, amplifier, false, false, true);
                    attacker.addEffect(blazingBrand);
                    if (!attacker.isOnFire()) {
                        attacker.setSecondsOnFire(5);
                    }
                }
            }
        }

        public static void gazeOfHeatUse(LivingEntity user) {
            boolean flag = false;

            for(Entity entity : user.level().getEntities(user, user.getBoundingBox().inflate(16.0F))) {
                if (entity instanceof LivingEntity target) {
                    MobEffectInstance existingBrand = target.getEffect(ModEffect.EFFECTBLAZING_BRAND.get());
                    int amplifier = 1;
                    if (existingBrand != null) {
                        amplifier += existingBrand.getAmplifier();
                        target.removeEffectNoUpdate(ModEffect.EFFECTBLAZING_BRAND.get());
                    } else {
                        --amplifier;
                    }

                    amplifier = Mth.clamp(amplifier, 0, 2);
                    MobEffectInstance blazingBrand = new MobEffectInstance(ModEffect.EFFECTBLAZING_BRAND.get(), 160, amplifier, true, true, true);
                    flag = target.addEffect(blazingBrand);
                }

                if (flag) {
                    ModifierCooldowns.getModifierCooldowns(user).addCooldown(TCModifiers.ignitium, 300);
                }
            }
        }
    }
}
