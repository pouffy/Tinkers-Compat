package io.github.pouffy.tcompat.compat.species;

import com.ninni.species.registry.SpeciesSoundEvents;
import com.ninni.species.server.item.WickedSwapperItem;
import io.github.pouffy.tcompat.TCompat;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import javax.annotation.Nullable;

public class SwappingModifier extends Modifier implements ProjectileHitModifierHook, MeleeHitModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT, ModifierHooks.MELEE_HIT);
    }

    @Override
    public Component getDisplayName(int level) {
        return ModifierLevelDisplay.NO_LEVELS.nameForLevel(this, level);
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target, boolean notBlocked) {
        if (notBlocked && target != null) {
            swap(target, projectile);
            return false;
        }
        return false;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getProjectile() != null) {
            swap(context.getLivingTarget(), context.getProjectile());
        }
    }

    private void swap(LivingEntity target, Projectile projectile) {
        var owner = projectile.getOwner();
        if (!projectile.level().isClientSide && !target.isPassenger() && owner != null && !owner.isPassenger()) {
            var level = owner.level();
            double targetX = target.getX();
            double targetY = target.getY();
            double targetZ = target.getZ();
            target.teleportTo(owner.getX(), owner.getY(), owner.getZ());
            owner.teleportTo(targetX, targetY, targetZ);
            SoundEvent teleport = BuiltInRegistries.SOUND_EVENT.get(TCompat.getResource("species:item.wicked_swapper.teleport"));
            if (teleport != null) {
                level.playSound(null, owner.getX(), owner.getY(), owner.getZ(), teleport, projectile.getSoundSource(), 1.0F, 1.0F);
                level.playSound(null, targetX, targetY, targetZ, teleport, projectile.getSoundSource(), 1.0F, 1.0F);
            }

            for(int i = 0; i < 5; ++i) {
                level.addParticle(ParticleTypes.PORTAL, owner.getX(), owner.getY() + (double)1.0F, owner.getZ(), (double)0.5F - level.random.nextDouble(), (double)0.5F - level.random.nextDouble(), (double)0.5F - level.random.nextDouble());
                level.addParticle(ParticleTypes.EXPLOSION, owner.getX(), owner.getY() + (double)1.0F, owner.getZ(), (double)0.5F - level.random.nextDouble(), (double)0.5F - level.random.nextDouble(), (double)0.5F - level.random.nextDouble());
                level.addParticle(ParticleTypes.PORTAL, targetX, targetY + (double)1.0F, targetZ, (double)0.5F - level.random.nextDouble(), (double)0.5F - level.random.nextDouble(), (double)0.5F - level.random.nextDouble());
                level.addParticle(ParticleTypes.EXPLOSION, targetX, targetY + (double)1.0F, targetZ, (double)0.5F - level.random.nextDouble(), (double)0.5F - level.random.nextDouble(), (double)0.5F - level.random.nextDouble());
            }
        }
    }
}
