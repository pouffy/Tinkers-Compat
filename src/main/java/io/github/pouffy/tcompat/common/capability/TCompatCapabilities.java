package io.github.pouffy.tcompat.common.capability;


import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.capability.phoenix.PhoenixTouched;
import io.github.pouffy.tcompat.common.capability.phoenix.PhoenixTouchedCapability;
import net.minecraft.world.entity.Entity;
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

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(PhoenixTouched.class);
    }

    @Mod.EventBusSubscriber(modid = TCompat.MOD_ID)
    public static class Registration {
        @SubscribeEvent
        public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Projectile projectile) {
                event.addCapability(TCompat.getResource("phoenix_touched"), new CapabilityProvider(TCompatCapabilities.PHOENIX_TOUCHED_CAPABILITY, new PhoenixTouchedCapability(projectile)));
            }
        }
    }
}
