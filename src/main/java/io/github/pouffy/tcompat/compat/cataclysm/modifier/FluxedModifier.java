package io.github.pouffy.tcompat.compat.cataclysm.modifier;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.capability.cooldown.ModifierCooldowns;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.module.AbstractTeamUpModifier;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.capability.PersistentDataCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import java.util.function.Predicate;

public class FluxedModifier extends AbstractTeamUpModifier implements ProjectileLaunchModifierHook, GeneralInteractionModifierHook {
    private final ResourceLocation timeLeftKey = TCompat.getResource("time_left");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_LAUNCH, ModifierHooks.GENERAL_INTERACT);
    }

    @Override
    public void onStoppedUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        if(!isValid(tool)) return;
        tool.getPersistentData().putInt(timeLeftKey, timeLeft);
    }

    @Override
    protected Predicate<IMaterial> getRequiredMaterials() {
        var tagSource = MaterialRegistry.getTagSource().valuesInTag(TCTags.Materials.WITHERITE_COMPANION);
        return (material) -> tagSource != null && tagSource.contains(material);
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistantData, boolean primary) {
        if (!CompatHelper.isLoaded("cataclysm")) return;
        Level level = entity.level();
        if (ModifierCooldowns.isOnCooldown(modifier.getId(), entity)) {
            CompatHelper.sendCooldownMessage(entity, modifier);
        }
        if (isValid(tool) && !ModifierCooldowns.isOnCooldown(modifier.getId(), entity) && primary && arrow != null) {
            int i = 72000 - tool.getPersistentData().getInt(timeLeftKey);
            float f = getPowerForTime(i);
            if (arrow.isCritArrow()) {
                if (!level.isClientSide) {
                    if (entity.isShiftKeyDown()) {
                        var howitzer = CataclysmHandler.createHowitzer(entity, f);
                        if (howitzer != null) {
                            TCompat.LOGGER.info("Creating Howitzer");
                            appendModifiers(tool, entity, howitzer);
                            level.addFreshEntity(howitzer);
                            arrow.discard();
                            ModifierCooldowns.addCooldown(modifier.getId(), 100, entity);
                        } else {
                            TCompat.LOGGER.info("Issue creating Howitzer");
                        }
                    } else {
                        float d7 = entity.getYRot();
                        float d = entity.getXRot();
                        float d1 = -Mth.sin(d7 * ((float)Math.PI / 180F)) * Mth.cos(d * ((float)Math.PI / 180F));
                        float d2 = -Mth.sin(d * ((float)Math.PI / 180F));
                        float d3 = Mth.cos(d7 * ((float)Math.PI / 180F)) * Mth.cos(d * ((float)Math.PI / 180F));
                        double theta = (double)d7 * (Math.PI / 180D);
                        ++theta;
                        double vecX = Math.cos(theta);
                        double vecZ = Math.sin(theta);
                        double x = entity.getX() + vecX;
                        double Z = entity.getZ() + vecZ;
                        Vec3 vec3 = new Vec3(d1, d2, d3);
                        float yRot = (float)(Mth.atan2(vec3.z, vec3.x) * (180D / Math.PI)) + 90.0F;
                        float xRot = (float)(-(Mth.atan2(vec3.y, Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)) * (180D / Math.PI)));
                        var witherMissile = CataclysmHandler.createWitherMissile(entity, vec3, x, Z, yRot, xRot);
                        if (witherMissile != null) {
                            TCompat.LOGGER.info("Creating Wither Missile");
                            appendModifiers(tool, entity, witherMissile);
                            level.addFreshEntity(witherMissile);
                            arrow.discard();
                            ModifierCooldowns.addCooldown(modifier.getId(), 40, entity);
                        } else {
                            TCompat.LOGGER.info("Issue creating Wither Missile");
                        }
                    }
                }
            }
        }
    }

    private void appendModifiers(IToolStackView tool, LivingEntity shooter, Projectile projectile) {
        ModifierNBT modifiers = tool.getModifiers();
        EntityModifierCapability.getCapability(projectile).addModifiers(modifiers);
        ModDataNBT arrowData = PersistentDataCapability.getOrWarn(projectile);
        for(ModifierEntry entry : modifiers.getModifiers()) {
            entry.getHook(ModifierHooks.PROJECTILE_LAUNCH).onProjectileLaunch(tool, entry, shooter, ItemStack.EMPTY, projectile, null, arrowData, false);
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
    public InteractionResult onToolUse(IToolStackView iToolStackView, ModifierEntry modifierEntry, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        return InteractionResult.PASS;
    }
}
