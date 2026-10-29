package io.github.pouffy.tcompat.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.pouffy.tcompat.compat.estrogen.EstrogenHandler;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientTextTooltip.class)
public class ClientTextTooltipMixin {
    @Shadow
    @Final
    private FormattedCharSequence text;

    @ModifyReturnValue(method = "getWidth", at = @At("RETURN"))
    private int modifyWidth(int original, @Local(argsOnly = true) Font font) {
        if (EstrogenHandler.TextRendererFeatures.getUwufy()) return font.width(EstrogenHandler.UwUOrderedText.orderedToUwUText(text));
        return original;
    }
}
