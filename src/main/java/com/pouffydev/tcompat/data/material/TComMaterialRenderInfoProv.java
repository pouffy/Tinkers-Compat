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
        for (MaterialVariantId materialId : TComMaterialIds.otbwgVariantWoods) {
            buildRenderInfo(materialId);
        }
        for (MaterialVariantId materialId : TComMaterialIds.otbwgVariantRocks) {
            buildRenderInfo(materialId);
        }
        for (MaterialVariantId materialId : TComMaterialIds.aetherVariantWoods) {
            buildRenderInfo(materialId);
        }
        for (MaterialVariantId materialId : TComMaterialIds.aetherVariantRocks) {
            buildRenderInfo(materialId);
        }
        for (MaterialVariantId materialId : TComMaterialIds.deepAetherVariantWoods) {
            buildRenderInfo(materialId);
        }
        for (MaterialVariantId materialId : TComMaterialIds.deepAetherVariantRocks) {
            buildRenderInfo(materialId);
        }
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Render Info";
    }
}
