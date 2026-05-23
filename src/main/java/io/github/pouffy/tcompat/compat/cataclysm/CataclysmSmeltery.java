package io.github.pouffy.tcompat.compat.cataclysm;

import io.github.pouffy.tcompat.common.fluid.TCFluids;
import io.github.pouffy.tcompat.common.material.TCMeltingInfo;
import io.github.pouffy.tcompat.common.util.CompatSmeltery;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.material.Fluids;
import slimeknights.mantle.recipe.data.ItemNameIngredient;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;

import java.util.function.Consumer;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class CataclysmSmeltery implements CompatSmeltery {
    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer, String folder) {
        var cConsumer = compatConsumer(consumer);
        metal(cConsumer, TCFluids.moltenWitherite, compatModId()).metal(9, true).optional();
        metal(cConsumer, TCFluids.moltenAncientMetal, compatModId()).metal(9, true).optional();
        metal(cConsumer, TCFluids.moltenBlackSteel, compatModId()).metal(9, true).optional();
        metal(cConsumer, TCFluids.moltenCursium, compatModId()).metal(9, true).optional();
        metal(cConsumer, TCFluids.moltenIgnitium, compatModId()).metal(9, true).optional();
        gem(cConsumer, TCFluids.moltenLacrima, compatModId()).gem(9, true).optional();

        simpleMelting(cConsumer, TinkerFluids.moltenIron, FluidValues.INGOT, "iron", ItemNameIngredient.from(compatId("belt_of_beginner")), metalFolder("melting"), "belt_of_beginner");
        simpleMelting(cConsumer, TinkerFluids.earthSlime, FluidValues.SLIMEBALL, "earth_slime", ItemNameIngredient.from(compatId("sticky_gloves")), miscFolder("melting"), "sticky_gloves");
        simpleMelting(cConsumer, TinkerFluids.moltenAmethyst, FluidValues.GEM_SHARD, "amethyst", ItemNameIngredient.from(compatId("amethyst_crab_shell")), gemFolder("melting"), "amethyst_crab_shell");
        simpleMelting(cConsumer, TinkerFluids.moltenAmethyst, FluidValues.GEM_SHARD * 2, "amethyst", listedInput("amethyst_crab_meat", "blessed_amethyst_crab_meat"), gemFolder("melting"), "amethyst_crab_meat");
        simpleMelting(cConsumer, TinkerFluids.moltenAmethyst, FluidValues.GEM_SHARD * 8, "amethyst", ItemNameIngredient.from(compatId("bloom_stone_pauldrons")), gemFolder("melting"), "bloom_stone_pauldrons", new int[]{5});
        simpleMelting(cConsumer, TinkerFluids.moltenNetherite, FluidValues.INGOT, "netherite", ItemNameIngredient.from(compatId("monstrous_horn")), metalFolder("melting"), "monstrous_horn");
        simpleMelting(cConsumer, TCFluids.moltenIgnitium, FluidValues.INGOT, "ignitium", ItemNameIngredient.from(compatId("blazing_grips")), metalFolder("melting"), "blazing_grips");

        salvageTools(cConsumer, this::compatId, TCFluids.moltenBlackSteel, FluidValues.INGOT, "black_steel", new int[]{FluidValues.NUGGET, FluidValues.NUGGET}, metalFolder("melting"));

        simpleMelting(cConsumer, TCFluids.moltenLacrima, FluidValues.GEM * 3, "lacrima", listedInput("astrape", "ceraunus"), metalFolder("melting"), "weapon");
        simpleMelting(TCFluids.moltenIgnitium, FluidValues.INGOT, listedInput("ignitium_chestplate", "ignitium_elytra_chestplate"))
                .addByproduct(TinkerFluids.moltenDiamond.result(FluidValues.GEM * 8))
                .addByproduct(TinkerFluids.moltenNetherite.result(FluidValues.INGOT))
                .save(cConsumer, location(gemFolder("melting") + "/ignitium/ignitium_chestplate"));

        simpleMelting(cConsumer, TCFluids.moltenAncientMetal, FluidValues.INGOT * 2, "ancient_metal", ItemNameIngredient.from(compatId("bone_reptile_helmet")), metalFolder("melting"), "bone_reptile_helmet", new int[]{FluidValues.NUGGET});
        simpleMelting(cConsumer, TCFluids.moltenAncientMetal, FluidValues.INGOT * 4, "ancient_metal", ItemNameIngredient.from(compatId("bone_reptile_chestplate")), metalFolder("melting"), "bone_reptile_chestplate", new int[]{FluidValues.NUGGET});
        simpleMelting(cConsumer, TCFluids.moltenBlackSteel, (FluidValues.NUGGET * 4) + FluidValues.INGOT, "black_steel", ItemNameIngredient.from(compatId("black_steel_targe")), metalFolder("melting"), "black_steel_targe", new int[]{FluidValues.NUGGET});
        simpleMelting(cConsumer, TCFluids.moltenAncientMetal, FluidValues.INGOT * 2, "ancient_metal", ItemNameIngredient.from(compatId("khopesh")), metalFolder("melting"), "khopesh", new int[]{FluidValues.NUGGET});
        simpleMelting(cConsumer, TinkerFluids.moltenGold, FluidValues.INGOT, "gold", listedInput("coral_spear", "coral_bardiche", "athame"), metalFolder("melting"), "coral_weapons", new int[]{FluidValues.NUGGET});

        simpleMelting(cConsumer, TCFluids.moltenAncientMetal, FluidValues.INGOT * 3, "ancient_metal", ItemNameIngredient.from(compatId("necklace_of_the_desert")), metalFolder("melting"), "necklace_of_the_desert");
        simpleMelting(cConsumer, TCFluids.moltenAncientMetal, FluidValues.INGOT * 4, "ancient_metal", ItemNameIngredient.from(compatId("vitality_ankh")), metalFolder("melting"), "vitality_ankh");
        simpleMelting(cConsumer, TCFluids.moltenAncientMetal, FluidValues.INGOT * 5, "ancient_metal", ItemNameIngredient.from(compatId("unbreakable_skull")), metalFolder("melting"), "unbreakable_skull");
        simpleMelting(cConsumer, TCFluids.moltenCursium, FluidValues.INGOT, "cursium", ItemNameIngredient.from(compatId("strange_key")), metalFolder("melting"), "strange_key");
        simpleMelting(cConsumer, TinkerFluids.moltenDiamond, FluidValues.GEM * 5, "diamond", ItemNameIngredient.from(compatId("ignitium_upgrade_smithing_template")), gemFolder("melting"), "ignitium_template");

        simpleMelting(TinkerFluids.moltenNetherite, FluidValues.INGOT * 2, ItemNameIngredient.from(compatId("monstrous_helm")))
                .addByproduct(TinkerFluids.moltenDiamond.result(FluidValues.GEM * 5)).setDamagable(new int[]{FluidValues.NUGGET, FluidValues.GEM_SHARD})
                .save(cConsumer, location(metalFolder("melting") + "/netherite/monstrous_helm"));
        simpleMelting(TCFluids.moltenLacrima, FluidValues.GEM * 4, ItemNameIngredient.from(compatId("azure_sea_shield")))
                .addByproduct(TinkerFluids.moltenGold.result((FluidValues.NUGGET * 4) + FluidValues.INGOT)).setDamagable(new int[]{FluidValues.GEM_SHARD, FluidValues.NUGGET})
                .save(cConsumer, location(gemFolder("melting") + "/lacrima/azure_sea_shield"));
        simpleMelting(TinkerFluids.moltenNetherite, FluidValues.INGOT, ItemNameIngredient.from(compatId("lava_power_cell")))
                .addByproduct(FluidOutput.fromFluid(Fluids.LAVA, 500))
                .save(cConsumer, location(metalFolder("melting") + "/netherite/lava_power_cell"));
        simpleMelting(TCFluids.moltenCursium, FluidValues.INGOT, listedInput("ring_of_grudged", "berserker_soul_amulet"))
                .addByproduct(TCFluids.moltenBlackSteel.result(FluidValues.INGOT))
                .save(cConsumer, location(metalFolder("melting") + "/cursium/accessory"));
        simpleMelting(TinkerFluids.moltenNetherite, FluidValues.INGOT, ItemNameIngredient.from(compatId("netherite_effigy")))
                .addByproduct(TinkerFluids.moltenGold.result(FluidValues.INGOT * 2))
                .save(cConsumer, location(metalFolder("melting") + "/netherite/effigy"));
        simpleMelting(TinkerFluids.moltenGold, FluidValues.INGOT * 6, ItemNameIngredient.from(compatId("cursium_upgrade_smithing_template")))
                .addByproduct(TinkerFluids.moltenDiamond.result(FluidValues.GEM * 5))
                .addByproduct(TCFluids.moltenBlackSteel.result(FluidValues.INGOT * 2))
                .save(cConsumer, location(metalFolder("melting") + "/gold/cursium_template"));

        simpleMelting(cConsumer, TCFluids.moltenBlackSteel, FluidValues.METAL_BLOCK, "black_steel", ItemNameIngredient.from(compatId("black_steel_wall")), metalFolder("melting"), "wall");
        simpleMelting(cConsumer, TCFluids.moltenBlackSteel, 570, "black_steel", ItemNameIngredient.from(compatId("black_steel_fence")), metalFolder("melting"), "fence");
        simpleMelting(cConsumer, TinkerFluids.moltenQuartz, FluidValues.SMALL_GEM_BLOCK, "quartz", ItemNameIngredient.from(compatId("quartz_brick_wall")), gemFolder("melting"), "brick_wall");
        simpleMelting(cConsumer, TinkerFluids.moltenObsidian, FluidValues.GLASS_BLOCK, "obsidian", listedInput(
                "obsidian_bricks",
                "obsidian_brick_wall",
                "polished_obsidian",
                "chiseled_obsidian_bricks",
                "obsidian_pillar",
                "polished_obsidian_wall"
        ), miscFolder("melting"), "decorational");
        simpleMelting(cConsumer, TinkerFluids.moltenObsidian, 1500, "obsidian", listedInput(
                "obsidian_brick_stairs",
                "polished_obsidian_stairs"
        ), miscFolder("melting"), "stairs");
        simpleMelting(cConsumer, TinkerFluids.moltenObsidian, 500, "obsidian", listedInput(
                "obsidian_brick_slab",
                "polished_obsidian_slab"
        ), miscFolder("melting"), "slab");

        TCMeltingInfo.Cataclysm.cataclysmGroup.saveAll(cConsumer);
    }

    @Override
    public String compatModId() {
        return "cataclysm";
    }
}
