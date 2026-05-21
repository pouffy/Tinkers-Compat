package io.github.pouffy.tcompat.datagen.tag;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.compat.aether.AetherMaterials;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxMaterials;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRMaterials;
import io.github.pouffy.tcompat.compat.betternether.BetternetherMaterials;
import io.github.pouffy.tcompat.compat.cataclysm.CataclysmMaterials;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherMaterials;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.tinkering.AbstractMaterialTagProvider;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialManager;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import static io.github.pouffy.tcompat.TCompat.getResource;

@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
public class TCMaterialTagProv extends AbstractMaterialTagProvider {
    public TCMaterialTagProv(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, TCompat.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(TCTags.Materials.AETHER).addOptional(
                AetherMaterials.aetherRock,
                AetherMaterials.aetherWood,
                AetherMaterials.zanite,
                AetherMaterials.gravitite,
                DeepAetherMaterials.skyjade,
                AetherReduxMaterials.veridium,
                AetherReduxMaterials.refinedSentrite,
                AetherTRMaterials.pyral,
                AetherTRMaterials.valkyrum,
                AetherTRMaterials.neptune,
                AetherMaterials.lightnum,
                DeepAetherMaterials.stormforgedSteel,
                AetherMaterials.draculite
        );
        tag(TinkerTags.Materials.NETHER_GATED).addOptional(
                BetternetherMaterials.cincinnasite,
                BetternetherMaterials.netherRuby
        );
        tag(TCTags.Materials.CURSIUM_COMPANION).addOptional(
                CataclysmMaterials.blackSteel
        );
        tag(TCTags.Materials.LACRIMA_COMPANION).addOptional(
                CataclysmMaterials.essenceOfTheStorm
        );
        tag(TCTags.Materials.WITHERITE_COMPANION).addOptional(
                MaterialIds.iron
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
