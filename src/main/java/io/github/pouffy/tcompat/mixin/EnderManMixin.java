package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.betterend.BetterendInit;
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
        if (CompatHelper.isLoaded("betterend")) {
            CompatHelper.asTool(player.getInventory().getArmor(3), (tool) -> {
                if (tool.getModifierLevel(BetterendInit.voidTouched.getId()) > 0) {
                    info.setReturnValue(false);
                }
            });
        }
    }
}
