package io.github.pouffy.tcompat.compat.ad_astra;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import net.minecraft.resources.ResourceLocation;
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

public class AdAstraInit extends CompatInitializer {
    public static final ModifierDeferredRegister ASTRA_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final FluidDeferredRegister ASTRA_F = new FluidDeferredRegister(TCompat.MOD_ID);

    public static final StaticModifier<OxygenatedModifier> oxygenated = ASTRA_M.register("oxygenated", OxygenatedModifier::new);

    //Molten Fluids
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenDesh = ASTRA_F.register("molten_desh").type(hot("molten_desh").temperature(1375).lightLevel(13)).block(createBurning(MapColor.COLOR_ORANGE, 13, 10, 6.5f)).bucket().commonTag().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenCalorite = ASTRA_F.register("molten_calorite").type(hot("molten_calorite").temperature(1575).lightLevel(7)).block(createBurning(MapColor.COLOR_RED, 13, 12, 7f)).bucket().commonTag().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenOstrum = ASTRA_F.register("molten_ostrum").type(hot("molten_ostrum").temperature(1785).lightLevel(7)).block(createBurning(MapColor.COLOR_PURPLE, 13, 15, 7.5f)).bucket().commonTag().flowing();

    @SubscribeEvent
    void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(moltenDesh, dispenseBucket);
            DispenserBlock.registerBehavior(moltenCalorite, dispenseBucket);
            DispenserBlock.registerBehavior(moltenOstrum, dispenseBucket);
        });
    }

    public static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        acceptMolten(output, moltenDesh);
        acceptMolten(output, moltenCalorite);
        acceptMolten(output, moltenOstrum);
    }

    public static void init(IEventBus eventBus) {
        ASTRA_M.register(eventBus);
        ASTRA_F.register(eventBus);
    }

    public static ResourceLocation moltenDesh() {
        return TCompat.getResource("molten_desh");
    }
    public static ResourceLocation moltenCalorite() {
        return TCompat.getResource("molten_calorite");
    }
    public static ResourceLocation moltenOstrum() {
        return TCompat.getResource("molten_ostrum");
    }
}
