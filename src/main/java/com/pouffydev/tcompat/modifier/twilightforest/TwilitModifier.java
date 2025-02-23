package com.pouffydev.tcompat.modifier.twilightforest;

import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.ArmorWalkModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.ProtectionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.DamageDealtModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.item.armor.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.UUID;
import java.util.function.BiConsumer;

public class TwilitModifier extends NoLevelsModifier implements BreakSpeedModifierHook, DamageDealtModifierHook, ProtectionModifierHook {

    public static final ResourceKey<Level> DIMENSION_KEY = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("twilightforest:twilight_forest"));
    public static final ResourceKey<DimensionType> DIMENSION_TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation("twilightforest:twilight_forest"));
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.DAMAGE_DEALT, ModifierHooks.BREAK_SPEED, ModifierHooks.PROTECTION);
    }

    @Override
    public void onDamageDealt(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, LivingEntity target, DamageSource source, float amount, boolean isDirectDamage) {
        LivingEntity attacker = context.getEntity();
        if (tool.getItem() instanceof ModifiableArmorItem) return;
        HolderLookup.RegistryLookup<DimensionType> dimensionLookup = attacker.level().registryAccess().lookupOrThrow(Registries.DIMENSION_TYPE);
        if (dimensionLookup.get(DIMENSION_TYPE_KEY).isPresent() && !attacker.level().dimensionTypeId().equals(DIMENSION_TYPE_KEY) && source != null) {
            target.hurt(source, 2f);
        }
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        if(event.getEntity().level().dimension().equals(DIMENSION_KEY)) {
            event.setNewSpeed(event.getNewSpeed() + 2.0F);
        }
    }

    @Override
    public float getProtectionModifier(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float modifierValue) {
        if (!(tool.getItem() instanceof ModifiableArmorItem)) return 0;
        HolderLookup.RegistryLookup<DimensionType> dimensionLookup = context.getEntity().level().registryAccess().lookupOrThrow(Registries.DIMENSION_TYPE);
        if (dimensionLookup.get(DIMENSION_TYPE_KEY).isPresent() && !context.getEntity().level().dimensionTypeId().equals(DIMENSION_TYPE_KEY)) {
            return 2f;
        }
        return 0;
    }
}
