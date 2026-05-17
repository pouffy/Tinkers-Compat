package io.github.pouffy.tcompat.common.capability.projectile.leeching;

import io.github.pouffy.tcompat.common.capability.TCompatCapabilities;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;

//TODO: Look over this. Some effects don't work properly.
public interface ProjectileAbility extends INBTSynchable<CompoundTag> {
    Projectile getProjectile();

    static LazyOptional<ProjectileAbility> get(Projectile projectile) {
        return projectile.getCapability(TCompatCapabilities.PROJECTILE_ABILITY_CAPABILITY);
    }

    void setLeeching(boolean isLeeching);
    boolean isLeeching();
    void setAmphithere(boolean isAmphithere);
    boolean isAmphithere();
    void setStymphalian(boolean isStymphalian);
    boolean isStymphalian();

    static void damageShield(Player player, float damage) {
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
