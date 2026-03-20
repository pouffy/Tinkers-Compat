package io.github.pouffy.tcompat.compat.aether;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.aether.modifier.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static slimeknights.mantle.block.fluid.BurningLiquidBlock.createBurning;

public class AetherInit extends CompatInitializer {
    public static final ModifierDeferredRegister AETHER_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final FluidDeferredRegister AETHER_F = new FluidDeferredRegister(TCompat.MOD_ID);
    public static final ItemDeferredRegister AETHER_I = new ItemDeferredRegister(TCompat.MOD_ID);

    //Traits
    public static final StaticModifier<AcclimatizationModifier> acclimatization = AETHER_M.register("acclimatization", AcclimatizationModifier::new); // Zanite
    public static final StaticModifier<AmbrogenModifier> ambrogen = AETHER_M.register("ambrogen", AmbrogenModifier::new); // Holystone
    public static final StaticModifier<AscensionModifier> ascension = AETHER_M.register("ascension", AscensionModifier::new); // Gravitite
    public static final StaticModifier<ThunderstruckModifier> thunderstruck = AETHER_M.register("thunderstruck", ThunderstruckModifier::new); // Lightnum
    public static final StaticModifier<BloodsuckingModifier> bloodsucking = AETHER_M.register("bloodsucking", BloodsuckingModifier::new); // Draculite

    //Craftable Modifiers
    public static final StaticModifier<AutochantModifier> autochant = AETHER_M.register("autochant", AutochantModifier::new);
    public static final StaticModifier<AutofreezeModifier> autofreeze = AETHER_M.register("autofreeze", AutofreezeModifier::new);

    //Molten Fluids
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenZanite = AETHER_F.register("molten_zanite").type(hot("molten_zanite").temperature(1250).lightLevel(13)).block(createBurning(MapColor.COLOR_PURPLE, 13, 10, 6.5f)).bucket().commonTag().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenGravitite = AETHER_F.register("molten_gravitite").type(hot("molten_gravitite").density(-2000).temperature(1575).lightLevel(7)).block(createBurning(MapColor.COLOR_MAGENTA, 7, 8, 6f)).bucket().commonTag().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenLightnum = AETHER_F.register("molten_lightnum").type(hot("molten_lightnum").temperature(1675).lightLevel(12)).block(createBurning(MapColor.COLOR_BLUE, 12, 8, 6f)).bucket().commonTag().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenDraculite = AETHER_F.register("molten_draculite").type(hot("molten_draculite").temperature(1675).lightLevel(15)).block(createBurning(MapColor.COLOR_RED, 15, 10, 7f)).bucket().commonTag().flowing();

    public static final ItemObject<Item> lightnumIngot = itemForMod("aether", "lightnum_ingot", new Item.Properties(), AETHER_I);
    public static final ItemObject<Item> draculiteIngot = itemForMod("aether", "draculite_ingot", new Item.Properties(), AETHER_I);

    @SubscribeEvent
    void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(moltenZanite, dispenseBucket);
            DispenserBlock.registerBehavior(moltenGravitite, dispenseBucket);
            DispenserBlock.registerBehavior(moltenLightnum, dispenseBucket);
            DispenserBlock.registerBehavior(moltenDraculite, dispenseBucket);
        });
    }

    public static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        acceptMolten(output, moltenZanite);
        acceptMolten(output, moltenGravitite);
        acceptMolten(output, moltenLightnum);
        acceptMolten(output, moltenDraculite);
    }

    public static void addCommonTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        output.accept(lightnumIngot);
        output.accept(draculiteIngot);
    }

    public static void init(IEventBus eventBus) {
        AETHER_M.register(eventBus);
        AETHER_F.register(eventBus);
        AETHER_I.register(eventBus);
    }
}
