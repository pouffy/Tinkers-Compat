package io.github.pouffy.tcompat.mixin.client;

import com.mojang.blaze3d.platform.Window;
import io.github.pouffy.tcompat.compat.estrogen.EstrogenHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    protected abstract String createTitle();

    @Shadow public abstract Window getWindow();

    @Inject(method = "updateTitle()V", at = @At("HEAD"), cancellable = true)
    private void updateTitle(final CallbackInfo info) {
        if(EstrogenHandler.TextRendererFeatures.getUwufy()) {
            info.cancel();
            this.getWindow().setTitle(EstrogenHandler.UwUfy.uwufy(this.createTitle()));
        }
    }
}
