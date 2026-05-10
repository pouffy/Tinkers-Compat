package io.github.pouffy.tcompat.compat.tinkersjewelry;

public class TJStats {

    public static CutGemMaterialStats CUT_GEM = CutGemMaterialStats.INSTANCE;

    public static PlainRingMaterialStats plainRing(int durability, float amplification) {
        return new PlainRingMaterialStats(durability, amplification);
    }
}
