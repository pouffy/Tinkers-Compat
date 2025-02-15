package com.pouffydev.tcompat.data.material;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.material.TComMaterialIds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

import java.util.List;

public class TComMaterialRenderInfoProv extends AbstractMaterialRenderInfoProvider {
    public TComMaterialRenderInfoProv(PackOutput packOutput, @Nullable AbstractMaterialSpriteProvider materialSprites, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, materialSprites, existingFileHelper);
    }

    @Override
    protected void addMaterialRenderInfo() {
        for (MaterialVariantId materialId : TComMaterialIds.otbwgVariants) {
            buildRenderInfo(materialId);
        }

    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Render Info";
    }
}
