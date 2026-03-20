package io.github.pouffy.tcompat.compat.aether.item;

import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.BowAmmoModifierHook;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ranged.ModifiableLauncherItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.tools.modifiers.ability.interaction.BlockingModifier;

import java.util.function.Predicate;

public class ModifiableDartShooter extends ModifiableLauncherItem {

    public ModifiableDartShooter(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return BlockingModifier.blockWhileCharging(ToolStack.from(stack), UseAnim.BOW);
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

        }
        player.startUsingItem(hand);
        if (!level.isClientSide) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), Sounds.LONGBOW_CHARGE.getSound(), SoundSource.PLAYERS, 0.75F, 1.0F);
        }
        return InteractionResultHolder.consume(shooter);
    }

    public ItemStack finishUsingItem(ItemStack shooter, Level level, LivingEntity living) {
        ToolStack tool = ToolStack.from(shooter);
        int duration = getUseDuration(shooter);
        for (ModifierEntry entry : tool.getModifiers()) {
            entry.getHook(ModifierHooks.TOOL_USING).beforeReleaseUsing(tool, entry, living, duration, 0, ModifierEntry.EMPTY);
        }

        // no broken
        if (tool.isBroken()) {
            return shooter;
        }

        Player player = living instanceof Player p ? p : null;
        boolean creative = player != null && player.getAbilities().instabuild;

        Predicate<ItemStack> ammoPredicate = getSupportedHeldProjectiles();

        ItemStack foundAmmo = BowAmmoModifierHook.getAmmo(tool, shooter, living, ammoPredicate);
        boolean hasAmmo = !foundAmmo.isEmpty() || creative && !tool.getVolatileData().getBoolean(BowAmmoModifierHook.SKIP_INVENTORY_AMMO);

        if (player != null) {
            ForgeEventFactory.onArrowLoose(shooter, level, player, 0, hasAmmo);
        }

        if (!hasAmmo) {
            return shooter;
        }

        if (!level.isClientSide) {
            int desiredProjectiles = BowAmmoModifierHook.getDesiredProjectiles(tool);

            ItemStack ammo = BowAmmoModifierHook.consumeAmmo(tool, shooter, living, player, ammoPredicate, desiredProjectiles);

            if (ammo.isEmpty()) {
                Item fallbackDart = ObjectRetriever.getItem("aether:golden_dart").orElse(Items.ARROW);
                ammo = new ItemStack(fallbackDart);
            }
        }

        return shooter;
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
