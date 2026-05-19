package io.github.pouffy.tcompat.compat.malum;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.compat.CompatToolStats;
import io.github.pouffy.tcompat.compat.tinkersjewelry.PlainRingMaterialStats;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.stats.*;

import static slimeknights.tconstruct.library.materials.MaterialRegistry.ARMOR;
import static net.minecraft.world.item.Tiers.DIAMOND;
import static net.minecraft.world.item.Tiers.GOLD;

public class MalumMaterials {

    public static final MaterialId soulStainedSteel = MaterialBuilder.material("malum", "soul_stained_steel")
            .data(d -> d.tier(3).order(3).craftable(false))
            .lang("Soulstained Steel")
            .traits(t -> t.trait(TCModifiers.stained).trait(ARMOR, TCModifiers.warded))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(1250, 7.5f, DIAMOND, 2.5f),
                            HandleMaterialStats.multipliers().miningSpeed(0.9f).attackSpeed(1.15f).build(),
                            StatlessMaterialStats.BINDING
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(22).armor(2, 6, 7, 3).toughness(2),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.plainRing(1250, 1.4f)
                    )
            )
            .renderInfo(r -> r.color(0xce7cee).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).armor().meleeHarvest().sixColor(0xFF40365b, 0xFF593b7c, 0xFF8a5eae, 0xFFce7cee, 0xFFee8fff, 0xFFffc5e4))
            .buildMaterial();

    public static final MaterialId hallowedGold = MaterialBuilder.material("malum", "hallowed_gold")
            .data(d -> d.tier(3).order(3).craftable(false))
            .traits(t -> t.trait(TCModifiers.hallowed))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(340, 6.5f, GOLD, 1.5f),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.Statless.CUT_GEM
                    )
            )
            .renderInfo(r -> r.color(0xf7d85a).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(CompatToolStats.Statless.CUT_GEM.getIdentifier()).head().maille().sixColor(0xFF772923, 0xFFa94f2d, 0xFFd97f28, 0xFFf7d85a, 0xFFfff394, 0xFFfffde9))
            .buildMaterial();

    public static void staticInit() {}
}
