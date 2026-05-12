package io.github.pouffy.tcompat.compat.cataclysm.modifier;

import io.github.pouffy.tcompat.TCompat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.capability.PersistentDataCapability;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;
import java.util.Optional;

public class PhantasmicModifier extends NoLevelsModifier implements ProjectileLaunchModifierHook, GeneralInteractionModifierHook {
    private final ResourceLocation timeLeftKey = TCompat.getResource("time_left");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_LAUNCH, ModifierHooks.GENERAL_INTERACT);
    }

    @Override
    public void onStoppedUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        tool.getPersistentData().putInt(timeLeftKey, timeLeft);
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        if(!primary) return;
        Entity pointedEntity = this.getPlayerLookTarget(shooter.level(), shooter);
        int arrowcount = 3;
        float offsetangle = 12.0F;
        int chargeTime = 72000 - tool.getPersistentData().getInt(timeLeftKey);
        if (arrow != null) {
            AbstractArrow abstractarrow = arrow;
            for(int j = 0; j < arrowcount; ++j) {
                float charge = GeneralInteractionModifierHook.getToolCharge(tool, (float)chargeTime);
                float velocity = ConditionalStatModifierHook.getModifiedStat(tool, shooter, ToolStats.VELOCITY);
                float power = charge * velocity;
                float baseDamageAddition = (float) (2.0F + (double) tool.getStats().get(ToolStats.PROJECTILE_DAMAGE));
                targeted: {
                    if (pointedEntity instanceof LivingEntity target) {
                        if (!target.isAlliedTo(shooter)) {
                            AbstractArrow homingArrow = CataclysmHandler.createPhantomArrow(shooter.level(), shooter, target);
                            if (homingArrow != null) {
                                homingArrow.setBaseDamage((float) arrow.getBaseDamage() * power);
                                if (baseDamageAddition > 0) {
                                    homingArrow.setBaseDamage(homingArrow.getBaseDamage() + (double)baseDamageAddition * 0.35 + (double)0.5F);
                                }
                                abstractarrow = homingArrow;
                                break targeted;
                            }
                        }
                    }

                    AbstractArrow homingArrow = CataclysmHandler.createPhantomArrow(shooter.level(), shooter, null);
                    if (homingArrow != null) {
                        homingArrow.setBaseDamage((float) arrow.getBaseDamage() * power);
                        if (baseDamageAddition > 0) {
                            homingArrow.setBaseDamage(homingArrow.getBaseDamage() + (double)baseDamageAddition * 0.35 + (double)0.5F);
                        }

                        abstractarrow = homingArrow;
                    }
                }
                ModifierNBT modifiers = tool.getModifiers();
                EntityModifierCapability.getCapability(abstractarrow).addModifiers(modifiers);
                ModDataNBT arrowData = PersistentDataCapability.getOrWarn(abstractarrow);
                for(ModifierEntry entry : modifiers.getModifiers()) {
                    entry.getHook(ModifierHooks.PROJECTILE_LAUNCH).onProjectileLaunch(tool, entry, shooter, ItemStack.EMPTY, abstractarrow, abstractarrow, arrowData, false);
                }
                float inaccuracy = ModifierUtil.getInaccuracy(tool, shooter);
                abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                abstractarrow.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + ((float)j - (float)(arrowcount - 1) / 2.0F) * offsetangle, 0.0F, power * 3.0F, inaccuracy);
                if (power == 1.0F) {
                    abstractarrow.setCritArrow(true);
                }

                shooter.level().addFreshEntity(abstractarrow);
            }
        }
    }

    private Entity getPlayerLookTarget(Level level, LivingEntity living) {
        Entity pointedEntity = null;
        double range = 40.0F;
        Vec3 srcVec = living.getEyePosition();
        Vec3 lookVec = living.getViewVector(1.0F);
        Vec3 destVec = srcVec.add(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range);
        float var9 = 2.0F;
        List<Entity> possibleList = level.getEntities(living, living.getBoundingBox().expandTowards(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range).inflate((double)var9, (double)var9, (double)var9));
        double hitDist = 0.0F;

        for(Entity possibleEntity : possibleList) {
            AABB collisionBB = possibleEntity.getBoundingBox().inflate(1.0F, 1.0F, 1.0F);
            Optional<Vec3> interceptPos = collisionBB.clip(srcVec, destVec);
            if (collisionBB.contains(srcVec)) {
                if ((double)0.0F < hitDist || hitDist == (double)0.0F) {
                    pointedEntity = possibleEntity;
                    hitDist = 0.0F;
                }
            } else if (interceptPos.isPresent()) {
                double possibleDist = srcVec.distanceTo(interceptPos.get());
                if (possibleDist < hitDist || hitDist == (double)0.0F) {
                    pointedEntity = possibleEntity;
                    hitDist = possibleDist;
                }
            }
        }

        return pointedEntity;
    }

    @Override
    public InteractionResult onToolUse(IToolStackView iToolStackView, ModifierEntry modifierEntry, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        return InteractionResult.CONSUME;
    }
}
