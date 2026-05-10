package io.github.pouffy.tcompat.compat.cataclysm;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.stats.*;

import static net.minecraft.world.item.Tiers.DIAMOND;
import static net.minecraft.world.item.Tiers.NETHERITE;

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
            .traits(t -> t.trait(ModifierIds.ductile))
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

    public static final MaterialId cursium = MaterialBuilder.material("cataclysm", "cursium")
            .data(d -> d.tier(4).order(1).craftable(false))
            .traits(t -> t
                    .trait(HeadMaterialStats.ID, TCModifiers.cataclysmic)
                    .trait(StatlessMaterialStats.MAILLE.getIdentifier(), TCModifiers.cataclysmic)
            )
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(2800, 9.0F, NETHERITE, 2.0F),
                            StatlessMaterialStats.BOWSTRING, StatlessMaterialStats.MAILLE
                    )
            )
            .renderInfo(r -> r.color(0x39d2b2).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().maille().bowstring().head().sixColor(0xFF0e4b3d, 0xFF156353, 0xFF198871, 0xFF39d2b2, 0xFF56eccc, 0xFF75ffe2))
            .buildMaterial();

    public static final MaterialId lacrima = MaterialBuilder.material("cataclysm", "lacrima")
            .data(d -> d.tier(4).order(1).craftable(false))
            .traits(t -> t.trait(HeadMaterialStats.ID, TCModifiers.cataclysmic))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(2800, 9.0F, NETHERITE, 2.0F),
                            HandleMaterialStats.multipliers().durability(1.1f).build()
                    )
            )
            .renderInfo(r -> r.color(0x7eb5d8).fallbacks("gem"))
            .spriteInfo(s -> s.fallbacks("gem").repairKit().handle().head().sixColor(0xFF443c86, 0xFF565d95, 0xFF6071c1, 0xFF7eb5d8, 0xFFa4dcea, 0xFFd9f4f7))
            .buildMaterial();

    public static final MaterialId essenceOfTheStorm = MaterialBuilder.material("cataclysm", "essence_of_the_storm")
            .data(d -> d.tier(4).order(1).craftable(false))
            .lang("Essence of the Storm")
            //.traits(t -> t.trait(StatlessMaterialStats.BINDING, CataclysmInit.stormed))
            .stats(s -> s.stat(StatlessMaterialStats.BINDING))
            .renderInfo(r -> r.color(0x67cbd2).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().binding().sixColor(0xFF2f7598, 0xFF3d86ab, 0xFF57a9c2, 0xFF67cbd2, 0xFF78dfda, 0xFFc1f1ea))
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
