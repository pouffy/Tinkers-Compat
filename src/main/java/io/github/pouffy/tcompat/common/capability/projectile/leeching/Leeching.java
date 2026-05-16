package io.github.pouffy.tcompat.common.capability.projectile.leeching;

import io.github.pouffy.tcompat.common.capability.TCompatCapabilities;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;

public interface Leeching extends INBTSynchable<CompoundTag> {
    Projectile getProjectile();

    static LazyOptional<Leeching> get(Projectile projectile) {
        return projectile.getCapability(TCompatCapabilities.LEECHING_CAPABILITY);
    }

    void setLeeching(boolean isLeeching);
    boolean isLeeching();

    void setOwner(Entity owner);
    Entity getOwner();

    default void damageShield(Player player, float damage) {
        if (damage >= 3.0F && player.getUseItem().getItem().canPerformAction(player.getUseItem(), ToolActions.SHIELD_BLOCK)) {
            ItemStack copyBeforeUse = player.getUseItem().copy();
            int i = 1 + Mth.floor(damage);
            player.getUseItem().hurtAndBreak(i, player, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.CHEST));
            if (player.getUseItem().isEmpty()) {
                InteractionHand hand = player.getUsedItemHand();
                ForgeEventFactory.onPlayerDestroyItem(player, copyBeforeUse, hand);
                if (hand == InteractionHand.MAIN_HAND) {
                    player.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                } else {
                    player.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                }

                player.stopUsingItem();
                player.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + player.level().random.nextFloat() * 0.4F);
            }
        }
    }
}
