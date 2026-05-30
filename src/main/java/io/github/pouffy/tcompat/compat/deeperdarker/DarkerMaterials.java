package io.github.pouffy.tcompat.compat.deeperdarker;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.compat.CompatToolStats;
import io.github.pouffy.tcompat.compat.constructs_casting.MagicBaseMaterialStats;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.stats.*;

import static net.minecraft.world.item.Tiers.IRON;

public class DarkerMaterials {

    public static final MaterialId sculkBone = MaterialBuilder.material("deeperdarker", "sculk_bone")
            .data(d -> d.tier(1).order(2).craftable(true))
            .traits(t -> t
                    .trait(TCModifiers.heartbeat)
                    .trait(CompatToolStats.Statless.CUT_GEM.getIdentifier(), TCModifiers.darkness_gem)
                    .trait(MagicBaseMaterialStats.ID, TCModifiers.eldritchUpgrade)
            )
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(200, 2.5f, IRON, 1.2f),
                            HandleMaterialStats.multipliers().durability(1.05f).attackSpeed(1.25f).build(),
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(200, 0.07f, -0.07f, 0.07f),
                            new GripMaterialStats(1.0f, 0.15f, 1.5f), StatlessMaterialStats.ARROW_SHAFT
                    ).statOptional(
                            CompatToolStats.Statless.CUT_GEM,
                            CompatToolStats.magicBase(90, -0.05f)
                    )
            )
            .renderInfo(r -> r.color(0xc6cda7).fallbacks("bone", "rock"))
            .spriteInfo(s -> s.meleeHarvest().statType(CompatToolStats.Statless.CUT_GEM.getIdentifier()).statType(MagicBaseMaterialStats.ID).arrowShaft().ranged().fallbacks("bone", "rock").sixColor(0xFF6e757b, 0xFF819988, 0xFFa2af86, 0xFFc6cda7, 0xFFdbe0c1, 0xFFecefda))
            .buildMaterial();

    public static void staticInit() {}
}
