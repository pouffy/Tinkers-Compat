package io.github.pouffy.tcompat.client.compat;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IceFireClientHandler {

    public static void hydraParticles(Projectile projectile, double xRatio, double zRatio, double d0, double d1, double d2) {
        if (!CompatHelper.isLoaded("iceandfire")) return;
        LoadedOnly.hydraParticles(projectile, xRatio, zRatio, d0, d1, d2);
    }

    public static void amphithereGust(Entity entity) {
        if (!CompatHelper.isLoaded("iceandfire")) return;
        LoadedOnly.amphithereGust(entity);
    }

    @OnlyIn(Dist.CLIENT)
    public static class LoadedOnly {
        public static void hydraParticles(Projectile projectile, double xRatio, double zRatio, double d0, double d1, double d2) {
            RandomSource random = projectile.level().random;
            IceAndFire.PROXY.spawnParticle(EnumParticles.Hydra, projectile.getX() + xRatio + (random.nextFloat() * projectile.getBbWidth() * 1.0F) - (double)projectile.getBbWidth() - d0 * (double)10.0F, projectile.getY() + (double)(random.nextFloat() * projectile.getBbHeight()) - d1 * (double)10.0F, projectile.getZ() + zRatio + (double)(random.nextFloat() * projectile.getBbWidth() * 1.0F) - (double)projectile.getBbWidth() - d2 * (double)10.0F, 0.1, 1.0F, 0.1);
        }

        public static void amphithereGust(Entity entity) {
            entity.playSound(IafSoundRegistry.AMPHITHERE_GUST, 1.0F, 1.0F);
        }
    }
}
