package com.pouffydev.tcompat.fluids;

import com.pouffydev.tcompat.TCompat;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.tconstruct.TConstruct;

@Mod.EventBusSubscriber(modid = TCompat.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TComFluidClientEvents {
    @SubscribeEvent
    static void clientSetup(final FMLClientSetupEvent event) {
        setTranslucent(TComFluids.moltenZanite);
        setTranslucent(TComFluids.moltenSkyjade);
    }

    private static void setTranslucent(FlowingFluidObject<?> fluid) {
        ItemBlockRenderTypes.setRenderLayer(fluid.getStill(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(fluid.getFlowing(), RenderType.translucent());
    }
}
