package io.github.pouffy.tcompat.compat.aether_redux;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCRocks;
import io.github.pouffy.tcompat.common.material.TCWoods;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.compat.CompatToolStats;
import io.github.pouffy.tcompat.compat.tinkersjewelry.PlainRingMaterialStats;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.tools.stats.*;

import static io.github.pouffy.tcompat.compat.aether.AetherMaterials.aetherRockVariant;
import static io.github.pouffy.tcompat.compat.aether.AetherMaterials.aetherWoodVariant;
import static net.minecraft.world.item.Tiers.DIAMOND;
import static net.minecraft.world.item.Tiers.IRON;

public class AetherReduxMaterials {

    public static final MaterialVariantId cloudcap = aetherWoodVariant(TCWoods.CLOUDCAP, 0xFF191A2B, 0xFF212338, 0xFF292C45, 0xFF323654, 0xFF3B3F60, 0xFF444A72, 0xFF4B537D);
    public static final MaterialVariantId jellyshroom = aetherWoodVariant(TCWoods.JELLYSHROOM, 0xFF373E4F, 0xFF414959, 0xFF4B5364, 0xFF555D6B, 0xFF646B76, 0xFF737982, 0xFF81868E);
    public static final MaterialVariantId blightwillow = aetherWoodVariant(TCWoods.BLIGHTWILLOW, 0xFF4B694E, 0xFF5E805C, 0xFF6C8C63, 0xFF7F9F74, 0xFF8FAC82, 0xFF9FBA90, 0xFFA8C198);
    public static final MaterialVariantId fieldsproot = aetherWoodVariant(TCWoods.FIELDSPROOT, 0xFF824241, 0xFF9A514E, 0xFFAF615B, 0xFFBF7069, 0xFFC97B72, 0xFFD4847B, 0xFFDB8B82);
    public static final MaterialVariantId crystal = aetherWoodVariant(TCWoods.CRYSTAL, 0xFF50607A, 0xFF5F728E, 0xFF6D84A3, 0xFF788FAB, 0xFF839CBA, 0xFF8DA6C1, 0xFF95AFC6);
    public static final MaterialVariantId glacia = aetherWoodVariant(TCWoods.GLACIA, 0xFF524D45, 0xFF645F54, 0xFF787365, 0xFF848072, 0xFF938F7E, 0xFF9E9A89, 0xFFA7A18F);

    public static final MaterialVariantId divinite = aetherRockVariant(TCRocks.DIVINITE, 0xFF8B7149, 0xFF987E55, 0xFFAA8F65, 0xFFBAA170, 0xFFCDB285, 0xFFD8C699);
    public static final MaterialVariantId driftshale = aetherRockVariant(TCRocks.DRIFTSHALE, 0xFFAA9A5D, 0xFFBEB06C, 0xFFCEC277, 0xFFD9CE81, 0xFFDFDA95, 0xFFE3E2A8);

    public static final MaterialId veridium = MaterialBuilder.material("aether_redux", "veridium")
            .flavor("Very close friends with Ambrosium.")
            .data(d -> d.tier(2).order(1).craftable(false)).excludeFromLoot()
            .traits(t -> t.trait(TCModifiers.aetherForged, AetherReduxInit.ambrofusion))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(750, 2.25f, IRON, 3.5f),
                            HandleMaterialStats.multipliers().durability(0.85f).miningSpeed(0.95f).build(),
                            StatlessMaterialStats.BINDING
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(19).armor(4, 6, 7, 4).toughness(0.5f), StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.plainRing(750, 1.1f)
                    )
            )
            .renderInfo(r -> r.color(0x5a90bd).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).armor().meleeHarvest().sixColor(0xFF0e193b, 0xFF142958, 0xFF32578c, 0xFF5a90bd, 0xFF7fbedc, 0xFFb9edfb))
            .buildMaterial();

    public static final MaterialId refinedSentrite = MaterialBuilder.material("aether_redux", "refined_sentrite")
            .flavor("Also makes a nice set of bricks.")
            .data(d -> d.tier(2).order(1).craftable(false)).excludeFromLoot()
            .traits(t -> t.trait(TCModifiers.aetherForged))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(1126, 5f, DIAMOND, 3.5f),
                            new GripMaterialStats(0.02f, 0.15f, 3.5f)
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(26).armor(5.2f, 7.2f, 8.2f, 5.2f).knockbackResistance(1).toughness(0.8f), StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.plainRing(1126, 1.2f)
                    )
            )
            .renderInfo(r -> r.color(0x5c5c61).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).statType(GripMaterialStats.ID, HeadMaterialStats.ID).armor().sixColor(0xFF27272b, 0xFF434346, 0xFF5c5c61, 0xFF747477, 0xFF98999b, 0xFFc1c1c1))
            .buildMaterial();

    public static final MaterialId blightbunnyFang = MaterialBuilder.material("deep_aether", "blightbunny_fang")
            .flavor("Big pointy teeth.")
            .data(d -> d.tier(1).order(4).craftable(true)).excludeFromLoot()
            .traits(t -> t
                    .trait(AetherReduxInit.blighted)
                    .trait(CompatToolStats.Statless.CUT_GEM.getIdentifier(), new ModifierEntry(TCModifiers.poison_gem, 3))
            )
            .stats(s ->
                    s.stat(StatlessMaterialStats.ARROW_HEAD).statOptional(CompatToolStats.Statless.CUT_GEM)
            )
            .renderInfo(r -> r.color(0xc1d3d8).fallbacks("bone", "metal"))
            .spriteInfo(s -> s.fallbacks("bone", "metal").repairKit().statType(CompatToolStats.Statless.CUT_GEM.getIdentifier()).arrowHead().sixColor(0xFF5a6972, 0xFF6e7e88, 0xFF7f919b, 0xFFa1b5be, 0xFFc1d3d8, 0xFFdeeef2))
            .buildMaterial();

    public static final MaterialId mykapodShell = MaterialBuilder.material("deep_aether", "mykapod_shell")
            .flavor("Stoppable force meets immovable shield.")
            .data(d -> d.tier(1).order(2).craftable(true)).excludeFromLoot()
            .traits(t -> t
                    .trait(StatlessMaterialStats.SHIELD_CORE.getIdentifier(), TCModifiers.escarstay)
                    .trait(CompatToolStats.Statless.CUT_GEM.getIdentifier(), new ModifierEntry(TCModifiers.armor_gem, 4))
            )
            .stats(s ->
                    s.stat(StatlessMaterialStats.SHIELD_CORE).statOptional(CompatToolStats.Statless.CUT_GEM)
            )
            .renderInfo(r -> r.color(0x5e65a5).fallbacks("bone", "metal"))
            .spriteInfo(s -> s.fallbacks("bone", "metal").repairKit().statType(CompatToolStats.Statless.CUT_GEM.getIdentifier()).shieldCore().sixColor(0xFF2c2e5b, 0xFF3a3d6f, 0xFF4b5187, 0xFF5e65a5, 0xFF7178c2, 0xFF8f92d8))
            .buildMaterial();

    public static void staticInit() {}
}
