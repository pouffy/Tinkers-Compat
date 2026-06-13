package io.github.pouffy.tcompat.compat.quark;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.util.CompatInitializer;
import io.github.pouffy.tcompat.compat.quark.item.TCSlimeInABucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;

public class QuarkInit extends CompatInitializer {
    public static final ModifierDeferredRegister QUARK_M = ModifierDeferredRegister.create(TCompat.MOD_ID);
    public static final ItemDeferredRegister QUARK_I = new ItemDeferredRegister(TCompat.MOD_ID);

    public static final ItemObject<TCSlimeInABucketItem.Sky> skySlimeInABucket = itemForMod("quark", "sky_slime_in_a_bucket", () -> new TCSlimeInABucketItem.Sky(new Item.Properties()), QUARK_I);
    public static final ItemObject<TCSlimeInABucketItem.Ender> enderSlimeInABucket = itemForMod("quark", "ender_slime_in_a_bucket", () -> new TCSlimeInABucketItem.Ender(new Item.Properties()), QUARK_I);
    public static final ItemObject<TCSlimeInABucketItem.Terra> terracubeInABucket = itemForMod("quark", "terracube_in_a_bucket", () -> new TCSlimeInABucketItem.Terra(new Item.Properties()), QUARK_I);

    public static void addCommonTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        if (QuarkHandler.isSlimeInABucketEnabled()) {
            output.accept(skySlimeInABucket);
            output.accept(enderSlimeInABucket);
            output.accept(terracubeInABucket);
        }
    }

    public static void init(IEventBus eventBus) {
        QUARK_M.register(eventBus);
        if (QuarkHandler.isSlimeInABucketEnabled()) {
            QUARK_I.register(eventBus);
        }
    }
}
