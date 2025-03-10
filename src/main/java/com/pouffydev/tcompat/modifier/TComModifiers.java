package com.pouffydev.tcompat.modifier;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.TCompatModule;
import com.pouffydev.tcompat.modifier.aether.*;
import com.pouffydev.tcompat.modifier.aether.recipe.AmbrofusionModifierRecipe;
import com.pouffydev.tcompat.modifier.twilightforest.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class TComModifiers extends TCompatModule {
    private static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(TCompat.MOD_ID);

    //Traits
    public static final StaticModifier<PrecipitateModifier> precipitate = MODIFIERS.register("precipitate", PrecipitateModifier::new);
    public static final StaticModifier<UprootingModifier> uprooting = MODIFIERS.register("uprooting", UprootingModifier::new);
    public static final StaticModifier<StalwartModifier> stalwart = MODIFIERS.register("stalwart", StalwartModifier::new);
    public static final StaticModifier<SuperheatModifier> superheat = MODIFIERS.register("superheat", SuperheatModifier::new);
    public static final StaticModifier<SynergyModifier> synergy = MODIFIERS.register("synergy", SynergyModifier::new);
    public static final StaticModifier<VeiledModifier> veiled = MODIFIERS.register("veiled", VeiledModifier::new);
    public static final StaticModifier<TwilitModifier> twilit = MODIFIERS.register("twilit", TwilitModifier::new);

    public static final StaticModifier<AmbrogenModifier> ambrogen = MODIFIERS.register("ambrogen", AmbrogenModifier::new);
    public static final StaticModifier<AscensionModifier> ascension = MODIFIERS.register("ascension", AscensionModifier::new);
    public static final StaticModifier<SuperJumpModifier> superJump = MODIFIERS.register("super_jump", SuperJumpModifier::new);
    public static final StaticModifier<BlastDeterranceModifier> blastDeterrance = MODIFIERS.register("blast_deterrance", BlastDeterranceModifier::new);

    public static final StaticModifier<DullingModifier> dulling = MODIFIERS.register("dulling", DullingModifier::new);
    public static final StaticModifier<AcclimatizationModifier> acclimatization = MODIFIERS.register("acclimatization", AcclimatizationModifier::new);
    //public static final StaticModifier<SentryflareModifier> sentryflare = MODIFIERS.register("sentryflare", SentryflareModifier::new);
    public static final StaticModifier<AmbrofusionModifier> ambrofusion = MODIFIERS.register("ambrofusion", AmbrofusionModifier::new);


    public static final RegistryObject<RecipeSerializer<AmbrofusionModifierRecipe>> ambrofusionSerializer = RECIPE_SERIALIZERS.register("ambrofusion_modifier", () -> LoadableRecipeSerializer.of(AmbrofusionModifierRecipe.LOADER));

    public TComModifiers() {
        MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


}
