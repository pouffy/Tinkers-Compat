package io.github.pouffy.tcompat.compat;

import com.google.common.collect.Lists;
import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.compat.constructs_casting.MagicBaseMaterialStats;
import io.github.pouffy.tcompat.compat.constructs_casting.MagicClothMaterialStats;
import io.github.pouffy.tcompat.compat.tinkersjewelry.PlainRingMaterialStats;
import net.minecraft.network.chat.Component;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;

import java.util.List;

public class CompatToolStats {

    public enum Statless implements IMaterialStats {
        CUT_GEM("tinkersjewelry", "gem"),
        ADORNMENT("constructs_casting", "adornment"),
        ;

        private final MaterialStatType<Statless> type;

        Statless(String modid, String name) {
            this.type = MaterialStatType.singleton(new MaterialStatsId(TCompat.getResource(modid, name)), this);
        }

        @Override
        public MaterialStatType<?> getType() {
            return type;
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
        public void apply(ModifierStatsBuilder builder, float scale) {}
    }

    public static PlainRingMaterialStats plainRing(int durability, float amplification) {
        return new PlainRingMaterialStats(durability, amplification);
    }

    public static MagicBaseMaterialStats magicBase(float maxMana, float spellPower) {
        return new MagicBaseMaterialStats(maxMana, spellPower);
    }

    public static MagicClothMaterialStats magicCloth(float spellSlots, float cooldownReduction) {
        return new MagicClothMaterialStats(spellSlots, cooldownReduction);
    }

    //Don't register these
    public static final FloatToolStat MAX_MANA = new FloatToolStat(new ToolStatId("constructs_casting", "max_mana"), 0xFF55FFFF, 0, -50, 5000);
    public static final FloatToolStat SPELL_SLOTS = new FloatToolStat(new ToolStatId("constructs_casting", "spell_slots"), 0xFFd6be96, 0, 0, 15f);
    public static final FloatToolStat SPELL_POWER = new FloatToolStat(new ToolStatId("constructs_casting", "spell_power"), 0xFF5555FF, 0, -2048f, 2048f);
    public static final FloatToolStat COOLDOWN_REDUCTION = new FloatToolStat(new ToolStatId("constructs_casting", "cooldown_reduction"), 0xffe8bfcf, 0, -2048f, 2048f);
}
