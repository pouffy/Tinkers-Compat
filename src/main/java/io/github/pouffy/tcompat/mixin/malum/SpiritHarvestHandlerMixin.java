package io.github.pouffy.tcompat.mixin.malum;

import com.sammy.malum.core.handlers.SpiritHarvestHandler;
import com.sammy.malum.registry.common.AttributeRegistry;
import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@Mixin(SpiritHarvestHandler.class)
public class SpiritHarvestHandlerMixin {

    @Inject(method = "pickupSpirit", at = @At("HEAD"), remap = false)
    private static void pickupSpirit(LivingEntity collector, ItemStack stack, CallbackInfo ci) {
        if (collector instanceof Player player) {
            AttributeInstance instance = player.getAttribute(AttributeRegistry.ARCANE_RESONANCE.get());
            var toolStacks = CompatHelper.getModifiableStacks(collector).stream().map(ToolStack::from).filter(toolStack -> !toolStack.getModifiers().getModifiers().stream().filter(entry -> entry.getHook(GlobalInit.COLLECT_SPIRIT).canTarget(toolStack, entry, collector, instance != null ? instance.getValue() : 0.0F)).toList().isEmpty()).toList();
            TCompat.LOGGER.info("Narrowed down to {} valid tool stacks", toolStacks.size());
            for (ToolStack toolStack : toolStacks) {
                for (ModifierEntry entry : toolStack.getModifiers()) {
                    entry.getHook(GlobalInit.COLLECT_SPIRIT).pickupSpirit(toolStack, entry, collector, instance != null ? instance.getValue() : 0.0F, 1.0F / toolStacks.size());
                }
            }
        }
    }
}
