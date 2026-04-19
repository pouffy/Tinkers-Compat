package io.github.pouffy.tcompat;

import com.mojang.logging.LogUtils;
import io.github.pouffy.tcompat.common.CompatModule;
import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.datagen.TCDataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.IEventBus;
import org.slf4j.Logger;
import slimeknights.tconstruct.common.TinkerModule;
import slimeknights.tconstruct.fluids.TinkerFluids;

@Mod(TCompat.MOD_ID)
public class TCompat {
    public static final String MOD_ID = "tcompat";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TCompat(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);
        CompatHelper.init(modEventBus);
        modEventBus.register(new TCFluids());
        CompatModule.initRegisters(context);

        modEventBus.addListener(EventPriority.LOWEST, TCDataGenerator::gatherData);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        TCompatNetworking.register();
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    public static ResourceLocation getResource(String name) {
        if (name.contains(":")) {
            return ResourceLocation.tryParse(name);
        }
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static ResourceLocation getResource(String namespace, String name) {
        return ResourceLocation.fromNamespaceAndPath(namespace, name);
    }

    public static String makeDescriptionId(String type, String name) {
        return type + "." + MOD_ID + "." + name;
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
        }
    }
}
