package io.github.pouffy.tcompat.client;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.client.modifier.AmbrofusionModifierModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.client.modifiers.ModifierModelManager;

import static io.github.pouffy.tcompat.TCompat.getResource;

@Mod.EventBusSubscriber(modid = TCompat.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TComToolsClientEvents {
    @SubscribeEvent
    static void registerModifierModels(ModifierModelManager.ModifierModelRegistrationEvent event) {
        event.registerModel(getResource("ambrofusion"), AmbrofusionModifierModel.UNBAKED_INSTANCE);
    }
}
