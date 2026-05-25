package io.github.pouffy.tcompat.common.modifier.hook;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;

public interface EntitySensitiveAttributesModifierHook {

    void addAttributes(IToolStackView tool, ModifierEntry modifierEntry, EquipmentSlot slot, LivingEntity wearer, BiConsumer<Attribute, AttributeModifier> attributes);

    static Multimap<Attribute, AttributeModifier> getAttributeModifiers(IToolStackView tool, EquipmentSlot slot, LivingEntity wearer) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (!tool.isBroken()) {
            Objects.requireNonNull(builder);
            BiConsumer<Attribute, AttributeModifier> attributeConsumer = builder::put;

            for(ModifierEntry entry : tool.getModifierList()) {
                entry.getHook(GlobalInit.ENTITY_SENSITIVE_ATTRIBUTES).addAttributes(tool, entry, slot, wearer, attributeConsumer);
            }
        }
        return builder.build();
    }

    record AllMerger(Collection<EntitySensitiveAttributesModifierHook> modules) implements EntitySensitiveAttributesModifierHook {

        @Override
        public void addAttributes(IToolStackView tool, ModifierEntry modifierEntry, EquipmentSlot slot, LivingEntity wearer, BiConsumer<Attribute, AttributeModifier> attributes) {
            for(EntitySensitiveAttributesModifierHook module : this.modules) {
                module.addAttributes(tool, modifierEntry, slot, wearer, attributes);
            }
        }
    }
}
