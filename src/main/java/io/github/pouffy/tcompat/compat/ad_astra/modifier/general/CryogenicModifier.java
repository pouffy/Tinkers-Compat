package io.github.pouffy.tcompat.compat.ad_astra.modifier.general;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.capability.projectile.ability.ProjectileAbility;
import io.github.pouffy.tcompat.common.capability.projectile.ability.ProjectileAbilityHooks;
import io.github.pouffy.tcompat.compat.ad_astra.AdAstraHandler;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.SlotStackModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.CapacityStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;

public class CryogenicModifier extends Modifier implements ValidateModifierHook, ModifierRemovalHook, ProjectileLaunchModifierHook, MeleeHitModifierHook, OnAttackedModifierHook, SlotStackModifierHook {
    public static final String FUEL_FORMAT = TCompat.makeDescriptionId("tool_stat", "cryogenic_fuel");
    public static final ResourceLocation CRYO_FUEL = TCompat.getResource("cryogenic_fuel");
    public static final CapacityStat MAX_STAT = new CapacityStat(new ToolStatId("tcompat", "max_cryogenic_fuel"), 0xa4e8da, FUEL_FORMAT);

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.VALIDATE, ModifierHooks.REMOVE, ModifierHooks.ON_ATTACKED, ModifierHooks.MELEE_HIT, ModifierHooks.SLOT_STACK, ModifierHooks.PROJECTILE_LAUNCH);
        hookBuilder.addModule(StatBoostModule.add(MAX_STAT).amount(1000, 500));
    }

    @Override
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry, @javax.annotation.Nullable RegistryAccess access) {
        return Component.translatable(this.getTranslationKey() + ".current").append(MAX_STAT.formatContents(getFuel(tool), getMaxFuel(tool)));
    }

    @Override
    public boolean overrideOtherStackedOnMe(IToolStackView slotTool, ModifierEntry modifier, ItemStack held, Slot slot, Player player, SlotAccess access) {
        if (!held.isEmpty() && AdAstraHandler.isCryoFreezerFuel(player.level(), held)) {
            int amount = AdAstraHandler.getCryoTime(player.level(), held);
            if (amount > 0 && receiveFuel(slotTool, amount, true) > 0) {
                receiveFuel(slotTool, amount, false);
                held.shrink(1);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifierEntry, LivingEntity livingEntity, Projectile projectile, @Nullable AbstractArrow abstractArrow, ModDataNBT modDataNBT, boolean b) {
        if (extractFuel(tool, 50, true) == 50) {
            ProjectileAbility.activate(projectile, ProjectileAbilityHooks.CRYOGENIC);
            extractFuel(tool, 50, false);
        }
    }

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifierEntry, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float v, boolean b) {
        if (extractFuel(tool, 50, true) == 50) {
            extractFuel(tool, 50, false);
        }
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (extractFuel(tool, 50, true) == 50) {
            extractFuel(tool, 50, false);
        }
    }

    @Nullable
    public Component validate(IToolStackView tool, ModifierEntry modifier) {
        checkFuel(tool);
        return null;
    }

    @Nullable
    public Component onRemoved(IToolStackView tool, Modifier modifier) {
        checkFuel(tool);
        return null;
    }

    public static int getMaxFuel(IToolStackView tool) {
        return tool.getStats().getInt(MAX_STAT);
    }

    public static int getFuel(IToolStackView tool) {
        return tool.getPersistentData().getInt(CRYO_FUEL);
    }

    private static void setFuelRaw(IToolStackView tool, int fuel) {
        if (fuel == 0) {
            tool.getPersistentData().remove(CRYO_FUEL);
        } else {
            tool.getPersistentData().putInt(CRYO_FUEL, fuel);
        }
    }

    public static void setFuel(IToolStackView tool, int fuel) {
        setFuelRaw(tool, Mth.clamp(fuel, 0, getMaxFuel(tool)));
    }

    public static void checkFuel(IToolStackView tool) {
        int fuel = getFuel(tool);
        if (fuel < 0) {
            setFuelRaw(tool, 0);
        } else {
            int capacity = getMaxFuel(tool);
            if (fuel > capacity) {
                setFuelRaw(tool, capacity);
            }
        }
    }

    public int receiveFuel(IToolStackView tool, int maxReceive, boolean simulate) {
        if (maxReceive <= 0) {
            return 0;
        } else {
            int current = getFuel(tool);
            int filled = Math.min(getMaxFuel(tool) - current, maxReceive);
            if (!simulate) {
                setFuelRaw(tool, current + filled);
            }

            return filled;
        }
    }

    public int extractFuel(IToolStackView tool, int maxExtract, boolean simulate) {
        if (maxExtract <= 0) {
            return 0;
        } else {
            int current = getFuel(tool);
            if (current <= 0) {
                return 0;
            } else {
                int drained = maxExtract;
                if (current < maxExtract) {
                    drained = current;
                }

                if (!simulate) {
                    setFuelRaw(tool, current - drained);
                }

                return drained;
            }
        }
    }
}
