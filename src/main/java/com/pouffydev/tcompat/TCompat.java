package com.pouffydev.tcompat;

import com.pouffydev.tcompat.data.TComDataGen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(TCompat.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCompat {
    public static final String MOD_ID = "tcompat";
    public static final String NAME = "Tinker's Compatability";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public TCompat() {
        onCtor();
    }

    public static void onCtor() {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        IEventBus modEventBus = FMLJavaModLoadingContext.get()
                .getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        modEventBus.addListener(EventPriority.LOWEST, TComDataGen::gatherData);
    }

    public static ResourceLocation getResource(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
    public static ResourceLocation getResource(String namespace, String name) {
        return new ResourceLocation(namespace, name);
    }

    public static void sealTComClass(Object self, String base, String solution) {
        String name = self.getClass().getName();
        if (!name.startsWith("com.pouffydev.tcompat.")) {
            throw new IllegalStateException(base + " being extended from invalid package " + name + ". " + solution);
        }
    }
}
