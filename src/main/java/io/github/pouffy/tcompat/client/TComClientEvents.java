package io.github.pouffy.tcompat.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.cooldown.CooldownInstance;
import io.github.pouffy.tcompat.common.fluid.TCFluids;
import io.github.pouffy.tcompat.common.cooldown.ClientModifierCooldowns;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.util.LazyModifier;

import java.util.Comparator;
import java.util.Map;

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

    private static final Comparator<Map.Entry<ModifierId, CooldownInstance>> COOLDOWNS_COMPARATOR = Map.Entry.comparingByValue(Comparator.comparingInt(CooldownInstance::getCooldownRemaining).thenComparing(CooldownInstance::getCooldownRemaining));

    @SubscribeEvent
    public static void renderGameOverlayEvent(CustomizeGuiOverlayEvent.DebugText event) {
        final var inst = Minecraft.getInstance();
        if (!inst.options.hideGui && !inst.player.isSpectator()) {
            if (!ClientModifierCooldowns.getCooldowns().getModifierCooldowns().isEmpty()) {
                int x = 2;
                int y = 5;
                var sortedCooldowns = ClientModifierCooldowns.getCooldowns().getModifierCooldowns().entrySet().stream().sorted(COOLDOWNS_COMPARATOR).toList();
                for (var entry : sortedCooldowns) {
                    renderModifier(entry.getKey(), entry.getValue(), event.getGuiGraphics(), x, y);
                    y+=11;
                }
            }
        }
    }

    public static void renderModifier(ModifierId modifier, CooldownInstance cooldownInstance, GuiGraphics guiGraphics, int x, int y) {
        PoseStack matrixStack = guiGraphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(x + TComClientConfig.MODIFIER_COOLDOWNS_X_OFFSET.get(), y + TComClientConfig.MODIFIER_COOLDOWNS_Y_OFFSET.get(), 0);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        Component modifierName = new LazyModifier(modifier).get().getDisplayName();
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("potion.withDuration", modifierName, Component.literal(StringUtil.formatTickDuration(cooldownInstance.getCooldownRemaining()))), 2, 2, 0xFFFFFF);
        matrixStack.popPose();
    }
}
