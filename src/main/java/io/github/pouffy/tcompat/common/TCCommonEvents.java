package io.github.pouffy.tcompat.common;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.capability.cooldown.ModifierCooldowns;
import io.github.pouffy.tcompat.common.capability.projectile.phoenix_touched.PhoenixTouched;
import io.github.pouffy.tcompat.common.capability.projectile.leeching.Leeching;
import io.github.pouffy.tcompat.common.capability.projectile.void_scatter.VoidScatter;
import io.github.pouffy.tcompat.common.capability.vampire_healing.VampireHealing;
import io.github.pouffy.tcompat.common.capability.void_touched.VoidTouched;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.common.module.AutosmeltModule;
import io.github.pouffy.tcompat.common.network.SwingClientArmPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.network.base.PacketRelay;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.aether.modifier.ThunderstruckModifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import slimeknights.mantle.util.OffhandCooldownTracker;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(modid = TCompat.MOD_ID)
public class TCCommonEvents {

    @SubscribeEvent
    static void projectileHit(ProjectileImpactEvent event) {
        HitResult hit = event.getRayTraceResult();
        if (event.getEntity() instanceof Projectile projectile) {
            if (hit instanceof EntityHitResult entityHitResult) {
                PhoenixTouched.get(projectile).ifPresent(phoenixTouched -> {
                    if (phoenixTouched.isPhoenixProjectile() && phoenixTouched.getFireTime() > 0) {
                        entityHitResult.getEntity().setSecondsOnFire(phoenixTouched.getFireTime());
                    }
                });
            }
            VoidScatter.get(projectile).ifPresent(voidScatter -> {
                if (voidScatter.shouldScatter()) {
                    voidScatter.hit(projectile, event.getRayTraceResult());
                    voidScatter.setScatter(false);
                }
            });
            if (hit instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity living) {
                Leeching.get(projectile).ifPresent(leeching -> {
                    if (leeching.isAmphithere()) {
                        leeching.amphithereEffect(living);
                    }
                });
            }
        }

    }


    @SubscribeEvent
    public static void onLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        ItemStack rightItem = event.getEntity().getMainHandItem();
        CompatHelper.asTool(rightItem, (tool) -> {
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
            CompatHelper.asTool(leftItem, (tool) -> {
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
            EquipmentContext context = new EquipmentContext(entity);
            if (CompatHelper.isLoaded("cataclysm")) {
                float dodgeChance = 0.0F;
                boolean projectile = event.getSource().is(DamageTypeTags.IS_PROJECTILE);
                boolean bypass = event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY);
                if (context.hasModifiableArmor()) {
                    for (EquipmentSlot slotType : EquipmentSlot.values()) {
                        if (ModifierUtil.validArmorSlot(entity, slotType)) {
                            IToolStackView toolStack = context.getToolInSlot(slotType);
                            if (toolStack != null && !toolStack.isBroken()) {
                                if (toolStack.getModifier(TCModifiers.ghostly) != ModifierEntry.EMPTY) {
                                    dodgeChance += projectile ? 0.12F : 0.06F;
                                }
                            }
                        }
                    }
                }
                if ((dodgeChance != 0.0F && event.getEntity().getRandom().nextFloat() < dodgeChance) && !bypass) {
                    event.setCanceled(true);
                }
            }
        }
        if (event.getSource().getEntity() instanceof Projectile projectile) {
            Leeching.get(projectile).ifPresent(leeching -> {
                if (leeching.isLeeching()) {
                    if (event.getSource().getDirectEntity() instanceof LivingEntity living) {
                        living.heal(event.getAmount());
                    }
                    if (entity instanceof Player player) {
                        leeching.damageShield(player, event.getAmount());
                    }
                }
            });
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
        if (entity instanceof Player player) {
            ModifierCooldowns.get(player).ifPresent(ModifierCooldowns::tick);
        }
        VampireHealing.get(entity).ifPresent(VampireHealing::onUpdate);
    }

    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        Entity entity = event.getEntity();
        LightningBolt lightningBolt = event.getLightning();
        if (!event.isCanceled() && ThunderstruckModifier.lightningTracking(entity, lightningBolt)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    static void hurt(LivingHurtEvent event) {
        var entity = event.getEntity();
        AtomicReference<Float> amount = new AtomicReference<>(event.getAmount());
        VoidTouched.get(entity).ifPresent(voidTouched -> {
            if (voidTouched.isVoided()) {
                float multiplier = ((voidTouched.getAmplifier()) * 0.05f);
                amount.updateAndGet(v -> v + (v * multiplier));
            }
        });
        if (amount.get() != event.getAmount()) {
            event.setAmount(amount.get());
        }
    }

    @SubscribeEvent
    void registerSerializers(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.RECIPE_SERIALIZER) {
            ModifierModule.LOADER.register(TCompat.getResource("autosmelt"), AutosmeltModule.LOADER);
        }
    }
}
