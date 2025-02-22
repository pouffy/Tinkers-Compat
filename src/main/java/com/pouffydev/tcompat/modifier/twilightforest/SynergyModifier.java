package com.pouffydev.tcompat.modifier.twilightforest;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.data.TComTags;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import twilightforest.util.TwilightItemTier;

public class SynergyModifier extends NoLevelsModifier implements InventoryTickModifierHook {
    private static final float REPAIR_DAMPENER = 1f / 256f;

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK);
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide() && holder instanceof Player player && !(holder instanceof FakePlayer)) {
            if (!isCorrectSlot) return;
            if (!needsRepair(tool)) return;

            int healPower = 0;

            NonNullList<ItemStack> playerInv = player.getInventory().items;

            for (int i = 0; i < 9; i++) {
                if (i != itemSlot) {
                    ItemStack invSlot = playerInv.get(i);
                    if (invSlot.is(TComTags.Items.STEELEAF_INGOTS)) {
                        healPower += invSlot.getCount();
                    } else if (invSlot.is(TComTags.Items.STEELEAF_BLOCKS)) {
                        healPower += invSlot.getCount() * 9;
                    }
                    if (TCompat.isClassFound("twilightforest.util.TwilightItemTier")) {
                        if (hasToolMaterial(invSlot, TwilightItemTier.STEELEAF)) {
                            healPower += 1;
                        }
                    }
                }
            }

            ToolDamageUtil.repair(tool, averageInt(healPower * REPAIR_DAMPENER));
        }
    }

    private static boolean needsRepair(IToolStackView tool) {
        return tool.getDamage() > 0 && !tool.isBroken();
    }

    private static int averageInt(float value) {
        double floor = Math.floor(value);
        double rem = value - floor;
        return (int) floor + (Math.random() < rem ? 1 : 0);
    }

    public static boolean hasToolMaterial(ItemStack stack, Tier tier) {
        Item item = stack.getItem();

        if (item instanceof TieredItem tieredItem && tier.equals(tieredItem.getTier())) {
            return true;
        }

        return false;
    }
}
