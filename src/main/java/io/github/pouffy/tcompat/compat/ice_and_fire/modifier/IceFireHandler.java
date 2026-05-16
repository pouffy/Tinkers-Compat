package io.github.pouffy.tcompat.compat.ice_and_fire.modifier;

import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class IceFireHandler {

    public static void freeze(Entity entity, int frozenTicks) {
        if (!CompatHelper.isLoaded("iceandfire")) return;
        if (entity instanceof LivingEntity living) {
            EntityDataProvider.getCapability(living).ifPresent(provider -> {
                provider.frozenData.setFrozen(living, frozenTicks);
            });
        }
    }
}
