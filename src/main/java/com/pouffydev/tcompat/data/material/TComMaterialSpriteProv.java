package com.pouffydev.tcompat.data.material;

import com.pouffydev.tcompat.material.TComMaterialIds;
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
import slimeknights.tconstruct.tools.stats.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.pouffydev.tcompat.TCompat.getResource;
import static slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider.WOOD;

public class TComMaterialSpriteProv extends AbstractMaterialSpriteProvider {
    @Override
    public String getName() {
        return "Tinker's Compatability Material Sprites";
    }

    @Override
    protected void addAllMaterials() {

        // Planks
        buildPlanks(TComMaterialIds.aspen, 0xFF9E644D, 0xFFA56C56, 0xFFAD755F, 0xFFC18260, 0xFFD19373, 0xFFDDA289, 0xFFDDAD8D);
        buildPlanks(TComMaterialIds.baobab, 0xFF52493E, 0xFF675C48, 0xFF77694D, 0xFF87764E, 0xFF948154, 0xFFA48F5D, 0xFFB3995B);
        buildPlanks(TComMaterialIds.blueEnchanted, 0xFF26367D, 0xFF2F4089, 0xFF3C4D97, 0xFF4757A2, 0xFF5666B0, 0xFF6574BB, 0xFF7180C9);
        buildPlanks(TComMaterialIds.cika, 0xFF432C27, 0xFF57372F, 0xFF643D35, 0xFF714439, 0xFF7F4B3C, 0xFF905344, 0xFFA15B4B);
        buildPlanks(TComMaterialIds.cypress, 0xFF594E4A, 0xFF6A614E, 0xFF756D5C, 0xFF807B61, 0xFF8F885C, 0xFF99906D, 0xFFAD9E76);
        buildPlanks(TComMaterialIds.ebony, 0xFF121212, 0xFF171717, 0xFF1A1A1A, 0xFF1F1F1F, 0xFF212121, 0xFF2E2E2E, 0xFF343232);
        buildPlanks(TComMaterialIds.fir, 0xFF453326, 0xFF57402E, 0xFF644835, 0xFF794D3A, 0xFF8B6349, 0xFF9F7553, 0xFFB28F6C);
        buildPlanks(TComMaterialIds.florus, 0xFF28511E, 0xFF305B26, 0xFF39672E, 0xFF44592A, 0xFF546B39, 0xFF607744, 0xFF768948);
        buildPlanks(TComMaterialIds.greenEnchanted, 0xFF1B6728, 0xFF237433, 0xFF2D7F3D, 0xFF3A8D4A, 0xFF479956, 0xFF54A564, 0xFF5EB26F);
        buildPlanks(TComMaterialIds.holly, 0xFF7B655B, 0xFF876F64, 0xFF92786C, 0xFFB48769, 0xFFBF977D, 0xFFDDB4A2, 0xFFDBC1AD);
        buildPlanks(TComMaterialIds.ironwood, 0xFF69615C, 0xFF6E6763, 0xFF756E6A, 0xFF7D7673, 0xFF84807D, 0xFF9E9C99, 0xFFA7A5A2);
        buildPlanks(TComMaterialIds.jacaranda, 0xFF5B3E3F, 0xFF714748, 0xFF865456, 0xFF916964, 0xFF9F716D, 0xFFAB8482, 0xFFC19B9D);
        buildPlanks(TComMaterialIds.mahogany, 0xFF612C4A, 0xFF6C3655, 0xFF723A5A, 0xFF854E6D, 0xFF8C5775, 0xFF95607E, 0xFF9E6A88);
        buildPlanks(TComMaterialIds.maple, 0xFF413C38, 0xFF4F4842, 0xFF58504B, 0xFF695952, 0xFF766A62, 0xFF84786F, 0xFF978D83);
        buildPlanks(TComMaterialIds.palm, 0xFF4D483D, 0xFF685B45, 0xFF79694E, 0xFF877E68, 0xFF9B8D6F, 0xFFA89B7A, 0xFFB0A792);
        buildPlanks(TComMaterialIds.paloVerde, 0xFFC4B18C, 0xFFD1BF98, 0xFFD7C59E, 0xFFE0CFA5, 0xFFE8DAAF, 0xFFF3E4B9, 0xFFF5E6BC);
        buildPlanks(TComMaterialIds.pine, 0xFF635B4C, 0xFF6E6754, 0xFF7A7362, 0xFF998E70, 0xFFA69C81, 0xFFB2A78A, 0xFFC5B99A);
        buildMaterial(TComMaterialIds.rainbowEucalyptus)
                .variant().meleeHarvest().ranged().shieldCore().statType(WOOD)
                .fallbacks("wood", "stick", "primitive")
                .transformer(transformerFromSprite(getResource("generator/rainbow_eucalyptus"), 1, 0));
        buildPlanks(TComMaterialIds.redwood, 0xFF582D2D, 0xFF6C3737, 0xFF7B3D3D, 0xFF974949, 0xFFAA5050, 0xFFBD5656, 0xFFCB5757);
        buildPlanks(TComMaterialIds.sakura, 0xFF501B25, 0xFF601F29, 0xFF702935, 0xFF742941, 0xFF822C3A, 0xFF922E3F, 0xFFA13043);
        buildPlanks(TComMaterialIds.skyris, 0xFF3B7087, 0xFF457B92, 0xFF4F859C, 0xFF6798AE, 0xFF74A2B7, 0xFF84AFC2, 0xFF95BCCB);
        buildPlanks(TComMaterialIds.whiteMangrove, 0xFF8A8585, 0xFF9A9898, 0xFFA1A1A1, 0xFFB5B0B0, 0xFFBEBBBB, 0xFFCCCCCC, 0xFFD4D4D4);
        buildPlanks(TComMaterialIds.willow, 0xFF293522, 0xFF3B422B, 0xFF495133, 0xFF515C38, 0xFF5B683E, 0xFF676D44, 0xFF717947);
        buildPlanks(TComMaterialIds.witchHazel, 0xFF183A26, 0xFF1D442D, 0xFF214F34, 0xFF225337, 0xFF286240, 0xFF31774D, 0xFF3B8C5B);
        buildPlanks(TComMaterialIds.zelkova, 0xFF4F2713, 0xFF592F17, 0xFF66351A, 0xFF824A23, 0xFF96582F, 0xFFAB6336, 0xFFBF6D36);

        buildMaterial(TComMaterialIds.aetherWood)
                .meleeHarvest().ranged().shieldCore()
                .fallbacks("wood", "stick", "primitive")
                .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF333122).addARGB(102, 0xFF403D2A).addARGB(140, 0xFF4F4D36).addARGB(178, 0xFF5C5B41).addARGB(216, 0xFF63634A).addARGB(255, 0xFF7B7E61).build());
        buildPlanks(TComMaterialIds.skyroot, 0xFF333122, 0xFF403D2A, 0xFF4F4D36, 0xFF5C5B41, 0xFF63634A, 0xFF737558, 0xFF7B7E61);
        buildPlanks(TComMaterialIds.roseroot, 0xFFA37185, 0xFFB48090, 0xFFCC9CA5, 0xFFDFA7AF, 0xFFF4B6B8, 0xFFFDBDBE, 0xFFFED7D0);
        buildPlanks(TComMaterialIds.yagroot, 0xFF313140, 0xFF3C3B4A, 0xFF4B475B, 0xFF564D67, 0xFF615473, 0xFF67587A, 0xFF72637D);
        buildPlanks(TComMaterialIds.cruderoot, 0xFF4F5266, 0xFF5E6075, 0xFF71708E, 0xFF7E79A0, 0xFF8E84B3, 0xFF9689BD, 0xFFA99AC1);
        buildPlanks(TComMaterialIds.conberry, 0xFF544338, 0xFF5F4A3F, 0xFF705C4C, 0xFF826653, 0xFF99765D, 0xFFA68064, 0xFFAA886F);
        buildPlanks(TComMaterialIds.sunroot, 0xFF8E6B65, 0xFF9C7F77, 0xFFB39C8D, 0xFFC6AB98, 0xFFD9C2A3, 0xFFE2CCA9, 0xFFE9DABA);
        buildPlanks(TComMaterialIds.blightwillow, 0xFF4B694E, 0xFF5E805C, 0xFF6C8C63, 0xFF7F9F74, 0xFF8FAC82, 0xFF9FBA90, 0xFFA8C198);
        buildPlanks(TComMaterialIds.fieldsproot, 0xFF824241, 0xFF9A514E, 0xFFAF615B, 0xFFBF7069, 0xFFC97B72, 0xFFD4847B, 0xFFDB8B82);
        buildPlanks(TComMaterialIds.cloudcap, 0xFF191A2B, 0xFF212338, 0xFF292C45, 0xFF323654, 0xFF3B3F60, 0xFF444A72, 0xFF4B537D);
        buildPlanks(TComMaterialIds.jellyshroom, 0xFF373E4F, 0xFF414959, 0xFF4B5364, 0xFF555D6B, 0xFF646B76, 0xFF737982, 0xFF81868E);
        buildPlanks(TComMaterialIds.crystal, 0xFF50607A, 0xFF5F728E, 0xFF6D84A3, 0xFF788FAB, 0xFF839CBA, 0xFF8DA6C1, 0xFF95AFC6);
        buildPlanks(TComMaterialIds.glacia, 0xFF524D45, 0xFF645F54, 0xFF787365, 0xFF848072, 0xFF938F7E, 0xFF9E9A89, 0xFFA7A18F);

        // Rock
        buildRock(TComMaterialIds.redRock, 0xFF7F4026, 0xFF904727, 0xFF95522F, 0xFFA95936, 0xFFAD633E, 0xFFB36D44);
        buildRock(TComMaterialIds.dacite, 0xFF663E2F, 0xFF6D4536, 0xFF7F5646, 0xFF926251, 0xFF9F6B58, 0xFFA97764);

        buildRock(TComMaterialIds.holystone, 0xFF808080, 0xFF8F8F8F, 0xFF9C9C9C, 0xFFADADAD, 0xFFCCCCCC, 0xFFE2E2E2);
        buildRock(TComMaterialIds.aseterite, 0xFF746772, 0xFF7F717D, 0xFF897A87, 0xFF938595, 0xFFA296A1, 0xFFB9ADB8);
        buildRock(TComMaterialIds.clorite, 0xFF415964, 0xFF4D6A74, 0xFF5B7C88, 0xFF699AA1, 0xFF7DAFAE, 0xFF95C9CB);
        buildRock(TComMaterialIds.divinite, 0xFF8B7149, 0xFF987E55, 0xFFAA8F65, 0xFFBAA170, 0xFFCDB285, 0xFFD8C699);
        buildRock(TComMaterialIds.driftshale, 0xFFAA9A5D, 0xFFBEB06C, 0xFFCEC277, 0xFFD9CE81, 0xFFDFDA95, 0xFFE3E2A8);

        ResourceLocation baseTexture = getResource("generator/fiery");
        ResourceLocation highlightTexture = getResource("generator/fiery_highlight");
        ResourceLocation borderTexture = getResource("generator/fiery_border");

        buildMaterial(TComMaterialIds.twilightWood)
                .meleeHarvest().ranged().shieldCore()
                .fallbacks("wood", "stick", "primitive")
                .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF614c34).addARGB(102, 0xFF69553c).addARGB(140, 0xFF7d6144).addARGB(178, 0xFF8d7150).addARGB(216, 0xFFa18159).addARGB(255, 0xFFaa8559).build());
        buildPlanks(TComMaterialIds.twilightOak, 0xFF614c34, 0xFF69553c, 0xFF7d6144, 0xFF8d7150, 0xFF997d5d, 0xFFa18159, 0xFFaa8559);
        buildPlanks(TComMaterialIds.canopy, 0xFF331e11, 0xFF402b15, 0xFF492f1e, 0xFF4d3826, 0xFF563c2b, 0xFF5f452b, 0xFF674d2f);
        buildPlanks(TComMaterialIds.darkwood, 0xFF523820, 0xFF6a4429, 0xFF734829, 0xFF835931, 0xFF945d39, 0xFFac6539, 0xFFb46d39);
        buildPlanks(TComMaterialIds.mangrove, 0xFF8f7553, 0xFF9b8564, 0xFFa5916d, 0xFFc19871, 0xFFcaa87d, 0xFFd6b484, 0xFFcfbf8d);
        buildPlanks(TComMaterialIds.transwood, 0xFF605654, 0xFF6e6256, 0xFF7f6e5c, 0xFF907f6e, 0xFF968b79, 0xFFa29073, 0xFFad9c80);
        buildPlanks(TComMaterialIds.timewood, 0xFF4b301c, 0xFF563528, 0xFF62412e, 0xFF6e4739, 0xFF79513f, 0xFF85563f, 0xFF8b5c3f);
        buildPlanks(TComMaterialIds.minewood, 0xFF7f7362, 0xFF857968, 0xFF968573, 0xFFa2907f, 0xFFad9c85, 0xFFb3a28b, 0xFFb9a88b);
        buildPlanks(TComMaterialIds.sortingwood, 0xFF381f0f, 0xFF402814, 0xFF4c2c18, 0xFF503420, 0xFF5d3c24, 0xFF673f1f, 0xFF6d4424);
        buildPlanks(TComMaterialIds.towerwood, 0xFF563920, 0xFF684628, 0xFF704c2e, 0xFF815934, 0xFF955d38, 0xFFa8673f, 0xFFb36d3c);


        buildMaterial(TComMaterialIds.fiery)
                .meleeHarvest().ranged().armor()
                .fallbacks("metal")
                .transformer(transformerFromSprite(borderTexture, baseTexture, highlightTexture, 2));

        //buildGeneric(TComMaterialIds.fiery, 0xFFFFD83A, 0xFFDA7600, 0xFF0B0507, 0xFF191313, 0xFF3A2424, 0xFF413333, addStats()).meleeHarvest();
        buildGeneric(TComMaterialIds.knightmetal, 0xFF6f4377, 0xFF947f9f, 0xFFaaaaaa, 0xFFb4c9aa, 0xFFd8e7c0, 0xFFf3ffce, 0xFFffffff, addStats(), "metal").meleeHarvest();
        buildGeneric(TComMaterialIds.nagascale, 0xFF0C1708, 0xFF172911, 0xFF1B4B4E, 0xFF254B29, 0xFF325D25, 0xFF575E1C, addStats(HeadMaterialStats.ID));
        buildGeneric(TComMaterialIds.ravenFeather, 0xFF141c27, 0xFF1d252f, 0xFF2b343d, 0xFF3e444d, 0xFF474c52, 0xFF68696c, 0xFF878789, addStats(StatlessMaterialStats.BOWSTRING.getIdentifier()));
        buildGeneric(TComMaterialIds.steeleaf, 0xFF081608, 0xFF1A2B11, 0xFF27401D, 0xFF345E27, 0xFF569243, 0xFF8CB05A, addStats()).meleeHarvest();

        buildGeneric(TComMaterialIds.zanite, 0xFF1c0d3b, 0xFF350f6c, 0xFF5b22b0, 0xFF8a4ee4, 0xFFaf7ff6, 0xFFbf9cf4, 0xFFe1cdff, plating(HeadMaterialStats.ID), "gem", "metal");
        buildGeneric(TComMaterialIds.skyjade, 0xFF434e34, 0xFF4e6741, 0xFF729752, 0xFF8cb955, 0xFF9ada5b, 0xFFb2e865, 0xFFd1f397, plating(HeadMaterialStats.ID), "gem", "metal");
        buildGeneric(TComMaterialIds.gravitite, 0xFF390c35, 0xFF5c1256, 0xFFb643a0, 0xFFda67d0, 0xFFf195ef, 0xFFffcbfd, addStats(), "metal").armor().ranged().meleeHarvest();
        buildGeneric(TComMaterialIds.veridium, 0xFF0e193b, 0xFF142958, 0xFF32578c, 0xFF5a90bd, 0xFF7fbedc, 0xFFb9edfb, addStats(), "metal").armor().meleeHarvest();
        buildGeneric(TComMaterialIds.refinedSentrite, 0xFF27272b, 0xFF434346, 0xFF5c5c61, 0xFF747477, 0xFF98999b, 0xFFc1c1c1, addStats(GripMaterialStats.ID, HeadMaterialStats.ID), "metal");
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
