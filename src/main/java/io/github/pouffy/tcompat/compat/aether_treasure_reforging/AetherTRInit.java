package io.github.pouffy.tcompat.compat.aether_treasure_reforging;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.aether_redux.BlightedModifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static slimeknights.mantle.block.fluid.BurningLiquidBlock.createBurning;

public class AetherTRInit extends CompatInitializer {
    public static final ModifierDeferredRegister AETHERTR_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final FluidDeferredRegister AETHERTR_F = new FluidDeferredRegister(TCompat.MOD_ID);

    public static final StaticModifier<PhoenixTouchedModifier> phoenixTouched = AETHERTR_M.register("phoenix_touched", PhoenixTouchedModifier::new);

    public static final FlowingFluidObject<ForgeFlowingFluid> moltenPyral = AETHERTR_F.register("molten_pyral").type(hot("molten_pyral").temperature(1850).lightLevel(15)).block(createBurning(MapColor.COLOR_ORANGE, 15, 12, 7.5f)).bucket().commonTag().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenValkyrum = AETHERTR_F.register("molten_valkyrum").type(hot("molten_valkyrum").temperature(1575).lightLevel(7)).block(createBurning(MapColor.COLOR_LIGHT_GRAY, 7, 8, 6f)).bucket().commonTag().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenNeptune = AETHERTR_F.register("molten_neptune").type(hot("molten_neptune").temperature(1575).lightLevel(7)).block(createBurning(MapColor.COLOR_BLUE, 7, 8, 6f)).bucket().commonTag().flowing();

    @SubscribeEvent
    void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(moltenPyral, dispenseBucket);
            DispenserBlock.registerBehavior(moltenValkyrum, dispenseBucket);
            DispenserBlock.registerBehavior(moltenNeptune, dispenseBucket);
        });
    }

    public static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        acceptMolten(output, moltenPyral);
        acceptMolten(output, moltenValkyrum);
        acceptMolten(output, moltenNeptune);
    }

    public static void init(IEventBus eventBus) {
        AETHERTR_M.register(eventBus);
        AETHERTR_F.register(eventBus);
    }
}
