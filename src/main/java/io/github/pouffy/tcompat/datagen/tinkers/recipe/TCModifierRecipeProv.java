package io.github.pouffy.tcompat.datagen.tinkers.recipe;

import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.material.TCMaterials;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.common.material.TCWoods;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.recipe.AmbrofusionModifierRecipeBuilder;
import io.github.pouffy.tcompat.compat.betternether.BetternetherInit;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import io.github.pouffy.tcompat.compat.species.SpeciesInit;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.CompoundIngredient;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.data.ItemNameOutput;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.util.LazyModifier;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IncrementalModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.SwappableModifierRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.adding.SwappableModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.partbuilder.Pattern;
import slimeknights.tconstruct.library.recipe.partbuilder.recycle.PartBuilderRecycleBuilder;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.tables.TinkerTables;
import slimeknights.tconstruct.tools.TinkerModifiers;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.pouffy.tcompat.TCompat.getResource;

@SuppressWarnings({"unused", "SameParameterValue"})
@MethodsReturnNonnullByDefault
public class TCModifierRecipeProv extends TCBaseRecipeProvider {

    public TCModifierRecipeProv(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Modifier Recipes";
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        addModifierRecipes(consumer);
        addTextureRecipes(consumer);
    }

    private void addModifierRecipes(Consumer<FinishedRecipe> consumer) {
        String abilityFolder = "tools/modifiers/ability/";
        String abilitySalvage = "tools/modifiers/salvage/ability/";
        String slotlessFolder = "tools/modifiers/slotless/";
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, modLoaded("aether"));
        Consumer<FinishedRecipe> speciesConsumer = withCondition(consumer, modLoaded("species"));
        Consumer<FinishedRecipe> betternetherConsumer = withCondition(consumer, modLoaded("betternether"));
        Consumer<FinishedRecipe> iceandfireConsumer = withCondition(consumer, modLoaded("iceandfire"));

        Function<String, ResourceLocation> speciesId = name -> getResource("species", name);
        Function<String, ResourceLocation> aetherId = name -> getResource("aether", name);
        Function<String, ResourceLocation> betternetherId = name -> getResource("betternether", name);
        Function<String, ResourceLocation> iceandfireId = name -> getResource("iceandfire", name);

        ModifierRecipeBuilder.modifier(SpeciesInit.ricoshield)
                .setTools(TinkerTags.Items.SHIELDS)
                .addInput(ItemNameIngredient.from(speciesId.apply("broken_links")))
                .addInput(ItemNameIngredient.from(speciesId.apply("kinetic_core")))
                .addInput(ItemNameIngredient.from(speciesId.apply("broken_links")))
                .addInput(ItemNameIngredient.from(speciesId.apply("broken_links")))
                .addInput(ItemNameIngredient.from(speciesId.apply("broken_links")))
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.DEFENSE, 1)
                .saveSalvage(speciesConsumer, prefix(SpeciesInit.ricoshield, abilitySalvage))
                .save(speciesConsumer, prefix(SpeciesInit.ricoshield, abilityFolder));

        ModifierRecipeBuilder.modifier(SpeciesInit.swapping)
                .setTools(TinkerTags.Items.RANGED)
                .addInput(ItemNameIngredient.from(speciesId.apply("wicked_swapper")))
                .addInput(Items.ENDER_PEARL)
                .addInput(ItemNameIngredient.from(speciesId.apply("wicked_swapper")))
                .addInput(ItemNameIngredient.from(speciesId.apply("wicked_wax")))
                .addInput(ItemNameIngredient.from(speciesId.apply("wicked_wax")))
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.ABILITY, 1)
                .saveSalvage(speciesConsumer, prefix(SpeciesInit.swapping, abilitySalvage))
                .save(speciesConsumer, prefix(SpeciesInit.swapping, abilityFolder));

        ModifierRecipeBuilder.modifier(SpeciesInit.birt)
                .setTools(TinkerTags.Items.MELEE_WEAPON)
                .addInput(ItemNameIngredient.from(speciesId.apply("birt_egg")))
                .addInput(Items.REDSTONE)
                .addInput(ItemNameIngredient.from(speciesId.apply("birt_egg")))
                .addInput(Tags.Items.INGOTS_IRON)
                .addInput(Tags.Items.INGOTS_IRON)
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.ABILITY, 1)
                .saveSalvage(speciesConsumer, prefix(SpeciesInit.birt, abilitySalvage))
                .save(speciesConsumer, prefix(SpeciesInit.birt, abilityFolder));

        ModifierRecipeBuilder.modifier(AetherInit.autochant)
                .setTools(ingredientFromTags(TinkerTags.Items.HARVEST, TinkerTags.Items.FISHING_RODS))
                .addInput(TCTags.Items.AUTOCHANT_LEFT)
                .addInput(ItemNameIngredient.from(aetherId.apply("altar")))
                .addInput(TCTags.Items.AUTOCHANT_RIGHT)
                .addInput(ItemNameIngredient.from(aetherId.apply("ambrosium_block")))
                .addInput(ItemNameIngredient.from(aetherId.apply("ambrosium_block")))
                .setMaxLevel(1).setSlots(SlotType.ABILITY, 1)
                .saveSalvage(aetherConsumer, prefix(AetherInit.autochant, abilitySalvage))
                .save(aetherConsumer, prefix(AetherInit.autochant, abilityFolder));

        ModifierRecipeBuilder.modifier(AetherInit.autofreeze)
                .setTools(ingredientFromTags(TinkerTags.Items.HARVEST, TinkerTags.Items.FISHING_RODS))
                .addInput(TCTags.Items.AUTOFREEZE_LEFT)
                .addInput(ItemNameIngredient.from(aetherId.apply("freezer")))
                .addInput(TCTags.Items.AUTOFREEZE_RIGHT)
                .addInput(ItemNameIngredient.from(aetherId.apply("icestone")))
                .addInput(ItemNameIngredient.from(aetherId.apply("icestone")))
                .setMaxLevel(1).setSlots(SlotType.ABILITY, 1)
                .saveSalvage(aetherConsumer, prefix(AetherInit.autofreeze, abilitySalvage))
                .save(aetherConsumer, prefix(AetherInit.autofreeze, abilityFolder));

        ModifierRecipeBuilder.modifier(BetternetherInit.rubysFire)
                .setTools(ingredientFromTags(TinkerTags.Items.HARVEST, TinkerTags.Items.MELEE_WEAPON, TinkerTags.Items.FISHING_RODS))
                .addInput(ItemNameIngredient.from(betternetherId.apply("flaming_ruby_upgrade_smithing_template")))
                .addInput(Items.SCULK_CATALYST)
                .setMaxLevel(1).setSlots(SlotType.ABILITY, 1)
                .saveSalvage(betternetherConsumer, prefix(BetternetherInit.rubysFire, abilitySalvage))
                .save(betternetherConsumer, prefix(BetternetherInit.rubysFire, abilityFolder));

        ModifierRecipeBuilder.modifier(IFInit.flamed)
                .setTools(ingredientFromTags(TinkerTags.Items.RANGED, TinkerTags.Items.MELEE_WEAPON))
                .addInput(ItemNameIngredient.from(iceandfireId.apply("fire_dragon_heart")))
                .addInput(ItemNameIngredient.from(iceandfireId.apply("dragonsteel_fire_ingot")))
                .addInput(ItemNameIngredient.from(iceandfireId.apply("fire_dragon_blood")))
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.ABILITY, 1)
                .saveSalvage(iceandfireConsumer, prefix(IFInit.flamed, abilitySalvage))
                .save(iceandfireConsumer, prefix(IFInit.flamed, abilityFolder));

        ModifierRecipeBuilder.modifier(IFInit.iced)
                .setTools(ingredientFromTags(TinkerTags.Items.RANGED, TinkerTags.Items.MELEE_WEAPON))
                .addInput(ItemNameIngredient.from(iceandfireId.apply("ice_dragon_heart")))
                .addInput(ItemNameIngredient.from(iceandfireId.apply("dragonsteel_ice_ingot")))
                .addInput(ItemNameIngredient.from(iceandfireId.apply("ice_dragon_blood")))
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.ABILITY, 1)
                .saveSalvage(iceandfireConsumer, prefix(IFInit.iced, abilitySalvage))
                .save(iceandfireConsumer, prefix(IFInit.iced, abilityFolder));

        ModifierRecipeBuilder.modifier(IFInit.lightning)
                .setTools(ingredientFromTags(TinkerTags.Items.RANGED, TinkerTags.Items.MELEE_WEAPON))
                .addInput(ItemNameIngredient.from(iceandfireId.apply("lightning_dragon_heart")))
                .addInput(ItemNameIngredient.from(iceandfireId.apply("dragonsteel_lightning_ingot")))
                .addInput(ItemNameIngredient.from(iceandfireId.apply("lightning_dragon_blood")))
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.ABILITY, 1)
                .saveSalvage(iceandfireConsumer, prefix(IFInit.lightning, abilitySalvage))
                .save(iceandfireConsumer, prefix(IFInit.lightning, abilityFolder));

        ModifierRecipeBuilder.modifier(IFInit.scorchThorns)
                .setTools(TinkerTags.Items.CHESTPLATES)
                .addInput(TCTags.Items.FIRE_DRAGON_SCALES)
                .addInput(ItemNameIngredient.from(iceandfireId.apply("fire_dragon_blood")))
                .addInput(TCTags.Items.FIRE_DRAGON_SCALES)
                .addInput(Tags.Items.GEMS_EMERALD)
                .addInput(Tags.Items.GEMS_EMERALD)
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.DEFENSE, 1)
                .saveSalvage(iceandfireConsumer, prefix(IFInit.scorchThorns, abilitySalvage))
                .save(iceandfireConsumer, prefix(IFInit.scorchThorns, abilityFolder));

        ModifierRecipeBuilder.modifier(IFInit.frostThorns)
                .setTools(TinkerTags.Items.CHESTPLATES)
                .addInput(TCTags.Items.ICE_DRAGON_SCALES)
                .addInput(ItemNameIngredient.from(iceandfireId.apply("ice_dragon_blood")))
                .addInput(TCTags.Items.ICE_DRAGON_SCALES)
                .addInput(ItemTags.create(getResource("forge", "gems/sapphire")))
                .addInput(ItemTags.create(getResource("forge", "gems/sapphire")))
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.DEFENSE, 1)
                .saveSalvage(iceandfireConsumer, prefix(IFInit.frostThorns, abilitySalvage))
                .save(iceandfireConsumer, prefix(IFInit.frostThorns, abilityFolder));

        ModifierRecipeBuilder.modifier(IFInit.voltThorns)
                .setTools(TinkerTags.Items.CHESTPLATES)
                .addInput(TCTags.Items.LIGHTNING_DRAGON_SCALES)
                .addInput(ItemNameIngredient.from(iceandfireId.apply("lightning_dragon_blood")))
                .addInput(TCTags.Items.LIGHTNING_DRAGON_SCALES)
                .addInput(Tags.Items.GEMS_AMETHYST)
                .addInput(Tags.Items.GEMS_AMETHYST)
                .setMaxLevel(1).checkTraitLevel()
                .setSlots(SlotType.DEFENSE, 1)
                .saveSalvage(iceandfireConsumer, prefix(IFInit.voltThorns, abilitySalvage))
                .save(iceandfireConsumer, prefix(IFInit.voltThorns, abilityFolder));

        IncrementalModifierRecipeBuilder.modifier(TCModifiers.dreadbane)
                .setTools(ingredientFromTags(TinkerTags.Items.RANGED, TinkerTags.Items.MELEE_WEAPON))
                .setInput(ItemNameIngredient.from(iceandfireId.apply("dread_shard")), 1, 25)
                .setMaxLevel(5).checkTraitLevel()
                .setSlots(SlotType.UPGRADE, 1)
                .saveSalvage(iceandfireConsumer, prefix(TCModifiers.dreadbane, abilitySalvage))
                .save(iceandfireConsumer, prefix(TCModifiers.dreadbane, abilityFolder));

        ModifierRecipeBuilder.modifier(TCModifiers.dampening)
                .setTools(ingredientFromTags(TinkerTags.Items.RANGED, TinkerTags.Items.MELEE_WEAPON))
                .addInput(ItemTags.create(getResource("forge", "gems/sapphire")))
                .addInput(ItemNameIngredient.from(iceandfireId.apply("dragonbone")))
                .addInput(ItemTags.create(getResource("forge", "gems/sapphire")))
                .addInput(ItemTags.create(getResource("forge", "ingots/silver")))
                .addInput(ItemTags.create(getResource("forge", "ingots/silver")))
                .setMaxLevel(1)
                .setSlots(SlotType.ABILITY, 1)
                .allowCrystal()
                .save(iceandfireConsumer, wrap(TCModifiers.dampening, abilityFolder, "_level_1"));
        ModifierRecipeBuilder.modifier(TCModifiers.dampening)
                .setTools(ingredientFromTags(TinkerTags.Items.RANGED, TinkerTags.Items.MELEE_WEAPON))
                .addInput(ItemTags.create(getResource("forge", "gems/sapphire")))
                .addInput(ItemNameIngredient.from(iceandfireId.apply("manuscript")))
                .addInput(ItemTags.create(getResource("forge", "gems/sapphire")))
                .addInput(ItemTags.create(getResource("forge", "storage_blocks/silver")))
                .addInput(ItemTags.create(getResource("forge", "storage_blocks/silver")))
                .disallowCrystal() // would allow a cost cheese
                .exactLevel(2)
                .save(iceandfireConsumer, wrap(TCModifiers.dampening, abilityFolder, "_level_2"));
        ModifierRecipeBuilder.modifier(TCModifiers.dampening)
                .setTools(ingredientFromTags(TinkerTags.Items.RANGED, TinkerTags.Items.MELEE_WEAPON))
                .addInput(ItemTags.create(getResource("forge", "storage_blocks/sapphire")))
                .addInput(ItemNameIngredient.from(iceandfireId.apply("sea_serpent_fang")))
                .addInput(ItemTags.create(getResource("forge", "storage_blocks/sapphire")))
                .addInput(ItemTags.create(getResource("forge", "storage_blocks/silver")))
                .addInput(ItemTags.create(getResource("forge", "storage_blocks/silver")))
                .disallowCrystal() // would allow a cost cheese
                .exactLevel(3)
                .save(iceandfireConsumer, wrap(TCModifiers.dampening, abilityFolder, "_level_3"));

        AmbrofusionModifierRecipeBuilder.modifier(ItemNameIngredient.from(aetherId.apply("ambrosium_shard")), 4)
                .save(aetherConsumer, location(slotlessFolder + "ambrofusion/ambrosium_shard"));
        AmbrofusionModifierRecipeBuilder.modifier(ItemNameIngredient.from(aetherId.apply("ambrosium_block")), 36)
                .save(aetherConsumer, location(slotlessFolder + "ambrofusion/ambrosium_block"));

        String recycleFolder = "tables/recycling/";
        var scales = List.of("red","bronze","green","gray","blue","white","sapphire","silver","electric","amythest","copper","black");
        Pattern leather = new Pattern(TConstruct.MOD_ID, "maille");
        scales.forEach(colour -> {
            Function<Integer, ItemOutput> scale = (count) -> ItemNameOutput.fromName(iceandfireId.apply("dragonscales_%s".formatted(colour)), count);
            PartBuilderRecycleBuilder.tool(ItemNameIngredient.from(iceandfireId.apply("armor_%s_helmet".formatted(colour))))
                    .result(leather, scale.apply(5))
                    .save(iceandfireConsumer, location(recycleFolder + colour + "_dragon_scale_helmet"));
            PartBuilderRecycleBuilder.tool(ItemNameIngredient.from(iceandfireId.apply("armor_%s_chestplate".formatted(colour))))
                    .result(leather, scale.apply(8))
                    .save(iceandfireConsumer, location(recycleFolder + colour + "_dragon_scale_chestplate"));
            PartBuilderRecycleBuilder.tool(ItemNameIngredient.from(iceandfireId.apply("armor_%s_leggings".formatted(colour))))
                    .result(leather, scale.apply(7))
                    .save(iceandfireConsumer, location(recycleFolder + colour + "_dragon_scale_leggings"));
            PartBuilderRecycleBuilder.tool(ItemNameIngredient.from(iceandfireId.apply("armor_%s_boots".formatted(colour))))
                    .result(leather, scale.apply(4))
                    .save(iceandfireConsumer, location(recycleFolder + colour + "_dragon_scale_boots"));
        });

    }

    private void addTextureRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/modifiers/slotless/";
        Consumer<FinishedRecipe> aetherConsumer = withCondition(consumer, modLoaded("aether"));

        ModifierRecipeBuilder.modifier(TCModifiers.aetherForged)
                .setTools(TinkerTags.Items.HARVEST)
                .addInput(TCTags.Items.ZANITE_BLOCKS)
                .addInput(itemTag("aether:golden_oak_logs"))
                .addInput(TCTags.Items.ZANITE_BLOCKS)
                .addInput(Items.GLOWSTONE)
                .addInput(Items.GLOWSTONE)
                .setMaxLevel(1).checkTraitLevel()
                .save(aetherConsumer, prefix(TCModifiers.aetherForged, folder));

        TCMaterials.woodVariants.forEach((materialVariantId, woodType) -> woodTexture(woodType.makeConsumer(consumer), woodType, materialVariantId));
    }

    private void woodTexture(Consumer<FinishedRecipe> consumer, TCWoods woodType, MaterialVariantId material) {
        if (!woodType.hasPlanks()) return;
        String folder = "tools/modifiers/slotless/";
        woodTexture(woodType.makeConsumer(consumer), material, woodType.plankTag(), folder);
    }

    private void woodTexture(Consumer<FinishedRecipe> consumer, MaterialVariantId material, TagKey<Item> planks, String folder) {
        SwappableModifierRecipeBuilder.modifier(TinkerModifiers.embellishment, material.toString())
                .variantFormatter(SwappableModifierRecipe.VariantFormatter.MATERIAL)
                .setTools(TinkerTags.Items.EMBELLISHMENT_WOOD)
                .addInput(planks).addInput(TinkerTables.pattern).addInput(planks)
                .save(consumer, wrap(TinkerModifiers.embellishment, folder, "/wood/" + material.getLocation('_').getPath()));
    }

    public ResourceLocation wrap(LazyModifier modifier, String prefix, String suffix) {
        return wrap(modifier.getId(), prefix, suffix);
    }

    private static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.parse(name));
    }

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.parse(name));
    }

    @SafeVarargs
    private static Ingredient ingredientFromTags(TagKey<Item>... tags) {
        Ingredient[] tagIngredients = new Ingredient[tags.length];
        for (int i = 0; i < tags.length; i++) {
            tagIngredients[i] = Ingredient.of(tags[i]);
        }
        return CompoundIngredient.of(tagIngredients);
    }
}
