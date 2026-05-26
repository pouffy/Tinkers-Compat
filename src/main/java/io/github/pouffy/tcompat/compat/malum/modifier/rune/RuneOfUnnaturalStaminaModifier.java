package io.github.pouffy.tcompat.compat.malum.modifier.rune;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.pouffy.tcompat.common.modifier.hook.curios.CurioAttributeHook;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.modules.behavior.AttributeModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.UUID;
import java.util.function.BiConsumer;

public class RuneOfUnnaturalStaminaModifier extends NoLevelsModifier implements CurioAttributeHook, AttributesModifierHook {

    public void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.ATTRIBUTES, GlobalInit.CURIO_ATTRIBUTE);
    }

    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifierEntry, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> attributes) {
        attributes.accept(Attributes.MOVEMENT_SPEED, new AttributeModifier(AttributeModule.getUUID("rune_of_unnatural_stamina.movement_speed", slot), "Movement Speed", 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(IToolStackView tool, ModifierEntry entry, String slotId, int slotIndex, LivingEntity wearer, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Curio Movement Speed", 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return map;
    }
}
