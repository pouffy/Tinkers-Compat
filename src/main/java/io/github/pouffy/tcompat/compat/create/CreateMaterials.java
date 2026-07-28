package io.github.pouffy.tcompat.compat.create;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.compat.CompatToolStats;
import io.github.pouffy.tcompat.compat.tinkersjewelry.PlainRingMaterialStats;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.stats.*;

import static net.minecraft.world.item.Tiers.DIAMOND;


public class CreateMaterials {

    public static final MaterialId brass = MaterialBuilder.material("create", "brass")
            .flavor("Smart alloys")
            .data(d -> d.tier(2).order(1).craftable(false))
            .traits(t -> t
                    .trait(CreateInit.clockwork)
                    .trait(PlainRingMaterialStats.ID, TCModifiers.armorToughness_gem)
            )
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(730, 6F, DIAMOND, 2.25F),
                            HandleMaterialStats.multipliers().durability(1.05F).miningSpeed(1.15F).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(730, -0.2f, 0.15f, -0.2f),
                            new GripMaterialStats(0.1f, 0f, 2.25f)
                    ).statOptional(
                            CompatToolStats.plainRing(730, 0.9f)
                    )
            )
            .renderInfo(r -> r.color(0xfbcc68).fallbacks("metal"))
            .spriteInfo(s -> s.fallbacks("metal").repairKit().statType(PlainRingMaterialStats.ID).meleeHarvest().ranged().sixColor(0xFF6c3124, 0xFF87472d, 0xFFc47545, 0xFFfbcc68, 0xFFffeb94, 0xFFfff9c7))
            .buildMaterial();

    public static void staticInit() {}
}
