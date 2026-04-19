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

    public static final StaticModifier<OxygenatedModifier> oxygenated = ASTRA_M.register("oxygenated", OxygenatedModifier::new);

    public static void init(IEventBus eventBus) {
        ASTRA_M.register(eventBus);
    }
}
