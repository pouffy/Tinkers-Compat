package com.pouffydev.tcompat.client.modifier;

import com.mojang.math.Transformation;
import com.pouffydev.tcompat.modifier.aether.AmbrofusionModifier;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import slimeknights.mantle.util.ItemLayerPixels;
import slimeknights.tconstruct.library.client.modifiers.IUnbakedModifierModel;
import slimeknights.tconstruct.library.client.modifiers.NormalModifierModel;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
/**
 * Modifier model that turns invisible when out of ambrosium
 */
public class AmbrofusionModifierModel extends NormalModifierModel {
    /** Constant unbaked model instance, as they are all the same */
    public static final IUnbakedModifierModel UNBAKED_INSTANCE = (smallGetter, largeGetter) -> {
        Material smallTexture = smallGetter.apply("");
        Material largeTexture = largeGetter.apply("");
        if (smallTexture != null || largeTexture != null) {
            return new AmbrofusionModifierModel(smallTexture, largeTexture);
        }
        return null;
    };

    public AmbrofusionModifierModel(@Nullable Material smallTexture, @Nullable Material largeTexture) {
        super(smallTexture, largeTexture);
    }

    @Nullable
    @Override
    public Object getCacheKey(IToolStackView tool, ModifierEntry entry) {
        if (entry.getModifier() instanceof AmbrofusionModifier ambrofusion && ambrofusion.getAmbrosium(tool) == 0) {
            return null;
        }
        return super.getCacheKey(tool, entry);
    }

    @Override
    public void addQuads(IToolStackView tool, ModifierEntry entry, Function<Material, TextureAtlasSprite> spriteGetter, Transformation transforms, boolean isLarge, int startTintIndex, Consumer<Collection<BakedQuad>> quadConsumer, @Nullable ItemLayerPixels pixels) {
        if (!(entry.getModifier() instanceof AmbrofusionModifier ambrofusion) || ambrofusion.getAmbrosium(tool) != 0) {
            super.addQuads(tool, entry, spriteGetter, transforms, isLarge, startTintIndex, quadConsumer, pixels);
        }
    }
}
