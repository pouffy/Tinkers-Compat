package io.github.pouffy.tcompat.compat.ae2;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.ae2.recipe.MaterialUpgradeRecipe;
import io.github.pouffy.tcompat.compat.ae2.recipe.SmithingMaterialUpgradeRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;

public class AE2Init extends CompatInitializer {
    public static final DeferredRegister<RecipeSerializer<?>> AE2_RS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, TCompat.MOD_ID);

    public static final RegistryObject<RecipeSerializer<MaterialUpgradeRecipe>> materialUpgradeSerializer = AE2_RS.register("material_upgrade", () -> LoadableRecipeSerializer.of(MaterialUpgradeRecipe.LOADER));
    public static final RegistryObject<RecipeSerializer<SmithingMaterialUpgradeRecipe>> smithingMaterialUpgradeSerializer = AE2_RS.register("smithing_material_upgrade", () -> LoadableRecipeSerializer.of(SmithingMaterialUpgradeRecipe.LOADER));

    public static void init(IEventBus eventBus) {
        AE2_RS.register(eventBus);
        AE2Materials.staticInit();
    }
}
