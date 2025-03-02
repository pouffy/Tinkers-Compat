package com.pouffydev.tcompat.modifier.aether;

import com.pouffydev.tcompat.client.Color;
import com.pouffydev.tcompat.modifier.TComModifiers;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrbitModifier extends NoLevelsModifier implements InventoryTickModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK);
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        float coverage = calcArmorCoverage(holder);
        if (!holder.isCrouching())
            orbitItems(coverage * 8, holder, world);
        else itemsInOrbit.clear();
    }

    private final Set<ItemEntity> itemsInOrbit = new HashSet<>();


    private void orbitItems(float range, LivingEntity holder, Level level) {
        if (range == 0) return;
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, holder.getBoundingBox().inflate(range));
        if (items.isEmpty()) return;
        long gameTime = level.getGameTime();
        float shrinkFactor = 1.0f - (gameTime % 30) / 30f;
        for (int i = 0; i < items.size(); i++) {
            ItemEntity item = items.get(i);
            Vec3 rotatingPos = rotatingRadialOffset(holder.position(), 1.8f, 1.8f, i, items.size(), gameTime, 200);
            Color color = new Color(218, 103, 208);
            ParticleOptions particle = new DustParticleOptions(color.asVectorF(), 0.5f);
            if (itemsInOrbit.contains(item)) {
                rotatingPos = new Vec3(rotatingPos.x * shrinkFactor, rotatingPos.y, rotatingPos.z * shrinkFactor);
                level.addParticle(particle, rotatingPos.x, rotatingPos.y, rotatingPos.z, 0, 0, 0);
                item.setPos(rotatingPos);
            } else {
                Vec3 currentPos = item.position();
                float progress = (gameTime % 60) / 60f;
                Vec3 eased = lerp(currentPos, rotatingPos, easeInOut(progress));

                level.addParticle(particle, eased.x, eased.y, eased.z, 0, 0, 0);
                item.setPos(eased);

                if (eased.distanceTo(rotatingPos) < 0.1) {
                    itemsInOrbit.add(item);
                }
            }



        }

    }

    public static float easeInOut(float t) {
        return t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t;
    }

    public static Vec3 lerp(Vec3 start, Vec3 end, float t) {
        // Ensure t is clamped between 0 and 1
        t = Math.min(1, Math.max(0, t));

        // Calculate the interpolated position
        double x = start.x + (end.x - start.x) * t;
        double y = start.y + (end.y - start.y) * t;
        double z = start.z + (end.z - start.z) * t;

        return new Vec3(x, y, z);
    }

    private Vec3 rotatingRadialOffset(Vec3 pos, float distanceX, float distanceZ, float current, float total, float gameTime, float timePerRotation) {
        double angle = current / total * (Math.PI * 2);
        angle += ((gameTime % timePerRotation) / timePerRotation) * (Math.PI * 2);
        double dx2 = (distanceX * Math.cos(angle));
        double dz2 = (distanceZ * Math.sin(angle));

        Vec3 vector2f = new Vec3(dx2, 0, dz2);
        double x = vector2f.x * distanceX;
        double z = vector2f.z * distanceZ;
        return pos.add(x, 0, z);
    }

    private float calcArmorCoverage(LivingEntity holder) {
        float coverage = 0.0f;
        for (ItemStack stack : holder.getArmorSlots()) {
            ToolStack tool = ToolStack.copyFrom(stack);
            if (tool.getModifier(TComModifiers.orbit.getId()) != ModifierEntry.EMPTY) {
                coverage += 0.25f;
            }
        }
        return coverage;
    }
}
