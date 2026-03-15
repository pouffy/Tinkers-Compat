package io.github.pouffy.tcompat.common.material;

import io.github.pouffy.tcompat.common.util.RockVariantBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import slimeknights.mantle.recipe.condition.TagFilledCondition;
import slimeknights.mantle.recipe.data.ConsumerWrapperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static io.github.pouffy.tcompat.common.data.TCTags.Items.local;
import static io.github.pouffy.tcompat.common.data.TCTags.Items.named;

public enum TCRocks {
    // Overworld Biome Mods
    RED_ROCK(builder("biomeswevegone")),
    DACITE(builder("biomeswevegone")),
    CHALK(builder("regions_unexplored")),

    // Quark
    LIMESTONE(builder("quark", "create")),
    JASPER(builder("quark")),
    SHALE(builder("quark")),
    PERMAFROST(builder("quark", "ad_astra")),

    // Vanilla
    DRIPSTONE(builder("minecraft").specialRockId("minecraft", "dripstone_block")),

    // Aether
    HOLYSTONE(builder("aether")),
    ASETERITE(builder("deep_aether")),
    CLORITE(builder("deep_aether")),
    DIVINITE(builder("aether_redux")),
    DRIFTSHALE(builder("aether_redux")),
    ;

    public final String name;
    public final RockVariantBuilder builder;

    TCRocks(RockVariantBuilder builder) {
        this.name = this.name().toLowerCase();
        this.builder = builder;
    }

    public boolean hasRockRedirect() {
        return this.builder.redirectRockTag != null;
    }

    public List<String> getNamespaces() {
        return this.builder.namespaces;
    }

    public TagKey<Item> getSpecialRockTag(String namespace) {
        return this.builder.specialRockTag.get(namespace);
    }

    public ResourceLocation getSpecialRockId(String namespace) {
        return this.builder.specialRockId.get(namespace);
    }

    // Creates a consumer that can only works if the required mods are loaded OR if the tags are filled.
    public Consumer<FinishedRecipe> makeConsumer(Consumer<FinishedRecipe> initial) {
        List<ICondition> conditions = new ArrayList<>();
        getNamespaces().forEach(namespace -> {
            conditions.add(new ModLoadedCondition(namespace));
        });
        conditions.add(new TagFilledCondition<>(rockTag()));
        return withCondition(initial, new OrCondition(conditions.toArray(new ICondition[0])));
    }

    static RockVariantBuilder builder(String... namespaces) {
        return new RockVariantBuilder(namespaces);
    }

    public TagKey<Item> externalRockTag(String namespace) {
        return Objects.requireNonNullElseGet(getSpecialRockTag(namespace), () -> named(namespace, this.name().toLowerCase() + "_rocks"));
    }

    public TagKey<Item> rockTag() {
        return local("rocks/" + this.name().toLowerCase());
    }

    public static TagKey<Item> rockTag(String name) {
        for (TCRocks rock : values()) {
            if (rock.name().toLowerCase().equals(name)) {
                return rock.rockTag();
            }
        }
        return local("rocks/" + name);
    }

    private Consumer<FinishedRecipe> withCondition(Consumer<FinishedRecipe> consumer, ICondition... conditions) {
        ConsumerWrapperBuilder builder = ConsumerWrapperBuilder.wrap();
        for (ICondition condition : conditions) {
            builder.addCondition(condition);
        }
        return builder.build(consumer);
    }
}
