package io.github.pouffy.tcompat.common;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.capability.phoenix.PhoenixTouched;
import io.github.pouffy.tcompat.common.module.AutosmeltModule;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;

@Mod.EventBusSubscriber(modid = TCompat.MOD_ID)
public class TCCommonEvents {

    @SubscribeEvent
    static void projectileHit(ProjectileImpactEvent event) {
        HitResult hit = event.getRayTraceResult();
        if (hit instanceof EntityHitResult entityHitResult && event.getEntity() instanceof Projectile projectile) {
            PhoenixTouched.get(projectile).ifPresent(phoenixTouched -> {
                if (phoenixTouched.isPhoenixProjectile() && phoenixTouched.getFireTime() > 0) {
                    entityHitResult.getEntity().setSecondsOnFire(phoenixTouched.getFireTime());
                }
            });
        }
    }

    //ModifierModule.LOADER.register(getResource("autosmelt"), AutosmeltModule.LOADER);

    @SubscribeEvent
    void registerSerializers(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.RECIPE_SERIALIZER) {
            ModifierModule.LOADER.register(TCompat.getResource("autosmelt"), AutosmeltModule.LOADER);
        }
    }
}
