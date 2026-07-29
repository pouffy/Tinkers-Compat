package io.github.pouffy.tcompat;

import com.mojang.logging.LogUtils;
import io.github.pouffy.tcompat.common.CompatModule;
import io.github.pouffy.tcompat.common.cooldown.CooldownHandler;
import io.github.pouffy.tcompat.common.fluid.TCFluids;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.aether.item.DartBarrelMaterialStats;
import io.github.pouffy.tcompat.compat.aether.item.LipGuardMaterialStats;
import io.github.pouffy.tcompat.compat.create.GogglesPredicate;
import io.github.pouffy.tcompat.config.TCompatConfig;
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
import net.minecraft.util.RandomSource;
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
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.resource.PathPackResources;
import org.slf4j.Logger;
import slimeknights.mantle.client.model.NBTKeyModel;
import slimeknights.mantle.registration.deferred.SynchronizedDeferredRegister;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.tools.client.material.ThrownToolRenderer;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.nio.file.Path;
import java.util.Optional;

@Mod(TCompat.MOD_ID)
public class TCompat {
    public static final String MOD_ID = "tcompat";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static CooldownHandler COOLDOWN_HANDLER;
    protected static final SynchronizedDeferredRegister<CreativeModeTab> CREATIVE_TABS = SynchronizedDeferredRegister.create(Registries.CREATIVE_MODE_TAB, TCompat.MOD_ID);

    public static final RegistryObject<CreativeModeTab> GENERAL_TAB = CREATIVE_TABS.register("general", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tcompat.general"))
            .icon(() -> TCFluids.iceBlood.getBucket().getDefaultInstance())
            .displayItems(GlobalInit::collectGeneralItems)
            .build());

    public static final RegistryObject<CreativeModeTab> TOOLS_TAB = CREATIVE_TABS.register("tools", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tcompat.tools"))
            .withBackgroundLocation(getResource("textures/gui/container/creative_inventory/tab_item_search.png"))
            .icon(() -> GlobalInit.glaive.get().getRenderTool())
            .displayItems(GlobalInit::collectToolItems)
            .build());

    public static final RegistryObject<CreativeModeTab> PARTS_TAB = CREATIVE_TABS.register("parts", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tcompat.parts"))
            .withBackgroundLocation(getResource("textures/gui/container/creative_inventory/tab_item_search.png"))
            .icon(() -> {
                MaterialVariantId material;
                if (MaterialRegistry.isFullyLoaded()) {
                    material = ToolBuildHandler.RANDOM.getMaterial(HeadMaterialStats.ID, RandomSource.create());
                } else {
                    material = ToolBuildHandler.getRenderMaterial(0);
                }

                return GlobalInit.dartBarrel.get().withMaterialForDisplay(material);
            })
            .displayItems(GlobalInit::collectPartItems)
            .build());

    public TCompat(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        COOLDOWN_HANDLER = new CooldownHandler();
        modEventBus.register(Listeners.class);
        CompatHelper.init(modEventBus);
        modEventBus.register(new TCFluids());
        CompatModule.initRegisters(context);
        CREATIVE_TABS.register(modEventBus);
        modEventBus.addListener(EventPriority.LOWEST, TCDataGenerator::gatherData);
        modEventBus.addListener(this::setupAddonCompatibilityPack);
        TCompatConfig.register(context);
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

    public static final class Listeners {

        @SubscribeEvent
        public static void commonSetup(final FMLCommonSetupEvent event) {
            TCompatNetworking.register();
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> NBTKeyModel.registerExtraTexture(TConstruct.getResource("creative_slot"), "rune", getResource("gui/modifiers/rune")));

            MaterialRegistry.getInstance().registerStatType(DartBarrelMaterialStats.TYPE, MaterialRegistry.RANGED);
            MaterialRegistry.getInstance().registerStatType(LipGuardMaterialStats.TYPE, MaterialRegistry.RANGED);
        }

        @SubscribeEvent
        public static void imcEnqueue(final InterModEnqueueEvent event) {
            if (CompatHelper.isLoaded("create")) {
                GogglesPredicate.registerGoggles();
            }
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
