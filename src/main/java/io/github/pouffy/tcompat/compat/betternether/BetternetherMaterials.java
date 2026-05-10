package io.github.pouffy.tcompat.compat.betternether;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.compat.tinkersjewelry.CutGemMaterialStats;
import io.github.pouffy.tcompat.compat.tinkersjewelry.PlainRingMaterialStats;
import io.github.pouffy.tcompat.compat.tinkersjewelry.TJStats;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.stats.*;

import static net.minecraft.world.item.Tiers.*;

public class BetternetherMaterials {

    public static final MaterialId cincinnasite = MaterialBuilder.material("betternether", "cincinnasite")
            .data(d -> d.tier(2).order(1).craftable(false))
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
                            TJStats.plainRing(513, 1.2f)
                    )
            )
            .renderInfo(r -> r.color(0xffc461).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).armor().meleeHarvest().ranged().sixColor(0xFF9b6607, 0xFFd88a0a, 0xFFf0a31d, 0xFFffc461, 0xFFffd58e, 0xFFfff0d5))
            .buildMaterial();

    public static final MaterialId netherRuby = MaterialBuilder.material("betternether", "nether_ruby")
            .data(d -> d.tier(2).order(1).craftable(false))
            .traits(t -> t.trait(ModifierIds.worldbound).trait(HeadMaterialStats.ID, BetternetherInit.obsidianBreaker))
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
                            TJStats.CUT_GEM
                    )
            )
            .renderInfo(r -> r.color(0xeb4150).fallbacks("gem", "metal"))
            .spriteInfo(s -> s.fallbacks("gem", "metal").repairKit().statType(CutGemMaterialStats.ID).armor().meleeHarvest().ranged().sixColor(0xFF4c000b, 0xFF6a000f, 0xFFce1425, 0xFFeb4150, 0xFFf0747f, 0xFFf8c0c5))
            .buildMaterial();

    public static void staticInit() {}
}
