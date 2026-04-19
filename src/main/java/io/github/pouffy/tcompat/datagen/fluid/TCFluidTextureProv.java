package io.github.pouffy.tcompat.datagen.fluid;

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
import net.minecraft.data.PackOutput;
import slimeknights.mantle.fluid.texture.AbstractFluidTextureProvider;
import slimeknights.mantle.fluid.texture.FluidTexture;
import slimeknights.mantle.registration.object.FluidObject;

import static io.github.pouffy.tcompat.TCompat.getResource;
import static slimeknights.tconstruct.fluids.TinkerFluids.withoutMolten;

@SuppressWarnings({"unused", "SameParameterValue", "UnusedReturnValue"})
@MethodsReturnNonnullByDefault
public class TCFluidTextureProv extends AbstractFluidTextureProvider {
    public TCFluidTextureProv(PackOutput packOutput) {
        super(packOutput, TCompat.MOD_ID);
    }

    @Override
    public void addTextures() {
        compatOre(TCFluids.moltenSkyjade);
        compatOre(TCFluids.moltenStratus);
        compatOre(TCFluids.moltenZanite);
        compatOre(TCFluids.moltenGravitite);
        compatOre(TCFluids.moltenRefinedSentrite);
        compatOre(TCFluids.moltenVeridium);
        compatOre(TCFluids.moltenPyral);
        compatOre(TCFluids.moltenNeptune);
        compatOre(TCFluids.moltenValkyrum);
        compatOre(TCFluids.moltenDesh);
        compatOre(TCFluids.moltenCalorite);
        compatOre(TCFluids.moltenOstrum);
        compatOre(TCFluids.moltenThallasium);
        compatOre(TCFluids.moltenTerminite);
        compatOre(TCFluids.moltenAeternium);
        compatOre(TCFluids.moltenLightnum);
        compatOre(TCFluids.moltenDraculite);
        compatOre(TCFluids.moltenStormforgedSteel);
        compatOre(TCFluids.moltenCincinnasite);
        compatOre(TCFluids.moltenNetherRuby);
        slime(TCFluids.fireBlood);
        slime(TCFluids.iceBlood);
        slime(TCFluids.lightningBlood);
        compatOre(TCFluids.moltenFireDragonsteel);
        compatOre(TCFluids.moltenIceDragonsteel);
        compatOre(TCFluids.moltenLightningDragonsteel);
    }

    private FluidTexture.Builder root(FluidObject<?> fluid) {
        return texture(fluid).wrapId("fluid/", "/", false, false);
    }

    private FluidTexture.Builder folder(FluidObject<?> fluid, String folder) {
        return texture(fluid).wrapId("fluid/"+folder+"/", "/", false, false);
    }

    private FluidTexture.Builder named(FluidObject<?> fluid, String name) {
        return texture(fluid).textures(getResource("fluid/"+name+"/"), false, false);
    }

    private FluidTexture.Builder slime(FluidObject<?> fluid) {
        return folder(fluid, "slime");
    }

    private FluidTexture.Builder slime(FluidObject<?> fluid, String name) {
        return named(fluid, "slime/"+name);
    }

    private FluidTexture.Builder molten(FluidObject<?> fluid) {
        return named(fluid, "molten/" + withoutMolten(fluid));
    }

    private FluidTexture.Builder moltenFolder(FluidObject<?> fluid, String folder) {
        return named(fluid, "molten/" + folder + "/" + withoutMolten(fluid));
    }

    private FluidTexture.Builder stone(FluidObject<?> fluid) {
        return moltenFolder(fluid, "stone");
    }

    private FluidTexture.Builder ore(FluidObject<?> fluid) {
        return moltenFolder(fluid, "ore");
    }

    private FluidTexture.Builder alloy(FluidObject<?> fluid) {
        return moltenFolder(fluid, "alloy");
    }

    private FluidTexture.Builder compatOre(FluidObject<?> fluid) {
        return moltenFolder(fluid, "compat_ore");
    }

    private FluidTexture.Builder compatAlloy(FluidObject<?> fluid) {
        return moltenFolder(fluid, "compat_alloy");
    }

    private FluidTexture.Builder tintedStew(FluidObject<?> fluid) {
        return named(fluid, "food/stew");
    }

    private FluidTexture.Builder tintedStone(FluidObject<?> fluid) {
        return named(fluid, "molten/stone");
    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Fluid Texture Providers";
    }
}
