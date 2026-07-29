package io.github.pouffy.tcompat.compat.aether.item;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import io.github.pouffy.tcompat.TCompat;
import net.minecraft.network.chat.Component;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public record LipGuardMaterialStats(float durability, float drawSpeed, float velocity) implements IMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(TCompat.getResource("lip_guard"));
    public static final MaterialStatType<LipGuardMaterialStats> TYPE = new MaterialStatType<>(ID, new LipGuardMaterialStats(0.0F, 0.0F, 0.0F), RecordLoadable.create(
            FloatLoadable.ANY.defaultField("durability", 0.0F, true, LipGuardMaterialStats::durability),
            FloatLoadable.ANY.defaultField("draw_speed", 0.0F, true, LipGuardMaterialStats::drawSpeed),
            FloatLoadable.ANY.defaultField("velocity", 0.0F, true, LipGuardMaterialStats::velocity),
            LipGuardMaterialStats::new));
    private static final String DURABILITY_PREFIX = IMaterialStats.makeTooltipKey(TConstruct.getResource("durability"));
    private static final String DRAW_SPEED_PREFIX = IMaterialStats.makeTooltipKey(TConstruct.getResource("draw_speed"));
    private static final String VELOCITY_PREFIX = IMaterialStats.makeTooltipKey(TConstruct.getResource("velocity"));
    private static final List<Component> DESCRIPTION = ImmutableList.of(IMaterialStats.makeTooltip(TConstruct.getResource("handle.durability.description")), ToolStats.DRAW_SPEED.getDescription(), ToolStats.VELOCITY.getDescription());

    @Override
    public MaterialStatType<?> getType() {
        return TYPE;
    }

    public List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(IToolStat.formatColoredPercentBoost(DURABILITY_PREFIX, this.durability));
        info.add(IToolStat.formatColoredBonus(DRAW_SPEED_PREFIX, this.drawSpeed));
        info.add(IToolStat.formatColoredBonus(VELOCITY_PREFIX, this.velocity));
        return info;
    }

    public List<Component> getLocalizedDescriptions() {
        return DESCRIPTION;
    }

    public void apply(ModifierStatsBuilder builder, float scale) {
        ToolStats.DURABILITY.percent(builder, this.durability * scale);
        ToolStats.DRAW_SPEED.add(builder, this.drawSpeed * scale);
        ToolStats.VELOCITY.add(builder, this.velocity * scale);
    }
}
