package io.github.pouffy.tcompat.compat.aether_treasure_reforging;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class AetherTRInit extends CompatInitializer {
    public static final ModifierDeferredRegister AETHERTR_M = ModifierDeferredRegister.create(TCompat.MOD_ID);

    public static final StaticModifier<PhoenixTouchedModifier> phoenixTouched = AETHERTR_M.register("phoenix_touched", PhoenixTouchedModifier::new);

    public static void init(IEventBus eventBus) {
        AETHERTR_M.register(eventBus);
        AetherTRMaterials.staticInit();
    }
}
