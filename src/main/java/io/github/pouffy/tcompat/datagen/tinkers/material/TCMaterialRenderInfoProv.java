package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.common.material.TCMaterials;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

public class TCMaterialRenderInfoProv extends AbstractMaterialRenderInfoProvider {
    public TCMaterialRenderInfoProv(PackOutput packOutput, @Nullable AbstractMaterialSpriteProvider materialSprites, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, materialSprites, existingFileHelper);
    }

    @Override
    protected void addMaterialRenderInfo() {
        buildRenderInfo(TCMaterials.aetherWood).color(0x5C5B41).fallbacks("wood", "stick", "primitive");
        redirect(TCMaterials.aetherRock, TCMaterials.holystone);
        for (MaterialVariantId materialId : TCMaterials.allVariants) {
            buildRenderInfo(materialId);
        }
        buildRenderInfo(TCMaterials.zanite).fallbacks("gem");
        buildRenderInfo(TCMaterials.gravitite).fallbacks("metal");
        buildRenderInfo(TCMaterials.skyjade).fallbacks("gem");
        buildRenderInfo(TCMaterials.veridium).fallbacks("metal");
        buildRenderInfo(TCMaterials.refinedSentrite).fallbacks("metal");
        buildRenderInfo(TCMaterials.hellbark).color(0x332929).fallbacks("wood", "stick", "primitive");
        buildRenderInfo(TCMaterials.blightbunnyFang).color(0xc1d3d8).fallbacks("bone", "metal");
        buildRenderInfo(TCMaterials.mykapodShell).color(0x5e65a5).fallbacks("bone", "metal");
        buildRenderInfo(TCMaterials.pyral).fallbacks("metal");
        buildRenderInfo(TCMaterials.valkyrum).fallbacks("metal");
        buildRenderInfo(TCMaterials.neptune).fallbacks("metal");
        buildRenderInfo(TCMaterials.wickedWax).color(0xf93985).fallbacks("bone", "metal");
        buildRenderInfo(TCMaterials.desh).color(0xc77142).fallbacks("metal");
        buildRenderInfo(TCMaterials.calorite).color(0xb83145).fallbacks("metal");
        buildRenderInfo(TCMaterials.ostrum).color(0x925e64).fallbacks("metal");
        buildRenderInfo(TCMaterials.thallasium).color(0x7ad0d3).fallbacks("metal");
        buildRenderInfo(TCMaterials.terminite).color(0x71f3e8).fallbacks("metal");
        buildRenderInfo(TCMaterials.aeternium).color(0x6d8883).fallbacks("metal");

        buildRenderInfo(TCMaterials.stormforgedSteel).color(0xb8c5d1).fallbacks("metal");
        buildRenderInfo(TCMaterials.lightnum).color(0x5a9bdb).fallbacks("metal");
        buildRenderInfo(TCMaterials.draculite).color(0xd11e1e).fallbacks("metal");
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Render Info";
    }
}
