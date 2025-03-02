package com.pouffydev.tcompat.mixin.aether;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.modifier.TComModifierIds;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import static com.aetherteam.aether.event.hooks.AbilityHooks.ToolHooks.debuffTools;

@Mixin(AbilityHooks.ToolHooks.class)
public class AbilityHooksMixin {

    @Inject(method = "reduceToolEffectiveness", at = @At("HEAD"), remap = false, cancellable = true)
    private static void reduceToolEffectiveness(Player player, BlockState state, ItemStack stack, float speed, CallbackInfoReturnable<Float> cir) {
        if (debuffTools) {
            if (stack.getItem() instanceof ModifiableItem) {
                if ((state.getBlock().getDescriptionId().startsWith("block.aether.") || state.is(AetherTags.Blocks.TREATED_AS_AETHER_BLOCK)) && !state.is(AetherTags.Blocks.TREATED_AS_VANILLA_BLOCK)) {
                    ToolStack toolStack = ToolStack.from(stack);
                    boolean isAetherForged = toolStack.getModifierLevel(TComModifierIds.aetherForged) > 0;
                    if (!isAetherForged)
                        cir.setReturnValue(1.0f);
                    else cir.setReturnValue(speed);
                }
            }
        }
        //cir.setReturnValue(speed);
    }
}
