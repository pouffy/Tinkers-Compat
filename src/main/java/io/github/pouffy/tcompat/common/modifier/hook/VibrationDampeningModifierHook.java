package io.github.pouffy.tcompat.common.modifier.hook;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public interface VibrationDampeningModifierHook {

    boolean isEffective(IToolStackView tool, ModifierEntry modifierEntry, Level level, GameEvent event, GameEvent.Context context, Vec3 pos);

    record AllMerger(Collection<VibrationDampeningModifierHook> modules) implements VibrationDampeningModifierHook {

        @Override
        public boolean isEffective(IToolStackView tool, ModifierEntry modifierEntry, Level level, GameEvent event, GameEvent.Context context, Vec3 pos) {
            boolean isEffective = false;
            for(VibrationDampeningModifierHook module : this.modules) {
                if (module.isEffective(tool, modifierEntry, level, event, context, pos)) {
                    isEffective = true; break;
                }
            }
            return isEffective;
        }
    }
}
