package io.github.pouffy.tcompat.datagen;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.datagen.fluid.TCFluidTextureProv;
import io.github.pouffy.tcompat.datagen.fluid.TCFluidTooltipProv;
import io.github.pouffy.tcompat.datagen.tag.TCBlockTagProv;
import io.github.pouffy.tcompat.datagen.tag.TCFluidTagProv;
import io.github.pouffy.tcompat.datagen.tag.TCItemTagProv;
import io.github.pouffy.tcompat.datagen.tag.TCMaterialTagProv;
import io.github.pouffy.tcompat.datagen.tinkers.material.*;
import io.github.pouffy.tcompat.datagen.tinkers.modifier.TCModifierProv;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCMaterialRecipeProv;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCModifierRecipeProv;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCSmelteryRecipeProv;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import slimeknights.tconstruct.fluids.data.FluidBlockstateModelProvider;
import slimeknights.tconstruct.fluids.data.FluidBucketModelProvider;
import slimeknights.tconstruct.library.client.data.material.MaterialPaletteDebugGenerator;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;

import java.util.concurrent.CompletableFuture;

public class TCDataGenerator {

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        boolean server = event.includeServer();
        boolean client = event.includeClient();

        TCBlockTagProv blockTags = new TCBlockTagProv(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(server, blockTags);
        generator.addProvider(server, new TCItemTagProv(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(server, new TCFluidTagProv(packOutput, lookupProvider, existingFileHelper));


        TCMaterialDataProv materials = new TCMaterialDataProv(packOutput);
        TCMaterialSpriteProv materialSprites = new TCMaterialSpriteProv();

        generator.addProvider(client, new TCMaterialRenderInfoProv(packOutput, materialSprites, existingFileHelper));
        generator.addProvider(client, new MaterialPaletteDebugGenerator(packOutput, TCompat.MOD_ID, materialSprites));
        generator.addProvider(client, new MaterialPartTextureGenerator(packOutput, existingFileHelper, new TinkerPartSpriteProvider(), materialSprites));
        generator.addProvider(server, new TCModifierProv(packOutput));
        generator.addProvider(server, new TCModifierRecipeProv(packOutput));

        generator.addProvider(server, new TCMaterialRecipeProv(packOutput));
        generator.addProvider(server, new TCSmelteryRecipeProv(packOutput));
        generator.addProvider(server, materials);
        generator.addProvider(server, new TCMaterialStatsProv(packOutput, materials));
        generator.addProvider(server, new TCMaterialTraitsProv(packOutput, materials));
        generator.addProvider(server, new TCMaterialTagProv(packOutput, existingFileHelper));

        generator.addProvider(client, new TCFluidTooltipProv(packOutput));
        generator.addProvider(client, new TCFluidTextureProv(packOutput));
        generator.addProvider(client, new FluidBucketModelProvider(packOutput, TCompat.MOD_ID));
        generator.addProvider(client, new FluidBlockstateModelProvider(packOutput, TCompat.MOD_ID));
    }
}
