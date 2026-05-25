package io.github.pouffy.tcompat.compat.malum;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import com.sammy.malum.common.enchantment.staff.ReplenishingEnchantment;
import com.sammy.malum.common.entity.bolt.AbstractBoltProjectileEntity;
import com.sammy.malum.common.entity.bolt.AuricFlameBoltEntity;
import com.sammy.malum.common.entity.bolt.DrainingBoltEntity;
import com.sammy.malum.common.entity.bolt.HexBoltEntity;
import com.sammy.malum.common.entity.nitrate.EthericNitrateEntity;
import com.sammy.malum.common.spiritrite.PotionRiteEffect;
import com.sammy.malum.common.spiritrite.TotemicRiteEffect;
import com.sammy.malum.core.handlers.SoulDataHandler;
import com.sammy.malum.core.helpers.ParticleHelper;
import com.sammy.malum.registry.client.ParticleRegistry;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.curios.CuriosHandler;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.helpers.CurioHelper;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.helpers.SoundHelper;
import team.lodestar.lodestone.registry.common.tag.LodestoneDamageTypeTags;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.DirectionalBehaviorComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem.canSweep;
import static io.github.pouffy.tcompat.compat.malum.modifier.ranged.ErosionModifier.MALIGNANT_BLACK;

public class MalumHandler {

    public static float weightOfWorlds(float damage, LivingEntity attacker, Entity target) {
        if (!CompatHelper.isLoaded("malum")) return damage;
        float returning = damage;
        Level level = attacker.level();
        var optionalEffect = ObjectRetriever.getEffect("malum:grim_certainty");
        if (optionalEffect.isPresent() && !level.isClientSide()) {
            ParticleEffectType particleEffectType = ParticleEffectTypeRegistry.SCYTHE_SLASH;
            var effect = optionalEffect.get();
            if (attacker.hasEffect(effect) || level.random.nextFloat() < 0.25F) {
                returning *= 2.0F;
                SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 2.0F, 0.75F);
                SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 3.0F, 1.25F);
                SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 3.0F, 1.75F);
                particleEffectType = ParticleEffectTypeRegistry.WEIGHT_OF_WORLDS_CRIT;
                attacker.removeEffect(effect);
            }
            ParticleHelper.createSlashingEffect(particleEffectType).setVertical().spawnForwardSlashingParticle(attacker);
            SoundHelper.playSound(target, SoundRegistry.WEIGHT_OF_WORLDS_CUT.get(), 2.0F, 0.75F);
        }
        return returning;
    }

    public static boolean isCloaking(LivingEntity entity) {
        if (!CompatHelper.isLoaded("malum")) return false;
        EquipmentContext context = new EquipmentContext(entity);
        for (EquipmentSlot slotType : EquipmentSlot.values()) {
            if (ModifierUtil.validArmorSlot(entity, slotType)) {
                IToolStackView toolStack = context.getToolInSlot(slotType);
                if (toolStack != null && !toolStack.isBroken()) {
                    return toolStack.getModifier(TCModifiers.cloaking) != ModifierEntry.EMPTY;
                }
            }
        }
        if (context.getValidTool(EquipmentSlot.MAINHAND) != null) {
            IToolStackView toolStack = context.getToolInSlot(EquipmentSlot.MAINHAND);
            if (toolStack != null && !toolStack.isBroken()) {
                return toolStack.getModifier(TCModifiers.cloaking) != ModifierEntry.EMPTY;
            }
        }
        if (context.getValidTool(EquipmentSlot.OFFHAND) != null) {
            IToolStackView toolStack = context.getToolInSlot(EquipmentSlot.OFFHAND);
            if (toolStack != null && !toolStack.isBroken()) {
                return toolStack.getModifier(TCModifiers.cloaking) != ModifierEntry.EMPTY;
            }
        }
        return false;
    }

    public static void exposeSoul(LivingHurtEvent event) {
        if (!CompatHelper.isLoaded("malum")) return;
        if (!event.isCanceled() && !(event.getAmount() <= 0.0F)) {
            LivingEntity target = event.getEntity();
            DamageSource source = event.getSource();
            Entity attacker = source.getEntity();
            if (attacker instanceof LivingEntity living) {
                ItemStack stack = living.getMainHandItem();
                CompatHelper.asTool(stack, (tool) -> {
                    List<ModifierEntry> validList = new ArrayList<>();
                    for (ModifierEntry entry : tool.getModifierList()) {
                        if (entry.getModifier().getHooks().hasHook(GlobalInit.SOUL_EXPOSURE)) {
                            validList.add(entry);
                        }
                    }
                    List<ModifierEntry> list = validList.stream().sorted(Comparator.comparingInt(entry -> -entry.getModifier().getPriority())).toList();
                    boolean shouldWork = !list.isEmpty() && list.stream().findFirst().map(entry -> entry.getHook(GlobalInit.SOUL_EXPOSURE).canUse(tool, entry)).orElse(false);
                    if (shouldWork) {
                        SoulDataHandler.exposeSoul(target);
                    }
                });
            }
        }
    }

    public static void deliverance(LivingEntity target, LivingEntity attacker) {
        SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 2.0F, 1.25F);
        SoundHelper.playSound(target, SoundRegistry.MALIGNANT_METAL_MOTIF.get(), 3.0F, 1.75F);
        ParticleHelper.SlashParticleEffectBuilder particle = ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.EDGE_OF_DELIVERANCE_CRIT);
        if (!canSweep(attacker)) {
            particle.setVertical();
        }

        particle.spawnTargetBoundSlashingParticle(attacker, target);
    }

    public static void staffReserve(Player player, ModifierEntry modifierEntry, int cooldown) {
        if (!CompatHelper.isLoaded("malum")) return;
        MalumPlayerDataCapability capability = MalumPlayerDataCapability.getCapability(player);
        if (capability.reserveStaffChargeHandler.chargeCount > 0) {
            --capability.reserveStaffChargeHandler.chargeCount;
        } else {
            TCompat.COOLDOWN_HANDLER.addCooldown(player, modifierEntry.getId(), cooldown);
        }
    }

    public static Projectile createHexBolt(LivingEntity shooter, Vec3 pos, float pitchOffset, int spawnDelay, float velocity, float magicDamage) {
        if (!CompatHelper.isLoaded("malum")) return null;
        HexBoltEntity entity = new HexBoltEntity(shooter.level(), pos.x, pos.y, pos.z);
        entity.setData(shooter, magicDamage, spawnDelay);
        entity.setItem(shooter.getUseItem());
        entity.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), -pitchOffset, velocity, 0.0F);
        return entity;
    }

    public static Projectile createErosion(LivingEntity shooter, Vec3 pos, float pitchOffset, int spawnDelay, float velocity, float magicDamage) {
        if (!CompatHelper.isLoaded("malum")) return null;
        Level level = shooter.level();
        DrainingBoltEntity entity = new DrainingBoltEntity(level, pos.x, pos.y, pos.z);
        entity.setData(shooter, magicDamage, spawnDelay);
        entity.setItem(shooter.getUseItem());
        entity.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), -pitchOffset, velocity, 0.0F);
        Vec3 projectileDirection = entity.getDeltaMovement();
        float yRot = (float)(Mth.atan2(projectileDirection.x, projectileDirection.z) * (double)(180F / (float)Math.PI));
        float yaw = (float)Math.toRadians(yRot);
        Vec3 left = new Vec3(-Math.cos(yaw), 0.0F, Math.sin(yaw));
        Vec3 up = left.cross(projectileDirection);
        entity.setDeltaMovement(entity.getDeltaMovement().add(left.scale(RandomHelper.randomBetween(level.random, -0.125F, 0.125F))).add(up.scale(RandomHelper.randomBetween(level.random, -0.025F, 0.025F))));
        return entity;
    }

    public static Projectile createAuricFlame(LivingEntity shooter, Vec3 pos, float pitchOffset, int spawnDelay, float velocity, float magicDamage, float spread) {
        if (!CompatHelper.isLoaded("malum")) return null;
        Level level = shooter.level();
        AuricFlameBoltEntity entity = new AuricFlameBoltEntity(level, pos.x, pos.y, pos.z);
        entity.setData(shooter, magicDamage, spawnDelay);
        entity.setItem(shooter.getUseItem());
        entity.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), -pitchOffset, velocity, 0.0F);
        Vec3 projectileDirection = entity.getDeltaMovement();
        float yRot = (float)(Mth.atan2(projectileDirection.x, projectileDirection.z) * (double)(180F / (float)Math.PI));
        float yaw = (float)Math.toRadians(yRot);
        Vec3 left = new Vec3(-Math.cos(yaw), 0.0F, Math.sin(yaw));
        entity.setDeltaMovement(entity.getDeltaMovement().add(left.scale(spread)));
        return entity;
    }

    public static void staffHurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (!CompatHelper.isLoaded("malum")) return;
        if (attacker instanceof Player player) {
            if (!(event.getSource().getDirectEntity() instanceof AbstractBoltProjectileEntity)) {
                Level level = player.level();
                spawnStaffSweepParticles(player, ParticleRegistry.STAFF_SLAM_PARTICLE.get());
                level.playSound(null, target.blockPosition(), SoundRegistry.STAFF_STRIKES.get(), attacker.getSoundSource(), 0.75F, Mth.nextFloat(level.random, 0.5F, 1.0F));
                if (event.getSource().is(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC)) {
                    ReplenishingEnchantment.replenishStaffCooldown(attacker, stack);
                }
            }
        }
    }

    public static void spawnStaffSweepParticles(Player player, SimpleParticleType type) {
        if (!CompatHelper.isLoaded("malum")) return;
        double d0 = -Mth.sin(player.getYRot() * ((float)Math.PI / 180F));
        double d1 = Mth.cos(player.getYRot() * ((float)Math.PI / 180F));
        Level var7 = player.level();
        if (var7 instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(type, player.getX() + d0, player.getY(0.5F), player.getZ() + d1, 0, d0, 0.0F, d1, 0.0F);
        }
    }

    public static void mnemonicParticles(LivingEntity shooter, RandomSource random, Vec3 pos, float chargePercentage) {
        if (!CompatHelper.isLoaded("malum")) return;
        SpinParticleData spinData = SpinParticleData.createRandomDirection(random, 0.25F, 0.5F).setSpinOffset(RandomHelper.randomBetween(random, 0.0F, 6.28F)).build();
        WorldParticleBuilder.create(ParticleRegistry.HEXAGON, new DirectionalBehaviorComponent(shooter.getLookAngle().normalize())).setRenderTarget(RenderHandler.LATE_DELAYED_RENDER).setTransparencyData(GenericParticleData.create(0.6F * chargePercentage, 0.0F).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build()).setSpinData(spinData).setScaleData(GenericParticleData.create(0.3F * chargePercentage, 0.0F).setEasing(Easing.SINE_IN_OUT).build()).setColorData(SpiritTypeRegistry.WICKED_SPIRIT.createColorData().build()).setLifetime(5).setLifeDelay(2).setMotion(shooter.getLookAngle().normalize().scale(0.05F)).enableNoClip().enableForcedSpawn().spawn(shooter.level(), pos.x, pos.y, pos.z).setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT).spawn(shooter.level(), pos.x, pos.y, pos.z);
    }

    public static void erosionParticles(LivingEntity shooter, RandomSource random, Vec3 pos, float chargePercentage) {
        if (!CompatHelper.isLoaded("malum")) return;
        SpinParticleData spinData = SpinParticleData.createRandomDirection(random, 0.25F, 0.5F).setSpinOffset(RandomHelper.randomBetween(random, 0.0F, 6.28F)).build();
        WorldParticleBuilder.create(ParticleRegistry.CIRCLE, new DirectionalBehaviorComponent(shooter.getLookAngle().normalize())).setRenderTarget(RenderHandler.LATE_DELAYED_RENDER).setTransparencyData(GenericParticleData.create(0.8F * chargePercentage, 0.0F).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build()).setSpinData(spinData).setScaleData(GenericParticleData.create(0.3F * chargePercentage, 0.0F).setEasing(Easing.SINE_IN_OUT).build()).setColorData(ColorParticleData.create(MALIGNANT_BLACK, MALIGNANT_BLACK).build()).setLifetime(5).setLifeDelay(2).setMotion(shooter.getLookAngle().normalize().scale(0.05F)).enableNoClip().enableForcedSpawn().setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT).spawn(shooter.level(), pos.x, pos.y, pos.z);
    }

    public static void auricFlameParticles(LivingEntity shooter, RandomSource random, Vec3 pos, float chargePercentage) {
        if (!CompatHelper.isLoaded("malum")) return;
        SpinParticleData spinData = SpinParticleData.createRandomDirection(random, 0.25F, 0.5F).setSpinOffset(RandomHelper.randomBetween(random, 0.0F, 6.28F)).build();
        WorldParticleBuilder.create(ParticleRegistry.HEXAGON, new DirectionalBehaviorComponent(shooter.getLookAngle().normalize())).setRenderTarget(RenderHandler.LATE_DELAYED_RENDER).setTransparencyData(GenericParticleData.create(0.5F * chargePercentage, 0.0F).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build()).setScaleData(GenericParticleData.create(0.35F * chargePercentage, 0.0F).setEasing(Easing.SINE_IN_OUT).build()).setSpinData(spinData).setColorData(EthericNitrateEntity.AURIC_COLOR_DATA).setLifetime(5).setMotion(shooter.getLookAngle().normalize().scale((double)0.05F)).enableNoClip().enableForcedSpawn().setLifeDelay(2).spawn(shooter.level(), pos.x, pos.y, pos.z).setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT).spawn(shooter.level(), pos.x, pos.y, pos.z);
    }

    public static Pair<Supplier<MobEffect>, Predicate<LivingEntity>> getRiteEffect(String name, boolean corrupted) {
        var riteType = name.equals("aerial") ? SpiritRiteRegistry.AERIAL_RITE : name.equals("aqueous") ? SpiritRiteRegistry.AQUEOUS_RITE : name.equals("earthen") ? SpiritRiteRegistry.EARTHEN_RITE : SpiritRiteRegistry.INFERNAL_RITE;
        TotemicRiteEffect riteEffect = riteType.getRiteEffect(corrupted);
        Supplier<MobEffect> mobEffectSupplier;
        Predicate<LivingEntity> entityPredicate;
        if (riteEffect instanceof PotionRiteEffect potionRiteEffect) {
            mobEffectSupplier = potionRiteEffect.mobEffectSupplier;
            entityPredicate = potionRiteEffect.getEntityPredicate();
        } else {
            throw new IllegalArgumentException("Supplied rite type must have an aura effect");
        }
        return Pair.of(mobEffectSupplier, entityPredicate);
    }

    public static void idleRestoration(LivingEvent.LivingTickEvent event) {
        if (!CompatHelper.isLoaded("malum")) return;
        LivingEntity entity = event.getEntity();
        boolean idleRestoration = false;
        for (var stack : CompatHelper.getModifiableStacks(entity)) {
            var tool = ToolStack.from(stack);
            if (tool.isBroken()) continue;
            if (tool.getModifiers().getEntry(TCModifiers.runeOfIdleRestoration) != ModifierEntry.EMPTY) {
                idleRestoration = true;
                break;
            }
        }
        // No point running if the player has the actual rune
        if (CuriosHandler.getCurioItems(entity).stream().map(ItemStack::getItem).toList().contains(ItemRegistry.RUNE_OF_IDLE_RESTORATION.get())) idleRestoration = false;
        if (idleRestoration) {
            if (entity.level().getGameTime() % 40L == 0L) {
                entity.heal(1.0F);
            }
        }
    }
    public static void volatileDistortion(LivingHurtEvent event, LivingEntity attacker) {
        if (!CompatHelper.isLoaded("malum")) return;
        boolean volatileDistortion = false;
        for (var stack : CompatHelper.getModifiableStacks(attacker)) {
            var tool = ToolStack.from(stack);
            if (tool.isBroken()) continue;
            if (tool.getModifiers().getEntry(TCModifiers.runeOfVolatileDistortion) != ModifierEntry.EMPTY) {
                volatileDistortion = true;
                break;
            }
        }
        // No point running if the player has the actual rune
        if (CuriosHandler.getCurioItems(attacker).stream().map(ItemStack::getItem).toList().contains(ItemRegistry.RUNE_OF_VOLATILE_DISTORTION.get())) volatileDistortion = false;
        if (volatileDistortion) {
            RandomSource random = attacker.getRandom();
            float multiplier = Mth.nextFloat(random, 0.9F, 1.2F);
            if (random.nextFloat() < 0.1F) {
                multiplier *= 2.0F;
            }

            event.setAmount(event.getAmount() * multiplier);
        }
    }

    public static void reactiveShielding(LivingHurtEvent event, LivingEntity attacker) {
        if (!CompatHelper.isLoaded("malum")) return;
        boolean reactiveShielding = false;
        for (var stack : CompatHelper.getModifiableStacks(attacker)) {
            var tool = ToolStack.from(stack);
            if (tool.isBroken()) continue;
            if (tool.getModifiers().getEntry(TCModifiers.runeOfReactiveShielding) != ModifierEntry.EMPTY) {
                reactiveShielding = true;
                break;
            }
        }
        // No point running if the player has the actual rune
        if (CuriosHandler.getCurioItems(attacker).stream().map(ItemStack::getItem).toList().contains(ItemRegistry.RUNE_OF_REACTIVE_SHIELDING.get())) reactiveShielding = false;
        if (reactiveShielding) {
            MobEffect shielding = MobEffectRegistry.REACTIVE_SHIELDING.get();
            MobEffectInstance effect = event.getEntity().getEffect(shielding);
            if (effect == null) {
                if (event.getEntity().level().random.nextFloat() < 0.5F) {
                    event.getEntity().addEffect(new MobEffectInstance(shielding, 80, 0, true, true, true));
                }
            } else {
                if (event.getEntity().level().random.nextFloat() < 0.5F) {
                    EntityHelper.amplifyEffect(effect, event.getEntity(), 1, 3);
                }
                EntityHelper.extendEffect(effect, event.getEntity(), 40, 100);
            }
        }
    }

    public static void ailmentCleansing(MobEffectEvent.Added event) {
        if (!CompatHelper.isLoaded("malum")) return;
        LivingEntity entity = event.getEntity();
        boolean ailmentCleansing = false;
        for (var stack : CompatHelper.getModifiableStacks(entity)) {
            var tool = ToolStack.from(stack);
            if (tool.isBroken()) continue;
            if (tool.getModifiers().getEntry(TCModifiers.runeOfAlimentCleansing) != ModifierEntry.EMPTY) {
                ailmentCleansing = true;
                break;
            }
        }
        // No point running if the player has the actual rune
        if (CuriosHandler.getCurioItems(entity).stream().map(ItemStack::getItem).toList().contains(ItemRegistry.RUNE_OF_ALIMENT_CLEANSING.get())) ailmentCleansing = false;
        if (ailmentCleansing) {
            if (event.getOldEffectInstance() == null && CurioHelper.hasCurioEquipped(entity, (Item)ItemRegistry.RUNE_OF_ALIMENT_CLEANSING.get())) {
                MobEffectInstance effect = event.getEffectInstance();
                MobEffect type = effect.getEffect();
                if (type.getCategory().equals(MobEffectCategory.HARMFUL)) {
                    EntityHelper.shortenEffect(effect, entity, effect.getDuration() / 4);
                }
            }
        }
    }

    public static void fervor(PlayerEvent.BreakSpeed event) {
        if (!CompatHelper.isLoaded("malum")) return;
        LivingEntity entity = event.getEntity();
        boolean fervor = false;
        for (var stack : CompatHelper.getModifiableStacks(entity)) {
            var tool = ToolStack.from(stack);
            if (tool.isBroken()) continue;
            if (tool.getModifiers().getEntry(TCModifiers.runeOfFervor) != ModifierEntry.EMPTY) {
                fervor = true;
                break;
            }
        }
        // No point running if the player has the actual rune
        if (CuriosHandler.getCurioItems(entity).stream().map(ItemStack::getItem).toList().contains(ItemRegistry.RUNE_OF_FERVOR.get())) fervor = false;
        if (fervor) {
            event.setNewSpeed(event.getOriginalSpeed() * 1.25F);
        }
    }
}
