package io.github.pouffy.tcompat.compat.betternether;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

@SuppressWarnings("unused")
public class BetternetherInit extends CompatInitializer {
    public static final ModifierDeferredRegister BN_M = ModifierDeferredRegister.create(TCompat.MOD_ID);

    public static final StaticModifier<ObsidianBreakerModifier> obsidianBreaker = BN_M.register("obsidian_breaker", ObsidianBreakerModifier::new);
    public static final StaticModifier<RubysFireModifier> rubysFire = BN_M.register("rubys_fire", RubysFireModifier::new);
    public static final StaticModifier<CinderspineModifier> cinderspine = BN_M.register("cinderspine", CinderspineModifier::new);

    public static void init(IEventBus eventBus) {
        BN_M.register(eventBus);
        BetternetherMaterials.staticInit();
    }
}
