package io.github.pouffy.tcompat.mixin.malum;

import com.sammy.malum.common.block.curiosities.weeping_well.VoidConduitBlockEntity;
import com.sammy.malum.registry.common.SoundRegistry;
import io.github.pouffy.tcompat.compat.malum.MalumInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

@Mixin(VoidConduitBlockEntity.class)
public abstract class VoidConduitBlockEntityMixin extends LodestoneBlockEntity {

    @Shadow
    public int streak;

    public VoidConduitBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "spitOutItem", at = @At("HEAD"), remap = false, cancellable = true)
    private void spitOutItem(ItemStack stack, CallbackInfoReturnable<Item> cir) {
        if (this.level == null) return;
        float pitch = Mth.nextFloat(this.level.getRandom(), 0.85F, 1.35F) + (float)this.streak * 0.1F;
        if (stack.getItem() instanceof ToolPartItem partItem) {
            MaterialVariantId materialVariantId = partItem.getMaterial(stack);
            if (MalumInit.weepingWellConversions.containsKey(materialVariantId)) {
                ++this.streak;

                int count;
                ItemStack newStack = partItem.setMaterial(stack, MalumInit.weepingWellConversions.get(materialVariantId));
                for(int amount = newStack.getCount() * stack.getCount(); amount > 0; amount -= count) {
                    count = Math.min(64, amount);
                    ItemStack outputStack = new ItemStack(newStack.getItem(), count);
                    outputStack.setTag(newStack.getTag());
                    ItemEntity entity = new ItemEntity(this.level, (float)this.worldPosition.getX() + 0.5F, (float)this.worldPosition.getY() + 0.5F, (float)this.worldPosition.getZ() + 0.5F, outputStack);
                    entity.setDeltaMovement(0.0F, 0.65F, 0.15F);
                    this.level.addFreshEntity(entity);
                }

                this.level.playSound(null, this.worldPosition, SoundRegistry.VOID_TRANSMUTATION.get(), SoundSource.HOSTILE, 2.0F, pitch);
                cir.setReturnValue(newStack.getItem());
            }
        }
    }

}
