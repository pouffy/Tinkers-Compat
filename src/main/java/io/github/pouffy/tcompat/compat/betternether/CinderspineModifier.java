package io.github.pouffy.tcompat.compat.betternether;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.json.LevelingValue;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.special.sling.SlingLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class CinderspineModifier extends Modifier implements ProjectileLaunchModifierHook, SlingLaunchModifierHook, ToolStatsModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_LAUNCH, ModifierHooks.SLING_LAUNCH, ModifierHooks.TOOL_STATS);
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        if (ToolStats.VELOCITY.supports(context.getItem())) {
            StatBoostModule.StatOperation.ADD.apply(builder, ToolStats.VELOCITY, LevelingValue.eachLevel(0.1f).compute(modifier.getEffectiveLevel()));
        }
        if (ToolStats.PROJECTILE_DAMAGE.supports(context.getItem()) && context.hasTag(TinkerTags.Items.THROWN_AMMO)) {
            StatBoostModule.StatOperation.ADD.apply(builder, ToolStats.PROJECTILE_DAMAGE, LevelingValue.eachLevel(0.5f).compute(modifier.getEffectiveLevel()));
        }
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        shooter.setSecondsOnFire((int) (1 * modifier.getEffectiveLevel()));
    }

    @Override
    public void afterSlingLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity holder, LivingEntity target, ModifierEntry slingSource, float force, float multiplier, Vec3 angle) {
        holder.setSecondsOnFire((int) (1 * modifier.getEffectiveLevel()));
    }
}
