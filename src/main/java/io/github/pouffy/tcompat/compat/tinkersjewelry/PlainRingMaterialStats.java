package io.github.pouffy.tcompat.compat.tinkersjewelry;

import com.google.common.collect.Lists;
import io.github.pouffy.tcompat.TCompat;
import net.minecraft.network.chat.Component;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IRepairableMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;

public record PlainRingMaterialStats(int durability, float amplification) implements IRepairableMaterialStats {

    public static final MaterialStatsId ID = new MaterialStatsId(TCompat.getResource("tinkersjewelry", "plain_ring"));
    public static final MaterialStatType<PlainRingMaterialStats> TYPE = new MaterialStatType<>(ID, new PlainRingMaterialStats(100, 1f),
            RecordLoadable.create(IRepairableMaterialStats.DURABILITY_FIELD, FloatLoadable.FROM_ZERO.requiredField("amplification", PlainRingMaterialStats::amplification), PlainRingMaterialStats::new));

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
        return Lists.newArrayList();
    }

    @Override
    public List<Component> getLocalizedDescriptions() {
        return Lists.newArrayList();
    }

    @Override
    public void apply(ModifierStatsBuilder modifierStatsBuilder, float scale) {}
}
