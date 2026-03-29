package io.github.pouffy.tcompat.datagen.tinkers.tool;

import io.github.pouffy.tcompat.TCompat;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;

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
    }
}
