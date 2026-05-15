package io.github.pouffy.tcompat.common;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.compat.ad_astra.AdAstraMaterials;
import io.github.pouffy.tcompat.compat.aether.AetherMaterials;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxMaterials;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRMaterials;
import io.github.pouffy.tcompat.compat.betterend.BetterendMaterials;
import io.github.pouffy.tcompat.compat.betternether.BetternetherMaterials;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherMaterials;
import io.github.pouffy.tcompat.datagen.fluid.TCFluidTextureProv;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import slimeknights.mantle.fluid.texture.FluidTextureCameraProvider;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.tconstruct.fluids.block.BurningLiquidBlock;
import slimeknights.tconstruct.fluids.data.FluidBlockstateModelProvider;
import slimeknights.tconstruct.fluids.data.FluidBucketModelProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

public class TCFluids extends CompatModule {

    public static final FlowingFluidObject<ForgeFlowingFluid> moltenDesh, moltenCalorite, moltenOstrum; // Ad Astra
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenZanite, moltenGravitite, moltenLightnum, moltenDraculite; // Aether
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenVeridium, moltenRefinedSentrite; // Aether Redux
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenPyral, moltenValkyrum, moltenNeptune; // Aether Treasure Reforging
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenThallasium, moltenTerminite, moltenAeternium; // Betterend
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenCincinnasite, moltenNetherRuby; // Betternether
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenSkyjade, moltenStratus, moltenStormforgedSteel; // Deep Aether
    public static final FlowingFluidObject<ForgeFlowingFluid> fireBlood, iceBlood, lightningBlood, moltenFireDragonsteel, moltenIceDragonsteel, moltenLightningDragonsteel; // Ice and Fire
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenAncientMetal, moltenBlackSteel, moltenCursium, moltenWitherite, moltenIgnitium, moltenLacrima; // Cataclysm


    public static FlowingFluidObject<ForgeFlowingFluid> fireLilyMixture, frostLilyMixture, lightningLilyMixture, ambrosia;
    public static FlowingFluidObject<ForgeFlowingFluid> aloeVeraJuice, whitePuffballStew, alliumOddionSoup;
    public static FlowingFluidObject<ForgeFlowingFluid> umbrellaClusterJuice;
    public static FlowingFluidObject<ForgeFlowingFluid> wartSoup, agaveMedicine;

    static {
        moltenDesh = FLUIDS.registerMetal("molten_desh").type(hot("molten_desh").temperature(1275).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_ORANGE, 5, 12, 4.0F)).bucket().flowing();
        moltenOstrum = FLUIDS.registerMetal("molten_ostrum").type(hot("molten_ostrum").temperature(1475).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_PURPLE, 5, 12, 4.0F)).bucket().flowing();
        moltenCalorite = FLUIDS.registerMetal("molten_calorite").type(hot("molten_calorite").temperature(1675).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_RED, 5, 12, 4.0F)).bucket().flowing();

        moltenZanite = FLUIDS.registerGem("molten_zanite").type(hot("molten_zanite").temperature(1150).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_PURPLE, 5, 12, 4.0F)).bucket().flowing();
        moltenGravitite = FLUIDS.registerMetal("molten_gravitite").type(hot("molten_gravitite").density(-2000).temperature(1375).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_MAGENTA, 5, 12, 4.0F)).bucket().flowing();
        moltenLightnum = FLUIDS.registerMetal("molten_lightnum").type(hot("molten_lightnum").temperature(1475).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_BLUE, 5, 12, 4.0F)).bucket().flowing();
        moltenDraculite = FLUIDS.registerMetal("molten_draculite").type(hot("molten_draculite").temperature(1475).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_RED, 5, 12, 4.0F)).bucket().flowing();

        moltenVeridium = FLUIDS.registerMetal("molten_veridium").type(hot("molten_veridium").temperature(1425).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_BLUE, 5, 12, 4.0F)).bucket().flowing();
        moltenRefinedSentrite = FLUIDS.registerMetal("molten_refined_sentrite").type(hot("molten_refined_sentrite").temperature(1575).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.TERRACOTTA_GRAY, 5, 12, 4.0F)).bucket().flowing();

        moltenPyral = FLUIDS.registerMetal("molten_pyral").type(hot("molten_pyral").temperature(1525).lightLevel(9)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_ORANGE, 9, 18, 5.0F)).bucket().flowing();
        moltenValkyrum = FLUIDS.registerMetal("molten_valkyrum").type(hot("molten_valkyrum").temperature(1575).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_LIGHT_GRAY, 5, 12, 4.0F)).bucket().flowing();
        moltenNeptune = FLUIDS.registerMetal("molten_neptune").type(hot("molten_neptune").temperature(1150).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_BLUE, 5, 12, 4.0F)).bucket().flowing();

        moltenThallasium = FLUIDS.registerMetal("molten_thallasium").type(hot("molten_thallasium").temperature(1175).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_LIGHT_BLUE, 5, 12, 4.0F)).bucket().flowing();
        moltenTerminite = FLUIDS.registerMetal("molten_terminite").type(hot("molten_terminite").temperature(1230).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_LIGHT_GREEN, 5, 12, 4.0F)).bucket().flowing();
        moltenAeternium = FLUIDS.registerMetal("molten_aeternium").type(hot("molten_aeternium").temperature(1460).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_GRAY, 5, 12, 4.0F)).bucket().flowing();

        moltenCincinnasite = FLUIDS.registerMetal("molten_cincinnasite").type(hot("molten_cincinnasite").temperature(1675).lightLevel(8)).block(BurningLiquidBlock.createBurning(MapColor.TERRACOTTA_YELLOW, 8, 10, 5.0F)).bucket().flowing();
        moltenNetherRuby = FLUIDS.registerMetal("molten_nether_ruby").type(hot("molten_nether_ruby").temperature(1530).lightLevel(8)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_RED, 8, 10, 5.0F)).bucket().flowing();

        moltenSkyjade = FLUIDS.registerGem("molten_skyjade").type(hot("molten_skyjade").temperature(1625).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_LIGHT_BLUE, 5, 12, 4.0F)).bucket().flowing();
        moltenStratus = FLUIDS.registerMetal("molten_stratus").type(hot("molten_stratus").temperature(1675).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_LIGHT_GREEN, 5, 12, 4.0F)).bucket().flowing();
        moltenStormforgedSteel = FLUIDS.registerMetal("molten_stormforged_steel").type(hot("molten_stormforged_steel").temperature(1675).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_GRAY, 5, 12, 4.0F)).bucket().flowing();

        fireBlood = FLUIDS.register("fire_dragon_blood").type(cool("fire_dragon_blood").temperature(1100)).bucket().block(MapColor.GOLD, 0).flowing();
        iceBlood = FLUIDS.register("ice_dragon_blood").type(cool("ice_dragon_blood").temperature(1100)).bucket().block(MapColor.ICE, 0).flowing();
        lightningBlood = FLUIDS.register("lightning_dragon_blood").type(cool("lightning_dragon_blood").temperature(1100)).bucket().block(MapColor.COLOR_MAGENTA, 0).flowing();
        moltenFireDragonsteel = FLUIDS.registerMetal("molten_fire_dragonsteel").type(hot("molten_fire_dragonsteel").temperature(1400).lightLevel(4)).block(BurningLiquidBlock.createBurning(MapColor.FIRE, 4, 10, 6f)).bucket().flowing();
        moltenIceDragonsteel = FLUIDS.registerMetal("molten_ice_dragonsteel").type(hot("molten_ice_dragonsteel").temperature(1400).lightLevel(4)).block(BurningLiquidBlock.createBurning(MapColor.ICE, 4, 10, 6f)).bucket().flowing();
        moltenLightningDragonsteel = FLUIDS.registerMetal("molten_lightning_dragonsteel").type(hot("molten_lightning_dragonsteel").temperature(1400).lightLevel(4)).block(BurningLiquidBlock.createBurning(MapColor.LAPIS, 4, 10, 6f)).bucket().flowing();

        moltenAncientMetal = FLUIDS.registerGem("molten_ancient_metal").type(hot("molten_ancient_metal").temperature(1350).lightLevel(3)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_ORANGE, 3, 12, 5.0F)).bucket().flowing();
        moltenBlackSteel = FLUIDS.registerMetal("molten_black_steel").type(hot("molten_black_steel").temperature(1100).lightLevel(3)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_BLACK, 3, 12, 3.0F)).bucket().flowing();
        moltenCursium = FLUIDS.registerMetal("molten_cursium").type(hot("molten_cursium").temperature(1500).lightLevel(11)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_CYAN, 11, 16, 5.0F)).bucket().flowing();
        moltenWitherite = FLUIDS.registerMetal("molten_witherite").type(hot("molten_witherite").temperature(1500).lightLevel(9)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_RED, 11, 16, 5.0F)).bucket().flowing();
        moltenIgnitium = FLUIDS.registerMetal("molten_ignitium").type(hot("molten_ignitium").temperature(1750).lightLevel(14)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_ORANGE, 14, 19, 7.0F)).bucket().flowing();
        moltenLacrima = FLUIDS.registerGem("molten_lacrima").type(hot("molten_lacrima").temperature(1230).lightLevel(10)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_LIGHT_BLUE, 10, 8, 2.0F)).bucket().flowing();

        fireLilyMixture = FLUIDS.register("fire_lily_mixture").type(cool("fire_lily_mixture").temperature(400)).bucket().block(MapColor.COLOR_ORANGE, 0).commonTag().flowing();
        frostLilyMixture = FLUIDS.register("frost_lily_mixture").type(cool("frost_lily_mixture").temperature(400)).bucket().block(MapColor.COLOR_LIGHT_BLUE, 0).commonTag().flowing();
        lightningLilyMixture = FLUIDS.register("lightning_lily_mixture").type(cool("lightning_lily_mixture").temperature(400)).bucket().block(MapColor.COLOR_PURPLE, 0).commonTag().flowing();
        ambrosia = FLUIDS.register("ambrosia").type(cool("ambrosia").temperature(400)).bucket().block(MapColor.COLOR_PINK, 0).commonTag().flowing();
        aloeVeraJuice = FLUIDS.register("aloe_vera_juice").type(cool("aloe_vera_juice").temperature(400)).bucket().block(MapColor.TERRACOTTA_LIGHT_BLUE, 0).commonTag().flowing();
        whitePuffballStew = FLUIDS.register("white_puffball_stew").type(cool("white_puffball_stew").temperature(400)).bucket().block(MapColor.TERRACOTTA_WHITE, 0).commonTag().flowing();
        alliumOddionSoup = FLUIDS.register("allium_oddion_soup").type(cool("allium_oddion_soup").temperature(400)).bucket().block(MapColor.TERRACOTTA_ORANGE, 0).commonTag().flowing();
        umbrellaClusterJuice = FLUIDS.register("umbrella_cluster_juice").type(cool("umbrella_cluster_juice").temperature(400)).bucket().block(MapColor.COLOR_PURPLE, 0).commonTag().flowing();
        wartSoup = FLUIDS.register("wart_soup").type(cool("wart_soup").temperature(400)).bucket().block(MapColor.TERRACOTTA_RED, 0).commonTag().flowing();
        agaveMedicine = FLUIDS.register("agave_medicine").type(cool("agave_medicine").temperature(400)).bucket().block(MapColor.TERRACOTTA_PURPLE, 0).commonTag().flowing();
    }

    private static FluidType.Properties cool() {
        return FluidType.Properties.create().sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).motionScale(0.0023333333333333335).canExtinguish(true);
    }

    private static FluidType.Properties cool(String name) {
        return cool().descriptionId(TCompat.makeDescriptionId("fluid", name)).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY);
    }

    private static FluidType.Properties hot(String name) {
        return FluidType.Properties.create().density(2000).viscosity(10000).temperature(1000).descriptionId(TCompat.makeDescriptionId("fluid", name)).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA).motionScale(0.0023333333333333335).canSwim(false).canDrown(false).pathType(BlockPathTypes.LAVA).adjacentPathType((BlockPathTypes)null);
    }

    @SubscribeEvent
    void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        boolean client = event.includeClient();
        TCFluidTextureProv textureProvider = new TCFluidTextureProv(packOutput);
        generator.addProvider(client, textureProvider);
        generator.addProvider(client, new FluidTextureCameraProvider(packOutput, event.getExistingFileHelper(), textureProvider));
        generator.addProvider(client, new FluidBucketModelProvider(packOutput, TCompat.MOD_ID));
        generator.addProvider(client, new FluidBlockstateModelProvider(packOutput, TCompat.MOD_ID));
    }

    @SubscribeEvent
    void commonSetup(FMLCommonSetupEvent event) {
        DispenseItemBehavior dispenseBucket = new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

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
            DispenserBlock.registerBehavior(moltenDesh, dispenseBucket);
            DispenserBlock.registerBehavior(moltenCalorite, dispenseBucket);
            DispenserBlock.registerBehavior(moltenOstrum, dispenseBucket);
            DispenserBlock.registerBehavior(moltenZanite, dispenseBucket);
            DispenserBlock.registerBehavior(moltenGravitite, dispenseBucket);
            DispenserBlock.registerBehavior(moltenLightnum, dispenseBucket);
            DispenserBlock.registerBehavior(moltenDraculite, dispenseBucket);
            DispenserBlock.registerBehavior(moltenVeridium, dispenseBucket);
            DispenserBlock.registerBehavior(moltenRefinedSentrite, dispenseBucket);
            DispenserBlock.registerBehavior(moltenPyral, dispenseBucket);
            DispenserBlock.registerBehavior(moltenValkyrum, dispenseBucket);
            DispenserBlock.registerBehavior(moltenNeptune, dispenseBucket);
            DispenserBlock.registerBehavior(moltenThallasium, dispenseBucket);
            DispenserBlock.registerBehavior(moltenTerminite, dispenseBucket);
            DispenserBlock.registerBehavior(moltenAeternium, dispenseBucket);
            DispenserBlock.registerBehavior(moltenCincinnasite, dispenseBucket);
            DispenserBlock.registerBehavior(moltenNetherRuby, dispenseBucket);
            DispenserBlock.registerBehavior(moltenSkyjade, dispenseBucket);
            DispenserBlock.registerBehavior(moltenStratus, dispenseBucket);
            DispenserBlock.registerBehavior(moltenStormforgedSteel, dispenseBucket);
            DispenserBlock.registerBehavior(fireBlood, dispenseBucket);
            DispenserBlock.registerBehavior(iceBlood, dispenseBucket);
            DispenserBlock.registerBehavior(lightningBlood, dispenseBucket);
            DispenserBlock.registerBehavior(moltenFireDragonsteel, dispenseBucket);
            DispenserBlock.registerBehavior(moltenIceDragonsteel, dispenseBucket);
            DispenserBlock.registerBehavior(moltenLightningDragonsteel, dispenseBucket);
            DispenserBlock.registerBehavior(fireLilyMixture, dispenseBucket);
            DispenserBlock.registerBehavior(frostLilyMixture, dispenseBucket);
            DispenserBlock.registerBehavior(lightningLilyMixture, dispenseBucket);
            DispenserBlock.registerBehavior(ambrosia, dispenseBucket);
            DispenserBlock.registerBehavior(aloeVeraJuice, dispenseBucket);
            DispenserBlock.registerBehavior(whitePuffballStew, dispenseBucket);
            DispenserBlock.registerBehavior(alliumOddionSoup, dispenseBucket);
            DispenserBlock.registerBehavior(umbrellaClusterJuice, dispenseBucket);
            DispenserBlock.registerBehavior(wartSoup, dispenseBucket);
            DispenserBlock.registerBehavior(agaveMedicine, dispenseBucket);
        });
    }

    public static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        acceptCompat(output, moltenDesh, AdAstraMaterials.desh, TCTags.Items.DESH_INGOTS);
        acceptCompat(output, moltenCalorite, AdAstraMaterials.calorite, TCTags.Items.CALORITE_INGOTS);
        acceptCompat(output, moltenOstrum, AdAstraMaterials.ostrum, TCTags.Items.OSTRUM_INGOTS);
        acceptCompat(output, moltenZanite, AetherMaterials.zanite, TCTags.Items.ZANITE_GEMS);
        acceptCompat(output, moltenGravitite, AetherMaterials.gravitite, TCTags.Items.GRAVITITE_INGOTS);
        acceptCompat(output, moltenLightnum, AetherMaterials.lightnum, TCTags.Items.LIGHTNUM_INGOTS);
        acceptCompat(output, moltenDraculite, AetherMaterials.draculite, TCTags.Items.DRACULITE_INGOTS);
        acceptCompat(output, moltenVeridium, AetherReduxMaterials.veridium, TCTags.Items.VERIDIUM_INGOTS);
        acceptCompat(output, moltenRefinedSentrite, AetherReduxMaterials.refinedSentrite, TCTags.Items.REFINED_SENTRITE_INGOTS);
        acceptCompat(output, moltenPyral, AetherTRMaterials.pyral, TCTags.Items.PYRAL_INGOTS);
        acceptCompat(output, moltenValkyrum, AetherTRMaterials.valkyrum, TCTags.Items.VALKYRUM_INGOTS);
        acceptCompat(output, moltenNeptune, AetherTRMaterials.neptune, TCTags.Items.NEPTUNE_MESH);
        acceptCompat(output, moltenThallasium, BetterendMaterials.thallasium, TCTags.Items.THALLASIUM_INGOTS);
        acceptCompat(output, moltenTerminite, BetterendMaterials.terminite, TCTags.Items.TERMINITE_INGOTS);
        acceptCompat(output, moltenAeternium, BetterendMaterials.aeternium, TCTags.Items.AETERNIUM_INGOTS);
        acceptCompat(output, moltenCincinnasite, BetternetherMaterials.cincinnasite, TCTags.Items.CINCINNASITE_INGOTS);
        acceptCompat(output, moltenNetherRuby, BetternetherMaterials.netherRuby, TCTags.Items.NETHER_RUBY_GEMS);
        acceptCompat(output, moltenSkyjade, DeepAetherMaterials.skyjade, TCTags.Items.SKYJADE_GEMS);
        acceptCompat(output, moltenStratus, TCTags.Items.STRATUS_INGOTS);
        acceptCompat(output, moltenStormforgedSteel, DeepAetherMaterials.stormforgedSteel, TCTags.Items.STORMFORGED_STEEL_INGOTS);
        acceptCompat(output, fireBlood, TCTags.Items.FIRE_DRAGON_SCALES);
        acceptCompat(output, iceBlood, TCTags.Items.ICE_DRAGON_SCALES);
        acceptCompat(output, lightningBlood, TCTags.Items.LIGHTNING_DRAGON_SCALES);
        acceptCompat(output, moltenFireDragonsteel, TCTags.Items.FIRE_DRAGONSTEEL_INGOTS);
        acceptCompat(output, moltenIceDragonsteel, TCTags.Items.ICE_DRAGONSTEEL_INGOTS);
        acceptCompat(output, moltenLightningDragonsteel, TCTags.Items.LIGHTNING_DRAGONSTEEL_INGOTS);
        acceptCompat(output, fireLilyMixture, TCompat.getResource("iceandfire:fire_stew"));
        acceptCompat(output, frostLilyMixture, TCompat.getResource("iceandfire:frost_stew"));
        acceptCompat(output, lightningLilyMixture, TCompat.getResource("iceandfire:lightning_stew"));
        acceptCompat(output, ambrosia, TCompat.getResource("iceandfire:ambrosia"));
        acceptCompat(output, aloeVeraJuice, TCompat.getResource("biomeswevegone:aloe_vera_juice"));
        acceptCompat(output, whitePuffballStew, TCompat.getResource("biomeswevegone:white_puffball_stew"));
        acceptCompat(output, alliumOddionSoup, TCompat.getResource("biomeswevegone:allium_oddion_soup"));
        acceptCompat(output, umbrellaClusterJuice, TCompat.getResource("betterend:umbrella_cluster_juice"));
        acceptCompat(output, wartSoup, TCompat.getResource("betternether:stalagnate_bowl_wart"));
        acceptCompat(output, agaveMedicine, TCompat.getResource("betternether:agave_medicine"));
    }

    private static void acceptCompat(CreativeModeTab.Output output, ItemLike item, ResourceLocation owner) {
        acceptIfItem(output, item, owner);
    }

    private static void acceptCompat(CreativeModeTab.Output output, ItemLike item, TagKey<Item> tagKey) {
        acceptIfTag(output, item, tagKey);
    }

    private static void acceptCompat(CreativeModeTab.Output output, ItemLike item, TagKey<Item> tagKey, MaterialId material) {
        if (!acceptIfMaterial(output, item, material)) {
            acceptCompat(output, item, tagKey);
        }
    }

    private static void acceptCompat(CreativeModeTab.Output output, ItemLike item, MaterialId material, TagKey<Item> tagKey) {
        acceptCompat(output, item, tagKey, material);
    }
}
