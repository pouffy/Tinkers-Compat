package io.github.pouffy.tcompat.compat.aether.item;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import io.github.pouffy.tcompat.TCompat;
import net.minecraft.network.chat.Component;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.IRepairableMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public record DartBarrelMaterialStats(int durability, float accuracy, float meleeDamage) implements IRepairableMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(TCompat.getResource("dart_barrel"));
    public static final MaterialStatType<DartBarrelMaterialStats> TYPE = new MaterialStatType<>(ID, new DartBarrelMaterialStats(1, 0.0F, 0.0F), RecordLoadable.create(
            IRepairableMaterialStats.DURABILITY_FIELD,
            FloatLoadable.ANY.defaultField("accuracy", 0.0F, true, DartBarrelMaterialStats::accuracy),
            FloatLoadable.FROM_ZERO.defaultField("melee_damage", 0.0F, true, DartBarrelMaterialStats::meleeDamage),
            DartBarrelMaterialStats::new));
    private static final String ACCURACY_PREFIX = IMaterialStats.makeTooltipKey(TConstruct.getResource("accuracy"));
    private static final List<Component> DESCRIPTION = ImmutableList.of(ToolStats.DURABILITY.getDescription(), ToolStats.ACCURACY.getDescription(), ToolStats.ATTACK_DAMAGE.getDescription());

    public MaterialStatType<?> getType() {
        return TYPE;
    }

    public List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(ToolStats.DURABILITY.formatValue((float)this.durability));
        info.add(IToolStat.formatColoredBonus(ACCURACY_PREFIX, this.accuracy));
        info.add(ToolStats.ATTACK_DAMAGE.formatValue(this.meleeDamage));
        return info;
    }

    public List<Component> getLocalizedDescriptions() {
        return DESCRIPTION;
    }

    public void apply(ModifierStatsBuilder builder, float scale) {
        ToolStats.DURABILITY.update(builder, this.durability * scale);
        ToolStats.ACCURACY.add(builder, this.accuracy * scale);
        ToolStats.ATTACK_DAMAGE.update(builder, this.meleeDamage * scale);
    }
}
