package io.github.pouffy.tcompat.compat.betternether;

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

@SuppressWarnings("unused")
public class BetternetherInit extends CompatInitializer {
    public static final ModifierDeferredRegister BN_M = ModifierDeferredRegister.create(TCompat.MOD_ID);

    public static final StaticModifier<ObsidianBreakerModifier> obsidianBreaker = BN_M.register("obsidian_breaker", ObsidianBreakerModifier::new);
    public static final StaticModifier<RubysFireModifier> rubysFire = BN_M.register("rubys_fire", RubysFireModifier::new);
    public static final StaticModifier<CinderspineModifier> cinderspine = BN_M.register("cinderspine", CinderspineModifier::new);

    public static void init(IEventBus eventBus) {
        BN_M.register(eventBus);
    }
}
