package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Mixin(VibrationSystem.Listener.class)
public class VibrationListenerMixin {

    @Inject(
            method = {"handleGameEvent"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void handleGameEvent(ServerLevel level, GameEvent event, GameEvent.Context context, Vec3 vec3, CallbackInfoReturnable<Boolean> cir) {
        if (context.sourceEntity() instanceof LivingEntity entity) {
            for (ToolStack stack : EquipmentHelper.getModifiableStacks(entity).stream().map(ToolStack::from).toList()) {
                List<ModifierEntry> validList = new ArrayList<>();
                for (ModifierEntry entry : stack.getModifierList()) {
                    if (entry.getModifier().getHooks().hasHook(GlobalInit.VIBRATION_DAMPENING)) {
                        validList.add(entry);
                    }
                }
                List<ModifierEntry> list = validList.stream().sorted(Comparator.comparingInt(entry -> -entry.getModifier().getPriority())).toList();
                boolean isEffective = !list.isEmpty() && list.stream().findFirst().map(entry -> entry.getHook(GlobalInit.VIBRATION_DAMPENING).isEffective(stack, entry, level, event, context, vec3)).orElse(false);
                if (isEffective) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
