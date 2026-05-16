package io.github.pouffy.tcompat.common.capability.projectile.void_scatter;

import io.github.pouffy.tcompat.common.network.LeechingSyncPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.VoidScatterSyncPacket;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class VoidScatterCapability implements VoidScatter {
    private final Projectile projectile;
    private boolean shouldScatter;

    public VoidScatterCapability(Projectile projectile) {
        this.projectile = projectile;
    }

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setScatter", Triple.of(Type.BOOLEAN, (object) -> this.setScatter((boolean) object), this::shouldScatter))
    );

    @Override
    public Projectile getProjectile() {
        return this.projectile;
    }

    @Override
    public void setScatter(boolean shouldScatter) {
        this.shouldScatter = shouldScatter;
    }

    @Override
    public boolean shouldScatter() {
        return this.shouldScatter;
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return synchableFunctions;
    }

    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new VoidScatterSyncPacket(this.getProjectile().getId(), key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return TCompatNetworking.INSTANCE;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("shouldScatter", this.shouldScatter());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("shouldScatter")) {
            this.setScatter(tag.getBoolean("shouldScatter"));
        }
    }
}
