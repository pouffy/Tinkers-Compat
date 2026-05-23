package io.github.pouffy.tcompat;

import com.mojang.logging.LogUtils;
import io.github.pouffy.tcompat.client.TComClientConfig;
import io.github.pouffy.tcompat.common.CompatModule;
import io.github.pouffy.tcompat.common.fluid.TCFluids;
import io.github.pouffy.tcompat.common.cooldown.CooldownHandler;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.datagen.TCDataGenerator;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import slimeknights.mantle.client.model.NBTKeyModel;
import slimeknights.mantle.registration.deferred.SynchronizedDeferredRegister;
import slimeknights.tconstruct.TConstruct;

@Mod(TCompat.MOD_ID)
public class TCompat {
    public static final String MOD_ID = "tcompat";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static CooldownHandler COOLDOWN_HANDLER;
    protected static final SynchronizedDeferredRegister<CreativeModeTab> CREATIVE_TABS = SynchronizedDeferredRegister.create(Registries.CREATIVE_MODE_TAB, TCompat.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tcompat.tcompat"))
            .withBackgroundLocation(getResource("textures/gui/container/creative_inventory/tab_item_search.png"))
            .withSearchBar(58)
            .icon(() -> GlobalInit.glaive.get().getRenderTool())
            .displayItems(GlobalInit::collectTabItems)
            .build());

    public TCompat(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        COOLDOWN_HANDLER = new CooldownHandler();
        modEventBus.addListener(this::commonSetup);
        CompatHelper.init(modEventBus);
        modEventBus.register(new TCFluids());
        CompatModule.initRegisters(context);
        CREATIVE_TABS.register(modEventBus);
        modEventBus.addListener(EventPriority.LOWEST, TCDataGenerator::gatherData);
        context.registerConfig(ModConfig.Type.CLIENT, TComClientConfig.SPEC, String.format("%s-client.toml", "tcompat"));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        TCompatNetworking.register();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> NBTKeyModel.registerExtraTexture(TConstruct.getResource("creative_slot"), "rune", getResource("gui/modifiers/rune")));
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
    }



    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
