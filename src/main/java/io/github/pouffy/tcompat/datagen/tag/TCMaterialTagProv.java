package io.github.pouffy.tcompat.datagen.tag;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.material.TCMaterials;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.tinkering.AbstractMaterialTagProvider;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialManager;

import static io.github.pouffy.tcompat.TCompat.getResource;

@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
public class TCMaterialTagProv extends AbstractMaterialTagProvider {
    public TCMaterialTagProv(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, TCompat.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(TCTags.Materials.AETHER).add(
                TCMaterials.aetherRock,
                TCMaterials.aetherWood,
                TCMaterials.zanite,
                TCMaterials.gravitite,
                TCMaterials.skyjade,
                TCMaterials.veridium,
                TCMaterials.refinedSentrite,
                TCMaterials.pyral,
                TCMaterials.valkyrum,
                TCMaterials.neptune,
                TCMaterials.lightnum,
                TCMaterials.stormforgedSteel,
                TCMaterials.draculite
        );
        tag(TinkerTags.Materials.NETHER_GATED).add(
                TCMaterials.cincinnasite,
                TCMaterials.netherRuby
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
