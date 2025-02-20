package com.pouffydev.tcompat.modifier.malum.rune;

import com.mojang.math.Transformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.level.Level;
import slimeknights.mantle.util.ItemLayerPixels;
import slimeknights.tconstruct.library.client.modifiers.IBakedModifierModel;
import slimeknights.tconstruct.library.client.modifiers.IUnbakedModifierModel;
import slimeknights.tconstruct.library.client.modifiers.TrimModifierModel;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.modifiers.slotless.TrimModifier;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public enum TotemicRuneModifierModel implements IBakedModifierModel {
    INSTANCE;

    private record RuneTexture(@Nullable TextureAtlasSprite sprite, int color) {
        public static final RuneTexture EMPTY = new RuneTexture(null, -1);
    }

    /** Cache texture for each item to save registry lookups */
    @SuppressWarnings("unchecked")
    private static final Map<String, RuneTexture>[] TEXTURE_CACHE = new Map[4];
    public static final ResourceLocation[] RUNE_TEXTURES = new ResourceLocation[4];
    public static final ResourceLocation[] RUNE_TEXTURES_CORRUPTED = new ResourceLocation[4];

    static {
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            TEXTURE_CACHE[type.ordinal()] = new HashMap<>();
            RUNE_TEXTURES[type.ordinal()] = new ResourceLocation("item/tool/armor/rune/" + type.getName());
            RUNE_TEXTURES_CORRUPTED[type.ordinal()] = new ResourceLocation("item/tool/armor/rune/" + type.getName() + "_corrupted");
        }
    }

    /** Constant unbaked model instance, as they are all the same */
    public static final IUnbakedModifierModel UNBAKED_INSTANCE = (smallGetter, largeGetter) -> {
        // if we are loading the model, then we are reloading resources
        // TODO: clear cache on datapack reload
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            TEXTURE_CACHE[type.ordinal()].clear();
        }
        return INSTANCE;
    };

    @Override
    public Object getCacheKey(IToolStackView tool, ModifierEntry modifier) {
        return tool.getPersistentData().getString(TotemicRuneModifier.TOTEMIC_RITE);
    }

    @Override
    public void addQuads(IToolStackView tool, ModifierEntry modifier, Function<Material,TextureAtlasSprite> spriteGetter, Transformation transforms, boolean isLarge, int startTintIndex, Consumer<Collection<BakedQuad>> quadConsumer, @Nullable ItemLayerPixels pixels) {
        if (!isLarge) {
            String runeEffectId = tool.getPersistentData().getString(TotemicRuneModifier.TOTEMIC_RITE);
            if (!runeEffectId.isEmpty() && tool.getItem() instanceof ArmorItem armor) {
                Map<String, RuneTexture> cache = TEXTURE_CACHE[armor.getType().ordinal()];
                RuneTexture texture = cache.get(runeEffectId);
                if (texture == null) {
                    texture = RuneTexture.EMPTY;
                    Level level = Minecraft.getInstance().level;
                    if (level != null) {

                    }
                }
            }
        }
    }
}
