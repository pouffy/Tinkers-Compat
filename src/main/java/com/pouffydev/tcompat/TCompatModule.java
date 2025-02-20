package com.pouffydev.tcompat;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.item.TooltipItem;
import slimeknights.mantle.registration.deferred.*;
import slimeknights.tconstruct.common.registration.BlockDeferredRegisterExtension;
import slimeknights.tconstruct.common.registration.EnumDeferredRegister;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.library.recipe.TinkerRecipeTypes;

import java.util.function.Function;
import java.util.function.Supplier;

public class TCompatModule {
    protected TCompatModule() {
        TCompat.sealTComClass(this, "TCompatModule", "This is a bug with the mod containing that class, they should create their own deferred registers.");
    }

    // deferred register instances
    // gameplay singleton
    protected static final BlockDeferredRegisterExtension BLOCKS = new BlockDeferredRegisterExtension(TCompat.MOD_ID);
    protected static final ItemDeferredRegisterExtension ITEMS = new ItemDeferredRegisterExtension(TCompat.MOD_ID);
    protected static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(TCompat.MOD_ID);
    protected static final EnumDeferredRegister<MobEffect> MOB_EFFECTS = new EnumDeferredRegister<>(Registries.MOB_EFFECT, TCompat.MOD_ID);
    protected static final SynchronizedDeferredRegister<ParticleType<?>> PARTICLE_TYPES = SynchronizedDeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TCompat.MOD_ID);
    protected static final SynchronizedDeferredRegister<EntityDataSerializer<?>> DATA_SERIALIZERS = SynchronizedDeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, TCompat.MOD_ID);
    protected static final SynchronizedDeferredRegister<CreativeModeTab> CREATIVE_TABS = SynchronizedDeferredRegister.create(Registries.CREATIVE_MODE_TAB, TCompat.MOD_ID);
    // gameplay instances
    protected static final BlockEntityTypeDeferredRegister BLOCK_ENTITIES = new BlockEntityTypeDeferredRegister(TCompat.MOD_ID);
    protected static final EntityTypeDeferredRegister ENTITIES = new EntityTypeDeferredRegister(TCompat.MOD_ID);
    protected static final MenuTypeDeferredRegister MENUS = new MenuTypeDeferredRegister(TCompat.MOD_ID);
    // datapacks
    protected static final SynchronizedDeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = SynchronizedDeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TCompat.MOD_ID);
    protected static final SynchronizedDeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = SynchronizedDeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, TCompat.MOD_ID);
    protected static final SynchronizedDeferredRegister<LootItemConditionType> LOOT_CONDITIONS = SynchronizedDeferredRegister.create(Registries.LOOT_CONDITION_TYPE, TCompat.MOD_ID);
    protected static final SynchronizedDeferredRegister<LootItemFunctionType> LOOT_FUNCTIONS = SynchronizedDeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, TCompat.MOD_ID);
    protected static final SynchronizedDeferredRegister<LootPoolEntryType> LOOT_ENTRIES = SynchronizedDeferredRegister.create(Registries.LOOT_POOL_ENTRY_TYPE, TCompat.MOD_ID);

    // base item properties
    protected static final Item.Properties ITEM_PROPS = new Item.Properties();
    protected static final Item.Properties UNSTACKABLE_PROPS = new Item.Properties().stacksTo(1);
    protected static final Function<Block,? extends BlockItem> BLOCK_ITEM = (b) -> new BlockItem(b, ITEM_PROPS);
    protected static final Function<Block,? extends BlockItem> TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, ITEM_PROPS);
    protected static final Function<Block,? extends BlockItem> UNSTACKABLE_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, UNSTACKABLE_PROPS);
    protected static final Supplier<Item> TOOLTIP_ITEM = () -> new TooltipItem(ITEM_PROPS);

    /** Called during construction to initialize the registers for this mod */
    public static void initRegisters() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // gameplay singleton
        BLOCKS.register(bus);
        ITEMS.register(bus);
        FLUIDS.register(bus);
        MOB_EFFECTS.register(bus);
        PARTICLE_TYPES.register(bus);
        DATA_SERIALIZERS.register(bus);
        CREATIVE_TABS.register(bus);
        // gameplay instance
        BLOCK_ENTITIES.register(bus);
        ENTITIES.register(bus);
        MENUS.register(bus);
        // datapacks
        RECIPE_SERIALIZERS.register(bus);
        GLOBAL_LOOT_MODIFIERS.register(bus);
        LOOT_CONDITIONS.register(bus);
        LOOT_FUNCTIONS.register(bus);
        LOOT_ENTRIES.register(bus);
        TinkerRecipeTypes.init(bus);
    }
}
