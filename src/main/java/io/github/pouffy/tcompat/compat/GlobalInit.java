package io.github.pouffy.tcompat.compat;

import com.google.common.collect.HashMultimap;
import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.condition.DartShooterCondition;
import io.github.pouffy.tcompat.common.fluid.TCFluids;
import io.github.pouffy.tcompat.common.modifier.hook.*;
import io.github.pouffy.tcompat.common.modifier.hook.curios.CurioAttributeHook;
import io.github.pouffy.tcompat.common.modifier.hook.curios.CurioTickModifierHook;
import io.github.pouffy.tcompat.common.modifier.module.*;
import io.github.pouffy.tcompat.common.util.CompatHelper;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.aether.entity.ModifiableDart;
import io.github.pouffy.tcompat.compat.aether.item.DartBarrelMaterialStats;
import io.github.pouffy.tcompat.compat.aether.item.LipGuardMaterialStats;
import io.github.pouffy.tcompat.compat.aether.item.ModifiableDartItem;
import io.github.pouffy.tcompat.compat.aether.item.ModifiableDartShooter;
import io.github.pouffy.tcompat.compat.constructs_casting.MagicClothMaterialStats;
import io.github.pouffy.tcompat.compat.ice_and_fire.item.ModifiableGlaiveItem;
import io.github.pouffy.tcompat.config.TCompatConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.data.loadable.record.SingletonLoader;
import slimeknights.mantle.data.predicate.IJsonPredicate;
import slimeknights.mantle.data.predicate.entity.LivingEntityPredicate;
import slimeknights.mantle.registration.deferred.EntityTypeDeferredRegister;
import slimeknights.mantle.registration.deferred.EnumDeferredRegister;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.mantle.registration.object.EnumObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.TinkerEffect;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.library.client.data.material.GeneratorPartTextureJsonGenerator;
import slimeknights.tconstruct.library.json.predicate.tool.ToolStackPredicate;
import slimeknights.tconstruct.library.json.variable.entity.EntityVariable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.TinkerToolParts;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;
import slimeknights.tconstruct.tools.stats.ToolType;

import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class GlobalInit extends CompatInitializer {
    public static final ItemDeferredRegisterExtension ITEMS = new ItemDeferredRegisterExtension(TCompat.MOD_ID);
    public static final EntityTypeDeferredRegister ENTITIES = new EntityTypeDeferredRegister(TCompat.MOD_ID);
    public static final EnumDeferredRegister<MobEffect> MOB_EFFECTS = new EnumDeferredRegister<>(Registries.MOB_EFFECT, TCompat.MOD_ID);

    public static final RegistryObject<EntityType<ModifiableDart>> modifiableDart = ENTITIES.register("dart", () -> EntityType.Builder.<ModifiableDart>of(ModifiableDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));

    public static final ItemObject<ModifiableGlaiveItem> glaive = ITEMS.register("glaive", () -> new ModifiableGlaiveItem(new Item.Properties().stacksTo(1), GlobalDefinitions.glaive));

    public static final ItemObject<ModifiableDartShooter> dartShooter = ITEMS.register("dart_shooter", () -> new ModifiableDartShooter(new Item.Properties().stacksTo(1), GlobalDefinitions.dartShooter, false));
    public static final ItemObject<ModifiableDartItem> dart = ITEMS.register("dart", () -> new ModifiableDartItem(new Item.Properties(), GlobalDefinitions.dart));

    public static final ItemObject<ToolPartItem> dartBarrel = ITEMS.register("dart_barrel", () -> new ToolPartItem(new Item.Properties(), DartBarrelMaterialStats.ID));
    public static final ItemObject<ToolPartItem> lipGuard = ITEMS.register("lip_guard", () -> new ToolPartItem(new Item.Properties(), LipGuardMaterialStats.ID));

    public static final CastItemObject dartBarrelCast = ITEMS.registerCast(dartBarrel, new Item.Properties());
    public static final CastItemObject lipGuardCast = ITEMS.registerCast(lipGuard, new Item.Properties());

    public static final EnumObject<ToolType, TinkerEffect> clockworkEffect = MOB_EFFECTS.registerEnum("momentum", new ToolType[]{ToolType.HARVEST, ToolType.RANGED, ToolType.MELEE}, (type) -> new NoMilkEffect(MobEffectCategory.BENEFICIAL, 6310251, true));

    public static final ModuleHook<AetherForgedModifierHook> AETHER_FORGED = ModifierHooks.register(getResource("aether_forged"), AetherForgedModifierHook.class, new AetherForgedModifierHook() {
        @Override
        public boolean canUse(IToolStackView toolStackView, ModifierEntry modifierEntry) {
            return true;
        }

        @Override
        public boolean canProjectileUse(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifierEntry) {
            return true;
        }
    });
    public static final ModuleHook<ToolSwingModifierHook> TOOL_SWING = ModifierHooks.register(getResource("tool_swing"), ToolSwingModifierHook.class, ToolSwingModifierHook.AllMerger::new, (toolStackView, modifierEntry, stack, player) -> false);
    public static final ModuleHook<SoulExposureModifierHook> SOUL_EXPOSURE = ModifierHooks.register(getResource("soul_exposure"), SoulExposureModifierHook.class, (tool, modifier) -> true);
    public static final ModuleHook<CollectSpiritModifierHook> COLLECT_SPIRIT = ModifierHooks.register(getResource("collect_spirit"), CollectSpiritModifierHook.class, new CollectSpiritModifierHook() {
        @Override
        public boolean canTarget(IToolStackView toolStack, ModifierEntry modifierEntry, LivingEntity collector, double arcaneResonance) {return false;}

        @Override
        public void pickupSpirit(IToolStackView toolStack, ModifierEntry modifierEntry, LivingEntity collector, double arcaneResonance, float effectiveness) {}
    });
    public static final ModuleHook<CurioTickModifierHook> CURIO_TICK = ModifierHooks.register(getResource("curio_tick"), CurioTickModifierHook.class, CurioTickModifierHook.AllMerger::new, (tool, modifierEntry, slotId, slotIndex, wearer, stack) -> {});
    public static final ModuleHook<CurioAttributeHook> CURIO_ATTRIBUTE = ModifierHooks.register(getResource("curio_attribute"), CurioAttributeHook.class, CurioAttributeHook.AllMerger::new, (tool, modifierEntry, slotId, slotIndex, wearer, uuid, stack) -> HashMultimap.create());
    public static final ModuleHook<EntitySensitiveAttributesModifierHook> ENTITY_SENSITIVE_ATTRIBUTES = ModifierHooks.register(getResource("entity_sensitive_attributes"), EntitySensitiveAttributesModifierHook.class, EntitySensitiveAttributesModifierHook.AllMerger::new, (tool, modifierEntry, slot, wearer, attributes) -> {});
    public static final ModuleHook<VibrationDampeningModifierHook> VIBRATION_DAMPENING = ModifierHooks.register(getResource("vibration_dampening"), VibrationDampeningModifierHook.class, VibrationDampeningModifierHook.AllMerger::new, (tool, modifierEntry, level, gameEvent, context, pos) -> true);


    public static LivingEntityPredicate SUN_EXPOSED = SingletonLoader.singleton((loader) -> new LivingEntityPredicate() {
        @Override
        public boolean matches(@NotNull LivingEntity living) {
            if (living.level().isDay() && !living.level().isClientSide) {
                float brightness = (float)living.level().getBrightness(LightLayer.SKY, living.blockPosition());
                BlockPos blockpos = living.getVehicle() instanceof Boat ? (new BlockPos(living.getBlockX(), living.getBlockY(), living.getBlockZ())).above() : new BlockPos(living.getBlockX(), living.getBlockY(), living.getBlockZ());
                return brightness > 0.5F && living.level().canSeeSky(blockpos);
            }
            return false;
        }

        @Override
        public @NotNull RecordLoadable<? extends IJsonPredicate<LivingEntity>> getLoader() {
            return loader;
        }
    });

    public static EntityVariable SKY_LIGHT = SingletonLoader.singleton((loader) -> new EntityVariable() {

        @Override
        public float getValue(LivingEntity living) {
            Level level = living.level();
            int light = 0;
            BlockPos blockpos = living.getVehicle() instanceof Boat ? (new BlockPos(living.getBlockX(), living.getBlockY(), living.getBlockZ())).above() : new BlockPos(living.getBlockX(), living.getBlockY(), living.getBlockZ());
            if (level.canSeeSky(blockpos)) {
                light = living.level().getBrightness(LightLayer.SKY, living.blockPosition());
            } else {
                light = Math.min(living.level().getBrightness(LightLayer.BLOCK, living.blockPosition()), 8);
            }
            return light;
        }

        @Override
        public RecordLoadable<? extends EntityVariable> getLoader() {
            return loader;
        }
    });

    public static ToolStackPredicate HAS_WINGS = SingletonLoader.singleton((loader) -> new ToolStackPredicate() {
        @Override
        public boolean matches(IToolStackView tool) {
            return tool.getModifier(ModifierIds.wings).getLevel() > 0;
        }

        @Override
        public RecordLoadable<? extends IJsonPredicate<IToolStackView>> getLoader() {
            return loader;
        }
    });

    @SubscribeEvent
    void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    void registerSerializers(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.RECIPE_SERIALIZER) {
            CraftingHelper.register(DartShooterCondition.Serializer.INSTANCE);

            ModifierModule.LOADER.register(getResource("aether_forged"), AetherForgedModule.LOADER);
            ModifierModule.LOADER.register(getResource("soul_exposure"), SoulExposureModule.LOADER);
            ModifierModule.LOADER.register(getResource("mob_effect_user"), MobEffectUserModule.LOADER);
            ModifierModule.LOADER.register(getResource("optional_attribute"), OptionalAttributeModule.LOADER);
            ModifierModule.LOADER.register(TCompat.getResource("autosmelt"), AutosmeltModule.LOADER);
            LivingEntityPredicate.LOADER.register(getResource("sun_exposed"), SUN_EXPOSED.getLoader());
            ToolStackPredicate.LOADER.register(getResource("has_wings"), HAS_WINGS.getLoader());
            EntityVariable.LOADER.register(getResource("sky_light"), SKY_LIGHT.getLoader());
            EntityVariable.LOADER.register(getResource("player_stat"), StatEntityVariable.LOADER);
            ModifierModule.LOADER.register(getResource("vibration_dampening"), VibrationDampeningModule.LOADER);
        }
    }

    public static void addToolTabItems(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output tab) {
        Consumer<ItemStack> output = tab::accept;
        ToolBuildHandler.addVariants(output, glaive.get(), "");
        if (CompatHelper.isLoaded("aether") || TCompatConfig.SERVER.forceEnableDartShooters.get()) {
            ToolBuildHandler.addVariants(output, dartShooter.get(), "");
            ToolBuildHandler.addVariants(output, dart.get(), "");
        }
    }

    public static void addPartTabItems(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output tab) {
        Consumer<ItemStack> output = tab::accept;
        if (CompatHelper.isLoaded("aether") || TCompatConfig.SERVER.forceEnableDartShooters.get()) {
            dartBarrel.get().addVariants(output, "");
            lipGuard.get().addVariants(output, "");
        }
    }

    public static void addCasts(CreativeModeTab.Output output, Function<CastItemObject, ItemLike> getter) {
        if (CompatHelper.isLoaded("aether") || TCompatConfig.SERVER.forceEnableDartShooters.get()) {
            output.accept(getter.apply(dartBarrelCast));
            output.accept(getter.apply(lipGuardCast));
        }
    }

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
        ENTITIES.register(eventBus);
        MOB_EFFECTS.register(eventBus);
        WoodMaterials.staticInit();
        RockMaterials.staticInit();
    }

    // Order: Compat Items -> Casts -> Fluids
    public static void collectGeneralItems(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        CompatHelper.compatItems.forEach((mod, consumer) -> {
            if (CompatHelper.isLoaded(mod))
                consumer.accept(parameters, output);
        });
        addCasts(output, ItemObject::get);
        addCasts(output, CastItemObject::getSand);
        addCasts(output, CastItemObject::getRedSand);
        TCFluids.addTabItems(parameters, output);
    }

    // Tools
    public static void collectToolItems(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        addToolTabItems(parameters, output);
    }

    // Tool Parts
    public static void collectPartItems(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        addPartTabItems(parameters, output);
    }

    public static GeneratorPartTextureJsonGenerator.StatOverride getStatOverrides() {
        GeneratorPartTextureJsonGenerator.StatOverride.Builder builder = new GeneratorPartTextureJsonGenerator.StatOverride.Builder();
        GlobalDefinitions.dartBarrelMaterials.forEach((material) -> builder.addVariant(DartBarrelMaterialStats.ID, material));
        GlobalDefinitions.lipGuardMaterials.forEach((material) -> builder.addVariant(LipGuardMaterialStats.ID, material));
        return builder.build();
    }
}
