package com.pouffydev.tcompat.modifier.aether;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.pouffydev.tcompat.TCompat;
import com.pouffydev.tcompat.data.TComTags;
import net.minecraft.nbt.ByteTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.DamageDealtModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.modifiers.slotless.TrimModifier;

import java.util.UUID;
import java.util.function.BiConsumer;

public class AmbrofusionModifier extends NoLevelsModifier implements ToolDamageModifierHook, BlockBreakModifierHook, DamageDealtModifierHook, GeneralInteractionModifierHook, AttributesModifierHook, ModifierRemovalHook {
    public static final ResourceLocation INFUSION_LEVEL = TCompat.getResource("infusion_level");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,
                ModifierHooks.REMOVE,
                ModifierHooks.TOOL_DAMAGE,
                ModifierHooks.BLOCK_BREAK,
                ModifierHooks.DAMAGE_DEALT,
                ModifierHooks.GENERAL_INTERACT,
                ModifierHooks.ATTRIBUTES
        );
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        return tool.getPersistentData().getCopy().getByte(INFUSION_LEVEL.toString()) > 0 ? amount * 4 : amount;
    }

    @Override
    public void onDamageDealt(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, LivingEntity target, DamageSource source, float amount, boolean isDirectDamage) {
        if (isDirectDamage) this.deplete(1, tool);
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        if (!context.getLiving().level().isClientSide()) {
            boolean instaBreak = context.getState().getDestroySpeed(context.getWorld(), context.getPos()) <= 0.0F;
            if (!instaBreak) {
                int amount = context.canHarvest() ? 1 : 2;
                this.deplete(amount, tool);
            }
        }
    }

    @Override
    public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source) {
        return infuse(tool, hand, player);
    }

    void deplete(int amount, IToolStackView tool) {
        if (tool.getPersistentData().getCopy().getByte(INFUSION_LEVEL.toString()) > amount) {
            byte infusion = (byte)(tool.getPersistentData().getCopy().getByte(INFUSION_LEVEL.toString()) - amount);
            tool.getPersistentData().put(INFUSION_LEVEL, ByteTag.valueOf(infusion));
            return;
        } else {
            byte infusion = (byte)0;
            tool.getPersistentData().put(INFUSION_LEVEL, ByteTag.valueOf(infusion));
        }
    }

    InteractionResult infuse(IToolStackView tool, InteractionHand hand, Player player) {
        InteractionHand ambrosiumHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        boolean hasAmbrosium = player.getItemInHand(ambrosiumHand).is(TComTags.Items.AMBROSIUM);
        if (hasAmbrosium) {
            ItemStack ambrosium = player.getItemInHand(ambrosiumHand);
            int size = ambrosium.getCount();
            byte infusion = (byte)(tool.getPersistentData().getCopy().getByte(INFUSION_LEVEL.toString()) + 4);
            if (size > 1) {
                tool.getPersistentData().put(INFUSION_LEVEL, ByteTag.valueOf(infusion));
                ambrosium.shrink(1);
                return InteractionResult.sidedSuccess(player.level().isClientSide);
            } else {
                tool.getPersistentData().put(INFUSION_LEVEL, ByteTag.valueOf(infusion));
                player.setItemInHand(ambrosiumHand, ItemStack.EMPTY);
                return InteractionResult.sidedSuccess(player.level().isClientSide);
            }
        }
        return InteractionResult.FAIL;
    }

    private final Lazy<UUID> uuidDmg = Lazy.of(() -> UUID.nameUUIDFromBytes((getId().toString() + "_attack_damage").getBytes()));
    private final Lazy<UUID> uuidSpd = Lazy.of(() -> UUID.nameUUIDFromBytes((getId().toString() + "_attack_speed").getBytes()));

    private final Lazy<String> attributeNameDmg = Lazy.of(() -> {
        ResourceLocation id = getId();
        return id.getPath() + "." + id.getNamespace() + ".attack_damage";
    });
    private final Lazy<String> attributeNameSpd = Lazy.of(() -> {
        ResourceLocation id = getId();
        return id.getPath() + "." + id.getNamespace() + ".attack_speed";
    });

    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (tool.getPersistentData().getCopy().getByte(INFUSION_LEVEL.toString()) > 0) {
            double dmgBoost = 2.0D;
            double spdBoost = 0.3D;
            consumer.accept(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuidDmg.get(), attributeNameDmg.get(), dmgBoost, AttributeModifier.Operation.ADDITION));
            consumer.accept(Attributes.ATTACK_SPEED, new AttributeModifier(uuidSpd.get(), attributeNameSpd.get(), spdBoost, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public @Nullable Component onRemoved(IToolStackView tool, Modifier modifier) {
        tool.getPersistentData().remove(INFUSION_LEVEL);
        return null;
    }
}
