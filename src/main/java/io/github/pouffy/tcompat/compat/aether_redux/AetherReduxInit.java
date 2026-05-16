package io.github.pouffy.tcompat.compat.aether_redux;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.aether_redux.recipe.AmbrofusionModifierRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class AetherReduxInit extends CompatInitializer {
    public static final ModifierDeferredRegister REDUX_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> REDUX_RS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, TCompat.MOD_ID);

    public static final StaticModifier<AmbrofusionModifier> ambrofusion = REDUX_M.register("ambrofusion", AmbrofusionModifier::new);
    public static final StaticModifier<BlightedModifier> blighted = REDUX_M.register("blighted", BlightedModifier::new);

    public static final RegistryObject<RecipeSerializer<AmbrofusionModifierRecipe>> ambrofusionSerializer = REDUX_RS.register("ambrofusion_modifier", () -> LoadableRecipeSerializer.of(AmbrofusionModifierRecipe.LOADER));

    public static void init(IEventBus eventBus) {
        REDUX_M.register(eventBus);
        REDUX_RS.register(eventBus);
        AetherReduxMaterials.staticInit();
    }
}
