package io.github.pouffy.tcompat.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Shadow
    @Final
    private ServerStatsCounter stats;

    @Inject(method = "awardStat(Lnet/minecraft/stats/Stat;I)V", at = @At("RETURN"))
    private void awardStat(Stat<?> stat, int amount, CallbackInfo ci) {
        this.stats.sendStats((ServerPlayer) (Object) this);
    }

    @Inject(method = "resetStat(Lnet/minecraft/stats/Stat;)V", at = @At("RETURN"))
    private void resetStat(Stat<?> stat, CallbackInfo ci) {
        this.stats.sendStats((ServerPlayer) (Object) this);
    }
}
