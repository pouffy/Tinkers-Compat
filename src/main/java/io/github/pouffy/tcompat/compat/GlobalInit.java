package io.github.pouffy.tcompat.compat;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.module.*;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.ice_and_fire.item.ModifiableGlaiveItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.data.loadable.record.SingletonLoader;
import slimeknights.mantle.data.predicate.IJsonPredicate;
import slimeknights.mantle.data.predicate.entity.LivingEntityPredicate;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import java.util.function.Consumer;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class GlobalInit extends CompatInitializer {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(TCompat.MOD_ID);

    public static final ItemObject<ModifiableGlaiveItem> glaive = ITEMS.register("glaive", () -> new ModifiableGlaiveItem(new Item.Properties().stacksTo(1), GlobalDefinitions.glaive));

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

    @SubscribeEvent
    void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    void registerSerializers(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.RECIPE_SERIALIZER) {
            ModifierModule.LOADER.register(getResource("aether_forged"), AetherForgedModule.LOADER);
            ModifierModule.LOADER.register(getResource("mob_effect_user"), MobEffectUserModule.LOADER);
            LivingEntityPredicate.LOADER.register(getResource("sun_exposed"), SUN_EXPOSED.getLoader());
        }
    }

    public static void addToolTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output tab) {
        Consumer<ItemStack> output = tab::accept;
        ToolBuildHandler.addVariants(output, glaive.get(), "");
    }

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
        WoodMaterials.staticInit();
        RockMaterials.staticInit();
    }
}
