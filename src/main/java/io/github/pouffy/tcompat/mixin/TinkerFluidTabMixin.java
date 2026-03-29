package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.compat.ad_astra.AdAstraInit;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRInit;
import io.github.pouffy.tcompat.compat.betterend.BetterendInit;
import io.github.pouffy.tcompat.compat.betternether.BetternetherInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.fluids.TinkerFluids;

import java.util.Map;
import java.util.function.BiConsumer;

@Mixin(TinkerFluids.class)
public class TinkerFluidTabMixin {

    @Unique
    private static final Map<String, BiConsumer<CreativeModeTab.ItemDisplayParameters, CreativeModeTab.Output>> tcompat$fluids = Map.of(
            "aether", AetherInit::addTabItems,
            "aether_redux", AetherReduxInit::addTabItems,
            "deep_aether", DeepAetherInit::addTabItems,
            "aether_treasure_reforging", AetherTRInit::addTabItems,
            "ad_astra", AdAstraInit::addTabItems,
            "betterend", BetterendInit::addTabItems,
            "betternether", BetternetherInit::addTabItems,
            "iceandfire", IFInit::addTabItems
    );

    @Inject(method = "addTabItems(Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters;Lnet/minecraft/world/item/CreativeModeTab$Output;)V", at = @At("TAIL"), remap = false)
    private static void addExtraTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output, CallbackInfo ci) {
        tcompat$fluids.forEach((mod, consumer) -> {
            if (CompatHelper.isLoaded(mod))
                consumer.accept(itemDisplayParameters, output);
        });

    }
}
