package com.pouffydev.tcompat.data.tag;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.fluids.TComFluids;
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

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class TComFluidTagProv extends FluidTagsProvider {

    public TComFluidTagProv(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
        super(packOutput, lookupProvider, TCompat.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        fluidTag(TComFluids.moltenKnightmetal);
        fluidTag(TComFluids.moltenFiery);
        fluidTag(TComFluids.fieryEssence);
        fluidTag(TComFluids.moltenGravitite);
        fluidTag(TComFluids.moltenZanite);
        fluidTag(TComFluids.moltenSkyjade);
        fluidTag(TComFluids.moltenRefinedSentrite);
        fluidTag(TComFluids.moltenVeridium);
        fluidTag(TComFluids.moltenStratus);
        this.tag(TinkerTags.Fluids.METAL_TOOLTIPS).addTags(
                // twilight forest metals
                TComFluids.moltenKnightmetal.getTag(),
                TComFluids.moltenFiery.getTag(),
                // aether materials
                TComFluids.moltenGravitite.getTag(),
                TComFluids.moltenVeridium.getTag(),
                TComFluids.moltenStratus.getTag(),
                TComFluids.moltenRefinedSentrite.getTag()
        );
        this.tag(TinkerTags.Fluids.LARGE_GEM_TOOLTIPS).addTags(
                // aether materials
                TComFluids.moltenZanite.getTag(),
                TComFluids.moltenSkyjade.getTag()

        );
        this.tag(TinkerTags.Fluids.SLIME_TOOLTIPS).addTag(FluidTags.create(TComFluids.fieryEssence.getId()));
    }


    @Override
    public String getName() {
        return "Tinkers' Compatibility Fluid TinkerTags";
    }

    /** Adds tags for an unplacable fluid */
    private void fluidTag(FluidObject<?> fluid) {
        tag(FluidTags.create(fluid.getId())).add(fluid.get());
        TagKey<Fluid> tag = fluid.getCommonTag();
        if (tag != null) {
            tag(tag).addTag(FluidTags.create(fluid.getId()));
        }
    }

    /** Adds tags for a placable fluid */
    private void fluidTag(FlowingFluidObject<?> fluid) {
        tag(fluid.getLocalTag()).add(fluid.getStill(), fluid.getFlowing());
        TagKey<Fluid> tag = fluid.getCommonTag();
        if (tag != null) {
            tag(tag).addTag(fluid.getLocalTag());
        }
    }
}
