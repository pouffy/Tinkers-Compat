package io.github.pouffy.tcompat.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.pouffy.tcompat.compat.malum.MalumHandler;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Unique
    private static void tcompat$colorizeHand(ModelPart modelPart, PoseStack matrices, VertexConsumer vertices, int light, int overlay, MultiBufferSource vertexConsumers, AbstractClientPlayer player) {
        if (MalumHandler.isCloaking(player)) {
            modelPart.render(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(player.getSkinTextureLocation())), light, overlay, 1.0F, 1.0F, 1.0F, 0.5f);
        } else
            modelPart.render(matrices, vertices, light, overlay);
    }

    @OnlyIn(Dist.CLIENT)
    @Redirect(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", ordinal = 0))
    private void makeArmTranslucent(ModelPart modelPart, PoseStack matrices, VertexConsumer vertices, int light, int overlay, PoseStack matrices2, MultiBufferSource vertexConsumers, int light2, AbstractClientPlayer player) {
        tcompat$colorizeHand(modelPart, matrices, vertices, light, overlay, vertexConsumers, player);
    }

    @OnlyIn(Dist.CLIENT)
    @Redirect(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", ordinal = 1))
    private void makeSleeveTranslucent(ModelPart modelPart, PoseStack matrices, VertexConsumer vertices, int light, int overlay, PoseStack matrices2, MultiBufferSource vertexConsumers, int light2, AbstractClientPlayer player) {
        tcompat$colorizeHand(modelPart, matrices, vertices, light, overlay, vertexConsumers, player);
    }
}
