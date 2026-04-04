package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.common.material.TCMaterials;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

@MethodsReturnNonnullByDefault
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

        buildRenderInfo(TCMaterials.cincinnasite).color(0xffc461).fallbacks("metal");
        buildRenderInfo(TCMaterials.netherRuby).color(0xeb4150).fallbacks("gem", "metal");

        buildRenderInfo(TCMaterials.fireDragonsteel).color(0x774153).fallbacks("bone", "metal").luminosity(9);
        buildRenderInfo(TCMaterials.iceDragonsteel).color(0xBAEAEC).fallbacks("bone", "metal").luminosity(9);
        buildRenderInfo(TCMaterials.lightningDragonsteel).color(0x917DD2).fallbacks("bone", "metal").luminosity(9);
        buildRenderInfo(TCMaterials.dragonBone).color(0xA39F93).fallbacks("bone", "rock");
        buildRenderInfo(TCMaterials.dragonScaleFire).color(0x700C0C).fallbacks("scales", "metal");
        buildRenderInfo(TCMaterials.dragonScaleIce).color(0x185084).fallbacks("scales", "metal");
        buildRenderInfo(TCMaterials.dragonScaleLightning).color(0x232323).fallbacks("scales", "metal");
        buildRenderInfo(TCMaterials.dragonRed).color(0x700C0C).fallbacks("scales", "metal");
        buildRenderInfo(TCMaterials.dragonGreen).color(0x40502c).fallbacks("scales", "metal");
        buildRenderInfo(TCMaterials.dragonBronze).color(0x7b622b).fallbacks("scales", "metal");
        buildRenderInfo(TCMaterials.dragonAmethyst).color(0x361c59).fallbacks("scales", "metal");
        buildRenderInfo(TCMaterials.dragonBlack).color(0x232323).fallbacks("scales", "metal");
        buildRenderInfo(TCMaterials.dragonBlue).color(0xafd1e1).fallbacks("scales", "metal");
        buildRenderInfo(TCMaterials.dragonCopper).color(0x5a3428).fallbacks("scales", "metal");
        buildRenderInfo(TCMaterials.dragonElectric).color(0x241c59).fallbacks("scales", "metal");
        buildRenderInfo(TCMaterials.dragonGray).color(0x4b443f).fallbacks("scales", "metal");
        buildRenderInfo(TCMaterials.dragonSapphire).color(0x185084).fallbacks("scales", "metal");
        buildRenderInfo(TCMaterials.dragonSilver).color(0x595c5e).fallbacks("scales", "metal");
        buildRenderInfo(TCMaterials.dragonWhite).color(0xf1f1f1).fallbacks("scales", "metal");
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Render Info";
    }
}
