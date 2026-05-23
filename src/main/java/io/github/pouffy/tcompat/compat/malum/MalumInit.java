package io.github.pouffy.tcompat.compat.malum;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.malum.modifier.melee.CertaintyModifier;
import io.github.pouffy.tcompat.compat.malum.modifier.melee.DeliveranceModifier;
import io.github.pouffy.tcompat.compat.malum.modifier.general.SpiritRepairModifier;
import io.github.pouffy.tcompat.compat.malum.modifier.ranged.AuricFlameModifier;
import io.github.pouffy.tcompat.compat.malum.modifier.ranged.ErosionModifier;
import io.github.pouffy.tcompat.compat.malum.modifier.ranged.MnemonicModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.library.tools.SlotType;

import java.util.Map;

public class MalumInit extends CompatInitializer {
    public static final ModifierDeferredRegister MALUM_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final ItemDeferredRegister MALUM_I = new ItemDeferredRegister(TCompat.MOD_ID);

    public static final StaticModifier<CertaintyModifier> certainty = MALUM_M.register("certainty", CertaintyModifier::new);
    public static final StaticModifier<SpiritRepairModifier> spiritRepair = MALUM_M.register("spirit_repair", SpiritRepairModifier::new);
    public static final StaticModifier<DeliveranceModifier> deliverance = MALUM_M.register("deliverance", DeliveranceModifier::new);
    public static final StaticModifier<MnemonicModifier> mnemonic = MALUM_M.register("mnemonic", MnemonicModifier::new);
    public static final StaticModifier<ErosionModifier> erosion = MALUM_M.register("erosion", ErosionModifier::new);
    public static final StaticModifier<AuricFlameModifier> auricFlame = MALUM_M.register("auric_flame", AuricFlameModifier::new);

    public static final SlotType RUNE_SLOT = SlotType.getOrCreate("rune");

    public static final Map<MaterialVariantId, MaterialVariantId> weepingWellConversions = Map.ofEntries(
            Map.entry(MalumMaterials.soulstone, MalumMaterials.nullSlate)
    );

    public static void init(IEventBus eventBus) {
        MALUM_M.register(eventBus);
        MALUM_I.register(eventBus);
        MalumMaterials.staticInit();
    }
}
