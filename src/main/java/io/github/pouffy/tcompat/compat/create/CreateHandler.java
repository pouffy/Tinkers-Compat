package io.github.pouffy.tcompat.compat.create;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.Create;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.create.modifier.harvest.WrenchingModifier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CreateHandler {

    public static void wrenchSound(Level level, BlockPos pos) {
        if (!CompatHelper.isLoaded("create")) return;
        LoadedOnly.wrenchSound(level, pos);
    }

    public static InteractionResult wrenchWrenchable(UseOnContext context, Player player, BlockState state, Block block) {
        if (!CompatHelper.isLoaded("create")) return InteractionResult.PASS;
        return LoadedOnly.wrenchWrenchable(context, player, state, block);
    }

    public static class LoadedOnly {

        public static void wrenchSound(Level level, BlockPos pos) {
            AllSoundEvents.WRENCH_REMOVE.playOnServer(level, pos, 1.0F, Create.RANDOM.nextFloat() * 0.5F + 0.5F);
        }

        public static InteractionResult wrenchWrenchable(UseOnContext context, Player player, BlockState state, Block block) {
            if (block instanceof IWrenchable actor) {
                return player.isShiftKeyDown() ? actor.onSneakWrenched(state, context) : actor.onWrenched(state, context);
            } else {
                return player.isShiftKeyDown() && WrenchingModifier.canWrenchPickup(state) ? WrenchingModifier.onItemUseOnOther(context) : InteractionResult.PASS;
            }
        }


    }
}
