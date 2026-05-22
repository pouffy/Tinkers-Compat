package io.github.pouffy.tcompat.compat.malum;

import com.sammy.malum.core.handlers.SoulDataHandler;
import com.sammy.malum.core.helpers.ParticleHelper;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import team.lodestar.lodestone.helpers.SoundHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem.canSweep;

public class MalumHandler {

    public static float weightOfWorlds(float damage, LivingEntity attacker, Entity target) {
        if (!CompatHelper.isLoaded("malum")) return damage;
        float returning = damage;
        Level level = attacker.level();
        var optionalEffect = ObjectRetriever.getEffect("malum:grim_certainty");
        if (optionalEffect.isPresent() && !level.isClientSide()) {
            ParticleEffectType particleEffectType = ParticleEffectTypeRegistry.SCYTHE_SLASH;
            var effect = optionalEffect.get();
            if (attacker.hasEffect(effect) || level.random.nextFloat() < 0.25F) {
                returning *= 2.0F;
                SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 2.0F, 0.75F);
                SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 3.0F, 1.25F);
                SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 3.0F, 1.75F);
                particleEffectType = ParticleEffectTypeRegistry.WEIGHT_OF_WORLDS_CRIT;
                attacker.removeEffect(effect);
            }
            ParticleHelper.createSlashingEffect(particleEffectType).setVertical().spawnForwardSlashingParticle(attacker);
            SoundHelper.playSound(target, SoundRegistry.WEIGHT_OF_WORLDS_CUT.get(), 2.0F, 0.75F);
        }
        return returning;
    }

    public static boolean isCloaking(LivingEntity entity) {
        if (!CompatHelper.isLoaded("malum")) return false;
        EquipmentContext context = new EquipmentContext(entity);
        for (EquipmentSlot slotType : EquipmentSlot.values()) {
            if (ModifierUtil.validArmorSlot(entity, slotType)) {
                IToolStackView toolStack = context.getToolInSlot(slotType);
                if (toolStack != null && !toolStack.isBroken()) {
                    return toolStack.getModifier(TCModifiers.cloaking) != ModifierEntry.EMPTY;
                }
            }
        }
        if (context.getValidTool(EquipmentSlot.MAINHAND) != null) {
            IToolStackView toolStack = context.getToolInSlot(EquipmentSlot.MAINHAND);
            if (toolStack != null && !toolStack.isBroken()) {
                return toolStack.getModifier(TCModifiers.cloaking) != ModifierEntry.EMPTY;
            }
        }
        if (context.getValidTool(EquipmentSlot.OFFHAND) != null) {
            IToolStackView toolStack = context.getToolInSlot(EquipmentSlot.OFFHAND);
            if (toolStack != null && !toolStack.isBroken()) {
                return toolStack.getModifier(TCModifiers.cloaking) != ModifierEntry.EMPTY;
            }
        }
        return false;
    }

    public static void exposeSoul(LivingHurtEvent event) {
        if (!CompatHelper.isLoaded("malum")) return;
        if (!event.isCanceled() && !(event.getAmount() <= 0.0F)) {
            LivingEntity target = event.getEntity();
            DamageSource source = event.getSource();
            Entity attacker = source.getEntity();
            if (attacker instanceof LivingEntity living) {
                ItemStack stack = living.getMainHandItem();
                CompatHelper.asTool(stack, (tool) -> {
                    List<ModifierEntry> validList = new ArrayList<>();
                    for (ModifierEntry entry : tool.getModifierList()) {
                        if (entry.getModifier().getHooks().hasHook(GlobalInit.SOUL_EXPOSURE)) {
                            validList.add(entry);
                        }
                    }
                    List<ModifierEntry> list = validList.stream().sorted(Comparator.comparingInt(entry -> -entry.getModifier().getPriority())).toList();
                    boolean shouldWork = !list.isEmpty() && list.stream().findFirst().map(entry -> entry.getHook(GlobalInit.SOUL_EXPOSURE).canUse(tool, entry)).orElse(false);
                    if (shouldWork) {
                        SoulDataHandler.exposeSoul(target);
                    }
                });
            }
        }
    }

    public static void deliverance(LivingEntity target, LivingEntity attacker) {
        SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 2.0F, 1.25F);
        SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 3.0F, 1.75F);
        ParticleHelper.SlashParticleEffectBuilder particle = ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.EDGE_OF_DELIVERANCE_CRIT);
        if (!canSweep(attacker)) {
            particle.setVertical();
        }

        particle.spawnTargetBoundSlashingParticle(attacker, target);
    }
}
