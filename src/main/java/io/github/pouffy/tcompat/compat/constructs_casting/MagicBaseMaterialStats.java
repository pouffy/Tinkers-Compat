package io.github.pouffy.tcompat.compat.constructs_casting;

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

public record MagicBaseMaterialStats(float maxMana, float spellPower) implements IRepairableMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(TCompat.getResource("constructs_casting", "magic_base"));
    public static final MaterialStatType<MagicBaseMaterialStats> TYPE = new MaterialStatType<>(ID, new MagicBaseMaterialStats(0, 0), RecordLoadable.create(
            FloatLoadable.ANY.defaultField("max_mana", 0f, true, MagicBaseMaterialStats::maxMana),
            FloatLoadable.ANY.defaultField("spell_power", 0f, true, MagicBaseMaterialStats::spellPower),
            MagicBaseMaterialStats::new
    ));

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

    @Override
    public int durability() {
        return 0;
    }
}
