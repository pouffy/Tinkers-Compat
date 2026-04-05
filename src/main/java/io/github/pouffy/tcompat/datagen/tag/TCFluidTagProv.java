package io.github.pouffy.tcompat.datagen.tag;

import io.github.pouffy.tcompat.TCompat;
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
        fluidTag(AetherInit.moltenGravitite);
        fluidTag(AetherInit.moltenZanite);
        fluidTag(DeepAetherInit.moltenSkyjade);
        fluidTag(DeepAetherInit.moltenStratus);
        fluidTag(AetherReduxInit.moltenRefinedSentrite);
        fluidTag(AetherReduxInit.moltenVeridium);
        fluidTag(AetherTRInit.moltenValkyrum);
        fluidTag(AetherTRInit.moltenPyral);
        fluidTag(AetherTRInit.moltenNeptune);
        fluidTag(AdAstraInit.moltenDesh);
        fluidTag(AdAstraInit.moltenCalorite);
        fluidTag(AdAstraInit.moltenOstrum);
        fluidTag(BetterendInit.moltenTerminite);
        fluidTag(BetterendInit.moltenThallasium);
        fluidTag(BetterendInit.moltenAeternium);
        fluidTag(AetherInit.moltenLightnum);
        fluidTag(AetherInit.moltenDraculite);
        fluidTag(DeepAetherInit.moltenStormforgedSteel);
        fluidTag(BetternetherInit.moltenCincinnasite);
        fluidTag(BetternetherInit.moltenNetherRuby);

        fluidTag(IFInit.fireBlood);
        fluidTag(IFInit.iceBlood);
        fluidTag(IFInit.lightningBlood);
        fluidTag(IFInit.moltenFireDragonsteel);
        fluidTag(IFInit.moltenIceDragonsteel);
        fluidTag(IFInit.moltenLightningDragonsteel);

        var metals = this.tag(TinkerTags.Fluids.METAL_TOOLTIPS);
        // Aether
        metals.addOptionalTag(AetherInit.moltenGravitite.getTag());
        metals.addOptionalTag(AetherInit.moltenLightnum.getTag());
        metals.addOptionalTag(AetherInit.moltenDraculite.getTag());
        metals.addOptionalTag(AetherInit.moltenZanite.getTag());
        metals.addOptionalTag(DeepAetherInit.moltenStratus.getTag());
        metals.addOptionalTag(DeepAetherInit.moltenStormforgedSteel.getTag());
        metals.addOptionalTag(DeepAetherInit.moltenSkyjade.getTag());
        metals.addOptionalTag(AetherReduxInit.moltenVeridium.getTag());
        metals.addOptionalTag(AetherReduxInit.moltenRefinedSentrite.getTag());
        metals.addOptionalTag(AetherTRInit.moltenValkyrum.getTag());
        metals.addOptionalTag(AetherTRInit.moltenPyral.getTag());
        metals.addOptionalTag(AetherTRInit.moltenNeptune.getTag());
        // Ad Astra
        metals.addOptionalTag(AdAstraInit.moltenDesh.getTag());
        metals.addOptionalTag(AdAstraInit.moltenCalorite.getTag());
        metals.addOptionalTag(AdAstraInit.moltenOstrum.getTag());
        // Betterend
        metals.addOptionalTag(BetterendInit.moltenTerminite.getTag());
        metals.addOptionalTag(BetterendInit.moltenThallasium.getTag());
        metals.addOptionalTag(BetterendInit.moltenAeternium.getTag());
        // Betternether
        metals.addOptionalTag(BetternetherInit.moltenCincinnasite.getTag());
        // Ice & Fire
        metals.addOptionalTag(IFInit.moltenFireDragonsteel.getTag());
        metals.addOptionalTag(IFInit.moltenIceDragonsteel.getTag());
        metals.addOptionalTag(IFInit.moltenLightningDragonsteel.getTag());

        var largeGems = this.tag(TinkerTags.Fluids.LARGE_GEM_TOOLTIPS);
        // Betternether
        largeGems.addOptionalTag(BetternetherInit.moltenNetherRuby.getTag());
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
