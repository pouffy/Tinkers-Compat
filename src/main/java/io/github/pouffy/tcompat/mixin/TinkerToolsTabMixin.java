package io.github.pouffy.tcompat.mixin;

import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.Map;
import java.util.function.BiConsumer;

@Mixin(TinkerTools.class)
public class TinkerToolsTabMixin {

    @Unique
    private static final Map<String, BiConsumer<CreativeModeTab.ItemDisplayParameters, CreativeModeTab.Output>> tcompat$tools = Map.of(

    );

    @Inject(method = "addTabItems(Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters;Lnet/minecraft/world/item/CreativeModeTab$Output;)V", at = @At("TAIL"), remap = false)
    private static void addExtraTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output, CallbackInfo ci) {
        tcompat$tools.forEach((mod, consumer) -> {
            if (CompatHelper.isLoaded(mod))
                consumer.accept(itemDisplayParameters, output);
        });
        GlobalInit.addToolTabItems(itemDisplayParameters, output);
    }
}
