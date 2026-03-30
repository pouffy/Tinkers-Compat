package io.github.pouffy.tcompat.datagen.tinkers.tool;

import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.tinkering.AbstractStationSlotLayoutProvider;
import slimeknights.tconstruct.tools.TinkerToolParts;

public class TCStationLayoutProv extends AbstractStationSlotLayoutProvider {
    public TCStationLayoutProv(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addLayouts() {
        defineModifiable(GlobalInit.glaive)
                .sortIndex(SORT_WEAPON + SORT_LARGE)
                .addInputItem(TinkerToolParts.smallBlade,  45, 26)
                .addInputItem(TinkerToolParts.toughHandle,  9, 62)
                .addInputItem(TinkerToolParts.toughHandle, 27, 44)
                .addInputItem(TinkerToolParts.toolBinding,  27, 62)
                .build();
    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Station Slot Layouts";
    }
}
