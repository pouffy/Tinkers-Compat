package io.github.pouffy.tcompat.compat.malum.modifier.rune;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.pouffy.tcompat.common.modifier.hook.EntitySensitiveAttributesModifierHook;
import io.github.pouffy.tcompat.common.modifier.hook.curios.CurioAttributeHook;
import io.github.pouffy.tcompat.common.modifier.hook.curios.CurioTickModifierHook;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.modules.behavior.AttributeModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.UUID;
import java.util.function.BiConsumer;

public class RuneOfDexterityModifier extends NoLevelsModifier implements CurioAttributeHook, EntitySensitiveAttributesModifierHook, InventoryTickModifierHook, CurioTickModifierHook {

    public void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, GlobalInit.ENTITY_SENSITIVE_ATTRIBUTES, GlobalInit.CURIO_ATTRIBUTE);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(IToolStackView tool, ModifierEntry entry, String slotId, int slotIndex, LivingEntity wearer, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Curio Movement Speed", 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL) {
            public double getAmount() {
                double amount = super.getAmount();
                if (wearer != null) {
                    float health = wearer.getHealth();
                    float maxHealth = wearer.getMaxHealth();
                    float pct = health / maxHealth;
                    return amount * (double)(2.0F - pct);
                } else {
                    return amount;
                }
            }
        });
        return map;
    }

    @Override
    public void addAttributes(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentSlot equipmentSlot, LivingEntity wearer, BiConsumer<Attribute, AttributeModifier> biConsumer) {
        biConsumer.accept(Attributes.MOVEMENT_SPEED, new AttributeModifier(AttributeModule.getUUID("rune_of_dexterity.movement_speed", equipmentSlot), "Movement Speed", 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL) {
            public double getAmount() {
                double amount = super.getAmount();
                if (wearer != null) {
                    float health = wearer.getHealth();
                    float maxHealth = wearer.getMaxHealth();
                    float pct = health / maxHealth;
                    return amount * (double)(2.0F - pct);
                } else {
                    return amount;
                }
            }
        });
    }

    @Override
    public void curioTick(IToolStackView tool, ModifierEntry entry, String slotId, int slotIndex, LivingEntity wearer, ItemStack stack) {
        if (tool.isBroken()) return;
        if (wearer.level().getGameTime() % 5L == 0L) {
            AttributeInstance attribute = wearer.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attribute != null) {
                attribute.setDirty();
            }
        }
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity living, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if ((!isCorrectSlot && !isSelected) || tool.isBroken()) return;
        if (living.level().getGameTime() % 5L == 0L) {
            AttributeInstance attribute = living.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attribute != null) {
                attribute.setDirty();
            }
        }
    }
}
