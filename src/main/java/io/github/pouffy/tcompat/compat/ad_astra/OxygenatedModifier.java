package io.github.pouffy.tcompat.compat.ad_astra;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidType;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.DamageDealtModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper.TANK_HELPER;

public class OxygenatedModifier extends Modifier implements BlockBreakModifierHook, OnAttackedModifierHook, DamageDealtModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BLOCK_BREAK, ModifierHooks.ON_ATTACKED, ModifierHooks.DAMAGE_DEALT);
        hookBuilder.addModule(ToolTankHelper.TANK_HANDLER);
        hookBuilder.addModule(StatBoostModule.add(ToolTankHelper.CAPACITY_STAT).eachLevel(FluidType.BUCKET_VOLUME));
    }

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        LivingEntity self = context.getEntity();
        replenish(tool, self);
    }

    @Override
    public void onDamageDealt(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, LivingEntity target, DamageSource source, float amount, boolean isDirectDamage) {
        var attacker = context.getEntity();
        if (amount < 0 && !target.isDamageSourceBlocked(source)) {
            replenish(tool, attacker);
        }
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        final ServerPlayer player = context.getPlayer();
        replenish(tool, player);
    }

    private void replenish(IToolStackView tool, LivingEntity holder) {
        var fluid = TANK_HELPER.getFluid(tool);
        if (fluid.getFluid().is(TCTags.Fluids.OXYGEN)) {
            int oxygen = fluid.getAmount();
            if (holder != null && !holder.level().isClientSide && oxygen > 0) {
                int toAdd = Math.min(oxygen, 20);
                holder.setAirSupply(Math.min(holder.getMaxAirSupply(), holder.getAirSupply() + toAdd));
                fluid.shrink(toAdd);
                TANK_HELPER.setFluid(tool, fluid);
            }
        }
    }


}
