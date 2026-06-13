package io.github.pouffy.tcompat.client.compat;

import com.sammy.malum.common.entity.nitrate.EthericNitrateEntity;
import com.sammy.malum.registry.client.ParticleRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.DirectionalBehaviorComponent;

import static io.github.pouffy.tcompat.compat.malum.modifier.ranged.ErosionModifier.MALIGNANT_BLACK;

@OnlyIn(Dist.CLIENT)
public class MalumClientHandler {

    public static void mnemonicParticles(LivingEntity shooter, RandomSource random, Vec3 pos, float chargePercentage) {
        if (!CompatHelper.isLoaded("malum")) return;
        LoadedOnly.mnemonicParticles(shooter, random, pos, chargePercentage);
    }

    public static void erosionParticles(LivingEntity shooter, RandomSource random, Vec3 pos, float chargePercentage) {
        if (!CompatHelper.isLoaded("malum")) return;
        LoadedOnly.erosionParticles(shooter, random, pos, chargePercentage);
    }

    public static void auricFlameParticles(LivingEntity shooter, RandomSource random, Vec3 pos, float chargePercentage) {
        if (!CompatHelper.isLoaded("malum")) return;
        LoadedOnly.auricFlameParticles(shooter, random, pos, chargePercentage);
    }

    @OnlyIn(Dist.CLIENT)
    public static class LoadedOnly {
        public static void mnemonicParticles(LivingEntity shooter, RandomSource random, Vec3 pos, float chargePercentage) {
            SpinParticleData spinData = SpinParticleData.createRandomDirection(random, 0.25F, 0.5F).setSpinOffset(RandomHelper.randomBetween(random, 0.0F, 6.28F)).build();
            WorldParticleBuilder.create(ParticleRegistry.HEXAGON, new DirectionalBehaviorComponent(shooter.getLookAngle().normalize())).setRenderTarget(RenderHandler.LATE_DELAYED_RENDER).setTransparencyData(GenericParticleData.create(0.6F * chargePercentage, 0.0F).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build()).setSpinData(spinData).setScaleData(GenericParticleData.create(0.3F * chargePercentage, 0.0F).setEasing(Easing.SINE_IN_OUT).build()).setColorData(SpiritTypeRegistry.WICKED_SPIRIT.createColorData().build()).setLifetime(5).setLifeDelay(2).setMotion(shooter.getLookAngle().normalize().scale(0.05F)).enableNoClip().enableForcedSpawn().spawn(shooter.level(), pos.x, pos.y, pos.z).setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT).spawn(shooter.level(), pos.x, pos.y, pos.z);
        }

        public static void erosionParticles(LivingEntity shooter, RandomSource random, Vec3 pos, float chargePercentage) {
            SpinParticleData spinData = SpinParticleData.createRandomDirection(random, 0.25F, 0.5F).setSpinOffset(RandomHelper.randomBetween(random, 0.0F, 6.28F)).build();
            WorldParticleBuilder.create(ParticleRegistry.CIRCLE, new DirectionalBehaviorComponent(shooter.getLookAngle().normalize())).setRenderTarget(RenderHandler.LATE_DELAYED_RENDER).setTransparencyData(GenericParticleData.create(0.8F * chargePercentage, 0.0F).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build()).setSpinData(spinData).setScaleData(GenericParticleData.create(0.3F * chargePercentage, 0.0F).setEasing(Easing.SINE_IN_OUT).build()).setColorData(ColorParticleData.create(MALIGNANT_BLACK, MALIGNANT_BLACK).build()).setLifetime(5).setLifeDelay(2).setMotion(shooter.getLookAngle().normalize().scale(0.05F)).enableNoClip().enableForcedSpawn().setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT).spawn(shooter.level(), pos.x, pos.y, pos.z);
        }

        public static void auricFlameParticles(LivingEntity shooter, RandomSource random, Vec3 pos, float chargePercentage) {
            SpinParticleData spinData = SpinParticleData.createRandomDirection(random, 0.25F, 0.5F).setSpinOffset(RandomHelper.randomBetween(random, 0.0F, 6.28F)).build();
            WorldParticleBuilder.create(ParticleRegistry.HEXAGON, new DirectionalBehaviorComponent(shooter.getLookAngle().normalize())).setRenderTarget(RenderHandler.LATE_DELAYED_RENDER).setTransparencyData(GenericParticleData.create(0.5F * chargePercentage, 0.0F).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build()).setScaleData(GenericParticleData.create(0.35F * chargePercentage, 0.0F).setEasing(Easing.SINE_IN_OUT).build()).setSpinData(spinData).setColorData(EthericNitrateEntity.AURIC_COLOR_DATA).setLifetime(5).setMotion(shooter.getLookAngle().normalize().scale((double)0.05F)).enableNoClip().enableForcedSpawn().setLifeDelay(2).spawn(shooter.level(), pos.x, pos.y, pos.z).setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT).spawn(shooter.level(), pos.x, pos.y, pos.z);
        }
    }
}
