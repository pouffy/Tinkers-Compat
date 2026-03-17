package io.github.pouffy.tcompat.common.capability;

import io.github.pouffy.tcompat.TCompat;
import lombok.Getter;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import slimeknights.tconstruct.TConstruct;

import java.util.Locale;

@Mod.EventBusSubscriber(modid = TCompat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public enum TCSounds {
    VOID_TOUCHED_ACTIVATE("void_touched.activate"),
    VOID_TOUCHED_DEACTIVATE("void_touched.deactivate")
    ;

    @Getter
    private final SoundEvent sound;

    private static SoundEvent createEvent(String name) {
        return SoundEvent.createVariableRangeEvent(TCompat.getResource(name));
    }

    TCSounds(String name) {
        sound = createEvent(name);
    }

    TCSounds() {
        sound = createEvent(name().toLowerCase(Locale.US));
    }

    @SubscribeEvent
    public static void registerSounds(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.SOUND_EVENT) {
            for (TCSounds sound : values()) {
                ForgeRegistries.SOUND_EVENTS.register(sound.sound.getLocation(), sound.getSound());
            }
        }
    }
}
