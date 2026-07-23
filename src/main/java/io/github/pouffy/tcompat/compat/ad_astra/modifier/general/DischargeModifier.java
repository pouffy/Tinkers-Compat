package io.github.pouffy.tcompat.compat.ad_astra.modifier.general;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.BlockInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.hook.interaction.SlotStackModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

public class DischargeModifier extends Modifier implements BlockInteractionModifierHook, ValidateModifierHook, ModifierRemovalHook, BlockBreakModifierHook, OnAttackedModifierHook, MeleeHitModifierHook, SlotStackModifierHook, ProjectileLaunchModifierHook {

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.VALIDATE, ModifierHooks.REMOVE, ModifierHooks.BLOCK_INTERACT, ModifierHooks.BLOCK_BREAK, ModifierHooks.ON_ATTACKED, ModifierHooks.MELEE_HIT, ModifierHooks.SLOT_STACK, ModifierHooks.PROJECTILE_LAUNCH);
        hookBuilder.addModule(StatBoostModule.add(ToolEnergyCapability.MAX_STAT).amount(10000, 5000));
    }

    @Override
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry, @Nullable RegistryAccess access) {
        return Component.translatable(this.getTranslationKey() + ".current").append(ToolEnergyCapability.MAX_STAT.formatContents(ToolEnergyCapability.getEnergy(tool), ToolEnergyCapability.getMaxEnergy(tool)));
    }

    public int getPriority() {
        return 299;
    }

    @Nullable
    public Component validate(IToolStackView tool, ModifierEntry modifier) {
        ToolEnergyCapability.checkEnergy(tool);
        return null;
    }

    @Nullable
    public Component onRemoved(IToolStackView tool, Modifier modifier) {
        ToolEnergyCapability.checkEnergy(tool);
        return null;
    }

    @Override
    public InteractionResult beforeBlockUse(IToolStackView tool, ModifierEntry modifier, UseOnContext context, InteractionSource source) {
        BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
        int toolEnergy = ToolEnergyCapability.getEnergy(tool);
        if (toolEnergy > 0 && blockEntity != null) {
            var energyCap = blockEntity.getCapability(ForgeCapabilities.ENERGY);
            energyCap.ifPresent((energy) -> {
                int received = energy.receiveEnergy(toolEnergy, false);
                ToolEnergyCapability.setEnergy(tool, Math.max(0, toolEnergy - received));
            });
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean overrideStackedOnOther(IToolStackView heldTool, ModifierEntry modifier, Slot slot, Player player) {
        ItemStack slotStack = slot.getItem();
        int toolEnergy = ToolEnergyCapability.getEnergy(heldTool);
        if (toolEnergy > 0 && !slotStack.isEmpty()) {
            var energyCap = slotStack.getCapability(ForgeCapabilities.ENERGY);
            AtomicInteger recieved = new AtomicInteger(0);
            energyCap.ifPresent((energy) -> {
                recieved.set(energy.receiveEnergy(toolEnergy, false));
            });
            if (recieved.get() > 0) {
                ToolEnergyCapability.setEnergy(heldTool, Math.max(0, toolEnergy - recieved.get()));
            }
            return true;
        }
        return false;
    }

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        charge(tool, Math.round(100 * amount));
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        charge(tool, Math.round(50 * damageDealt));
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        charge(tool, 50);
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @org.jetbrains.annotations.Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        charge(tool, primary ? 100 : 50);
    }

    private void charge(IToolStackView tool, int amount) {
        if (amount > 0) {
            ToolEnergyCapability.setEnergy(tool, Math.min(ToolEnergyCapability.getMaxEnergy(tool), ToolEnergyCapability.getEnergy(tool) + amount));
        }
    }
}
