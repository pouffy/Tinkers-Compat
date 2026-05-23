package io.github.pouffy.tcompat.client;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.common.cooldown.ClientModifierCooldowns;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import slimeknights.mantle.registration.object.FlowingFluidObject;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TCompat.MOD_ID, value = Dist.CLIENT)
public class TComClientEvents {

    @SubscribeEvent
    public static void onPostRenderLiving(RenderLivingEvent.Post event) {}

    @SubscribeEvent
    static void clientSetup(FMLClientSetupEvent event) {
        setTranslucent(TCFluids.moltenZanite);
        setTranslucent(TCFluids.moltenSkyjade);
        setTranslucent(TCFluids.fireBlood);
        setTranslucent(TCFluids.iceBlood);
        setTranslucent(TCFluids.lightningBlood);
        setTranslucent(TCFluids.moltenLacrima);
        setTranslucent(TCFluids.moltenBlazingQuartz);
        setTranslucent(TCFluids.runicSap);
        setTranslucent(TCFluids.cursedSap);
    }

    private static void setTranslucent(FlowingFluidObject<?> fluid) {
        ItemBlockRenderTypes.setRenderLayer(fluid.getStill(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(fluid.getFlowing(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        ClientModifierCooldowns.getCooldowns().tick(1);
    }
}
