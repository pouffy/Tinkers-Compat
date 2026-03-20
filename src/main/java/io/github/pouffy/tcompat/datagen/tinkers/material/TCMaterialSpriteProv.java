package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.common.material.TCMaterials;
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

import static io.github.pouffy.tcompat.TCompat.getResource;
import static slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider.*;

public class TCMaterialSpriteProv extends AbstractMaterialSpriteProvider {
    @Override
    public String getName() {
        return "Tinker's Compatability Material Sprites";
    }

    @Override
    protected void addAllMaterials() {
// Planks
        buildPlanks(TCMaterials.aspen,              0xFF9E644D, 0xFFA56C56, 0xFFAD755F, 0xFFC18260, 0xFFD19373, 0xFFDDA289, 0xFFDDAD8D);
        buildPlanks(TCMaterials.baobab,             0xFF52493E, 0xFF675C48, 0xFF77694D, 0xFF87764E, 0xFF948154, 0xFFA48F5D, 0xFFB3995B);
        buildPlanks(TCMaterials.blueEnchanted,      0xFF26367D, 0xFF2F4089, 0xFF3C4D97, 0xFF4757A2, 0xFF5666B0, 0xFF6574BB, 0xFF7180C9);
        buildPlanks(TCMaterials.cika,               0xFF432C27, 0xFF57372F, 0xFF643D35, 0xFF714439, 0xFF7F4B3C, 0xFF905344, 0xFFA15B4B);
        buildPlanks(TCMaterials.cypress,            0xFF594E4A, 0xFF6A614E, 0xFF756D5C, 0xFF807B61, 0xFF8F885C, 0xFF99906D, 0xFFAD9E76);
        buildPlanks(TCMaterials.ebony,              0xFF121212, 0xFF171717, 0xFF1A1A1A, 0xFF1F1F1F, 0xFF212121, 0xFF2E2E2E, 0xFF343232);
        buildPlanks(TCMaterials.fir,                0xFF453326, 0xFF57402E, 0xFF644835, 0xFF794D3A, 0xFF8B6349, 0xFF9F7553, 0xFFB28F6C);
        buildPlanks(TCMaterials.florus,             0xFF28511E, 0xFF305B26, 0xFF39672E, 0xFF44592A, 0xFF546B39, 0xFF607744, 0xFF768948);
        buildPlanks(TCMaterials.greenEnchanted,     0xFF1B6728, 0xFF237433, 0xFF2D7F3D, 0xFF3A8D4A, 0xFF479956, 0xFF54A564, 0xFF5EB26F);
        buildPlanks(TCMaterials.holly,              0xFF7B655B, 0xFF876F64, 0xFF92786C, 0xFFB48769, 0xFFBF977D, 0xFFDDB4A2, 0xFFDBC1AD);
        buildPlanks(TCMaterials.ironwood,           0xFF69615C, 0xFF6E6763, 0xFF756E6A, 0xFF7D7673, 0xFF84807D, 0xFF9E9C99, 0xFFA7A5A2);
        buildPlanks(TCMaterials.jacaranda,          0xFF5B3E3F, 0xFF714748, 0xFF865456, 0xFF916964, 0xFF9F716D, 0xFFAB8482, 0xFFC19B9D);
        buildPlanks(TCMaterials.mahogany,           0xFF612C4A, 0xFF6C3655, 0xFF723A5A, 0xFF854E6D, 0xFF8C5775, 0xFF95607E, 0xFF9E6A88);
        buildPlanks(TCMaterials.maple,              0xFF413C38, 0xFF4F4842, 0xFF58504B, 0xFF695952, 0xFF766A62, 0xFF84786F, 0xFF978D83);
        buildPlanks(TCMaterials.palm,               0xFF4D483D, 0xFF685B45, 0xFF79694E, 0xFF877E68, 0xFF9B8D6F, 0xFFA89B7A, 0xFFB0A792);
        buildPlanks(TCMaterials.paloVerde,          0xFFC4B18C, 0xFFD1BF98, 0xFFD7C59E, 0xFFE0CFA5, 0xFFE8DAAF, 0xFFF3E4B9, 0xFFF5E6BC);
        buildPlanks(TCMaterials.pine,               0xFF635B4C, 0xFF6E6754, 0xFF7A7362, 0xFF998E70, 0xFFA69C81, 0xFFB2A78A, 0xFFC5B99A);
        buildMaterial(TCMaterials.rainbowEucalyptus)
                .variant().meleeHarvest().ranged().shieldCore().statType(WOOD)
                .fallbacks("wood", "stick", "primitive")
                .transformer(transformerFromSprite(getResource("generator/rainbow_eucalyptus"), 1, 0));
        buildPlanks(TCMaterials.redwood,            0xFF582D2D, 0xFF6C3737, 0xFF7B3D3D, 0xFF974949, 0xFFAA5050, 0xFFBD5656, 0xFFCB5757);
        buildPlanks(TCMaterials.sakura,             0xFF501B25, 0xFF601F29, 0xFF702935, 0xFF742941, 0xFF822C3A, 0xFF922E3F, 0xFFA13043);
        buildPlanks(TCMaterials.skyris,             0xFF3B7087, 0xFF457B92, 0xFF4F859C, 0xFF6798AE, 0xFF74A2B7, 0xFF84AFC2, 0xFF95BCCB);
        buildPlanks(TCMaterials.whiteMangrove,      0xFF8A8585, 0xFF9A9898, 0xFFA1A1A1, 0xFFB5B0B0, 0xFFBEBBBB, 0xFFCCCCCC, 0xFFD4D4D4);
        buildPlanks(TCMaterials.willow,             0xFF293522, 0xFF3B422B, 0xFF495133, 0xFF515C38, 0xFF5B683E, 0xFF676D44, 0xFF717947);
        buildPlanks(TCMaterials.witchHazel,         0xFF183A26, 0xFF1D442D, 0xFF214F34, 0xFF225337, 0xFF286240, 0xFF31774D, 0xFF3B8C5B);
        buildPlanks(TCMaterials.zelkova,            0xFF4F2713, 0xFF592F17, 0xFF66351A, 0xFF824A23, 0xFF96582F, 0xFFAB6336, 0xFFBF6D36);
        buildPlanks(TCMaterials.hellbark,           0xFF20191a, 0xFF281e1f, 0xFF2f2425, 0xFF332929, 0xFF382d2d, 0xFF3b3031, 0xFF3e3233).arrowShaft();
        buildMaterial(TCMaterials.wickedWax)
                .statType(StatlessMaterialStats.BINDING.getIdentifier()).fallbacks("bone", "metal")
                .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF750d83).addARGB(102, 0xFFb20e7b).addARGB(140, 0xFFf93985).addARGB(178, 0xFFff5e3e).addARGB(216, 0xFFffa342).addARGB(255, 0xFFfde46d).build());
        buildMaterial(TCMaterials.blightbunnyFang)
                .arrowHead()
                .fallbacks("bone", "metal")
                .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF5a6972).addARGB(102, 0xFF6e7e88).addARGB(140, 0xFF7f919b).addARGB(178, 0xFFa1b5be).addARGB(216, 0xFFc1d3d8).addARGB(255, 0xFFdeeef2).build());
        buildMaterial(TCMaterials.mykapodShell)
                .shieldCore()
                .fallbacks("bone", "metal")
                .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF2c2e5b).addARGB(102, 0xFF3a3d6f).addARGB(140, 0xFF4b5187).addARGB(178, 0xFF5e65a5).addARGB(216, 0xFF7178c2).addARGB(255, 0xFF8f92d8).build());
        buildMaterial(TCMaterials.aetherWood)
                .meleeHarvest().ranged().shieldCore().arrowShaft()
                .fallbacks("wood", "stick", "primitive")
                .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF333122).addARGB(102, 0xFF403D2A).addARGB(140, 0xFF4F4D36).addARGB(178, 0xFF5C5B41).addARGB(216, 0xFF63634A).addARGB(255, 0xFF7B7E61).build());
        buildPlanks(TCMaterials.skyroot,            0xFF333122, 0xFF403D2A, 0xFF4F4D36, 0xFF5C5B41, 0xFF63634A, 0xFF737558, 0xFF7B7E61);
        buildPlanks(TCMaterials.roseroot,           0xFFA37185, 0xFFB48090, 0xFFCC9CA5, 0xFFDFA7AF, 0xFFF4B6B8, 0xFFFDBDBE, 0xFFFED7D0);
        buildPlanks(TCMaterials.yagroot,            0xFF313140, 0xFF3C3B4A, 0xFF4B475B, 0xFF564D67, 0xFF615473, 0xFF67587A, 0xFF72637D);
        buildPlanks(TCMaterials.cruderoot,          0xFF4F5266, 0xFF5E6075, 0xFF71708E, 0xFF7E79A0, 0xFF8E84B3, 0xFF9689BD, 0xFFA99AC1);
        buildPlanks(TCMaterials.conberry,           0xFF544338, 0xFF5F4A3F, 0xFF705C4C, 0xFF826653, 0xFF99765D, 0xFFA68064, 0xFFAA886F);
        buildPlanks(TCMaterials.sunroot,            0xFF8E6B65, 0xFF9C7F77, 0xFFB39C8D, 0xFFC6AB98, 0xFFD9C2A3, 0xFFE2CCA9, 0xFFE9DABA);
        buildPlanks(TCMaterials.blightwillow,       0xFF4B694E, 0xFF5E805C, 0xFF6C8C63, 0xFF7F9F74, 0xFF8FAC82, 0xFF9FBA90, 0xFFA8C198);
        buildPlanks(TCMaterials.fieldsproot,        0xFF824241, 0xFF9A514E, 0xFFAF615B, 0xFFBF7069, 0xFFC97B72, 0xFFD4847B, 0xFFDB8B82);
        buildPlanks(TCMaterials.cloudcap,           0xFF191A2B, 0xFF212338, 0xFF292C45, 0xFF323654, 0xFF3B3F60, 0xFF444A72, 0xFF4B537D);
        buildPlanks(TCMaterials.jellyshroom,        0xFF373E4F, 0xFF414959, 0xFF4B5364, 0xFF555D6B, 0xFF646B76, 0xFF737982, 0xFF81868E);
        buildPlanks(TCMaterials.crystal,            0xFF50607A, 0xFF5F728E, 0xFF6D84A3, 0xFF788FAB, 0xFF839CBA, 0xFF8DA6C1, 0xFF95AFC6);
        buildPlanks(TCMaterials.glacia,             0xFF524D45, 0xFF645F54, 0xFF787365, 0xFF848072, 0xFF938F7E, 0xFF9E9A89, 0xFFA7A18F);
        buildPlanks(TCMaterials.ashen,              0xFFa3a3ae, 0xFFb9b6bb, 0xFFd9cdd3, 0xFFeadbe4, 0xFFe3ede9, 0xFFfaffef, 0xFFffffff);
        buildPlanks(TCMaterials.azalea,             0xFF71733d, 0xFF818149, 0xFF999b54, 0xFF99a654, 0xFFabb662, 0xFFbfc664, 0xFFced46b);
        buildPlanks(TCMaterials.trumpet,            0xFF361b1b, 0xFF41211b, 0xFF48221c, 0xFF4e271c, 0xFF5b2822, 0xFF622d23, 0xFF6b3324);
        buildPlanks(TCMaterials.alpha,              0xFF4c3d26, 0xFF695433, 0xFF735e39, 0xFF7c623e, 0xFF9f844d, 0xFFb4905a, 0xFFbc9862);
        buildPlanks(TCMaterials.blackwood,          0xFF130f0c, 0xFF1b1510, 0xFF1f1813, 0xFF261e18, 0xFF2c241d, 0xFF372d27, 0xFF3d332c);
        buildPlanks(TCMaterials.blueBioshroom,      0xFF2a748d, 0xFF398b9f, 0xFF419aae, 0xFF4bacc2, 0xFF57bbd1, 0xFF65c9db, 0xFF82d9e7);
        buildMaterial(TCMaterials.brimwood)
                .fallbacks("wood", "stick", "primitive")
                .meleeHarvest().ranged().shieldCore().statType(WOOD)
                .transformer(transformerFromSprite(getResource("generator/brimwood"), 2, 0));
        buildPlanks(TCMaterials.cobalt,             0xFF11101a, 0xFF151625, 0xFF0a1536, 0xFF0d1a43, 0xFF112254, 0xFF142866, 0xFF19317a);
        buildPlanks(TCMaterials.eucalyptus,         0xFF7d472b, 0xFF8d5437, 0xFF9d5e41, 0xFFa66749, 0xFFb37253, 0xFFb87c5b, 0xFFc38362);
        buildPlanks(TCMaterials.greenBioshroom,     0xFF3a892c, 0xFF449c35, 0xFF50ac3e, 0xFF5dc049, 0xFF6ccf55, 0xFF7fd865, 0xFF9ee487);
        buildPlanks(TCMaterials.joshua,             0xFF6f5539, 0xFF7c6144, 0xFF8b7054, 0xFF977b5f, 0xFFa78a6d, 0xFFb4977a, 0xFFc0a183);
        buildPlanks(TCMaterials.kapok,              0xFF5b4926, 0xFF6d5831, 0xFF836443, 0xFF977051, 0xFFa87b62, 0xFFb28571, 0xFFb98c7b);
        buildPlanks(TCMaterials.larch,              0xFF806953, 0xFF8f7660, 0xFF9a836d, 0xFFa48b75, 0xFFae9781, 0xFFb59e88, 0xFFbca591);
        buildPlanks(TCMaterials.magnolia,           0xFF651b23, 0xFF742028, 0xFF80252e, 0xFF902c34, 0xFF9b3239, 0xFFa5383e, 0xFFb74648);
        buildPlanks(TCMaterials.mauve,              0xFF4d3c5c, 0xFF584667, 0xFF655377, 0xFF6d5c80, 0xFF755f83, 0xFF7f638a, 0xFF8e6e97);
        buildPlanks(TCMaterials.pinkBioshroom,      0xFF832f74, 0xFF8f357f, 0xFFa13e92, 0xFFb64ba9, 0xFFc558ba, 0xFFd773d1, 0xFFdd8dd9);
        buildPlanks(TCMaterials.socotra,            0xFF723117, 0xFF854e2a, 0xFF946539, 0xFF9e7344, 0xFFa8824f, 0xFFaf8b56, 0xFFb4915c);
        buildPlanks(TCMaterials.yellowBioshroom,    0xFF887e2b, 0xFF948930, 0xFFa79839, 0xFFbca945, 0xFFcbb552, 0xFFddc46e, 0xFFe2cd89);
        buildPlanks(TCMaterials.aeronos,            0xFF3b3145, 0xFF453e57, 0xFF4f4966, 0xFF55536f, 0xFF5d5c7a, 0xFF646e82, 0xFF677d87);
        buildPlanks(TCMaterials.strophar,           0xFF6b5e5b, 0xFF776b65, 0xFF8a7d72, 0xFF968c7d, 0xFFa09986, 0xFFaea893, 0xFFccc2a7);
        buildPlanks(TCMaterials.glacian,            0xFF8c5b7d, 0xFF99688b, 0xFFae7e96, 0xFFb6859e, 0xFFb9889b, 0xFFc08ea0, 0xFFc59fa8);

        buildPlanks(TCMaterials.mossyGlowshroom,    0xFF015847, 0xFF016b57, 0xFF01856c, 0xFF01937b, 0xFF01a38b, 0xFF01ab94, 0xFF02b7a1);
        buildPlanks(TCMaterials.pythadendron,       0xFF3e193e, 0xFF441b44, 0xFF532253, 0xFF622862, 0xFF702f70, 0xFF783378, 0xFF873c87);
        buildPlanks(TCMaterials.endLotus,           0xFF4cadcc, 0xFF55b6d4, 0xFF62bfdd, 0xFF7ccce4, 0xFF82d2eb, 0xFF88d8f0, 0xFF97e0f3);
        buildPlanks(TCMaterials.lacugrove,          0xFFb3aa77, 0xFFbeb784, 0xFFccc792, 0xFFd9d99e, 0xFFe4e9a8, 0xFFe9f0ae, 0xFFf0f8b8);
        buildPlanks(TCMaterials.dragonTree,         0xFF191123, 0xFF1c1227, 0xFF241833, 0xFF2a1d3c, 0xFF312143, 0xFF38264c, 0xFF433059);
        buildPlanks(TCMaterials.tenanea,            0xFF67335a, 0xFF7a3e6c, 0xFF934c82, 0xFFa15692, 0xFFad5f9f, 0xFFb363a4, 0xFFbf6fb2);
        buildPlanks(TCMaterials.helixTree,          0xFF9e531d, 0xFFa75c1c, 0xFFb3681c, 0xFFbb701c, 0xFFc1761c, 0xFFcb8021, 0xFFd2872a);
        buildPlanks(TCMaterials.umbrellaTree,       0xFF01464d, 0xFF024f58, 0xFF025861, 0xFF025c66, 0xFF02656c, 0xFF026e71, 0xFF037a78);
        buildPlanks(TCMaterials.endJellyshroom,     0xFF4b6d77, 0xFF567a84, 0xFF608590, 0xFF70949e, 0xFF80a1a9, 0xFF88a8af, 0xFF91b0b7);
        buildPlanks(TCMaterials.lucernia,           0xFF842b2d, 0xFF9d373a, 0xFFae4140, 0xFFbe4e4b, 0xFFc95651, 0xFFd46b60, 0xFFe08270);


        // Rock
        buildRock(TCMaterials.redRock,              0xFF7F4026, 0xFF904727, 0xFF95522F, 0xFFA95936, 0xFFAD633E, 0xFFB36D44);
        buildRock(TCMaterials.dacite,               0xFF663E2F, 0xFF6D4536, 0xFF7F5646, 0xFF926251, 0xFF9F6B58, 0xFFA97764);

        buildRock(TCMaterials.limestone,            0xFF68604b, 0xFF78705a, 0xFF877f69, 0xFF968e78, 0xFFa09b85, 0xFFada78b);
        buildRock(TCMaterials.jasper,               0xFF542f28, 0xFF5d2f2c, 0xFF673930, 0xFF744436, 0xFF7e4a3b, 0xFF8f5140);
        buildRock(TCMaterials.shale,                0xFF435256, 0xFF4b5a5d, 0xFF4c666a, 0xFF5a7177, 0xFF617a7e, 0xFF74878a);
        buildRock(TCMaterials.permafrost,           0xFF829fd5, 0xFF90aada, 0xFFa3b8e0, 0xFFabbee3, 0xFFbccce9, 0xFFd4def1);

        buildRock(TCMaterials.chalk,                0xFFadadad, 0xFFbababa, 0xFFbfbfbf, 0xFFcccccc, 0xFFd9d9d9, 0xFFe5e5e5);

        buildRock(TCMaterials.dripstone,            0xFF543d3a, 0xFF634a47, 0xFF735450, 0xFF836356, 0xFF927965, 0xFFa08d71);

        buildRock(TCMaterials.holystone,            0xFF808080, 0xFF8F8F8F, 0xFF9C9C9C, 0xFFADADAD, 0xFFCCCCCC, 0xFFE2E2E2);
        buildRock(TCMaterials.aseterite,            0xFF746772, 0xFF7F717D, 0xFF897A87, 0xFF938595, 0xFFA296A1, 0xFFB9ADB8);
        buildRock(TCMaterials.clorite,              0xFF415964, 0xFF4D6A74, 0xFF5B7C88, 0xFF699AA1, 0xFF7DAFAE, 0xFF95C9CB);
        buildRock(TCMaterials.divinite,             0xFF8B7149, 0xFF987E55, 0xFFAA8F65, 0xFFBAA170, 0xFFCDB285, 0xFFD8C699);
        buildRock(TCMaterials.driftshale,           0xFFAA9A5D, 0xFFBEB06C, 0xFFCEC277, 0xFFD9CE81, 0xFFDFDA95, 0xFFE3E2A8);

        buildRock(TCMaterials.moonStone,            0xFF353e43, 0xFF3f474c, 0xFF424c4e, 0xFF4a5253, 0xFF4f5c5d, 0xFF506869);
        buildRock(TCMaterials.conglomerate,         0xFF885e5e, 0xFF936464, 0xFF936c65, 0xFFa47569, 0xFFa97b6f, 0xFFba897a);
        buildRock(TCMaterials.marsStone,            0xFFbf6f50, 0xFFc17a51, 0xFFc87e53, 0xFFc98555, 0xFFd08957, 0xFFcd9360);
        buildRock(TCMaterials.venusStone,           0xFFb3763e, 0xFFb88143, 0xFFc28d4f, 0xFFc99b5a, 0xFFd5a664, 0xFFdfb271);
        buildRock(TCMaterials.mercuryStone,         0xFF4a263b, 0xFF4d2a3c, 0xFF55313f, 0xFF603948, 0xFF723e49, 0xFF844751);
        buildRock(TCMaterials.glacioStone,          0xFF7c73a9, 0xFF877cad, 0xFF9185b6, 0xFF9897bf, 0xFFa5a6ca, 0xFFb4b8d2);

        buildRock(TCMaterials.flavolite,            0xFFc9b07d, 0xFFd4bb8b, 0xFFdbc293, 0xFFe3ca9b, 0xFFead2a3, 0xFFf0dbb1);
        buildRock(TCMaterials.violecite,            0xFF1a131e, 0xFF211a26, 0xFF29202f, 0xFF352a3c, 0xFF403348, 0xFF483b51);
        buildRock(TCMaterials.sulphuricRock,        0xFF3d2e2d, 0xFF413231, 0xFF493a39, 0xFF544241, 0xFF604d4c, 0xFF775e5d);
        buildRock(TCMaterials.viridJadestone,       0xFF193d30, 0xFF1f4537, 0xFF244b3d, 0xFF295042, 0xFF305f4e, 0xFF356f5a);
        buildRock(TCMaterials.azureJadestone,       0xFF214f54, 0xFF2b5a5e, 0xFF316361, 0xFF396a66, 0xFF40786c, 0xFF458576);
        buildRock(TCMaterials.sandyJadestone,       0xFF697335, 0xFF737d3c, 0xFF7b8648, 0xFF829653, 0xFF8fa45a, 0xFF95b264);
        buildRock(TCMaterials.umbralith,            0xFF07040d, 0xFF0c0816, 0xFF110f23, 0xFF282d41, 0xFF303c4d, 0xFF40535f);


        buildGeneric(TCMaterials.zanite,            0xFF1c0d3b, 0xFF350f6c, 0xFF5b22b0, 0xFF8a4ee4, 0xFFaf7ff6, 0xFFbf9cf4, 0xFFe1cdff, plating(HeadMaterialStats.ID), "gem", "metal").arrowHead().fletching();
        buildGeneric(TCMaterials.skyjade,           0xFF434e34, 0xFF4e6741, 0xFF729752, 0xFF8cb955, 0xFF9ada5b, 0xFFb2e865, 0xFFd1f397, plating(HeadMaterialStats.ID), "gem", "metal").arrowHead();
        buildGeneric(TCMaterials.gravitite,         0xFF390c35, 0xFF5c1256, 0xFFb643a0, 0xFFda67d0, 0xFFf195ef, 0xFFffcbfd, addStats(), "metal").armor().ranged().meleeHarvest();
        buildGeneric(TCMaterials.veridium,          0xFF0e193b, 0xFF142958, 0xFF32578c, 0xFF5a90bd, 0xFF7fbedc, 0xFFb9edfb, addStats(), "metal").armor().meleeHarvest();
        buildGeneric(TCMaterials.refinedSentrite,   0xFF27272b, 0xFF434346, 0xFF5c5c61, 0xFF747477, 0xFF98999b, 0xFFc1c1c1, addStats(GripMaterialStats.ID, HeadMaterialStats.ID), "metal");

        buildGeneric(TCMaterials.stormforgedSteel,  0xFF353a3f, 0xFF58606c, 0xFF9198a9, 0xFFb8c5d1, 0xFFd6e7f1, 0xFFdeffff, addStats(INGOT), "metal").meleeHarvest();
        buildGeneric(TCMaterials.lightnum,          0xFF0f274b, 0xFF1f4478, 0xFF2a5a9d, 0xFF5a9bdb, 0xFF94c9ff, 0xFFffffff, addStats(INGOT), "metal").meleeHarvest();
        buildGeneric(TCMaterials.draculite,         0xFF3b0303, 0xFF630505, 0xFF700606, 0xFFd11e1e, 0xFFf56262, 0xFFffadad, addStats(INGOT), "metal").meleeHarvest();

        buildGeneric(TCMaterials.pyral,             0xFF6b130b, 0xFFb7420c, 0xFFd86a0b, 0xFFffb326, 0xFFfeda87, 0xFFffeec5, addStats(), "metal").armor().ranged();

        buildGeneric(TCMaterials.calorite,          0xFF470d2f, 0xFF6a1534, 0xFF9e1f3f, 0xFFb83145, 0xFFcb4e4f, 0xFFdf6d5c, addStats(HandleMaterialStats.ID), "metal").armor().meleeHarvest().ranged();
        buildGeneric(TCMaterials.desh,              0xFF7a3045, 0xFF9e4539, 0xFFb25b3d, 0xFFc77142, 0xFFd68d4d, 0xFFe9ba5d, addStats(HandleMaterialStats.ID), "metal").armor().meleeHarvest().ranged();
        buildGeneric(TCMaterials.ostrum,            0xFF42313d, 0xFF553e4b, 0xFF775360, 0xFF925e64, 0xFFa86c73, 0xFFbf7a82, addStats(HandleMaterialStats.ID), "metal").armor().meleeHarvest().ranged();

        buildGeneric(TCMaterials.thallasium,        0xFF378099, 0xFF449aaf, 0xFF54b0bf, 0xFF7ad0d3, 0xFFa4e8da, 0xFFcff9e2, addStats(HandleMaterialStats.ID), "metal").armor().meleeHarvest().ranged();
        buildGeneric(TCMaterials.terminite,         0xFF01625b, 0xFF00a094, 0xFF46d5ca, 0xFF71f3e8, 0xFFa1f8f0, 0xFFebfefc, addStats(HandleMaterialStats.ID), "metal").armor().meleeHarvest().ranged();
        buildGeneric(TCMaterials.aeternium,         0xFF0c100f, 0xFF282f2d, 0xFF495e5a, 0xFF697876, 0xFF8d9a96, 0xFFbdcbc9, addStats(HandleMaterialStats.ID), "metal").armor().meleeHarvest().ranged();
        buildMaterial(TCMaterials.valkyrum)
                .armor().meleeHarvest()
                .transformer(transformerFromSprite(getResource("generator/valkyrum"), 1, 0));
        buildGeneric(TCMaterials.neptune,           0xFF111b42, 0xFF1a2a66, 0xFF29439c, 0xFF3559c6, 0xFF3e6fd8, 0xFF7cbbff, addStats(), "metal").maille();
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
