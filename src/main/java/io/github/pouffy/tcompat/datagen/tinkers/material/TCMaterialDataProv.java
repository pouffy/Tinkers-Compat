package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.common.material.TCMaterials;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

@SuppressWarnings("SameParameterValue")
@MethodsReturnNonnullByDefault
public class TCMaterialDataProv extends AbstractMaterialDataProvider {
    public TCMaterialDataProv(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Materials";
    }

    @Override
    protected void addMaterials() {
        ICondition aether = new ModLoadedCondition("aether");
        ICondition deepAether = new ModLoadedCondition("deep_aether");
        ICondition aetherRedux = new ModLoadedCondition("aether_redux");
        ICondition aetherTreasure = new ModLoadedCondition("aether_treasure_reforging");
        ICondition bop = new ModLoadedCondition("biomesoplenty");
        ICondition species = new ModLoadedCondition("species");
        ICondition adAstra = new ModLoadedCondition("ad_astra");
        ICondition betterend = new ModLoadedCondition("betterend");
        ICondition betternether = new ModLoadedCondition("betternether");
        ICondition iceandfire = new ModLoadedCondition("iceandfire");

        //ICondition brass = tagFull("forge", "ingots/brass");
        //ICondition zinc = tagFull("forge", "ingots/zinc");

        addMaterial(TCMaterials.aetherWood, 1, ORDER_HARVEST, true, false, aether);
        addMaterial(TCMaterials.aetherRock, 1, ORDER_HARVEST, true, false, aether);
        addMaterial(TCMaterials.zanite, 2, ORDER_HARVEST, false, false, aether);
        addMaterial(TCMaterials.gravitite, 3, ORDER_HARVEST, false, false, aether);
        addMaterial(TCMaterials.lightnum, 3, ORDER_HARVEST, false, false, aether);
        addMaterial(TCMaterials.draculite, 3, ORDER_HARVEST, false, false, aether);

        addMaterial(TCMaterials.skyjade, 2, ORDER_HARVEST, false, false, deepAether);
        addMaterial(TCMaterials.stormforgedSteel, 2, ORDER_HARVEST, false, false, deepAether);

        addMaterial(TCMaterials.veridium, 3, ORDER_HARVEST, false, false, aetherRedux);
        addMaterial(TCMaterials.refinedSentrite, 4, ORDER_HARVEST, false, false, aetherRedux);
        addMaterial(TCMaterials.blightbunnyFang, 2, ORDER_RANGED, true, false, aetherRedux);
        addMaterial(TCMaterials.mykapodShell, 2, ORDER_WEAPON, true, false, aetherRedux);

        addMaterial(TCMaterials.hellbark, 1, ORDER_HARVEST, true, false, bop);
        addMaterial(TCMaterials.wickedWax, 1, ORDER_BINDING, true, false, species);

        addMaterial(TCMaterials.pyral, 1, ORDER_SPECIAL, false, false, aetherTreasure);
        addMaterial(TCMaterials.valkyrum, 1, ORDER_SPECIAL, false, false, aetherTreasure);
        addMaterial(TCMaterials.neptune, 2, ORDER_SPECIAL, false, false, aetherTreasure);

        addMaterial(TCMaterials.desh, 2, ORDER_SPECIAL, false, false, adAstra);
        addMaterial(TCMaterials.calorite, 2, ORDER_SPECIAL, false, false, adAstra);
        addMaterial(TCMaterials.ostrum, 2, ORDER_SPECIAL, false, false, adAstra);

        addMaterial(TCMaterials.thallasium, 2, ORDER_SPECIAL, false, false, betterend);
        addMaterial(TCMaterials.terminite, 2, ORDER_SPECIAL, false, false, betterend);
        addMaterial(TCMaterials.aeternium, 2, ORDER_SPECIAL, false, false, betterend);

        addMaterial(TCMaterials.cincinnasite, 2, ORDER_HARVEST, false, false, betternether);
        addMaterial(TCMaterials.netherRuby, 2, ORDER_HARVEST, false, false, betternether);

        addMaterial(TCMaterials.fireDragonsteel, 4, ORDER_HARVEST, false, false, iceandfire);
        addMaterial(TCMaterials.iceDragonsteel, 4, ORDER_HARVEST, false, false, iceandfire);
        addMaterial(TCMaterials.lightningDragonsteel, 4, ORDER_HARVEST, false, false, iceandfire);
        addMaterial(TCMaterials.dragonBone,   4, ORDER_COMPAT, true, false, iceandfire);
        addMaterial(TCMaterials.dragonScaleFire, 4, ORDER_COMPAT, true, false, iceandfire);
        addMaterial(TCMaterials.dragonScaleIce, 4, ORDER_COMPAT, true, false, iceandfire);
        addMaterial(TCMaterials.dragonScaleLightning, 4, ORDER_COMPAT, true, false, iceandfire);


        //addMaterial(TCMaterials.zinc, 2, ORDER_HARVEST, false, false, zinc);
        //addMaterial(TCMaterials.brass, 2, ORDER_HARVEST, false, false, brass);
    }

    private ICondition tagFull(String namespace, String path) {
        return new NotCondition(new TagEmptyCondition(namespace, path));
    }
}
