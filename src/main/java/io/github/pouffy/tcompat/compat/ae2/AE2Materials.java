package io.github.pouffy.tcompat.compat.ae2;

import io.github.pouffy.tcompat.common.material.MaterialCreator;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import static net.minecraft.world.item.Tiers.IRON;

public class AE2Materials {

    public static final MaterialId certusQuartz = MaterialCreator.material("ae2", "certus_quartz")
            .flavor("Not to be confused with regular quartz.")
            .data(d -> d.tier(2).order(25).craftable(true))
            .stats(s -> s.stat(new HeadMaterialStats(250, 6f, IRON, 2f)))
            .renderInfo(r -> r.color(0x93c7ff).fallbacks("crystal", "rock"))
            .spriteInfo(s -> s.fallbacks("crystal", "rock").head().sixColor(0xFF476682, 0xFF678bab, 0xFF7da2c4, 0xFF93c7ff, 0xFFbadbff, 0xFFf1feff))
            .buildMaterial();

    public static final MaterialId fluix = MaterialCreator.material("ae2", "fluix")
            .flavor("Charged with possibility.")
            .data(d -> d.tier(2).order(25).craftable(true))
            .traits(t -> t.trait(MaterialRegistry.MELEE_HARVEST, ModifierIds.luck))
            .stats(s -> s.stat(new HeadMaterialStats(750, 7.2f, IRON, 2.4f)))
            .renderInfo(r -> r.color(0x915dcd).fallbacks("crystal", "rock"))
            .spriteInfo(s -> s.fallbacks("crystal", "rock").head().sevenColor(0xFF25305b, 0xFF4e3c95, 0xFF614eab, 0xFF915dcd, 0xFFa470e0, 0xFFc090f7, 0xFFdbbcff))
            .buildMaterial();

    public static void staticInit() {}
}
