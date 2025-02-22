package com.pouffydev.tcompat.data.fluid;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.fluids.TComFluids;
import net.minecraft.data.PackOutput;
import slimeknights.mantle.fluid.texture.AbstractFluidTextureProvider;
import slimeknights.mantle.fluid.texture.FluidTexture;
import slimeknights.mantle.registration.object.FluidObject;

import static com.pouffydev.tcompat.TCompat.getResource;
import static slimeknights.tconstruct.fluids.TinkerFluids.withoutMolten;

public class TComFluidTextureProv extends AbstractFluidTextureProvider {
    public TComFluidTextureProv(PackOutput packOutput) {
        super(packOutput, TCompat.MOD_ID);
    }

    @Override
    public void addTextures() {
        compatOre(TComFluids.moltenKnightmetal);
        compatOre(TComFluids.moltenFiery);
        root(TComFluids.fieryEssence);
    }

    /* Helpers */

    /** Creates a texture in the root folder */
    private FluidTexture.Builder root(FluidObject<?> fluid) {
        return texture(fluid).wrapId("fluid/", "/", false, false);
    }

    /** Creates a texture using the fluid's ID in the given folder */
    private FluidTexture.Builder folder(FluidObject<?> fluid, String folder) {
        return texture(fluid).wrapId("fluid/"+folder+"/", "/", false, false);
    }

    /** Creates a texture using the given fixed name in the fluid folder */
    private FluidTexture.Builder named(FluidObject<?> fluid, String name) {
        return texture(fluid).textures(getResource("fluid/"+name+"/"), false, false);
    }

    /** Creates a texture in the slime folder using the ID */
    private FluidTexture.Builder slime(FluidObject<?> fluid) {
        return folder(fluid, "slime");
    }

    /** Creates a texture with the given name in the slime folder */
    private FluidTexture.Builder slime(FluidObject<?> fluid, String name) {
        return named(fluid, "slime/"+name);
    }


    /* Molten */

    /** Creates a texture in the molten using the fluid ID (stripping molten) */
    private FluidTexture.Builder molten(FluidObject<?> fluid) {
        return named(fluid, "molten/" + withoutMolten(fluid));
    }

    /** Creates a texture in given subfolder of molten, stripping molten from the name */
    private FluidTexture.Builder moltenFolder(FluidObject<?> fluid, String folder) {
        return named(fluid, "molten/" + folder + "/" + withoutMolten(fluid));
    }

    /** Creates a texture in the molten stone folder using the given name */
    private FluidTexture.Builder stone(FluidObject<?> fluid) {
        return moltenFolder(fluid, "stone");
    }

    /** Creates a texture in the ore folder using the given name */
    private FluidTexture.Builder ore(FluidObject<?> fluid) {
        return moltenFolder(fluid, "ore");
    }

    /** Creates a texture in the alloy folder using the given name */
    private FluidTexture.Builder alloy(FluidObject<?> fluid) {
        return moltenFolder(fluid, "alloy");
    }

    /** Creates a texture in the compat ore folder using the given name */
    private FluidTexture.Builder compatOre(FluidObject<?> fluid) {
        return moltenFolder(fluid, "compat_ore");
    }

    /** Creates a texture in the compat alloy folder using the given name */
    private FluidTexture.Builder compatAlloy(FluidObject<?> fluid) {
        return moltenFolder(fluid, "compat_alloy");
    }


    /* Tinted textures */

    /** Builder with the stew texture */
    private FluidTexture.Builder tintedStew(FluidObject<?> fluid) {
        return named(fluid, "food/stew");
    }

    /** Builder with the stone texture */
    private FluidTexture.Builder tintedStone(FluidObject<?> fluid) {
        return named(fluid, "molten/stone");
    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Fluid Texture Providers";
    }
}
