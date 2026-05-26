package io.github.pouffy.tcompat.common;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.capability.compatible.LightningOwner;
import io.github.pouffy.tcompat.common.capability.projectile.ability.ProjectileAbility;
import io.github.pouffy.tcompat.common.capability.vampire_healing.VampireHealing;
import io.github.pouffy.tcompat.common.capability.void_touched.VoidTouched;
import io.github.pouffy.tcompat.common.cooldown.ModifierCooldowns;
import io.github.pouffy.tcompat.common.modifier.hook.EntitySensitiveAttributesModifierHook;
import io.github.pouffy.tcompat.common.network.SwingClientArmPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.base.PacketRelay;
import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.cataclysm.CataclysmHandler;
import io.github.pouffy.tcompat.compat.curios.CuriosHandler;
import io.github.pouffy.tcompat.compat.malum.MalumHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.mantle.util.OffhandCooldownTracker;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@Mod.EventBusSubscriber(modid = TCompat.MOD_ID)
public class TCCommonEvents {

    @SubscribeEvent
    static void projectileHit(ProjectileImpactEvent event) {
        if (event.getEntity() instanceof Projectile projectile) {
            ProjectileAbility.get(projectile).ifPresent(projectileAbility -> {
                Projectile innerProjectile = projectileAbility.projectile();
                projectileAbility.getActiveAbilities().forEach((name, ability) -> {
                    ability.impactEvent(event, innerProjectile);
                });
            });
        }
    }

    @SubscribeEvent
    public static void livingVisibility(LivingEvent.LivingVisibilityEvent event) {
        Entity entity = event.getLookingEntity();
        if (entity instanceof LivingEntity watcher) {
            LivingEntity target = event.getEntity();
            if (MalumHandler.isCloaking(target)) {
                float visibilityModifier = 0.25f;
                event.modifyVisibility(visibilityModifier);
            }
        }
    }

    @SubscribeEvent
    public static void onLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        ItemStack rightItem = event.getEntity().getMainHandItem();
        EquipmentHelper.asTool(rightItem, (tool) -> {
            boolean flag = false;
            if (!player.getItemInHand(InteractionHand.MAIN_HAND).is(rightItem.getItem())) return;
            for (ModifierEntry entry : tool.getModifiers()) {
                boolean swing = entry.getHook(GlobalInit.TOOL_SWING).swingMainHand(tool, entry, rightItem, event.getEntity());
                if (swing) flag = true;
            }
            if (event.getLevel().isClientSide && flag) {
                PacketRelay.sendToServer(TCompatNetworking.INSTANCE, new SwingClientArmPacket(event.getEntity(), InteractionHand.MAIN_HAND));
            }
        });
        //Only swing off-hand if the main hand is empty.
        ItemStack leftItem = event.getEntity().getOffhandItem();
        if (rightItem.isEmpty()) {
            EquipmentHelper.asTool(leftItem, (tool) -> {
                boolean flag = false;
                if (!player.getItemInHand(InteractionHand.OFF_HAND).is(leftItem.getItem())) return;
                for (ModifierEntry entry : tool.getModifiers()) {
                    boolean swing = entry.getHook(GlobalInit.TOOL_SWING).swingOffHand(tool, entry, leftItem, event.getEntity());
                    if (swing) flag = true;
                }
                OffhandCooldownTracker.swingHand(player, InteractionHand.OFF_HAND, false);
                if (event.getLevel().isClientSide && flag) {
                    PacketRelay.sendToServer(TCompatNetworking.INSTANCE, new SwingClientArmPacket(event.getEntity(), InteractionHand.OFF_HAND));
                }
            });
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.level().isClientSide() && !entity.isDeadOrDying()) {
            CataclysmHandler.ghostDodge(event);
        }
    }

    @SubscribeEvent
    static void livingTick(LivingEvent.LivingTickEvent event) {
        var entity = event.getEntity();
        VoidTouched.get(entity).ifPresent(voidTouched -> {
            if (voidTouched.isVoided()) {
                voidTouched.tick();
            }
        });
        VampireHealing.get(entity).ifPresent(VampireHealing::onUpdate);
        TCompat.COOLDOWN_HANDLER.tickEntity(entity);
        CuriosHandler.tickHook(event);
        MalumHandler.idleRestoration(event);
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (!event.level.isClientSide) {
            if (event.phase != TickEvent.Phase.START) {
                TCompat.COOLDOWN_HANDLER.tickPlayers(event.level);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            ModifierCooldowns.getModifierCooldowns(serverPlayer).syncToPlayer(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.getEntity() instanceof ServerPlayer newServerPlayer) {
            ModifierCooldowns oldCooldowns = ModifierCooldowns.getModifierCooldowns(event.getOriginal());
            ModifierCooldowns newCooldowns = ModifierCooldowns.getModifierCooldowns(newServerPlayer);
            oldCooldowns.getModifierCooldowns().forEach((modifierId, cooldown) -> newCooldowns.getModifierCooldowns().put(modifierId, cooldown));
        }
    }

    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        Entity entity = event.getEntity();
        LightningBolt lightningBolt = event.getLightning();
        if (!event.isCanceled() && LightningOwner.lightningTracking(entity, lightningBolt)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    static void hurt(LivingHurtEvent event) {
        MalumHandler.exposeSoul(event);
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            MalumHandler.volatileDistortion(event, attacker);
            MalumHandler.reactiveShielding(event, attacker);
            MalumHandler.heretic(event);
            MalumHandler.igneousSolace(event);
        }
        VoidTouched.get(event.getEntity()).ifPresent(voidTouched -> voidTouched.hurtEvent(event));
    }

    @SubscribeEvent
    static void changeEquipment(LivingEquipmentChangeEvent event) {
        LivingEntity wearer = event.getEntity();
        ItemStack from = event.getFrom();
        ItemStack to = event.getTo();
        if (!from.isEmpty() && EquipmentHelper.isTool(from)) {
            ToolStack fromTool = ToolStack.from(from);
            wearer.getAttributes().removeAttributeModifiers(EntitySensitiveAttributesModifierHook.getAttributeModifiers(fromTool, event.getSlot(), wearer));
        }
        if (!to.isEmpty() && EquipmentHelper.isTool(to)) {
            ToolStack toTool = ToolStack.from(to);
            wearer.getAttributes().addTransientAttributeModifiers(EntitySensitiveAttributesModifierHook.getAttributeModifiers(toTool, event.getSlot(), wearer));
        }
    }

    @SubscribeEvent
    static void effectAdded(MobEffectEvent.Added event) {
        MalumHandler.ailmentCleansing(event);
        MalumHandler.twinnedDuration(event);
    }

    @SubscribeEvent
    static void breakSpeed(PlayerEvent.BreakSpeed event) {
        MalumHandler.fervor(event);
    }

    @SubscribeEvent
    static void livingDeath(LivingDeathEvent event) {
        MalumHandler.sacrificialEmpowerment(event);
    }
}
