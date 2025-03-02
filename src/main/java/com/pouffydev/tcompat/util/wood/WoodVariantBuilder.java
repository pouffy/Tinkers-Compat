package com.pouffydev.tcompat.util.wood;

import com.pouffydev.tcompat.data.TComTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.*;

import static com.pouffydev.tcompat.data.TComTags.Items.named;

public class WoodVariantBuilder {
    public boolean stem = false;
    public List<String> namespaces = List.of();
    public Map<String, TagKey<Item>> specialLogTag = new HashMap<>();
    public TagKey<Item> redirectPlankTag = null;
    public Map<String, ResourceLocation> specialPlankId = new HashMap<>();

    public WoodVariantBuilder(String... namespaces) {
        Collection<String> namespaceList = new ArrayList<>(List.of());
        namespaceList.addAll(Arrays.asList(namespaces));
        this.namespaces = namespaceList.stream().toList();
    }

    public WoodVariantBuilder stem() {
        this.stem = true;
        return this;
    }

    public WoodVariantBuilder specialLogTag(String namespace, String name) {
        TagKey<Item> tag = named(namespace, name);
        this.specialLogTag.put(namespace, tag);
        return this;
    }

    public WoodVariantBuilder redirectPlankTag(String namespace, String name) {
        this.redirectPlankTag = named(namespace, name);
        return this;
    }

    public WoodVariantBuilder specialPlankId(String namespace, String name) {
        ResourceLocation id = new ResourceLocation(namespace, name);
        this.specialPlankId.put(namespace, id);
        return this;
    }
}
