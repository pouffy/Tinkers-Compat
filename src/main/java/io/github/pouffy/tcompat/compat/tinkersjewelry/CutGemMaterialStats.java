package io.github.pouffy.tcompat.compat.tinkersjewelry;

import io.github.pouffy.tcompat.TCompat;
import net.minecraft.network.chat.Component;
import slimeknights.mantle.data.loadable.record.SingletonLoader;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;

public record CutGemMaterialStats() implements IMaterialStats {

    public static final MaterialStatsId ID = new MaterialStatsId(TCompat.getResource("tinkersjewelry", "gem"));
    public static final CutGemMaterialStats INSTANCE = new CutGemMaterialStats();
    public static final MaterialStatType<CutGemMaterialStats> TYPE = new MaterialStatType<>(ID, INSTANCE, new SingletonLoader<>(INSTANCE));

    @Override
    public MaterialStatType<?> getType() {
        return TYPE;
    }

    @Override
    public MaterialStatsId getIdentifier() {
        return ID;
    }

    @Override
    public List<Component> getLocalizedInfo() {
        return List.of();
    }

    @Override
    public List<Component> getLocalizedDescriptions() {
        return List.of();
    }

    @Override
    public void apply(ModifierStatsBuilder modifierStatsBuilder, float scale) {}
}
