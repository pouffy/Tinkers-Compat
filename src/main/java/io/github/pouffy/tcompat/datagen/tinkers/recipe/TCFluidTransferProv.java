package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.TCFluids;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import slimeknights.mantle.fluid.transfer.AbstractFluidContainerTransferProvider;
import slimeknights.mantle.fluid.transfer.EmptyFluidContainerTransfer;
import slimeknights.mantle.fluid.transfer.FillFluidContainerTransfer;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class TCFluidTransferProv extends AbstractFluidContainerTransferProvider {
    public TCFluidTransferProv(PackOutput packOutput) {
        super(packOutput, TCompat.MOD_ID);
    }

    @Override
    protected void addTransfers() {
        addBottleTransfer("fire_dragon_blood", "fire_dragon_blood", TCFluids.fireBlood, "iceandfire");
        addBottleTransfer("ice_dragon_blood", "ice_dragon_blood", TCFluids.iceBlood, "iceandfire");
        addBottleTransfer("lightning_dragon_blood", "lightning_dragon_blood", TCFluids.lightningBlood, "iceandfire");

        addBowlTransfer("fire_lily_mixture", "fire_stew", TCFluids.fireLilyMixture, "iceandfire");
        addBowlTransfer("frost_lily_mixture", "frost_stew", TCFluids.frostLilyMixture, "iceandfire");
        addBowlTransfer("lightning_lily_mixture", "lightning_stew", TCFluids.lightningLilyMixture, "iceandfire");
        addBowlTransfer("ambrosia", "ambrosia", TCFluids.ambrosia, "iceandfire");
        addBottleTransfer("aloe_vera_juice", "aloe_vera_juice", TCFluids.aloeVeraJuice, "biomeswevegone");
        addBowlTransfer("white_puffball_stew", "white_puffball_stew", TCFluids.whitePuffballStew, "biomeswevegone");
        addBowlTransfer("allium_oddion_soup", "allium_oddion_soup", TCFluids.alliumOddionSoup, "biomeswevegone");
        addBottleTransfer("umbrella_cluster_juice", "umbrella_cluster_juice", TCFluids.umbrellaClusterJuice, "betterend");
        addSpecialTransfer("stalagnate_wart_soup", "stalagnate_bowl_wart", TCFluids.wartSoup, "betternether", "betternether:stalagnate_bowl", FluidValues.BOWL);
        addSpecialTransfer("stalagnate_agave_medicine", "agave_medicine", TCFluids.agaveMedicine, "betternether", "betternether:stalagnate_bowl", FluidValues.BOWL);
        addSpecialTransfer("stalagnate_mushroom_stew", "stalagnate_bowl_mushroom", TinkerFluids.mushroomStew, "betternether", "betternether:stalagnate_bowl", FluidValues.BOWL);
    }

    protected void addBottleTransfer(String name, String item, FluidObject<?> fluid, String namespace) {
        addTransfer(name + "_empty", new EmptyFluidContainerTransfer(ItemNameIngredient.from(getResource(namespace, item)), ItemOutput.fromItem(Items.GLASS_BOTTLE), fluid.result(FluidValues.BOTTLE)), new ModLoadedCondition(namespace));
        addTransfer(name + "_fill", new FillFluidContainerTransfer(Ingredient.of(Items.GLASS_BOTTLE), ItemNameOutput.fromName(getResource(namespace, item)), fluid.ingredient(FluidValues.BOTTLE)), new ModLoadedCondition(namespace));
    }

    protected void addBowlTransfer(String name, String item, FluidObject<?> fluid, String namespace) {
        addTransfer(name + "_empty", new EmptyFluidContainerTransfer(ItemNameIngredient.from(getResource(namespace, item)), ItemOutput.fromItem(Items.BOWL), fluid.result(FluidValues.BOWL)), new ModLoadedCondition(namespace));
        addTransfer(name + "_fill", new FillFluidContainerTransfer(Ingredient.of(Items.BOWL), ItemNameOutput.fromName(getResource(namespace, item)), fluid.ingredient(FluidValues.BOWL)), new ModLoadedCondition(namespace));
    }

    protected void addSpecialTransfer(String name, String item, FluidObject<?> fluid, String namespace, String container, int amount) {
        addTransfer(name + "_empty", new EmptyFluidContainerTransfer(ItemNameIngredient.from(getResource(namespace, item)), ItemNameOutput.fromName(getResource(container)), fluid.result(amount)), new ModLoadedCondition(namespace));
        addTransfer(name + "_fill", new FillFluidContainerTransfer(ItemNameIngredient.from(getResource(container)), ItemNameOutput.fromName(getResource(namespace, item)), fluid.ingredient(amount)), new ModLoadedCondition(namespace));
    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Fluid Container Transfer";
    }
}
