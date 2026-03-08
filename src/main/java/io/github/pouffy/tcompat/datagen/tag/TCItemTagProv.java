package io.github.pouffy.tcompat.datagen.tag;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.material.TCMaterials;
import io.github.pouffy.tcompat.common.material.TCWoods;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

import java.util.concurrent.CompletableFuture;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class TCItemTagProv extends ItemTagsProvider {
    public TCItemTagProv(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagProvider, TCompat.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        addPlanks();
        addMetals();
        addMisc();
        addForge();
    }

    private void addMisc() {

    }

    private void addForge() {
        this.tag(TCTags.Items.ZANITE_GEMS).addOptional(getResource("aether", "zanite_gemstone"));
        this.tag(TCTags.Items.ZANITE_BLOCKS).addOptional(getResource("aether", "zanite_block"));
        this.tag(TCTags.Items.ZANITE_ORES).addOptional(getResource("aether", "zanite_ore"));
        this.tag(TCTags.Items.SKYJADE_GEMS).addOptional(getResource("deep_aether", "skyjade"));
        this.tag(TCTags.Items.SKYJADE_BLOCKS).addOptional(getResource("deep_aether", "skyjade_block"));
        this.tag(TCTags.Items.SKYJADE_ORES).addOptional(getResource("deep_aether", "skyjade_ore"));
        this.tag(TCTags.Items.GRAVITITE_INGOTS).addOptional(getResource("aether_redux", "gravitite_ingot"));
        this.tag(TCTags.Items.GRAVITITE_BLOCKS).addOptional(getResource("aether_redux", "gravitite_block"));
        this.tag(TCTags.Items.GRAVITITE_ORES).addOptional(getResource("aether", "gravitite_ore"));
        this.tag(TCTags.Items.GRAVITITE_RAW_BLOCKS).addOptional(getResource("aether_redux", "raw_gravitite_block"));
        this.tag(TCTags.Items.GRAVITITE_RAW_ORES).addOptional(getResource("aether_redux", "raw_gravitite"));
        this.tag(TCTags.Items.VERIDIUM_INGOTS).addOptional(getResource("aether_redux", "veridium_ingot"));
        this.tag(TCTags.Items.VERIDIUM_NUGGETS).addOptional(getResource("aether_redux", "veridium_nugget"));
        this.tag(TCTags.Items.VERIDIUM_BLOCKS).addOptional(getResource("aether_redux", "veridium_block"));
        this.tag(TCTags.Items.VERIDIUM_ORES).addOptional(getResource("aether_redux", "veridium_ore"));
        this.tag(TCTags.Items.VERIDIUM_RAW_BLOCKS).addOptional(getResource("aether_redux", "raw_veridium_block"));
        this.tag(TCTags.Items.VERIDIUM_RAW_ORES).addOptional(getResource("aether_redux", "raw_veridium"));
        this.tag(TCTags.Items.REFINED_SENTRITE_INGOTS).addOptional(getResource("aether_redux", "refined_sentrite"));
        this.tag(TCTags.Items.REFINED_SENTRITE_NUGGETS).addOptional(getResource("aether_redux", "sentrite_chunk"));
        this.tag(TCTags.Items.REFINED_SENTRITE_BLOCKS).addOptional(getResource("aether_redux", "refined_sentrite_block"));
        this.tag(TCTags.Items.REFINED_SENTRITE_ORES).addOptional(getResource("aether_redux", "sentrite"));
        this.tag(TCTags.Items.STRATUS_BLOCKS).addOptional(getResource("deep_aether", "stratus_block"));
        this.tag(TCTags.Items.STRATUS_INGOTS).addOptional(getResource("deep_aether", "stratus_ingot"));
    }

    private void addMetals() {
        this.tag(TinkerTags.Items.ANVIL_METAL).addOptional(getResource("deep_aether", "stratus_block"));
    }

    private void addPlanks() {
        for (MaterialVariantId materialVariantId : TCMaterials.otbwgVariantRocks) {
            rockTagging(materialVariantId.getVariant(), "biomeswevegone");
        }
        for (MaterialVariantId materialVariantId : TCMaterials.aetherVariantRocks) {
            rockTagging(materialVariantId.getVariant(), "aether");
        }
        for (MaterialVariantId materialVariantId : TCMaterials.deepAetherVariantRocks) {
            rockTagging(materialVariantId.getVariant(), "deep_aether");
        }
        for (MaterialVariantId materialVariantId : TCMaterials.aetherReduxVariantRocks) {
            rockTagging(materialVariantId.getVariant(), "aether_redux");
        }
        for (TCWoods wood : TCWoods.values()) {
            String woodName = wood.name;
            for (String namespace : wood.getNamespaces()) {
                if (!wood.hasPlankRedirect()) {
                    ResourceLocation plankId = wood.getSpecialPlankId(namespace) != null ? wood.getSpecialPlankId(namespace) : getResource(namespace, "%s_planks".formatted(woodName));
                    this.tag(wood.plankTag()).addOptional(plankId);
                }

                this.tag(TinkerTags.Items.VARIANT_PLANKS)
                        .addOptionalTag(wood.plankTag());
                this.tag(wood.logTag())
                        .addOptionalTag(wood.externalLogTag(namespace));
                this.tag(TinkerTags.Items.VARIANT_LOGS)
                        .addOptionalTag(wood.logTag());
            }
        }
        this.tag(TinkerTags.Items.PLANKLIKE).addOptionalTag(itemTag("aether:planks_crafting"));
    }

    private void rockTagging(String rockName, String namespace) {
        this.tag(TinkerTags.Items.WORKSTATION_ROCK)
                .addOptional(getResource(namespace, rockName));
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, getResource(name));
    }
}
