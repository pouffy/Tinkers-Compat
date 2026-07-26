package io.github.pouffy.tcompat.common;

import io.github.pouffy.tcompat.TCompat;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public final class TCDamageSources {
    public static final ResourceKey<DamageType> CRYOGENIC = ResourceKey.create(Registries.DAMAGE_TYPE, TCompat.getResource("cryogenic"));


    public static DamageSource create(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }
}
