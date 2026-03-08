package io.github.pouffy.tcompat.compat.aether;

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

public class AetherInit extends CompatInitializer {
    public static final ModifierDeferredRegister AETHER_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final FluidDeferredRegister AETHER_F = new FluidDeferredRegister(TCompat.MOD_ID);

    public static final StaticModifier<AcclimatizationModifier> acclimatization = AETHER_M.register("acclimatization", AcclimatizationModifier::new);
    public static final StaticModifier<AmbrogenModifier> ambrogen = AETHER_M.register("ambrogen", AmbrogenModifier::new);
    public static final StaticModifier<AscensionModifier> ascension = AETHER_M.register("ascension", AscensionModifier::new);

    public static final FlowingFluidObject<ForgeFlowingFluid> moltenZanite = AETHER_F.register("molten_zanite").type(hot("molten_zanite").temperature(1750).lightLevel(13)).block(createBurning(MapColor.COLOR_PURPLE, 13, 10, 6.5f)).bucket().commonTag().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenGravitite = AETHER_F.register("molten_gravitite").type(hot("molten_gravitite").density(-2000).temperature(1575).lightLevel(7)).block(createBurning(MapColor.COLOR_MAGENTA, 7, 8, 6f)).bucket().commonTag().flowing();

    @SubscribeEvent
    void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(moltenZanite, dispenseBucket);
            DispenserBlock.registerBehavior(moltenGravitite, dispenseBucket);
        });
    }

    public static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        acceptMolten(output, moltenZanite);
        acceptMolten(output, moltenGravitite);
    }

    public static void init(IEventBus eventBus) {
        AETHER_M.register(eventBus);
        AETHER_F.register(eventBus);
    }
}
