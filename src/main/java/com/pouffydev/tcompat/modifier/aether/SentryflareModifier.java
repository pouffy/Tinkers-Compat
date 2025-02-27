package com.pouffydev.tcompat.modifier.aether;

import com.pouffydev.tcompat.TCompat;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.zepalesque.redux.data.resource.ReduxDamageTypes;
import net.zepalesque.redux.entity.projectile.Ember;
import net.zepalesque.redux.item.ReduxItems;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.DamageDealtModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class SentryflareModifier extends NoLevelsModifier implements DamageDealtModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.DAMAGE_DEALT);
    }

    @Override
    public void onDamageDealt(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, LivingEntity target, DamageSource source, float amount, boolean isDirectDamage) {
        if (!TCompat.isClassFound("net.zepalesque.redux.entity.projectile.Ember")) {
            return;
        }
        if (target != null && source.getDirectEntity() instanceof Player player && !source.is(ReduxDamageTypes.EMBER) && amount > 1.0) {
            RandomSource random = target.level().getRandom();
            int embers = amount < 1.0 ? 0 : Mth.ceil(((amount * 0.5D) + 1D)) - random.nextInt(1);
            for (int i = 1; i <= embers; i++) {
                float rotation = Mth.wrapDegrees(random.nextInt(360));
                Ember ember = new Ember(target.level(), player, target);
                ember.setPos(target.getX(), target.getY() + (target.getBbHeight() / 2) + ((random.nextFloat() * 2) - 1), target.getZ());
                ember.shootFromRotation(target, -45 + (90 * random.nextFloat()), rotation, 0.0F, 1F, 1.0F);
                if (!player.level().isClientSide()) {
                    player.level().addFreshEntity(ember);
                }
            }
        }
    }

}
