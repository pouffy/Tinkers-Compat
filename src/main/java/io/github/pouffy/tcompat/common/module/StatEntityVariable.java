package io.github.pouffy.tcompat.common.module;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.json.TinkerLoadables;
import slimeknights.tconstruct.library.json.variable.entity.EntityVariable;

public record StatEntityVariable(ResourceLocation stat) implements EntityVariable {
    public static final RecordLoadable<StatEntityVariable> LOADER = RecordLoadable.create(
            TinkerLoadables.CUSTOM_STAT.requiredField("stat", (e) -> e.stat), StatEntityVariable::new);

    @Override
    public float getValue(@NotNull LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer player) {
            return player.getStats().getValue(Stats.CUSTOM.get(this.stat));
        } else if (livingEntity instanceof LocalPlayer player) {
            return player.getStats().getValue(Stats.CUSTOM.get(this.stat));
        }
        return 0;
    }

    @Override
    public RecordLoadable<StatEntityVariable> getLoader() {
        return LOADER;
    }
}
