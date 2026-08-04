package io.github.pouffy.tcompat.common.data.recipe;

import io.github.pouffy.tcompat.compat.GlobalInit;
import io.github.pouffy.tcompat.compat.caverns_chasms.CavernsChasmsMaterials;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.tools.definition.module.material.MaterialRepairToolHook;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;
import slimeknights.tconstruct.library.tools.part.IRepairKitItem;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import javax.annotation.Nullable;

public class CraftingTableZirconiaRepairKitRecipe extends CustomRecipe {
    public CraftingTableZirconiaRepairKitRecipe(ResourceLocation id) {
        super(id, CraftingBookCategory.EQUIPMENT);
    }

    protected boolean toolMatches(ItemStack stack) {
        return stack.is(TinkerTags.Items.DURABILITY);
    }

    @Nullable
    protected ToolRepair getRelevantInputs(CraftingContainer inv) {
        ItemStack tool = null;
        ItemStack repairKit = null;

        for(int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                Item var7 = stack.getItem();
                if (var7 instanceof IRepairKitItem) {
                    IRepairKitItem kit = (IRepairKitItem)var7;
                    if (kit.canRepairInCraftingTable()) {
                        if (repairKit != null) {
                            return null;
                        }

                        repairKit = stack;
                        continue;
                    }
                }

                if (!this.toolMatches(stack)) {
                    return null;
                }

                if (tool != null) {
                    return null;
                }

                tool = stack;
            }
        }

        if (tool != null && repairKit != null) {
            return new ToolRepair(tool, repairKit);
        } else {
            return null;
        }
    }

    public boolean matches(CraftingContainer inv, Level worldIn) {
        ToolRepair inputs = this.getRelevantInputs(inv);
        if (inputs == null) {
            return false;
        } else {
            MaterialId inputMaterial = IMaterialItem.getMaterialFromStack(inputs.repairKit).getId();
            if (inputMaterial.equals(IMaterial.UNKNOWN_ID)) {
                return false;
            } else {
                IToolStackView tool = ToolStack.from(inputs.tool);
                return (tool.isBroken() || tool.getDamage() > 0) && CavernsChasmsMaterials.zirconia.matches(MaterialRegistry.getMaterial(inputMaterial));
            }
        }
    }

    protected float getRepairAmount(IToolStackView tool, ItemStack repairStack) {
        float amount = MaterialRepairToolHook.repairAmount(tool, tool.getMaterial(0).getId());
        if (amount <= 0.0) {
            amount = MaterialRepairToolHook.repairAmount(tool, MaterialIds.slimewood);
        }
        return amount;
    }

    public ItemStack assemble(CraftingContainer inv, RegistryAccess access) {
        ToolRepair inputs = this.getRelevantInputs(inv);
        if (inputs == null) {
            TConstruct.LOG.error("Recipe repair on {} failed to find items after matching", this.getId());
            return ItemStack.EMPTY;
        } else {
            ToolStack tool = ToolStack.from(inputs.tool);
            float repairAmount = this.getRepairAmount(tool, inputs.repairKit);
            if (repairAmount <= 0.0F) {
                return ItemStack.EMPTY;
            } else {
                Item var7 = inputs.repairKit.getItem();
                float var10001;
                if (var7 instanceof IRepairKitItem) {
                    IRepairKitItem kit = (IRepairKitItem)var7;
                    var10001 = kit.getRepairAmount();
                } else {
                    var10001 = Config.COMMON.repairKitAmount.get().floatValue();
                }

                repairAmount *= var10001 / 3.0F;

                for(ModifierEntry entry : tool.getModifierList()) {
                    repairAmount = entry.getHook(ModifierHooks.REPAIR_FACTOR).getRepairFactor(tool, entry, repairAmount);
                    if (repairAmount <= 0.0F) {
                        return ItemStack.EMPTY;
                    }
                }

                tool = tool.copy();
                ToolDamageUtil.repair(tool, (int)repairAmount);
                return tool.copyStack(inputs.tool);
            }
        }
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return GlobalInit.craftingTableZirconiaRepairSerializer.get();
    }

    protected static record ToolRepair(ItemStack tool, ItemStack repairKit) {
    }
}
