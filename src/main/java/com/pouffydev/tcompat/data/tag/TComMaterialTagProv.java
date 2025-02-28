package com.pouffydev.tcompat.data.tag;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.data.TComTags;
import com.pouffydev.tcompat.material.TComMaterialIds;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.library.data.tinkering.AbstractMaterialTagProvider;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialManager;

import static com.pouffydev.tcompat.TCompat.getResource;


public class TComMaterialTagProv extends AbstractMaterialTagProvider {
    public TComMaterialTagProv(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, TCompat.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(TComTags.Materials.AETHER).add(
                TComMaterialIds.aetherRock,
                TComMaterialIds.aetherWood,
                TComMaterialIds.zanite,
                TComMaterialIds.gravitite,
                TComMaterialIds.skyjade,
                TComMaterialIds.veridium,
                TComMaterialIds.refinedSentrite
        );
        tag(TComTags.Materials.TWILIGHT_FOREST).add(
                TComMaterialIds.twilightWood,
                TComMaterialIds.fiery,
                TComMaterialIds.knightmetal,
                TComMaterialIds.nagascale,
                TComMaterialIds.ravenFeather,
                TComMaterialIds.steeleaf
        );
    }

    private static TagKey<IMaterial> materialTag(String name) {
        return MaterialManager.getTag(getResource(name));
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Tags";
    }
}
