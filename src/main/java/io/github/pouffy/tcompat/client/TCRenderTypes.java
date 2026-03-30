package io.github.pouffy.tcompat.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class TCRenderTypes extends RenderType {
    public TCRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }

    public static RenderType getIce(ResourceLocation locationIn) {
        RenderStateShard.TextureStateShard lvt_1_1_ = new RenderStateShard.TextureStateShard(locationIn, false, false);
        return create("tcompat_ice_texture", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RenderType.RENDERTYPE_BEACON_BEAM_SHADER).setTextureState(lvt_1_1_).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(true));
    }
}
