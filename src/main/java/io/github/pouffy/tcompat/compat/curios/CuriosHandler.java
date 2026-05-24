package io.github.pouffy.tcompat.compat.curios;

import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.items.IItemHandlerModifiable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.ArrayList;
import java.util.Map;
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

    public static void tickHook(LivingEvent.LivingTickEvent event) {
        if (!CompatHelper.isLoaded("curios")) return;
        LivingEntity livingEntity = event.getEntity();

        CuriosApi.getCuriosInventory(livingEntity).ifPresent((handler) -> {
            Map<String, ICurioStacksHandler> curios = handler.getCurios();
            for(Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                ICurioStacksHandler stacksHandler = entry.getValue();
                String identifier = entry.getKey();
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                for(int i = 0; i < stacksHandler.getSlots(); ++i) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && CompatHelper.isTool(stack)) {
                        ToolStack tool = ToolStack.from(stack);
                        if (tool.isBroken()) {
                            return;
                        }
                        for (ModifierEntry modifierEntry: tool.getModifierList()) {
                            modifierEntry.getHook(GlobalInit.CURIO_TICK).curioTick(tool, modifierEntry, identifier, i, livingEntity, stack);
                        }
                    }
                }
            }
        });
    }
}
