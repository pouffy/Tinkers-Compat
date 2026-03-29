package io.github.pouffy.tcompat.datagen.tag;

import io.github.pouffy.tcompat.TCompat;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class TCEntityTagProv extends EntityTypeTagsProvider {

    public TCEntityTagProv(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, TCompat.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(create("tcompat:fire_dragons")).addOptional(getResource("iceandfire:fire_dragon"));
        tag(create("tcompat:ice_dragons")).addOptional(getResource("iceandfire:ice_dragon"));
        tag(create("tcompat:lightning_dragons")).addOptional(getResource("iceandfire:lightning_dragon"));
    }

    public static TagKey<EntityType<?>> create(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse(name));
    }
}
