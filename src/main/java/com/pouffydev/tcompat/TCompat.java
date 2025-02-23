package com.pouffydev.tcompat;

import com.pouffydev.tcompat.data.TComDataGen;
import com.pouffydev.tcompat.data.TComTags;
import com.pouffydev.tcompat.fluids.TComFluids;
import com.pouffydev.tcompat.modifier.TComModifiers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.utils.Util;

@Mod(TCompat.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCompat {
    public static final String MOD_ID = "tcompat";
    public static final String NAME = "Tinkers' Compatability";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public TCompat() {
        onCtor();
    }

    public static void onCtor() {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        IEventBus modEventBus = FMLJavaModLoadingContext.get()
                .getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        modEventBus.register(new TComModifiers());
        modEventBus.register(new TComFluids());
        TCompatModule.initRegisters();

        TComTags.init();


        modEventBus.addListener(EventPriority.LOWEST, TComDataGen::gatherData);
    }

    public static ResourceLocation getResource(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
    public static ResourceLocation getResource(String namespace, String name) {
        return new ResourceLocation(namespace, name);
    }

    public static String makeDescriptionId(String type, String name) {
        return type + "." + MOD_ID + "." + name;
    }

    public static String makeTranslationKey(String base, String name) {
        return Util.makeTranslationKey(base, getResource(name));
    }

    public static void sealTComClass(Object self, String base, String solution) {
        String name = self.getClass().getName();
        if (!name.startsWith("com.pouffydev.tcompat.")) {
            throw new IllegalStateException(base + " being extended from invalid package " + name + ". " + solution);
        }
    }

    public static boolean isClassFound(String className) {
        try {
            Class.forName(className, false, Thread.currentThread().getContextClassLoader());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
