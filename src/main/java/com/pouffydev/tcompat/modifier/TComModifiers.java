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


}
