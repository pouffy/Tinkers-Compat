package io.github.pouffy.tcompat.common.modifier.base;

import io.github.pouffy.tcompat.common.modifier.hook.CurioTickModifierHook;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class TotemicRuneModifier extends NoLevelsModifier implements CurioTickModifierHook, InventoryTickModifierHook {
    public final Supplier<MobEffect> mobEffectSupplier;
    public final Predicate<LivingEntity> entityPredicate;
    private final int interval;

    protected TotemicRuneModifier(Supplier<MobEffect> mobEffectSupplier, Predicate<LivingEntity> entityPredicate, int interval) {
        this.mobEffectSupplier = mobEffectSupplier;
        this.entityPredicate = entityPredicate;
        this.interval = interval;
    }

    public void curioTick(IToolStackView tool, ModifierEntry entry, String slotId, int slotIndex, LivingEntity wearer, ItemStack stack) {
        if (!wearer.level().isClientSide() && wearer.level().getGameTime() % (long)this.interval == 0L && this.entityPredicate.test(wearer)) {
            wearer.addEffect(new MobEffectInstance(this.mobEffectSupplier.get(), 200, 0, true, true));
        }
    }

    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity living, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!isCorrectSlot || tool.isBroken()) return;
        if (!living.level().isClientSide() && living.level().getGameTime() % (long)this.interval == 0L && this.entityPredicate.test(living)) {
            living.addEffect(new MobEffectInstance(this.mobEffectSupplier.get(), 200, 0, true, true));
        }
    }
}
