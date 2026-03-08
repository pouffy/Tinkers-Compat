package io.github.pouffy.tcompat.datagen.tag;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
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

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
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
        this.tag(TinkerTags.Fluids.METAL_TOOLTIPS).addTags(
                AetherInit.moltenGravitite.getTag(),
                DeepAetherInit.moltenStratus.getTag(),
                AetherReduxInit.moltenVeridium.getTag(),
                AetherReduxInit.moltenRefinedSentrite.getTag()
        );
        this.tag(TinkerTags.Fluids.LARGE_GEM_TOOLTIPS).addTags(
                AetherInit.moltenZanite.getTag(),
                DeepAetherInit.moltenSkyjade.getTag()
        );
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
