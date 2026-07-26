package io.github.pouffy.tcompat.compat.aether.item;

import com.aetherteam.aether.item.combat.DartShooterItem;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.compat.aether.AetherHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.BowAmmoModifierHook;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ranged.ModifiableLauncherItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.tools.modifiers.ability.interaction.BlockingModifier;

import java.util.function.Predicate;

public class ModifiableDartShooter extends ModifiableLauncherItem {
    private final boolean storeDrawingItem;

    public ModifiableDartShooter(Properties properties, ToolDefinition toolDefinition, boolean storeDrawingItem) {
        super(properties, toolDefinition);
        this.storeDrawingItem = storeDrawingItem;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return BlockingModifier.blockWhileCharging(ToolStack.from(stack), UseAnim.BOW);
    }

    public int getUseDuration(ItemStack pStack) {
        return 10;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack shooter = player.getItemInHand(hand);
        ToolStack tool = ToolStack.from(shooter);
        if (tool.isBroken()) {
            return InteractionResultHolder.fail(shooter);
        }
        ItemStack ammo = BowAmmoModifierHook.getAmmo(tool, shooter, player, getSupportedHeldProjectiles());
        InteractionResultHolder<ItemStack> override = ForgeEventFactory.onArrowNock(shooter, level, player, hand, !ammo.isEmpty());
        if (override != null) {
            return override;
        }
        if (!player.getAbilities().instabuild && ammo.isEmpty() && !tool.getModifiers().has(TinkerTags.Modifiers.CHARGE_EMPTY_BOW_WITH_DRAWTIME)) {
            if (tool.getModifiers().has(TinkerTags.Modifiers.CHARGE_EMPTY_BOW_WITHOUT_DRAWTIME)) {
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(shooter);
            }
            return InteractionResultHolder.fail(shooter);
        }
        GeneralInteractionModifierHook.startDrawtime(tool, player, 1);
        if (!ammo.isEmpty()) {
            if (this.storeDrawingItem) {
                tool.getPersistentData().put(KEY_DRAWBACK_AMMO, ammo.save(new CompoundTag()));
            } else {
                tool.getPersistentData().putBoolean(KEY_DRAWBACK_AMMO, true);
            }
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(shooter);
    }

    public ItemStack finishUsingItem(ItemStack dartShooter, Level level, LivingEntity living) {
        return AetherHandler.handleDartShooterFinished(this, dartShooter, level, living);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (stack) -> stack.is(TCTags.Items.DARTS);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }
}
