package io.github.pouffy.tcompat.compat.cataclysm.modifier;

import io.github.pouffy.tcompat.common.capability.cooldown.ModifierCooldowns;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.module.AbstractTeamUpModifier;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.function.Predicate;

public class TidalModifier extends AbstractTeamUpModifier implements GeneralInteractionModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.GENERAL_INTERACT);
    }

    @Override
    public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifierEntry, Player player, InteractionHand interactionHand, InteractionSource source) {
        if (!isValid(tool)) return InteractionResult.PASS;
        if (!tool.isBroken() && source == InteractionSource.RIGHT_CLICK && player.isCrouching()) {
            if (ModifierCooldowns.isOnCooldown(modifierEntry.getId(), player)) {
                CompatHelper.sendCooldownMessage(player, modifierEntry);
                return InteractionResult.PASS;
            }
            GeneralInteractionModifierHook.startUsing(tool, modifierEntry.getId(), player, interactionHand);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onStoppedUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        int i = this.getUseDuration(tool, modifier) - timeLeft;
        float f = getPowerForTime(i);
        if (!tool.isBroken() && entity.isCrouching() && !ModifierCooldowns.isOnCooldown(modifier.getId(), entity)) {
            if (!((double)f < (double)0.5F) && !entity.level().isClientSide) {
                ModifierCooldowns.addCooldown(modifier.getId(), 150, entity);
                CataclysmHandler.tidalWave(entity.level(), entity);
            }
        }
    }

    public static float getPowerForTime(int time) {
        float f = (float)time / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 72000;
    }

    @Override
    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return UseAnim.SPEAR;
    }

    @Override
    protected Predicate<IMaterial> getRequiredMaterials() {
        var tagSource = MaterialRegistry.getTagSource().valuesInTag(TCTags.Materials.LACRIMA_COMPANION);
        return (material) -> tagSource != null && tagSource.contains(material);
    }
}
