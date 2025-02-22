package com.pouffydev.tcompat.fluids;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.TCompatModule;
import com.pouffydev.tcompat.data.fluid.TComFluidTextureProv;
import com.pouffydev.tcompat.data.fluid.TComFluidTooltipProv;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import slimeknights.mantle.fluid.UnplaceableFluid;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.fluids.data.FluidBlockstateModelProvider;
import slimeknights.tconstruct.fluids.data.FluidBucketModelProvider;
import slimeknights.tconstruct.fluids.data.FluidTextureProvider;
import slimeknights.tconstruct.fluids.data.FluidTooltipProvider;

import static slimeknights.mantle.Mantle.commonResource;
import static slimeknights.tconstruct.fluids.block.BurningLiquidBlock.createBurning;

public final class TComFluids extends TCompatModule {

    public static final FlowingFluidObject<ForgeFlowingFluid> moltenKnightmetal = FLUIDS.register("molten_knightmetal").type(hot("molten_knightmetal").temperature(1215).lightLevel(12)).block(createBurning(MapColor.COLOR_LIGHT_GREEN, 12, 10, 6f)).bucket().commonTag().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenFiery = FLUIDS.register("molten_fiery").type(hot("molten_fiery").temperature(1645).lightLevel(12)).block(createBurning(MapColor.COLOR_LIGHT_GREEN, 12, 25, 4f)).bucket().commonTag().flowing();
    public static final FluidObject<UnplaceableFluid> fieryEssence = FLUIDS.register("fiery_essence").type(hot("fiery_essence").temperature(1500).lightLevel(0)).bucket().commonTag().unplacable();

    private static FluidType.Properties hot(String name) {
        return FluidType.Properties.create().density(2000).viscosity(10000).temperature(1000)
                .descriptionId(TConstruct.makeDescriptionId("fluid", name))
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                // from forge lava type
                .motionScale(0.0023333333333333335D)
                .canSwim(false).canDrown(false)
                .pathType(BlockPathTypes.LAVA).adjacentPathType(null);
    }

    @SubscribeEvent
    void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        boolean client = event.includeClient();
        generator.addProvider(client, new TComFluidTooltipProv(packOutput));
        generator.addProvider(client, new TComFluidTextureProv(packOutput));
        generator.addProvider(client, new FluidBucketModelProvider(packOutput, TCompat.MOD_ID));
        generator.addProvider(client, new FluidBlockstateModelProvider(packOutput, TCompat.MOD_ID));
    }

    @SubscribeEvent
    void commonSetup(final FMLCommonSetupEvent event) {
        // dispense buckets
        DispenseItemBehavior dispenseBucket = new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

            @Override
            public ItemStack execute(BlockSource source, ItemStack stack) {
                DispensibleContainerItem container = (DispensibleContainerItem)stack.getItem();
                BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                Level level = source.getLevel();
                if (container.emptyContents(null, level, blockpos, null, stack)) {
                    container.checkExtraContent(null, level, stack, blockpos);
                    return new ItemStack(Items.BUCKET);
                } else {
                    return this.defaultDispenseItemBehavior.dispense(source, stack);
                }
            }
        };

        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(moltenKnightmetal, dispenseBucket);
            DispenserBlock.registerBehavior(moltenFiery, dispenseBucket);
            DispenserBlock.registerBehavior(fieryEssence, dispenseBucket);
        });
    }

    @SubscribeEvent
    public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == TinkerFluids.tabFluids.get()) {
            acceptMolten(event, moltenKnightmetal);
            acceptMolten(event, moltenFiery);
            acceptMolten(event, fieryEssence);
        }
    }

    /** Accepts the given item if any of the listed ingots are present */
    private static void acceptCompat(CreativeModeTab.Output output, ItemLike item, String... ingots) {
        for (String ingot : ingots) {
            if (acceptIfTag(output, item, ItemTags.create(commonResource("ingots/" + ingot)))) {
                break;
            }
        }
    }

    /** Accepts the given item if the ingot named after the fluid is present */
    private static void acceptMolten(CreativeModeTab.Output output, FluidObject<?> fluid) {
        acceptCompat(output, fluid, withoutMolten(fluid));
    }

    /** Accepts the given item if the ingot named after the fluid or the passed ingot name is present */
    private static void acceptMolten(CreativeModeTab.Output output, FluidObject<?> fluid, String ingot) {
        acceptCompat(output, fluid, withoutMolten(fluid), ingot);
    }

    /** Length of the molten prefix */
    private static final int MOLTEN_LENGTH = "molten_".length();

    /** Removes the "molten_" prefix from the fluids ID */
    public static String withoutMolten(FluidObject<?> fluid) {
        return fluid.getId().getPath().substring(MOLTEN_LENGTH);
    }
}
