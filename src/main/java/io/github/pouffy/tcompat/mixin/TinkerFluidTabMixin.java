package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.compat.ad_astra.AdAstraInit;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.fluids.TinkerFluids;

import java.util.Map;
import java.util.function.BiConsumer;

@Mixin(TinkerFluids.class)
public class TinkerFluidTabMixin {

    private static Map<String, BiConsumer<CreativeModeTab.ItemDisplayParameters, CreativeModeTab.Output>> compatFluids = Map.of(
            "aether", AetherInit::addTabItems,
            "aether_redux", AetherReduxInit::addTabItems,
            "deep_aether", DeepAetherInit::addTabItems,
            "aether_treasure_reforging", AetherTRInit::addTabItems,
            "ad_astra", AdAstraInit::addTabItems
    );

    @Inject(method = "addTabItems(Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters;Lnet/minecraft/world/item/CreativeModeTab$Output;)V", at = @At("TAIL"), remap = false)
    private static void addExtraTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output, CallbackInfo ci) {
        compatFluids.forEach((mod, consumer) -> {
            if (CompatHelper.isLoaded(mod))
                consumer.accept(itemDisplayParameters, output);
        });
    }
}
