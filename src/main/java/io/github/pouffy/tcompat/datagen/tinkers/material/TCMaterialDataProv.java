package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.common.material.TCMaterials;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

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

        ICondition brass = tagFull("forge", "ingots/brass");
        ICondition zinc = tagFull("forge", "ingots/zinc");

        addMaterial(TCMaterials.aetherWood, 1, ORDER_HARVEST, true, false, aether);
        addMaterial(TCMaterials.aetherRock, 1, ORDER_HARVEST, true, false, aether);
        addMaterial(TCMaterials.zanite, 2, ORDER_HARVEST, false, false, aether);
        addMaterial(TCMaterials.gravitite, 3, ORDER_HARVEST, false, false, aether);

        addMaterial(TCMaterials.skyjade, 2, ORDER_HARVEST, false, false, deepAether);

        addMaterial(TCMaterials.veridium, 3, ORDER_HARVEST, false, false, aetherRedux);
        addMaterial(TCMaterials.refinedSentrite, 4, ORDER_HARVEST, false, false, aetherRedux);
        addMaterial(TCMaterials.blightbunnyFang, 2, ORDER_RANGED, true, false, aetherRedux);
        addMaterial(TCMaterials.mykapodShell, 2, ORDER_WEAPON, true, false, aetherRedux);

        addMaterial(TCMaterials.hellbark, 1, ORDER_HARVEST, true, false, bop);
        addMaterial(TCMaterials.wickedWax, 1, ORDER_BINDING, true, false, species);

        addMaterial(TCMaterials.pyral, 1, ORDER_SPECIAL, false, false, aetherTreasure);
        addMaterial(TCMaterials.valkyrum, 1, ORDER_SPECIAL, false, false, aetherTreasure);
        addMaterial(TCMaterials.neptune, 2, ORDER_SPECIAL, true, false, aetherTreasure);

        //addMaterial(TCMaterials.zinc, 2, ORDER_HARVEST, false, false, zinc);
        //addMaterial(TCMaterials.brass, 2, ORDER_HARVEST, false, false, brass);
    }

    private ICondition tagFull(String namespace, String path) {
        return new NotCondition(new TagEmptyCondition(namespace, path));
    }
}
