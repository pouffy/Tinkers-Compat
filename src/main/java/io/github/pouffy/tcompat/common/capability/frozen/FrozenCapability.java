package io.github.pouffy.tcompat.common.capability.frozen;

import io.github.pouffy.tcompat.common.network.FrozenSyncPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.base.BasePacket;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.datagen.tag.TCEntityTagProv;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FrozenCapability implements Frozen {
    private final LivingEntity entity;

    private boolean isFrozen; //Used for: Ice & Fire Freezing.
    private int frozenTicks; //Used for: Ice & Fire Freezing.

    private boolean shouldSyncAfterJoin;
    private boolean shouldSyncBetweenClients;

    public FrozenCapability(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public LivingEntity getEntity() {
        return this.entity;
    }

    @Override
    public void onUpdate() {
        this.syncAfterJoin();
        this.syncClients();
        if (!CompatHelper.isLoaded("iceandfire")) {
            return;
        }
        if (this.isFrozen()) {
            if (this.getEntity().getType().is(TCEntityTagProv.create("tcompat:ice_dragons"))) {
                this.unfreeze();
            } else if (this.getEntity().isOnFire()) {
                this.unfreeze();
                this.getEntity().clearFire();
            } else if (this.getEntity().isDeadOrDying()) {
                this.unfreeze();
            } else {
                if (this.getFrozenTicks() > 0) {
                    this.setFrozenTicks(this.getFrozenTicks() - 1);
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

    @Override
    public void onJoinLevel() {
        if (this.getEntity().level().isClientSide()) {
            this.setSynched(Direction.SERVER, "setShouldSyncBetweenClients", true);
        }
    }

    @Override
    public void onLogin() {
        this.shouldSyncAfterJoin = true;
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
        if (!isFrozen()) getEntity().playSound(SoundEvents.GLASS_PLACE, 1.0F, 1.0F);
        this.setSynched(Direction.CLIENT, "setFrozenTicks", ticks);
        this.setSynched(Direction.CLIENT, "setFrozen", true);
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
        this.setSynched(Direction.CLIENT, "setFrozen", false);
        this.setSynched(Direction.CLIENT, "setFrozenTicks", 0);
    }

    @Override
    public void setFrozen(boolean frozen) {
        this.isFrozen = frozen;
    }

    @Override
    public void setFrozenTicks(int frozenTicks) {
        this.frozenTicks = frozenTicks;
    }

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setFrozen", Triple.of(Type.BOOLEAN, (object) -> this.setFrozen((boolean) object), this::isFrozen)),
            Map.entry("setFrozenTicks", Triple.of(Type.INT, (object) -> this.setFrozenTicks((int) object), this::getFrozenTicks)),
            Map.entry("setShouldSyncBetweenClients", Triple.of(Type.BOOLEAN, (object) -> this.setShouldSyncBetweenClients((boolean) object), this::shouldSyncBetweenClients))
    );

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return synchableFunctions;
    }

    @Override
    public BasePacket getSyncPacket(String s, Type type, Object o) {
        return new FrozenSyncPacket(this.getEntity().getId(), s, type, o);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return TCompatNetworking.INSTANCE;
    }

    private void syncAfterJoin() {
        if (this.shouldSyncAfterJoin) {
            this.forceSync(Direction.CLIENT);
            this.shouldSyncAfterJoin = false;
        }
    }

    private void syncClients() {
        if (this.shouldSyncBetweenClients()) {
            if (!this.getEntity().level().isClientSide()) {
                MinecraftServer server = this.getEntity().getServer();
                if (server != null) {
                    PlayerList playerList = server.getPlayerList();
                    for (ServerPlayer serverPlayer : playerList.getPlayers()) {
                        if (!serverPlayer.getUUID().equals(this.getEntity().getUUID())) {
                            Frozen.get(serverPlayer).ifPresent(frozen -> {
                                if (frozen instanceof FrozenCapability capability) {
                                    capability.forceSync(Direction.CLIENT);
                                }
                            });
                        }
                    }
                }
            }
            this.setShouldSyncBetweenClients(false);
        }
    }

    private boolean shouldSyncBetweenClients() {
        return this.shouldSyncBetweenClients;
    }

    private void setShouldSyncBetweenClients(boolean shouldSyncBetweenClients) {
        this.shouldSyncBetweenClients = shouldSyncBetweenClients;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("frozenTicks", this.getFrozenTicks());
        tag.putBoolean("isFrozen", this.isFrozen());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("frozenTicks")) {
            this.setFrozenTicks(tag.getInt("frozenTicks"));
        }
        if (tag.contains("isFrozen")) {
            this.setFrozen(tag.getBoolean("isFrozen"));
        }
    }
}
