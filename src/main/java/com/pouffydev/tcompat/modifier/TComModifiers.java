package com.pouffydev.tcompat.modifier;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.TCompatModule;
import com.pouffydev.tcompat.modifier.twilightforest.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.tools.modifiers.upgrades.armor.LightspeedArmorModifier;

public class TComModifiers extends TCompatModule {
    private static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(TCompat.MOD_ID);

    //Traits
    public static final StaticModifier<PrecipitateModifier> precipitate = MODIFIERS.register("precipitate", PrecipitateModifier::new);
    public static final StaticModifier<StalwartModifier> stalwart = MODIFIERS.register("stalwart", StalwartModifier::new);
    public static final StaticModifier<SuperheatModifier> superheat = MODIFIERS.register("superheat", SuperheatModifier::new);
    public static final StaticModifier<SynergyModifier> synergy = MODIFIERS.register("synergy", SynergyModifier::new);
    public static final StaticModifier<VeiledModifier> veiled = MODIFIERS.register("veiled", VeiledModifier::new);
    public static final StaticModifier<TwilitModifier> twilit = MODIFIERS.register("twilit", TwilitModifier::new);

    public TComModifiers() {
        MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


}
