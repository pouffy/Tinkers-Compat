package io.github.pouffy.tcompat.common.util;

import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.compat.curios.CuriosHandler;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EquipmentHelper {

    public static boolean isTool(ItemStack stack) {
        return stack.getItem() instanceof IModifiable modifiable && modifiable.getToolDefinition() != ToolDefinition.EMPTY;
    }

    /**
     * Only run if the stack is a tool.
     * @param stack the ItemStack being used.
     * @param consumer the method  to run.
     */
    public static void asTool(ItemStack stack, Consumer<ToolStack> consumer) {
        if (isTool(stack)) {
            consumer.accept(ToolStack.from(stack));
        }
    }

    public static ArrayList<ItemStack> getModifiableStacks(LivingEntity wearer) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        Optional.ofNullable(getModifiableHeld(wearer, false)).ifPresent(itemStacks::add);
        Optional.ofNullable(getModifiableHeld(wearer, true)).ifPresent(itemStacks::add);
        itemStacks.addAll(getModifiableArmour(wearer));
        itemStacks.addAll(getModifiableCurios(wearer));
        return itemStacks;
    }

    public static ArrayList<ItemStack> getModifiableCurios(LivingEntity wearer) {
        return CuriosHandler.getCurioTools(wearer);
    }

    public static ArrayList<ItemStack> getModifiableArmour(LivingEntity wearer) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        wearer.getArmorSlots().forEach(stack -> {
            if (isTool(stack)) itemStacks.add(stack);
        });
        return itemStacks;
    }

    /**
     * Method for retrieving held tools whilst excluding armour and curios
     * @param wearer the entity wearing the equipment
     * @param offhand whether to target the offhand item
     * @return the held stack if it meets the conditions.
     */
    public static ItemStack getModifiableHeld(LivingEntity wearer, boolean offhand) {
        ItemStack held = offhand ? wearer.getOffhandItem() : wearer.getMainHandItem();
        if (!isTool(held) || (held.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot() != (offhand ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND))) {
            return ItemStack.EMPTY;
        }
        if (CuriosHandler.isCurio(held) && !held.is(TCTags.Items.HANDHELD_CURIO)) return ItemStack.EMPTY;
        return held;
    }

    public static float getEffectiveLevel(LivingEntity wearer, ModifierId modifier) {
        return getEffectiveLevel(wearer, modifier, (stack) -> true);
    }

    public static float getEffectiveLevel(LivingEntity wearer, ModifierId modifier, Predicate<ItemStack> stackPredicate) {
        float level = 0;
        for (var stack : getModifiableStacks(wearer).stream().filter(stackPredicate).toList()) {
            var tool = ToolStack.from(stack);
            if (tool.isBroken()) continue;
            level += tool.getModifiers().getEntry(modifier).getEffectiveLevel();
        }
        return level;
    }

    public static boolean hasModifier(ToolStack tool, ModifierId modifier) {
        return tool.getModifiers().getEntry(modifier) != ModifierEntry.EMPTY;
    }

    public static boolean hasModifier(LivingEntity wearer, ModifierId modifier) {
        return hasModifier(wearer, modifier, (stack) -> true);
    }

    public static boolean hasModifier(LivingEntity wearer, ModifierId modifier, Predicate<ItemStack> stackPredicate) {
        boolean equipped = false;
        for (var stack : getModifiableStacks(wearer).stream().filter(stackPredicate).toList()) {
            var tool = ToolStack.from(stack);
            if (tool.isBroken()) continue;
            if (tool.getModifiers().getEntry(modifier) != ModifierEntry.EMPTY) {
                equipped = true;
                break;
            }
        }
        return equipped;
    }
}
