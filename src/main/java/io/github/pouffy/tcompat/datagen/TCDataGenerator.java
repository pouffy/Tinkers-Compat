package io.github.pouffy.tcompat.datagen;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.datagen.client.TCItemModelProv;
import io.github.pouffy.tcompat.datagen.client.TCToolItemModelProv;
import io.github.pouffy.tcompat.datagen.fluid.TCFluidTextureProv;
import io.github.pouffy.tcompat.datagen.fluid.TCFluidTooltipProv;
import io.github.pouffy.tcompat.datagen.lang.TCLangProv;
import io.github.pouffy.tcompat.datagen.tag.*;
import io.github.pouffy.tcompat.datagen.tinkers.material.*;
import io.github.pouffy.tcompat.datagen.tinkers.modifier.TCModifierProv;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCMaterialRecipeProv;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCModifierRecipeProv;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCSmelteryRecipeProv;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCToolRecipeProv;
import io.github.pouffy.tcompat.datagen.tinkers.tool.TCPartSpriteProvider;
import io.github.pouffy.tcompat.datagen.tinkers.tool.TCStationLayoutProv;
import io.github.pouffy.tcompat.datagen.tinkers.tool.TCToolDefinitionProv;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.fluids.data.FluidBlockstateModelProvider;
import slimeknights.tconstruct.fluids.data.FluidBucketModelProvider;
import slimeknights.tconstruct.library.client.data.material.GeneratorPartTextureJsonGenerator;
import slimeknights.tconstruct.library.client.data.material.MaterialPaletteDebugGenerator;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.tools.data.sprite.TinkerMaterialSpriteProvider;
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
        generator.addProvider(server, new TCEntityTagProv(packOutput, lookupProvider, existingFileHelper));


        TCMaterialDataProv materials = new TCMaterialDataProv(packOutput);
        TCMaterialSpriteProv materialSprites = new TCMaterialSpriteProv();
        TCPartSpriteProvider partSprites = new TCPartSpriteProvider();

        generator.addProvider(true, new TCLangProv(packOutput));

        generator.addProvider(client, new TCToolItemModelProv(packOutput, existingFileHelper));
        generator.addProvider(client, new TCItemModelProv(packOutput, existingFileHelper));

        generator.addProvider(client, new TCMaterialRenderInfoProv(packOutput, materialSprites, existingFileHelper));
        generator.addProvider(client, new MaterialPaletteDebugGenerator(packOutput, TCompat.MOD_ID, materialSprites));
        //For Tinkers' Tools
        generator.addProvider(client, new MaterialPartTextureGenerator(packOutput, existingFileHelper, new TinkerPartSpriteProvider(), materialSprites));
        //For TCompat Tools
        generator.addProvider(client, new MaterialPartTextureGenerator(packOutput, existingFileHelper, partSprites, materialSprites, new TinkerMaterialSpriteProvider()));
        generator.addProvider(client, new GeneratorPartTextureJsonGenerator(packOutput, TCompat.MOD_ID, partSprites));
        generator.addProvider(client, new GeneratorPartTextureJsonGenerator(packOutput, TConstruct.MOD_ID, partSprites));
        generator.addProvider(server, new TCModifierProv(packOutput));
        generator.addProvider(server, new TCModifierRecipeProv(packOutput));

        generator.addProvider(server, new TCToolDefinitionProv(packOutput));
        generator.addProvider(server, new TCStationLayoutProv(packOutput));

        generator.addProvider(server, new TCMaterialRecipeProv(packOutput));
        generator.addProvider(server, new TCSmelteryRecipeProv(packOutput));
        generator.addProvider(server, new TCToolRecipeProv(packOutput));
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
