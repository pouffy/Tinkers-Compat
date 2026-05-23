package io.github.pouffy.tcompat.compat.betterend;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.betterend.modifier.combat.VoidTouchedModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class BetterendInit extends CompatInitializer {
    public static final ModifierDeferredRegister BE_M = ModifierDeferredRegister.create(TCompat.MOD_ID);

    public static final StaticModifier<VoidTouchedModifier> voidTouched = BE_M.register("void_touched", VoidTouchedModifier::new);

    public static void init(IEventBus eventBus) {
        BE_M.register(eventBus);
        BetterendMaterials.staticInit();
    }
}
