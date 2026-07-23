package io.github.pouffy.tcompat.common.material;

import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.datagen.lang.TCLangProv;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.IColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.ISpriteTransformer;
import slimeknights.tconstruct.library.client.data.spritetransformer.RecolorSpriteTransformer;
import slimeknights.tconstruct.library.client.materials.MaterialGeneratorInfo;
import slimeknights.tconstruct.library.json.JsonRedirect;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.util.LazyModifier;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;
import slimeknights.tconstruct.tools.modules.ArmorModuleBuilder;
import slimeknights.tconstruct.tools.stats.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

import static slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider.WOOD;

public class MaterialBuilder {
    public static final Map<MaterialVariantId, MaterialBuilder> variants = new HashMap<>();
    public static final Map<MaterialVariantId, MaterialBuilder> materials = new HashMap<>();
    public static final List<MaterialBuilder> materialBuilders = new ArrayList<>();
    public static final Map<MaterialBuilder, TCWoods> woodMaterials = new HashMap<>();
    public static final Map<MaterialBuilder, TCRocks> rockMaterials = new HashMap<>();

    @Getter
    private final MaterialVariantId id;
    @Getter
    private final String translationKey;
    @Getter
    private String englishName;
    @Getter
    private final boolean isVariant;
    @Getter
    private boolean excludedFromLoot;

    @Getter
    private Data data;
    @Getter
    private Stats stats;
    @Getter
    private Traits traits;
    @Getter
    private RenderInfo renderInfo;
    @Getter
    private SpriteInfo spriteInfo;

    private MaterialBuilder(ICondition condition, String name, @Nullable MaterialId parent) {
        if (parent != null) {
            this.id = MaterialVariantId.create(parent, name);
            this.translationKey = "material.%s.%s.%s".formatted(parent.getNamespace(), parent.getPath(), name);
            this.isVariant = true;
        } else {
            this.id = new MaterialId(TCompat.MOD_ID, name);
            this.translationKey = "material.%s.%s".formatted(TCompat.MOD_ID, name);
            this.isVariant = false;
        }
        this.data = Data.fresh(condition);
        this.stats = Stats.fresh();
        this.traits = Traits.fresh();
        this.renderInfo = RenderInfo.fresh();
        this.spriteInfo = SpriteInfo.fresh();

        this.englishName = TCLangProv.toEngStr(name);
        this.excludedFromLoot = false;
    }

    public static MaterialBuilder material(String modid, String name) {
        return material(new ModLoadedCondition(modid), name);
    }

    public static MaterialBuilder variant(String modid, String name, MaterialId variant) {
        return variant(new ModLoadedCondition(modid), name, variant);
    }

    public static MaterialBuilder material(ICondition condition, String name) {
        return new MaterialBuilder(condition, name, null);
    }

    public static MaterialBuilder variant(ICondition condition, String name, MaterialId variant) {
        return new MaterialBuilder(condition, name, variant);
    }

    public static List<MaterialBuilder> getMaterials() {
        List<MaterialBuilder> builders = new ArrayList<>();
        for (var builder : materialBuilders) {
            if (builder.isVariant()) continue;
            builders.add(builder);
        }
        return builders;
    }

    public MaterialBuilder lang(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public MaterialBuilder excludeFromLoot() {
        this.excludedFromLoot = true;
        return this;
    }

    public MaterialBuilder data(Function<Data, Data> dataFunction) {
        this.assertNoVariant("data");
        this.data = dataFunction.apply(this.data);
        if (this.data.isDeprecated()) {
            return this.excludeFromLoot();
        }
        return this;
    }

    public MaterialBuilder stats(Function<Stats, Stats> statsFunction) {
        this.assertNoVariant("stats");
        this.stats = statsFunction.apply(this.stats);
        return this;
    }

    public MaterialBuilder traits(Function<Traits, Traits> traitsFunction) {
        this.assertNoVariant("traits");
        this.traits = traitsFunction.apply(this.traits);
        return this;
    }

    public MaterialBuilder renderInfo(Function<RenderInfo, RenderInfo> renderInfoFunction) {
        this.renderInfo = renderInfoFunction.apply(this.renderInfo);
        return this;
    }

    public MaterialBuilder spriteInfo(Function<SpriteInfo, SpriteInfo> spriteInfoFunction) {
        this.spriteInfo = spriteInfoFunction.apply(this.spriteInfo);
        return this;
    }

    // Variants shouldn't have traits or stats so this is useful there...
    private void assertNoVariant(String type) {
        if (this.isVariant) {
            throw new IllegalStateException("%s is a variant and cannot support %s".formatted(this.getId().getLocation('_').toString(), type));
        }
    }

    private void assertVariant(String type) {
        if (!this.isVariant) {
            throw new IllegalStateException("%s is not a variant and cannot support %s".formatted(this.getId().getLocation('_').toString(), type));
        }
    }

    public MaterialId buildMaterial() {
        MaterialId materialId = materialId();
        materials.put(materialId, this);
        materialBuilders.add(this);
        return materialId;
    }

    public MaterialVariantId buildVariant() {
        MaterialVariantId variantId = variantId();
        variants.put(variantId, this);
        materialBuilders.add(this);
        return variantId;
    }

    public MaterialId materialId() {
        assertNoVariant("material ids");
        return this.getId().getId();
    }

    public MaterialVariantId variantId() {
        assertVariant("variant ids");
        return this.getId();
    }

    public static class Data {
        @Getter
        private final ICondition condition;
        @Getter
        private int tier;
        @Getter
        private int order;
        @Getter
        private boolean craftable;
        @Getter
        private boolean hidden;
        @Getter
        private JsonRedirect[] redirect;

        @Getter
        private boolean deprecated;

        public Data(ICondition condition, int tier, int order, boolean craftable, boolean hidden, JsonRedirect[] redirect) {
            this.condition = condition;
            this.tier = tier;
            this.order = order;
            this.craftable = craftable;
            this.hidden = hidden;
            this.redirect = redirect;
        }

        public static Data fresh(ICondition condition) {
            return new Data(condition, 1, -1, false, false, new JsonRedirect[0]);
        }

        public Data deprecate() {
            this.hidden = true;
            this.craftable = false;
            this.deprecated = true;
            return this;
        }

        public Data tier(int tier) {
            this.tier = tier;
            return this;
        }

        public Data order(int order) {
            this.order = order;
            return this;
        }

        public Data craftable(boolean craftable) {
            this.craftable = craftable;
            return this;
        }

        public Data hidden(boolean hidden) {
            this.hidden = hidden;
            return this;
        }

        public Data redirect(JsonRedirect... redirect) {
            this.redirect = redirect;
            return this;
        }
    }

    public record Stats(Map<MaterialStatsId, IMaterialStats> stats, Map<MaterialStatsId, IMaterialStats> optionalStats) {

        public static Stats fresh() {
            return new Stats(new HashMap<>(), new HashMap<>());
        }

        public Stats stat(IMaterialStats... stats) {
            for (IMaterialStats stat : stats) {
                if (!this.stats.containsKey(stat.getIdentifier())) {
                    this.stats.put(stat.getIdentifier(), stat);
                }
            }
            return this;
        }

        public Stats statOptional(IMaterialStats... stats) {
            for (IMaterialStats stat : stats) {
                if (!this.optionalStats.containsKey(stat.getIdentifier())) {
                    this.optionalStats.put(stat.getIdentifier(), stat);
                }
            }
            return this;
        }

        public Stats armorStats(ArmorModuleBuilder<? extends IMaterialStats> statBuilder, IMaterialStats... otherStats) {
            IMaterialStats[] stats = new IMaterialStats[4];

            for(ArmorItem.Type slotType : ArmorItem.Type.values()) {
                stats[slotType.ordinal()] = statBuilder.build(slotType);
            }

            this.stat(stats);
            if (otherStats.length > 0) {
                this.stat(otherStats);
            }
            return this;
        }

        public Stats armorStatsOptional(ArmorModuleBuilder<? extends IMaterialStats> statBuilder, IMaterialStats... otherStats) {
            IMaterialStats[] stats = new IMaterialStats[4];

            for(ArmorItem.Type slotType : ArmorItem.Type.values()) {
                stats[slotType.ordinal()] = statBuilder.build(slotType);
            }

            this.statOptional(stats);
            if (otherStats.length > 0) {
                this.statOptional(otherStats);
            }
            return this;
        }

        public Stats armorShieldStats(ArmorModuleBuilder.ArmorShieldModuleBuilder<? extends IMaterialStats> statBuilder, IMaterialStats... otherStats) {
            this.armorStats(statBuilder, otherStats);
            this.stat(statBuilder.buildShield());
            return this;
        }

        public Stats armorShieldStatsOptional(ArmorModuleBuilder.ArmorShieldModuleBuilder<? extends IMaterialStats> statBuilder, IMaterialStats... otherStats) {
            this.armorStatsOptional(statBuilder, otherStats);
            this.statOptional(statBuilder.buildShield());
            return this;
        }
    }

    public record Traits(List<ModifierEntry> defaultTraits, Map<MaterialStatsId, List<ModifierEntry>> traits) {

        public static Traits fresh() {
            return new Traits(new ArrayList<>(), new HashMap<>());
        }

        public Traits trait(Object... traits) {
            for (Object trait : traits) {
                if (trait instanceof ModifierEntry entry) {
                    this.trait(entry);
                } else if (trait instanceof LazyModifier lazy) {
                    this.trait(lazy);
                } else if (trait instanceof ModifierId id) {
                    this.trait(id);
                }
            }
            return this;
        }

        public Traits trait(MaterialStatsId stat, Object... traits) {
            for (Object trait : traits) {
                if (trait instanceof ModifierEntry entry) {
                    this.trait(stat, entry);
                } else if (trait instanceof LazyModifier lazy) {
                    this.trait(stat, lazy);
                } else if (trait instanceof ModifierId id) {
                    this.trait(stat, id);
                }
            }
            return this;
        }

        public Traits trait(ModifierId... modifiers) {
            for (ModifierId modifier : modifiers) {
                if (!this.defaultTraits.stream().map(ModifierEntry::getId).toList().contains(modifier)) {
                    this.defaultTraits.add(new ModifierEntry(modifier, 1));
                }
            }
            return this;
        }

        public Traits trait(ModifierEntry... modifiers) {
            for (ModifierEntry modifier : modifiers) {
                if (!this.defaultTraits.stream().map(ModifierEntry::getId).toList().contains(modifier.getId())) {
                    this.defaultTraits.add(modifier);
                }
            }
            return this;
        }

        public Traits trait(LazyModifier... modifiers) {
            for (LazyModifier modifier : modifiers) {
                if (!this.defaultTraits.stream().map(ModifierEntry::getId).toList().contains(modifier.getId())) {
                    this.defaultTraits.add(new ModifierEntry(modifier, 1));
                }
            }
            return this;
        }

        public Traits trait(MaterialStatsId stat, ModifierId... modifiers) {
            for (ModifierId modifier : modifiers) {
                var existing = this.traits.get(stat);
                boolean safe = existing == null || !existing.stream().map(ModifierEntry::getId).toList().contains(modifier);
                if (safe) {
                    this.traits.compute(stat, (id, list) -> {
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(new ModifierEntry(modifier, 1));
                        return list;
                    });
                }
            }
            return this;
        }

        public Traits trait(MaterialStatsId stat, ModifierEntry... modifiers) {
            for (ModifierEntry modifier : modifiers) {
                var existing = this.traits.get(stat);
                boolean safe = existing == null || !existing.stream().map(ModifierEntry::getId).toList().contains(modifier.getId());
                if (safe) {
                    this.traits.compute(stat, (id, list) -> {
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(modifier);
                        return list;
                    });
                }
            }
            return this;
        }

        public Traits trait(MaterialStatsId stat, LazyModifier... modifiers) {
            for (LazyModifier modifier : modifiers) {
                var existing = this.traits.get(stat);
                boolean safe = existing == null || !existing.stream().map(ModifierEntry::getId).toList().contains(modifier.getId());
                if (safe) {
                    this.traits.compute(stat, (id, list) -> {
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(new ModifierEntry(modifier, 1));
                        return list;
                    });
                }
            }
            return this;
        }
    }

    public static class RenderInfo {
        @Getter @Nullable
        private ResourceLocation texture;
        @Getter @Nullable
        private ResourceLocation parent;
        @Getter
        private String[] fallbacks;
        @Getter
        private int color;
        @Getter
        private int luminosity;
        @Getter
        private MaterialGeneratorInfo generator;

        public RenderInfo(@Nullable ResourceLocation texture, @Nullable ResourceLocation parent, String[] fallbacks, int color, int luminosity, MaterialGeneratorInfo generator) {
            this.texture = texture;
            this.parent = parent;
            this.fallbacks = fallbacks;
            this.color = color;
            this.luminosity = luminosity;
            this.generator = generator;
        }

        public static RenderInfo fresh() {
            return new RenderInfo(null, null, new String[0], -1, 0, null);
        }

        public RenderInfo texture(@Nullable ResourceLocation texture) {
            this.texture = texture;
            return this;
        }

        public RenderInfo parent(@Nullable ResourceLocation parent) {
            this.parent = parent;
            return this;
        }

        public RenderInfo fallbacks(String... fallbacks) {
            this.fallbacks = fallbacks;
            return this;
        }

        public RenderInfo color(int color) {
            if ((color & -16777216) == 0) {
                color |= -16777216;
            }
            this.color = color;
            return this;
        }

        public RenderInfo luminosity(int luminosity) {
            this.luminosity = luminosity;
            return this;
        }

        public RenderInfo generator(MaterialGeneratorInfo generator) {
            this.generator = generator;
            return this;
        }
    }

    @CanIgnoreReturnValue
    public static class SpriteInfo {
        @Getter
        private String[] fallbacks;
        @Getter
        private final ImmutableSet.Builder<MaterialStatsId> statTypes;
        @Getter @Nullable
        private ISpriteTransformer transformer;

        public SpriteInfo(String[] fallbacks, ImmutableSet.Builder<MaterialStatsId> statTypes, @Nullable ISpriteTransformer transformer) {
            this.fallbacks = fallbacks;
            this.statTypes = statTypes;
            this.transformer = transformer;
        }

        public static SpriteInfo fresh() {
            return new SpriteInfo(new String[0], ImmutableSet.builder(), null);
        }

        public SpriteInfo fallbacks(String... fallbacks) {
            this.fallbacks = fallbacks;
            return this;
        }

        public SpriteInfo colorMapper(IColorMapping mapping) {
            return this.transformer(new RecolorSpriteTransformer(mapping));
        }

        public SpriteInfo planks() {
            return this.fallbacks("wood", "stick", "primitive").meleeHarvest().ranged().shieldCore().arrowShaft().statType(WOOD);
        }

        public SpriteInfo rock() {
            return this.fallbacks("rock").meleeHarvest();
        }

        public SpriteInfo flint() {
            return this.fallbacks("crystal", "rock", "stick").meleeHarvest().arrowHead();
        }

        public SpriteInfo scales() {
            return this.fallbacks("scales", "metal").armor();
        }

        public SpriteInfo vines() {
            return this.fallbacks("primitive", "cloth").binding().bowstring().maille();
        }

        public SpriteInfo sixColor(int c63, int c102, int c140, int c178, int c216, int c255) {
            return this.colorMapper(GreyToColorMapping.builderFromBlack()
                    .addARGB(63, c63)
                    .addARGB(102, c102)
                    .addARGB(140, c140)
                    .addARGB(178, c178)
                    .addARGB(216, c216)
                    .addARGB(255, c255)
                    .build());
        }

        public SpriteInfo sevenColor(int c63, int c102, int c140, int c178, int c216, int c234, int c255) {
            return this.colorMapper(GreyToColorMapping.builderFromBlack()
                    .addARGB(63, c63)
                    .addARGB(102, c102)
                    .addARGB(140, c140)
                    .addARGB(178, c178)
                    .addARGB(216, c216)
                    .addARGB(234, c234)
                    .addARGB(255, c255)
                    .build());
        }

        public SpriteInfo statType(MaterialStatsId statsId) {
            this.statTypes.add(statsId);
            return this;
        }

        public SpriteInfo statType(MaterialStatsId... statsId) {
            this.statTypes.add(statsId);
            return this;
        }

        public SpriteInfo statType(IMaterialStats... stats) {
            for(IMaterialStats stat : stats) {
                this.statTypes.add(stat.getIdentifier());
            }
            return this;
        }

        public SpriteInfo statType(MaterialStatType<?>... stats) {
            for(MaterialStatType<?> stat : stats) {
                this.statTypes.add(stat.getId());
            }
            return this;
        }

        public SpriteInfo statType(List<? extends MaterialStatType<?>> stats) {
            for(MaterialStatType<?> stat : stats) {
                this.statTypes.add(stat.getId());
            }
            return this;
        }

        public SpriteInfo repairKit() {
            return this.statType(StatlessMaterialStats.REPAIR_KIT.getIdentifier());
        }

        public SpriteInfo meleeHarvest() {
            this.statType(HeadMaterialStats.ID);
            this.statType(HandleMaterialStats.ID);
            this.statType(StatlessMaterialStats.BINDING.getIdentifier());
            this.repairKit();
            return this;
        }

        public SpriteInfo head() {
            this.statType(HeadMaterialStats.ID);
            this.repairKit();
            return this;
        }

        public SpriteInfo handle() {
            this.statType(HandleMaterialStats.ID);
            this.repairKit();
            return this;
        }

        public SpriteInfo binding() {
            this.statType(StatlessMaterialStats.BINDING.getIdentifier());
            this.repairKit();
            return this;
        }

        public SpriteInfo bowstring() {
            this.statType(StatlessMaterialStats.BOWSTRING.getIdentifier());
            this.repairKit();
            return this;
        }

        public SpriteInfo ranged() {
            this.statType(LimbMaterialStats.ID);
            this.statType(GripMaterialStats.ID);
            this.repairKit();
            return this;
        }

        public SpriteInfo maille() {
            this.statType(StatlessMaterialStats.MAILLE.getIdentifier());
            this.statType(TinkerPartSpriteProvider.ARMOR_MAILLE);
            return this;
        }

        public SpriteInfo cuirass() {
            this.statType(StatlessMaterialStats.CUIRASS.getIdentifier());
            this.statType(TinkerPartSpriteProvider.ARMOR_CUIRASS);
            this.repairKit();
            return this;
        }

        public SpriteInfo plating() {
            this.statType(TinkerPartSpriteProvider.ARMOR_PLATING);

            for(MaterialStatType<?> type : PlatingMaterialStats.TYPES) {
                this.statType(type.getId());
            }

            this.repairKit();
            return this;
        }

        public SpriteInfo plating(MaterialStatsId... extraStats) {
            Collection<MaterialStatsId> stats = new ArrayList<>(List.of());
            stats.add(TinkerPartSpriteProvider.ARMOR_PLATING);
            for (MaterialStatType<?> type : PlatingMaterialStats.TYPES) {
                stats.add(type.getId());
            }
            stats.addAll(Arrays.asList(extraStats));
            stats.add(StatlessMaterialStats.REPAIR_KIT.getIdentifier());

            for(MaterialStatsId type : stats) {
                this.statType(type);
            }
            return this;
        }

        public SpriteInfo armor() {
            this.plating();
            this.maille();
            return this;
        }

        public SpriteInfo shieldCore() {
            this.statType(StatlessMaterialStats.SHIELD_CORE);
            this.repairKit();
            return this;
        }

        public SpriteInfo arrowHead() {
            this.statType(StatlessMaterialStats.ARROW_HEAD);
            return this;
        }

        public SpriteInfo arrowShaft() {
            this.statType(StatlessMaterialStats.ARROW_SHAFT);
            return this;
        }

        public SpriteInfo fletching() {
            this.statType(StatlessMaterialStats.FLETCHING);
            return this;
        }

        public SpriteInfo transformer(@Nullable ISpriteTransformer transformer) {
            this.transformer = transformer;
            return this;
        }
    }
}
