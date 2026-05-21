package io.github.pouffy.tcompat.compat.curios;

import io.github.pouffy.tcompat.common.util.CompatHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.Optional;

public class CuriosHandler {

    public static ArrayList<ItemStack> getCurioTools(LivingEntity attacker) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        if (!CompatHelper.isLoaded("curios")) return itemStacks;
        Optional<IItemHandlerModifiable> optional = CuriosApi.getCuriosHelper().getEquippedCurios(attacker).resolve();
        if (optional.isPresent()) {
            IItemHandlerModifiable handler = optional.get();
            for(int i = 0; i < handler.getSlots(); ++i) {
                ItemStack stack = handler.getStackInSlot(i);
                if (CompatHelper.isTool(stack)) {
                    itemStacks.add(stack);
                }
            }
        }
        return itemStacks;
    }

}
