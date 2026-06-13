package io.github.pouffy.tcompat.compat;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCRocks;
import slimeknights.tconstruct.library.client.data.spritetransformer.ISpriteTransformer;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

public class RockMaterials {
    public static final MaterialVariantId
    // Ad Astra
    moonStone = rockVariant(TCRocks.MOON_STONE,                 0xFF353e43, 0xFF3f474c, 0xFF424c4e, 0xFF4a5253, 0xFF4f5c5d, 0xFF506869),
    conglomerate = rockVariant(TCRocks.CONGLOMERATE,            0xFF885e5e, 0xFF936464, 0xFF936c65, 0xFFa47569, 0xFFa97b6f, 0xFFba897a),
    marsStone = rockVariant(TCRocks.MARS_STONE,                 0xFFbf6f50, 0xFFc17a51, 0xFFc87e53, 0xFFc98555, 0xFFd08957, 0xFFcd9360),
    venusStone = rockVariant(TCRocks.VENUS_STONE,               0xFFb3763e, 0xFFb88143, 0xFFc28d4f, 0xFFc99b5a, 0xFFd5a664, 0xFFdfb271),
    mercuryStone = rockVariant(TCRocks.MERCURY_STONE,           0xFF4a263b, 0xFF4d2a3c, 0xFF55313f, 0xFF603948, 0xFF723e49, 0xFF844751),
    glacioStone = rockVariant(TCRocks.GLACIO_STONE,             0xFF7c73a9, 0xFF877cad, 0xFF9185b6, 0xFF9897bf, 0xFFa5a6ca, 0xFFb4b8d2),
    // OTBWG
    redRock = rockVariant(TCRocks.RED_ROCK,                     0xFF7F4026, 0xFF904727, 0xFF95522F, 0xFFA95936, 0xFFAD633E, 0xFFB36D44),
    dacite = rockVariant(TCRocks.DACITE,                        0xFF663E2F, 0xFF6D4536, 0xFF7F5646, 0xFF926251, 0xFF9F6B58, 0xFFA97764),
    // Regions Unexplored
    chalk = rockVariant(TCRocks.CHALK,                          0xFFadadad, 0xFFbababa, 0xFFbfbfbf, 0xFFcccccc, 0xFFd9d9d9, 0xFFe5e5e5),
    // Quark
    limestone = rockVariant(TCRocks.LIMESTONE,                  0xFF68604b, 0xFF78705a, 0xFF877f69, 0xFF968e78, 0xFFa09b85, 0xFFada78b),
    jasper = rockVariant(TCRocks.JASPER,                        0xFF542f28, 0xFF5d2f2c, 0xFF673930, 0xFF744436, 0xFF7e4a3b, 0xFF8f5140),
    shale = rockVariant(TCRocks.SHALE,                          0xFF435256, 0xFF4b5a5d, 0xFF4c666a, 0xFF5a7177, 0xFF617a7e, 0xFF74878a),
    permafrost = rockVariant(TCRocks.PERMAFROST,                0xFF829fd5, 0xFF90aada, 0xFFa3b8e0, 0xFFabbee3, 0xFFbccce9, 0xFFd4def1),
    //Betterend
    flavolite = endStoneVariant(TCRocks.FLAVOLITE,              0xFFc9b07d, 0xFFd4bb8b, 0xFFdbc293, 0xFFe3ca9b, 0xFFead2a3, 0xFFf0dbb1),
    violecite = endStoneVariant(TCRocks.VIOLECITE,              0xFF1a131e, 0xFF211a26, 0xFF29202f, 0xFF352a3c, 0xFF403348, 0xFF483b51),
    sulphuricRock = endStoneVariant(TCRocks.SULPHURIC_ROCK,     0xFF3d2e2d, 0xFF413231, 0xFF493a39, 0xFF544241, 0xFF604d4c, 0xFF775e5d),
    viridJadestone = endStoneVariant(TCRocks.VIRID_JADESTONE,   0xFF193d30, 0xFF1f4537, 0xFF244b3d, 0xFF295042, 0xFF305f4e, 0xFF356f5a),
    azureJadestone = endStoneVariant(TCRocks.AZURE_JADESTONE,   0xFF214f54, 0xFF2b5a5e, 0xFF316361, 0xFF396a66, 0xFF40786c, 0xFF458576),
    sandyJadestone = endStoneVariant(TCRocks.SANDY_JADESTONE,   0xFF697335, 0xFF737d3c, 0xFF7b8648, 0xFF829653, 0xFF8fa45a, 0xFF95b264),
    umbralith = endStoneVariant(TCRocks.UMBRALITH,              0xFF07040d, 0xFF0c0816, 0xFF110f23, 0xFF282d41, 0xFF303c4d, 0xFF40535f),
    //Deeper and Darker
    sculkStone = rockVariant(TCRocks.SCULK_STONE,               0xFF061519, 0xFF091c21, 0xFF0a242d, 0xFF132f38, 0xFF164249, 0xFF185a63),
    gloomslate = flintVariant(TCRocks.GLOOMSLATE,               0xFF130e0e, 0xFF1c1616, 0xFF211b1a, 0xFF2a2020, 0xFF312523, 0xFF3b2d29),
    // Vanilla
    dripstone = rockVariant(TCRocks.DRIPSTONE,                  0xFF543d3a, 0xFF634a47, 0xFF735450, 0xFF836356, 0xFF927965, 0xFFa08d71);

    private static MaterialVariantId rockVariant(TCRocks rockType, int c63, int c102, int c140, int c178, int c216, int c255) {
        var builder = MaterialBuilder.variant(rockType.makeCondition(), rockType.getSerializedName(), MaterialIds.rock)
                .lang(rockType.englishName())
                .renderInfo(r -> r.color(c216).fallbacks("rock"))
                .spriteInfo(s -> s.rock().sixColor(c63, c102, c140, c178, c216, c255));
        MaterialBuilder.rockMaterials.put(builder, rockType);
        return builder.buildVariant();
    }

    private static MaterialVariantId rockVariant(TCRocks rockType, ISpriteTransformer spriteTransformer) {
        var builder = MaterialBuilder.variant(rockType.makeCondition(), rockType.getSerializedName(), MaterialIds.rock)
                .renderInfo(r -> r.fallbacks("rock"))
                .spriteInfo(s -> s.rock().fallbacks("rock").transformer(spriteTransformer));
        MaterialBuilder.rockMaterials.put(builder, rockType);
        return builder.buildVariant();
    }

    private static MaterialVariantId flintVariant(TCRocks rockType, int c63, int c102, int c140, int c178, int c216, int c255) {
        var builder = MaterialBuilder.variant(rockType.makeCondition(), rockType.getSerializedName(), MaterialIds.flint)
                .renderInfo(r -> r.color(c216).fallbacks("crystal", "rock", "stick"))
                .spriteInfo(s -> s.flint().sixColor(c63, c102, c140, c178, c216, c255));
        MaterialBuilder.rockMaterials.put(builder, rockType);
        return builder.buildVariant();
    }

    private static MaterialVariantId flintVariant(TCRocks rockType, ISpriteTransformer spriteTransformer) {
        var builder = MaterialBuilder.variant(rockType.makeCondition(), rockType.getSerializedName(), MaterialIds.flint)
                .renderInfo(r -> r.fallbacks("crystal", "rock", "stick"))
                .spriteInfo(s -> s.flint().transformer(spriteTransformer));
        MaterialBuilder.rockMaterials.put(builder, rockType);
        return builder.buildVariant();
    }

    private static MaterialVariantId endStoneVariant(TCRocks rockType, int c63, int c102, int c140, int c178, int c216, int c255) {
        var builder = MaterialBuilder.variant(rockType.makeCondition(), rockType.getSerializedName(), MaterialIds.whitestone)
                .renderInfo(r -> r.color(c216).fallbacks("rock"))
                .spriteInfo(s -> s.rock().fallbacks("rock").sixColor(c63, c102, c140, c178, c216, c255));
        MaterialBuilder.rockMaterials.put(builder, rockType);
        return builder.buildVariant();
    }

    private static MaterialVariantId endStoneVariant(TCRocks rockType, ISpriteTransformer spriteTransformer) {
        var builder = MaterialBuilder.variant(rockType.makeCondition(), rockType.getSerializedName(), MaterialIds.whitestone)
                .renderInfo(r -> r.fallbacks("rock"))
                .spriteInfo(s -> s.rock().fallbacks("rock").transformer(spriteTransformer));
        MaterialBuilder.rockMaterials.put(builder, rockType);
        return builder.buildVariant();
    }

    public static void staticInit() {}
}
