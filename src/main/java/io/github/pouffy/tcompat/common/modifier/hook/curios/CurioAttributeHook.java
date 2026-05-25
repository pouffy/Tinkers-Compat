package io.github.pouffy.tcompat.common.modifier.hook.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;
import java.util.UUID;

public interface CurioAttributeHook {

    Multimap<Attribute, AttributeModifier> getAttributeModifiers(IToolStackView tool, ModifierEntry entry, String slotId, int slotIndex, LivingEntity wearer, UUID uuid, ItemStack stack);

    record AllMerger(Collection<CurioAttributeHook> modules) implements CurioAttributeHook {

        @Override
        public Multimap<Attribute, AttributeModifier> getAttributeModifiers(IToolStackView tool, ModifierEntry entry, String slotId, int slotIndex, LivingEntity wearer, UUID uuid, ItemStack stack) {
            Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
            for (var module : modules) {
                multimap.putAll(module.getAttributeModifiers(tool, entry, slotId, slotIndex, wearer, uuid, stack));
            }
            return multimap;
        }
    }
}
