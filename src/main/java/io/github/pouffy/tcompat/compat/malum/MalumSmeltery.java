package io.github.pouffy.tcompat.compat.malum;

import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.function.Consumer;

public class MalumSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);
        metal(cConsumer, TCFluids.moltenSoulStainedSteel, compatModId()).metal(9, true).optional();
        metal(cConsumer, TCFluids.moltenHallowedGold, compatModId()).metal(9, true).optional();
        metal(cConsumer, TCFluids.moltenMalignantPewter, compatModId()).metal(9, true).optional();

        gem(cConsumer, TCFluids.moltenBlazingQuartz, compatModId())
                .oreRate(IMeltingContainer.OreRateType.GEM)
                .baseUnit(FluidValues.GEM)
                .damageUnit(FluidValues.GEM_SHARD)
                .melting(9, "block", "storage_blocks", 3.0f, false, true)
                .blockCasting(9, Ingredient.EMPTY, true)
                .meltingCasting(1, TinkerSmeltery.gemCast, 1.0f, false)
                .meltingCasting(1 / 8f, TinkerSmeltery.nuggetCast, 1 / 3f, false);

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("runic_sap")))
                .setFluidAndTime(TCFluids.runicSap, FluidValues.BOTTLE / 2)
                .setCast(Items.GLASS_BOTTLE, true).setCoolingTime(1)
                .save(cConsumer, location(miscFolder("casting") + "/sap/runic"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("cursed_sap")))
                .setFluidAndTime(TCFluids.cursedSap, FluidValues.BOTTLE / 2)
                .setCast(Items.GLASS_BOTTLE, true).setCoolingTime(1)
                .save(cConsumer, location(miscFolder("casting") + "/sap/cursed"));
    }

    @Override
    public String compatModId() {
        return "malum";
    }
}
