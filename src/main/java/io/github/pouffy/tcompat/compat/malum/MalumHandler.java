package io.github.pouffy.tcompat.compat.malum;

import com.sammy.malum.core.helpers.ParticleHelper;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.SoundHelper;

public class MalumHandler {

    public static float weightOfWorlds(float damage, LivingEntity attacker, Entity target) {
        if (!CompatHelper.isLoaded("malum")) return damage;
        float returning = damage;
        Level level = attacker.level();
        var optionalEffect = ObjectRetriever.getEffect("malum:grim_certainty");
        if (optionalEffect.isPresent() && !level.isClientSide()) {
            ParticleEffectType particleEffectType = ParticleEffectTypeRegistry.SCYTHE_SLASH;
            var effect = optionalEffect.get();
            if (attacker.hasEffect(effect) || level.random.nextFloat() < 0.25F) {
                returning *= 2.0F;
                SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 2.0F, 0.75F);
                SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 3.0F, 1.25F);
                SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 3.0F, 1.75F);
                particleEffectType = ParticleEffectTypeRegistry.WEIGHT_OF_WORLDS_CRIT;
                attacker.removeEffect(effect);
            }
            ParticleHelper.createSlashingEffect(particleEffectType).setVertical().spawnForwardSlashingParticle(attacker);
            SoundHelper.playSound(target, SoundRegistry.WEIGHT_OF_WORLDS_CUT.get(), 2.0F, 0.75F);
        }
        return returning;
    }
}
