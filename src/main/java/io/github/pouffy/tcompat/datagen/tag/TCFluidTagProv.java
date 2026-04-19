package io.github.pouffy.tcompat.datagen.tag;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.compat.ad_astra.AdAstraInit;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRInit;
import io.github.pouffy.tcompat.compat.betterend.BetterendInit;
import io.github.pouffy.tcompat.compat.betternether.BetternetherInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.ExistingFileHelper;
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

        var largeGems = this.tag(TinkerTags.Fluids.LARGE_GEM_TOOLTIPS);
        // Betternether
        largeGems.addOptionalTag(TCFluids.moltenNetherRuby.getTag());
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
