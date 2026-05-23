package io.github.pouffy.tcompat.compat.malum.modifier.ranged;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.modifier.base.MalumStaffModifier;
import io.github.pouffy.tcompat.common.modifier.module.OptionalAttributeModule;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.ObjectRetriever;
import io.github.pouffy.tcompat.compat.malum.MalumHandler;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class MnemonicModifier extends MalumStaffModifier {

    public MnemonicModifier() {
        super(15);
    }

    @Override
    public void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addModule(OptionalAttributeModule.builder(TCompat.getResource("lodestone:magic_damage"), AttributeModifier.Operation.ADDITION).slots(EquipmentSlot.MAINHAND).flat(5));
    }

    @Override
    public void spawnChargeParticles(Level level, LivingEntity entity, Vec3 pos, IToolStackView tool, float chargePercentage) {
        if (!CompatHelper.isLoaded("malum")) return;
        RandomSource random = level.random;
        MalumHandler.mnemonicParticles(entity, random, pos, chargePercentage);
    }

    @Override
    public int getCooldownDuration(Level level, LivingEntity entity) {
        return 80;
    }

    @Override
    public int getProjectileCount(Level level, LivingEntity entity, float chargePercentage) {
        return chargePercentage == 1.0F ? 3 : 0;
    }

    @Override
    public void fireProjectile(LivingEntity entity, IToolStackView tool, Level level, InteractionHand hand, float chargePercentage, int count) {
        if (!CompatHelper.isLoaded("malum")) return;
        float pitchOffset = 3.0F + (float)count;
        int spawnDelay = count * 3;
        float velocity = 3.0F + 0.5F * (float)count;
        double magicDamage = ObjectRetriever.getAttribute("lodestone:magic_damage").map(attr -> entity.getAttributes().getValue(attr)).orElse(0.0D);
        Vec3 pos = this.getProjectileSpawnPos(entity, hand, 0.5F, 0.5F);
        MalumHandler.createHexBolt(entity, pos, pitchOffset, spawnDelay, velocity, (float) magicDamage);
    }
}
