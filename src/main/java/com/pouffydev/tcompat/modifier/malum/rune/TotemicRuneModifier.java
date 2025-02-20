package com.pouffydev.tcompat.modifier.malum.rune;

import com.pouffydev.tcompat.TCompat;
import com.sammy.malum.registry.common.MobEffectRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IModDataView;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Optional;

//public class TotemicRuneModifier extends NoLevelsModifier implements ModifierRemovalHook, InventoryTickModifierHook {
//    private static final String FORMAT_KEY = TCompat.makeTranslationKey("modifier", "totemic_rune.formatted");
//    public static final ResourceLocation TOTEMIC_RITE = TCompat.getResource("totemic_rune");
//    //public static final ResourceLocation TRIM_MATERIAL = TConstruct.getResource("trim_material");
//
//    @Override
//    public Component getDisplayName(IToolStackView tool, ModifierEntry entry, @javax.annotation.Nullable RegistryAccess access) {
//        IModDataView modDataNBT = tool.getPersistentData();
//        String rite = modDataNBT.getString(TOTEMIC_RITE);
//        ResourceLocation riteId = new ResourceLocation(rite);
//        // Format is Rune of ___
//        Component riteComponent = Component.translatable("tcompat.totemic_rune.%s".formatted(riteId.getPath()));
//        return Component.translatable(FORMAT_KEY, riteComponent);
//    }
//
//    @Override
//    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
//        super.registerHooks(hookBuilder);
//        hookBuilder.addHook(this, ModifierHooks.REMOVE);
//        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK);
//    }
//
//
//    @Override
//    public @Nullable Component onRemoved(IToolStackView tool, Modifier modifier) {
//        tool.getPersistentData().remove(TOTEMIC_RITE);
//        return null;
//    }
//
//    @Override
//    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
//        boolean malumPresent = TCompat.isClassFound("com.sammy.malum.MalumMod");
//        boolean isBeingWorn = false;
//        for (ItemStack armorPiece : holder.getArmorSlots()) {
//            if (armorPiece.equals(stack)) isBeingWorn = true;
//        }
//        if (!malumPresent || !isBeingWorn) {
//            return;
//        }
//        IModDataView modDataNBT = tool.getPersistentData();
//        String rite = modDataNBT.getString(TOTEMIC_RITE);
//        Optional<MobEffect> effect = BuiltInRegistries.MOB_EFFECT.getOptional(new ResourceLocation(rite));
//        if (effect.isEmpty()) {
//            return;
//        }
//        int interval = effect.get() == MobEffectRegistry.IFRITS_EMBRACE.get() ? 10 : 40;
//        if (!world.isClientSide() && world.getGameTime() % interval == 0) {
//            holder.addEffect(new MobEffectInstance(effect.get(), 200, 0, true, true));
//        }
//    }
//}
