package io.github.pouffy.tcompat.compat.deeperdarker;

import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraftforge.event.entity.living.LivingEvent;

public class DarkerHandler {

    public static void heartbeat(LivingEvent.LivingTickEvent event) {
        if (EquipmentHelper.hasModifier(event.getEntity(), TCModifiers.heartbeat) && RandomSource.create().nextFloat() < 0.006F) {
            event.getEntity().level().playSound(null, event.getEntity().blockPosition(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.AMBIENT, 1.7F, 1.0F);
        }
    }

    public static class LoadedOnly {

    }
}
