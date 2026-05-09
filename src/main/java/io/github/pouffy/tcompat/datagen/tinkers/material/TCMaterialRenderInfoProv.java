package io.github.pouffy.tcompat.datagen.tinkers.material;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.material.MaterialBuilder;
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
        for (MaterialBuilder builder : MaterialBuilder.materialBuilders) {
            if (builder.getSpriteInfo().getTransformer() == null) {
                continue;
            }
            TCompat.LOGGER.info("Adding render info for material: {}", builder.getId());
            var riBuilder = buildRenderInfo(builder.getId());
            var color = builder.getRenderInfo().getColor();
            if (color != -1) {
                riBuilder.color(color);
            }
            var fallbacks = builder.getRenderInfo().getFallbacks();
            if (!Arrays.equals(fallbacks, new String[0])) {
                riBuilder.fallbacks(fallbacks);
            }
            var texture = builder.getRenderInfo().getTexture();
            if (texture != null) {
                riBuilder.texture(texture);
            }
            var luminosity = builder.getRenderInfo().getLuminosity();
            if (luminosity != 0) {
                riBuilder.luminosity(luminosity);
            }
            var parent = builder.getRenderInfo().getParent();
            if (parent != null) {
                riBuilder.parent(parent);
            }
            var generator = builder.getRenderInfo().getGenerator();
            if (generator != null) {
                riBuilder.generator(generator);
            }
        }
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Material Render Info";
    }
}
