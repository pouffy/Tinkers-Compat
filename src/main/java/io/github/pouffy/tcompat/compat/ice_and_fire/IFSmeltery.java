package io.github.pouffy.tcompat.compat.ice_and_fire;

import io.github.pouffy.tcompat.common.data.TCShapedRecipeBuilder;
import io.github.pouffy.tcompat.common.data.TCShapelessRecipeBuilder;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import io.github.pouffy.tcompat.compat.betternether.BetternetherInit;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.pouffy.tcompat.TCompat.getResource;
import static slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe.getTemperature;

public class IFSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);
        metal(cConsumer, IFInit.moltenFireDragonsteel, compatModId()).metal(9, true).optional();
        metal(cConsumer, IFInit.moltenIceDragonsteel, compatModId()).metal(9, true).optional();
        metal(cConsumer, IFInit.moltenLightningDragonsteel, compatModId()).metal(9, true).optional();

        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 4, "iron", listedInput(
                "dragonforge_fire_input",
                "dragonforge_ice_input",
                "dragonforge_lightning_input"
        ), metalFolder("melting"), "dragonforge_aperture");
        simpleMelting(cConsumer, TinkerFluids.moltenGlass, FluidValues.GLASS_BLOCK * 7, "glass", listedInput(
                "pixie_jar_empty",
                "pixie_jar_0",
                "pixie_jar_1",
                "pixie_jar_2",
                "pixie_jar_3",
                "pixie_jar_4"
        ), miscFolder("melting"), "pixie_jar");

        salvageTools(cConsumer, this::compatId, TinkerFluids.moltenSilver, FluidValues.INGOT, "silver", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder("melting"));
        salvageTools(cConsumer, this::compatId, TinkerFluids.moltenCopper, FluidValues.INGOT, "copper", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder("melting"));
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.INGOT * 5, "silver", ItemNameIngredient.from(getResource("iceandfire:armor_silver_metal_helmet")), metalFolder("melting"), "helmet");
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.INGOT * 8, "silver", ItemNameIngredient.from(getResource("iceandfire:armor_silver_metal_chestplate")), metalFolder("melting"), "chestplate");
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.INGOT * 7, "silver", ItemNameIngredient.from(getResource("iceandfire:armor_silver_metal_leggings")), metalFolder("melting"), "leggings");
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.INGOT * 4, "silver", ItemNameIngredient.from(getResource("iceandfire:armor_silver_metal_boots")), metalFolder("melting"), "boots");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 5, "copper", ItemNameIngredient.from(getResource("iceandfire:armor_copper_metal_helmet")), metalFolder("melting"), "helmet");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 8, "copper", ItemNameIngredient.from(getResource("iceandfire:armor_copper_metal_chestplate")), metalFolder("melting"), "chestplate");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 7, "copper", ItemNameIngredient.from(getResource("iceandfire:armor_copper_metal_leggings")), metalFolder("melting"), "leggings");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.INGOT * 4, "copper", ItemNameIngredient.from(getResource("iceandfire:armor_copper_metal_boots")), metalFolder("melting"), "boots");
        dragonArmorMelting(cConsumer, "iron", TinkerFluids.moltenIron);
        dragonArmorMelting(cConsumer, "copper", TinkerFluids.moltenCopper);
        dragonArmorMelting(cConsumer, "gold", TinkerFluids.moltenGold);
        dragonArmorMelting(cConsumer, "diamond", TinkerFluids.moltenDiamond);
        dragonArmorMelting(cConsumer, "silver", TinkerFluids.moltenSilver);
        dragonArmorMelting(cConsumer, "fire_dragonsteel", "dragonsteel_fire", IFInit.moltenFireDragonsteel);
        dragonArmorMelting(cConsumer, "ice_dragonsteel", "dragonsteel_ice", IFInit.moltenFireDragonsteel);
        dragonArmorMelting(cConsumer, "lightning_dragonsteel", "dragonsteel_lightning", IFInit.moltenFireDragonsteel);
        MeltingRecipeBuilder.melting(ItemNameIngredient.from(getResource("iceandfire:pixie_wand")), TinkerFluids.moltenDiamond.result(FluidValues.GEM), getTemperature(TinkerFluids.moltenDiamond), IMeltingRecipe.calcTimeFactor(FluidValues.GEM)).setDamagable(new int[]{FluidValues.GEM_SHARD}).save(consumer, location(gemFolder("melting") + "/diamond/pixie_wand"));
        MeltingRecipeBuilder.melting(ItemNameIngredient.from(getResource("iceandfire:hippogryph_sword")), TinkerFluids.moltenIron.result(FluidValues.INGOT), getTemperature(TinkerFluids.moltenIron), IMeltingRecipe.calcTimeFactor(FluidValues.INGOT)).setDamagable(new int[]{FluidValues.NUGGET}).save(consumer, location(metalFolder("melting") + "/iron/hippogryph_sword"));
        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT, "iron", ItemNameIngredient.from(getResource("iceandfire:dragon_flute")), metalFolder("melting"), "dragon_flute");
        simpleMelting(cConsumer, TinkerFluids.moltenIron, (FluidValues.INGOT * 3) + (FluidValues.NUGGET * 6), "iron", listedInput("chain", "chain_sticky"), metalFolder("melting"), "chain_line");
        var chainLine = (FluidValues.INGOT * 3) + (FluidValues.NUGGET * 6);
        MeltingRecipeBuilder.melting(listedInput(
                "deathworm_gauntlet_yellow",
                "deathworm_gauntlet_white",
                "deathworm_gauntlet_red"
        ), TinkerFluids.moltenIron.result(chainLine), getTemperature(TinkerFluids.moltenIron), IMeltingRecipe.calcTimeFactor(chainLine)).setDamagable(new int[]{FluidValues.NUGGET}).save(consumer, location(metalFolder("melting") + "/iron/deathworm_gauntlet"));

        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT * 7, "iron", ItemNameIngredient.from(getResource("iceandfire:iron_hippogryph_armor")), metalFolder("melting"), "hippogryph_armor");
        simpleMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.INGOT * 7, "gold", ItemNameIngredient.from(getResource("iceandfire:gold_hippogryph_armor")), metalFolder("melting"), "hippogryph_armor");
        simpleMelting(cConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM * 7, "diamond", ItemNameIngredient.from(getResource("iceandfire:diamond_hippogryph_armor")), gemFolder("melting"), "hippogryph_armor");
        simpleMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.NUGGET * 2, "gold", ItemNameIngredient.from(getResource("iceandfire:gold_pile")), metalFolder("melting"), "pile");
        simpleMelting(cConsumer, TinkerFluids.moltenSilver, FluidValues.NUGGET * 2, "silver", ItemNameIngredient.from(getResource("iceandfire:silver_pile")), metalFolder("melting"), "pile");
        simpleMelting(cConsumer, TinkerFluids.moltenCopper, FluidValues.NUGGET * 2, "copper", ItemNameIngredient.from(getResource("iceandfire:copper_pile")), metalFolder("melting"), "pile");
        dragonsteelSet(cConsumer, IFInit.moltenFireDragonsteel, "fire");
        dragonsteelSet(cConsumer, IFInit.moltenIceDragonsteel, "ice");
        dragonsteelSet(cConsumer, IFInit.moltenLightningDragonsteel, "lightning");

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("fire_dragon_blood")))
                .setFluidAndTime(IFInit.fireBlood, FluidValues.BOTTLE)
                .setCast(Items.GLASS_BOTTLE, true)
                .save(cConsumer, location(miscFolder("casting") + "/dragon_blood/fire"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("ice_dragon_blood")))
                .setFluidAndTime(IFInit.iceBlood, FluidValues.BOTTLE)
                .setCast(Items.GLASS_BOTTLE, true)
                .save(cConsumer, location(miscFolder("casting") + "/dragon_blood/ice"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("lightning_dragon_blood")))
                .setFluidAndTime(IFInit.lightningBlood, FluidValues.BOTTLE)
                .setCast(Items.GLASS_BOTTLE, true)
                .save(cConsumer, location(miscFolder("casting") + "/dragon_blood/lightning"));

        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("dragonbone_sword_fire")))
                .setFluidAndTime(IFInit.fireBlood, FluidValues.BOTTLE)
                .setCast(ItemNameIngredient.from(compatId("dragonbone_sword")), true)
                .save(cConsumer, location(miscFolder("casting") + "/dragon_bone_sword/fire"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("dragonbone_sword_ice")))
                .setFluidAndTime(IFInit.iceBlood, FluidValues.BOTTLE)
                .setCast(ItemNameIngredient.from(compatId("dragonbone_sword")), true)
                .save(cConsumer, location(miscFolder("casting") + "/dragon_bone_sword/ice"));
        ItemCastingRecipeBuilder.tableRecipe(ItemNameOutput.fromName(compatId("dragonbone_sword_lightning")))
                .setFluidAndTime(IFInit.lightningBlood, FluidValues.BOTTLE)
                .setCast(ItemNameIngredient.from(compatId("dragonbone_sword")), true)
                .save(cConsumer, location(miscFolder("casting") + "/dragon_bone_sword/lightning"));

        nugget(cConsumer, "fire", TCTags.Items.FIRE_DRAGONSTEEL_NUGGETS, TCTags.Items.FIRE_DRAGONSTEEL_INGOTS);
        nugget(cConsumer, "ice", TCTags.Items.ICE_DRAGONSTEEL_NUGGETS, TCTags.Items.ICE_DRAGONSTEEL_INGOTS);
        nugget(cConsumer, "lightning", TCTags.Items.LIGHTNING_DRAGONSTEEL_NUGGETS, TCTags.Items.LIGHTNING_DRAGONSTEEL_INGOTS);
    }

    void dragonArmorMelting(Consumer<FinishedRecipe> consumer, String name, String idName, FlowingFluidObject<ForgeFlowingFluid> molten) {
        var unit = Objects.equals(name, "diamond") ? FluidValues.LARGE_GEM_BLOCK : FluidValues.METAL_BLOCK;
        Function<String, String> folder = Objects.equals(name, "diamond") ? this::gemFolder : this::metalFolder;
        simpleMelting(consumer, molten, unit * 5, name, listedInput(
                "dragonarmor_"+idName+"_head",
                "dragonarmor_"+idName+"_neck"
        ), folder.apply("melting"), "upper_dragon_armor");
        simpleMelting(consumer, molten, unit * 8, name, ItemNameIngredient.from(getResource("iceandfire:dragonarmor_"+idName+"_body")), folder.apply("melting"), "body_dragon_armor");
        simpleMelting(consumer, molten, unit * 3, name, ItemNameIngredient.from(getResource("iceandfire:dragonarmor_"+idName+"_tail")), folder.apply("melting"), "tail_dragon_armor");
    }

    void dragonArmorMelting(Consumer<FinishedRecipe> consumer, String name, FlowingFluidObject<ForgeFlowingFluid> molten) {
        dragonArmorMelting(consumer, name, name, molten);
    }

    void dragonsteelSet(Consumer<FinishedRecipe> consumer, FlowingFluidObject<ForgeFlowingFluid> molten, String name) {
        MeltingRecipeBuilder.melting(listedInput(
                "dragonsteel_%s_sword".formatted(name),
                "dragonsteel_%s_hoe".formatted(name)
        ), molten.result((FluidValues.INGOT * 2)), getTemperature(molten), IMeltingRecipe.calcTimeFactor((FluidValues.INGOT * 2))).setDamagable(new int[]{FluidValues.NUGGET}).save(consumer, location(metalFolder("melting") + "/"+name+"_dragonsteel/weapon"));
        MeltingRecipeBuilder.melting(listedInput(
                "dragonsteel_%s_axe".formatted(name),
                "dragonsteel_%s_pickaxe".formatted(name)
        ), molten.result((FluidValues.INGOT * 3)), getTemperature(molten), IMeltingRecipe.calcTimeFactor((FluidValues.INGOT * 3))).setDamagable(new int[]{FluidValues.NUGGET}).save(consumer, location(metalFolder("melting") + "/"+name+"_dragonsteel/axe"));
        MeltingRecipeBuilder.melting(ItemNameIngredient.from(getResource("iceandfire:dragonsteel_%s_shovel".formatted(name))),
                molten.result((FluidValues.INGOT)), getTemperature(molten), IMeltingRecipe.calcTimeFactor((FluidValues.INGOT))).setDamagable(new int[]{FluidValues.NUGGET}).save(consumer, location(metalFolder("melting") + "/"+name+"_dragonsteel/shovel"));
        MeltingRecipeBuilder.melting(ItemNameIngredient.from(getResource("iceandfire:dragonsteel_%s_helmet".formatted(name))),
                molten.result((FluidValues.INGOT * 5)), getTemperature(molten), IMeltingRecipe.calcTimeFactor((FluidValues.INGOT * 5))).setDamagable(new int[]{FluidValues.NUGGET}).save(consumer, location(metalFolder("melting") + "/"+name+"_dragonsteel/helmet"));
        MeltingRecipeBuilder.melting(ItemNameIngredient.from(getResource("iceandfire:dragonsteel_%s_chestplate".formatted(name))),
                molten.result((FluidValues.INGOT * 8)), getTemperature(molten), IMeltingRecipe.calcTimeFactor((FluidValues.INGOT * 8))).setDamagable(new int[]{FluidValues.NUGGET}).save(consumer, location(metalFolder("melting") + "/"+name+"_dragonsteel/chestplate"));
        MeltingRecipeBuilder.melting(ItemNameIngredient.from(getResource("iceandfire:dragonsteel_%s_leggings".formatted(name))),
                molten.result((FluidValues.INGOT * 7)), getTemperature(molten), IMeltingRecipe.calcTimeFactor((FluidValues.INGOT * 7))).setDamagable(new int[]{FluidValues.NUGGET}).save(consumer, location(metalFolder("melting") + "/"+name+"_dragonsteel/leggings"));
        MeltingRecipeBuilder.melting(ItemNameIngredient.from(getResource("iceandfire:dragonsteel_%s_boots".formatted(name))),
                molten.result((FluidValues.INGOT * 4)), getTemperature(molten), IMeltingRecipe.calcTimeFactor((FluidValues.INGOT * 4))).setDamagable(new int[]{FluidValues.NUGGET}).save(consumer, location(metalFolder("melting") + "/"+name+"_dragonsteel/boots"));
    }

    void nugget(Consumer<FinishedRecipe> consumer, String type, TagKey<Item> nugget, TagKey<Item> ingot) {
        TCShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, getResource("%s_dragonsteel_nugget".formatted(type)), 9)
                .requires(ingot)
                .unlockedBy("has_item", new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[]{ItemPredicate.Builder.item().of(ingot).build()}))
                .save(consumer, location("common/materials/%s_dragonsteel_nugget_from_ingot".formatted(type)));
        TCShapedRecipeBuilder.shaped(RecipeCategory.MISC, compatId("dragonsteel_%s_ingot".formatted(type)))
                .pattern("###").pattern("###").pattern("###")
                .define('#', nugget)
                .unlockedBy("has_item", new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[]{ItemPredicate.Builder.item().of(nugget).build()}))
                .save(consumer, location("common/materials/%s_dragonsteel_ingot_from_nuggets".formatted(type)));
    }

    @Override
    public String compatModId() {
        return "iceandfire";
    }
}
