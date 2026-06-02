package io.github.pouffy.tcompat.compat.deeperdarker.modifier.ranged;

import io.github.pouffy.tcompat.common.cooldown.ClientModifierCooldowns;
import io.github.pouffy.tcompat.common.cooldown.ModifierCooldowns;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.hook.interaction.UsingToolModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.modifiers.ability.interaction.BlockingModifier;

public class SonorousModifier extends NoLevelsModifier implements GeneralInteractionModifierHook, UsingToolModifierHook {
    public double dropOffFactor = 0.3333333333333333;

    public void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT, ModifierHooks.TOOL_USING);
    }

    @Override
    public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source) {
        if (!tool.isBroken() && source == InteractionSource.RIGHT_CLICK && !ClientModifierCooldowns.getCooldowns().isOnCooldown(modifier.getId())) {
            GeneralInteractionModifierHook.startUsingWithDrawtime(tool, modifier.getId(), player, hand, 1.0F);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public void beforeReleaseUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity user, int useDuration, int timeLeft, ModifierEntry activeModifier) {
        Level level = user.level();
        if (GeneralInteractionModifierHook.getActiveModifier(tool) != modifier) return;
        int timeUsed = this.getUseDuration(tool, modifier) - timeLeft;
        int damage = (int)Math.round((double)50.0F / ((double)1.0F + (double)16.0F / Math.exp(0.06 * (double)timeUsed)));
        int range = (int)Math.min(40L, Math.round((double)3.0F * Math.log(timeUsed + 1)));
        Vec3 eyePos = user.getEyePosition();
        Vec3 facing = user.getForward();

        for(int i = 0; i < range; ++i) {
            Vec3 scanVec = eyePos.add(facing.scale(i));
            BlockPos targetPos = new BlockPos((int)scanVec.x, (int)scanVec.y, (int)scanVec.z);
            BlockState targetState = level.getBlockState(targetPos);
            if (!targetState.isAir() && targetState.canOcclude()) {
                break;
            }
            if (level.isClientSide() && i % 2 == 0) {
                level.addParticle(ParticleTypes.SONIC_BOOM, scanVec.x, scanVec.y, scanVec.z, 1.0F, 0.0F, 0.0F);
            }
            AABB aabb = (new AABB(targetPos)).inflate(0.4);
            for(LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, aabb)) {
                if (!entity.is(user)) {
                    int finalDamage = (int)((double)damage * ((double)1.0F - this.dropOffFactor * Math.pow((double)i / (double)range, (double)2.0F)));
                    entity.hurt(level.damageSources().sonicBoom(user), (float)finalDamage);
                    double horizontalResistance = (double)1.0F - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                    double verticalResistance = (double)1.0F - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                    entity.push(facing.x * horizontalResistance, facing.y * verticalResistance, facing.z * horizontalResistance);
                }
            }
        }
        user.playSound(SoundEvents.WARDEN_SONIC_BOOM);
        ToolDamageUtil.damage(tool, 2, user, null);
        ModifierCooldowns.getModifierCooldowns(user).addCooldown(modifier.getId(), 20);
        if (user instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(tool.getItem()));
        }
    }

    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 72000;
    }

    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return BlockingModifier.blockWhileCharging(tool, UseAnim.BOW);
    }
}
