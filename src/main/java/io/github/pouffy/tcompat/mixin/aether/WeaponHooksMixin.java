package io.github.pouffy.tcompat.mixin.aether;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.capability.PersistentDataCapability;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.item.armor.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Mixin(AbilityHooks.WeaponHooks.class)
public class WeaponHooksMixin {

    @Inject(method = "reduceWeaponEffectiveness", at = @At("HEAD"), remap = false, cancellable = true)
    private static void reduceWeaponEffectiveness(LivingEntity target, Entity source, float damage, CallbackInfoReturnable<Float> cir) {
        if (AetherConfig.SERVER.tools_debuff.get() && !target.level().isClientSide()) {
            double pow = Math.max(Math.pow(damage, (double)damage > (double)1.0F ? 0.6 : 1.6), 1.0F);
            if (source instanceof LivingEntity livingEntity) {
                ItemStack stack = livingEntity.getMainHandItem();
                if (stack.getItem() instanceof ModifiableItem) {
                    if ((target.getType().getDescriptionId().startsWith("entity.aether") || target.getType().is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY)) && !target.getType().is(AetherTags.Entities.TREATED_AS_VANILLA_ENTITY) && !stack.isEmpty() && !stack.getAttributeModifiers(EquipmentSlot.MAINHAND).isEmpty() && stack.getAttributeModifiers(EquipmentSlot.MAINHAND).containsKey(Attributes.ATTACK_DAMAGE) && !stack.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).isEmpty()) {
                        double value = stack.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).stream().mapToDouble(AttributeModifier::getAmount).sum();
                        if (value > livingEntity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) && !stack.getItem().getDescriptionId().startsWith("item.aether.") && !stack.is(AetherTags.Items.TREATED_AS_AETHER_ITEM)) {
                            EquipmentHelper.asTool(stack, (tool) -> {
                                List<ModifierEntry> validList = new ArrayList<>();
                                for (ModifierEntry entry : tool.getModifierList()) {
                                    if (entry.getModifier().getHooks().hasHook(GlobalInit.AETHER_FORGED)) {
                                        validList.add(entry);
                                    }
                                }
                                List<ModifierEntry> list = validList.stream().sorted(Comparator.comparingInt(entry -> -entry.getModifier().getPriority())).toList();
                                boolean shouldWork = !list.isEmpty() && list.stream().findFirst().map(entry -> entry.getHook(GlobalInit.AETHER_FORGED).canUse(tool, entry)).orElse(false);
                                if (!shouldWork)
                                    cir.setReturnValue((float) pow);
                                else cir.setReturnValue(damage);
                            });
                        }
                    }
                }
            } else if (source instanceof Projectile projectile && (target.getType().getDescriptionId().startsWith("entity.aether") || target.getType().is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY)) && !target.getType().is(AetherTags.Entities.TREATED_AS_VANILLA_ENTITY) && !source.getType().getDescriptionId().startsWith("entity.aether") && !source.getType().is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY)) {
                ModifierNBT modifiers = EntityModifierCapability.getOrEmpty(projectile);
                if (!modifiers.isEmpty()) {
                    ModDataNBT nbt = PersistentDataCapability.getOrWarn(projectile);
                    List<ModifierEntry> validList = new ArrayList<>();
                    for (ModifierEntry entry : modifiers.getModifiers()) {
                        if (entry.getModifier().getHooks().hasHook(GlobalInit.AETHER_FORGED)) {
                            validList.add(entry);
                        }
                    }
                    List<ModifierEntry> list = validList.stream().sorted(Comparator.comparingInt(entry -> -entry.getModifier().getPriority())).toList();
                    boolean shouldWork = !list.isEmpty() && list.stream().findFirst().map(entry -> entry.getHook(GlobalInit.AETHER_FORGED).canProjectileUse(modifiers, nbt, entry)).orElse(false);
                    if (!shouldWork)
                        cir.setReturnValue((float) pow);
                    else cir.setReturnValue(damage);
                }
            }
        }
    }

    @Inject(method = "reduceArmorEffectiveness", at = @At("HEAD"), remap = false, cancellable = true)
    private static void reduceArmorEffectiveness(LivingEntity target, Entity source, float damage, CallbackInfoReturnable<Float> cir) {
        if (source != null && (source.getType().getDescriptionId().startsWith("entity.aether") || source.getType().is(AetherTags.Entities.TREATED_AS_AETHER_ENTITY) && !source.getType().is(AetherTags.Entities.TREATED_AS_VANILLA_ENTITY))) {
            for (ItemStack stack : target.getArmorSlots()) {
                if (stack.getItem() instanceof ModifiableArmorItem armorItem) {
                    if (!stack.getItem().getDescriptionId().startsWith("item.aether.") && !stack.is(AetherTags.Items.TREATED_AS_AETHER_ITEM) && !stack.getAttributeModifiers(armorItem.getEquipmentSlot()).isEmpty() && stack.getAttributeModifiers(armorItem.getEquipmentSlot()).containsKey(Attributes.ARMOR) && !stack.getAttributeModifiers(armorItem.getEquipmentSlot()).get(Attributes.ARMOR).isEmpty()) {
                        double value = stack.getAttributeModifiers(armorItem.getEquipmentSlot()).get(Attributes.ARMOR).stream().mapToDouble((attributeModifier) -> attributeModifier.getAmount() / (double)15.0F).sum();
                        EquipmentHelper.asTool(stack, (tool) -> {
                            List<ModifierEntry> validList = new ArrayList<>();
                            for (ModifierEntry entry : tool.getModifierList()) {
                                if (entry.getModifier().getHooks().hasHook(GlobalInit.AETHER_FORGED)) {
                                    validList.add(entry);
                                }
                            }
                            List<ModifierEntry> list = validList.stream().sorted(Comparator.comparingInt(entry -> -entry.getModifier().getPriority())).toList();
                            boolean shouldWork = !list.isEmpty() && list.stream().findFirst().map(entry -> entry.getHook(GlobalInit.AETHER_FORGED).canUse(tool, entry)).orElse(false);
                            if (!shouldWork)
                                cir.setReturnValue((float)((double)damage + value));
                            else cir.setReturnValue(damage);
                        });
                    }
                }
            }
        }
    }
}
