package io.github.pouffy.tcompat.compat.caverns_chasms;

import com.google.common.collect.ImmutableList;
import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.caverns_chasms.modifier.combat.NullificationModifier;
import io.github.pouffy.tcompat.compat.caverns_chasms.modifier.combat.ranged.BluntModifier;
import io.github.pouffy.tcompat.compat.ice_and_fire.modifier.combat.ranged.LeechingModifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Instrument;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class CavernsChasmsInit extends CompatInitializer {
    public static final ModifierDeferredRegister C_M = ModifierDeferredRegister.create(TCompat.MOD_ID);

    public static final StaticModifier<BluntModifier> blunt = C_M.register("blunt", BluntModifier::new);
    public static final StaticModifier<NullificationModifier> nullification = C_M.register("nullification", NullificationModifier::new);

    public static void init(IEventBus eventBus) {
        C_M.register(eventBus);
        CavernsChasmsMaterials.staticInit();
    }

    // Fake Goat Horn keys
    static ResourceKey<Instrument> FLY_GOAT_HORN = createInstrument("caverns_and_chasms:fly_goat_horn");
    static ResourceKey<Instrument> RESIST_GOAT_HORN = createInstrument("caverns_and_chasms:resist_goat_horn");

    public static final ImmutableList<ResourceKey<Instrument>> GREAT_SKY_FALLING_COPPER_HORN = createCopperHorn("great", "sky", "falling");
    public static final ImmutableList<ResourceKey<Instrument>> OLD_HYMN_RESTING_COPPER_HORN = createCopperHorn("old", "hymn", "resting");
    public static final ImmutableList<ResourceKey<Instrument>> PURE_WATER_DESIRE_COPPER_HORN = createCopperHorn("pure", "water", "desire");
    public static final ImmutableList<ResourceKey<Instrument>> HUMBLE_FIRE_MEMORY_COPPER_HORN = createCopperHorn("humble", "fire", "memory");
    public static final ImmutableList<ResourceKey<Instrument>> DRY_URGE_ANGER_COPPER_HORN = createCopperHorn("dry", "urge", "anger");
    public static final ImmutableList<ResourceKey<Instrument>> CLEAR_TEMPER_JOURNEY_COPPER_HORN = createCopperHorn("clear", "temper", "journey");
    public static final ImmutableList<ResourceKey<Instrument>> FRESH_NEST_THOUGHT_COPPER_HORN = createCopperHorn("fresh", "nest", "thought");
    public static final ImmutableList<ResourceKey<Instrument>> SECRET_LAKE_TEAR_COPPER_HORN = createCopperHorn("secret", "lake", "tear");
    public static final ImmutableList<ResourceKey<Instrument>> FEARLESS_RIVER_GIFT_COPPER_HORN = createCopperHorn("fearless", "river", "gift");
    public static final ImmutableList<ResourceKey<Instrument>> SWEET_MOON_LOVE_COPPER_HORN = createCopperHorn("sweet", "moon", "love");

    private static ResourceKey<Instrument> createInstrument(String name) {
        return ResourceKey.create(Registries.INSTRUMENT, TCompat.getResource(name));
    }

    private static ImmutableList<ResourceKey<Instrument>> createCopperHorn(String harmonyName, String melodyName, String bassName) {
        ResourceKey<Instrument> harmony = createInstrument("caverns_and_chasms:" + harmonyName + "_copper_horn");
        ResourceKey<Instrument> melody = createInstrument("caverns_and_chasms:" + melodyName + "_copper_horn");
        ResourceKey<Instrument> bass = createInstrument("caverns_and_chasms:" + bassName + "_copper_horn");
        return ImmutableList.of(harmony, melody, bass);
    }
}
