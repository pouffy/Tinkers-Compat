package io.github.pouffy.tcompat.compat.malum;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.tools.SlotType;

public class MalumInit extends CompatInitializer {
    public static final ModifierDeferredRegister MALUM_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final ItemDeferredRegister MALUM_I = new ItemDeferredRegister(TCompat.MOD_ID);

    public static final SlotType RUNE_SLOT = SlotType.getOrCreate("rune");

    public static void init(IEventBus eventBus) {
        MALUM_M.register(eventBus);
        MALUM_I.register(eventBus);
        MalumMaterials.staticInit();
    }
}
