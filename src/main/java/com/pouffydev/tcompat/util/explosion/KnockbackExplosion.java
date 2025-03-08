package com.pouffydev.tcompat.util.explosion;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.pouffydev.tcompat.util.event.ModEventHooks;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.*;

public class KnockbackExplosion extends Explosion{
    private static final int MAX_DROPS_PER_COMBINED_STACK = 16;
    private final boolean fire;
    private final Explosion.BlockInteraction blockInteraction;
    private final RandomSource random;
    private final Level level;
    private final double x;
    private final double y;
    private final double z;
    @Nullable
    private final Entity source;
    private final float radius;
    private final DamageSource damageSource;
    private final SimpleExplosionDamageCalculator damageCalculator;
    private final ParticleOptions smallExplosionParticles;
    private final ParticleOptions largeExplosionParticles;
    private final SoundEvent explosionSound;
    private final ObjectArrayList<BlockPos> toBlow;
    private final Map<Player, Vec3> hitPlayers;

    public static DamageSource getDefaultDamageSource(Level level, @Nullable Entity source) {
        return level.damageSources().explosion(source, getIndirectSourceEntityInternal(source));
    }

    public KnockbackExplosion(Level level, @Nullable Entity source, double x, double y, double z, float radius, List<BlockPos> toBlow, Explosion.BlockInteraction blockInteraction, ParticleOptions smallExplosionParticles, ParticleOptions largeExplosionParticles, SoundEvent explosionSound) {
        this(level, source, getDefaultDamageSource(level, source), (SimpleExplosionDamageCalculator)null, x, y, z, radius, false, blockInteraction, smallExplosionParticles, largeExplosionParticles, explosionSound);
        this.toBlow.addAll(toBlow);
    }

    public KnockbackExplosion(Level level, @Nullable Entity source, double x, double y, double z, float radius, boolean fire, Explosion.BlockInteraction blockInteraction, List<BlockPos> positions) {
        this(level, source, x, y, z, radius, fire, blockInteraction);
        this.toBlow.addAll(positions);
    }

    public KnockbackExplosion(Level level, @Nullable Entity source, double x, double y, double z, float radius, boolean fire, Explosion.BlockInteraction blockInteraction) {
        this(level, source, getDefaultDamageSource(level, source), (SimpleExplosionDamageCalculator)null, x, y, z, radius, fire, blockInteraction, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, SoundEvents.GENERIC_EXPLODE);
    }

    public KnockbackExplosion(Level level, @Nullable Entity source, @Nullable DamageSource damageSource, @Nullable SimpleExplosionDamageCalculator damageCalculator, double x, double y, double z, float radius, boolean fire, Explosion.BlockInteraction blockInteraction, ParticleOptions smallExplosionParticles, ParticleOptions largeExplosionParticles, SoundEvent explosionSound) {
        super(level, source, damageSource, damageCalculator, x, y, z, radius, fire, blockInteraction);
        this.random = RandomSource.create();
        this.toBlow = new ObjectArrayList();
        this.hitPlayers = Maps.newHashMap();
        this.level = level;
        this.source = source;
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.z = z;
        this.fire = fire;
        this.blockInteraction = blockInteraction;
        this.damageSource = damageSource == null ? level.damageSources().explosion(this) : damageSource;
        this.damageCalculator = damageCalculator == null ? this.makeDamageCalculator() : damageCalculator;
        this.smallExplosionParticles = smallExplosionParticles;
        this.largeExplosionParticles = largeExplosionParticles;
        this.explosionSound = explosionSound;
    }

    private SimpleExplosionDamageCalculator makeDamageCalculator() {
        return new SimpleExplosionDamageCalculator(false, false, Optional.of(1.0f), Optional.empty());
    }

    public static float getSeenPercent(Vec3 explosionVector, Entity entity) {
        AABB aabb = entity.getBoundingBox();
        double d0 = (double)1.0F / ((aabb.maxX - aabb.minX) * (double)2.0F + (double)1.0F);
        double d1 = (double)1.0F / ((aabb.maxY - aabb.minY) * (double)2.0F + (double)1.0F);
        double d2 = (double)1.0F / ((aabb.maxZ - aabb.minZ) * (double)2.0F + (double)1.0F);
        double d3 = ((double)1.0F - Math.floor((double)1.0F / d0) * d0) / (double)2.0F;
        double d4 = ((double)1.0F - Math.floor((double)1.0F / d2) * d2) / (double)2.0F;
        if (!(d0 < (double)0.0F) && !(d1 < (double)0.0F) && !(d2 < (double)0.0F)) {
            int i = 0;
            int j = 0;

            for(double d5 = (double)0.0F; d5 <= (double)1.0F; d5 += d0) {
                for(double d6 = (double)0.0F; d6 <= (double)1.0F; d6 += d1) {
                    for(double d7 = (double)0.0F; d7 <= (double)1.0F; d7 += d2) {
                        double d8 = Mth.lerp(d5, aabb.minX, aabb.maxX);
                        double d9 = Mth.lerp(d6, aabb.minY, aabb.maxY);
                        double d10 = Mth.lerp(d7, aabb.minZ, aabb.maxZ);
                        Vec3 vec3 = new Vec3(d8 + d3, d9, d10 + d4);
                        if (entity.level().clip(new ClipContext(vec3, explosionVector, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getType() == HitResult.Type.MISS) {
                            ++i;
                        }

                        ++j;
                    }
                }
            }

            return (float)i / (float)j;
        } else {
            return 0.0F;
        }
    }

    public float radius() {
        return this.radius;
    }

    public Vec3 center() {
        return new Vec3(this.x, this.y, this.z);
    }

    public void explode() {
        this.level.gameEvent(this.source, GameEvent.EXPLODE, new Vec3(this.x, this.y, this.z));
        Set<BlockPos> set = Sets.newHashSet();
        int i = 16;

        for(int j = 0; j < 16; ++j) {
            for(int k = 0; k < 16; ++k) {
                for(int l = 0; l < 16; ++l) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                        double d0 = (double)((float)j / 15.0F * 2.0F - 1.0F);
                        double d1 = (double)((float)k / 15.0F * 2.0F - 1.0F);
                        double d2 = (double)((float)l / 15.0F * 2.0F - 1.0F);
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f = this.radius * (0.7F + this.level.random.nextFloat() * 0.6F);
                        double d4 = this.x;
                        double d6 = this.y;
                        double d8 = this.z;

                        for(float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
                            BlockPos blockpos = BlockPos.containing(d4, d6, d8);
                            BlockState blockstate = this.level.getBlockState(blockpos);
                            FluidState fluidstate = this.level.getFluidState(blockpos);
                            if (!this.level.isInWorldBounds(blockpos)) {
                                break;
                            }

                            Optional<Float> optional = this.damageCalculator.getBlockExplosionResistance(this, this.level, blockpos, blockstate, fluidstate);
                            if (optional.isPresent()) {
                                f -= ((Float)optional.get() + 0.3F) * 0.3F;
                            }

                            if (f > 0.0F && this.damageCalculator.shouldBlockExplode(this, this.level, blockpos, blockstate, f)) {
                                set.add(blockpos);
                            }

                            d4 += d0 * (double)0.3F;
                            d6 += d1 * (double)0.3F;
                            d8 += d2 * (double)0.3F;
                        }
                    }
                }
            }
        }

        this.toBlow.addAll(set);
        float f2 = this.radius * 2.0F;
        int k1 = Mth.floor(this.x - (double)f2 - (double)1.0F);
        int l1 = Mth.floor(this.x + (double)f2 + (double)1.0F);
        int i2 = Mth.floor(this.y - (double)f2 - (double)1.0F);
        int i1 = Mth.floor(this.y + (double)f2 + (double)1.0F);
        int j2 = Mth.floor(this.z - (double)f2 - (double)1.0F);
        int j1 = Mth.floor(this.z + (double)f2 + (double)1.0F);
        List<Entity> list = this.level.getEntities(this.source, new AABB((double)k1, (double)i2, (double)j2, (double)l1, (double)i1, (double)j1));
        ForgeEventFactory.onExplosionDetonate(this.level, this, list, (double)f2);
        Vec3 vec3 = new Vec3(this.x, this.y, this.z);

        for(Entity entity : list) {
            if (!entity.ignoreExplosion()) {
                double d11 = Math.sqrt(entity.distanceToSqr(vec3)) / (double)f2;
                if (d11 <= (double)1.0F) {
                    double d5 = entity.getX() - this.x;
                    double d7 = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - this.y;
                    double d9 = entity.getZ() - this.z;
                    double d12 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
                    if (d12 != (double)0.0F) {
                        d5 /= d12;
                        d7 /= d12;
                        d9 /= d12;
                        if (this.damageCalculator.shouldDamageEntity(this, entity)) {
                            entity.hurt(this.damageSource, this.damageCalculator.getEntityDamageAmount(this, entity));
                        }

                        double d13 = ((double)1.0F - d11) * (double)getSeenPercent(vec3, entity) * (double)this.damageCalculator.getKnockbackMultiplier(entity);
                        double d10;
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingentity = (LivingEntity)entity;
                            d10 = d13 * ((double)1.0F - livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                        } else {
                            d10 = d13;
                        }

                        d5 *= d10;
                        d7 *= d10;
                        d9 *= d10;
                        Vec3 vec31 = new Vec3(d5, d7, d9);
                        if (entity != source) {
                            vec31 = ModEventHooks.getExplosionKnockback(this.level, this, entity, vec31);
                            entity.setDeltaMovement(entity.getDeltaMovement().add(vec31));
                            if (entity instanceof Player) {
                                Player player = (Player) entity;
                                if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)) {
                                    this.hitPlayers.put(player, vec31);
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public void finalizeExplosion(boolean spawnParticles) {
        if (this.level.isClientSide) {
            this.level.playLocalSound(this.x, this.y, this.z, this.explosionSound, SoundSource.BLOCKS, 4.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
        }

        boolean flag = this.interactsWithBlocks();
        if (spawnParticles) {
            ParticleOptions particleoptions;
            if (!(this.radius < 2.0F) && flag) {
                particleoptions = this.largeExplosionParticles;
            } else {
                particleoptions = this.smallExplosionParticles;
            }

            this.level.addParticle(particleoptions, this.x, this.y, this.z, (double)1.0F, (double)0.0F, (double)0.0F);
        }

        if (flag) {
            this.level.getProfiler().push("explosion_blocks");
            List<Pair<ItemStack, BlockPos>> list = new ArrayList();
            Util.shuffle(this.toBlow, this.level.random);

            for(Pair<ItemStack, BlockPos> pair : list) {
                net.minecraft.world.level.block.Block.popResource(this.level, (BlockPos)pair.getSecond(), (ItemStack)pair.getFirst());
            }

            this.level.getProfiler().pop();
        }

        if (this.fire) {
            ObjectListIterator var7 = this.toBlow.iterator();

            while(var7.hasNext()) {
                BlockPos blockpos1 = (BlockPos)var7.next();
                if (this.random.nextInt(3) == 0 && this.level.getBlockState(blockpos1).isAir() && this.level.getBlockState(blockpos1.below()).isSolidRender(this.level, blockpos1.below())) {
                    this.level.setBlockAndUpdate(blockpos1, BaseFireBlock.getState(this.level, blockpos1));
                }
            }
        }

    }

    private static void addOrAppendStack(List<Pair<ItemStack, BlockPos>> drops, ItemStack stack, BlockPos pos) {
        for(int i = 0; i < drops.size(); ++i) {
            Pair<ItemStack, BlockPos> pair = (Pair)drops.get(i);
            ItemStack itemstack = (ItemStack)pair.getFirst();
            if (ItemEntity.areMergable(itemstack, stack)) {
                drops.set(i, Pair.of(ItemEntity.merge(itemstack, stack, 16), (BlockPos)pair.getSecond()));
                if (stack.isEmpty()) {
                    return;
                }
            }
        }

        drops.add(Pair.of(stack, pos));
    }

    public boolean interactsWithBlocks() {
        return this.blockInteraction != Explosion.BlockInteraction.KEEP;
    }

    public Map<Player, Vec3> getHitPlayers() {
        return this.hitPlayers;
    }

    @Nullable
    private static LivingEntity getIndirectSourceEntityInternal(@Nullable Entity source) {
        if (source == null) {
            return null;
        } else if (source instanceof PrimedTnt) {
            PrimedTnt primedtnt = (PrimedTnt)source;
            return primedtnt.getOwner();
        } else if (source instanceof LivingEntity) {
            return (LivingEntity)source;
        } else {
            if (source instanceof Projectile) {
                Projectile projectile = (Projectile)source;
                Entity entity = projectile.getOwner();
                if (entity instanceof LivingEntity) {
                    return (LivingEntity)entity;
                }
            }

            return null;
        }
    }

    @Nullable
    public LivingEntity getIndirectSourceEntity() {
        return getIndirectSourceEntityInternal(this.source);
    }

    @Nullable
    public Entity getDirectSourceEntity() {
        return this.source;
    }

    public void clearToBlow() {
        this.toBlow.clear();
    }

    public List<BlockPos> getToBlow() {
        return this.toBlow;
    }

    public Explosion.BlockInteraction getBlockInteraction() {
        return this.blockInteraction;
    }

    public ParticleOptions getSmallExplosionParticles() {
        return this.smallExplosionParticles;
    }

    public ParticleOptions getLargeExplosionParticles() {
        return this.largeExplosionParticles;
    }

    public SoundEvent getExplosionSound() {
        return this.explosionSound;
    }
}
