package io.github.pouffy.tcompat.client;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.client.modifier.AmbrofusionModifierModel;
import io.github.pouffy.tcompat.client.particle.KineticEnergyParticle;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.species.SpeciesInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.client.modifiers.ModifierModelManager;

import static io.github.pouffy.tcompat.TCompat.getResource;

@Mod.EventBusSubscriber(modid = TCompat.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TComToolsClientEvents {
    @SubscribeEvent
    static void registerModifierModels(ModifierModelManager.ModifierModelRegistrationEvent event) {
        event.registerModel(getResource("ambrofusion"), AmbrofusionModifierModel.UNBAKED_INSTANCE);
    }

    @SubscribeEvent
    public static void registerParticleTypes(RegisterParticleProvidersEvent event) {
        if (CompatHelper.isLoaded("species")) {
            event.registerSpriteSet(SpeciesInit.SMALL_KINETIC_ENERGY.get(), KineticEnergyParticle.SmallProvider::new);
        }
    }
}
