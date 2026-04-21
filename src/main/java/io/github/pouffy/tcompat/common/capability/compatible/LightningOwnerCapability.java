package io.github.pouffy.tcompat.common.capability.compatible;

import io.github.pouffy.tcompat.common.network.LightningOwnerSyncPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LightningOwnerCapability implements LightningOwner {
    private final Entity entity;

    private Entity lightningOwner; //Used for: Lightnum Material. Saves the attacker as the owner of the LightningBolt to avoid hurting allies.

    public LightningOwnerCapability(Entity entity){
        this.entity = entity;
    }

    @Override
    public Entity getEntity() {
        return this.entity;
    }

    //Only for Lightning
    @Override
    public void setLightningOwner(Entity lightningOwner) {
        if (this.getEntity() instanceof LightningBolt) {
            this.lightningOwner = lightningOwner;
        }
    }

    @Override
    public Entity getLightningOwner() {
        return this.lightningOwner;
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return Map.of();
    }

    @Override
    public BasePacket getSyncPacket(String s, Type type, Object o) {
        return new LightningOwnerSyncPacket(this.getEntity().getId(), s, type, o);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return TCompatNetworking.INSTANCE;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (this.getLightningOwner() != null) {
            tag.putInt("LightningOwner", this.getLightningOwner().getId());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("LightningOwner")) {
            this.setLightningOwner(this.getEntity().level().getEntity(tag.getInt("Owner")));
        }
    }
}
