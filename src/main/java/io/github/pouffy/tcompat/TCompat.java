package io.github.pouffy.tcompat;

import com.aetherteam.aether.Aether;
import com.mojang.logging.LogUtils;
import io.github.pouffy.tcompat.client.TComClientConfig;
import io.github.pouffy.tcompat.common.CompatModule;
import io.github.pouffy.tcompat.common.cooldown.CooldownHandler;
import io.github.pouffy.tcompat.common.fluid.TCFluids;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.datagen.TCDataGenerator;
import net.minecraft.SharedConstants;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.resource.PathPackResources;
import org.slf4j.Logger;
import slimeknights.mantle.client.model.NBTKeyModel;
import slimeknights.mantle.registration.deferred.SynchronizedDeferredRegister;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.tools.client.material.ThrownToolRenderer;

import java.nio.file.Path;
import java.util.Optional;

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
        modEventBus.addListener(this::setupAddonCompatibilityPack);
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


    @SuppressWarnings("resource")
    private void setupAddonCompatibilityPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(TCompat.MOD_ID).getFile().findResource("packs/addon_compat");
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(TCompat.MOD_ID).getFile().getFileName() + ":" + resourcePath, true, resourcePath);
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.tcompat.addon_compat.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            event.addRepositorySource((source) ->
                    source.accept(Pack.create(
                            "builtin/tcompat_addon_compat",
                            Component.translatable("pack.tcompat.addon_compat.title"),
                            false,
                            (string) -> pack,
                            new Pack.Info(metadata.getDescription(), metadata.getPackFormat(PackType.SERVER_DATA), metadata.getPackFormat(PackType.CLIENT_RESOURCES), FeatureFlagSet.of(), pack.isHidden()),
                            PackType.CLIENT_RESOURCES,
                            Pack.Position.TOP,
                            false,
                            PackSource.BUILT_IN)
                    )
            );
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemProperties.registerGeneric(getResource("excited"), (stack, world, e, id) -> Optional.of(stack.getOrCreateTag().getBoolean("excited")).orElse(false) ? 1 : 0);
        }

        @SubscribeEvent
        static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(GlobalInit.modifiableDart.get(), ThrownToolRenderer::new);
        }
    }
}
