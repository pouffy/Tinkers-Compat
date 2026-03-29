package io.github.pouffy.tcompat.compat.ice_and_fire;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.ice_and_fire.item.ModifiableGlaiveItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

import java.util.function.Consumer;

import static slimeknights.tconstruct.fluids.block.BurningLiquidBlock.createBurning;

public class IFInit extends CompatInitializer {
    public static final ModifierDeferredRegister IF_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final FluidDeferredRegisterExtension IF_F = new FluidDeferredRegisterExtension(TCompat.MOD_ID);
    public static final ItemDeferredRegister IF_I = new ItemDeferredRegister(TCompat.MOD_ID);

    public static final FlowingFluidObject<ForgeFlowingFluid> fireBlood = IF_F.registerSlime("fire_dragon_blood").type(slime("fire_dragon_blood").temperature(1100)).bucket().flowing(SlimeFluid.Source::new, SlimeFluid.Flowing::new);
    public static final FlowingFluidObject<ForgeFlowingFluid> iceBlood = IF_F.registerSlime("ice_dragon_blood").type(slime("ice_dragon_blood").temperature(1100)).bucket().flowing(SlimeFluid.Source::new, SlimeFluid.Flowing::new);
    public static final FlowingFluidObject<ForgeFlowingFluid> lightningBlood = IF_F.registerSlime("lightning_dragon_blood").type(slime("lightning_dragon_blood").temperature(1100)).bucket().flowing(SlimeFluid.Source::new, SlimeFluid.Flowing::new);
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenFireDragonsteel = IF_F.register("molten_fire_dragonsteel").type(hot("molten_fire_dragonsteel").temperature(1400).lightLevel(4)).block(createBurning(MapColor.FIRE, 4, 10, 6f)).bucket().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenIceDragonsteel = IF_F.register("molten_ice_dragonsteel").type(hot("molten_fire_dragonsteel").temperature(1400).lightLevel(4)).block(createBurning(MapColor.ICE, 4, 10, 6f)).bucket().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenLightningDragonsteel = IF_F.register("molten_lightning_dragonsteel").type(hot("molten_fire_dragonsteel").temperature(1400).lightLevel(4)).block(createBurning(MapColor.LAPIS, 4, 10, 6f)).bucket().flowing();

    public static final ItemObject<ModifiableGlaiveItem> glaive = IF_I.register("glaive", () -> new ModifiableGlaiveItem(new Item.Properties().stacksTo(1), IFToolDefinitions.glaive));

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

    public static void addToolTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output tab) {
        Consumer<ItemStack> output = tab::accept;
        ToolBuildHandler.addVariants(output, glaive.get(), "");
    }

    public static void init(IEventBus eventBus) {
        IF_M.register(eventBus);
        IF_F.register(eventBus);
        IF_I.register(eventBus);
    }
}
