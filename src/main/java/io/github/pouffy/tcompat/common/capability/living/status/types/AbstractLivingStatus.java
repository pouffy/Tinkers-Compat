package io.github.pouffy.tcompat.common.capability.living.status.types;

import com.google.common.base.CaseFormat;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Map;

@Setter
@Getter
public abstract class AbstractLivingStatus {
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

    public void tick(LivingEntity entity, LivingEntity innerEntity) {}

    public void hurtEvent(LivingHurtEvent event, LivingEntity innerEntity) {}

    public static Map.Entry<String, AbstractLivingStatus> makeEntry(AbstractLivingStatus status) {
        return Map.entry(status.serializedName(), status);
    }

    public String getSetKey() {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "set_" + serializedName());
    }
}
