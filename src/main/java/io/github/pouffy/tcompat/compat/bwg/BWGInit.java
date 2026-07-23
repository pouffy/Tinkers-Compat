package io.github.pouffy.tcompat.compat.bwg;

import io.github.pouffy.tcompat.common.util.CompatInitializer;
import net.minecraftforge.eventbus.api.IEventBus;

public class BWGInit extends CompatInitializer {

    public static void init(IEventBus eventBus) {
        BWGMaterials.staticInit();
    }
}
