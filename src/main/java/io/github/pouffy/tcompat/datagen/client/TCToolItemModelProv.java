package io.github.pouffy.tcompat.datagen.client;

import com.google.gson.JsonObject;
import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.library.data.AbstractToolItemModelProvider;

import java.io.IOException;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class TCToolItemModelProv extends AbstractToolItemModelProvider {
    public TCToolItemModelProv(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, existingFileHelper, TCompat.MOD_ID);
    }

    @Override
    protected void addModels() throws IOException {
        JsonObject toolBlocking = readJson(getResource("tconstruct", "base/tool_blocking"));

        tool(IFInit.glaive, toolBlocking, "head");
    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Tool Item Models";
    }
}
