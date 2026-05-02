package io.github.pouffy.tcompat.mixin.aether;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.common.module.AetherForgedModifierHook;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.aetherteam.aether.event.hooks.AbilityHooks.ToolHooks.debuffTools;

@Mixin(AbilityHooks.ToolHooks.class)
public class AbilityHooksMixin {

    @Inject(method = "reduceToolEffectiveness", at = @At("HEAD"), remap = false, cancellable = true)
    private static void reduceToolEffectiveness(Player player, BlockState state, ItemStack stack, float speed, CallbackInfoReturnable<Float> cir) {
        if (debuffTools) {
            if (stack.getItem() instanceof ModifiableItem) {
                if ((state.getBlock().getDescriptionId().startsWith("block.aether.") || state.is(AetherTags.Blocks.TREATED_AS_AETHER_BLOCK)) && !state.is(AetherTags.Blocks.TREATED_AS_VANILLA_BLOCK)) {
                    CompatHelper.asTool(stack, (tool) -> {
                        List<ModifierEntry> validList = new ArrayList<>();
                        for (ModifierEntry entry : tool.getModifierList()) {
                            if (entry.getModifier().getHooks().hasHook(GlobalInit.AETHER_FORGED)) {
                                validList.add(entry);
                            }
                        }
                        List<ModifierEntry> list = validList.stream().sorted(Comparator.comparingInt(entry -> -entry.getModifier().getPriority())).toList();
                        boolean shouldWork = !list.isEmpty() && list.stream().findFirst().map(entry -> entry.getHook(GlobalInit.AETHER_FORGED).canUse(tool, entry)).orElse(false);
                        if (!shouldWork)
                            cir.setReturnValue(1.0f);
                        else cir.setReturnValue(speed);
                    });
                }
            }
        }
    }
}
