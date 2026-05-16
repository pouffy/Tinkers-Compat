package io.github.pouffy.tcompat.compat.cataclysm;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.cataclysm.modifier.*;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class CataclysmInit extends CompatInitializer {
    public static final ModifierDeferredRegister C_M = ModifierDeferredRegister.create(TCompat.MOD_ID);

    public static final StaticModifier<VoidScatterModifier> voidScatter = C_M.register("void_scatter", VoidScatterModifier::new); // Void Jaws
    public static final StaticModifier<AquaticModifier> aquatic = C_M.register("aquatic", AquaticModifier::new); // Coral
    public static final StaticModifier<SandstormModifier> sandstorm = C_M.register("sandstorm", SandstormModifier::new);
    public static final StaticModifier<PhantasmicModifier> phantasmic = C_M.register("phantasmic", PhantasmicModifier::new); // Cursium Bowstring
    public static final StaticModifier<TidalModifier> tidal = C_M.register("tidal", TidalModifier::new); // Lacrima Tool Head
    public static final StaticModifier<FluxedModifier> fluxed = C_M.register("fluxed", FluxedModifier::new); // Witherite Bow Grip

    public static void init(IEventBus eventBus) {
        C_M.register(eventBus);
        CataclysmMaterials.staticInit();
    }
}
