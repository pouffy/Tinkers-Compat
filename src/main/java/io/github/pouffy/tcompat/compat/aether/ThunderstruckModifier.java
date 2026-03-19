package io.github.pouffy.tcompat.compat.aether;

import io.github.pouffy.tcompat.common.capability.compatible.Compatibility;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Optional;

public class ThunderstruckModifier extends NoLevelsModifier implements MeleeHitModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();
        Entity target = context.getTarget();
        if (!(attacker instanceof Player) || context.isFullyCharged()) {
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(attacker.level());
            if (lightningBolt != null) {
                Compatibility.get(lightningBolt).ifPresent(compatibility -> compatibility.setLightningOwner(attacker));
                lightningBolt.setPos(target.getX(), target.getY(), target.getZ());
                attacker.level().addFreshEntity(lightningBolt);
            }
        }
    }

    //Copy of Aether hook to work with our compatibility data
    public static boolean lightningTracking(Entity entity, LightningBolt lightning) {
        if (entity instanceof LivingEntity livingEntity) {
            Optional<Compatibility> compatibilityOptional = Compatibility.get(lightning).resolve();
            if (compatibilityOptional.isPresent()) {
                Compatibility lightningTracker = compatibilityOptional.get();
                if (lightningTracker.getLightningOwner() != null) {
                    return livingEntity == lightningTracker.getLightningOwner() || livingEntity == lightningTracker.getLightningOwner().getVehicle() || lightningTracker.getLightningOwner().getPassengers().contains(livingEntity);
                }
            }
        }
        return false;
    }
}
