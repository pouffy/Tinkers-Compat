package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.shared.TinkerCommons;

import java.util.Map;
import java.util.function.BiConsumer;

@Mixin(TinkerCommons.class)
public class TinkerCommonTabMixin {

    @Unique
    private static final Map<String, BiConsumer<CreativeModeTab.ItemDisplayParameters, CreativeModeTab.Output>> tcompat$items = Map.of(
            "aether", AetherInit::addCommonTabItems,
            "deep_aether", DeepAetherInit::addCommonTabItems,
            "iceandfire", IFInit::addCommonTabItems
    );

    @Inject(method = "addTabItems(Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters;Lnet/minecraft/world/item/CreativeModeTab$Output;)V", at = @At("TAIL"), remap = false)
    private static void addExtraTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output, CallbackInfo ci) {
        tcompat$items.forEach((mod, consumer) -> {
            if (CompatHelper.isLoaded(mod))
                consumer.accept(itemDisplayParameters, output);
        });
    }
}
