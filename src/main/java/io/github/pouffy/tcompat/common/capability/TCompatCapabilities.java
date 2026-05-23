package io.github.pouffy.tcompat.common.capability;


import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.capability.compatible.LightningOwner;
import io.github.pouffy.tcompat.common.capability.compatible.LightningOwnerCapability;
import io.github.pouffy.tcompat.common.capability.projectile.phoenix_touched.PhoenixTouched;
import io.github.pouffy.tcompat.common.capability.projectile.phoenix_touched.PhoenixTouchedCapability;
import io.github.pouffy.tcompat.common.capability.projectile.leeching.ProjectileAbility;
import io.github.pouffy.tcompat.common.capability.projectile.leeching.ProjectileAbilityCapability;
import io.github.pouffy.tcompat.common.capability.projectile.void_scatter.VoidScatter;
import io.github.pouffy.tcompat.common.capability.projectile.void_scatter.VoidScatterCapability;
import io.github.pouffy.tcompat.common.capability.vampire_healing.VampireHealing;
import io.github.pouffy.tcompat.common.capability.vampire_healing.VampireHealingCapability;
import io.github.pouffy.tcompat.common.capability.void_touched.VoidTouched;
import io.github.pouffy.tcompat.common.capability.void_touched.VoidTouchedCapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
    public static final Capability<PhoenixTouched> PHOENIX_TOUCHED_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
    public static final Capability<ProjectileAbility> PROJECTILE_ABILITY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
    public static final Capability<VoidScatter> VOID_SCATTER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
    public static final Capability<VoidTouched> VOID_TOUCHED_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
    public static final Capability<LightningOwner> LIGHTNING_OWNER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
    public static final Capability<VampireHealing> VAMPIRE_HEALING_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(PhoenixTouched.class);
    }

    @Mod.EventBusSubscriber(modid = TCompat.MOD_ID)
    public static class Registration {
        @SubscribeEvent
        public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player player) {
            }
            if (event.getObject() instanceof Projectile projectile) {
                event.addCapability(TCompat.getResource("phoenix_touched"), new CapabilityProvider(TCompatCapabilities.PHOENIX_TOUCHED_CAPABILITY, new PhoenixTouchedCapability(projectile)));
                event.addCapability(TCompat.getResource("projectile_ability"), new CapabilityProvider(TCompatCapabilities.PROJECTILE_ABILITY_CAPABILITY, new ProjectileAbilityCapability(projectile)));
                event.addCapability(TCompat.getResource("void_scatter"), new CapabilityProvider(TCompatCapabilities.VOID_SCATTER_CAPABILITY, new VoidScatterCapability(projectile)));
            }
            if (event.getObject() instanceof LivingEntity livingEntity) {
                event.addCapability(TCompat.getResource("void_touched"), new CapabilityProvider(TCompatCapabilities.VOID_TOUCHED_CAPABILITY, new VoidTouchedCapability(livingEntity)));
                event.addCapability(TCompat.getResource("vampire_healing"), new CapabilityProvider(TCompatCapabilities.VAMPIRE_HEALING_CAPABILITY, new VampireHealingCapability(livingEntity)));
            }
            if (event.getObject() instanceof LightningBolt lightningBolt) {
                event.addCapability(TCompat.getResource("lightning_owner"), new CapabilityProvider(TCompatCapabilities.LIGHTNING_OWNER_CAPABILITY, new LightningOwnerCapability(lightningBolt)));
            }
        }
    }
}
