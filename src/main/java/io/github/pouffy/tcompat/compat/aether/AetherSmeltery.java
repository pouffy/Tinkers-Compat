package io.github.pouffy.tcompat.compat.aether;

import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.common.data.TCShapedRecipeBuilder;
import io.github.pouffy.tcompat.common.data.TCShapelessRecipeBuilder;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import io.github.pouffy.tcompat.datagen.tinkers.recipe.TCByproduct;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.List;
import java.util.function.Consumer;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class AetherSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);

        //Generic Melting
        gem(cConsumer, TCFluids.moltenZanite, compatModId())
                .baseUnit(FluidValues.INGOT).damageUnit(FluidValues.NUGGET).oreRate(IMeltingContainer.OreRateType.METAL)
                .sparseOre(0.5f).singularOre(1).denseOre(3).ore(TCByproduct.QUARTZ)
                .melting(9, "block", "storage_blocks", (float)Math.sqrt(9), false, true)
                .blockCasting(9, Ingredient.EMPTY, true)
                .meltingCasting(1, TinkerSmeltery.gemCast, 1.0f, true);

        metal(cConsumer, TCFluids.moltenGravitite, compatModId()).ore(TCByproduct.ZANITE).metal(true).optional();
        metal(cConsumer, TCFluids.moltenLightnum, compatModId()).metal(true).optional();
        metal(cConsumer, TCFluids.moltenDraculite, compatModId()).metal(true).optional();

        simpleMelting(cConsumer, TCFluids.moltenZanite, FluidValues.INGOT, "zanite", ItemNameIngredient.from(compatId("altar")), gemFolder("melting"), "altar");

        //Gloves
        glovesMelting(cConsumer, TinkerFluids.moltenIron, (FluidValues.NUGGET * 6) * 2, "chainmail", ItemNameIngredient.from(compatId("chainmail_gloves")), metalFolder("melting"), true, new int[]{FluidValues.NUGGET}, TinkerFluids.moltenSteel.result((FluidValues.NUGGET * 3) * 2));
        glovesMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 2, "iron", ItemNameIngredient.from(compatId("iron_gloves")), metalFolder("melting"), true, new int[]{FluidValues.NUGGET});
        glovesMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 2, "gold", ItemNameIngredient.from(compatId("golden_gloves")), metalFolder("melting"), true, new int[]{FluidValues.NUGGET});
        glovesMelting(cConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM * 2, "diamond", ItemNameIngredient.from(compatId("diamond_gloves")), gemFolder("melting"), true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(cConsumer, TinkerFluids.moltenNetherite, FluidValues.INGOT, "netherite", ItemNameIngredient.from(compatId("netherite_gloves")), metalFolder("melting"), true, new int[]{FluidValues.NUGGET, FluidValues.GEM_SHARD}, TinkerFluids.moltenDiamond.result(FluidValues.GEM * 2));
        glovesMelting(cConsumer, TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK * 2, "obsidian", ItemNameIngredient.from(compatId("obsidian_gloves")), miscFolder("melting"), true, new int[]{FluidValues.GLASS_PANE});
        glovesMelting(cConsumer, TCFluids.moltenZanite, FluidValues.INGOT * 2, "zanite", ItemNameIngredient.from(compatId("zanite_gloves")), gemFolder("melting"), true, new int[]{FluidValues.GEM_SHARD});
        glovesMelting(cConsumer, TCFluids.moltenGravitite, FluidValues.INGOT * 2, "gravitite", ItemNameIngredient.from(compatId("gravitite_gloves")), metalFolder("melting"), true, new int[]{FluidValues.NUGGET});
        //Rings
        ringMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 4, "iron", ItemNameIngredient.from(compatId("iron_ring")), metalFolder("melting"), true);
        ringMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 4, "gold", ItemNameIngredient.from(compatId("golden_ring")), metalFolder("melting"), true);
        ringMelting(cConsumer, TCFluids.moltenZanite, FluidValues.INGOT * 4, "zanite", ItemNameIngredient.from(compatId("zanite_ring")), gemFolder("melting"), true);
        //Pendant
        pendantMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT, "iron", ItemNameIngredient.from(compatId("iron_pendant")), metalFolder("melting"), true);
        pendantMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.INGOT, "gold", ItemNameIngredient.from(compatId("golden_pendant")), metalFolder("melting"), true);
        pendantMelting(cConsumer, TCFluids.moltenZanite, FluidValues.INGOT, "zanite", ItemNameIngredient.from(compatId("zanite_pendant")), gemFolder("melting"), true);
        //Salvaging
        salvageAll(cConsumer, this::compatId, TCFluids.moltenZanite, FluidValues.INGOT, "zanite", new int[]{FluidValues.NUGGET}, gemFolder("melting"));
        salvageAll(cConsumer, this::compatId, TCFluids.moltenGravitite, FluidValues.INGOT, "gravitite", new int[]{FluidValues.NUGGET}, metalFolder("melting"));
        salvageArmor(cConsumer, this::compatId, TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK, "obsidian", new int[]{FluidValues.GLASS_PANE}, miscFolder("melting"));

        simpleMelting(cConsumer, TCFluids.moltenLightnum, FluidValues.INGOT * 2, "lightnum", ItemNameIngredient.from(compatId("lightning_sword")), metalFolder("melting"), "sword");
        simpleMelting(cConsumer, TCFluids.moltenLightnum, FluidValues.INGOT, "lightnum", ItemNameIngredient.from(compatId("lightning_knife")), metalFolder("melting"), "knife");
        simpleMelting(cConsumer, TCFluids.moltenDraculite, FluidValues.INGOT * 2, "draculite", ItemNameIngredient.from(compatId("vampire_blade")), metalFolder("melting"), "sword");
        simpleMelting(cConsumer, TCFluids.moltenDraculite, FluidValues.INGOT, "draculite", listedInput("life_shard", "regeneration_stone"), metalFolder("melting"), "ingot_1");

        nugget(cConsumer, "lightnum", TCTags.Items.LIGHTNUM_NUGGETS, TCTags.Items.LIGHTNUM_INGOTS);
        nugget(cConsumer, "draculite", TCTags.Items.DRACULITE_NUGGETS, TCTags.Items.DRACULITE_INGOTS);
    }

    void nugget(Consumer<FinishedRecipe> consumer, String type, TagKey<Item> nugget, TagKey<Item> ingot) {
        TCShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, getResource("%s_nugget".formatted(type)), 9)
                .requires(ingot)
                .unlockedBy("has_item", new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[]{ItemPredicate.Builder.item().of(ingot).build()}))
                .save(consumer, location("common/materials/%s_nugget_from_ingot".formatted(type)));
        TCShapedRecipeBuilder.shaped(RecipeCategory.MISC, getResource("%s_ingot".formatted(type)))
                .pattern("###").pattern("###").pattern("###")
                .define('#', nugget)
                .unlockedBy("has_item", new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[]{ItemPredicate.Builder.item().of(nugget).build()}))
                .save(consumer, location("common/materials/%s_ingot_from_nuggets".formatted(type)));
    }

    @Override
    public String compatModId() {
        return "aether";
    }
}
