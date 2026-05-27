package io.github.pouffy.tcompat.datagen.tag;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.GameEventTagsProvider;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class TCGameEventTagProv extends GameEventTagsProvider {
    public TCGameEventTagProv(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
        super(packOutput, lookupProvider, TCompat.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(TCTags.GameEvents.FEET_VIBRATION).add(GameEvent.HIT_GROUND, GameEvent.STEP, GameEvent.SWIM, GameEvent.SPLASH);
        this.tag(TCTags.GameEvents.LEGS_VIBRATION).add(GameEvent.STEP, GameEvent.ENTITY_DISMOUNT);
        this.tag(TCTags.GameEvents.CHEST_VIBRATION).add(GameEvent.BLOCK_ACTIVATE, GameEvent.BLOCK_CLOSE, GameEvent.BLOCK_DEACTIVATE, GameEvent.BLOCK_PLACE, GameEvent.CONTAINER_CLOSE, GameEvent.CONTAINER_OPEN);
        this.tag(TCTags.GameEvents.HEAD_VIBRATION).add(GameEvent.EAT, GameEvent.DRINK);
        this.tag(TCTags.GameEvents.RANGED_VIBRATION).add(GameEvent.PROJECTILE_SHOOT, GameEvent.PROJECTILE_LAND);
        this.tag(TCTags.GameEvents.HARVEST_VIBRATION).add(GameEvent.BLOCK_DESTROY, GameEvent.SHEAR, GameEvent.FLUID_PICKUP);
        this.tag(TCTags.GameEvents.COMBAT_VIBRATION).add(GameEvent.ENTITY_DAMAGE, GameEvent.ENTITY_DIE);
        this.tag(TCTags.GameEvents.WINGS_VIBRATION).add(GameEvent.ELYTRA_GLIDE, GameEvent.FLAP);
    }
}
