package io.github.pouffy.tcompat.compat.deep_aether;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class DeepAetherInit extends CompatInitializer {
    public static final ModifierDeferredRegister DEEP_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final ItemDeferredRegister DEEP_I = new ItemDeferredRegister(TCompat.MOD_ID);

    public static final StaticModifier<GaleModifier> gale = DEEP_M.register("gale", GaleModifier::new); // Stormforged Steel

    public static final StaticModifier<DullingModifier> skyjade = DEEP_M.register("skyjade", DullingModifier::new);

    public static final ItemObject<Item> stormforgedSteelIngot = itemForMod("deep_aether", "stormforged_steel_ingot", new Item.Properties(), DEEP_I);

    public static void addCommonTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        output.accept(stormforgedSteelIngot);
    }

    public static void init(IEventBus eventBus) {
        DEEP_M.register(eventBus);
        DEEP_I.register(eventBus);
    }
}
