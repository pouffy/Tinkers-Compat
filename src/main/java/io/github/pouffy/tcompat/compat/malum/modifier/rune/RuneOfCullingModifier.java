package io.github.pouffy.tcompat.compat.malum.modifier.rune;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.pouffy.tcompat.common.modifier.hook.curios.CurioAttributeHook;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.modules.behavior.AttributeModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;
import java.util.function.BiConsumer;

@ParametersAreNonnullByDefault
public class RuneOfCullingModifier extends NoLevelsModifier implements CurioAttributeHook, AttributesModifierHook {

    public void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.ATTRIBUTES, GlobalInit.CURIO_ATTRIBUTE);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(IToolStackView tool, ModifierEntry entry, String slotId, int slotIndex, LivingEntity wearer, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        ObjectRetriever.getAttribute("lodestone:magic_proficiency").ifPresent(attribute -> map.put(attribute, new AttributeModifier(uuid, "Curio Magic Proficiency", 0.2, AttributeModifier.Operation.MULTIPLY_BASE)));
        ObjectRetriever.getAttribute("irons_spellbooks:spell_power").ifPresent(attribute -> map.put(attribute, new AttributeModifier(uuid, "Curio Spell Power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE)));
        return map;
    }

    @Override
    public void addAttributes(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentSlot equipmentSlot, BiConsumer<Attribute, AttributeModifier> biConsumer) {
        if (equipmentSlot != EquipmentSlot.MAINHAND) return;
        ObjectRetriever.getAttribute("lodestone:magic_proficiency").ifPresent(attribute -> biConsumer.accept(attribute, new AttributeModifier(AttributeModule.getUUID("rune_of_culling.magic_proficiency", equipmentSlot), "Curio Magic Proficiency", 0.2, AttributeModifier.Operation.MULTIPLY_BASE)));
        ObjectRetriever.getAttribute("irons_spellbooks:spell_power").ifPresent(attribute -> biConsumer.accept(attribute, new AttributeModifier(AttributeModule.getUUID("rune_of_culling.spell_power", equipmentSlot), "Curio Spell Power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE)));
    }
}
