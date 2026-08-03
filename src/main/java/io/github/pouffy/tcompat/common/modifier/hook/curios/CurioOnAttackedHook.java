package io.github.pouffy.tcompat.common.modifier.hook.curios;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public interface CurioOnAttackedHook {

    void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, String slotId, int slotIndex, DamageSource source, float amount, boolean isDirectDamage);

    default boolean isDamageBlocked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, String slotId, int slotIndex, DamageSource source, float amount, boolean isDirectDamage) {
        return false;
    }

    static boolean isDirectDamage(DamageSource source) {
        return source.getEntity() != null && !source.isIndirect() && !source.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS);
    }

    record AllMerger(Collection<CurioOnAttackedHook> modules) implements CurioOnAttackedHook {
        public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, String slotId, int slotIndex, DamageSource source, float amount, boolean isDirectDamage) {
            for(CurioOnAttackedHook module : this.modules) {
                module.onAttacked(tool, modifier, context, slotId, slotIndex, source, amount, isDirectDamage);
            }
        }

        public boolean isDamageBlocked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, String slotId, int slotIndex, DamageSource source, float amount, boolean isDirectDamage) {
            for(CurioOnAttackedHook module : this.modules) {
                if (module.isDamageBlocked(tool, modifier, context, slotId, slotIndex, source, amount, isDirectDamage)) {
                    return true;
                }
            }
            return false;
        }
    }
}
