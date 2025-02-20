package com.pouffydev.tcompat.modifier;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.TCompatModule;
import com.pouffydev.tcompat.modifier.malum.rune.TotemicRuneModifier;
import com.pouffydev.tcompat.modifier.malum.rune.TotemicRuneRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.recipe.helper.SimpleRecipeSerializer;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.tools.recipe.ArmorTrimRecipe;

public class TComModifiers extends TCompatModule {
    private static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(TCompat.MOD_ID);

    public TComModifiers() {
        MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final StaticModifier<TotemicRuneModifier> totemicRune = MODIFIERS.register("totemic_rune", TotemicRuneModifier::new);

    public static final RegistryObject<RecipeSerializer<TotemicRuneRecipe>> totemicRuneSerializer = RECIPE_SERIALIZERS.register("totemic_rune_modifier", () -> new SimpleRecipeSerializer<>(TotemicRuneRecipe::new));

}
