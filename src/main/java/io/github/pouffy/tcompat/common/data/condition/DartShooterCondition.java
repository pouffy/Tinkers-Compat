package io.github.pouffy.tcompat.common.data.condition;

import com.google.gson.JsonObject;
import io.github.pouffy.tcompat.config.TCompatConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class DartShooterCondition implements ICondition {
    private static final ResourceLocation NAME = getResource("force_enabled_dart_shooters");

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext iContext) {
        return TCompatConfig.SERVER.forceEnableDartShooters.get();
    }

    public static class Serializer implements IConditionSerializer<DartShooterCondition> {
        public static final Serializer INSTANCE = new Serializer();

        public Serializer() {
        }

        public void write(JsonObject json, DartShooterCondition value) {

        }

        public DartShooterCondition read(JsonObject json) {
            return new DartShooterCondition();
        }

        public ResourceLocation getID() {
            return DartShooterCondition.NAME;
        }
    }
}
