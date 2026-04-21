package io.github.pouffy.tcompat.common.network.base;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.commons.lang3.tuple.Triple;
import oshi.util.tuples.Quartet;

public abstract class SyncLivingEntityPacket<T extends INBTSynchable<CompoundTag>> extends SyncPacket {
    private final int entityID;

    public SyncLivingEntityPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        this(values.getA(), values.getB(), values.getC(), values.getD());
    }

    public SyncLivingEntityPacket(int entityID, String key, INBTSynchable.Type type, Object value) {
        super(key, type, value);
        this.entityID = entityID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        super.encode(buf);
    }

    public static Quartet<Integer, String, INBTSynchable.Type, Object> decodeEntityValues(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        Triple<String, INBTSynchable.Type, Object> decodeBase = SyncPacket.decodeValues(buf);
        return new Quartet<>(entityID, decodeBase.getLeft(), decodeBase.getMiddle(), decodeBase.getRight());
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null && this.value != null) {
            LivingEntity entity = playerEntity.level().getEntity(this.entityID) instanceof LivingEntity living ? living : null;
            if (entity != null && !entity.level().isClientSide()) {
                this.getCapability(entity).ifPresent((synchable) -> synchable.getSynchableFunctions().get(this.key).getMiddle().accept(this.value));
            }
        } else {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && this.value != null) {
                LivingEntity entity = Minecraft.getInstance().level.getEntity(this.entityID) instanceof LivingEntity living ? living : null;
                if (entity != null && entity.level().isClientSide()) {
                    this.getCapability(entity).ifPresent((synchable) -> synchable.getSynchableFunctions().get(this.key).getMiddle().accept(this.value));
                }
            }
        }
    }

    public abstract LazyOptional<T> getCapability(LivingEntity entity);
}
