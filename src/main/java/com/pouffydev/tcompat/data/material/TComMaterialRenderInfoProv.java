package com.pouffydev.tcompat.data.material;

import com.pouffydev.tcompat.material.TComMaterialIds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

public class TComMaterialRenderInfoProv extends AbstractMaterialRenderInfoProvider {
    public TComMaterialRenderInfoProv(PackOutput packOutput, @Nullable AbstractMaterialSpriteProvider materialSprites, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, materialSprites, existingFileHelper);
    }

    @Override
    protected void addMaterialRenderInfo() {
        buildRenderInfo(TComMaterialIds.aetherWood).color(0x5C5B41).fallbacks("wood", "stick", "primitive");
        redirect(TComMaterialIds.aetherRock, TComMaterialIds.holystone);
        for (MaterialVariantId materialId : TComMaterialIds.allVariants) {
            buildRenderInfo(materialId);
        }
        buildRenderInfo(TComMaterialIds.twilightWood).color(0x5C5B41).fallbacks("wood", "stick", "primitive");
        buildRenderInfo(TComMaterialIds.fiery).luminosity(15).fallbacks("metal");
        buildRenderInfo(TComMaterialIds.knightmetal).fallbacks("metal");
        buildRenderInfo(TComMaterialIds.nagascale);
        buildRenderInfo(TComMaterialIds.ravenFeather);
        buildRenderInfo(TComMaterialIds.steeleaf);
        buildRenderInfo(TComMaterialIds.zanite).fallbacks("gem");
        buildRenderInfo(TComMaterialIds.gravitite).fallbacks("metal");
        buildRenderInfo(TComMaterialIds.skyjade).fallbacks("gem");
        buildRenderInfo(TComMaterialIds.veridium).fallbacks("metal");
        buildRenderInfo(TComMaterialIds.refinedSentrite).fallbacks("metal");
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Render Info";
    }
}
