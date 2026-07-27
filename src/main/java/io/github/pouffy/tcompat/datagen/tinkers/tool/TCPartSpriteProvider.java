package io.github.pouffy.tcompat.datagen.tinkers.tool;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.compat.aether.item.DartBarrelMaterialStats;
import io.github.pouffy.tcompat.compat.aether.item.LipGuardMaterialStats;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

public class TCPartSpriteProvider extends AbstractPartSpriteProvider {
    public TCPartSpriteProvider() {
        super(TCompat.MOD_ID);
    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Parts";
    }

    @Override
    protected void addAllSpites() {
        buildTool("glaive").withLarge().addBreakableHead("head").addHandle("handle").addHandle("grip").addBinding("accessory");
        buildTool("dart_shooter").addPart("barrel", DartBarrelMaterialStats.ID).addPart("guard", LipGuardMaterialStats.ID);
        buildTool("ammo").addArrowHead("dart_head").addPart("dart_shaft", StatlessMaterialStats.ARROW_SHAFT).addPart("dart_feather", StatlessMaterialStats.FLETCHING);

        addPart("dart_barrel", DartBarrelMaterialStats.ID);
        addPart("lip_guard", LipGuardMaterialStats.ID);
    }
}
