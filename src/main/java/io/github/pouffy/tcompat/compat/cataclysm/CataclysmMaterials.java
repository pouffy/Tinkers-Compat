package io.github.pouffy.tcompat.compat.cataclysm;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.compat.CompatToolStats;
import io.github.pouffy.tcompat.compat.tinkersjewelry.PlainRingMaterialStats;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.stats.*;

import static io.github.pouffy.tcompat.datagen.tinkers.material.TCMaterialSpriteProv.complexTransformer;
import static net.minecraft.world.item.Tiers.DIAMOND;
import static net.minecraft.world.item.Tiers.NETHERITE;
import static slimeknights.tconstruct.library.materials.MaterialRegistry.ARMOR;

public class CataclysmMaterials {

    public static final MaterialId ancientMetal = MaterialBuilder.material("cataclysm", "ancient_metal")
            .data(d -> d.tier(4).order(1).craftable(false))
            .traits(t -> t
                    .trait(HeadMaterialStats.ID, TCModifiers.cataclysmic, CataclysmInit.sandstorm)
                    .trait(ARMOR, TCModifiers.cataclysmic)
                    .trait(PlainRingMaterialStats.ID, new ModifierEntry(TCModifiers.precious, 1))
            )
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(750, 8.0F, DIAMOND, 2.0F),
                            HandleMaterialStats.multipliers().durability(1.25f).build(),
                            StatlessMaterialStats.BINDING
                    ).armorStats(
                            PlatingMaterialStats.builder().durabilityFactor(35).armor(6, 11, 7, 4).toughness(3.0F).knockbackResistance(0.2F),
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.plainRing(750, 1.4f)
                    )
            )
            .renderInfo(r -> r.color(0xb98a3c).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).armor().meleeHarvest().sixColor(0xFF4c1e15, 0xFF682e22, 0xFF945b31, 0xFFb98a3c, 0xFFdbb86a, 0xFFffe69a))
            .buildMaterial();

    public static final MaterialId blackSteel = MaterialBuilder.material("cataclysm", "black_steel")
            .data(d -> d.tier(3).order(1).craftable(false))
            .traits(t -> t
                    .trait(ModifierIds.ductile)
                    .trait(PlainRingMaterialStats.ID, new ModifierEntry(TCModifiers.ductile, 1))
            )
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(750, 8.0F, DIAMOND, 2.0F),
                            HandleMaterialStats.multipliers().durability(0.89f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(750, 0.1f, 0, 0.05f),
                            new GripMaterialStats(0.15f, 0.1f, 2.0F),
                            PlatingMaterialStats.builder().shieldDurability(840).buildShield()
                    ).statOptional(
                            CompatToolStats.plainRing(750, 1.1f)
                    )
            )
            .renderInfo(r -> r.color(0x485063).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).statType(PlatingMaterialStats.SHIELD.getId()).meleeHarvest().ranged().sixColor(0xFF171a1e, 0xFF1f232c, 0xFF2d313d, 0xFF485063, 0xFF616879, 0xFF969cad))
            .buildMaterial();

    public static final MaterialId cursium = MaterialBuilder.material("cataclysm", "cursium")
            .data(d -> d.tier(4).order(1).craftable(false))
            .traits(t -> t
                    .trait(StatlessMaterialStats.MAILLE.getIdentifier(), TCModifiers.cataclysmic, TCModifiers.ghostly)
                    .trait(StatlessMaterialStats.BOWSTRING.getIdentifier(), CataclysmInit.phantasmic)
                    .trait(CompatToolStats.Statless.CUT_GEM.getIdentifier(), new ModifierEntry(TCModifiers.glowing_gem, 1))
            )
            .stats(s ->
                    s.stat(
                            StatlessMaterialStats.BOWSTRING,
                            StatlessMaterialStats.MAILLE
                    ).statOptional(
                            CompatToolStats.Statless.CUT_GEM
                    )
            )
            .renderInfo(r -> r.color(0x39d2b2).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(CompatToolStats.Statless.CUT_GEM.getIdentifier()).maille().bowstring().head().sixColor(0xFF0e4b3d, 0xFF156353, 0xFF198871, 0xFF39d2b2, 0xFF56eccc, 0xFF75ffe2))
            .buildMaterial();

    public static final MaterialId lacrima = MaterialBuilder.material("cataclysm", "lacrima")
            .data(d -> d.tier(4).order(1).craftable(true))
            .traits(t -> t
                    .trait(HeadMaterialStats.ID, TCModifiers.cataclysmic, CataclysmInit.tidal)
                    .trait(CompatToolStats.Statless.CUT_GEM.getIdentifier(), new ModifierEntry(TCModifiers.swim_gem, 1))
                    .trait(CompatToolStats.Statless.ADORNMENT.getIdentifier(), TCModifiers.iceUpgrade, TCModifiers.lightningUpgrade)
            )
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(2800, 9.0F, NETHERITE, 2.0F),
                            HandleMaterialStats.multipliers().durability(1.1f).build()
                    ).statOptional(
                            CompatToolStats.Statless.CUT_GEM,
                            CompatToolStats.Statless.ADORNMENT
                    )
            )
            .renderInfo(r -> r.color(0x7eb5d8).fallbacks("gem"))
            .spriteInfo(s -> s.fallbacks("gem").repairKit().statType(CompatToolStats.Statless.CUT_GEM.getIdentifier()).statType(CompatToolStats.Statless.ADORNMENT.getIdentifier()).handle().head().sixColor(0xFF443c86, 0xFF565d95, 0xFF6071c1, 0xFF7eb5d8, 0xFFa4dcea, 0xFFd9f4f7))
            .buildMaterial();

    public static final MaterialId essenceOfTheStorm = MaterialBuilder.material("cataclysm", "essence_of_the_storm")
            .data(d -> d.tier(4).order(1).craftable(true))
            .lang("Essence of the Storm")
            .traits(t -> t.trait(StatlessMaterialStats.BINDING, ModifierIds.deciduous))
            .stats(s -> s.stat(StatlessMaterialStats.BINDING))
            .renderInfo(r -> r.color(0x67cbd2).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().binding().sixColor(0xFF2f7598, 0xFF3d86ab, 0xFF57a9c2, 0xFF67cbd2, 0xFF78dfda, 0xFFc1f1ea))
            .buildMaterial();

    public static final MaterialId witherite = MaterialBuilder.material("cataclysm", "witherite")
            .data(d -> d.tier(4).order(1).craftable(false))
            .traits(t -> t.trait(GripMaterialStats.ID, CataclysmInit.fluxed).trait(HandleMaterialStats.ID, TCModifiers.standstill))
            .stats(s ->
                    s.stat(
                            HandleMaterialStats.multipliers().durability(1.4f).build(), StatlessMaterialStats.BINDING,
                            new GripMaterialStats(0.14f, 0.05f, 2.0F)
                    ).statOptional(
                            CompatToolStats.plainRing(2800, 1.4f)
                    )
            )
            .renderInfo(r -> r.color(0x6e7786).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).statType(GripMaterialStats.ID).handle().binding().transformer(complexTransformer("witherite", 1, 0xFF5e6777, 0xFF6e7786)))
            .buildMaterial();

    public static final MaterialId coral = MaterialBuilder.material("cataclysm", "coral")
            .data(d -> d.tier(4).order(1).craftable(true))
            .traits(t -> t
                    .trait(HandleMaterialStats.ID, CataclysmInit.aquatic)
                    .trait(CompatToolStats.Statless.CUT_GEM.getIdentifier(), new ModifierEntry(TCModifiers.swim_gem, 2))
            )
            .stats(s ->
                    s.stat(
                            HandleMaterialStats.multipliers().attackSpeed(0.9f).build()
                    ).statOptional(
                            CompatToolStats.Statless.CUT_GEM
                    )
            )
            .renderInfo(r -> r.color(0xa4222f).fallbacks("rock"))
            .spriteInfo(s -> s.fallbacks("rock").repairKit().statType(CompatToolStats.Statless.CUT_GEM.getIdentifier()).handle().sixColor(0xFF490911, 0xFF791a26, 0xFF8c1d2a, 0xFFa4222f, 0xFFc62a37, 0xFFe23f36))
            .buildMaterial();

    public static final MaterialId voidJaw = MaterialBuilder.material("cataclysm", "void_jaw")
            .data(d -> d.tier(4).order(1).craftable(true))
            .traits(t -> t.trait(StatlessMaterialStats.ARROW_HEAD.getIdentifier(), CataclysmInit.voidScatter))
            .stats(s ->
                    s.stat(StatlessMaterialStats.ARROW_HEAD)
            )
            .renderInfo(r -> r.color(0x8219ff).fallbacks("bone"))
            .spriteInfo(s -> s.fallbacks("bone").repairKit().arrowHead().sixColor(0xFF340073, 0xFF4d00a9, 0xFF5700bf, 0xFF8219ff, 0xFF9842ff, 0xFFa153fe))
            .buildMaterial();

    public static final MaterialId koboletonBone = MaterialBuilder.material("cataclysm", "koboleton_bone")
            .data(d -> d.tier(4).order(1).craftable(true))
            .traits(t -> t.trait(HandleMaterialStats.ID, TCModifiers.archaeologist))
            .stats(s ->
                    s.stat(StatlessMaterialStats.MAILLE, HandleMaterialStats.multipliers().attackDamage(1.2f).build())
            )
            .renderInfo(r -> r.color(0x7e675e).fallbacks("bone", "metal"))
            .spriteInfo(s -> s.fallbacks("bone", "metal").repairKit().maille().handle().sixColor(0xFF200e07, 0xFF3e291b, 0xFF5f473f, 0xFF7e675e, 0xFF9a887c, 0xFFdbcca7))
            .buildMaterial();

    //TODO:
    //  Materials:
    //   - Cursium [Head, Bowstring] x
    //   - Essence of the Storm [Binding] x
    //   - Lacrima [Handle, Head] x
    //   - Witherite [Handle, Binding] x
    //   - Coral Chunk [Handle] x
    //   - Void Jaw [Arrow Head] x
    //   - Koboleton Bone [Maille, Handle] x
    //  Traits:
    //   - Phantasmic [Cursium, Black Steel (Team Up)] x
    //   - Tidal [Essence of the Storm, Lacrima (Team Up)] x
    //   - Fluxed [Witherite, Iron (Team Up)] x
    //   - Void Scatter [Void Jaw] x
    //   - Archaeologist [Koboleton Bone] x
    //  Modifiers:
    //   - Ignited [Armor, Req. Netherite]
    //  Recipes:
    //   - Melting
    //   - Misc Casting
    //   - Modifiers


    public static void staticInit() {}
}
