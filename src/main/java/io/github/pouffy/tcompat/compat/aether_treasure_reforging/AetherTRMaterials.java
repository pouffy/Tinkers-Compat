package io.github.pouffy.tcompat.compat.aether_treasure_reforging;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.compat.CompatToolStats;
import io.github.pouffy.tcompat.compat.tinkersjewelry.PlainRingMaterialStats;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.stats.*;

import static io.github.pouffy.tcompat.TCompat.getResource;
import static io.github.pouffy.tcompat.datagen.tinkers.material.TCMaterialSpriteProv.transformerFromSprite;
import static net.minecraft.world.item.Tiers.DIAMOND;
import static slimeknights.tconstruct.library.materials.MaterialRegistry.*;

public class AetherTRMaterials {

    public static final MaterialId pyral = MaterialBuilder.material("aether_treasure_reforging", "pyral")
            .data(d -> d.tier(4).order(4).craftable(false))
            .traits(t -> t
                    .trait(TCModifiers.aetherForged)
                    .trait(ARMOR, ModifierIds.fireProtection)
                    .trait(RANGED, AetherTRInit.phoenixTouched)
            )
            .stats(s ->
                    s.stat(
                            new LimbMaterialStats(80, -0.1f, 0.1f, -0.15f),
                            new GripMaterialStats(-0.2f, -0.35f, 0)
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(33).armor(3, 6, 8, 3).toughness(2f), StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.plainRing(342, 1.3f)
                    )
            )
            .renderInfo(r -> r.color(0xffb326).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).armor().ranged().sixColor(0xFF6b130b, 0xFFb7420c, 0xFFd86a0b, 0xFFffb326, 0xFFfeda87, 0xFFffeec5))
            .buildMaterial();

    public static final MaterialId valkyrum = MaterialBuilder.material("aether_treasure_reforging", "valkyrum")
            .data(d -> d.tier(4).order(2).craftable(false))
            .traits(t -> t
                    .trait(TCModifiers.aetherForged)
                    .trait(MELEE_HARVEST, ModifierIds.reach)
                    .trait(ARMOR, ModifierIds.doubleJump)
            )
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(1562, 4f, DIAMOND, 3f)
                    ).armorShieldStats(
                            PlatingMaterialStats.builder().durabilityFactor(33).armor(3, 6, 8, 3).toughness(2f), StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.plainRing(1562, 1.3f)
                    )
            )
            .renderInfo(r -> r.color(0xd11e1e).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).armor().meleeHarvest().transformer(transformerFromSprite(getResource("generator/valkyrum"), 1, 0)))
            .buildMaterial();

    public static final MaterialId neptune = MaterialBuilder.material("aether_treasure_reforging", "neptune")
            .data(d -> d.tier(2).order(3).craftable(false))
            .traits(t -> t.trait(TCModifiers.aetherForged).trait(ARMOR, ModifierIds.depthStrider))
            .stats(s ->
                    s.stat(StatlessMaterialStats.MAILLE)
            )
            .renderInfo(r -> r.color(0x3559c6).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().maille().sixColor(0xFF111b42, 0xFF1a2a66, 0xFF29439c, 0xFF3559c6, 0xFF3e6fd8, 0xFF7cbbff))
            .buildMaterial();

    public static void staticInit() {}
}
