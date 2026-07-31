package io.github.pouffy.tcompat.compat.caverns_chasms;

import io.github.pouffy.tcompat.common.fluid.TCFluids;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCByproduct;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.data.Byproduct;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CavernsChasmsSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);
        metal(cConsumer, TCFluids.moltenNecromium, compatModId()).metal(9, true).optional();

        gem(cConsumer, TCFluids.moltenSpinel, compatModId())
                .oreRate(IMeltingContainer.OreRateType.GEM)
                .ore(TCByproduct.ENDER)
                .baseUnit(100)
                .damageUnit(FluidValues.GEM_SHARD)
                .melting(9, "block", "storage_blocks", 3.0f, false, true)
                .blockCasting(9, Ingredient.EMPTY, true)
                .meltingCasting(1, TinkerSmeltery.gemCast, 1.0f, true)
                .sparseOre(0.5f).singularOre(1).denseOre(3);
        gem(cConsumer, TCFluids.moltenTurquoise, compatModId())
                .oreRate(IMeltingContainer.OreRateType.GEM)
                .ore(Byproduct.SMALL_DIAMOND)
                .baseUnit(100)
                .damageUnit(FluidValues.GEM_SHARD)
                .melting(9, "block", "storage_blocks", 3.0f, false, true)
                .blockCasting(9, Ingredient.EMPTY, true)
                .meltingCasting(1, TinkerSmeltery.gemCast, 1.0f, true)
                .sparseOre(0.5f).singularOre(1).denseOre(3);
        casting(cConsumer);
    }
    private void casting(Consumer<FinishedRecipe> consumer) {
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("sugilite")))
                .setCast(Items.GRANITE, true)
                .setFluidAndTime(TCFluids.moltenSpinel, FluidValues.GEM)
                .save(consumer, location(gemFolder("casting") + "/spinel/sugilite"));
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("cassiterite")))
                .setCast(Items.GRANITE, true)
                .setFluidAndTime(TinkerFluids.moltenTin, FluidValues.INGOT)
                .save(consumer, location(metalFolder("casting") + "/tin/cassiterite"));
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("cylindrite")))
                .setCast(Items.DEEPSLATE, true)
                .setFluidAndTime(TinkerFluids.moltenTin, FluidValues.INGOT)
                .save(consumer, location(metalFolder("casting") + "/tin/cylindrite"));

        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("magmatic_rhyolite")))
                .setCast(ItemNameIngredient.from(compatId("rhyolite")), true)
                .setFluidAndTime(TinkerFluids.magma, FluidValues.SLIMEBALL)
                .save(consumer, location(miscFolder("casting") + "/magma/magmatic_rhyolite"));
    }

    @Override
    public String compatModId() {
        return "caverns_and_chasms";
    }
}
