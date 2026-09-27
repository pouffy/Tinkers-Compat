package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.material.MaterialCreator;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.crafting.conditions.ICondition;
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
        int count = 0;
        for (MaterialCreator creator : MaterialCreator.materialCreators) {
            if(creator.isVariant()) continue;
            addMaterial(creator.getId().getId(), creator.getData().getTier(), creator.getData().getOrder(), creator.getData().isCraftable(), creator.getData().isHidden(), creator.getData().getCondition());
            count++;
        }
        TCompat.LOGGER.info("Added data for {} materials", count);
    }

    private ICondition tagFull(String namespace, String path) {
        return new NotCondition(new TagEmptyCondition(namespace, path));
    }
}
