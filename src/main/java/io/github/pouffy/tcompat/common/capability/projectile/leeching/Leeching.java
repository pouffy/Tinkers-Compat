package io.github.pouffy.tcompat.common.capability.projectile.leeching;

import io.github.pouffy.tcompat.common.capability.TCompatCapabilities;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;

//TODO: Look over this. Some effects don't work properly.
public interface Leeching extends INBTSynchable<CompoundTag> {
    Projectile getProjectile();

    static LazyOptional<Leeching> get(Projectile projectile) {
        return projectile.getCapability(TCompatCapabilities.LEECHING_CAPABILITY);
    }

    void setLeeching(boolean isLeeching);
    boolean isLeeching();
    void setAmphithere(boolean isAmphithere);
    boolean isAmphithere();
    void setStymphalian(boolean isStymphalian);
    boolean isStymphalian();

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

    default void amphithereEffect(LivingEntity target) {
        RandomSource random = getProjectile().level().random;
        target.hasImpulse = true;
        double xRatio = getProjectile().getDeltaMovement().x;
        double zRatio = getProjectile().getDeltaMovement().z;
        float strength = -1.4F;
        float f = Mth.sqrt((float)(xRatio * xRatio + zRatio * zRatio));
        target.setDeltaMovement(target.getDeltaMovement().multiply(0.5F, 1.0F, 0.5F).subtract(xRatio / (double)f * (double)strength, 0.0F, zRatio / (double)f * (double)strength).add(0.0F, 0.6, 0.0F));
        if (getProjectile().level().isClientSide) {
            for(int height = 0; height < 1 + random.nextInt(2); ++height) {
                for(int i = 0; i < 20; ++i) {
                    double d0 = random.nextGaussian() * 0.02;
                    double d1 = random.nextGaussian() * 0.02;
                    double d2 = random.nextGaussian() * 0.02;
                    double d3 = 10.0F;
                    double xRatio1 = getProjectile().getDeltaMovement().x * (double)getProjectile().getBbWidth();
                    double zRatio1 = getProjectile().getDeltaMovement().z * (double)getProjectile().getBbWidth();
                    getProjectile().level().addParticle(ParticleTypes.CLOUD, getProjectile().getX() + xRatio1 + (double)(random.nextFloat() * getProjectile().getBbWidth() * 1.0F) - (double)getProjectile().getBbWidth() - d0 * d3, getProjectile().getY() + (double)(random.nextFloat() * getProjectile().getBbHeight()) - d1 * d3, getProjectile().getZ() + zRatio1 + (double)(random.nextFloat() * getProjectile().getBbWidth() * 1.0F) - (double)getProjectile().getBbWidth() - d2 * d3, d0, d1, d2);
                }
            }
        } else {
            getProjectile().level().broadcastEntityEvent(getProjectile(), (byte)20);
        }
    }
}
