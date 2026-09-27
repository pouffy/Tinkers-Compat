package io.github.pouffy.tcompat.common.modifier.module;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import io.github.pouffy.tcompat.TCompat;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import slimeknights.mantle.data.loadable.Loadables;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.json.LevelingInt;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.modifiers.modules.util.ModifierCondition;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record OptionalEffectImmunityModule(ResourceLocation effect, LevelingInt maxLevel, ModifierCondition<IToolStackView> condition) implements ModifierModule, EquipmentChangeModifierHook, ModifierCondition.ConditionalModule<IToolStackView> {
    private static final List<ModuleHook<?>> DEFAULT_HOOKS;
    public static final TinkerDataCapability.ComputableDataKey<Multiset<ResourceLocation>> EFFECT_IMMUNITY;
    private static final LevelingInt ANY_LEVEL;
    public static final RecordLoadable<OptionalEffectImmunityModule> LOADER;

    public OptionalEffectImmunityModule(ResourceLocation effect, ModifierCondition<IToolStackView> condition) {
        this(effect, ANY_LEVEL, condition);
    }

    public OptionalEffectImmunityModule(ResourceLocation effect, LevelingInt maxLevel) {
        this(effect, maxLevel, ModifierCondition.ANY_TOOL);
    }

    public OptionalEffectImmunityModule(ResourceLocation effect) {
        this(effect, ANY_LEVEL);
    }

    public RecordLoadable<OptionalEffectImmunityModule> getLoader() {
        return LOADER;
    }

    public List<ModuleHook<?>> getDefaultHooks() {
        return DEFAULT_HOOKS;
    }

    public void onEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        if (!tool.isBroken() && ArmorLevelModule.validSlot(tool, context.getChangedSlot(), TinkerTags.Items.HELD_ARMOR) && this.condition.matches(tool, modifier)) {
            TinkerDataCapability.Holder data = context.getDataHolder();
            if (data != null) {
                data.computeIfAbsent(EFFECT_IMMUNITY).add(this.effect, this.maxLevel.compute(modifier));
            }
        }
    }

    public void onUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        if (!tool.isBroken() && ArmorLevelModule.validSlot(tool, context.getChangedSlot(), TinkerTags.Items.HELD_ARMOR) && this.condition.matches(tool, modifier)) {
            TinkerDataCapability.Holder data = context.getDataHolder();
            if (data != null) {
                Multiset<ResourceLocation> effects = data.get(EFFECT_IMMUNITY);
                if (effects != null) {
                    effects.remove(this.effect, this.maxLevel.compute(modifier));
                }
            }
        }
    }

    static {
        DEFAULT_HOOKS = HookProvider.defaultHooks(ModifierHooks.EQUIPMENT_CHANGE);
        EFFECT_IMMUNITY = TinkerDataCapability.ComputableDataKey.of(TCompat.getResource("optional_effect_immunity"), HashMultiset::create);
        ANY_LEVEL = LevelingInt.flat(255);
        LOADER = RecordLoadable.create(Loadables.RESOURCE_LOCATION.requiredField("effect", OptionalEffectImmunityModule::effect), LevelingInt.LOADABLE.defaultField("max_level", ANY_LEVEL, false, OptionalEffectImmunityModule::maxLevel), ModifierCondition.TOOL_FIELD, OptionalEffectImmunityModule::new);
    }
}
