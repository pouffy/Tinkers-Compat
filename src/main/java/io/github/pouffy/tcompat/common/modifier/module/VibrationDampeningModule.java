package io.github.pouffy.tcompat.common.modifier.module;

import io.github.pouffy.tcompat.common.data.variable.GameEventEntry;
import io.github.pouffy.tcompat.common.data.variable.GameEventLoadable;
import io.github.pouffy.tcompat.common.modifier.hook.VibrationDampeningModifierHook;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.data.predicate.IJsonPredicate;
import slimeknights.mantle.data.predicate.block.BlockPredicate;
import slimeknights.mantle.data.predicate.entity.LivingEntityPredicate;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.modifiers.modules.util.ModifierCondition;
import slimeknights.tconstruct.library.modifiers.modules.util.ModuleBuilder;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public record VibrationDampeningModule(GameEventEntry gameEvent, LevelingValue chance, IJsonPredicate<LivingEntity> user, IJsonPredicate<BlockState> block, ModifierCondition<IToolStackView> condition) implements ModifierModule, TooltipModifierHook, VibrationDampeningModifierHook, ModifierCondition.ConditionalModule<IToolStackView> {

    public static final RecordLoadable<VibrationDampeningModule> LOADER = RecordLoadable.create(
            GameEventLoadable.INSTANCE.requiredField("event", VibrationDampeningModule::gameEvent),
            LevelingValue.LOADABLE.defaultField("chance", LevelingValue.flat(1.0F), false, VibrationDampeningModule::chance),
            LivingEntityPredicate.LOADER.defaultField("user", false, VibrationDampeningModule::user),
            BlockPredicate.LOADER.defaultField("block", false, VibrationDampeningModule::block),
            ModifierCondition.TOOL_FIELD,
            VibrationDampeningModule::new);

    @Override
    public boolean isEffective(IToolStackView tool, ModifierEntry modifier, Level level, GameEvent event, GameEvent.Context context, Vec3 pos) {
        if (this.condition.matches(tool, modifier)) {
            if (!testEvent(event)) return false;
            if (user != LivingEntityPredicate.ANY && !testUser(context)) return false;
            if (block != BlockPredicate.ANY && !testBlock(context)) return false;
            float chance = this.chance.compute(modifier.getEffectiveLevel());
            return chance >= 1.0F || TConstruct.RANDOM.nextFloat() < chance;
        }
        return false;
    }

    private boolean testEvent(GameEvent event) {
        return this.gameEvent().matches(event);
    }

    private boolean testUser(GameEvent.Context context) {
        if (context.sourceEntity() instanceof LivingEntity living) {
            return user.matches(living);
        }
        return false;
    }

    private boolean testBlock(GameEvent.Context context) {
        if (context.affectedState() != null) {
            return block.matches(context.affectedState());
        }
        return false;
    }

    @Override
    public RecordLoadable<VibrationDampeningModule> getLoader() {
        return LOADER;
    }

    @Override
    public @NotNull List<ModuleHook<?>> getDefaultHooks() {
        return HookProvider.defaultHooks(GlobalInit.VIBRATION_DAMPENING, ModifierHooks.TOOLTIP);
    }

    public static Builder event(GameEventEntry gameEvent) {
        return new Builder(gameEvent);
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (this.condition.matches(tool, modifier)) {
            list.add(Component.translatable("notification.tcompat.dampens_vibrations"));
        }
    }

    public static class Builder extends ModuleBuilder.Stack<Builder> {
        private final GameEventEntry gameEvent;
        private LevelingValue chance;
        private IJsonPredicate<LivingEntity> user;
        private IJsonPredicate<BlockState> block;

        public Builder(GameEventEntry gameEvent) {
            this.gameEvent = gameEvent;
        }

        public Builder chance(LevelingValue chance) {
            this.chance = chance;
            return this;
        }

        public Builder user(IJsonPredicate<LivingEntity> user) {
            this.user = user;
            return this;
        }

        public Builder block(IJsonPredicate<BlockState> block) {
            this.block = block;
            return this;
        }

        public VibrationDampeningModule build() {
            return new VibrationDampeningModule(this.gameEvent, this.chance, this.user, this.block, this.condition);
        }
    }
}
