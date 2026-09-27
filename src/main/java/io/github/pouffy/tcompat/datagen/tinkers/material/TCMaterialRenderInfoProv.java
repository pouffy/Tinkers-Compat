package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.material.MaterialCreator;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;

import java.util.Arrays;

@MethodsReturnNonnullByDefault
public class TCMaterialRenderInfoProv extends AbstractMaterialRenderInfoProvider {
    public TCMaterialRenderInfoProv(PackOutput packOutput, @Nullable AbstractMaterialSpriteProvider materialSprites, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, materialSprites, existingFileHelper);
    }

    @Override
    protected void addMaterialRenderInfo() {
        int count = 0;
        int variantCount = 0;
        for (MaterialCreator creator : MaterialCreator.materialCreators) {
            if (creator.getSpriteInfo().getTransformer() == null) {
                continue;
            }
            TCompat.LOGGER.info("Adding render info for material: {}", creator.getId());
            var riBuilder = buildRenderInfo(creator.getId());
            var color = creator.getRenderInfo().getColor();
            if (color != -1) {
                riBuilder.color(color);
            }
            var fallbacks = creator.getRenderInfo().getFallbacks();
            if (!Arrays.equals(fallbacks, new String[0])) {
                riBuilder.fallbacks(fallbacks);
            }
            var texture = creator.getRenderInfo().getTexture();
            if (texture != null) {
                riBuilder.texture(texture);
            }
            var luminosity = creator.getRenderInfo().getLuminosity();
            if (luminosity != 0) {
                riBuilder.luminosity(luminosity);
            }
            var parent = creator.getRenderInfo().getParent();
            if (parent != null) {
                riBuilder.parent(parent);
            }
            var generator = creator.getRenderInfo().getGenerator();
            if (generator != null) {
                riBuilder.generator(generator);
            }
            if (creator.isVariant()) {
                variantCount++;
            } else count++;
        }
        TCompat.LOGGER.info("Added render info for {} materials", count);
        TCompat.LOGGER.info("Added render info for {} material variants", variantCount);
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Render Info";
    }
}
