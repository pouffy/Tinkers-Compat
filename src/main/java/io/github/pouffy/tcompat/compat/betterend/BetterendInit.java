package io.github.pouffy.tcompat.compat.betterend;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.PhoenixTouchedModifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static slimeknights.mantle.block.fluid.BurningLiquidBlock.createBurning;

//TODO: Fluid Textures & Recipes
public class BetterendInit extends CompatInitializer {
    public static final ModifierDeferredRegister BE_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final FluidDeferredRegister BE_F = new FluidDeferredRegister(TCompat.MOD_ID);

    public static final StaticModifier<VoidTouchedModifier> voidTouched = BE_M.register("void_touched", VoidTouchedModifier::new);

    public static final FlowingFluidObject<ForgeFlowingFluid> moltenThallasium = BE_F.register("molten_thallasium").type(hot("molten_thallasium").temperature(1175).lightLevel(8)).block(createBurning(MapColor.COLOR_LIGHT_BLUE, 8, 10, 5f)).bucket().commonTag().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenTerminite = BE_F.register("molten_terminite").type(hot("molten_terminite").temperature(1230).lightLevel(8)).block(createBurning(MapColor.COLOR_LIGHT_GREEN, 8, 10, 5f)).bucket().commonTag().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenAeternium = BE_F.register("molten_aeternium").type(hot("molten_aeternium").temperature(1460).lightLevel(10)).block(createBurning(MapColor.COLOR_GRAY, 8, 10, 5f)).bucket().commonTag().flowing();

    @SubscribeEvent
    void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(moltenThallasium, dispenseBucket);
            DispenserBlock.registerBehavior(moltenTerminite, dispenseBucket);
            DispenserBlock.registerBehavior(moltenAeternium, dispenseBucket);
        });
    }

    public static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        acceptMolten(output, moltenThallasium);
        acceptMolten(output, moltenTerminite);
        acceptMolten(output, moltenAeternium);
    }

    public static void init(IEventBus eventBus) {
        BE_M.register(eventBus);
        BE_F.register(eventBus);
    }
}
