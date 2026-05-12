package io.github.pouffy.tcompat.compat.cataclysm.modifier;

import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Arrow_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Sandstorm_Projectile;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Shard_Entity;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
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

}
