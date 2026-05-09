package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
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
        for (MaterialBuilder builder : MaterialBuilder.materialBuilders) {
            if(!builder.isVariant()) {
                addMaterial(builder.getId().getId(), builder.getData().getTier(), builder.getData().getOrder(), builder.getData().isCraftable(), builder.getData().isHidden(), builder.getData().getCondition());
            }
        }
    }

    private ICondition tagFull(String namespace, String path) {
        return new NotCondition(new TagEmptyCondition(namespace, path));
    }
}
