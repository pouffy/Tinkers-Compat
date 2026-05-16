package io.github.pouffy.tcompat.compat.ancient_aether;

import io.github.pouffy.tcompat.common.util.CompatInitializer;
import net.minecraftforge.eventbus.api.IEventBus;

public class AncientAetherInit extends CompatInitializer {


    public static void init(IEventBus eventBus) {
        AncientAetherMaterials.staticInit();
    }
}
