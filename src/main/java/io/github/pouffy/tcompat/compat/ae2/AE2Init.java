package io.github.pouffy.tcompat.compat.ae2;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.ae2.recipe.MaterialUpgradeRecipe;
import io.github.pouffy.tcompat.compat.ae2.recipe.SmithingMaterialUpgradeRecipe;
import net.minecraft.SharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;

import java.nio.file.Path;

public class AE2Init extends CompatInitializer {
    public static final DeferredRegister<RecipeSerializer<?>> AE2_RS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, TCompat.MOD_ID);

    public static final RegistryObject<RecipeSerializer<MaterialUpgradeRecipe>> materialUpgradeSerializer = AE2_RS.register("material_upgrade", () -> LoadableRecipeSerializer.of(MaterialUpgradeRecipe.LOADER));
    public static final RegistryObject<RecipeSerializer<SmithingMaterialUpgradeRecipe>> smithingMaterialUpgradeSerializer = AE2_RS.register("smithing_material_upgrade", () -> LoadableRecipeSerializer.of(SmithingMaterialUpgradeRecipe.LOADER));

    public static void init(IEventBus eventBus) {
        AE2_RS.register(eventBus);
        AE2Materials.staticInit();
        eventBus.addListener(AE2Init::addQuartzPack);
    }

    /**
     * Like AE2's nether quartz tools: a built-in pack, only present with AE2, gives Tinkers' quartz head stats (merged with its arrow head stats),
     * makes it craftable in the part builder and adds the head sprites. Required, so it is active in every world.
     */
    @SuppressWarnings("resource")
    private static void addQuartzPack(AddPackFindersEvent event) {
        if (!ModList.get().isLoaded("ae2")) return;
        IModFile file = ModList.get().getModFileById(TCompat.MOD_ID).getFile();
        Path path = file.findResource("packs/ae2_quartz");
        PathPackResources pack = new PathPackResources(file.getFileName() + ":" + path, true, path);
        event.addRepositorySource(source -> source.accept(Pack.create(
                "builtin/tcompat_ae2_quartz",
                Component.literal("TCompat: AE2 Nether Quartz Tools"),
                true,
                id -> pack,
                new Pack.Info(Component.literal("Nether quartz tool heads, like AE2"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), FeatureFlagSet.of(), false),
                event.getPackType(),
                Pack.Position.TOP,
                false,
                PackSource.BUILT_IN)));
    }
}
