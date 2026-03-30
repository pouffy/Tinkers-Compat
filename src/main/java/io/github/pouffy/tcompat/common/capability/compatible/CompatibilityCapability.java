package io.github.pouffy.tcompat.common.capability.compatible;

import io.github.pouffy.tcompat.common.network.CompatibilitySyncPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.datagen.tag.TCEntityTagProv;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CompatibilityCapability implements Compatibility{
    private final Entity entity;

    private Entity lightningOwner; //Used for: Lightnum Material. Saves the attacker as the owner of the LightningBolt to avoid hurting allies.

    private boolean performVampireHealing; //Used for: Draculite Material. Copy of Aether's vampire healing.

    private boolean isFrozen; //Used for: Ice & Fire Freezing.
    private int frozenTicks; //Used for: Ice & Fire Freezing.

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
        this.handleFrozen();
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
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override
    public int getFrozenTicks() {
        return this.frozenTicks;
    }

    public void freeze(int ticks) {
        if (!this.isFrozen) getEntity().playSound(SoundEvents.GLASS_PLACE, 1.0F, 1.0F);
        this.setFrozen(true);
        this.setFrozenTicks(ticks);
    }

    public void unfreeze() {
        for(int i = 0; i < 15; ++i) {
            var random = getEntity().level().getRandom();
            var clientLevel = Minecraft.getInstance().level;
            if (clientLevel != null) {
                ObjectRetriever.getBlock("iceandfire:dragon_ice").ifPresent(block -> clientLevel.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, block.defaultBlockState()), getEntity().getX() + (random.nextDouble() - (double)0.5F) * (double)getEntity().getBbWidth(), getEntity().getY() + random.nextDouble() * (double)getEntity().getBbHeight(), getEntity().getZ() + (random.nextDouble() - (double)0.5F) * (double)getEntity().getBbWidth(), 0.0F, 0.0F, 0.0F));
            }
        }
        getEntity().playSound(SoundEvents.GLASS_BREAK, 3.0F, 1.0F);
        this.setFrozen(false);
        this.setFrozenTicks(0);
    }

    @Override
    public void setFrozen(boolean frozen) {
        this.isFrozen = frozen;
    }

    @Override
    public void setFrozenTicks(int frozenTicks) {
        this.frozenTicks = frozenTicks;
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

    private void handleFrozen() {
        //Don't do anything if Ice & Fire is gone.
        if (!CompatHelper.isLoaded("iceandfire")) {
            return;
        }
        if (this.isFrozen() && this.getEntity() instanceof LivingEntity living) {
            if (living.getType().is(TCEntityTagProv.create("tcompat:ice_dragons"))) {
                this.unfreeze();
            } else if (living.isOnFire()) {
                this.unfreeze();
                living.clearFire();
            } else if (living.isDeadOrDying()) {
                this.unfreeze();
            } else {
                if (this.getFrozenTicks() > 0) {
                    setFrozenTicks(this.getFrozenTicks() - 1);
                } else {
                    this.unfreeze();
                }

                if (this.isFrozen()) {
                    if (getEntity() instanceof Player player) {
                        if (player.isCreative()) {
                            return;
                        }
                    }
                    getEntity().setDeltaMovement(getEntity().getDeltaMovement().multiply(0.25F, 1.0F, 0.25F));
                    if (!(getEntity() instanceof EnderDragon) && !getEntity().onGround()) {
                        getEntity().setDeltaMovement(getEntity().getDeltaMovement().add(0.0F, -0.2, 0.0F));
                    }
                }
            }
        }
    }

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setFrozen", Triple.of(Type.BOOLEAN, (object) -> this.setFrozen((boolean) object), this::isFrozen)),
            Map.entry("setFrozenTicks", Triple.of(Type.INT, (object) -> this.setFrozenTicks((int) object), this::getFrozenTicks))
    );

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return synchableFunctions;
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
        tag.putInt("frozenTicks", this.getFrozenTicks());
        tag.putBoolean("isFrozen", this.isFrozen());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("LightningOwner")) {
            this.setLightningOwner(this.getEntity().level().getEntity(tag.getInt("Owner")));
        }
        this.setFrozen(tag.getBoolean("isFrozen"));
        this.setFrozenTicks(tag.getInt("frozenTicks"));
    }
}
