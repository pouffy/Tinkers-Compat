package io.github.pouffy.tcompat.compat.ad_astra;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class AdAstraInit extends CompatInitializer {
    public static final ModifierDeferredRegister ASTRA_M = ModifierDeferredRegister.create(TCompat.MOD_ID);

    public static final StaticModifier<OxygenatedModifier> oxygenated = ASTRA_M.register("oxygenated", OxygenatedModifier::new);

    public static void init(IEventBus eventBus) {
        ASTRA_M.register(eventBus);
        AdAstraMaterials.staticInit();
    }
}
