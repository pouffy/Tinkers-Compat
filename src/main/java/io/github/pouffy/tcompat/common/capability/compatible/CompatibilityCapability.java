package io.github.pouffy.tcompat.common.capability.compatible;

import io.github.pouffy.tcompat.common.network.CompatibilitySyncPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CompatibilityCapability implements Compatibility{
    private final Entity entity;

    private Entity lightningOwner; //Used for: Lightnum Material. Saves the attacker as the owner of the LightningBolt to avoid hurting allies.

    private boolean performVampireHealing; //Used for: Draculite Material. Copy of Aether's vampire healing.

    public CompatibilityCapability(Entity entity){
        this.entity = entity;
    }

    @Override
    public Entity getEntity() {
        return this.entity;
    }

    @Override
    public void tick() {
        this.handleVampireHealing();
    }

    //Only for Living Entities
    @Override
    public void setVampireHealing(boolean performVampireHealing) {
        if (this.getEntity() instanceof LivingEntity) {
            this.performVampireHealing = performVampireHealing;
        }
    }

    //Only for Lightning
    @Override
    public void setLightningOwner(Entity lightningOwner) {
        if (this.getEntity() instanceof LightningBolt) {
            this.lightningOwner = lightningOwner;
        }
    }

    @Override
    public boolean performVampireHealing() {
        return this.performVampireHealing;
    }

    @Override
    public Entity getLightningOwner() {
        return this.lightningOwner;
    }

    private void handleVampireHealing() {
        if (!this.getEntity().level().isClientSide() && this.performVampireHealing()) {
            if (this.getEntity() instanceof LivingEntity livingEntity) {
                livingEntity.heal(1.0F);
            }
            this.setVampireHealing(false);
        }
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return Map.of();
    }

    @Override
    public BasePacket getSyncPacket(String s, Type type, Object o) {
        return new CompatibilitySyncPacket(this.getEntity().getId(), s, type, o);
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
