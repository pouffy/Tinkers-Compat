package io.github.pouffy.tcompat.compat.ice_and_fire;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.ice_and_fire.modifier.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class IFInit extends CompatInitializer {
    public static final ModifierDeferredRegister IF_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final ItemDeferredRegister IF_I = new ItemDeferredRegister(TCompat.MOD_ID);

    //Traits
    public static final StaticModifier<FlamedModifier> flamed = IF_M.register("flamed", FlamedModifier::new);
    public static final StaticModifier<ScorchThornsModifier> scorchThorns = IF_M.register("scorch_thorns", ScorchThornsModifier::new);
    public static final StaticModifier<IcedModifier> iced = IF_M.register("iced", IcedModifier::new);
    public static final StaticModifier<FrostThornsModifier> frostThorns = IF_M.register("frost_thorns", FrostThornsModifier::new);
    public static final StaticModifier<LightningModifier> lightning = IF_M.register("lightning", LightningModifier::new);
    public static final StaticModifier<VoltThornsModifier> voltThorns = IF_M.register("volt_thorns", VoltThornsModifier::new);
    public static final StaticModifier<BreathlessModifier> breathless = IF_M.register("breathless", BreathlessModifier::new);

    public static final ItemObject<Item> fireDragonsteelNugget = itemForMod("iceandfire", "fire_dragonsteel_nugget", new Item.Properties(), IF_I);
    public static final ItemObject<Item> iceDragonsteelNugget = itemForMod("iceandfire", "ice_dragonsteel_nugget", new Item.Properties(), IF_I);
    public static final ItemObject<Item> lightningDragonsteelNugget = itemForMod("iceandfire", "lightning_dragonsteel_nugget", new Item.Properties(), IF_I);

    public static void addCommonTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        output.accept(fireDragonsteelNugget);
        output.accept(iceDragonsteelNugget);
        output.accept(lightningDragonsteelNugget);
    }

    public static void init(IEventBus eventBus) {
        IF_M.register(eventBus);
        IF_I.register(eventBus);
    }
}
