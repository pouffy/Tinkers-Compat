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
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

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
}
