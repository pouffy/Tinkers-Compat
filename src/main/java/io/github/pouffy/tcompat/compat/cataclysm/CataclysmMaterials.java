package io.github.pouffy.tcompat.compat.cataclysm;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.stats.*;

import static net.minecraft.world.item.Tiers.DIAMOND;

public class CataclysmMaterials {

    public static final MaterialId ancientMetal = MaterialBuilder.material("cataclysm", "ancient_metal")
            .data(d -> d.tier(4).order(1).craftable(false))
            .traits(t -> t.trait(HeadMaterialStats.ID, TCModifiers.cataclysmic))
            .stats(s ->
                    s.stat(
                                    new HeadMaterialStats(750, 8.0F, DIAMOND, 2.0F),
                                    HandleMaterialStats.multipliers().durability(1.25f).build(),
                                    StatlessMaterialStats.BINDING)
                            .armorStats(
                                    PlatingMaterialStats.builder().durabilityFactor(35).armor(6, 11, 7, 4).toughness(3.0F).knockbackResistance(0.2F),
                                    StatlessMaterialStats.MAILLE))
            .renderInfo(r -> r.color(0xb98a3c).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().armor().meleeHarvest().sixColor(0xFF4c1e15, 0xFF682e22, 0xFF945b31, 0xFFb98a3c, 0xFFdbb86a, 0xFFffe69a))
            .buildMaterial();

    public static final MaterialId blackSteel = MaterialBuilder.material("cataclysm", "black_steel")
            .data(d -> d.tier(3).order(1).craftable(false))
            .traits(t -> t)
            .stats(s ->
                    s.stat(
                                    new HeadMaterialStats(750, 8.0F, DIAMOND, 2.0F),
                                    HandleMaterialStats.multipliers().durability(0.89f).build(),
                                    StatlessMaterialStats.BINDING,
                                    new LimbMaterialStats(750, 0.1f, 0, 0.05f),
                                    new GripMaterialStats(0.15f, 0.1f, 2.0F)
                    ).stat(PlatingMaterialStats.builder().shieldDurability(840).buildShield()))
            .renderInfo(r -> r.color(0x485063).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlatingMaterialStats.SHIELD.getId()).meleeHarvest().ranged().sixColor(0xFF171a1e, 0xFF1f232c, 0xFF2d313d, 0xFF485063, 0xFF616879, 0xFF969cad))
            .buildMaterial();

    //TODO:
    //  Materials:
    //   - Cursium [Head, Bowstring]
    //   - Essence of the Storm [Binding]
    //   - Lacrima [Handle, Head]
    //   - Witherite [Handle, Binding]
    //   - Coral Chunk [Handle]
    //   - Void Jaw [Arrow Head]
    //   - Koboleton Bone [Maille]
    //  Traits:
    //   - Phantasmic [Cursium, Black Steel (Team Up)]
    //   - Stormed [Essence of the Storm, Lacrima (Team Up)]
    //   - Fluxed [Witherite, Iron (Team Up)]
    //   - Scatter [Void Jaw]
    //  Modifiers:
    //   - Cursed [Armor, Req. Netherite]
    //   - Ignited [Armor, Req. Netherite]


    public static void staticInit() {}
}
