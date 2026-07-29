package io.github.pouffy.tcompat.compat.create;

import com.simibubi.create.content.equipment.goggles.GogglesItem;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class GogglesPredicate {

    public static void registerGoggles() {
        GogglesItem.addIsWearingPredicate((player) -> {
            ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
            return EquipmentHelper.hasModifier(ToolStack.copyFrom(helmet), TCModifiers.goggles);
        });
    }
}
