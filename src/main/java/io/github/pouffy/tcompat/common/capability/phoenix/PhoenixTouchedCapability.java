package io.github.pouffy.tcompat.common.capability.phoenix;

import com.aetherteam.aether.network.packet.PhoenixArrowSyncPacket;
import io.github.pouffy.tcompat.common.network.PhoenixProjectileSyncPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PhoenixTouchedCapability implements PhoenixTouched {
    private final Projectile projectile;

    private boolean isPhoenixProjectile;
    private int fireTime;

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setPhoenixProjectile", Triple.of(Type.BOOLEAN, (object) -> this.setPhoenixProjectile((boolean) object), this::isPhoenixProjectile))
    );

    public PhoenixTouchedCapability(Projectile projectile) {
        this.projectile = projectile;
    }

    @Override
    public Projectile getProjectile() {
        return this.projectile;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("PhoenixProjectile", this.isPhoenixProjectile());
        tag.putInt("FireTime", this.getFireTime());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("PhoenixProjectile")) {
            this.setPhoenixProjectile(tag.getBoolean("PhoenixProjectile"));
        }
        if (tag.contains("FireTime")) {
            this.setFireTime(tag.getInt("FireTime"));
        }
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    @Override
    public void setPhoenixProjectile(boolean isPhoenixProjectile) {
        this.isPhoenixProjectile = isPhoenixProjectile;
    }

    @Override
    public boolean isPhoenixProjectile() {
        return this.isPhoenixProjectile;
    }

    @Override
    public void setFireTime(int time) {
        this.fireTime = time;
    }

    @Override
    public int getFireTime() {
        return this.fireTime;
    }

    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new PhoenixProjectileSyncPacket(this.getProjectile().getId(), key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return TCompatNetworking.INSTANCE;
    }
}
