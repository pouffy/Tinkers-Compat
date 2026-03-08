package io.github.pouffy.tcompat.compat.aether_redux;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.aether_redux.recipe.AmbrofusionModifierRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static slimeknights.mantle.block.fluid.BurningLiquidBlock.createBurning;

public class AetherReduxInit extends CompatInitializer {
    public static final ModifierDeferredRegister REDUX_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final FluidDeferredRegister REDUX_F = new FluidDeferredRegister(TCompat.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> REDUX_RS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, TCompat.MOD_ID);

    public static final StaticModifier<AmbrofusionModifier> ambrofusion = REDUX_M.register("ambrofusion", AmbrofusionModifier::new);
    public static final StaticModifier<BlightedModifier> blighted = REDUX_M.register("blighted", BlightedModifier::new);

    public static final FlowingFluidObject<ForgeFlowingFluid> moltenVeridium = REDUX_F.register("molten_veridium").type(hot("molten_veridium").temperature(1425).lightLevel(13)).block(createBurning(MapColor.COLOR_BLUE, 13, 10, 5f)).bucket().commonTag().flowing();
    public static final FlowingFluidObject<ForgeFlowingFluid> moltenRefinedSentrite = REDUX_F.register("molten_refined_sentrite").type(hot("molten_refined_sentrite").temperature(1755).lightLevel(8)).block(createBurning(MapColor.TERRACOTTA_GRAY, 8, 13, 7.5f)).bucket().commonTag().flowing();

    public static final RegistryObject<RecipeSerializer<AmbrofusionModifierRecipe>> ambrofusionSerializer = REDUX_RS.register("ambrofusion_modifier", () -> LoadableRecipeSerializer.of(AmbrofusionModifierRecipe.LOADER));

    @SubscribeEvent
    void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(moltenVeridium, dispenseBucket);
            DispenserBlock.registerBehavior(moltenRefinedSentrite, dispenseBucket);
        });
    }

    public static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        acceptMolten(output, moltenVeridium);
        acceptMolten(output, moltenRefinedSentrite);
    }

    public static void init(IEventBus eventBus) {
        REDUX_M.register(eventBus);
        REDUX_F.register(eventBus);
        REDUX_RS.register(eventBus);
    }
}
