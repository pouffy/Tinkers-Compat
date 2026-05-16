package io.github.pouffy.tcompat.compat;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCWoods;
import io.github.pouffy.tcompat.datagen.lang.TCLangProv;
import slimeknights.tconstruct.library.client.data.spritetransformer.ISpriteTransformer;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import slimeknights.tconstruct.tools.stats.*;

import static io.github.pouffy.tcompat.TCompat.getResource;
import static io.github.pouffy.tcompat.datagen.tinkers.material.TCMaterialSpriteProv.transformerFromSprite;
import static net.minecraft.world.item.Tiers.WOOD;

public class WoodMaterials {
    public static final MaterialVariantId
    // Ad Astra
            aeronos = woodVariant(TCWoods.AERONOS,                          0xFF3b3145, 0xFF453e57, 0xFF4f4966, 0xFF55536f, 0xFF5d5c7a, 0xFF646e82, 0xFF677d87),
            strophar = woodVariant(TCWoods.STROPHAR,                        0xFF6b5e5b, 0xFF776b65, 0xFF8a7d72, 0xFF968c7d, 0xFFa09986, 0xFFaea893, 0xFFccc2a7),
            glacian = woodVariant(TCWoods.GLACIAN,                          0xFF8c5b7d, 0xFF99688b, 0xFFae7e96, 0xFFb6859e, 0xFFb9889b, 0xFFc08ea0, 0xFFc59fa8),
    // OTBWG
            aspen = woodVariant(TCWoods.ASPEN,                              0xFF9E644D, 0xFFA56C56, 0xFFAD755F, 0xFFC18260, 0xFFD19373, 0xFFDDA289, 0xFFDDAD8D),
            baobab = woodVariant(TCWoods.BAOBAB,                            0xFF52493E, 0xFF675C48, 0xFF77694D, 0xFF87764E, 0xFF948154, 0xFFA48F5D, 0xFFB3995B),
            blueEnchanted = woodVariant(TCWoods.BLUE_ENCHANTED,             0xFF26367D, 0xFF2F4089, 0xFF3C4D97, 0xFF4757A2, 0xFF5666B0, 0xFF6574BB, 0xFF7180C9),
            cika = woodVariant(TCWoods.CIKA,                                0xFF432C27, 0xFF57372F, 0xFF643D35, 0xFF714439, 0xFF7F4B3C, 0xFF905344, 0xFFA15B4B),
            cypress = woodVariant(TCWoods.CYPRESS,                          0xFF594E4A, 0xFF6A614E, 0xFF756D5C, 0xFF807B61, 0xFF8F885C, 0xFF99906D, 0xFFAD9E76),
            ebony = woodVariant(TCWoods.EBONY,                              0xFF121212, 0xFF171717, 0xFF1A1A1A, 0xFF1F1F1F, 0xFF212121, 0xFF2E2E2E, 0xFF343232),
            fir = woodVariant(TCWoods.FIR,                                  0xFF453326, 0xFF57402E, 0xFF644835, 0xFF794D3A, 0xFF8B6349, 0xFF9F7553, 0xFFB28F6C),
            florus = woodVariant(TCWoods.FLORUS,                            0xFF28511E, 0xFF305B26, 0xFF39672E, 0xFF44592A, 0xFF546B39, 0xFF607744, 0xFF768948),
            greenEnchanted = woodVariant(TCWoods.GREEN_ENCHANTED,           0xFF1B6728, 0xFF237433, 0xFF2D7F3D, 0xFF3A8D4A, 0xFF479956, 0xFF54A564, 0xFF5EB26F),
            holly = woodVariant(TCWoods.HOLLY,                              0xFF7B655B, 0xFF876F64, 0xFF92786C, 0xFFB48769, 0xFFBF977D, 0xFFDDB4A2, 0xFFDBC1AD),
            ironwood = woodVariant(TCWoods.IRONWOOD,                        0xFF69615C, 0xFF6E6763, 0xFF756E6A, 0xFF7D7673, 0xFF84807D, 0xFF9E9C99, 0xFFA7A5A2),
            jacaranda = woodVariant(TCWoods.JACARANDA,                      0xFF5B3E3F, 0xFF714748, 0xFF865456, 0xFF916964, 0xFF9F716D, 0xFFAB8482, 0xFFC19B9D),
            mahogany = woodVariant(TCWoods.MAHOGANY,                        0xFF612C4A, 0xFF6C3655, 0xFF723A5A, 0xFF854E6D, 0xFF8C5775, 0xFF95607E, 0xFF9E6A88),
            maple = woodVariant(TCWoods.MAPLE,                              0xFF413C38, 0xFF4F4842, 0xFF58504B, 0xFF695952, 0xFF766A62, 0xFF84786F, 0xFF978D83),
            palm = woodVariant(TCWoods.PALM,                                0xFF4D483D, 0xFF685B45, 0xFF79694E, 0xFF877E68, 0xFF9B8D6F, 0xFFA89B7A, 0xFFB0A792),
            paloVerde = woodVariant(TCWoods.PALO_VERDE,                     0xFFC4B18C, 0xFFD1BF98, 0xFFD7C59E, 0xFFE0CFA5, 0xFFE8DAAF, 0xFFF3E4B9, 0xFFF5E6BC),
            pine = woodVariant(TCWoods.PINE,                                0xFF635B4C, 0xFF6E6754, 0xFF7A7362, 0xFF998E70, 0xFFA69C81, 0xFFB2A78A, 0xFFC5B99A),
            rainbowEucalyptus = woodVariant(TCWoods.RAINBOW_EUCALYPTUS, transformerFromSprite(getResource("generator/rainbow_eucalyptus"), 1, 0)),
            redwood = woodVariant(TCWoods.REDWOOD,                          0xFF582D2D, 0xFF6C3737, 0xFF7B3D3D, 0xFF974949, 0xFFAA5050, 0xFFBD5656, 0xFFCB5757),
            sakura = woodVariant(TCWoods.SAKURA,                            0xFF501B25, 0xFF601F29, 0xFF702935, 0xFF742941, 0xFF822C3A, 0xFF922E3F, 0xFFA13043),
            skyris = woodVariant(TCWoods.SKYRIS,                            0xFF3B7087, 0xFF457B92, 0xFF4F859C, 0xFF6798AE, 0xFF74A2B7, 0xFF84AFC2, 0xFF95BCCB),
            whiteMangrove = woodVariant(TCWoods.WHITE_MANGROVE,             0xFF8A8585, 0xFF9A9898, 0xFFA1A1A1, 0xFFB5B0B0, 0xFFBEBBBB, 0xFFCCCCCC, 0xFFD4D4D4),
            willow = woodVariant(TCWoods.WILLOW,                            0xFF293522, 0xFF3B422B, 0xFF495133, 0xFF515C38, 0xFF5B683E, 0xFF676D44, 0xFF717947),
            witchHazel = woodVariant(TCWoods.WITCH_HAZEL,                   0xFF183A26, 0xFF1D442D, 0xFF214F34, 0xFF225337, 0xFF286240, 0xFF31774D, 0xFF3B8C5B),
            zelkova = woodVariant(TCWoods.ZELKOVA,                          0xFF4F2713, 0xFF592F17, 0xFF66351A, 0xFF824A23, 0xFF96582F, 0xFFAB6336, 0xFFBF6D36),
    // Regions Unexplored
            alpha = woodVariant(TCWoods.ALPHA,                              0xFF4c3d26, 0xFF695433, 0xFF735e39, 0xFF7c623e, 0xFF9f844d, 0xFFb4905a, 0xFFbc9862),
            blackwood = woodVariant(TCWoods.BLACKWOOD,                      0xFF130f0c, 0xFF1b1510, 0xFF1f1813, 0xFF261e18, 0xFF2c241d, 0xFF372d27, 0xFF3d332c),
            blueBioshroom = woodVariant(TCWoods.BLUE_BIOSHROOM,             0xFF2a748d, 0xFF398b9f, 0xFF419aae, 0xFF4bacc2, 0xFF57bbd1, 0xFF65c9db, 0xFF82d9e7),
            brimwood = woodVariant(TCWoods.BRIMWOOD, transformerFromSprite(getResource("generator/brimwood"), 2, 0)),
            cobalt = woodVariant(TCWoods.COBALT,                            0xFF11101a, 0xFF151625, 0xFF0a1536, 0xFF0d1a43, 0xFF112254, 0xFF142866, 0xFF19317a),
            eucalyptus = woodVariant(TCWoods.EUCALYPTUS,                    0xFF7d472b, 0xFF8d5437, 0xFF9d5e41, 0xFFa66749, 0xFFb37253, 0xFFb87c5b, 0xFFc38362),
            greenBioshroom = woodVariant(TCWoods.GREEN_BIOSHROOM,           0xFF3a892c, 0xFF449c35, 0xFF50ac3e, 0xFF5dc049, 0xFF6ccf55, 0xFF7fd865, 0xFF9ee487),
            joshua = woodVariant(TCWoods.JOSHUA,                            0xFF6f5539, 0xFF7c6144, 0xFF8b7054, 0xFF977b5f, 0xFFa78a6d, 0xFFb4977a, 0xFFc0a183),
            kapok = woodVariant(TCWoods.KAPOK,                              0xFF5b4926, 0xFF6d5831, 0xFF836443, 0xFF977051, 0xFFa87b62, 0xFFb28571, 0xFFb98c7b),
            larch = woodVariant(TCWoods.LARCH,                              0xFF806953, 0xFF8f7660, 0xFF9a836d, 0xFFa48b75, 0xFFae9781, 0xFFb59e88, 0xFFbca591),
            magnolia = woodVariant(TCWoods.MAGNOLIA,                        0xFF651b23, 0xFF742028, 0xFF80252e, 0xFF902c34, 0xFF9b3239, 0xFFa5383e, 0xFFb74648),
            mauve = woodVariant(TCWoods.MAUVE,                              0xFF4d3c5c, 0xFF584667, 0xFF655377, 0xFF6d5c80, 0xFF755f83, 0xFF7f638a, 0xFF8e6e97),
            pinkBioshroom = woodVariant(TCWoods.PINK_BIOSHROOM,             0xFF832f74, 0xFF8f357f, 0xFFa13e92, 0xFFb64ba9, 0xFFc558ba, 0xFFd773d1, 0xFFdd8dd9),
            socotra = woodVariant(TCWoods.SOCOTRA,                          0xFF723117, 0xFF854e2a, 0xFF946539, 0xFF9e7344, 0xFFa8824f, 0xFFaf8b56, 0xFFb4915c),
            yellowBioshroom = woodVariant(TCWoods.YELLOW_BIOSHROOM,         0xFF887e2b, 0xFF948930, 0xFFa79839, 0xFFbca945, 0xFFcbb552, 0xFFddc46e, 0xFFe2cd89),
    // Quark
            ashen = woodVariant(TCWoods.ANCIENT,                            0xFFa3a3ae, 0xFFb9b6bb, 0xFFd9cdd3, 0xFFeadbe4, 0xFFe3ede9, 0xFFfaffef, 0xFFffffff),
            azalea = woodVariant(TCWoods.AZALEA,                            0xFF71733d, 0xFF818149, 0xFF999b54, 0xFF99a654, 0xFFabb662, 0xFFbfc664, 0xFFced46b),
            trumpet = woodVariant(TCWoods.BLOSSOM,                          0xFF361b1b, 0xFF41211b, 0xFF48221c, 0xFF4e271c, 0xFF5b2822, 0xFF622d23, 0xFF6b3324),
    //Betterend
            mossyGlowshroom = woodVariant(TCWoods.MOSSY_GLOWSHROOM,         0xFF015847, 0xFF016b57, 0xFF01856c, 0xFF01937b, 0xFF01a38b, 0xFF01ab94, 0xFF02b7a1),
            pythadendron = woodVariant(TCWoods.PYTHADENDRON,                0xFF3e193e, 0xFF441b44, 0xFF532253, 0xFF622862, 0xFF702f70, 0xFF783378, 0xFF873c87),
            endLotus = woodVariant(TCWoods.END_LOTUS,                       0xFF4cadcc, 0xFF55b6d4, 0xFF62bfdd, 0xFF7ccce4, 0xFF82d2eb, 0xFF88d8f0, 0xFF97e0f3),
            lacugrove = woodVariant(TCWoods.LACUGROVE,                      0xFFb3aa77, 0xFFbeb784, 0xFFccc792, 0xFFd9d99e, 0xFFe4e9a8, 0xFFe9f0ae, 0xFFf0f8b8),
            dragonTree = woodVariant(TCWoods.DRAGON_TREE,                   0xFF191123, 0xFF1c1227, 0xFF241833, 0xFF2a1d3c, 0xFF312143, 0xFF38264c, 0xFF433059),
            tenanea = woodVariant(TCWoods.TENANEA,                          0xFF67335a, 0xFF7a3e6c, 0xFF934c82, 0xFFa15692, 0xFFad5f9f, 0xFFb363a4, 0xFFbf6fb2),
            helixTree = woodVariant(TCWoods.HELIX_TREE,                     0xFF9e531d, 0xFFa75c1c, 0xFFb3681c, 0xFFbb701c, 0xFFc1761c, 0xFFcb8021, 0xFFd2872a),
            umbrellaTree = woodVariant(TCWoods.UMBRELLA_TREE,               0xFF01464d, 0xFF024f58, 0xFF025861, 0xFF025c66, 0xFF02656c, 0xFF026e71, 0xFF037a78),
            endJellyshroom = woodVariant(TCWoods.END_JELLYSHROOM,           0xFF4b6d77, 0xFF567a84, 0xFF608590, 0xFF70949e, 0xFF80a1a9, 0xFF88a8af, 0xFF91b0b7),
            lucernia = woodVariant(TCWoods.LUCERNIA,                        0xFF842b2d, 0xFF9d373a, 0xFFae4140, 0xFFbe4e4b, 0xFFc95651, 0xFFd46b60, 0xFFe08270),
    //Betternether
            netherReed = woodVariant(TCWoods.NETHER_REED,                   0xFF131219, 0xFF18171e, 0xFF111f37, 0xFF291d5b, 0xFF2d3076, 0xFF3a4177, 0xFF57498f),
            stalagnate = woodVariant(TCWoods.STALAGNATE,                    0xFF2c4637, 0xFF30583f, 0xFF3b6049, 0xFF417c57, 0xFF51916c, 0xFF599d76, 0xFF5da57a),
            netherWillow = woodVariant(TCWoods.NETHER_WILLOW,               0xFF601b1a, 0xFF751d1d, 0xFF7f2721, 0xFF8a302a, 0xFF9e3a33, 0xFFa7403a, 0xFFb64a42),
            wart = woodVariant(TCWoods.WART,                                0xFF3f0000, 0xFF4a0000, 0xFF5d0000, 0xFF6d0404, 0xFF790c0a, 0xFF8c0f0f, 0xFF981818),
            rubeus = woodVariant(TCWoods.RUBEUS,                            0xFF5c1831, 0xFF711e3d, 0xFF841f46, 0xFF8c264d, 0xFF9a2854, 0xFFa22a58, 0xFFab2d5e),
            mushroomFir = woodVariant(TCWoods.MUSHROOM_FIR,                 0xFF0a1e6e, 0xFF0c2583, 0xFF0e2c98, 0xFF1137a0, 0xFF1243ac, 0xFF134ab2, 0xFF1955bc),
            netherMushroom = woodVariant(TCWoods.NETHER_MUSHROOM,           0xFFcfc29d, 0xFFded2b0, 0xFFe9e1ca, 0xFFeee8d6, 0xFFf4f0e5, 0xFFf9f7f1, 0xFFfaf9f4),
            anchorTree = woodVariant(TCWoods.ANCHOR_TREE,                   0xFF254622, 0xFF2d562a, 0xFF32622f, 0xFF3b7337, 0xFF3e793a, 0xFF458540, 0xFF4d8f47),
            netherSakura = woodVariant(TCWoods.NETHER_SAKURA,               0xFF491e12, 0xFF562618, 0xFF6a3221, 0xFF753926, 0xFF7a3c29, 0xFF8d412c, 0xFF984830);

    public static final MaterialId hellbark = MaterialBuilder.material(TCWoods.HELLBARK.makeCondition(), "hellbark")
            .data(d -> d.tier(1).order(1).craftable(true))
            .traits(t -> t.trait(ModifierIds.fiery).trait(MaterialRegistry.AMMO, ModifierIds.spectral))
            .stats(s ->
                    s.stat(
                            new HeadMaterialStats(60, 2f, WOOD, 0f),
                            HandleMaterialStats.percents().build(), // flat all around
                            StatlessMaterialStats.BINDING,
                            new LimbMaterialStats(60, 0, 0, 0),
                            new GripMaterialStats(0f, 0, 0),
                            StatlessMaterialStats.ARROW_SHAFT,
                            StatlessMaterialStats.SHIELD_CORE
                    )
            )
            .renderInfo(r -> r.color(0x332929).fallbacks("wood", "stick", "primitive"))
            .spriteInfo(s -> s.meleeHarvest().ranged().shieldCore().arrowShaft().fallbacks("wood", "stick", "primitive").sevenColor(0xFF20191a, 0xFF281e1f, 0xFF2f2425, 0xFF332929, 0xFF382d2d, 0xFF3b3031, 0xFF3e3233))
            .buildMaterial();

    private static MaterialVariantId woodVariant(TCWoods woodType, int c63, int c102, int c140, int c178, int c216, int c234, int c255) {
        var builder = MaterialBuilder.variant(woodType.makeCondition(), woodType.getSerializedName(), MaterialIds.wood)
                .lang(TCLangProv.toEngStr(woodType.getSerializedName()) + " Wood")
                .renderInfo(r -> r.color(c178).fallbacks("wood", "stick", "primitive"))
                .spriteInfo(s -> s.planks().sevenColor(c63, c102, c140, c178, c216, c234, c255));
        MaterialBuilder.woodMaterials.put(builder, woodType);
        return builder.buildVariant();
    }

    private static MaterialVariantId woodVariant(TCWoods woodType, ISpriteTransformer spriteTransformer) {
        var builder = MaterialBuilder.variant(woodType.makeCondition(), woodType.getSerializedName(), MaterialIds.wood)
                .lang(TCLangProv.toEngStr(woodType.getSerializedName()) + " Wood")
                .renderInfo(r -> r.fallbacks("wood", "stick", "primitive"))
                .spriteInfo(s -> s.planks().transformer(spriteTransformer));
        MaterialBuilder.woodMaterials.put(builder, woodType);
        return builder.buildVariant();
    }

    public static void staticInit() {}
}
