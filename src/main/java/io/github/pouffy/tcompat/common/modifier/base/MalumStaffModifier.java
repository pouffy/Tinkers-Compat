package io.github.pouffy.tcompat.common.modifier.base;

import io.github.pouffy.tcompat.common.cooldown.ClientModifierCooldowns;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.malum.MalumHandler;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

public abstract class MalumStaffModifier extends NoLevelsModifier implements GeneralInteractionModifierHook, UsingToolModifierHook {
    public final float chargeDuration;

    public MalumStaffModifier(float chargeDuration) {
        this.chargeDuration = chargeDuration;
    }

    public void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT, ModifierHooks.TOOL_USING);
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void spawnChargeParticles(Level level, LivingEntity entity, Vec3 pos, IToolStackView tool, float chargePercentage);

    public abstract int getCooldownDuration(Level level, LivingEntity entity);

    public abstract int getProjectileCount(Level level, LivingEntity entity, float chargePercentage);

    public abstract void fireProjectile(LivingEntity entity, IToolStackView tool, Level level, InteractionHand hand, float chargePercentage, int count);

    @Override
    public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source) {
        if (!tool.isBroken() && source == InteractionSource.RIGHT_CLICK && !ClientModifierCooldowns.getCooldowns().isOnCooldown(modifier.getId())) {
            GeneralInteractionModifierHook.startUsingWithDrawtime(tool, modifier.getId(), player, hand, 1.5F);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public void beforeReleaseUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int useDuration, int timeLeft, ModifierEntry activeModifier) {
        Level level = entity.level();
        if (GeneralInteractionModifierHook.getActiveModifier(tool) != modifier) return;
        float chargePercentage = Math.min(this.chargeDuration, (float)(this.getUseDuration(tool, modifier) - timeLeft)) / this.chargeDuration;
        int projectileCount = this.getProjectileCount(level, entity, chargePercentage);
        if (projectileCount > 0) {
            InteractionHand hand = entity.getUsedItemHand();
            if (!level.isClientSide) {
                double magicDamage = ObjectRetriever.getAttribute("lodestone:magic_damage").map(attr -> entity.getAttributes().getValue(attr)).orElse(0.0D);
                if (magicDamage == 0.0F) {
                    float pitch = Mth.nextFloat(level.random, 0.5F, 0.8F);
                    ObjectRetriever.getSound("malum:staff_sizzles").ifPresent(sound -> level.playSound(null, entity.blockPosition(), sound, SoundSource.PLAYERS, 0.5F, pitch));
                    entity.swing(hand, true);
                    return;
                }
                for(int i = 0; i < projectileCount; ++i) {
                    this.fireProjectile(entity, tool, level, hand, chargePercentage, i);
                }

                if (entity instanceof Player player) {
                    if (!player.getAbilities().instabuild) {
                        ToolDamageUtil.damage(tool, 2, entity, null);
                        MalumHandler.staffReserve(player, modifier, getCooldownDuration(level, player));
                    }
                    player.swing(hand, true);
                }
            }
        } else {
            float pitch = Mth.nextFloat(level.random, 0.5F, 0.8F);
            ObjectRetriever.getSound("malum:staff_sizzles").ifPresent(sound -> level.playSound(null, entity.blockPosition(), sound, SoundSource.PLAYERS, 0.5F, pitch));
        }
    }

    public void onUsingTick(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        int useDuration = this.getUseDuration(tool, modifier);
        if (GeneralInteractionModifierHook.getActiveModifier(tool) != modifier) return;
        float chargePercentage = Math.min(this.chargeDuration, (float)(useDuration - timeLeft)) / this.chargeDuration;
        Level level = entity.level();
        if (level.isClientSide) {
            InteractionHand hand = entity.getUsedItemHand();
            Vec3 pos = this.getProjectileSpawnPos(entity, hand, 1.5F, 0.6F);
            this.spawnChargeParticles(level, entity, pos, tool, chargePercentage);
        }

        if ((float)timeLeft == (float)useDuration - this.chargeDuration) {
            float pitch = Mth.nextFloat(level.random, 1.2F, 1.6F);
            ObjectRetriever.getSound("malum:staff_charged").ifPresent(sound -> level.playSound(null, entity.blockPosition(), sound, SoundSource.PLAYERS, 1.25F, pitch));
        } else if ((float)timeLeft > (float)useDuration - this.chargeDuration && timeLeft % 5 == 0) {
            float pitch = 0.25F + chargePercentage + Mth.nextFloat(level.random, 0.2F, 0.6F);
            ObjectRetriever.getSound("malum:staff_powers_up").ifPresent(sound -> level.playSound(null, entity.blockPosition(), sound, SoundSource.PLAYERS, 0.75F, pitch));
        } else if (timeLeft % 5 == 0) {
            float pitch = Mth.nextFloat(level.random, 0.2F, 0.6F);
            ObjectRetriever.getSound("malum:staff_powers_up").ifPresent(sound -> level.playSound(null, entity.blockPosition(), sound, SoundSource.PLAYERS, 0.5F, pitch));
        }
    }

    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 72000;
    }

    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return BlockingModifier.blockWhileCharging(tool, UseAnim.BOW);
    }

    public Vec3 getProjectileSpawnPos(LivingEntity player, InteractionHand hand, float distance, float spread) {
        int angle = hand == InteractionHand.MAIN_HAND ? 225 : 90;
        double radians = Math.toRadians((float)angle - player.yHeadRot);
        return player.position().add(player.getLookAngle().scale(distance)).add((double)spread * Math.sin(radians), player.getBbHeight() * 0.9F, (double)spread * Math.cos(radians));
    }
}
