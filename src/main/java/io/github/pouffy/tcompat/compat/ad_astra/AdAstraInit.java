package io.github.pouffy.tcompat.compat.ad_astra;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.ad_astra.modifier.general.CryogenicModifier;
import io.github.pouffy.tcompat.compat.ad_astra.modifier.general.DischargeModifier;
import io.github.pouffy.tcompat.compat.ad_astra.modifier.general.OxygenatedModifier;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegisterEvent;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class AdAstraInit extends CompatInitializer {
    public static final ModifierDeferredRegister ASTRA_M = ModifierDeferredRegister.create(TCompat.MOD_ID);

    public static final StaticModifier<DischargeModifier> discharge = ASTRA_M.register("discharge", DischargeModifier::new);
    public static final StaticModifier<CryogenicModifier> cryogenic = ASTRA_M.register("cryogenic", CryogenicModifier::new);
    public static final StaticModifier<OxygenatedModifier> oxygenated = ASTRA_M.register("oxygenated", OxygenatedModifier::new);

    public static void init(IEventBus eventBus) {
        ASTRA_M.register(eventBus);
        AdAstraMaterials.staticInit();
    }

    @SubscribeEvent
    void registerSerializers(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.RECIPE_SERIALIZER) {
            ToolStats.register(CryogenicModifier.MAX_STAT);
        }
    }
}
