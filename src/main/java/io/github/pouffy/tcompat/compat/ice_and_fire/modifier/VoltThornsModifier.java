package io.github.pouffy.tcompat.compat.ice_and_fire.modifier;

import io.github.pouffy.tcompat.common.capability.compatible.Compatibility;
import io.github.pouffy.tcompat.compat.aether.modifier.ThunderstruckModifier;
import io.github.pouffy.tcompat.datagen.tag.TCEntityTagProv;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class VoltThornsModifier extends NoLevelsModifier implements OnAttackedModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ON_ATTACKED);
    }

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        LivingEntity attacker = (LivingEntity) source.getEntity();
        boolean canUse = false;
        if (context.getEntity() instanceof Player player) {
            canUse = !player.getCooldowns().isOnCooldown(tool.getItem());
        }
        if (attacker != null) {
            canUse = attacker.getRandom().nextIntBetweenInclusive(1, 16) < 3;
        }
        if (attacker != null && isDirectDamage && canUse) {
            LivingEntity user = context.getEntity();
            ToolDamageUtil.damageAnimated(tool, 1, user, slotType);
            boolean flag = !(user instanceof Player) || !((double) user.attackAnim > 0.2);
            if (!user.level().isClientSide && flag) {
                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(attacker.level());
                lightningBolt.moveTo(attacker.position());
                Compatibility.get(lightningBolt).ifPresent(compatibility -> compatibility.setLightningOwner(attacker));
                if (!attacker.level().isClientSide) {
                    attacker.knockback(1.0, user.getX() - attacker.getX(), user.getZ() - attacker.getZ());
                    attacker.level().addFreshEntity(lightningBolt);
                }
            }
            if (context.getEntity() instanceof Player player) {
                player.getCooldowns().addCooldown(tool.getItem(), Math.round((amount / 3) * 20));
            }
        }
    }
}
