package io.github.pouffy.tcompat.compat.betternether;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import org.betterx.betternether.MHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.recipe.SingleItemContainer;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.concurrent.ExecutionException;

@ParametersAreNonnullByDefault
public class RubysFireModifier extends NoLevelsModifier implements MeleeHitModifierHook, ProcessLootModifierHook {
    private final Cache<Item,Optional<BlastingRecipe>> recipeCache = CacheBuilder
            .newBuilder()
            .maximumSize(64)
            .build();
    private final SingleItemContainer inventory = new SingleItemContainer();

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.PROCESS_LOOT);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();
        Entity target = context.getTarget();
        Level level = context.getLevel();
        if (!(damageDealt <= 0) && level.random.nextFloat() < 0.2F * damageDealt) {
            target.hurt(attacker.damageSources().indirectMagic(target, target), damageDealt > 10 ? damageDealt - 10 : 2 + level.random.nextInt(5));
            target.setRemainingFireTicks(100 + 50 * level.random.nextInt(3));
            if (target instanceof LivingEntity living) {
                living.knockback(1.0F + MHelper.nextFloat(level.random, 2.0F), attacker.getX() - living.getX(), attacker.getZ() - living.getZ());
            }
        }
    }

    @Override
    public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {
        Level world = context.getLevel();
        if (!generatedLoot.isEmpty()) {
            ListIterator<ItemStack> iterator = generatedLoot.listIterator();
            while (iterator.hasNext()) {
                ItemStack stack = iterator.next();
                ItemStack smelted = smeltItem(stack, world);
                if (stack != smelted) {
                    iterator.set(smelted);
                }
            }
        }
    }

    private Optional<BlastingRecipe> findRecipe(ItemStack stack, Level world) {
        inventory.setStack(stack);
        return world.getRecipeManager().getRecipeFor(RecipeType.BLASTING, inventory, world);
    }

    @Nullable
    private BlastingRecipe findCachedRecipe(ItemStack stack, Level world) {
        // don't use the cache if there is a tag, prevent breaking NBT sensitive recipes
        if (stack.hasTag()) {
            return findRecipe(stack, world).orElse(null);
        }
        try {
            return recipeCache.get(stack.getItem(), () -> findRecipe(stack, world)).orElse(null);
        } catch (ExecutionException e) {
            return null;
        }
    }

    private ItemStack smeltItem(ItemStack stack, Level world) {
        // skip blacklisted entries
        if (stack.is(TinkerTags.Items.AUTOSMELT_BLACKLIST)) {
            return stack;
        }
        BlastingRecipe recipe = findCachedRecipe(stack, world);
        if (recipe != null) {
            inventory.setStack(stack);
            ItemStack output = recipe.assemble(inventory, world.registryAccess());
            if (stack.getCount() > 1) {
                // recipe output is a copy, safe to modify
                output.setCount(output.getCount() * stack.getCount());
            }
            return output;
        }
        return stack;
    }
}
