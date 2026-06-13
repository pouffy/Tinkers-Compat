package io.github.pouffy.tcompat.compat.aether.modifier.combat.melee;

import io.github.pouffy.tcompat.compat.aether.AetherHandler;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.BlockInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class AscensionModifier extends NoLevelsModifier implements BlockInteractionModifierHook, MeleeHitModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BLOCK_INTERACT, ModifierHooks.MELEE_HIT);
    }

    @Override
    public InteractionResult beforeBlockUse(IToolStackView tool, ModifierEntry modifier, UseOnContext context, InteractionSource source) {
        return AetherHandler.floatBlock(context) ? InteractionResult.sidedSuccess(context.getLevel().isClientSide()) : InteractionResult.PASS;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (tool.hasTag(TinkerTags.Items.MELEE_WEAPON)) {
            AetherHandler.launchEntity(context.getLivingTarget(), context.getAttacker());
        }
    }
}
