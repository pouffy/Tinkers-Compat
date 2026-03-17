package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.common.material.TCModifiers;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@Mixin(EnderMan.class)
public class EnderManMixin {

    @Inject(method = "isLookingAtMe", at = @At("HEAD"), cancellable = true)
    private void isLookingAtMe(Player player, CallbackInfoReturnable<Boolean> info) {
        ItemStack itemStack = player.getInventory().getArmor(3);
        ToolStack toolStack = ToolStack.from(itemStack);
        if (toolStack.getModifierLevel(TCModifiers.voidHoned) > 0) {
            info.setReturnValue(false);
        }
    }
}
