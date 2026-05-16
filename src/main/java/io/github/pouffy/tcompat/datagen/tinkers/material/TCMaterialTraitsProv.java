package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;

@MethodsReturnNonnullByDefault
public class TCMaterialTraitsProv extends AbstractMaterialTraitDataProvider {
    public TCMaterialTraitsProv(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Traits";
    }

    @Override
    protected void addMaterialTraits() {
        for (MaterialBuilder builder : MaterialBuilder.materialBuilders) {
            if(builder.isVariant()) continue;
            addDefaultTraits(builder.getId().getId(), builder.getTraits().defaultTraits().toArray(new ModifierEntry[0]));
            for (var statEntry : builder.getTraits().traits().entrySet()) {
                addTraits(builder.getId().getId(), statEntry.getKey(), statEntry.getValue().toArray(new ModifierEntry[0]));
            }
        }
        //addTraits(TCMaterials.cursium, RANGED, TCModifiers.cataclysmic);
        //addTraits(TCMaterials.ignitium, MELEE_HARVEST, TCModifiers.cataclysmic);
        //addTraits(TCMaterials.witherite, RANGED, TCModifiers.cataclysmic);
    }
}
