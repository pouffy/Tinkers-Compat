package io.github.pouffy.tcompat.compat.aether;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.aether.modifier.combat.melee.AscensionModifier;
import io.github.pouffy.tcompat.compat.aether.modifier.combat.melee.BloodsuckingModifier;
import io.github.pouffy.tcompat.compat.aether.modifier.combat.melee.ThunderstruckModifier;
import io.github.pouffy.tcompat.compat.aether.modifier.harvest.AmbrogenModifier;
import io.github.pouffy.tcompat.compat.aether.modifier.harvest.AutochantModifier;
import io.github.pouffy.tcompat.compat.aether.modifier.harvest.AutofreezeModifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class AetherInit extends CompatInitializer {
    public static final ModifierDeferredRegister AETHER_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final ItemDeferredRegister AETHER_I = new ItemDeferredRegister(TCompat.MOD_ID);

    //Traits
    public static final StaticModifier<AmbrogenModifier> ambrogen = AETHER_M.register("ambrogen", AmbrogenModifier::new); // Holystone
    public static final StaticModifier<AscensionModifier> ascension = AETHER_M.register("ascension", AscensionModifier::new); // Gravitite
    public static final StaticModifier<ThunderstruckModifier> thunderstruck = AETHER_M.register("thunderstruck", ThunderstruckModifier::new); // Lightnum
    public static final StaticModifier<BloodsuckingModifier> bloodsucking = AETHER_M.register("bloodsucking", BloodsuckingModifier::new); // Draculite

    //Craftable Modifiers
    public static final StaticModifier<AutochantModifier> autochant = AETHER_M.register("autochant", AutochantModifier::new);
    public static final StaticModifier<AutofreezeModifier> autofreeze = AETHER_M.register("autofreeze", AutofreezeModifier::new);

    public static final ItemObject<Item> lightnumIngot = itemForMod("aether", "lightnum_ingot", new Item.Properties(), AETHER_I);
    public static final ItemObject<Item> lightnumNugget = itemForMod("aether", "lightnum_nugget", new Item.Properties(), AETHER_I);
    public static final ItemObject<Item> draculiteIngot = itemForMod("aether", "draculite_ingot", new Item.Properties(), AETHER_I);
    public static final ItemObject<Item> draculiteNugget = itemForMod("aether", "draculite_nugget", new Item.Properties(), AETHER_I);

    public static void addCommonTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        output.accept(lightnumIngot);
        output.accept(lightnumNugget);
        output.accept(draculiteIngot);
        output.accept(draculiteNugget);
    }

    public static void init(IEventBus eventBus) {
        AETHER_M.register(eventBus);
        AETHER_I.register(eventBus);
        AetherMaterials.staticInit();
    }
}
