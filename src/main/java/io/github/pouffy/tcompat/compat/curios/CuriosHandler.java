package io.github.pouffy.tcompat.compat.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotAttribute;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.*;

public class CuriosHandler {

    public static ArrayList<ItemStack> getCurioTools(LivingEntity wearer) {
        return new ArrayList<>(getCurioItems(wearer).stream().filter(EquipmentHelper::isTool).toList());
    }

    public static ArrayList<ItemStack> getCurioItems(LivingEntity wearer) {
        if (!CompatHelper.isLoaded("curios")) return new ArrayList<>();
        return LoadedOnly.getCurioItems(wearer);
    }

    public static void tickHook(LivingEvent.LivingTickEvent event) {
        if (!CompatHelper.isLoaded("curios")) return;
        LoadedOnly.tickHook(event);
    }

    public static boolean hasItem(LivingEntity wearer, String identifier) {
        var retrieved = ObjectRetriever.getItem(identifier);
        return retrieved.filter(item -> CuriosHandler.getCurioItems(wearer).stream().map(ItemStack::getItem).toList().contains(item)).isPresent();
    }

    public static boolean isCurio(ItemStack stack) {
        if (!CompatHelper.isLoaded("curios")) return false;
        return LoadedOnly.isCurio(stack);
    }

    public static class LoadedOnly {

        public static boolean isCurio(ItemStack stack) {
            return stack.getItem() instanceof ICurioItem;
        }

        public static ArrayList<ItemStack> getCurioItems(LivingEntity wearer) {
            ArrayList<ItemStack> itemStacks = new ArrayList<>();
            CuriosApi.getCuriosInventory(wearer).ifPresent((handler) -> {
                Map<String, ICurioStacksHandler> curios = handler.getCurios();
                for(Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                    ICurioStacksHandler stacksHandler = entry.getValue();
                    IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                    for(int i = 0; i < stacksHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty()) {
                            itemStacks.add(stack);
                        }
                    }
                }
            });
            return itemStacks;
        }

        public static void tickHook(LivingEvent.LivingTickEvent event) {
            LivingEntity livingEntity = event.getEntity();
            CuriosApi.getCuriosInventory(livingEntity).ifPresent((handler) -> {
                Map<String, ICurioStacksHandler> curios = handler.getCurios();
                for(Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                    ICurioStacksHandler stacksHandler = entry.getValue();
                    String identifier = entry.getKey();
                    IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                    for(int i = 0; i < stacksHandler.getSlots(); ++i) {
                        NonNullList<Boolean> renderStates = stacksHandler.getRenders();
                        SlotContext slotContext = new SlotContext(identifier, livingEntity, i, false, renderStates.size() > i && renderStates.get(i));
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!EquipmentHelper.isTool(stack)) return;
                        if (!stack.isEmpty()) {
                            ToolStack tool = ToolStack.from(stack);
                            if (tool.isBroken()) {
                                return;
                            }
                            for (ModifierEntry modifierEntry: tool.getModifierList()) {
                                modifierEntry.getHook(GlobalInit.CURIO_TICK).curioTick(tool, modifierEntry, identifier, i, livingEntity, stack);
                            }
                        }
                        if (!livingEntity.level().isClientSide) {
                            ItemStack prevStack = stackHandler.getPreviousStackInSlot(i);
                            if (!ItemStack.matches(stack, prevStack)) {
                                UUID uuid = CuriosApi.getSlotUuid(slotContext);
                                if (!prevStack.isEmpty()) {
                                    Multimap<Attribute, AttributeModifier> map = getToolAttributes(prevStack, slotContext, uuid).getFirst();
                                    livingEntity.getAttributes().removeAttributeModifiers(map);
                                    handler.removeSlotModifiers(getToolAttributes(prevStack, slotContext, uuid).getSecond());
                                }
                                if (!stack.isEmpty()) {
                                    Multimap<Attribute, AttributeModifier> map = getToolAttributes(stack, slotContext, uuid).getFirst();
                                    livingEntity.getAttributes().addTransientAttributeModifiers(map);
                                    handler.addTransientSlotModifiers(getToolAttributes(prevStack, slotContext, uuid).getSecond());
                                }
                            }
                        }
                    }
                }
            });
        }

        private static Pair<Multimap<Attribute, AttributeModifier>, Multimap<String, AttributeModifier>> getToolAttributes(ItemStack stack, SlotContext slotContext, UUID uuid) {
            Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
            if (!EquipmentHelper.isTool(stack)) return Pair.of(HashMultimap.create(), HashMultimap.create());
            ToolStack tool = ToolStack.from(stack);
            for (ModifierEntry modifierEntry: tool.getModifierList()) {
                map.putAll(modifierEntry.getHook(GlobalInit.CURIO_ATTRIBUTE).getAttributeModifiers(tool, modifierEntry, slotContext.identifier(), slotContext.index(), slotContext.entity(), uuid, stack));
            }
            Multimap<String, AttributeModifier> slots = HashMultimap.create();
            Set<SlotAttribute> toRemove = new HashSet<>();
            for(Attribute attribute : map.keySet()) {
                if (attribute instanceof SlotAttribute wrapper) {
                    slots.putAll(wrapper.getIdentifier(), map.get(attribute));
                    toRemove.add(wrapper);
                }
            }
            for(Attribute attribute : toRemove) {
                map.removeAll(attribute);
            }
            return Pair.of(map, slots);
        }
    }
}
