package com.pouffydev.tcompat.modifier.aether;

import com.pouffydev.tcompat.modifier.SetBonusModifier;
import com.pouffydev.tcompat.util.explosion.KnockbackExplosion;
import com.pouffydev.tcompat.util.explosion.SimpleExplosionDamageCalculator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Optional;

public class BlastDeterranceModifier extends SetBonusModifier implements OnAttackedModifierHook {
    private static final SimpleExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR;

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ON_ATTACKED);
    }

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        float chance = 0.9f * getArmorCoverage();
        if (context.getLevel().random.nextFloat() < chance) {
            BlockPos pos = context.getEntity().getOnPos();
            explode(context.getLevel(), pos, context.getEntity());
        }
    }

    private KnockbackExplosion explode(Level level, BlockPos pos, LivingEntity attacker) {
        KnockbackExplosion explosion = new KnockbackExplosion(level, attacker, null, EXPLOSION_DAMAGE_CALCULATOR, pos.getX(), pos.getY(), pos.getZ(), 2.4F * getArmorCoverage(), false, Explosion.BlockInteraction.KEEP, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, SoundEvents.GENERIC_EXPLODE);
        if (ForgeEventFactory.onExplosionStart(level, explosion)) {
            return explosion;
        } else {
            explosion.explode();
            explosion.finalizeExplosion(true);
            return explosion;
        }
    }

    static {
        EXPLOSION_DAMAGE_CALCULATOR = new SimpleExplosionDamageCalculator(true, false, Optional.of(1.22F), Optional.empty());
    }
}
