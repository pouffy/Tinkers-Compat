package io.github.pouffy.tcompat.compat.species;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class SpeciesInit extends CompatInitializer {
    public static final ModifierDeferredRegister SPECIES_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final DeferredRegister<ParticleType<?>> SPECIES_PT = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TCompat.MOD_ID);

    public static final StaticModifier<KineticModifier> ricoshield = SPECIES_M.register("ricoshield", KineticModifier::new);
    public static final StaticModifier<WickedModifier> wicked = SPECIES_M.register("wicked", WickedModifier::new);
    public static final StaticModifier<SwappingModifier> swapping = SPECIES_M.register("swapping", SwappingModifier::new);
    public static final StaticModifier<BirtModifier> birt = SPECIES_M.register("birt", BirtModifier::new);

    public static final RegistryObject<SimpleParticleType> SMALL_KINETIC_ENERGY = SPECIES_PT.register("small_kinetic_energy", () -> new SimpleParticleType(true));

    public static void init(IEventBus eventBus) {
        SPECIES_M.register(eventBus);
        SPECIES_PT.register(eventBus);
    }
}
