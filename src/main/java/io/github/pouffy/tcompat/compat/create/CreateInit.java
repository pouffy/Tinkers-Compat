package io.github.pouffy.tcompat.compat.create;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.create.modifier.harvest.ClockworkModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class CreateInit extends CompatInitializer {
    public static final ModifierDeferredRegister C_M = ModifierDeferredRegister.create(TCompat.MOD_ID);

    public static final StaticModifier<ClockworkModifier> clockwork = C_M.register("clockwork", ClockworkModifier::new); // Brass


    public static void init(IEventBus eventBus) {
        C_M.register(eventBus);
        CreateMaterials.staticInit();
    }
}
