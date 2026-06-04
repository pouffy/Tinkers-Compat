package io.github.pouffy.tcompat.compat.quark;

import io.github.pouffy.tcompat.common.util.CompatHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.violetmoon.zeta.registry.CreativeTabManager;
import slimeknights.tconstruct.world.TinkerWorld;
import slimeknights.tconstruct.world.entity.EnderSlimeEntity;
import slimeknights.tconstruct.world.entity.SkySlimeEntity;
import slimeknights.tconstruct.world.entity.TerracubeEntity;

public class QuarkHandler {

    public static void addToTab(Item item, Item nextTo, boolean behind, ResourceKey<CreativeModeTab> tab) {
        if (!CompatHelper.isLoaded("quark")) return;
        LoadedOnly.addToTab(item, nextTo, behind, tab);
    }

    public static void pickupSlime(PlayerInteractEvent.EntityInteract event) {
        if(event.getTarget() != null && canPickUp(event.getTarget())) {
            Player player = event.getEntity();
            InteractionHand hand = InteractionHand.MAIN_HAND;
            ItemStack stack = player.getMainHandItem();
            if(stack.isEmpty() || stack.getItem() != Items.BUCKET) {
                stack = player.getOffhandItem();
                hand = InteractionHand.OFF_HAND;
            }
            if(!stack.isEmpty() && stack.getItem() == Items.BUCKET) {
                if (!event.getLevel().isClientSide) {
                    ItemStack outStack = pickupResult(event.getTarget());
                    if (!outStack.isEmpty()) {
                        if(stack.getCount() == 1)
                            player.setItemInHand(hand, outStack);
                        else {
                            stack.shrink(1);
                            if(stack.getCount() == 0)
                                player.setItemInHand(hand, outStack);
                            else if(!player.getInventory().add(outStack))
                                player.drop(outStack, false);
                        }
                        event.getLevel().gameEvent(player, GameEvent.ENTITY_INTERACT, event.getTarget().position());
                        event.getTarget().discard();
                    }
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(player.level().isClientSide));
            }
        }
    }

    private static boolean canPickUp(Entity entity) {
        if (!entity.isAlive()) return false;
        if (entity.getType() == TinkerWorld.skySlimeEntity.get() && ((SkySlimeEntity) entity).getSize() == 1) {
            return true;
        }
        if (entity.getType() == TinkerWorld.enderSlimeEntity.get() && ((EnderSlimeEntity) entity).getSize() == 1) {
            return true;
        }
        if (entity.getType() == TinkerWorld.terracubeEntity.get() && ((TerracubeEntity) entity).getSize() == 1) {
            return true;
        }
        return false;
    }

    private static ItemStack pickupResult(Entity entity) {
        ItemStack result = ItemStack.EMPTY;
        if (!entity.isAlive()) return result;
        if (entity.getType() == TinkerWorld.skySlimeEntity.get() && ((SkySlimeEntity) entity).getSize() == 1) {
            result = new ItemStack(QuarkInit.skySlimeInABucket);
        }
        if (entity.getType() == TinkerWorld.enderSlimeEntity.get() && ((EnderSlimeEntity) entity).getSize() == 1) {
            result = new ItemStack(QuarkInit.enderSlimeInABucket);
        }
        if (entity.getType() == TinkerWorld.terracubeEntity.get() && ((TerracubeEntity) entity).getSize() == 1) {
            result = new ItemStack(QuarkInit.terracubeInABucket);
        }
        if (!result.isEmpty()) {
            CompoundTag cmp = entity.serializeNBT();
            result.getOrCreateTag().put("slime_nbt", cmp);
        }
        return result;
    }

    public static class LoadedOnly {
        public static void addToTab(Item item, Item nextTo, boolean behind, ResourceKey<CreativeModeTab> tab) {
            CreativeTabManager.addToCreativeTabNextTo(tab, item, nextTo, behind);
        }
    }
}
