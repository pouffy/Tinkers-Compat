package io.github.pouffy.tcompat.client.compat;

import com.github.L_Ender.cataclysm.init.ModKeybind;
import io.github.pouffy.tcompat.common.cooldown.ClientModifierCooldowns;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.common.network.GazeOfHeatPacket;
import io.github.pouffy.tcompat.common.network.TCompatNetworking;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.EquipmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.common.TinkerTags;

@OnlyIn(Dist.CLIENT)
public class CataclysmClientHandler {

    public static void gazeOfHeat(LivingEvent.LivingTickEvent event) {
        if (!CompatHelper.isLoaded("cataclysm")) return;
        boolean canUse = EquipmentHelper.hasModifier(event.getEntity(), TCModifiers.ignitium, (stack) -> stack.is(TinkerTags.Items.HELMETS)) && !ClientModifierCooldowns.getCooldowns().isOnCooldown(TCModifiers.ignitium);
        if (canUse) {
            LoadedOnly.gazeOfHeat(event);
        }
    }

    public static class LoadedOnly {
        public static void gazeOfHeat(LivingEvent.LivingTickEvent event) {
            if (ModKeybind.HELMET_KEY_ABILITY.consumeClick()) {
                TCompatNetworking.INSTANCE.sendToServer(new GazeOfHeatPacket());
            }
        }
    }
}
