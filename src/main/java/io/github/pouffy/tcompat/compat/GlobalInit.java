package io.github.pouffy.tcompat.compat;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.module.AetherForgedModifierHook;
import io.github.pouffy.tcompat.common.module.AetherForgedModule;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.ice_and_fire.item.ModifiableGlaiveItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.tools.modules.interaction.BrushModule;

import java.util.function.Consumer;

public class GlobalInit extends CompatInitializer {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(TCompat.MOD_ID);

    public static final ItemObject<ModifiableGlaiveItem> glaive = ITEMS.register("glaive", () -> new ModifiableGlaiveItem(new Item.Properties().stacksTo(1), GlobalDefinitions.glaive));

    public static final ModuleHook<AetherForgedModifierHook> AETHER_FORGED = ModifierHooks.register(TCompat.getResource("aether_forged"), AetherForgedModifierHook.class, (toolStackView, modifierEntry) -> true);

    @SubscribeEvent
    void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    void registerSerializers(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.RECIPE_SERIALIZER) {
            ModifierModule.LOADER.register(TCompat.getResource("aether_forged"), AetherForgedModule.LOADER);
        }
    }

    public static void addToolTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output tab) {
        Consumer<ItemStack> output = tab::accept;
        ToolBuildHandler.addVariants(output, glaive.get(), "");
    }

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
