package io.github.pouffy.tcompat.common.capability;


import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.capability.lightning.LightningOwner;
import io.github.pouffy.tcompat.common.capability.lightning.LightningOwnerCapability;
import io.github.pouffy.tcompat.common.capability.living.status.LivingStatus;
import io.github.pouffy.tcompat.common.capability.living.status.LivingStatusCapability;
import io.github.pouffy.tcompat.common.capability.projectile.ability.ProjectileAbility;
import io.github.pouffy.tcompat.common.capability.projectile.ability.ProjectileAbilityCapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TCompat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCompatCapabilities {
    public static final Capability<ProjectileAbility> PROJECTILE_ABILITY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
    public static final Capability<LivingStatus> LIVING_STATUS_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
    public static final Capability<LightningOwner> LIGHTNING_OWNER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(ProjectileAbility.class);
        event.register(LightningOwner.class);
    }

    @Mod.EventBusSubscriber(modid = TCompat.MOD_ID)
    public static class Registration {
        @SubscribeEvent
        public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Projectile projectile) {
                event.addCapability(TCompat.getResource("projectile_ability"), new CapabilityProvider(TCompatCapabilities.PROJECTILE_ABILITY_CAPABILITY, new ProjectileAbilityCapability(projectile)));
            }
            if (event.getObject() instanceof LivingEntity livingEntity) {
                event.addCapability(TCompat.getResource("living_status"), new CapabilityProvider(TCompatCapabilities.LIVING_STATUS_CAPABILITY, new LivingStatusCapability(livingEntity)));
            }
            if (event.getObject() instanceof LightningBolt lightningBolt) {
                event.addCapability(TCompat.getResource("lightning_owner"), new CapabilityProvider(TCompatCapabilities.LIGHTNING_OWNER_CAPABILITY, new LightningOwnerCapability(lightningBolt)));
            }
        }
    }
}
