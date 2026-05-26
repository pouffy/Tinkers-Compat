package io.github.pouffy.tcompat.compat.cataclysm.modifier.combat.melee;

import io.github.pouffy.tcompat.common.modifier.hook.ToolSwingModifierHook;
import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.cataclysm.CataclysmHandler;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class SandstormModifier extends NoLevelsModifier implements MeleeHitModifierHook, ToolSwingModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, GlobalInit.TOOL_SWING);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();
        ItemStack stack = attacker.getItemInHand(context.getHand());
        launch(stack, attacker);
    }

    @Override
    public boolean swingMainHand(IToolStackView toolStackView, ModifierEntry modifierEntry, ItemStack stack, LivingEntity playerIn) {
        launch(stack, playerIn);
        return true;
    }

    public boolean launch(ItemStack stack, LivingEntity attacker) {
        boolean charged = true;
        if (attacker instanceof Player player) {
            charged = player.getAttackStrengthScale(0.5F) > 0.9F;
        }
        if (charged) {
            Level worldIn = attacker.level();
            if (!worldIn.isClientSide) {
                stack.hurtAndBreak(1, attacker, (p_43388_) -> p_43388_.broadcastBreakEvent(attacker.getUsedItemHand()));
                float d7 = attacker.getYRot();
                float d = attacker.getXRot();
                float d1 = -Mth.sin(d7 * ((float)Math.PI / 180F)) * Mth.cos(d * ((float)Math.PI / 180F));
                float d2 = -Mth.sin(d * ((float)Math.PI / 180F));
                float d3 = Mth.cos(d7 * ((float)Math.PI / 180F)) * Mth.cos(d * ((float)Math.PI / 180F));
                double theta = (double)d7 * (Math.PI / 180D);
                ++theta;
                double vecX = Math.cos(theta);
                double vecZ = Math.sin(theta);
                double x = attacker.getX() + vecX;
                double Z = attacker.getZ() + vecZ;
                var sandstorm = CataclysmHandler.createSandstorm(attacker, d1, d2, d3, x, Z);
                if (sandstorm != null) {
                    worldIn.addFreshEntity(sandstorm);
                    return true;
                }
            }
        }
        return false;
    }


}
