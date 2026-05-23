package io.github.pouffy.tcompat.common.modifier.module;

import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.mantle.data.loadable.Loadables;
import slimeknights.mantle.data.loadable.primitive.EnumLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.json.TinkerLoadables;
import slimeknights.tconstruct.library.json.math.ModifierFormula;
import slimeknights.tconstruct.library.json.variable.VariableFormula;
import slimeknights.tconstruct.library.json.variable.VariableFormulaLoadable;
import slimeknights.tconstruct.library.json.variable.tool.ToolFormula;
import slimeknights.tconstruct.library.json.variable.tool.ToolVariable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.modifiers.modules.behavior.AttributeUniqueField;
import slimeknights.tconstruct.library.modifiers.modules.util.ModifierCondition;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public record OptionalAttributeModule(String unique, ResourceLocation attribute, AttributeModifier.Operation operation, ToolFormula formula, UUID[] slotUUIDs, TooltipStyle tooltipStyle, ModifierCondition<IToolStackView> condition) implements AttributesModifierHook, ModifierModule, EquipmentChangeModifierHook, TooltipModifierHook, ModifierCondition.ConditionalModule<IToolStackView> {
    private static final String[] VARIABLES = new String[]{"level"};
    private static final RecordLoadable<ToolFormula> VARIABLE_LOADER = new VariableFormulaLoadable<>(ToolVariable.LOADER, VARIABLES, ModifierFormula.FallbackFormula.IDENTITY, (formula, variables, percent) -> new ToolFormula(formula, variables, VariableFormula.EMPTY_STRINGS));
    private static final List<ModuleHook<?>> ATTRIBUTE_HOOKS = HookProvider.defaultHooks(ModifierHooks.ATTRIBUTES);
    private static final List<ModuleHook<?>> TOOLTIP_HOOKS = HookProvider.defaultHooks(ModifierHooks.EQUIPMENT_CHANGE, ModifierHooks.TOOLTIP);
    private static final List<ModuleHook<?>> NO_TOOLTIP_HOOKS = HookProvider.defaultHooks(ModifierHooks.EQUIPMENT_CHANGE);
    public static final RecordLoadable<OptionalAttributeModule> LOADER = RecordLoadable.create(
            new AttributeUniqueField<>(OptionalAttributeModule::unique),
            Loadables.RESOURCE_LOCATION.requiredField("attribute", OptionalAttributeModule::attribute),
            TinkerLoadables.OPERATION.requiredField("operation", OptionalAttributeModule::operation),
            VARIABLE_LOADER.directField(OptionalAttributeModule::formula),
            TinkerLoadables.EQUIPMENT_SLOT_SET.requiredField("slots", (m) -> uuidsToSlots(m.slotUUIDs)),
            TooltipStyle.LOADABLE.defaultField("tooltip_style", TooltipStyle.ATTRIBUTE, OptionalAttributeModule::tooltipStyle),
            ModifierCondition.TOOL_FIELD,
            (unique, attribute, operation, amount, slots, tooltipStyle, condition) -> new OptionalAttributeModule(unique, attribute, operation, amount, slotsToUUIDs(unique, slots), tooltipStyle, condition));


    @ApiStatus.Internal
    public OptionalAttributeModule {}

    public static UUID getUUID(String name, EquipmentSlot slot) {
        return UUID.nameUUIDFromBytes((name + "." + slot.getName()).getBytes());
    }

    public static UUID[] slotsToUUIDs(String name, Collection<EquipmentSlot> slots) {
        UUID[] slotUUIDs = new UUID[6];

        for(EquipmentSlot slot : slots) {
            slotUUIDs[slot.getFilterFlag()] = getUUID(name, slot);
        }

        return slotUUIDs;
    }

    public static Set<EquipmentSlot> uuidsToSlots(UUID[] uuids) {
        Set<EquipmentSlot> set = EnumSet.noneOf(EquipmentSlot.class);

        for(EquipmentSlot slot : EquipmentSlot.values()) {
            if (uuids[slot.getFilterFlag()] != null) {
                set.add(slot);
            }
        }

        return set;
    }

    private @Nullable UUID getUUID(EquipmentSlot slot) {
        return this.slotUUIDs[slot.getFilterFlag()];
    }

    private @Nullable AttributeModifier createModifier(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot) {
        UUID uuid = this.getUUID(slot);
        return uuid != null ? new AttributeModifier(uuid, this.unique + "." + slot.getName(), this.formula.apply(tool, modifier), this.operation) : null;
    }

    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (this.condition.matches(tool, modifier)) {
            AttributeModifier attributeModifier = this.createModifier(tool, modifier, slot);
            ObjectRetriever.getAttribute(this.attribute.toString()).ifPresent(attr -> {
                if (attributeModifier != null) {
                    consumer.accept(attr, attributeModifier);
                }
            });
        }
    }

    public void onEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        if (this.condition.matches(tool, modifier)) {
            ObjectRetriever.getAttribute(this.attribute.toString()).ifPresent(attr -> {
                AttributeInstance instance = context.getEntity().getAttribute(attr);
                if (instance != null) {
                    AttributeModifier attributeModifier = this.createModifier(tool, modifier, context.getChangedSlot());
                    if (attributeModifier != null) {
                        instance.removeModifier(attributeModifier.getId());
                        instance.addTransientModifier(attributeModifier);
                    }
                }
            });
        }
    }

    public void onUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        if (this.condition.matches(tool, modifier)) {
            UUID uuid = this.getUUID(context.getChangedSlot());
            if (uuid != null) {
                ObjectRetriever.getAttribute(this.attribute.toString()).ifPresent(attr -> {
                    AttributeInstance instance = context.getEntity().getAttribute(attr);
                    if (instance != null) {
                        instance.removeModifier(uuid);
                    }
                });
            }
        }
    }

    public static void addTooltip(Modifier modifier, Attribute attribute, AttributeModifier.Operation operation, TooltipStyle tooltipStyle, float amount, @Nullable UUID uuid, @Nullable Player player, List<Component> tooltip) {
        switch (tooltipStyle) {
            case ATTRIBUTE -> TooltipUtil.addAttribute(attribute, operation, amount, uuid, player, tooltip);
            case BOOST -> TooltipModifierHook.addFlatBoost(modifier, Component.translatable(attribute.getDescriptionId()), amount, tooltip);
            case PERCENT -> TooltipModifierHook.addPercentBoost(modifier, Component.translatable(attribute.getDescriptionId()), amount, tooltip);
        }
    }

    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (this.condition.matches(tool, modifier)) {
            float value = this.formula.apply(tool, modifier);
            if (value != 0.0F) {
                ObjectRetriever.getAttribute(this.attribute.toString()).ifPresent(attr -> addTooltip(modifier.getModifier(), attr, this.operation, this.tooltipStyle, value, null, player, tooltip));
            }
        }
    }

    public List<ModuleHook<?>> getDefaultHooks() {
        if (this.tooltipStyle == TooltipStyle.ATTRIBUTE) {
            return ATTRIBUTE_HOOKS;
        } else {
            return this.tooltipStyle == TooltipStyle.NONE ? NO_TOOLTIP_HOOKS : TOOLTIP_HOOKS;
        }
    }

    public RecordLoadable<OptionalAttributeModule> getLoader() {
        return LOADER;
    }

    public static Builder builder(ResourceLocation attribute, AttributeModifier.Operation operation) {
        return new Builder(attribute, operation);
    }

    public static Builder builder(Supplier<ResourceLocation> attribute, AttributeModifier.Operation operation) {
        return new Builder(attribute.get(), operation);
    }

    public enum TooltipStyle {
        ATTRIBUTE,
        NONE,
        BOOST,
        PERCENT;

        public static final EnumLoadable<TooltipStyle> LOADABLE = new EnumLoadable<>(TooltipStyle.class);

        TooltipStyle() {
        }
    }

    public static class Builder extends VariableFormula.Builder<OptionalAttributeModule.Builder, OptionalAttributeModule, ToolVariable> {
        protected final ResourceLocation attribute;
        protected final AttributeModifier.Operation operation;
        protected String unique = "";
        private EquipmentSlot[] slots = EquipmentSlot.values();
        private TooltipStyle tooltipStyle;

        protected Builder(ResourceLocation attribute, AttributeModifier.Operation operation) {
            super(OptionalAttributeModule.VARIABLES);
            this.tooltipStyle = TooltipStyle.ATTRIBUTE;
            this.attribute = attribute;
            this.operation = operation;
        }

        public Builder slots(EquipmentSlot... slots) {
            this.slots = slots;
            return this;
        }

        public Builder uniqueFrom(ResourceLocation id) {
            String var10001 = id.getNamespace();
            return this.unique(var10001 + ".modifier." + id.getPath());
        }

        public Builder percent() {
            this.tooltipStyle = TooltipStyle.PERCENT;
            return this;
        }

        protected OptionalAttributeModule build(ModifierFormula formula) {
            return new OptionalAttributeModule(this.unique, this.attribute, this.operation, new ToolFormula(formula, this.variables), OptionalAttributeModule.slotsToUUIDs(this.unique, List.of(this.slots)), this.tooltipStyle, this.condition);
        }

        public Builder unique(String unique) {
            this.unique = unique;
            return this;
        }

        public Builder tooltipStyle(TooltipStyle tooltipStyle) {
            this.tooltipStyle = tooltipStyle;
            return this;
        }
    }
}
