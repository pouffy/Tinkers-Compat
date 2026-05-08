package io.github.pouffy.tcompat.compat.cataclysm;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;

public class CataclysmInit extends CompatInitializer {
    public static final ModifierDeferredRegister C_M = ModifierDeferredRegister.create(TCompat.MOD_ID);


    public static void init(IEventBus eventBus) {
        C_M.register(eventBus);
    }
}
