package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.material.MaterialCreator;
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
        int count = 0;
        for (MaterialCreator creator : MaterialCreator.materialCreators) {
            if(creator.isVariant()) continue;
            addDefaultTraits(creator.getId().getId(), creator.getTraits().defaultTraits().toArray(new ModifierEntry[0]));
            for (var statEntry : creator.getTraits().traits().entrySet()) {
                addTraits(creator.getId().getId(), statEntry.getKey(), statEntry.getValue().toArray(new ModifierEntry[0]));
            }
            count++;
        }
        TCompat.LOGGER.info("Added traits for {} materials", count);
    }
}
