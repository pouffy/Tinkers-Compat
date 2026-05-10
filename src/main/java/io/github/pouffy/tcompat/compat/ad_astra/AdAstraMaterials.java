package io.github.pouffy.tcompat.compat.ad_astra;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.compat.tinkersjewelry.PlainRingMaterialStats;
import io.github.pouffy.tcompat.compat.tinkersjewelry.TJStats;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.stats.*;

import static net.minecraft.world.item.Tiers.DIAMOND;
import static net.minecraft.world.item.Tiers.NETHERITE;

public class AdAstraMaterials {

    public static final MaterialId calorite = MaterialBuilder.material("ad_astra", "calorite")
            .data(d -> d.tier(2).order(3).craftable(false))
            .traits(t -> t.trait(AdAstraInit.oxygenated))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(1840, 6f, NETHERITE, 4.5f),
                            HandleMaterialStats.multipliers().durability(0.85f).miningSpeed(1.25f).attackDamage(1.15f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(1840, 0.1f, 0.2f, 0.05f),
                            new GripMaterialStats(0f, -0.1f, 4.5f)
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(36).armor(4, 9, 7, 4).toughness(2.5f).knockbackResistance(0.1f),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            TJStats.plainRing(1840, 1.1f)
                    )
            )
            .renderInfo(r -> r.color(0xb83145).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).armor().meleeHarvest().ranged().sixColor(0xFF470d2f, 0xFF6a1534, 0xFF9e1f3f, 0xFFb83145, 0xFFcb4e4f, 0xFFdf6d5c))
            .buildMaterial();

    public static final MaterialId desh = MaterialBuilder.material("ad_astra", "desh")
            .data(d -> d.tier(2).order(3).craftable(false))
            .traits(t -> t.trait(AdAstraInit.oxygenated))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(940, 4f, DIAMOND, 2.6f),
                            HandleMaterialStats.multipliers().durability(1.15f).miningSpeed(1.15f).attackDamage(0.95f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(940, 0.15f, 0.09f, 0.03f),
                            new GripMaterialStats(-0.15f, 0.06f, 2.6f)
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(35).armor(4, 9, 7, 4).toughness(2f),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            TJStats.plainRing(940, 1f)
                    )
            )

            .renderInfo(r -> r.color(0xc77142).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).armor().meleeHarvest().ranged().sixColor(0xFF7a3045, 0xFF9e4539, 0xFFb25b3d, 0xFFc77142, 0xFFd68d4d, 0xFFe9ba5d))
            .buildMaterial();

    public static final MaterialId ostrum = MaterialBuilder.material("ad_astra", "ostrum")
            .data(d -> d.tier(2).order(3).craftable(false))
            .traits(t -> t.trait(AdAstraInit.oxygenated))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(1250, 5f, DIAMOND, 3.8f),
                            HandleMaterialStats.multipliers().durability(0.7f).miningSpeed(1.05f).attackDamage(1.05f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(1250, 0.1f, 0.13f, -0.05f),
                            new GripMaterialStats(0.05f, 0.35f, 3.8f)
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(37).armor(4, 9, 7, 4).toughness(3f).knockbackResistance(0.1f),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            TJStats.plainRing(1250, 1f)
                    )
            )
            .renderInfo(r -> r.color(0x925e64).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).armor().meleeHarvest().ranged().sixColor(0xFF42313d, 0xFF553e4b, 0xFF775360, 0xFF925e64, 0xFFa86c73, 0xFFbf7a82))
            .buildMaterial();

    public static void staticInit() {}
}
