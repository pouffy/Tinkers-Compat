package io.github.pouffy.tcompat.common.capability.projectile.ability.types;

import com.google.common.base.CaseFormat;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

import java.util.Map;

@Setter
@Getter
public abstract class AbstractProjectileAbility {
    private boolean isActive;

    abstract String serializedName();

    public CompoundTag serializeNBT() {return new CompoundTag();}
    public void deserializeNBT(CompoundTag tag) {}

    public CompoundTag serializeTag() {
        CompoundTag tag = this.serializeNBT();
        tag.putBoolean("active", this.isActive());
        return tag;
    }

    public void deserializeTag(CompoundTag tag) {
        this.deserializeNBT(tag);
        this.setActive(tag.getBoolean("active"));
    }

    public void tickArrow(AbstractArrow arrow, Projectile innerProjectile, boolean inGround, int inGroundTime) {}
    public void tickProjectile(Projectile projectile, Projectile innerProjectile) {}
    public void impactEvent(ProjectileImpactEvent event, Projectile projectile) {}

    public static Map.Entry<String, AbstractProjectileAbility> makeEntry(AbstractProjectileAbility ability) {
        return Map.entry(ability.serializedName(), ability);
    }

    public String getSetKey() {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "set_" + serializedName());
    }
}
