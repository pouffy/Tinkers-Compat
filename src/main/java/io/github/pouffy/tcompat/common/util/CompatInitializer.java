package io.github.pouffy.tcompat.common.util;

import io.github.pouffy.tcompat.TCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.HolderSet;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.registration.object.FluidObject;

import java.util.Optional;

import static slimeknights.mantle.Mantle.commonResource;

public class CompatInitializer {

    protected static FluidType.Properties hot(String name) {
        return FluidType.Properties.create().density(2000).viscosity(10000).temperature(1000)
                .descriptionId(TCompat.makeDescriptionId("fluid", name))
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                // from forge lava type
                .motionScale(0.0023333333333333335D)
                .canSwim(false).canDrown(false)
                .pathType(BlockPathTypes.LAVA).adjacentPathType(null);
    }

    protected DispenseItemBehavior dispenseBucket = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        @Override
        public @NotNull ItemStack execute(BlockSource source, ItemStack stack) {
            DispensibleContainerItem container = (DispensibleContainerItem)stack.getItem();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            Level level = source.getLevel();
            if (container.emptyContents(null, level, blockpos, null, stack)) {
                container.checkExtraContent(null, level, stack, blockpos);
                return new ItemStack(Items.BUCKET);
            } else {
                return this.defaultDispenseItemBehavior.dispense(source, stack);
            }
        }
    };

    protected static void acceptCompat(CreativeModeTab.Output output, ItemLike item, String... ingots) {
        for (String ingot : ingots) {
            if (acceptIfTag(output, item, ItemTags.create(commonResource("ingots/" + ingot)))) {
                break;
            }
            if (acceptIfTag(output, item, ItemTags.create(commonResource("gems/" + ingot)))) {
                break;
            }
        }
    }

    protected static void acceptMolten(CreativeModeTab.Output output, FluidObject<?> fluid) {
        acceptCompat(output, fluid, withoutMolten(fluid));
    }

    protected static void acceptMolten(CreativeModeTab.Output output, FluidObject<?> fluid, String ingot) {
        acceptCompat(output, fluid, withoutMolten(fluid), ingot);
    }

    private static final int MOLTEN_LENGTH = "molten_".length();

    protected static String withoutMolten(FluidObject<?> fluid) {
        return fluid.getId().getPath().substring(MOLTEN_LENGTH);
    }

    /** Accepts the given item if the passed tag has items */
    protected static boolean acceptIfTag(CreativeModeTab.Output output, ItemLike item, CreativeModeTab.TabVisibility visibility, TagKey<Item> tagCondition) {
        Optional<HolderSet.Named<Item>> tag = BuiltInRegistries.ITEM.getTag(tagCondition);
        if (tag.isPresent() && tag.get().size() > 0) {
            output.accept(item, visibility);
            return true;
        }
        return false;
    }

    /** Accepts the given item if the passed tag has items */
    protected static boolean acceptIfTag(CreativeModeTab.Output output, ItemLike item, TagKey<Item> tagCondition) {
        return acceptIfTag(output, item, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tagCondition);
    }
}
