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

public class BetterendInit extends CompatInitializer {
    public static final ModifierDeferredRegister BE_M = ModifierDeferredRegister.create(TCompat.MOD_ID);

    public static final StaticModifier<VoidTouchedModifier> voidTouched = BE_M.register("void_touched", VoidTouchedModifier::new);

    public static void init(IEventBus eventBus) {
        BE_M.register(eventBus);
    }
}
