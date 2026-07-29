package io.github.pouffy.tcompat.compat.create.modifier.harvest;

import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerEffect;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MonsterMeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedContext;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.special.sling.SlingLaunchModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.stats.ToolType;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ClockworkModifier extends Modifier implements ProjectileLaunchModifierHook, ConditionalStatModifierHook, BlockBreakModifierHook, BreakSpeedModifierHook, SlingLaunchModifierHook, TooltipModifierHook, MeleeHitModifierHook, MonsterMeleeHitModifierHook.RedirectAfter {
    private static final Component SPEED = TCompat.makeTranslation("modifier", "clockwork.speed");

    public ClockworkModifier() {
    }

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.CONDITIONAL_STAT, ModifierHooks.PROJECTILE_LAUNCH, ModifierHooks.BLOCK_BREAK, ModifierHooks.BREAK_SPEED, ModifierHooks.SLING_LAUNCH, ModifierHooks.TOOLTIP, ModifierHooks.MELEE_HIT, ModifierHooks.MONSTER_MELEE_HIT);
    }

    public int getPriority() {
        return 75;
    }

    private static float getBonus(LivingEntity living, ToolType type, ModifierEntry modifier) {
        return modifier.getEffectiveLevel() * (float) TinkerEffect.getLevel(living, GlobalInit.clockworkEffect.get(type));
    }

    private static void applyEffect(LivingEntity living, ToolType type, int duration, int maxLevel) {
        TinkerEffect effect = GlobalInit.clockworkEffect.get(type);
        effect.apply(living, duration, Math.min(maxLevel, TinkerEffect.getAmplifier(living, effect) + 1), true);
    }

    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        if (isEffective) {
            event.setNewSpeed(event.getNewSpeed() * (1.0F + getBonus(event.getEntity(), ToolType.HARVEST, modifier) / 40.0F));
        }
    }

    public float modifyBreakSpeed(IToolStackView tool, ModifierEntry modifier, BreakSpeedContext context, float speed) {
        if (context.isEffective()) {
            speed *= 1.0F + getBonus(context.player(), ToolType.HARVEST, modifier) / 40.0F;
        }
        return speed;
    }

    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        if (context.canHarvest() && context.isEffective() && !context.isAOE()) {
            int duration = Math.max(100, (int)(50.0F * context.getState().getDestroySpeed(context.getWorld(), context.getPos()) / tool.getStats().get(ToolStats.MINING_SPEED)));
            applyEffect(context.getLiving(), ToolType.HARVEST, duration, 9);
        }

    }

    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        if (primary && (arrow == null || arrow.isCritArrow())) {
            applyEffect(shooter, ToolType.RANGED, 200, 9);
        }

    }

    public void afterSlingLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity holder, LivingEntity target, ModifierEntry slingSource, float force, float multiplier, Vec3 angle) {
        applyEffect(holder, ToolType.RANGED, 200, 9);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (!context.isExtraAttack() && context.isFullyCharged()) {
            applyEffect(context.getAttacker(), ToolType.MELEE, 200, 9);
        }
    }

    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        if (stat == ToolStats.DRAW_SPEED) {
            return baseValue * (1.0F + getBonus(living, ToolType.RANGED, modifier) / 40.0F);
        }
        if (stat == ToolStats.ATTACK_SPEED) {
            return baseValue * (1.0F + getBonus(living, ToolType.MELEE, modifier) / 40.0F);
        }
        return baseValue;
    }

    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey key, TooltipFlag tooltipFlag) {
        ToolType type = ToolType.from(tool.getItem(), ToolType.HARVEST, ToolType.RANGED, ToolType.MELEE);
        if (type != null) {
            float bonus;
            if (player != null && key == TooltipKey.SHIFT) {
                bonus = getBonus(player, type, modifier) / 40.0F;
            } else {
                bonus = modifier.getEffectiveLevel() * 0.25F;
            }

            if (bonus > 0.0F) {
                TooltipModifierHook.addPercentBoost(this, SPEED, bonus, tooltip);
            }
        }

    }
}
