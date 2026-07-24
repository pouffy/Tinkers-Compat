package io.github.pouffy.tcompat.common.capability.projectile.ability;

import io.github.pouffy.tcompat.common.capability.projectile.ability.types.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.ForgeEventFactory;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.capability.PersistentDataCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

public class ProjectileAbilityHooks {

    /**
     * Creates a cloud particle trail
     * @see AmphithereAbility
     */
    public static final String AMPHITHERE = "amphithere";
    /**
     * Instantly discards the projectile on its first tick
     * @see InstantDiscardAbility
     */
    public static final String INSTANT_DISCARD = "instant_discard";
    /**
     * Creates a green particle trail
     * @see LeechingAbility
     */
    public static final String LEECHING = "leeching";
    /**
     * Projectile ignites targets
     * <p>
     * Creates a fire particle trail
     * </p>
     * @see PhoenixTouchedAbility
     */
    public static final String PHOENIX_TOUCHED = "phoenix_touched";
    /**
     * Projectile travels straight for a period of time before dropping
     * @see StymphalianAbility
     */
    public static final String STYMPHALIAN = "stymphalian";
    /**
     * Projectile explodes into multiple Void Shards on impact
     * @see VoidScatterAbility
     */
    public static final String VOID_SCATTER = "void_scatter";
    /**
     * Projectile cryogenically freezes the target.
     * @see CryogenicAbility
     */
    public static final String CRYOGENIC = "cryogenic";

    /**
     * Adds modifier data to launched projectiles. Used when summoning non-vanilla projectiles via modifiers.
     * @param tool the current ToolStack
     * @param shooter the shooting entity
     * @param projectile the projectile to be modified
     * @param primary if the projectile should be considered the "main" projectile
     */
    public static void addModifiersToProjectile(IToolStackView tool, LivingEntity shooter, Projectile projectile, boolean primary) {
        ModifierNBT modifiers = tool.getModifiers();
        EntityModifierCapability.getCapability(projectile).addModifiers(modifiers);
        ModDataNBT arrowData = PersistentDataCapability.getOrWarn(projectile);
        for(ModifierEntry entry : modifiers.getModifiers()) {
            entry.getHook(ModifierHooks.PROJECTILE_LAUNCH).onProjectileLaunch(tool, entry, shooter, ItemStack.EMPTY, projectile, null, arrowData, primary);
        }
    }

    public static void damageShield(Player player, float damage) {
        ItemStack shield = player.getUseItem();

        if (damage < 3.0F || !player.isBlocking() || !shield.canPerformAction(ToolActions.SHIELD_BLOCK)) return;

        InteractionHand hand = player.getUsedItemHand();
        ItemStack copyBeforeUse = shield.copy();

        int durabilityDamage = 1 + Mth.floor(damage);
        shield.hurtAndBreak(durabilityDamage, player, entity -> entity.broadcastBreakEvent(
                hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND)
        );

        if (shield.isEmpty()) {
            ForgeEventFactory.onPlayerDestroyItem(player, copyBeforeUse, hand);

            if (hand == InteractionHand.MAIN_HAND) player.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            else player.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);

            player.stopUsingItem();
            player.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + player.level().random.nextFloat() * 0.4F);
        }
    }
}
