package io.github.pouffy.tcompat.common.capability.projectile.ability.types;

import com.google.common.base.CaseFormat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

import java.util.Map;

public interface IProjectileAbility {

    String serializedName();
    boolean isActive();
    void setActive(boolean active);

    default CompoundTag serializeNBT() {return new CompoundTag();}
    default void deserializeNBT(CompoundTag tag) {}

    default CompoundTag serializeTag() {
        CompoundTag tag = this.serializeNBT();
        tag.putBoolean("active", this.isActive());
        return tag;
    }

    default void deserializeTag(CompoundTag tag) {
        this.deserializeNBT(tag);
        this.setActive(tag.getBoolean("active"));
    }

    default void tickArrow(AbstractArrow arrow, Projectile innerProjectile, boolean inGround, int inGroundTime) {}
    default void tickProjectile(Projectile projectile, Projectile innerProjectile) {}
    default void impactEvent(ProjectileImpactEvent event, Projectile projectile) {}

    static Map.Entry<String, IProjectileAbility> makeEntry(IProjectileAbility ability) {
        return Map.entry(ability.serializedName(), ability);
    }

    default String getSetKey() {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "set_" + serializedName());
    }
}
