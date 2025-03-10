package com.pouffydev.tcompat.client;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.client.modifier.AmbrofusionModifierModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.common.ClientEventBase;
import slimeknights.tconstruct.library.client.modifiers.ModifierModelManager;

import static com.pouffydev.tcompat.TCompat.getResource;


@Mod.EventBusSubscriber(modid = TCompat.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TComToolsClientEvents extends ClientEventBase {

    @SubscribeEvent
    static void registerModifierModels(ModifierModelManager.ModifierModelRegistrationEvent event) {
        event.registerModel(getResource("ambrofusion"), AmbrofusionModifierModel.UNBAKED_INSTANCE);
    }
}
