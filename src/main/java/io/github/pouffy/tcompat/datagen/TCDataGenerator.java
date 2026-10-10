package io.github.pouffy.tcompat.datagen;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.datagen.client.TCItemModelProv;
import io.github.pouffy.tcompat.datagen.client.TCToolItemModelProv;
import io.github.pouffy.tcompat.datagen.fluid.TCFluidTooltipProv;
import io.github.pouffy.tcompat.datagen.lang.TCLangProv;
import io.github.pouffy.tcompat.datagen.mantle.MantleColorsProvider;
import io.github.pouffy.tcompat.datagen.tag.*;
import io.github.pouffy.tcompat.datagen.tinkers.material.*;
import io.github.pouffy.tcompat.datagen.tinkers.modifier.TCModifierProv;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.*;
import io.github.pouffy.tcompat.datagen.tinkers.tool.TCPartSpriteProvider;
import io.github.pouffy.tcompat.datagen.tinkers.tool.TCStationLayoutProv;
import io.github.pouffy.tcompat.datagen.tinkers.tool.TCToolDefinitionProv;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.material.GeneratorPartTextureJsonGenerator;
import slimeknights.tconstruct.library.client.data.material.MaterialPaletteDebugGenerator;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import slimeknights.tconstruct.tools.data.sprite.TinkerMaterialSpriteProvider;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

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
        generator.addProvider(server, new TCGameEventTagProv(packOutput, lookupProvider, existingFileHelper));

        TCMaterialDataProv materials = new TCMaterialDataProv(packOutput);
        TCMaterialSpriteProv materialSprites = new TCMaterialSpriteProv();
        TCPartSpriteProvider partSprites = new TCPartSpriteProvider();

        generator.addProvider(true, new TCLangProv(packOutput));

        generator.addProvider(client, new TCToolItemModelProv(packOutput, existingFileHelper));
        generator.addProvider(client, new TCItemModelProv(packOutput, existingFileHelper));

        generator.addProvider(client, new TCMaterialRenderInfoProv(packOutput, materialSprites, existingFileHelper));
        generator.addProvider(client, new MantleColorsProvider(packOutput));
        generator.addProvider(client, new MaterialPaletteDebugGenerator(packOutput, TCompat.MOD_ID, materialSprites));
        //For Tinkers' Tools
        generator.addProvider(client, new MaterialPartTextureGenerator(packOutput, existingFileHelper, new TinkerPartSpriteProvider(), materialSprites));
        //For TCompat Tools
        generator.addProvider(client, new MaterialPartTextureGenerator(packOutput, existingFileHelper, partSprites, GlobalInit.getStatOverrides(), materialSprites, new TinkerMaterialSpriteProvider()));
        // AE2: head sprites for Tinkers' nether quartz, into the pack only added when AE2 is installed (see AE2Init)
        PackOutput ae2QuartzPack = new PackOutput(packOutput.getOutputFolder().resolve("packs/ae2_quartz"));
        AbstractMaterialSpriteProvider quartzHeads = new AbstractMaterialSpriteProvider() {
            @Override
            public String getName() {
                return "Tinker's Compatability AE2 Quartz Head Sprites";
            }

            @Override
            protected void addAllMaterials() {
                // same palette as Tinkers' quartz arrow heads
                buildMaterial(MaterialIds.quartz)
                        .statType(HeadMaterialStats.ID)
                        .fallbacks("crystal", "rock")
                        .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF6F5D5A).addARGB(102, 0xFF897B73).addARGB(140, 0xFFB6A48E).addARGB(178, 0xFFD4CABA).addARGB(216, 0xFFEAE5DE).addARGB(255, 0xFFF7F5F2).build());
            }
        };
        generator.addProvider(client, new MaterialPartTextureGenerator(ae2QuartzPack, existingFileHelper, new TinkerPartSpriteProvider(), quartzHeads));
        generator.addProvider(client, new MaterialPartTextureGenerator(ae2QuartzPack, existingFileHelper, partSprites, GlobalInit.getStatOverrides(), quartzHeads));
        generator.addProvider(client, new GeneratorPartTextureJsonGenerator(packOutput, TCompat.MOD_ID, partSprites));
        generator.addProvider(client, new GeneratorPartTextureJsonGenerator(packOutput, TConstruct.MOD_ID, partSprites));
        generator.addProvider(server, new TCModifierProv(packOutput));
        generator.addProvider(server, new TCModifierRecipeProv(packOutput));

        generator.addProvider(server, new TCToolDefinitionProv(packOutput));
        generator.addProvider(server, new TCStationLayoutProv(packOutput));

        generator.addProvider(server, new TCFluidTransferProv(packOutput));
        generator.addProvider(client, new TCFluidTooltipProv(packOutput));
        generator.addProvider(server, new TCMaterialRecipeProv(packOutput));
        generator.addProvider(server, new TCSmelteryRecipeProv(packOutput));
        generator.addProvider(server, new TCToolRecipeProv(packOutput));
        generator.addProvider(server, materials);
        generator.addProvider(server, new TCMaterialStatsProv(packOutput, materials));
        generator.addProvider(server, new TCMaterialTraitsProv(packOutput, materials));
        generator.addProvider(server, new TCMaterialTagProv(packOutput, existingFileHelper));
        generator.addProvider(server, new TCModifierTagProv(packOutput, existingFileHelper));
    }
}
