package com.pouffydev.tcompat.data;

import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.data.material.*;
import com.pouffydev.tcompat.data.modifier.TComModifierRecipeProv;
import com.pouffydev.tcompat.data.tag.TComBlockTagProv;
import com.pouffydev.tcompat.data.tag.TComItemTagProv;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import slimeknights.tconstruct.common.data.tags.BlockTagProvider;
import slimeknights.tconstruct.common.data.tags.ItemTagProvider;
import slimeknights.tconstruct.library.client.data.material.MaterialPaletteDebugGenerator;

import java.util.concurrent.CompletableFuture;

public class TComDataGen {

    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        boolean server = event.includeServer();
        boolean client = event.includeClient();

        TComBlockTagProv blockTags = new TComBlockTagProv(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(server, blockTags);
        generator.addProvider(server, new TComItemTagProv(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));

        TComMaterialDataProv materials = new TComMaterialDataProv(packOutput);
        TComMaterialSpriteProv materialSprites = new TComMaterialSpriteProv();

        generator.addProvider(client, new TComMaterialRenderInfoProv(packOutput, materialSprites, existingFileHelper));
        generator.addProvider(client, new MaterialPaletteDebugGenerator(packOutput, TCompat.MOD_ID, materialSprites));
        generator.addProvider(server, new TComModifierRecipeProv(packOutput, lookupProvider));

        generator.addProvider(server, new TComMaterialRecipeProv(packOutput, lookupProvider));
        generator.addProvider(server, materials);
        generator.addProvider(server, new TComMaterialStatsProv(packOutput, materials));
        generator.addProvider(server, new TComMaterialTraitsProv(packOutput, materials));
    }
}
