package io.github.pouffy.tcompat.datagen.tag;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.fluid.TCFluids;
import io.github.pouffy.tcompat.common.data.TCTags;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.mantle.datagen.MantleTags;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.common.TinkerTags;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"unused"})
@ParametersAreNonnullByDefault @MethodsReturnNonnullByDefault
public class TCFluidTagProv extends FluidTagsProvider {
    public TCFluidTagProv(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
        super(packOutput, lookupProvider, TCompat.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        fluidTag(TCFluids.moltenGravitite);
        fluidTag(TCFluids.moltenZanite);
        fluidTag(TCFluids.moltenSkyjade);
        fluidTag(TCFluids.moltenStratus);
        fluidTag(TCFluids.moltenRefinedSentrite);
        fluidTag(TCFluids.moltenVeridium);
        fluidTag(TCFluids.moltenValkyrum);
        fluidTag(TCFluids.moltenPyral);
        fluidTag(TCFluids.moltenNeptune);
        fluidTag(TCFluids.moltenDesh);
        fluidTag(TCFluids.moltenCalorite);
        fluidTag(TCFluids.moltenOstrum);
        fluidTag(TCFluids.moltenTerminite);
        fluidTag(TCFluids.moltenThallasium);
        fluidTag(TCFluids.moltenAeternium);
        fluidTag(TCFluids.moltenLightnum);
        fluidTag(TCFluids.moltenDraculite);
        fluidTag(TCFluids.moltenStormforgedSteel);
        fluidTag(TCFluids.moltenCincinnasite);
        fluidTag(TCFluids.moltenNetherRuby);
        fluidTag(TCFluids.fireBlood);
        fluidTag(TCFluids.iceBlood);
        fluidTag(TCFluids.lightningBlood);
        fluidTag(TCFluids.moltenFireDragonsteel);
        fluidTag(TCFluids.moltenIceDragonsteel);
        fluidTag(TCFluids.moltenLightningDragonsteel);
        fluidTag(TCFluids.fireLilyMixture);
        fluidTag(TCFluids.frostLilyMixture);
        fluidTag(TCFluids.lightningLilyMixture);
        fluidTag(TCFluids.ambrosia);
        fluidTag(TCFluids.aloeVeraJuice);
        fluidTag(TCFluids.whitePuffballStew);
        fluidTag(TCFluids.alliumOddionSoup);
        fluidTag(TCFluids.umbrellaClusterJuice);
        fluidTag(TCFluids.wartSoup);
        fluidTag(TCFluids.agaveMedicine);
        fluidTag(TCFluids.moltenAncientMetal);
        fluidTag(TCFluids.moltenBlackSteel);
        fluidTag(TCFluids.moltenCursium);
        fluidTag(TCFluids.moltenWitherite);
        fluidTag(TCFluids.moltenIgnitium);
        fluidTag(TCFluids.moltenLacrima);
        fluidTag(TCFluids.runicSap);
        fluidTag(TCFluids.cursedSap);
        fluidTag(TCFluids.moltenSoulStainedSteel);
        fluidTag(TCFluids.moltenMalignantPewter);
        fluidTag(TCFluids.moltenHallowedGold);
        fluidTag(TCFluids.moltenBlazingQuartz);
        fluidTag(TCFluids.moltenCthonicGold);
        fluidTag(TCFluids.moltenMalignantLead);

        var metals = this.tag(TinkerTags.Fluids.METAL_TOOLTIPS);
        // Aether
        metals.addOptionalTag(TCFluids.moltenGravitite.getTag());
        metals.addOptionalTag(TCFluids.moltenLightnum.getTag());
        metals.addOptionalTag(TCFluids.moltenDraculite.getTag());
        metals.addOptionalTag(TCFluids.moltenZanite.getTag());
        metals.addOptionalTag(TCFluids.moltenStratus.getTag());
        metals.addOptionalTag(TCFluids.moltenStormforgedSteel.getTag());
        metals.addOptionalTag(TCFluids.moltenSkyjade.getTag());
        metals.addOptionalTag(TCFluids.moltenVeridium.getTag());
        metals.addOptionalTag(TCFluids.moltenRefinedSentrite.getTag());
        metals.addOptionalTag(TCFluids.moltenValkyrum.getTag());
        metals.addOptionalTag(TCFluids.moltenPyral.getTag());
        metals.addOptionalTag(TCFluids.moltenNeptune.getTag());
        // Ad Astra
        metals.addOptionalTag(TCFluids.moltenDesh.getTag());
        metals.addOptionalTag(TCFluids.moltenCalorite.getTag());
        metals.addOptionalTag(TCFluids.moltenOstrum.getTag());
        // Betterend
        metals.addOptionalTag(TCFluids.moltenTerminite.getTag());
        metals.addOptionalTag(TCFluids.moltenThallasium.getTag());
        metals.addOptionalTag(TCFluids.moltenAeternium.getTag());
        // Betternether
        metals.addOptionalTag(TCFluids.moltenCincinnasite.getTag());
        // Ice & Fire
        metals.addOptionalTag(TCFluids.moltenFireDragonsteel.getTag());
        metals.addOptionalTag(TCFluids.moltenIceDragonsteel.getTag());
        metals.addOptionalTag(TCFluids.moltenLightningDragonsteel.getTag());
        // Cataclysm
        metals.addOptionalTag(TCFluids.moltenAncientMetal.getTag());
        metals.addOptionalTag(TCFluids.moltenBlackSteel.getTag());
        metals.addOptionalTag(TCFluids.moltenCursium.getTag());
        metals.addOptionalTag(TCFluids.moltenWitherite.getTag());
        metals.addOptionalTag(TCFluids.moltenIgnitium.getTag());
        // Malum
        metals.addOptionalTag(TCFluids.moltenSoulStainedSteel.getTag());
        metals.addOptionalTag(TCFluids.moltenMalignantPewter.getTag());
        metals.addOptionalTag(TCFluids.moltenHallowedGold.getTag());

        var largeGems = this.tag(TinkerTags.Fluids.LARGE_GEM_TOOLTIPS);
        // Betternether
        largeGems.addOptionalTag(TCFluids.moltenNetherRuby.getTag());
        // Cataclysm
        largeGems.addOptionalTag(TCFluids.moltenLacrima.getTag());

        var smallerGems = this.tag(TCTags.Fluids.SMALLER_GEM_TOOLTIPS);
        // Malum
        smallerGems.addOptionalTag(TCFluids.moltenBlazingQuartz.getTag());
        smallerGems.addOptionalTag(TCFluids.moltenCthonicGold.getTag());
        smallerGems.addOptionalTag(TCFluids.moltenMalignantLead.getTag());

        var slimes = this.tag(TinkerTags.Fluids.SLIME_TOOLTIPS);
        // Ice and Fire
        slimes.addOptionalTag(TCFluids.fireBlood.getTag());
        slimes.addOptionalTag(TCFluids.iceBlood.getTag());
        slimes.addOptionalTag(TCFluids.lightningBlood.getTag());
        // Malum
        slimes.addOptionalTag(TCFluids.runicSap.getTag());
        slimes.addOptionalTag(TCFluids.cursedSap.getTag());

        var soups = this.tag(MantleTags.Fluids.SOUP);
        soups.addOptionalTag(TCFluids.fireLilyMixture.getTag());
        soups.addOptionalTag(TCFluids.frostLilyMixture.getTag());
        soups.addOptionalTag(TCFluids.lightningLilyMixture.getTag());
        soups.addOptionalTag(TCFluids.ambrosia.getTag());
        soups.addOptionalTag(TCFluids.whitePuffballStew.getTag());
        soups.addOptionalTag(TCFluids.alliumOddionSoup.getTag());
        soups.addOptionalTag(TCFluids.wartSoup.getTag());
        soups.addOptionalTag(TCFluids.agaveMedicine.getTag());

        var bottles = this.tag(TinkerTags.Fluids.BOTTLE_TOOLTIPS);
        bottles.addOptionalTag(TCFluids.aloeVeraJuice.getTag());
        bottles.addOptionalTag(TCFluids.umbrellaClusterJuice.getTag());
    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Fluid TinkerTags";
    }

    private void fluidTag(FluidObject<?> fluid) {
        tag(FluidTags.create(fluid.getId())).add(fluid.get());
        TagKey<Fluid> tag = fluid.getCommonTag();
        if (tag != null) {
            tag(tag).addTag(FluidTags.create(fluid.getId()));
        }
    }

    private void fluidTag(FlowingFluidObject<?> fluid) {
        tag(fluid.getLocalTag()).add(fluid.getStill(), fluid.getFlowing());
        TagKey<Fluid> tag = fluid.getCommonTag();
        if (tag != null) {
            tag(tag).addTag(fluid.getLocalTag());
        }
    }
}
