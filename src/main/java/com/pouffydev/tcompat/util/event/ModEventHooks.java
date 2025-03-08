package com.pouffydev.tcompat.util.event;

import com.pouffydev.tcompat.util.explosion.ExplosionKnockbackEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;

public class ModEventHooks {

    public static Vec3 getExplosionKnockback(Level level, Explosion explosion, Entity entity, Vec3 initialVelocity) {
        ExplosionKnockbackEvent event = new ExplosionKnockbackEvent(level, explosion, entity, initialVelocity);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getKnockbackVelocity();
    }
}
