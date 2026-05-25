package io.github.pouffy.tcompat.common.modifier.base;

import io.github.pouffy.tcompat.common.modifier.hook.curios.CurioTickModifierHook;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.malum.MalumHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class TotemicRuneModifier extends NoLevelsModifier implements CurioTickModifierHook, InventoryTickModifierHook {
    public final Supplier<MobEffect> mobEffectSupplier;
    public final Predicate<LivingEntity> entityPredicate;
    private final int interval;

    public void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.INVENTORY_TICK, GlobalInit.CURIO_TICK);
    }

    protected TotemicRuneModifier(Supplier<MobEffect> mobEffectSupplier, Predicate<LivingEntity> entityPredicate, int interval) {
        this.mobEffectSupplier = mobEffectSupplier;
        this.entityPredicate = entityPredicate;
        this.interval = interval;
    }

    public TotemicRuneModifier(Supplier<MobEffect> mobEffectSupplier, Predicate<LivingEntity> entityPredicate) {
        this(mobEffectSupplier, entityPredicate, 40);
    }

    public TotemicRuneModifier(String riteName, boolean corrupted, int interval) {
        if (CompatHelper.isLoaded("malum")) {
            var rite = MalumHandler.getRiteEffect(riteName, corrupted);
            this.mobEffectSupplier = rite.getFirst();
            this.entityPredicate = rite.getSecond();
        } else {
            this.mobEffectSupplier = () -> null;
            this.entityPredicate = (e) -> false;
        }
        this.interval = interval;
    }

    public TotemicRuneModifier(String riteName, boolean corrupted) {
        this(riteName, corrupted, 40);
    }

    public void curioTick(IToolStackView tool, ModifierEntry entry, String slotId, int slotIndex, LivingEntity wearer, ItemStack stack) {
        if (tool.isBroken() || this.mobEffectSupplier.get() == null) return;
        if (!wearer.level().isClientSide() && wearer.level().getGameTime() % (long)this.interval == 0L && this.entityPredicate.test(wearer)) {
            wearer.addEffect(new MobEffectInstance(this.mobEffectSupplier.get(), 200, 0, true, true));
        }
    }

    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity living, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if ((!isCorrectSlot && !isSelected) || tool.isBroken() || this.mobEffectSupplier.get() == null) return;
        if (!living.level().isClientSide() && this.entityPredicate.test(living)) {
            living.addEffect(new MobEffectInstance(this.mobEffectSupplier.get(), 20, 0, true, true));
        }
    }
}
