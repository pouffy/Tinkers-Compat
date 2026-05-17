package io.github.pouffy.tcompat.common.network;

import io.github.pouffy.tcompat.common.capability.projectile.leeching.ProjectileAbility;
import io.github.pouffy.tcompat.common.network.base.INBTSynchable;
import io.github.pouffy.tcompat.common.network.base.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Quartet;

public class ProjectileAbilitySyncPacket extends SyncEntityPacket<ProjectileAbility> {
    public ProjectileAbilitySyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public ProjectileAbilitySyncPacket(int entityID, String key, INBTSynchable.Type type, Object value) {
        super(entityID, key, type, value);
    }

    public static ProjectileAbilitySyncPacket decode(FriendlyByteBuf buf) {
        return new ProjectileAbilitySyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<ProjectileAbility> getCapability(Entity entity) {
        return ProjectileAbility.get((Projectile) entity);
    }
}
