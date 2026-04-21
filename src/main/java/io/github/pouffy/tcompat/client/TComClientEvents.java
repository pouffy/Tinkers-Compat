package io.github.pouffy.tcompat.client;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.client.render.FrozenStateRenderer;
import io.github.pouffy.tcompat.common.capability.frozen.Frozen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TCompat.MOD_ID, value = Dist.CLIENT)
public class TComClientEvents {

    @SubscribeEvent
    public static void onPostRenderLiving(RenderLivingEvent.Post event) {
        new FrozenStateRenderer(Frozen::getFrozenTicks, Frozen::isFrozen).render(event.getEntity(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
    }
}
