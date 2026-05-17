package io.github.pouffy.tcompat.common.module;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.mantle.data.loadable.Loadables;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.data.predicate.IJsonPredicate;
import slimeknights.mantle.data.predicate.entity.LivingEntityPredicate;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.json.RandomLevelingValue;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.modifiers.modules.util.ModifierCondition;
import slimeknights.tconstruct.library.modifiers.modules.util.ModuleBuilder;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public record MobEffectUserModule(IJsonPredicate<LivingEntity> user, MobEffect effect, RandomLevelingValue level, RandomLevelingValue time, LevelingValue chance, ModifierCondition<IToolStackView> condition) implements InventoryTickModifierHook, ModifierModule, ModifierCondition.ConditionalModule<IToolStackView> {
    private static final List<ModuleHook<?>> DEFAULT_HOOKS = HookProvider.defaultHooks(ModifierHooks.INVENTORY_TICK);
    public static final RecordLoadable<MobEffectUserModule> LOADER = RecordLoadable.create(
            LivingEntityPredicate.LOADER.defaultField("target", MobEffectUserModule::user),
            Loadables.MOB_EFFECT.requiredField("effect", MobEffectUserModule::effect),
            RandomLevelingValue.LOADABLE.requiredField("level", MobEffectUserModule::level),
            RandomLevelingValue.LOADABLE.requiredField("time", MobEffectUserModule::time),
            LevelingValue.LOADABLE.defaultField("chance", LevelingValue.eachLevel(0.25F), false, MobEffectUserModule::chance),
            ModifierCondition.TOOL_FIELD,
            MobEffectUserModule::new);

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (isCorrectSlot) {
            if (this.user.matches(holder) && !tool.isBroken()) {
                float scaledLevel = modifier.getEffectiveLevel();
                float chance = this.chance.compute(scaledLevel);
                if (chance >= 1.0F || TConstruct.RANDOM.nextFloat() < chance) {
                    int level = Math.round(this.level.computeValue(scaledLevel)) - 1;
                    if (level >= 0) {
                        float duration = this.time.computeValue(scaledLevel);
                        if (duration > 0.0F) {
                            holder.addEffect(new MobEffectInstance(this.effect, (int)duration, level));
                        }
                    }
                }
            }
        }
    }

    public RecordLoadable<MobEffectUserModule> getLoader() {
        return LOADER;
    }

    public List<ModuleHook<?>> getDefaultHooks() {
        return DEFAULT_HOOKS;
    }

    public static Builder builder(MobEffect effect) {
        return new Builder(effect);
    }

    public static class Builder extends ModuleBuilder.Stack<Builder> {
        private final MobEffect effect;
        private IJsonPredicate<LivingEntity> user;
        private RandomLevelingValue level;
        private RandomLevelingValue time;
        private LevelingValue chance;

        public MobEffectUserModule build() {
            return new MobEffectUserModule(this.user, this.effect, this.level, this.time, this.chance, this.condition);
        }

        private Builder(MobEffect effect) {
            this.user = LivingEntityPredicate.ANY;
            this.level = RandomLevelingValue.flat(1.0F);
            this.time = RandomLevelingValue.flat(0.0F);
            this.chance = LevelingValue.eachLevel(0.25F);
            this.effect = effect;
        }

        public Builder user(IJsonPredicate<LivingEntity> user) {
            this.user = user;
            return this;
        }

        public Builder level(RandomLevelingValue level) {
            this.level = level;
            return this;
        }

        public Builder time(RandomLevelingValue time) {
            this.time = time;
            return this;
        }

        public Builder chance(LevelingValue chance) {
            this.chance = chance;
            return this;
        }
    }
}
