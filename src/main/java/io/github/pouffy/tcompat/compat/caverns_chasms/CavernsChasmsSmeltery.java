package io.github.pouffy.tcompat.compat.caverns_chasms;

import io.github.pouffy.tcompat.common.fluid.TCFluids;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCByproduct;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
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

        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", oxidised("copper_ingot", true), metalFolder("melting"), "oxidised_ingot");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.NUGGET * 11, "copper", oxidised("chiseled_copper", false), metalFolder("melting"), "chiseled");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.METAL_BLOCK, "copper", oxidised("copper_grate", false), metalFolder("melting"), "grate");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.INGOT / 2, "copper", CompoundIngredient.of(oxidised("copper_bricks", false), oxidised("copper_brick_wall", false), oxidised("chiseled_copper_bricks", false)), metalFolder("melting"), "bricks");
        simpleMelting(consumer, TinkerFluids.moltenCopper, 65, "copper", oxidised("copper_brick_stairs", false), metalFolder("melting"), "brick_stairs");
        simpleMelting(consumer, TinkerFluids.moltenCopper, 20, "copper", oxidised("copper_brick_slab", false), metalFolder("melting"), "brick_slab");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", oxidised("copper_button", false), metalFolder("melting"), "button");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 2, "copper", oxidised("copper_door", false), metalFolder("melting"), "door");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 4, "copper", oxidised("copper_trapdoor", false), metalFolder("melting"), "trapdoor");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.NUGGET * 3, "copper", CompoundIngredient.of(oxidised("copper_bars", false), oxidised("copper_chain", false)), metalFolder("melting"), "bars");
        simpleMelting(consumer, TinkerFluids.moltenCopper, 605, "copper", oxidised("copper_bulb", false), metalFolder("melting"), "bulb");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.NUGGET * 8, "copper", oxidised("copper_lantern", false), metalFolder("melting"), "lantern");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 30, "copper", oxidised("toolbox", false), metalFolder("melting"), "toolbox");
        simpleMelting(consumer, TinkerFluids.moltenCopper, FluidValues.NUGGET * 2, "copper", oxidised("copper_rail", false), metalFolder("melting"), "rail");

        platedBricks(cConsumer, TinkerFluids.moltenIron, "iron");
        platedBricks(cConsumer, TinkerFluids.moltenTin, "tin");
        platedBricks(cConsumer, TinkerFluids.moltenGold, "gold");
        platedBricks(cConsumer, TinkerFluids.moltenSilver, "silver");

        simpleMelting(consumer, TinkerFluids.moltenTin, FluidValues.INGOT, "tin", listedInput("saddled_egg", "hold_button"), metalFolder("melting"), "egg");
        simpleMelting(consumer, TinkerFluids.moltenTin, FluidValues.INGOT * 2, "tin", listedInput("roller_door", "roller_window", "hold_plate"), metalFolder("melting"), "roller");

        simpleMelting(consumer, TinkerFluids.moltenTin, FluidValues.NUGGET * 3, "tin", listedInput("tin_bars"), metalFolder("melting"), "bars");
        simpleMelting(consumer, TinkerFluids.moltenSilver, FluidValues.NUGGET * 3, "silver", listedInput("silver_bars"), metalFolder("melting"), "bars");
        simpleMelting(consumer, TinkerFluids.moltenGold, FluidValues.NUGGET * 3, "gold", listedInput("golden_bars"), metalFolder("melting"), "bars");

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
        ItemCastingRecipeBuilder.basinRecipe(ItemNameOutput.fromName(compatId("turquoise_tiles")))
                .setCast(Items.STONE_BRICKS, true)
                .setFluidAndTime(TCFluids.moltenTurquoise, 10)
                .save(consumer, location(gemFolder("casting") + "/turquoise/tiles"));

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("tin_bars")))
                .setFluidAndTime(TinkerFluids.moltenTin, FluidValues.NUGGET * 3)
                .save(consumer, location(metalFolder("casting") + "/tin/bars"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("silver_bars")))
                .setFluidAndTime(TinkerFluids.moltenSilver, FluidValues.NUGGET * 3)
                .save(consumer, location(metalFolder("casting") + "/silver/bars"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("copper_bars")))
                .setFluidAndTime(TinkerFluids.moltenCopper, FluidValues.NUGGET * 3)
                .save(consumer, location(metalFolder("casting") + "/copper/bars"));
    }

    private void platedBricks(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, String name) {
        simpleMelting(consumer, fluid, FluidValues.INGOT / 2, name, listedInput(name + "_bricks", name + "_brick_wall", "chiseled_" + name + "_bricks"), metalFolder("melting"), "bricks");
        simpleMelting(consumer, fluid, 65, name, listedInput(name + "_brick_stairs"), metalFolder("melting"), "brick_stairs");
        simpleMelting(consumer, fluid, 20, name, listedInput(name + "_brick_slab"), metalFolder("melting"), "brick_slab");
    }

    private Ingredient oxidised(String id, boolean ignoreRegular) {
        List<ResourceLocation> list = new ArrayList<>();
        ResourceLocation primaryId = compatId(id);
        if (!ignoreRegular) list.add(primaryId);

        list.add(primaryId.withPrefix("exposed_"));
        list.add(primaryId.withPrefix("weathered_"));
        list.add(primaryId.withPrefix("oxidized_"));
        list.add(primaryId.withPrefix("waxed_"));
        list.add(primaryId.withPrefix("waxed_exposed_"));
        list.add(primaryId.withPrefix("waxed_weathered_"));
        list.add(primaryId.withPrefix("waxed_oxidized_"));
        return ItemNameIngredient.from(list);
    }

    @Override
    public String compatModId() {
        return "caverns_and_chasms";
    }
}
