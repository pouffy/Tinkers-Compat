package io.github.pouffy.tcompat.compat.deep_aether;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
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

public class DeepAetherInit extends CompatInitializer {
    public static final ModifierDeferredRegister DEEP_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final FluidDeferredRegister DEEP_F = new FluidDeferredRegister(TCompat.MOD_ID);

    public static final StaticModifier<DullingModifier> dulling = DEEP_M.register("dulling", DullingModifier::new); // Skyjade
    public static final StaticModifier<GaleModifier> gale = DEEP_M.register("gale", GaleModifier::new); // Stormforged Steel

    public static final FlowingFluidObject<ForgeFlowingFluid> moltenSkyjade = DEEP_F.register("molten_skyjade").type(hot("molten_skyjade").temperature(1625).lightLevel(13)).block(createBurning(MapColor.COLOR_LIGHT_GREEN, 13, 10, 5f)).bucket().commonTag().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenStratus = DEEP_F.register("molten_stratus").type(hot("molten_stratus").temperature(1675).lightLevel(12)).block(createBurning(MapColor.COLOR_PINK, 12, 8, 6f)).bucket().commonTag().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenStormforgedSteel = DEEP_F.register("molten_stormforged_steel").type(hot("molten_stormforged_steel").temperature(1675).lightLevel(15)).block(createBurning(MapColor.COLOR_LIGHT_BLUE, 15, 10, 7f)).bucket().commonTag().flowing();

    @SubscribeEvent
    void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(moltenStratus, dispenseBucket);
            DispenserBlock.registerBehavior(moltenSkyjade, dispenseBucket);
        });
    }

    public static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        output.accept(moltenSkyjade);
        acceptMolten(output, moltenSkyjade);
        acceptMolten(output, moltenStratus);
    }

    public static void init(IEventBus eventBus) {
        DEEP_M.register(eventBus);
        DEEP_F.register(eventBus);
    }
}
