package io.github.pouffy.tcompat.common.capability.projectile.leeching;

import io.github.pouffy.tcompat.common.network.LeechingSyncPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LeechingCapability implements Leeching {
    private final Projectile projectile;
    private boolean isLeeching;
    private boolean isAmphithere;
    private boolean isStymphalian;

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setLeeching", Triple.of(Type.BOOLEAN, (object) -> this.setLeeching((boolean) object), this::isLeeching)),
            Map.entry("setAmphithere", Triple.of(Type.BOOLEAN, (object) -> this.setAmphithere((boolean) object), this::isAmphithere)),
            Map.entry("setStymphalian", Triple.of(Type.BOOLEAN, (object) -> this.setStymphalian((boolean) object), this::isStymphalian))
    );

    public LeechingCapability(Projectile projectile) {
        this.projectile = projectile;
    }

    @Override
    public Projectile getProjectile() {
        return this.projectile;
    }

    @Override
    public void setLeeching(boolean isLeeching) {
        this.isLeeching = isLeeching;
    }

    @Override
    public boolean isLeeching() {
        return this.isLeeching;
    }

    @Override
    public void setAmphithere(boolean isAmphithere) {
        this.isAmphithere = isAmphithere;
    }

    @Override
    public boolean isAmphithere() {
        return this.isAmphithere;
    }

    @Override
    public void setStymphalian(boolean isStymphalian) {
        this.isStymphalian = isStymphalian;
    }

    @Override
    public boolean isStymphalian() {
        return this.isStymphalian;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("isLeeching", this.isLeeching());
        tag.putBoolean("isAmphithere", this.isAmphithere());
        tag.putBoolean("isStymphalian", this.isStymphalian());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("isLeeching")) {
            this.setLeeching(tag.getBoolean("isLeeching"));
        }
        if (tag.contains("isAmphithere")) {
            this.setLeeching(tag.getBoolean("isAmphithere"));
        }
        if (tag.contains("isStymphalian")) {
            this.setStymphalian(tag.getBoolean("isStymphalian"));
        }
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return synchableFunctions;
    }

    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new LeechingSyncPacket(this.getProjectile().getId(), key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return TCompatNetworking.INSTANCE;
    }
}
