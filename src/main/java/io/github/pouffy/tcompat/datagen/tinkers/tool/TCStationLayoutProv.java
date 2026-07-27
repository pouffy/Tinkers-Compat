package io.github.pouffy.tcompat.datagen.tinkers.tool;

import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.tinkering.AbstractStationSlotLayoutProvider;
import slimeknights.tconstruct.tools.TinkerToolParts;
import slimeknights.tconstruct.tools.TinkerTools;

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
                .addInputItem(TinkerToolParts.toolBinding,  45, 44)
                .build();

        defineModifiable(GlobalInit.dartShooter).sortIndex(SORT_RANGED).addInputItem(GlobalInit.lipGuard, 46, 56).addInputItem(GlobalInit.dartBarrel, 28, 38).build();
        defineModifiable(GlobalInit.dart).sortIndex(SORT_AMMO).addInputItem(TinkerToolParts.arrowHead, 53, 22).addInputItem(TinkerToolParts.arrowShaft, 33, 42).addInputItem(TinkerToolParts.fletching, 15, 60).build();

    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Station Slot Layouts";
    }
}
