package io.github.pouffy.tcompat.mixin.tconstruct;

import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.compat.caverns_chasms.CavernsChasmsMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.recipe.material.MaterialRecipe;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationContainer;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.part.IRepairKitItem;
import slimeknights.tconstruct.tables.recipe.TinkerStationRepairRecipe;

@Mixin(value = TinkerStationRepairRecipe.class, remap = false)
public abstract class TinkerStationRepairRecipeMixin {

    @Shadow
    protected abstract float getRepairPerItem(ToolStack tool, ITinkerStationContainer inv, int slot, MaterialId repairMaterial);

    @Inject(method = "matches(Lslimeknights/tconstruct/library/recipe/tinkerstation/ITinkerStationContainer;Lnet/minecraft/world/level/Level;)Z", at = @At("HEAD"), cancellable = true)
    public void matches(ITinkerStationContainer inv, Level world, CallbackInfoReturnable<Boolean> cir) {
        if (CompatHelper.isLoaded("caverns_and_chasms")) {
            ItemStack tinkerable = inv.getTinkerableStack();
            if (!tinkerable.isEmpty() && tinkerable.is(TinkerTags.Items.DURABILITY)) {
                for(int i = 0; i < inv.getInputCount(); ++i) {
                    ItemStack stack = inv.getInput(i);
                    if (!stack.isEmpty()) {
                        MaterialId inputMaterial = tcompat$getMaterialFrom(inv, i);
                        if (inputMaterial.getId().equals(CavernsChasmsMaterials.zirconia)) {
                            cir.setReturnValue(true);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Unique
    private MaterialId tcompat$getMaterialFrom(ITinkerStationContainer inv, int slot) {
        ItemStack item = inv.getInput(slot);
        Item var4 = item.getItem();
        if (var4 instanceof IRepairKitItem kit) {
            return kit.getMaterial(item).getId();
        } else {
            MaterialRecipe recipe = inv.getInputMaterial(slot);
            return recipe != null ? recipe.getMaterial().getId() : IMaterial.UNKNOWN_ID;
        }
    }
}
