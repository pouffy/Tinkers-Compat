package io.github.pouffy.tcompat.compat.malum.modifier.ranged;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.client.compat.MalumClientHandler;
import io.github.pouffy.tcompat.common.capability.projectile.ability.ProjectileAbilityHooks;
import io.github.pouffy.tcompat.common.modifier.base.MalumStaffModifier;
import io.github.pouffy.tcompat.common.modifier.module.OptionalAttributeModule;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.malum.MalumHandler;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.awt.*;

public class ErosionModifier extends MalumStaffModifier {
    public static final Color MALIGNANT_PURPLE = new Color(68, 11, 61);
    public static final Color MALIGNANT_BLACK = new Color(12, 4, 11);

    public ErosionModifier() {
        super(10);
    }

    @Override
    public void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(OptionalAttributeModule.builder(TCompat.getResource("lodestone:magic_damage"), AttributeModifier.Operation.ADDITION).slots(EquipmentSlot.MAINHAND).flat(5));
    }

    @Override
    public void spawnChargeParticles(Level level, LivingEntity entity, Vec3 pos, IToolStackView tool, float chargePercentage) {
        RandomSource random = level.random;
        MalumClientHandler.erosionParticles(entity, random, pos, chargePercentage);
    }

    @Override
    public int getCooldownDuration(Level level, LivingEntity entity) {
        return 80;
    }

    @Override
    public int getProjectileCount(Level level, LivingEntity entity, float chargePercentage) {
        return chargePercentage == 1.0F ? 2 : 0;
    }

    @Override
    public void fireProjectile(LivingEntity entity, IToolStackView tool, Level level, InteractionHand hand, float chargePercentage, int count) {
        int spawnDelay = count * 5;
        float pitchOffset = (float)count * 1.5F;
        float velocity = 4.0F;
        double magicDamage = ObjectRetriever.getAttribute("lodestone:magic_damage").map(attr -> entity.getAttributes().getValue(attr) - 2.0F).orElse(0.0D);
        Vec3 pos = this.getProjectileSpawnPos(entity, hand, 0.5F, 0.5F);
        for(int i = 0; i < 4; ++i) {
            var erosion = MalumHandler.createErosion(entity, pos, pitchOffset, spawnDelay, velocity, (float) magicDamage);
            if (erosion != null) {
                if (i > 1) {
                    erosion.setSilent(true);
                }
                ProjectileAbilityHooks.addModifiersToProjectile(tool, entity, erosion, (count + i) < ((getProjectileCount(level, entity, chargePercentage) + 4) / 2));
                level.addFreshEntity(erosion);
            }
        }
    }
}
