package io.github.pouffy.tcompat.compat.betterend;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.compat.CompatToolStats;
import io.github.pouffy.tcompat.compat.tinkersjewelry.PlainRingMaterialStats;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import slimeknights.tconstruct.tools.stats.*;

import static net.minecraft.world.item.Tiers.*;

public class BetterendMaterials {

    public static final MaterialId thallasium = MaterialBuilder.material("betterend", "thallasium")
            .flavor("Some people have a phobia of it.")
            .data(d -> d.tier(2).order(0).craftable(false))
            .traits(t -> t.trait(BetterendInit.voidTouched).trait(PlainRingMaterialStats.ID, new ModifierEntry[] {}))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(321, 7f, IRON, 2.5f),
                            HandleMaterialStats.multipliers().attackDamage(1.10f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(321, -0.1f, 0.25f, 0),
                            new GripMaterialStats(0.15f, 0f, 1.1f)
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(15).armor(1, 4, 5, 2),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.plainRing(321, 1.1f)
                    )
            )
            .renderInfo(r -> r.color(0x7ad0d3).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).armor().meleeHarvest().ranged().sixColor(0xFF378099, 0xFF449aaf, 0xFF54b0bf, 0xFF7ad0d3, 0xFFa4e8da, 0xFFcff9e2))
            .buildMaterial();

    public static final MaterialId terminite = MaterialBuilder.material("betterend", "terminite")
            .flavor("Special iron.")
            .data(d -> d.tier(2).order(0).craftable(false)).excludeFromLoot()
            .traits(t -> t.trait(BetterendInit.voidTouched).trait(PlainRingMaterialStats.ID, new ModifierEntry[] {}))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(1231, 8.5f, DIAMOND, 3.0f),
                            HandleMaterialStats.multipliers().durability(1.20f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(1231, -0.2f, 0f, 0.05f),
                            new GripMaterialStats(0.2f, -0.1f, 1.1f)
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(33).armor(3, 6, 7, 3).toughness(1f).knockbackResistance(0.05f),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.plainRing(1231, 1.2f)
                    )
            )
            .renderInfo(r -> r.color(0x71f3e8).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).armor().meleeHarvest().ranged().sixColor(0xFF01625b, 0xFF00a094, 0xFF46d5ca, 0xFF71f3e8, 0xFFa1f8f0, 0xFFebfefc))
            .buildMaterial();

    public static final MaterialId aeternium = MaterialBuilder.material("betterend", "aeternium")
            .flavor("The netherite of the void.")
            .data(d -> d.tier(4).order(0).craftable(false)).excludeFromLoot()
            .traits(t -> t.trait(BetterendInit.voidTouched).trait(PlainRingMaterialStats.ID, new ModifierEntry[] {}))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(2197, 10.0f, NETHERITE, 4.0f),
                            HandleMaterialStats.multipliers().attackSpeed(0.8f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(2197, 0f, -0.05f, 0.1f),
                            new GripMaterialStats(0f, 0.1f, 1.1f)
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(40).armor(4, 7, 9, 4).toughness(3.5f).knockbackResistance(0.2f),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.plainRing(2197, 1.3f)
                    )
            )
            .renderInfo(r -> r.color(0x6d8883).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).armor().meleeHarvest().ranged().sixColor(0xFF0c100f, 0xFF282f2d, 0xFF495e5a, 0xFF697876, 0xFF8d9a96, 0xFFbdcbc9))
            .buildMaterial();

    public static final MaterialVariantId twistedVine = MaterialBuilder.variant("betterend", "twisted_vine", MaterialIds.twistingVine)
            .renderInfo(r -> r.color(0xFF4b4b4b).fallbacks("primitive", "cloth"))
            .spriteInfo(s -> s.vines().sixColor(0xFF101010, 0xFF2b2b2b, 0xFF323232, 0xFF4b4b4b, 0xFF5e5e5e, 0xFF6b6b6b))
            .buildVariant();

    public static final MaterialVariantId jungleVine = MaterialBuilder.variant("betterend", "jungle_vine", MaterialIds.twistingVine)
            .renderInfo(r -> r.color(0xFF238278).fallbacks("primitive", "cloth"))
            .spriteInfo(s -> s.vines().sixColor(0xFF174c60, 0xFF195a6c, 0xFF1f6c7f, 0xFF238278, 0xFF34998b, 0xFF49aa8f))
            .buildVariant();

    public static final MaterialId bulbVine = MaterialBuilder.material("betterend", "bulb_vine")
            .flavor("You're positively glowing!")
            .data(d -> d.tier(4).order(0).craftable(true))
            .traits(t -> t.trait(ModifierIds.glowing))
            .stats(s -> s.stat(StatlessMaterialStats.BINDING, StatlessMaterialStats.BOWSTRING, StatlessMaterialStats.MAILLE))
            .renderInfo(r -> r.color(0xFF9b3b81).fallbacks("primitive", "cloth"))
            .spriteInfo(s -> s.vines().sixColor(0xFF5d2c54, 0xFF64335b, 0xFF6c3b63, 0xFF9b3b81, 0xFFb24e8e, 0xFFc55a9f))
            .buildMaterial();

    public static final MaterialId denseVine = MaterialBuilder.material("betterend", "dense_vine")
            .flavor("Are you being intentionally dense?")
            .data(d -> d.tier(4).order(0).craftable(true))
            .traits(t -> t.trait(ModifierIds.dense))
            .stats(s -> s.stat(StatlessMaterialStats.BINDING, StatlessMaterialStats.BOWSTRING, StatlessMaterialStats.MAILLE))
            .renderInfo(r -> r.color(0xFF26bbb1).fallbacks("primitive", "cloth"))
            .spriteInfo(s -> s.vines().sixColor(0xFF0c5453, 0xFF0c7271, 0xFF118c8a, 0xFF26bbb1, 0xFF58e2cf, 0xFF7ff0dd))
            .buildMaterial();

    public static void staticInit() {}
}
