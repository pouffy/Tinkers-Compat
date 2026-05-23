package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.common.cooldown.IModifierCooldownHolder;
import io.github.pouffy.tcompat.common.cooldown.ModifierCooldowns;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements IModifierCooldownHolder {
    @Unique
    ModifierCooldowns tcompat$modifierCooldowns = null;

    public LivingEntityMixin() {
    }

    public ModifierCooldowns tcompat$getCooldowns() {
        if (this.tcompat$modifierCooldowns == null) {
            this.tcompat$modifierCooldowns = new ModifierCooldowns();
        }

        return this.tcompat$modifierCooldowns;
    }

    @Inject(method = {"addAdditionalSaveData"}, at = {@At("TAIL")})
    public void tcompat$saveDataAttachment(CompoundTag compoundTag, CallbackInfo ci) {
        if (this.tcompat$modifierCooldowns != null) {
            compoundTag.put("tcompat:modifier_cooldowns", this.tcompat$modifierCooldowns.saveNBTData());
        }
    }

    @Inject(method = {"readAdditionalSaveData"}, at = {@At("TAIL")})
    public void tcompat$readDataAttachment(CompoundTag compoundTag, CallbackInfo ci) {
        if (compoundTag.contains("tcompat:modifier_cooldowns")) {
            this.tcompat$getCooldowns();
            ListTag listTag = (ListTag) compoundTag.get("tcompat:modifier_cooldowns");
            if (listTag != null && !listTag.isEmpty()) {
                this.tcompat$modifierCooldowns.loadNBTData(listTag);
            }
        }
    }
}
