package io.github.pouffy.tcompat.compat.ice_and_fire;

import com.github.alexthe666.iceandfire.item.DragonSteelOverrides;
import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.ice_and_fire.modifier.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.registration.FluidDeferredRegisterExtension;
import slimeknights.tconstruct.fluids.fluids.SlimeFluid;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static slimeknights.tconstruct.fluids.block.BurningLiquidBlock.createBurning;

public class IFInit extends CompatInitializer {
    public static final ModifierDeferredRegister IF_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final FluidDeferredRegisterExtension IF_F = new FluidDeferredRegisterExtension(TCompat.MOD_ID);
    public static final ItemDeferredRegister IF_I = new ItemDeferredRegister(TCompat.MOD_ID);

    //Traits
    public static final StaticModifier<FlamedModifier> flamed = IF_M.register("flamed", FlamedModifier::new);
    public static final StaticModifier<ScorchThornsModifier> scorchThorns = IF_M.register("scorch_thorns", ScorchThornsModifier::new);
    public static final StaticModifier<IcedModifier> iced = IF_M.register("iced", IcedModifier::new);
    public static final StaticModifier<FrostThornsModifier> frostThorns = IF_M.register("frost_thorns", FrostThornsModifier::new);
    public static final StaticModifier<LightningModifier> lightning = IF_M.register("lightning", LightningModifier::new);
    public static final StaticModifier<VoltThornsModifier> voltThorns = IF_M.register("volt_thorns", VoltThornsModifier::new);
    public static final StaticModifier<BreathlessModifier> breathless = IF_M.register("breathless", BreathlessModifier::new);

    public static final FlowingFluidObject<ForgeFlowingFluid> fireBlood = IF_F.registerSlime("fire_dragon_blood").type(slime("fire_dragon_blood").temperature(1100)).bucket().flowing(SlimeFluid.Source::new, SlimeFluid.Flowing::new);
    public static final FlowingFluidObject<ForgeFlowingFluid> iceBlood = IF_F.registerSlime("ice_dragon_blood").type(slime("ice_dragon_blood").temperature(1100)).bucket().flowing(SlimeFluid.Source::new, SlimeFluid.Flowing::new);
    public static final FlowingFluidObject<ForgeFlowingFluid> lightningBlood = IF_F.registerSlime("lightning_dragon_blood").type(slime("lightning_dragon_blood").temperature(1100)).bucket().flowing(SlimeFluid.Source::new, SlimeFluid.Flowing::new);
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenFireDragonsteel = IF_F.register("molten_fire_dragonsteel").type(hot("molten_fire_dragonsteel").temperature(1400).lightLevel(4)).block(createBurning(MapColor.FIRE, 4, 10, 6f)).bucket().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenIceDragonsteel = IF_F.register("molten_ice_dragonsteel").type(hot("molten_fire_dragonsteel").temperature(1400).lightLevel(4)).block(createBurning(MapColor.ICE, 4, 10, 6f)).bucket().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenLightningDragonsteel = IF_F.register("molten_lightning_dragonsteel").type(hot("molten_fire_dragonsteel").temperature(1400).lightLevel(4)).block(createBurning(MapColor.LAPIS, 4, 10, 6f)).bucket().flowing();

    public static final ItemObject<Item> fireDragonsteelNugget = itemForMod("iceandfire", "fire_dragonsteel_nugget", new Item.Properties(), IF_I);
    public static final ItemObject<Item> iceDragonsteelNugget = itemForMod("iceandfire", "ice_dragonsteel_nugget", new Item.Properties(), IF_I);
    public static final ItemObject<Item> lightningDragonsteelNugget = itemForMod("iceandfire", "lightning_dragonsteel_nugget", new Item.Properties(), IF_I);

    @SubscribeEvent
    void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(fireBlood, dispenseBucket);
            DispenserBlock.registerBehavior(iceBlood, dispenseBucket);
            DispenserBlock.registerBehavior(lightningBlood, dispenseBucket);
            DispenserBlock.registerBehavior(moltenFireDragonsteel, dispenseBucket);
            DispenserBlock.registerBehavior(moltenIceDragonsteel, dispenseBucket);
            DispenserBlock.registerBehavior(moltenLightningDragonsteel, dispenseBucket);
        });
    }

    public static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        output.accept(fireBlood);
        output.accept(iceBlood);
        output.accept(lightningBlood);
        acceptMolten(output, moltenFireDragonsteel);
        acceptMolten(output, moltenIceDragonsteel);
        acceptMolten(output, moltenLightningDragonsteel);
    }

    public static void addCommonTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        output.accept(fireDragonsteelNugget);
        output.accept(iceDragonsteelNugget);
        output.accept(lightningDragonsteelNugget);
    }

    public static void init(IEventBus eventBus) {
        IF_M.register(eventBus);
        IF_F.register(eventBus);
        IF_I.register(eventBus);
    }
}
