package io.github.pouffy.tcompat.common;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.capability.phoenix.PhoenixTouched;
import io.github.pouffy.tcompat.common.capability.void_touched.VoidTouched;
import io.github.pouffy.tcompat.common.module.AutosmeltModule;
import io.github.pouffy.tcompat.compat.aether.ThunderstruckModifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;

import java.util.concurrent.atomic.AtomicReference;

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

    @SubscribeEvent
    static void livingTick(LivingEvent.LivingTickEvent event) {
        var entity = event.getEntity();
        VoidTouched.get(entity).ifPresent(voidTouched -> {
            if (voidTouched.isVoided()) {
                voidTouched.tick();
            }
        });
    }

    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        Entity entity = event.getEntity();
        LightningBolt lightningBolt = event.getLightning();
        if (!event.isCanceled() && ThunderstruckModifier.lightningTracking(entity, lightningBolt)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    static void hurt(LivingHurtEvent event) {
        var entity = event.getEntity();
        AtomicReference<Float> amount = new AtomicReference<>(event.getAmount());
        VoidTouched.get(entity).ifPresent(voidTouched -> {
            if (voidTouched.isVoided()) {
                float multiplier = ((voidTouched.getAmplifier()) * 0.05f);
                amount.updateAndGet(v -> v + (v * multiplier));
            }
        });
        if (amount.get() != event.getAmount()) {
            event.setAmount(amount.get());
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
