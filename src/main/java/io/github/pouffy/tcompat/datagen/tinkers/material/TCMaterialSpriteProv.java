package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToSpriteTransformer;
import slimeknights.tconstruct.library.client.data.spritetransformer.ISpriteTransformer;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;
import slimeknights.tconstruct.tools.stats.PlatingMaterialStats;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider.WOOD;

@SuppressWarnings({"UnusedReturnValue", "unused"})
@MethodsReturnNonnullByDefault
public class TCMaterialSpriteProv extends AbstractMaterialSpriteProvider {
    @Override
    public String getName() {
        return "Tinker's Compatability Material Sprites";
    }

    @Override
    protected void addAllMaterials() {
        for (MaterialBuilder builder : MaterialBuilder.materialBuilders) {
            TCompat.LOGGER.info("Adding sprite info for material: {}", builder.getId());
            var sBuilder = buildMaterial(builder.getId());
            var fallbacks = builder.getRenderInfo().getFallbacks();
            if (!Arrays.equals(fallbacks, new String[0])) {
                sBuilder.fallbacks(fallbacks);
            }
            var stats = builder.getSpriteInfo().getStatTypes().build().toArray(new MaterialStatsId[0]);
            if (!Arrays.equals(stats, new MaterialStatsId[0])) {
                sBuilder.statType(stats);
            }
            var transformer = builder.getSpriteInfo().getTransformer();
            if (transformer != null) {
                sBuilder.transformer(transformer);
            } else {
                TCompat.LOGGER.warn("Missing sprite transformer for material: {}! This will cause render info issues!", builder.getId());
            }
            sBuilder.variant(builder.isVariant());
        }
    }

    public static ISpriteTransformer transformerFromSprite(ResourceLocation texture, int frames, int highlightColor) {
        GreyToSpriteTransformer.Builder builder = GreyToSpriteTransformer.builderFromBlack();
        builder.addTexture( 63, texture, 0xFF404040)
                .addTexture(102, texture, 0xFF808080)
                .addTexture(140, texture);
        if (highlightColor != 0) {
            builder.addTexture(216, texture).addARGB(255, highlightColor);
        }
        if (frames > 1) {
            return builder.animated(texture, frames);
        }
        return builder.build();
    }

    public static ISpriteTransformer transformerFromSprite(ResourceLocation border, ResourceLocation base, ResourceLocation highlight, int frames) {
        GreyToSpriteTransformer.Builder builder = GreyToSpriteTransformer.builderFromBlack();
        builder.addTexture( 63, border,    0xFFC8C8C8).addTexture(102, border)
                .addTexture(140, base,      0xFFE1E1E1).addTexture(178, base)
                .addTexture(216, highlight, 0xFFE1E1E1).addTexture(255, highlight);
        if (frames > 1) {
            return builder.animated(base, frames);
        }
        return builder.build();
    }

    public static ISpriteTransformer complexTransformer(String name, int frames, int c216, int c255) {
        GreyToSpriteTransformer.Builder builder = GreyToSpriteTransformer.builderFromBlack();
        Function<String, ResourceLocation> resolver = (n) -> TCompat.getResource("generator/complex/" + n);
        builder.addTexture(63, resolver.apply(name + "_63"))
                .addTexture(102, resolver.apply(name + "_102"))
                .addTexture(140, resolver.apply(name + "_140"))
                .addTexture(178, resolver.apply(name + "_178"))
                .addARGB(216, c216)
                .addARGB(255, c255);
        if (frames > 1) {
            return builder.animated(resolver.apply(name + "_102"), frames);
        }
        return builder.build();
    }

    private MaterialSpriteInfoBuilder buildPlanks(MaterialVariantId material, int c63, int c102, int c140, int c178, int c216, int c234, int c255) {
        return buildPlanks(material).colorMapper(GreyToColorMapping.builderFromBlack()
                .addARGB(63, c63)
                .addARGB(102, c102)
                .addARGB(140, c140)
                .addARGB(178, c178)
                .addARGB(216, c216)
                .addARGB(234, c234)
                .addARGB(255, c255)
                .build());
    }

    private MaterialSpriteInfoBuilder buildRock(MaterialVariantId material, int c63, int c102, int c140, int c178, int c216, int c255) {
        return buildRock(material).colorMapper(GreyToColorMapping.builderFromBlack()
                .addARGB(63, c63)
                .addARGB(102, c102)
                .addARGB(140, c140)
                .addARGB(178, c178)
                .addARGB(216, c216)
                .addARGB(255, c255)
                .build());
    }

    private MaterialSpriteInfoBuilder buildScales(MaterialVariantId material, int c63, int c102, int c140, int c178, int c216, int c255) {
        return buildScales(material).colorMapper(GreyToColorMapping.builderFromBlack()
                .addARGB(63, c63)
                .addARGB(102, c102)
                .addARGB(140, c140)
                .addARGB(178, c178)
                .addARGB(216, c216)
                .addARGB(255, c255)
                .build());
    }

    private MaterialSpriteInfoBuilder buildBindingOnly(MaterialId material, int c63, int c102, int c140, int c178, int c216, int c255, boolean bowstring, String... fallbacks) {
        Collection<MaterialStatsId> stats = new ArrayList<>(List.of(StatlessMaterialStats.BINDING.getIdentifier()));
        if (bowstring) stats.add(StatlessMaterialStats.BOWSTRING.getIdentifier());
        return buildGeneric(material, c63, c102, c140, c178, c216, c255, stats, fallbacks);
    }
    private MaterialSpriteInfoBuilder buildBindingOnly(MaterialId material, int c63, int c102, int c140, int c178, int c216, int c234, int c255, boolean bowstring, String... fallbacks) {
        Collection<MaterialStatsId> stats = new ArrayList<>(List.of(StatlessMaterialStats.BINDING.getIdentifier()));
        if (bowstring) stats.add(StatlessMaterialStats.BOWSTRING.getIdentifier());
        return buildGeneric(material, c63, c102, c140, c178, c216, c234, c255, stats, fallbacks);
    }

    private MaterialSpriteInfoBuilder buildGeneric(MaterialId material, int c63, int c102, int c140, int c178, int c216, int c234, int c255, Collection<MaterialStatsId> stats, String... fallbacks) {
        MaterialSpriteInfoBuilder builder = buildBlankMaterial(material, fallbacks).repairKit();
        for (MaterialStatsId statId : stats) {
            builder.statType(statId);
        }
        return builder.colorMapper(GreyToColorMapping.builderFromBlack()
                .addARGB(63, c63)
                .addARGB(102, c102)
                .addARGB(140, c140)
                .addARGB(178, c178)
                .addARGB(216, c216)
                .addARGB(234, c234)
                .addARGB(255, c255)
                .build());
    }
    private MaterialSpriteInfoBuilder buildGeneric(MaterialId material, int c63, int c102, int c140, int c178, int c216, int c255, Collection<MaterialStatsId> stats, String... fallbacks) {
        MaterialSpriteInfoBuilder builder = buildBlankMaterial(material, fallbacks).repairKit();
        for (MaterialStatsId statId : stats) {
            builder.statType(statId);
        }
        return builder.colorMapper(GreyToColorMapping.builderFromBlack()
                .addARGB(63, c63)
                .addARGB(102, c102)
                .addARGB(140, c140)
                .addARGB(178, c178)
                .addARGB(216, c216)
                .addARGB(255, c255)
                .build());
    }

    /** Adds a plank type as a wood variant */
    private MaterialSpriteInfoBuilder buildPlanks(MaterialVariantId material) {
        return buildBlankVariant(material, "wood", "stick", "primitive").meleeHarvest().ranged().shieldCore().statType(WOOD);
    }

    private MaterialSpriteInfoBuilder buildRock(MaterialVariantId material) {
        return buildBlankVariant(material, "rock").meleeHarvest().variant();
    }

    private MaterialSpriteInfoBuilder buildScales(MaterialVariantId material) {
        return buildBlankVariant(material, "scales", "metal").armor().variant();
    }

    private MaterialSpriteInfoBuilder buildBlankVariant(MaterialVariantId materialVariant, String... fallbacks) {
        return buildMaterial(materialVariant).fallbacks(fallbacks).variant();
    }

    private MaterialSpriteInfoBuilder buildBlankMaterial(MaterialId material, String... fallbacks) {
        return buildMaterial(material).fallbacks(fallbacks);
    }

    private Collection<MaterialStatsId> addStats(MaterialStatsId... statsIds) {
        Collection<MaterialStatsId> stats = new ArrayList<>(List.of());
        stats.addAll(Arrays.asList(statsIds));
        return stats;
    }

    public Collection<MaterialStatsId> plating(MaterialStatsId... extraStats) {
        Collection<MaterialStatsId> stats = new ArrayList<>(List.of());
        stats.add(TinkerPartSpriteProvider.ARMOR_PLATING);
        for (MaterialStatType<?> type : PlatingMaterialStats.TYPES) {
            stats.add(type.getId());
        }
        stats.addAll(Arrays.asList(extraStats));
        stats.add(StatlessMaterialStats.REPAIR_KIT.getIdentifier());
        return stats;
    }
}
