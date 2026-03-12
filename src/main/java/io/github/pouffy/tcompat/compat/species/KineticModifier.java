package io.github.pouffy.tcompat.compat.species;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.UsingToolModifierHook;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class KineticModifier extends Modifier implements UsingToolModifierHook, OnAttackedModifierHook, TooltipModifierHook {

    public static final ResourceLocation STORED_DAMAGE = TCompat.getResource("stored_damage");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOL_USING, ModifierHooks.ON_ATTACKED);
    }

    @Override
    public Component getDisplayName(int level) {
        return ModifierLevelDisplay.NO_LEVELS.nameForLevel(this, level);
    }

    @Override
    public void afterStopUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int useDuration, int timeLeft, ModifierEntry activeModifier) {
        var data = tool.getPersistentData();
        if (data.contains(STORED_DAMAGE) && data.getFloat(STORED_DAMAGE) > 0.0F) {
            this.damageTargets(tool, entity.level(), data.getFloat(STORED_DAMAGE), entity);
        }
    }

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        LivingEntity self = context.getEntity();
        var data = tool.getPersistentData();
        if (amount > 0.0F && self.isDamageSourceBlocked(source)) {
            if (self instanceof Player player && amount > 40) {
                player.disableShield(true);
            }
            data.putFloat(STORED_DAMAGE, Math.min(data.getFloat(STORED_DAMAGE) + amount, 40));
            ObjectRetriever.getSound("species:item.ricoshield.absorb").ifPresent(sound -> {
                self.level().playSound(null, self.blockPosition(), sound, SoundSource.PLAYERS, 1F, data.getFloat(STORED_DAMAGE) * 0.05F);
            });
        }
    }

    private void damageTargets(IToolStackView tool, Level level, float amount, LivingEntity player) {
        var data = tool.getPersistentData();
        List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(4.0F), EntitySelector.NO_CREATIVE_OR_SPECTATOR);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(SpeciesInit.SMALL_KINETIC_ENERGY.get(), player.position().x, player.position().y + 0.01, player.position().z, 1, 0.0F, 0.0F, 0.0F, 0.5F);
        }

        ObjectRetriever.getSound("species:item.ricoshield.attack").ifPresent(sound -> {
            level.playSound(player, player.blockPosition(), sound, SoundSource.PLAYERS, 1.0F, 1.0F);
        });

        for(LivingEntity target : list) {
            if (target != player) {
                if (target instanceof TamableAnimal) {
                    TamableAnimal tamableAnimal = (TamableAnimal)target;
                    if (tamableAnimal.getOwner() == player) {
                        continue;
                    }
                }

                Vec3 enemyPos = target.position().subtract(player.position());
                Vec3 normalizedDirection = enemyPos.normalize();
                double knockbackXZ = (double)0.75F * ((double)1.0F - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double knockbackY = 0.15 * ((double)1.0F - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                target.push(normalizedDirection.x() * knockbackXZ, normalizedDirection.y() * knockbackY, normalizedDirection.z() * knockbackXZ);
                double distanceFromEnemy = target.position().distanceTo(player.position());
                float scalingFactor;
                if (distanceFromEnemy <= (double)1.0F) {
                    scalingFactor = 1.0F;
                } else if (distanceFromEnemy <= (double)2.0F) {
                    scalingFactor = 0.8F;
                } else if (distanceFromEnemy <= (double)3.0F) {
                    scalingFactor = 0.65F;
                } else {
                    scalingFactor = 0.5F;
                }

                amount *= scalingFactor;
                var damage = player.damageSources().source(CompatHelper.damageKey("species:kinetic"), target, player);
                target.hurt(damage, amount);
                player.doHurtTarget(target);
            }
        }

        if (player instanceof Player player1) {
            player1.getCooldowns().addCooldown(tool.getItem(), (int)(data.getFloat(STORED_DAMAGE) / 4.0F) * 20);
        }

        data.putFloat(STORED_DAMAGE, 0.0F);
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        var data = tool.getPersistentData();
        var storedDamage = data.getFloat(STORED_DAMAGE);
        tooltip.add(Component.translatable("modifier.tcompat.kinetic.tooltip.damage", storedDamage).withStyle(style -> style.withColor(0xE21447)));
    }
}
