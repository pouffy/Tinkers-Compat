package io.github.pouffy.tcompat.common.material;

import io.github.pouffy.tcompat.common.util.WoodVariantBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.recipe.condition.TagFilledCondition;
import slimeknights.mantle.recipe.data.ConsumerWrapperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static io.github.pouffy.tcompat.common.data.TCTags.Items.local;
import static io.github.pouffy.tcompat.common.data.TCTags.Items.named;

/**
 * A special container for all compatible wood variants.
 */

@SuppressWarnings("unused")
public enum TCWoods implements StringRepresentable {
    // Overworld Biome Mods
    ALPHA(builder("regions_unexplored")),
    ASPEN(builder("biomeswevegone")),
    BAOBAB(builder("biomeswevegone", "regions_unexplored")),
    BLACKWOOD(builder("regions_unexplored")),
    BLUE_BIOSHROOM(builder("regions_unexplored")),
    BLUE_ENCHANTED(builder("biomeswevegone")),
    BRIMWOOD(builder("regions_unexplored")),
    CIKA(builder("biomeswevegone")),
    COBALT(builder("regions_unexplored")),
    CYPRESS(builder("biomeswevegone", "regions_unexplored")),
    DEAD(builder("biomesoplenty", "regions_unexplored")),
    EBONY(builder("biomeswevegone")),
    EUCALYPTUS(builder("regions_unexplored")),
    FIR(builder("biomeswevegone", "biomesoplenty")),
    FLORUS(builder("biomeswevegone")),
    GREEN_BIOSHROOM(builder("regions_unexplored")),
    GREEN_ENCHANTED(builder("biomeswevegone")),
    HOLLY(builder("biomeswevegone")),
    IRONWOOD(builder("biomeswevegone")),
    JACARANDA(builder("biomeswevegone", "biomesoplenty")),
    JOSHUA(builder("regions_unexplored")),
    KAPOK(builder("regions_unexplored")),
    LARCH(builder("regions_unexplored")),
    MAGNOLIA(builder("regions_unexplored")),
    MAHOGANY(builder("biomeswevegone", "biomesoplenty")),
    MAPLE(builder("biomeswevegone", "biomesoplenty", "regions_unexplored")),
    MAUVE(builder("regions_unexplored")),
    PALM(builder("biomeswevegone", "biomesoplenty", "regions_unexplored")),
    PALO_VERDE(builder("biomeswevegone").noPlanks()),
    PINE(builder("biomeswevegone", "biomesoplenty")),
    PINK_BIOSHROOM(builder("regions_unexplored")),
    RAINBOW_EUCALYPTUS(builder("biomeswevegone")),
    REDWOOD(builder("biomeswevegone", "biomesoplenty", "regions_unexplored")),
    SAKURA(builder("biomeswevegone")),
    SOCOTRA(builder("regions_unexplored")),
    SKYRIS(builder("biomeswevegone")),
    WHITE_MANGROVE(builder("biomeswevegone")),
    WILLOW(builder("biomeswevegone", "biomesoplenty", "regions_unexplored")),
    WITCH_HAZEL(builder("biomeswevegone")),
    YELLOW_BIOSHROOM(builder("regions_unexplored")),
    ZELKOVA(builder("biomeswevegone")),
    HELLBARK(builder("biomesoplenty")),

    // Quark
    ANCIENT(builder("quark").alias("ashen")),
    AZALEA(builder("quark")),
    BLOSSOM(builder("quark").alias("trumpet")),

    // Aether
    SKYROOT(builder("aether")),
    ROSEROOT(builder("deep_aether")),
    YAGROOT(builder("deep_aether")),
    CRUDEROOT(builder("deep_aether")),
    CONBERRY(builder("deep_aether")),
    SUNROOT(builder("deep_aether")),
    CLOUDCAP(builder("aether_redux").stem()),
    JELLYSHROOM(builder("aether_redux").stem()),
    BLIGHTWILLOW(builder("aether_redux")),
    FIELDSPROOT(builder("aether_redux")),
    CRYSTAL(builder("aether_redux")),
    GLACIA(builder("aether_redux")),

    // Ad Astra
    AERONOS(builder("ad_astra").logType("caps")),
    STROPHAR(builder("ad_astra").logType("caps")),
    GLACIAN(builder("ad_astra")),

    // Betterend
    MOSSY_GLOWSHROOM(builder("betterend")),
    PYTHADENDRON(builder("betterend")),
    END_LOTUS(builder("betterend")),
    LACUGROVE(builder("betterend")),
    DRAGON_TREE(builder("betterend")),
    TENANEA(builder("betterend")),
    HELIX_TREE(builder("betterend")),
    UMBRELLA_TREE(builder("betterend")),
    END_JELLYSHROOM(builder("betterend").specialLogTag("betterend", "jellyshroom_logs").alias("jellyshroom")),
    LUCERNIA(builder("betterend")),

    // Betternether
    NETHER_REED(builder("betternether").noLogs()),
    STALAGNATE(builder("betternether")),
    NETHER_WILLOW(builder("betternether")),
    WART(builder("betternether")),
    RUBEUS(builder("betternether")),
    MUSHROOM_FIR(builder("betternether")),
    NETHER_MUSHROOM(builder("betternether").stem()),
    ANCHOR_TREE(builder("betternether")),
    NETHER_SAKURA(builder("betternether")),
    ;

    public final String name;
    public final WoodVariantBuilder builder;

    TCWoods(WoodVariantBuilder builder) {
        this.builder = builder;
        this.name = this.name().toLowerCase();
    }

    public boolean isStem() {
        return this.builder.stem;
    }

    public boolean hasPlankRedirect() {
        return this.builder.redirectPlankTag != null;
    }

    public List<String> getNamespaces() {
        return this.builder.namespaces;
    }

    public TagKey<Item> getSpecialLogTag(String namespace) {
        return this.builder.specialLogTag.get(namespace);
    }
    public ResourceLocation getSpecialPlankId(String namespace) {
        return this.builder.specialPlankId.get(namespace);
    }

    // Creates a consumer that can only works if the required mods are loaded OR if the tags are filled.
    public Consumer<FinishedRecipe> makeConsumer(Consumer<FinishedRecipe> initial) {
        List<ICondition> conditions = new ArrayList<>();
        getNamespaces().forEach(namespace -> conditions.add(new ModLoadedCondition(namespace)));
        conditions.add(new TagFilledCondition<>(logTag()));
        conditions.add(new TagFilledCondition<>(plankTag()));
        return withCondition(initial, new OrCondition(conditions.toArray(new ICondition[0])));
    }

    static WoodVariantBuilder builder(String... namespaces) {
        return new WoodVariantBuilder(namespaces);
    }

    public TagKey<Item> externalLogTag(String namespace) {
        return Objects.requireNonNullElseGet(getSpecialLogTag(namespace), () -> named(namespace, this.name().toLowerCase() + "_" + this.builder.logType));
    }

    public TagKey<Item> plankTag() {
        return Objects.requireNonNullElseGet(this.builder.redirectPlankTag, () -> local("planks/" + this.name().toLowerCase()));
    }

    public TagKey<Item> logTag() {
        return local(this.builder.logType + "/" + this.name().toLowerCase());
    }

    public static TagKey<Item> plankTag(String name) {
        for (TCWoods wood : values()) {
            if (wood.name().toLowerCase().equals(name)) {
                return wood.plankTag();
            }
        }
        return local("planks/" + name);
    }
    public static TagKey<Item> logTag(String name) {
        for (TCWoods wood : values()) {
            if (wood.name().toLowerCase().equals(name)) {
                return wood.logTag();
            }
        }
        return local("logs/" + name);
    }

    public boolean hasLogs() {
        return !this.builder.noLogs;
    }

    public boolean hasPlanks() {
        return !this.builder.noPlanks;
    }

    public static TagKey<Item> stemTag(String name) {
        return local("stems/" + name);
    }

    private Consumer<FinishedRecipe> withCondition(Consumer<FinishedRecipe> consumer, ICondition... conditions) {
        ConsumerWrapperBuilder builder = ConsumerWrapperBuilder.wrap();
        for (ICondition condition : conditions) {
            builder.addCondition(condition);
        }
        return builder.build(consumer);
    }

    //Used for providing an ID to the variant
    @Override
    public @NotNull String getSerializedName() {
        if (!this.builder.alias.isEmpty()) {
            return this.builder.alias;
        }
        return this.name().toLowerCase();
    }
}
