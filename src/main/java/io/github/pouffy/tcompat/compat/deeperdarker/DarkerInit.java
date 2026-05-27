package io.github.pouffy.tcompat.compat.deeperdarker;

import io.github.pouffy.tcompat.TCompat;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;

public class DarkerInit {
    public static final ModifierDeferredRegister DD_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final ItemDeferredRegister DD_I = new ItemDeferredRegister(TCompat.MOD_ID);

    public static void init(IEventBus eventBus) {
        DD_M.register(eventBus);
        DD_I.register(eventBus);
        DarkerMaterials.staticInit();
    }
}
