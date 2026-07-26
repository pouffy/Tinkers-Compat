package io.github.pouffy.tcompat.compat.aether.entity;

import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.aether.AetherHandler;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ScheduledProjectileTaskModifierHook;
import slimeknights.tconstruct.library.tools.IndestructibleItemEntity;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.capability.PersistentDataCapability;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.utils.Schedule;
import slimeknights.tconstruct.tools.entity.ToolProjectile;

import javax.annotation.Nullable;

public class ModifiableDart extends AbstractArrow implements ToolProjectile {
    protected static final EntityDataAccessor<ItemStack> STACK;
    protected static final EntityDataAccessor<Float> WATER_INERTIA;
    private ItemStack stack;
    private IToolStackView tool;
    private boolean reclaim;
    private boolean dealtDamage;
    private Schedule tasks;
    private CaptureDiscard captureDiscard;
    private static final String KEY_STACK = "stack";
    private static final String KEY_WATER_INERTIA = "water_inertia";
    private static final String KEY_DEALT_DAMAGE = "dealt_damage";
    private static final String KEY_TASKS = "tasks";

    public ModifiableDart(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
        this.stack = ItemStack.EMPTY;
        this.tool = null;
        this.reclaim = false;
        this.dealtDamage = false;
        this.tasks = Schedule.EMPTY;
        this.captureDiscard = CaptureDiscard.NOT_CAPTURING;
    }

    public ModifiableDart(Level level, double pX, double pY, double pZ) {
        super(GlobalInit.modifiableDart.get(), pX, pY, pZ, level);
        this.stack = ItemStack.EMPTY;
        this.tool = null;
        this.reclaim = false;
        this.dealtDamage = false;
        this.tasks = Schedule.EMPTY;
        this.captureDiscard = CaptureDiscard.NOT_CAPTURING;
    }

    public ModifiableDart(Level level, LivingEntity shooter) {
        super(GlobalInit.modifiableDart.get(), shooter, level);
        this.stack = ItemStack.EMPTY;
        this.tool = null;
        this.reclaim = false;
        this.dealtDamage = false;
        this.tasks = Schedule.EMPTY;
        this.captureDiscard = CaptureDiscard.NOT_CAPTURING;
    }

    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return AetherHandler.dartHitSound();
    }

    public ItemStack getPickupItem() {
        return this.stack.copy();
    }

    private void setStack(ItemStack stack) {
        this.stack = stack;
        this.entityData.set(STACK, stack);
        this.reclaim = ModifierUtil.checkVolatileFlag(stack, IndestructibleItemEntity.INDESTRUCTIBLE_ENTITY);
    }

    private IToolStackView getTool() {
        if (this.tool == null) {
            this.tool = ToolStack.from(this.stack);
        }

        return this.tool;
    }

    public IToolStackView onCreate(ItemStack stack, @Nullable LivingEntity shooter) {
        stack = stack.copyWithCount(1);
        this.setStack(stack);
        IToolStackView tool = this.getTool();
        EntityModifierCapability.getCapability(this).addModifiers(tool.getModifiers());
        this.setBaseDamage((double) ConditionalStatModifierHook.getModifiedStat(tool, shooter, ToolStats.PROJECTILE_DAMAGE));
        this.entityData.set(WATER_INERTIA, ConditionalStatModifierHook.getModifiedStat(tool, shooter, ToolStats.WATER_INERTIA));
        return tool;
    }

    public void shoot(double pX, double pY, double pZ, float velocity, float inaccuracy) {
        if (!this.stack.isEmpty()) {
            IToolStackView tool = this.getTool();
            LivingEntity shooter = ModifierUtil.asLiving(this.getOwner());
            velocity *= ConditionalStatModifierHook.getModifiedStat(tool, shooter, ToolStats.VELOCITY);
            inaccuracy *= ModifierUtil.getInaccuracy(tool, shooter);
            super.shoot(pX, pY, pZ, velocity, inaccuracy);
            ModDataNBT arrowData = PersistentDataCapability.getOrWarn(this);

            for(ModifierEntry entry : tool.getModifiers()) {
                entry.getHook(ModifierHooks.PROJECTILE_SHOT).onProjectileShoot(tool, entry, shooter, this.stack, this, this, arrowData, true);
            }

            this.tasks = ScheduledProjectileTaskModifierHook.createSchedule(tool, this.stack, this, this, arrowData);
        } else {
            super.shoot(pX, pY, pZ, velocity, inaccuracy);
        }

    }

    public void tick() {
        super.tick();
        if (!this.tasks.isEmpty() && !this.stack.isEmpty()) {
            ScheduledProjectileTaskModifierHook.checkSchedule(this.getTool(), this.stack, this, this, this.tasks);
        }

    }

    public void setKnockback(int knockback) {
        super.setKnockback(this.getKnockback() + knockback);
    }

    public void setPierceLevel(byte pierceLevel) {
        super.setPierceLevel((byte)(this.getPierceLevel() + pierceLevel));
    }

    public void tickDespawn() {
        if (this.pickup != Pickup.ALLOWED || !this.reclaim) {
            super.tickDespawn();
        }

    }

    protected void onHitEntity(EntityHitResult result) {
        if (this.reclaim) {
            this.captureDiscard = CaptureDiscard.CAPTURING;
        }

        super.onHitEntity(result);
        if (this.captureDiscard == CaptureDiscard.DISCARDED) {
            this.dealtDamage = true;
            this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        }

        this.captureDiscard = CaptureDiscard.NOT_CAPTURING;
    }

    public void remove(Entity.RemovalReason reason) {
        if (reason == RemovalReason.DISCARDED && this.captureDiscard != CaptureDiscard.NOT_CAPTURING && this.getDeltaMovement().lengthSqr() >= 1.0E-7) {
            this.captureDiscard = CaptureDiscard.DISCARDED;
        } else {
            super.remove(reason);
        }

    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return this.dealtDamage ? null : super.findHitEntity(pStartVec, pEndVec);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STACK, ItemStack.EMPTY);
        this.entityData.define(WATER_INERTIA, 0.6F);
    }

    public ItemStack getDisplayTool() {
        return (ItemStack)this.entityData.get(STACK);
    }

    public Component getDisplayName() {
        return this.getDisplayTool().getDisplayName();
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("stack", this.stack.save(new CompoundTag()));
        tag.putFloat("water_inertia", (Float)this.entityData.get(WATER_INERTIA));
        tag.putBoolean("dealt_damage", this.dealtDamage);
        if (!this.tasks.isEmpty()) {
            tag.put("tasks", this.tasks.serialize());
        }

    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("stack", 10)) {
            this.setStack(ItemStack.of(tag.getCompound("stack")));
        }

        this.entityData.set(WATER_INERTIA, tag.getFloat("water_inertia"));
        this.dealtDamage = tag.getBoolean("dealt_damage");
        if (tag.contains("tasks", 9)) {
            this.tasks = Schedule.deserialize(tag.getList("tasks", 10));
        }

    }

    static {
        STACK = SynchedEntityData.defineId(ModifiableDart.class, EntityDataSerializers.ITEM_STACK);
        WATER_INERTIA = SynchedEntityData.defineId(ModifiableDart.class, EntityDataSerializers.FLOAT);
    }

    private static enum CaptureDiscard {
        NOT_CAPTURING,
        CAPTURING,
        DISCARDED;

        private CaptureDiscard() {
        }
    }
}
