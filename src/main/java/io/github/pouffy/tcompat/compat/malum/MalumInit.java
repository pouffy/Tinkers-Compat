package io.github.pouffy.tcompat.compat.malum;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.modifier.base.TotemicRuneModifier;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.malum.modifier.melee.CertaintyModifier;
import io.github.pouffy.tcompat.compat.malum.modifier.melee.DeliveranceModifier;
import io.github.pouffy.tcompat.compat.malum.modifier.general.SpiritRepairModifier;
import io.github.pouffy.tcompat.compat.malum.modifier.ranged.AuricFlameModifier;
import io.github.pouffy.tcompat.compat.malum.modifier.ranged.ErosionModifier;
import io.github.pouffy.tcompat.compat.malum.modifier.ranged.MnemonicModifier;
import io.github.pouffy.tcompat.compat.malum.modifier.rune.RuneOfCullingModifier;
import io.github.pouffy.tcompat.compat.malum.modifier.rune.RuneOfDexterityModifier;
import io.github.pouffy.tcompat.compat.malum.modifier.rune.RuneOfReinforcementModifier;
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

    //Totemic Runes
    public static final StaticModifier<TotemicRuneModifier> runeOfMotion = MALUM_M.register("rune_of_motion", () -> new TotemicRuneModifier("aerial", false));
    public static final StaticModifier<TotemicRuneModifier> runeOfLoyalty = MALUM_M.register("rune_of_loyalty", () -> new TotemicRuneModifier("aqueous", false));
    public static final StaticModifier<TotemicRuneModifier> runeOfWarding = MALUM_M.register("rune_of_warding", () -> new TotemicRuneModifier("earthen", false));
    public static final StaticModifier<TotemicRuneModifier> runeOfHaste = MALUM_M.register("rune_of_haste", () -> new TotemicRuneModifier("infernal", false));
    public static final StaticModifier<TotemicRuneModifier> runeOfTheAether = MALUM_M.register("rune_of_the_aether", () -> new TotemicRuneModifier("aerial", true));
    public static final StaticModifier<TotemicRuneModifier> runeOfTheSeas = MALUM_M.register("rune_of_the_seas", () -> new TotemicRuneModifier("aqueous", true));
    public static final StaticModifier<TotemicRuneModifier> runeOfTheArena = MALUM_M.register("rune_of_the_arena", () -> new TotemicRuneModifier("earthen", true));
    public static final StaticModifier<TotemicRuneModifier> runeOfTheHells = MALUM_M.register("rune_of_the_hells", () -> new TotemicRuneModifier("infernal", true));

    public static final StaticModifier<RuneOfCullingModifier> runeOfCulling = MALUM_M.register("rune_of_culling", RuneOfCullingModifier::new);
    public static final StaticModifier<RuneOfReinforcementModifier> runeOfReinforcement = MALUM_M.register("rune_of_reinforcement", RuneOfReinforcementModifier::new);
    public static final StaticModifier<RuneOfDexterityModifier> runeOfDexterity = MALUM_M.register("rune_of_dexterity", RuneOfDexterityModifier::new);

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
