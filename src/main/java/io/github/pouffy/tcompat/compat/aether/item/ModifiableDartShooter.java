package io.github.pouffy.tcompat.compat.aether.item;

import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.aether.AetherHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.BowAmmoModifierHook;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.capability.PersistentDataCapability;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.item.ranged.ModifiableLauncherItem;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
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
        ToolStack tool = ToolStack.from(dartShooter);
        int duration = this.getUseDuration(dartShooter);
        for (ModifierEntry entry : tool.getModifiers()) {
            entry.getHook(ModifierHooks.TOOL_USING).beforeReleaseUsing(tool, entry, living, duration, 0, ModifierEntry.EMPTY);
        }
        if (!tool.isBroken()) {
            Player player;
            if (living instanceof Player p) {
                player = p;
            } else {
                player = null;
            }

            boolean creative = player != null && player.getAbilities().instabuild;
            Predicate<ItemStack> ammoPredicate = this.getAllSupportedProjectiles();
            ItemStack foundAmmo = BowAmmoModifierHook.getAmmo(tool, dartShooter, living, ammoPredicate);
            boolean hasAmmo = !foundAmmo.isEmpty() || creative && !tool.getVolatileData().getBoolean(BowAmmoModifierHook.SKIP_INVENTORY_AMMO);

            if (hasAmmo && duration >= 0) {
                float charge = GeneralInteractionModifierHook.getToolCharge(tool, (float) duration);
                float velocity = ConditionalStatModifierHook.getModifiedStat(tool, living, ToolStats.VELOCITY);
                float power = charge * velocity;

                if (!(power < 0.1F)) {
                    if (!level.isClientSide) {
                        int desiredProjectiles = 1;
                        desiredProjectiles = BowAmmoModifierHook.getDesiredProjectiles(tool);
                        ItemStack ammo = BowAmmoModifierHook.consumeAmmo(tool, dartShooter, living, player, ammoPredicate, desiredProjectiles);
                        if (ammo.isEmpty()) {
                            Item fallbackDart = ObjectRetriever.getItem("aether:golden_dart").orElse(Items.ARROW);
                            ammo = new ItemStack(fallbackDart);
                        }

                        Item ammoItem = ammo.getItem();


                        SoundEvent sound = AetherHandler.dartShooterFireSound();

                        float inaccuracy = ModifierUtil.getInaccuracy(tool, living);
                        float startAngle = ModifiableLauncherItem.getAngleStart(ammo.getCount());
                        int primaryIndex = ammo.getCount() / 2;

                        for(int arrowIndex = 0; arrowIndex < ammo.getCount(); ++arrowIndex) {
                            AbstractArrow dart = AetherHandler.createDart(ammoItem, level, ammo, living);
                            if (dart == null) break;
                            float angle = startAngle + (float)(10 * arrowIndex);

                            AbstractArrow prepared = prepareDart(dart, tool, ammo, angle, inaccuracy, power, charge, arrowIndex, primaryIndex, living, creative);

                            level.addFreshEntity(prepared);
                            level.playSound(null, living.getX(), living.getY(), living.getZ(), sound, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + charge * 0.5F + angle / 10.0F);
                        }

                        int damage = ammo.getCount();
                        ToolDamageUtil.damageAnimated(tool, damage, living, living.getUsedItemHand());
                    }

                    if (player != null) {
                        player.awardStat(Stats.ITEM_USED.get(this));
                    }
                }
            }
        }
        return dartShooter;
    }

    public static AbstractArrow prepareDart(AbstractArrow dart, ToolStack tool, ItemStack ammo, float angle, float inaccuracy, float power, float charge, int arrowIndex, int primaryIndex, LivingEntity shooter, boolean creative) {
        dart.shootFromRotation(shooter, shooter.getXRot() + angle, shooter.getYRot(), 0.0F, power * 3.0F, inaccuracy);
        if (charge == 1.0F) {
            dart.setCritArrow(true);
        }

        float baseArrowDamage = (float)(dart.getBaseDamage() - (double)2.0F + (double) tool.getStats().get(ToolStats.PROJECTILE_DAMAGE));
        dart.setBaseDamage(ConditionalStatModifierHook.getModifiedStat(tool, shooter, ToolStats.PROJECTILE_DAMAGE, baseArrowDamage));
        ModifierNBT modifiers = tool.getModifiers();
        EntityModifierCapability.getCapability(dart).addModifiers(modifiers);
        ModDataNBT arrowData = PersistentDataCapability.getOrWarn(dart);
        if (creative) {
            dart.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }

        dart.setNoGravity(true);
        for(ModifierEntry entry : modifiers.getModifiers()) {
            entry.getHook(ModifierHooks.PROJECTILE_LAUNCH).onProjectileLaunch(tool, entry, shooter, ammo, dart, dart, arrowData, arrowIndex == primaryIndex);
        }

        return dart;
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
