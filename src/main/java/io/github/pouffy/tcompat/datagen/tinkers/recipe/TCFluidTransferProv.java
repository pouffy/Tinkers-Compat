package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
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
    }

    protected void addBottleTransfer(String name, String item, FluidObject<?> fluid, String namespace) {
        addTransfer(name + "_empty", new EmptyFluidContainerTransfer(ItemNameIngredient.from(getResource(namespace, item)), ItemOutput.fromItem(Items.GLASS_BOTTLE), fluid.result(FluidValues.BOTTLE)), new ModLoadedCondition(namespace));
        addTransfer(name + "_fill", new FillFluidContainerTransfer(Ingredient.of(Items.GLASS_BOTTLE), ItemNameOutput.fromName(getResource(namespace, item)), fluid.ingredient(FluidValues.BOTTLE)), new ModLoadedCondition(namespace));
    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Fluid Container Transfer";
    }
}
