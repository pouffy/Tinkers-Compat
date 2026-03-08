package io.github.pouffy.tcompat.compat.aether;

import io.github.pouffy.tcompat.TCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.BlockInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class AscensionModifier extends NoLevelsModifier implements BlockInteractionModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BLOCK_INTERACT);
    }

    @Override
    public InteractionResult beforeBlockUse(IToolStackView tool, ModifierEntry modifier, UseOnContext context, InteractionSource source) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        ItemStack itemStack = context.getItemInHand();
        BlockState blockState = level.getBlockState(blockPos);
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        if (player != null && !player.isSpectator()) {
            if ((itemStack.isCorrectToolForDrops(blockState)) && isFree(level.getBlockState(blockPos.above()))) {
                if (level.getBlockEntity(blockPos) == null && blockState.getDestroySpeed(level, blockPos) >= 0.0F && !blockState.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF) && !blockState.is(BlockTags.create(TCompat.getResource("aether:gravitite_ability_blacklist")))) {
                    if (!level.isClientSide()) {
                        Entity entity = createEntity(level, blockPos, blockState);
                        level.addFreshEntity(entity);
                        level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                        itemStack.hurtAndBreak(4, player, (p) -> p.broadcastBreakEvent(hand));
                    } else {
                        player.swing(hand);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    public static boolean isFree(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.liquid() || state.canBeReplaced();
    }

    public static Entity createEntity(Level level, BlockPos pos, BlockState blockState) {
        var type = BuiltInRegistries.ENTITY_TYPE.get(TCompat.getResource("aether:floating_block")).builtInRegistryHolder();
        CompoundTag compoundtag = new CompoundTag();
        compoundtag.putString("id", type.key().location().toString());
        compoundtag.put("BlockState", NbtUtils.writeBlockState(blockState));
        compoundtag.putBoolean("Natural", false);
        if (blockState.is(BlockTags.ANVIL)) {
            compoundtag.putBoolean("HurtEntities", true);
            compoundtag.putFloat("FallHurtAmount", 2.0F);
            compoundtag.putInt("FallHurtMax", 40);
        }
        Entity entity = EntityType.loadEntityRecursive(compoundtag, level, (e) -> {
            e.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, e.getYRot(), e.getXRot());
            return e;
        });
        entity.blocksBuilding = true;
        entity.setPos(entity.getX(), entity.getY() + (double) ((1.0F - entity.getBbHeight()) / 2.0F), entity.getZ());
        entity.setDeltaMovement(Vec3.ZERO);
        return entity;
    }
}
