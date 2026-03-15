package io.github.pouffy.tcompat.common.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.*;

import static io.github.pouffy.tcompat.common.data.TCTags.Items.named;

public class RockVariantBuilder {
    public List<String> namespaces = List.of();
    public Map<String, TagKey<Item>> specialRockTag = new HashMap<>();
    public TagKey<Item> redirectRockTag = null;
    public Map<String, ResourceLocation> specialRockId = new HashMap<>();

    public RockVariantBuilder(String... namespaces) {
        Collection<String> namespaceList = new ArrayList<>(List.of());
        namespaceList.addAll(Arrays.asList(namespaces));
        this.namespaces = namespaceList.stream().toList();
    }

    public RockVariantBuilder specialRockTag(String namespace, String name) {
        TagKey<Item> tag = named(namespace, name);
        this.specialRockTag.put(namespace, tag);
        return this;
    }

    public RockVariantBuilder redirectRockTag(String namespace, String name) {
        this.redirectRockTag = named(namespace, name);
        return this;
    }

    public RockVariantBuilder specialRockId(String namespace, String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace, name);
        this.specialRockId.put(namespace, id);
        return this;
    }
}
