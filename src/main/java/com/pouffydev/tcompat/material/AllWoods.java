package com.pouffydev.tcompat.material;

import com.pouffydev.tcompat.util.wood.WoodVariantBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Objects;

import static com.pouffydev.tcompat.data.TComTags.Items.local;
import static com.pouffydev.tcompat.data.TComTags.Items.named;

public enum AllWoods {
    //BWG, BOP, TC
    ASPEN(builder("biomeswevegone")),
    BAOBAB(builder("biomeswevegone")),
    BLUE_ENCHANTED(builder("biomeswevegone")),
    CIKA(builder("biomeswevegone")),
    CYPRESS(builder("biomeswevegone")),
    EBONY(builder("biomeswevegone")),
    FIR(builder("biomeswevegone", "biomesoplenty")),
    FLORUS(builder("biomeswevegone")),
    GREEN_ENCHANTED(builder("biomeswevegone")),
    HOLLY(builder("biomeswevegone")),
    IRONWOOD(builder("biomeswevegone")),
    JACARANDA(builder("biomeswevegone", "biomesoplenty")),
    MAHOGANY(builder("biomeswevegone", "biomesoplenty")),
    MAPLE(builder("biomeswevegone", "biomesoplenty")),
    PALM(builder("biomeswevegone", "biomesoplenty")),
    PALO_VERDE(builder("biomeswevegone")),
    PINE(builder("biomeswevegone", "biomesoplenty")),
    RAINBOW_EUCALYPTUS(builder("biomeswevegone")),
    REDWOOD(builder("biomeswevegone", "biomesoplenty")),
    SAKURA(builder("biomeswevegone")),
    SKYRIS(builder("biomeswevegone")),
    WHITE_MANGROVE(builder("biomeswevegone")),
    WILLOW(builder("biomeswevegone", "biomesoplenty")),
    WITCH_HAZEL(builder("biomeswevegone")),
    ZELKOVA(builder("biomeswevegone")),

    //Aether
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

    //Twilight Forest
    TWILIGHT_OAK(builder("twilightforest")),
    CANOPY(builder("twilightforest")),
    MANGROVE(builder("twilightforest")),
    DARKWOOD(builder("twilightforest").specialPlankId("twilightforest", "dark_planks")),
    TIMEWOOD(builder("twilightforest").specialLogTag("twilightforest", "timewood_logs").specialPlankId("twilightforest", "time_planks")),
    TRANSWOOD(builder("twilightforest").specialLogTag("twilightforest", "transwood_logs").specialPlankId("twilightforest", "transformation_planks")),
    MINEWOOD(builder("twilightforest").specialLogTag("twilightforest", "mining_logs").specialPlankId("twilightforest", "mining_planks")),
    SORTINGWOOD(builder("twilightforest").specialLogTag("twilightforest", "sortwood_logs").specialPlankId("twilightforest", "sorting_planks")),
    TOWERWOOD(builder("twilightforest").redirectPlankTag("twilightforest", "towerwood"))
    ;

    public final String name;
    public final WoodVariantBuilder builder;

    AllWoods(WoodVariantBuilder builder) {
        this.name = this.name().toLowerCase();
        this.builder = builder;
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

    static WoodVariantBuilder builder(String... namespaces) {
        return new WoodVariantBuilder(namespaces);
    }

    public TagKey<Item> externalLogTag(String namespace) {
        String suffix = this.builder.stem ? "_stems" : "_logs";
        return Objects.requireNonNullElseGet(getSpecialLogTag(namespace), () -> named(namespace, this.name().toLowerCase() + suffix));
    }
    public TagKey<Item> plankTag() {
        return Objects.requireNonNullElseGet(this.builder.redirectPlankTag, () -> local("planks/" + this.name().toLowerCase()));
    }
    public TagKey<Item> logTag() {
        String prefix = this.builder.stem ? "stems/" : "logs/";
        return local(prefix + this.name().toLowerCase());
    }
    public static TagKey<Item> plankTag(String name) {
        for (AllWoods wood : values()) {
            if (wood.name().toLowerCase().equals(name)) {
                return wood.plankTag();
            }
        }
        return local("planks/" + name);
    }
    public static TagKey<Item> logTag(String name) {
        for (AllWoods wood : values()) {
            if (wood.name().toLowerCase().equals(name)) {
                return wood.logTag();
            }
        }
        return local("logs/" + name);
    }
    public static TagKey<Item> stemTag(String name) {
        return local("stems/" + name);
    }
}
