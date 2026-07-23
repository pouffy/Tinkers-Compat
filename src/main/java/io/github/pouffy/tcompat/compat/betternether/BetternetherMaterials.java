package io.github.pouffy.tcompat.compat.betternether;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.compat.CompatToolStats;
import io.github.pouffy.tcompat.compat.tinkersjewelry.PlainRingMaterialStats;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import slimeknights.tconstruct.tools.stats.*;

import static net.minecraft.world.item.Tiers.DIAMOND;
import static net.minecraft.world.item.Tiers.IRON;

public class BetternetherMaterials {

    public static final MaterialId cincinnasite = MaterialBuilder.material("betternether", "cincinnasite")
            .flavor("Cinnamonasite")
            .data(d -> d.tier(4).order(0).craftable(false))
            .traits(t -> t.trait(ModifierIds.worldbound).trait(HeadMaterialStats.ID, BetternetherInit.obsidianBreaker))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(513, 6.2f, IRON, 2.5f),
                            HandleMaterialStats.multipliers().durability(1.20f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(513, 0.1f, 0, 0.05f),
                            new GripMaterialStats(0.05f, 0.05f, 2.5f)
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(12).armor(3, 5, 6, 3).toughness(1f).knockbackResistance(0.05f),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.plainRing(513, 1.2f)
                    )
            )
            .renderInfo(r -> r.color(0xffc461).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).armor().meleeHarvest().ranged().sixColor(0xFF9b6607, 0xFFd88a0a, 0xFFf0a31d, 0xFFffc461, 0xFFffd58e, 0xFFfff0d5))
            .buildMaterial();

    public static final MaterialId netherRuby = MaterialBuilder.material("betternether", "nether_ruby")
            .flavor("Lamp oil, rope, bombs...")
            .data(d -> d.tier(4).order(0).craftable(false)).excludeFromLoot()
            .traits(t -> t
                    .trait(ModifierIds.worldbound)
                    .trait(HeadMaterialStats.ID, BetternetherInit.obsidianBreaker)
                    .trait(CompatToolStats.Statless.ADORNMENT.getIdentifier(), TCModifiers.fireUpgrade, TCModifiers.fireDispulsion)
            )
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(2562, 7.1f, DIAMOND, 3.1f),
                            HandleMaterialStats.multipliers().miningSpeed(1.10f).attackDamage(0.9f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(2562, -0.2f, 0.15f, 0),
                            new GripMaterialStats(0.15f, 0.1f, 3.1f)
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(18).armor(3, 5, 7, 3).toughness(1.4f).knockbackResistance(0.2f),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.Statless.CUT_GEM,
                            CompatToolStats.Statless.ADORNMENT
                    )
            )
            .renderInfo(r -> r.color(0xeb4150).fallbacks("gem", "metal"))
            .spriteInfo(s -> s.fallbacks("gem", "metal")
                    .repairKit()
                    .statType(CompatToolStats.Statless.CUT_GEM.getIdentifier())
                    .statType(CompatToolStats.Statless.ADORNMENT.getIdentifier())
                    .armor()
                    .meleeHarvest()
                    .ranged()
                    .sixColor(0xFF4c000b, 0xFF6a000f, 0xFFce1425, 0xFFeb4150, 0xFFf0747f, 0xFFf8c0c5))
            .buildMaterial();

    public static final MaterialVariantId blackVine = MaterialBuilder.variant("betternether", "black_vine", MaterialIds.weepingVine)
            .renderInfo(r -> r.color(0xFF36343c).fallbacks("primitive", "cloth"))
            .spriteInfo(s -> s.vines().sixColor(0xFF010101, 0xFF151516, 0xFF26242b, 0xFF36343c, 0xFF4f4b59, 0xFF635f6e))
            .buildVariant();

    public static final MaterialId goldenVine = MaterialBuilder.material("betternether", "golden_vine")
            .flavor("I can't believe it's not gold.")
            .data(d -> d.tier(2).order(0).craftable(true))
            .traits(t -> t.trait(ModifierIds.glowing))
            .stats(s -> s.stat(StatlessMaterialStats.BINDING, StatlessMaterialStats.BOWSTRING, StatlessMaterialStats.MAILLE))
            .renderInfo(r -> r.color(0xFFffcb00).fallbacks("primitive", "cloth"))
            .spriteInfo(s -> s.vines().sixColor(0xFFfa7e06, 0xFFff9a00, 0xFFffb800, 0xFFffcb00, 0xFFfcdd3a, 0xFFf8f17d))
            .buildMaterial();

    public static final MaterialId bloomingVine = MaterialBuilder.material("betternether", "blooming_vine")
            .flavor("Howdy!")
            .data(d -> d.tier(2).order(0).craftable(true))
            .traits(t -> t.trait(ModifierIds.deciduous))
            .stats(s -> s.stat(StatlessMaterialStats.BINDING, StatlessMaterialStats.BOWSTRING, StatlessMaterialStats.MAILLE))
            .renderInfo(r -> r.color(0xFF363e6a).fallbacks("primitive", "cloth"))
            .spriteInfo(s -> s.vines().sixColor(0xFF1a1a24, 0xFF262945, 0xFF2a2e5b, 0xFF363e6a, 0xFF4e5d88, 0xFF5d6f9e))
            .buildMaterial();

    public static void staticInit() {}
}
