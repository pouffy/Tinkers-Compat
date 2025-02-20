package com.pouffydev.tcompat.modifier;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.TCompatModule;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;

public class TComModifiers extends TCompatModule {
    private static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(TCompat.MOD_ID);

    public TComModifiers() {
        MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //public static final StaticModifier<TotemicRuneModifier> totemicRune = MODIFIERS.register("totemic_rune", TotemicRuneModifier::new);
//
    //public static final RegistryObject<RecipeSerializer<TotemicRuneRecipe>> totemicRuneSerializer = RECIPE_SERIALIZERS.register("totemic_rune_modifier", () -> new SimpleRecipeSerializer<>(TotemicRuneRecipe::new));

}
