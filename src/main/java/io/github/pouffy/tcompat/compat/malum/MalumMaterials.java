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
            .data(d -> d.tier(3).order(1).craftable(false))
            .lang("Soulstained Steel")
            .traits(t -> t.trait(TCModifiers.stained).trait(ARMOR, TCModifiers.warded).trait(CompatToolStats.Statless.CUT_GEM.getIdentifier(), TCModifiers.armorToughness_gem))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(1250, 7.5f, DIAMOND, 2.5f),
                            HandleMaterialStats.multipliers().miningSpeed(0.9f).attackSpeed(1.15f).build(),
                            StatlessMaterialStats.BINDING
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(22).armor(2, 6, 7, 3).toughness(2),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.Statless.CUT_GEM
                    )
            )
            .renderInfo(r -> r.color(0xce7cee).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(CompatToolStats.Statless.CUT_GEM.getIdentifier()).armor().meleeHarvest().sixColor(0xFF40365b, 0xFF593b7c, 0xFF8a5eae, 0xFFce7cee, 0xFFee8fff, 0xFFffc5e4))
            .buildMaterial();

    public static final MaterialId hallowedGold = MaterialBuilder.material("malum", "hallowed_gold")
            .data(d -> d.tier(3).order(2).craftable(false))
            .traits(t -> t.trait(TCModifiers.hallowed).trait(CompatToolStats.Statless.CUT_GEM.getIdentifier(), TCModifiers.armor_gem))
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

    public static final MaterialId malignantPewter = MaterialBuilder.material("malum", "malignant_pewter")
            .data(d -> d.tier(3).order(1).craftable(false))
            .traits(t -> t.trait(MalumInit.certainty).trait(ARMOR, TCModifiers.stronghold))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(2500, 8.0F, DIAMOND, 4.0F),
                            HandleMaterialStats.multipliers().durability(1.25f).attackSpeed(0.9f).build(),
                            StatlessMaterialStats.BINDING
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(32).armor(3, 6, 8, 3).toughness(2).knockbackResistance(0.1f),
                            StatlessMaterialStats.MAILLE
                    )
            )
            .renderInfo(r -> r.color(0xc9a1d0).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().armor().meleeHarvest().sixColor(0xFF38204d, 0xFF4a385c, 0xFF705b7c, 0xFFc9a1d0, 0xFFeccef6, 0xFFffeaf7))
            .buildMaterial();

    public static final MaterialId spiritFabric = MaterialBuilder.material("malum", "spirit_fabric")
            .data(d -> d.tier(2).order(4).craftable(true))
            .traits(t -> t.trait(TCModifiers.magicProficiency))
            .stats(s ->
                    s.stat(
                            StatlessMaterialStats.BINDING,
                            StatlessMaterialStats.BOWSTRING,
                            StatlessMaterialStats.MAILLE
                    )
            )
            .renderInfo(r -> r.color(0x3e2e3e).fallbacks("cloth"))
            .spriteInfo(s -> s.fallbacks("cloth").repairKit().binding().bowstring().maille().sixColor(0xFF1d1c20, 0xFF272429, 0xFF312c31, 0xFF3e2e3e, 0xFF463652, 0xFF5e367a))
            .buildMaterial();

    public static void staticInit() {}
}
