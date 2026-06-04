package io.github.pouffy.tcompat.compat.quark.item;

import io.github.pouffy.tcompat.compat.quark.QuarkHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.world.TinkerWorld;
import slimeknights.tconstruct.world.entity.EnderSlimeEntity;
import slimeknights.tconstruct.world.entity.SkySlimeEntity;
import slimeknights.tconstruct.world.entity.TerracubeEntity;

import java.util.Optional;

public abstract class TCSlimeInABucketItem extends Item {

    public TCSlimeInABucketItem(Properties properties) {
        super(properties.stacksTo(1).craftRemainder(Items.BUCKET));
        QuarkHandler.addToTab(this, Items.TADPOLE_BUCKET, false, CreativeModeTabs.TOOLS_AND_UTILITIES);
    }

    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level world, @NotNull Entity entity, int itemSlot, boolean isSelected) {
        if (world instanceof ServerLevel serverLevel) {
            Vec3 pos = entity.position();
            int x = Mth.floor(pos.x);
            int z = Mth.floor(pos.z);
            boolean slime = isSlimeChunk(serverLevel, x, z);
            boolean excited = stack.getOrCreateTag().getBoolean("excited");
            if (excited != slime) {
                stack.getOrCreateTag().putBoolean("excited", slime);
            }
        }
    }

    abstract Vec3 createSlime(Level worldIn, CompoundTag data, double x, double y, double z);

    public @NotNull InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getClickedFace();
        Level worldIn = context.getLevel();
        Player playerIn = context.getPlayer();
        InteractionHand hand = context.getHand();
        double x = (double)pos.getX() + (double)0.5F + (double)facing.getStepX();
        double y = (double)pos.getY() + (double)0.5F + (double)facing.getStepY();
        double z = (double)pos.getZ() + (double)0.5F + (double)facing.getStepZ();
        if (!worldIn.isClientSide && playerIn != null) {
            CompoundTag data = Optional.of(playerIn.getItemInHand(hand).getOrCreateTag().getCompound("slime_nbt")).orElse(new CompoundTag());
            Vec3 slimePos = createSlime(worldIn, data, x, y, z);
            worldIn.gameEvent(playerIn, GameEvent.ENTITY_PLACE, slimePos);
            playerIn.swing(hand);
        }

        worldIn.playSound(playerIn, pos, SoundEvents.BUCKET_EMPTY, SoundSource.NEUTRAL, 1.0F, 1.0F);
        if (!playerIn.getAbilities().instabuild) {
            playerIn.setItemInHand(hand, new ItemStack(Items.BUCKET));
        }

        return InteractionResult.sidedSuccess(worldIn.isClientSide);
    }

    public @NotNull Component getName(@NotNull ItemStack stack) {
        if (stack.hasTag()) {
            CompoundTag cmp = Optional.of(stack.getOrCreateTag().getCompound("slime_nbt")).orElse(new CompoundTag());
            if (cmp.contains("CustomName")) {
                Component custom = Component.Serializer.fromJson(cmp.getString("CustomName"));
                return Component.translatable("item.quark.slime_in_a_bucket.named", custom);
            }
        }

        return super.getName(stack);
    }

    public static boolean isSlimeChunk(ServerLevel world, int x, int z) {
        ChunkPos chunkpos = new ChunkPos(new BlockPos(x, 0, z));
        return WorldgenRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, world.getSeed(), 987234911L).nextInt(10) == 0;
    }

    public static class Sky extends TCSlimeInABucketItem {

        public Sky(Properties properties) {
            super(properties);
        }

        @Override
        Vec3 createSlime(Level worldIn, CompoundTag data, double x, double y, double z) {
            SkySlimeEntity slime = new SkySlimeEntity(TinkerWorld.skySlimeEntity.get(), worldIn);
            if (data != null) {
                slime.load(data);
            } else {
                slime.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1.0F);
                slime.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3);
                slime.setHealth(slime.getMaxHealth());
            }
            slime.setPos(x, y, z);
            Vec3 position = slime.position();
            worldIn.addFreshEntity(slime);
            return position;
        }
    }

    public static class Ender extends TCSlimeInABucketItem {

        public Ender(Properties properties) {
            super(properties);
        }

        @Override
        Vec3 createSlime(Level worldIn, CompoundTag data, double x, double y, double z) {
            EnderSlimeEntity slime = new EnderSlimeEntity(TinkerWorld.enderSlimeEntity.get(), worldIn);
            if (data != null) {
                slime.load(data);
            } else {
                slime.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1.0F);
                slime.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3);
                slime.setHealth(slime.getMaxHealth());
            }
            slime.setPos(x, y, z);
            Vec3 position = slime.position();
            worldIn.addFreshEntity(slime);
            return position;
        }
    }

    public static class Terra extends TCSlimeInABucketItem {

        public Terra(Properties properties) {
            super(properties);
        }

        @Override
        Vec3 createSlime(Level worldIn, CompoundTag data, double x, double y, double z) {
            TerracubeEntity slime = new TerracubeEntity(TinkerWorld.terracubeEntity.get(), worldIn);
            if (data != null) {
                slime.load(data);
            } else {
                slime.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1.0F);
                slime.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3);
                slime.setHealth(slime.getMaxHealth());
            }
            slime.setPos(x, y, z);
            Vec3 position = slime.position();
            worldIn.addFreshEntity(slime);
            return position;
        }
    }
}
