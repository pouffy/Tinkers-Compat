package io.github.pouffy.tcompat.common;

import com.mojang.datafixers.util.Either;
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
import org.checkerframework.checker.units.qual.A;
import slimeknights.mantle.fluid.texture.FluidTextureCameraProvider;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.block.BurningLiquidBlock;
import slimeknights.tconstruct.fluids.data.FluidBlockstateModelProvider;
import slimeknights.tconstruct.fluids.data.FluidBucketModelProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import java.util.HashMap;
import java.util.Map;

import static io.github.pouffy.tcompat.datagen.lang.TCLangProv.toEngStr;

public class TCFluids extends CompatModule {

    public static final Map<ResourceLocation, RegisteredFluid<?>> registeredFluids = new HashMap<>();

    public static final FlowingFluidObject<ForgeFlowingFluid> moltenDesh, moltenCalorite, moltenOstrum; // Ad Astra
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenZanite, moltenGravitite, moltenLightnum, moltenDraculite; // Aether
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenVeridium, moltenRefinedSentrite; // Aether Redux
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenPyral, moltenValkyrum, moltenNeptune; // Aether Treasure Reforging
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenThallasium, moltenTerminite, moltenAeternium; // Betterend
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenCincinnasite, moltenNetherRuby; // Betternether
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenSkyjade, moltenStratus, moltenStormforgedSteel; // Deep Aether
    public static final FlowingFluidObject<ForgeFlowingFluid> fireBlood, iceBlood, lightningBlood, moltenFireDragonsteel, moltenIceDragonsteel, moltenLightningDragonsteel; // Ice and Fire
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenAncientMetal, moltenBlackSteel, moltenCursium, moltenWitherite, moltenIgnitium, moltenLacrima; // Cataclysm
    public static final FlowingFluidObject<ForgeFlowingFluid> runicSap, cursedSap, moltenSoulStainedSteel, moltenMalignantPewter, moltenHallowedGold, moltenBlazingQuartz; // Malum


    public static FlowingFluidObject<ForgeFlowingFluid> fireLilyMixture, frostLilyMixture, lightningLilyMixture, ambrosia;
    public static FlowingFluidObject<ForgeFlowingFluid> aloeVeraJuice, whitePuffballStew, alliumOddionSoup;
    public static FlowingFluidObject<ForgeFlowingFluid> umbrellaClusterJuice;
    public static FlowingFluidObject<ForgeFlowingFluid> wartSoup, agaveMedicine;

    static {
        moltenDesh = register(TCTags.Items.DESH_INGOTS, FLUIDS.registerMetal("molten_desh").type(hot("molten_desh").temperature(1275).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_ORANGE, 5, 12, 4.0F)).bucket().flowing());
        moltenOstrum = register(TCTags.Items.OSTRUM_INGOTS, FLUIDS.registerMetal("molten_ostrum").type(hot("molten_ostrum").temperature(1475).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_PURPLE, 5, 12, 4.0F)).bucket().flowing());
        moltenCalorite = register(TCTags.Items.CALORITE_INGOTS, FLUIDS.registerMetal("molten_calorite").type(hot("molten_calorite").temperature(1675).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_RED, 5, 12, 4.0F)).bucket().flowing());

        moltenZanite = register(TCTags.Items.ZANITE_GEMS, FLUIDS.registerGem("molten_zanite").type(hot("molten_zanite").temperature(1150).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_PURPLE, 5, 12, 4.0F)).bucket().flowing());
        moltenGravitite = register(TCTags.Items.GRAVITITE_INGOTS, FLUIDS.registerMetal("molten_gravitite").type(hot("molten_gravitite").density(-2000).temperature(1375).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_MAGENTA, 5, 12, 4.0F)).bucket().flowing());
        moltenLightnum = register(TCTags.Items.LIGHTNUM_INGOTS, FLUIDS.registerMetal("molten_lightnum").type(hot("molten_lightnum").temperature(1475).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_BLUE, 5, 12, 4.0F)).bucket().flowing());
        moltenDraculite = register(TCTags.Items.DRACULITE_INGOTS, FLUIDS.registerMetal("molten_draculite").type(hot("molten_draculite").temperature(1475).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_RED, 5, 12, 4.0F)).bucket().flowing());

        moltenVeridium = register(TCTags.Items.VERIDIUM_INGOTS, FLUIDS.registerMetal("molten_veridium").type(hot("molten_veridium").temperature(1425).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_BLUE, 5, 12, 4.0F)).bucket().flowing());
        moltenRefinedSentrite = register(TCTags.Items.REFINED_SENTRITE_INGOTS, FLUIDS.registerMetal("molten_refined_sentrite").type(hot("molten_refined_sentrite").temperature(1575).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.TERRACOTTA_GRAY, 5, 12, 4.0F)).bucket().flowing());

        moltenPyral = register(TCTags.Items.PYRAL_INGOTS, FLUIDS.registerMetal("molten_pyral").type(hot("molten_pyral").temperature(1525).lightLevel(9)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_ORANGE, 9, 18, 5.0F)).bucket().flowing());
        moltenValkyrum = register(TCTags.Items.VALKYRUM_INGOTS, FLUIDS.registerMetal("molten_valkyrum").type(hot("molten_valkyrum").temperature(1575).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_LIGHT_GRAY, 5, 12, 4.0F)).bucket().flowing());
        moltenNeptune = register(TCTags.Items.NEPTUNE_MESH, FLUIDS.registerMetal("molten_neptune").type(hot("molten_neptune").temperature(1150).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_BLUE, 5, 12, 4.0F)).bucket().flowing());

        moltenThallasium = register(TCTags.Items.THALLASIUM_INGOTS, FLUIDS.registerMetal("molten_thallasium").type(hot("molten_thallasium").temperature(1175).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_LIGHT_BLUE, 5, 12, 4.0F)).bucket().flowing());
        moltenTerminite = register(TCTags.Items.TERMINITE_INGOTS, FLUIDS.registerMetal("molten_terminite").type(hot("molten_terminite").temperature(1230).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_LIGHT_GREEN, 5, 12, 4.0F)).bucket().flowing());
        moltenAeternium = register(TCTags.Items.AETERNIUM_INGOTS, FLUIDS.registerMetal("molten_aeternium").type(hot("molten_aeternium").temperature(1460).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_GRAY, 5, 12, 4.0F)).bucket().flowing());

        moltenCincinnasite = register(TCTags.Items.CINCINNASITE_INGOTS, FLUIDS.registerMetal("molten_cincinnasite").type(hot("molten_cincinnasite").temperature(1675).lightLevel(8)).block(BurningLiquidBlock.createBurning(MapColor.TERRACOTTA_YELLOW, 8, 10, 5.0F)).bucket().flowing());
        moltenNetherRuby = register(TCTags.Items.NETHER_RUBY_GEMS, FLUIDS.registerMetal("molten_nether_ruby").type(hot("molten_nether_ruby").temperature(1530).lightLevel(8)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_RED, 8, 10, 5.0F)).bucket().flowing());

        moltenSkyjade = register(TCTags.Items.SKYJADE_GEMS, FLUIDS.registerGem("molten_skyjade").type(hot("molten_skyjade").temperature(1625).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_LIGHT_BLUE, 5, 12, 4.0F)).bucket().flowing());
        moltenStratus = register(TCTags.Items.STRATUS_INGOTS, FLUIDS.registerMetal("molten_stratus").type(hot("molten_stratus").temperature(1675).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_LIGHT_GREEN, 5, 12, 4.0F)).bucket().flowing());
        moltenStormforgedSteel = register(TCTags.Items.STORMFORGED_STEEL_INGOTS, FLUIDS.registerMetal("molten_stormforged_steel").type(hot("molten_stormforged_steel").temperature(1675).lightLevel(5)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_GRAY, 5, 12, 4.0F)).bucket().flowing());

        fireBlood = register(TCTags.Items.FIRE_DRAGON_SCALES, FLUIDS.registerSlime("fire_dragon_blood").type(cool("fire_dragon_blood").temperature(1100)).bucket().block(MapColor.GOLD, 0).flowing());
        iceBlood = register(TCTags.Items.ICE_DRAGON_SCALES, FLUIDS.registerSlime("ice_dragon_blood").type(cool("ice_dragon_blood").temperature(1100)).bucket().block(MapColor.ICE, 0).flowing());
        lightningBlood = register(TCTags.Items.LIGHTNING_DRAGON_SCALES, FLUIDS.registerSlime("lightning_dragon_blood").type(cool("lightning_dragon_blood").temperature(1100)).bucket().block(MapColor.COLOR_MAGENTA, 0).flowing());
        moltenFireDragonsteel = register(TCTags.Items.FIRE_DRAGONSTEEL_INGOTS, FLUIDS.registerMetal("molten_fire_dragonsteel").type(hot("molten_fire_dragonsteel").temperature(1400).lightLevel(4)).block(BurningLiquidBlock.createBurning(MapColor.FIRE, 4, 10, 6f)).bucket().flowing());
        moltenIceDragonsteel = register(TCTags.Items.ICE_DRAGONSTEEL_INGOTS, FLUIDS.registerMetal("molten_ice_dragonsteel").type(hot("molten_ice_dragonsteel").temperature(1400).lightLevel(4)).block(BurningLiquidBlock.createBurning(MapColor.ICE, 4, 10, 6f)).bucket().flowing());
        moltenLightningDragonsteel = register(TCTags.Items.LIGHTNING_DRAGONSTEEL_INGOTS, FLUIDS.registerMetal("molten_lightning_dragonsteel").type(hot("molten_lightning_dragonsteel").temperature(1400).lightLevel(4)).block(BurningLiquidBlock.createBurning(MapColor.LAPIS, 4, 10, 6f)).bucket().flowing());

        moltenAncientMetal = register(TCTags.Items.ANCIENT_METAL_INGOTS, FLUIDS.registerGem("molten_ancient_metal").type(hot("molten_ancient_metal").temperature(1350).lightLevel(3)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_ORANGE, 3, 12, 5.0F)).bucket().flowing());
        moltenBlackSteel = register(TCTags.Items.BLACK_STEEL_INGOTS, FLUIDS.registerMetal("molten_black_steel").type(hot("molten_black_steel").temperature(1100).lightLevel(3)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_BLACK, 3, 12, 3.0F)).bucket().flowing());
        moltenCursium = register(TCTags.Items.CURSIUM_INGOTS, FLUIDS.registerMetal("molten_cursium").type(hot("molten_cursium").temperature(1500).lightLevel(11)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_CYAN, 11, 16, 5.0F)).bucket().flowing());
        moltenWitherite = register(TCTags.Items.WITHERITE_INGOTS, FLUIDS.registerMetal("molten_witherite").type(hot("molten_witherite").temperature(1500).lightLevel(9)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_RED, 11, 16, 5.0F)).bucket().flowing());
        moltenIgnitium = register(TCTags.Items.IGNITIUM_INGOTS, FLUIDS.registerMetal("molten_ignitium").type(hot("molten_ignitium").temperature(1750).lightLevel(14)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_ORANGE, 14, 19, 7.0F)).bucket().flowing());
        moltenLacrima = register(TCTags.Items.LACRIMA_GEMS, FLUIDS.registerGem("molten_lacrima").type(hot("molten_lacrima").temperature(1230).lightLevel(10)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_LIGHT_BLUE, 10, 8, 2.0F)).bucket().flowing());

        runicSap = register(TCompat.getResource("malum:runic_sap"), FLUIDS.registerSlime("runic_sap").type(cool("runic_sap").temperature(450)).bucket().block(MapColor.TERRACOTTA_WHITE, 0).flowing());
        cursedSap = register(TCompat.getResource("malum:cursed_sap"), FLUIDS.registerSlime("cursed_sap").type(cool("cursed_sap").temperature(450)).bucket().block(MapColor.TERRACOTTA_RED, 0).flowing());
        moltenSoulStainedSteel = register(TCTags.Items.SOUL_STAINED_STEEL_INGOTS, "Molten Soulstained Steel", FLUIDS.registerMetal("molten_soul_stained_steel").type(hot("molten_soul_stained_steel").temperature(1250).lightLevel(3)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_PURPLE, 3, 12, 5.0F)).bucket().flowing());
        moltenMalignantPewter = register(TCTags.Items.MALIGNANT_PEWTER_INGOTS, FLUIDS.registerMetal("molten_malignant_pewter").type(hot("molten_malignant_pewter").temperature(1450).lightLevel(3)).block(BurningLiquidBlock.createBurning(MapColor.COLOR_LIGHT_GRAY, 3, 12, 5.0F)).bucket().flowing());
        moltenHallowedGold = register(TCTags.Items.HALLOWED_GOLD_INGOTS, FLUIDS.registerMetal("molten_hallowed_gold").type(hot("molten_hallowed_gold").temperature(1075).lightLevel(3)).block(BurningLiquidBlock.createBurning(MapColor.GOLD, 3, 12, 5.0F)).bucket().flowing());
        moltenBlazingQuartz = register(TCTags.Items.BLAZING_QUARTZ_GEMS, FLUIDS.registerGem("molten_blazing_quartz").type(hot("molten_blazing_quartz").temperature(1275).lightLevel(9)).block(BurningLiquidBlock.createBurning(MapColor.GOLD, 9, 20, 3.0F)).bucket().flowing());

        fireLilyMixture = register(TCompat.getResource("iceandfire:fire_stew"), FLUIDS.register("fire_lily_mixture").type(cool("fire_lily_mixture").temperature(400)).bucket().block(MapColor.COLOR_ORANGE, 0).commonTag().flowing());
        frostLilyMixture = register(TCompat.getResource("iceandfire:frost_stew"), FLUIDS.register("frost_lily_mixture").type(cool("frost_lily_mixture").temperature(400)).bucket().block(MapColor.COLOR_LIGHT_BLUE, 0).commonTag().flowing());
        lightningLilyMixture = register(TCompat.getResource("iceandfire:lightning_stew"), FLUIDS.register("lightning_lily_mixture").type(cool("lightning_lily_mixture").temperature(400)).bucket().block(MapColor.COLOR_PURPLE, 0).commonTag().flowing());
        ambrosia = register(TCompat.getResource("iceandfire:ambrosia"), FLUIDS.register("ambrosia").type(cool("ambrosia").temperature(400)).bucket().block(MapColor.COLOR_PINK, 0).commonTag().flowing());
        aloeVeraJuice = register(TCompat.getResource("biomeswevegone:aloe_vera_juice"), FLUIDS.register("aloe_vera_juice").type(cool("aloe_vera_juice").temperature(400)).bucket().block(MapColor.TERRACOTTA_LIGHT_BLUE, 0).commonTag().flowing());
        whitePuffballStew = register(TCompat.getResource("biomeswevegone:white_puffball_stew"), FLUIDS.register("white_puffball_stew").type(cool("white_puffball_stew").temperature(400)).bucket().block(MapColor.TERRACOTTA_WHITE, 0).commonTag().flowing());
        alliumOddionSoup = register(TCompat.getResource("biomeswevegone:allium_oddion_soup"), FLUIDS.register("allium_oddion_soup").type(cool("allium_oddion_soup").temperature(400)).bucket().block(MapColor.TERRACOTTA_ORANGE, 0).commonTag().flowing());
        umbrellaClusterJuice = register(TCompat.getResource("betterend:umbrella_cluster_juice"), FLUIDS.register("umbrella_cluster_juice").type(cool("umbrella_cluster_juice").temperature(400)).bucket().block(MapColor.COLOR_PURPLE, 0).commonTag().flowing());
        wartSoup = register(TCompat.getResource("betternether:stalagnate_bowl_wart"), FLUIDS.register("wart_soup").type(cool("wart_soup").temperature(400)).bucket().block(MapColor.TERRACOTTA_RED, 0).commonTag().flowing());
        agaveMedicine = register(TCompat.getResource("betternether:agave_medicine"), FLUIDS.register("agave_medicine").type(cool("agave_medicine").temperature(400)).bucket().block(MapColor.TERRACOTTA_PURPLE, 0).commonTag().flowing());
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
            registeredFluids.forEach((key, fluid) -> DispenserBlock.registerBehavior(fluid.fluid(), dispenseBucket));
        });
    }

    public static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        registeredFluids.forEach((key, fluid) -> fluid.acceptToTab(output));
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

    public static <T extends FluidObject<?>> T register(ResourceLocation owner, T object) {
        registeredFluids.put(object.getId(), RegisteredFluid.create(object, Either.left(owner)));
        return object;
    }

    public static <T extends FluidObject<?>> T register(TagKey<Item> owner, T object) {
        registeredFluids.put(object.getId(), RegisteredFluid.create(object, Either.right(owner)));
        return object;
    }

    public static <T extends FluidObject<?>> T register(ResourceLocation owner, String lang, T object) {
        registeredFluids.put(object.getId(), RegisteredFluid.create(object, lang, Either.left(owner)));
        return object;
    }

    public static <T extends FluidObject<?>> T register(TagKey<Item> owner, String lang, T object) {
        registeredFluids.put(object.getId(), RegisteredFluid.create(object, lang, Either.right(owner)));
        return object;
    }

    public record RegisteredFluid<T extends FluidObject<?>>(T fluid, String lang, Either<ResourceLocation, TagKey<Item>> condition) {

        public static <A extends FluidObject<?>> RegisteredFluid<A> create(A fluid, Either<ResourceLocation, TagKey<Item>> condition) {
            return new RegisteredFluid<>(fluid, toEngStr(fluid.getId().toLanguageKey("fluid")), condition);
        }

        public static <A extends FluidObject<?>> RegisteredFluid<A> create(A fluid, String lang, Either<ResourceLocation, TagKey<Item>> condition) {
            return new RegisteredFluid<>(fluid, lang, condition);
        }

        public void acceptToTab(CreativeModeTab.Output output) {
            condition.ifLeft((c) -> acceptCompat(output, fluid, c)).ifRight((c) -> acceptCompat(output, fluid, c));
        }
    }
}
