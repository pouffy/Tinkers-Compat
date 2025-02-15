package com.pouffydev.tcompat.data.material;

import com.pouffydev.tcompat.material.TComMaterialIds;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToSpriteTransformer;
import slimeknights.tconstruct.library.client.data.spritetransformer.ISpriteTransformer;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

import static slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider.WOOD;

public class TComMaterialSpriteProv extends AbstractMaterialSpriteProvider {
    @Override
    public String getName() {
        return "Tinker's Compatability Material Sprites";
    }

    @Override
    protected void addAllMaterials() {
        //buildMaterial(GTCMaterialIds.bismuth)
        //        .meleeHarvest()
        //        .fallbacks("metal").ranged()
        //        .colorMapper(GreyToColorMapping.builderFromBlack()
        //                .addARGB(63, 0xFF24383e)
        //                .addARGB(102, 0xFF274449)
        //                .addARGB(140, 0xFF315e63)
        //                .addARGB(178, 0xFF3d7d81)
        //                .addARGB(216, 0xFF489fa2)
        //                .addARGB(255, 0xFF55c5c5).build());
        buildPlanks(TComMaterialIds.aspen).colorMapper(GreyToColorMapping.builderFromBlack()
                .addARGB(63, 0xFF9E644D)
                .addARGB(102, 0xFFA56C56)
                .addARGB(140, 0xFFAD755F)
                .addARGB(178, 0xFFC18260)
                .addARGB(216, 0xFFD19373)
                .addARGB(234, 0xFFDDA289)
                .addARGB(255, 0xFFDDAD8D)
                .build());
        buildPlanks(TComMaterialIds.baobab).colorMapper(GreyToColorMapping.builderFromBlack()
                .addARGB(63, 0xFF52493E)
                .addARGB(102, 0xFF675C48)
                .addARGB(140, 0xFF77694D)
                .addARGB(178, 0xFF87764E)
                .addARGB(216, 0xFF948154)
                .addARGB(234, 0xFFA48F5D)
                .addARGB(255, 0xFFB3995B)
                .build());
        buildPlanks(TComMaterialIds.blueEnchanted).colorMapper(GreyToColorMapping.builderFromBlack()
                .addARGB(63, 0xFF26367D)
                .addARGB(102, 0xFF2F4089)
                .addARGB(140, 0xFF3C4D97)
                .addARGB(178, 0xFF4757A2)
                .addARGB(216, 0xFF5666B0)
                .addARGB(234, 0xFF6574BB)
                .addARGB(255, 0xFF7180C9)
                .build());
        buildPlanks(TComMaterialIds.cika).colorMapper(GreyToColorMapping.builderFromBlack()
                .addARGB(63, 0xFF432C27)
                .addARGB(102, 0xFF57372F)
                .addARGB(140, 0xFF643D35)
                .addARGB(178, 0xFF714439)
                .addARGB(216, 0xFF7F4B3C)
                .addARGB(234, 0xFF905344)
                .addARGB(255, 0xFFA15B4B)
                .build());


    }

    public static ISpriteTransformer transformerFromSprite(ResourceLocation texture, int frames, int highlightColor) {
        GreyToSpriteTransformer.Builder builder = GreyToSpriteTransformer.builderFromBlack();
        builder.addTexture( 63, texture, 0xFF404040)
                .addTexture(102, texture, 0xFF808080)
                .addTexture(140, texture);
        if (highlightColor != 0) {
            builder.addTexture(216, texture).addARGB(255, highlightColor);
        }
        if (frames > 1) {
            return builder.animated(texture, frames);
        }
        return builder.build();
    }

    /** Adds a plank type as a wood variant */
    private MaterialSpriteInfoBuilder buildPlanks(MaterialVariantId material) {
        return buildMaterial(material)
                .variant().meleeHarvest().ranged().shieldCore().statType(WOOD)
                .fallbacks("wood", "stick", "primitive");
    }
}
