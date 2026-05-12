package io.github.pouffy.tcompat.client;

import io.github.pouffy.tcompat.TCompat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TCompat.MOD_ID, value = Dist.CLIENT)
public class TComClientEvents {

    @SubscribeEvent
    public static void onPostRenderLiving(RenderLivingEvent.Post event) {}
}
