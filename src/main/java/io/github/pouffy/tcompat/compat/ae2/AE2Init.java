package io.github.pouffy.tcompat.compat.ae2;

import io.github.pouffy.tcompat.common.util.CompatInitializer;
import net.minecraftforge.eventbus.api.IEventBus;

public class AE2Init extends CompatInitializer {

    public static void init(IEventBus eventBus) {
        AE2Materials.staticInit();
    }
}
