package io.github.pouffy.tcompat.common.material;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.TCFluids;
import io.github.pouffy.tcompat.common.melting.MeltingInfo;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluids;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.recipe.FluidValues;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.pouffy.tcompat.TCompat.getResource;

public class TCMeltingInfo {

    private static final int MOLTEN_LENGTH = "molten_".length();
    public static String withoutMolten(FluidObject<?> fluid) {
        return fluid.getId().getPath().substring(MOLTEN_LENGTH);
    }

    private static final MeltingInfo netheriteHelmet = MeltingInfo.create(getResource("minecraft:netherite_helmet"))
            .setResult(TinkerFluids.moltenNetherite, FluidValues.INGOT)
            .byproduct(TinkerFluids.moltenDiamond.result(FluidValues.GEM * 5));
    private static final MeltingInfo netheriteChestplate = MeltingInfo.create(getResource("minecraft:netherite_chestplate"))
            .setResult(TinkerFluids.moltenNetherite, FluidValues.INGOT)
            .byproduct(TinkerFluids.moltenDiamond.result(FluidValues.GEM * 8));
    private static final MeltingInfo netheriteLeggings = MeltingInfo.create(getResource("minecraft:netherite_leggings"))
            .setResult(TinkerFluids.moltenNetherite, FluidValues.INGOT)
            .byproduct(TinkerFluids.moltenDiamond.result(FluidValues.GEM * 7));
    private static final MeltingInfo netheriteBoots = MeltingInfo.create(getResource("minecraft:netherite_boots"))
            .setResult(TinkerFluids.moltenNetherite, FluidValues.INGOT)
            .byproduct(TinkerFluids.moltenDiamond.result(FluidValues.GEM * 4));

    private static final MeltingInfo netheriteSword = MeltingInfo.create(getResource("minecraft:netherite_sword"))
            .setResult(TinkerFluids.moltenNetherite, FluidValues.INGOT)
            .byproduct(TinkerFluids.moltenDiamond.result(FluidValues.GEM * 2));

    private static final MeltingInfo shield = MeltingInfo.create(getResource("minecraft:shield"))
            .setResult(TinkerFluids.moltenIron, 90);

    private static final MeltingInfo enderEye = MeltingInfo.create(getResource("minecraft:ender_eye"))
            .setResult(TinkerFluids.moltenEnder, 250);

    private static final int rod = 45;

    public static ResourceLocation tcMolten(String material) {
        return TCompat.getResource("tconstruct:molten_" + material);
    }

    public static class Cataclysm {
        public static final Function<String, ResourceLocation> cataclysm = name -> getResource("cataclysm", name);
        public static final InfoGroup cataclysmGroup = new InfoGroup();

        public static final MeltingInfo gauntletOfGuard = cataclysmGroup.create("misc", MeltingInfo.create(cataclysm.apply("gauntlet_of_guard"))
                .byproduct(TinkerFluids.enderSlime.result(500))
                .setResult(TinkerFluids.moltenObsidian, 4000));
        public static final MeltingInfo gauntletOfBulwark = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("gauntlet_of_bulwark"))
                .component(gauntletOfGuard)
                .byproduct(tcMolten("iron"), 90)
                .setResult(TCFluids.moltenIgnitium, 180));
        public static final MeltingInfo gauntletOfMaelstrom = cataclysmGroup.create("misc", MeltingInfo.create(cataclysm.apply("gauntlet_of_maelstrom"))
                .component(gauntletOfGuard)
                .setResult(TinkerFluids.moltenGlass, 1000));
        public static final MeltingInfo infernalForge = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("infernal_forge"))
                .byproduct(tcMolten("gold"), 90)
                .setResult(TinkerFluids.moltenNetherite, 270));
        public static final MeltingInfo brontes = cataclysmGroup.create("gem", MeltingInfo.create(cataclysm.apply("brontes"))
                .component(infernalForge)
                .setResult(TCFluids.moltenLacrima, 300));
        public static final MeltingInfo voidForge = cataclysmGroup.create("misc", MeltingInfo.create(cataclysm.apply("void_forge"))
                .component(infernalForge)
                .setResult(TinkerFluids.enderSlime, 500));
        public static final MeltingInfo witherAssaultShoulderWeapon = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("wither_assault_shoulder_weapon"))
                .setResult(TCFluids.moltenWitherite, 180));
        public static final MeltingInfo voidAssaultShoulderWeapon = cataclysmGroup.create("misc", MeltingInfo.create(cataclysm.apply("void_assault_shoulder_weapon"))
                .component(witherAssaultShoulderWeapon)
                .setResult(TinkerFluids.enderSlime, 500));
        public static final MeltingInfo theAnnihilator = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("the_annihilator"))
                .byproduct(TCFluids.moltenBlackSteel.result(90))
                .setResult(TCFluids.moltenCursium, 180));
        public static final MeltingInfo theImmolator = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("the_immolator"))
                .component(theAnnihilator)
                .setResult(TCFluids.moltenIgnitium, 90));
        public static final MeltingInfo soulRender = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("soul_render"))
                .byproduct(TCFluids.moltenBlackSteel.result(180))
                .setResult(TCFluids.moltenCursium, 270));
        public static final MeltingInfo cursedBow = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("cursed_bow"))
                .byproduct(TCFluids.moltenBlackSteel.result(90))
                .setResult(TCFluids.moltenCursium, 180));
        public static final MeltingInfo wrathOfTheDesert = cataclysmGroup.create("misc", MeltingInfo.create(cataclysm.apply("wrath_of_the_desert"))
                .component(cursedBow)
                .setResult(TinkerFluids.moltenGlass, 1000));
        public static final MeltingInfo meatShredder = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("meat_shredder"))
                .byproduct(tcMolten("iron"), 810)
                .setResult(TCFluids.moltenWitherite, 180));
        public static final MeltingInfo laserGatling = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("laser_gatling"))
                .byproduct(tcMolten("iron"), 270)
                .setResult(TCFluids.moltenWitherite, 180));
        public static final MeltingInfo ancientSpear = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("ancient_spear"))
                .setResult(TCFluids.moltenAncientMetal, 360));

        public static final MeltingInfo theIncinerator = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("the_incinerator"))
                .component(netheriteSword)
                .setResult(TCFluids.moltenIgnitium, 180));
        public static final MeltingInfo bulwarkOfTheFlame = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("bulwark_of_the_flame"))
                .component(shield)
                .setResult(TCFluids.moltenIgnitium, 180));
        public static final MeltingInfo ignitiumHelmet = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("ignitium_helmet"))
                .component(netheriteHelmet)
                .setResult(TCFluids.moltenIgnitium, 90));
        public static final MeltingInfo ignitiumLeggings = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("ignitium_leggings"))
                .component(netheriteLeggings)
                .setResult(TCFluids.moltenIgnitium, 90));
        public static final MeltingInfo ignitiumBoots = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("ignitium_boots"))
                .component(netheriteBoots)
                .setResult(TCFluids.moltenIgnitium, 90));
        public static final MeltingInfo cursiumHelmet = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("cursium_helmet"))
                .component(netheriteHelmet)
                .setResult(TCFluids.moltenCursium, 90));
        public static final MeltingInfo cursiumChestplate = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("cursium_chestplate"))
                .component(netheriteChestplate)
                .setResult(TCFluids.moltenCursium, 90));
        public static final MeltingInfo cursiumLeggings = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("cursium_leggings"))
                .component(netheriteLeggings)
                .setResult(TCFluids.moltenCursium, 90));
        public static final MeltingInfo cursiumBoots = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("cursium_boots"))
                .component(netheriteBoots)
                .setResult(TCFluids.moltenCursium, 90));

        public static final MeltingInfo mechEye = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("mech_eye"))
                .component(enderEye)
                .setResult(TinkerFluids.moltenIron, 360));
        public static final MeltingInfo flameEye = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("flame_eye"))
                .component(enderEye)
                .byproduct(TinkerFluids.liquidSoul.result(3000))
                .setResult(TinkerFluids.moltenDebris, 180));
        public static final MeltingInfo voidEye = cataclysmGroup.create("misc", MeltingInfo.create(cataclysm.apply("void_eye"))
                .component(enderEye)
                .setResult(TinkerFluids.enderSlime, 250));
        public static final MeltingInfo monstrousEye = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("monstrous_eye"))
                .component(enderEye)
                .byproduct(FluidOutput.fromFluid(Fluids.LAVA, 4000))
                .setResult(TinkerFluids.moltenDebris, 180));
        public static final MeltingInfo abyssEye = cataclysmGroup.create("misc", MeltingInfo.create(cataclysm.apply("abyss_eye"))
                .component(enderEye)
                .setResult(TinkerFluids.moltenObsidian, 4000));
        public static final MeltingInfo desertEye = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("desert_eye"))
                .component(enderEye)
                .byproduct(TinkerFluids.moltenEmerald.result(100))
                .setResult(TinkerFluids.moltenGold, 90));
        public static final MeltingInfo cursedEye = cataclysmGroup.create(MeltingInfo.create(cataclysm.apply("cursed_eye"))
                .component(enderEye)
                .setResult(TinkerFluids.moltenGold, 360));
        public static final MeltingInfo stormEye = cataclysmGroup.create("gem", MeltingInfo.create(cataclysm.apply("storm_eye"))
                .component(enderEye)
                .byproduct(tcMolten("copper"), 270)
                .setResult(TinkerFluids.moltenDiamond, 200));

        public static final MeltingInfo abyssalSacrifice = cataclysmGroup.create("gem", MeltingInfo.create(cataclysm.apply("abyssal_sacrifice"))
                .byproduct(tcMolten("emerald"), 900)
                .byproduct(tcMolten("gold"), 900)
                .byproduct(tcMolten("iron"), 810)
                .byproduct(tcMolten("amethyst"), 400)
                .setResult(TinkerFluids.moltenDiamond, 900));
    }

    public static class AdAstra {
        public static final Function<String, ResourceLocation> adAstraId = name -> getResource("ad_astra", name);
        public static final InfoGroup adAstraGroup = new InfoGroup();
        //Parts
        public static final MeltingInfo rocketFin = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("rocket_fin"))
                .setResult(TinkerFluids.moltenSteel, 135));
        public static final MeltingInfo engineFrame = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("engine_frame"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT + (rod * 8)));
        public static final MeltingInfo fan = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("fan"))
                .setResult(TinkerFluids.moltenSteel, (FluidValues.INGOT * 4) + rod));
        public static final MeltingInfo noseCone = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("rocket_nose_cone"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 4)
                .byproduct(tcMolten("copper"), FluidValues.INGOT * 3));
        public static final MeltingInfo oxygenGear = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("oxygen_gear"))
                .setResult(TinkerFluids.moltenSteel, (FluidValues.INGOT * 4) + (rod * 3)));
        public static final MeltingInfo wheel = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("wheel"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT));
        public static final MeltingInfo gasTank = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("gas_tank"))
                .setResult(TinkerFluids.moltenIron, (FluidValues.INGOT * 4) + rod));
        public static final MeltingInfo largeGasTank = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("large_gas_tank"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 4)
                .component(gasTank.copyWithCount(2)));
        public static final MeltingInfo etrionicCapacitor = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("etrionic_capacitor"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 4)
                .byproduct(tcMolten("diamond"), (FluidValues.GEM * 3)));
        public static final MeltingInfo steelEngine = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("steel_engine"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 5)
                .components(engineFrame, fan));
        public static final MeltingInfo deshEngine = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("desh_engine"))
                .setResult(TCFluids.moltenDesh, FluidValues.INGOT * 5)
                .component(steelEngine));
        public static final MeltingInfo ostrumEngine = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("ostrum_engine"))
                .setResult(TCFluids.moltenOstrum, FluidValues.INGOT * 5)
                .component(deshEngine));
        public static final MeltingInfo caloriteEngine = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("calorite_engine"))
                .setResult(TCFluids.moltenCalorite, FluidValues.INGOT * 5)
                .component(ostrumEngine));
        public static final MeltingInfo steelTank = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("steel_tank"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 5)
                .component(gasTank));
        public static final MeltingInfo deshTank = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("desh_tank"))
                .setResult(TCFluids.moltenDesh, FluidValues.INGOT * 5)
                .byproduct(tcMolten("steel"), (rod))
                .component(steelTank));
        public static final MeltingInfo ostrumTank = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("ostrum_tank"))
                .setResult(TCFluids.moltenOstrum, FluidValues.INGOT * 5)
                .byproduct(tcMolten("steel"), (rod))
                .component(deshTank));
        public static final MeltingInfo caloriteTank = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("calorite_tank"))
                .setResult(TCFluids.moltenCalorite, FluidValues.INGOT * 5)
                .byproduct(tcMolten("steel"), (rod))
                .component(ostrumTank));
        public static final MeltingInfo photovoltaicEtriumCell = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("photovoltaic_etrium_cell"))
                .setResult(TCFluids.moltenDesh, FluidValues.INGOT * 2)
                .byproduct(tcMolten("diamond"), (FluidValues.GEM))
                .byproduct(tcMolten("glass"), (FluidValues.GLASS_BLOCK * 3)));

        //Equipment
        public static final MeltingInfo zipGun = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("zip_gun"))
                .setResult(TinkerFluids.moltenSteel, (FluidValues.INGOT * 2) + rod)
                .component(gasTank));
        public static final MeltingInfo ti69 = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("ti_69"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 5)
                .byproduct(tcMolten("glass"), (FluidValues.GLASS_PANE * 3)));
        public static final MeltingInfo wrench = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("wrench"))
                .setResult(TinkerFluids.moltenIron, (FluidValues.INGOT * 2) + (rod * 2)));
        public static final MeltingInfo spaceHelmet = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("space_helmet"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 5).setDamagable(FluidValues.NUGGET, FluidValues.GLASS_PANE)
                .byproduct(tcMolten("glass"), (FluidValues.GLASS_PANE)));
        public static final MeltingInfo spaceSuit = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("space_suit"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 4).setDamagable(FluidValues.NUGGET, FluidValues.NUGGET)
                .components(oxygenGear, gasTank.copyWithCount(2)));
        public static final MeltingInfo spacePants = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("space_pants"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 5)).setDamagable(FluidValues.NUGGET, FluidValues.NUGGET);
        public static final MeltingInfo spaceBoots = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("space_boots"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 2)).setDamagable(FluidValues.NUGGET, FluidValues.NUGGET);
        public static final MeltingInfo netheriteSpaceHelmet = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("netherite_space_helmet"))
                .setResult(TCFluids.moltenOstrum, FluidValues.INGOT * 4).setDamagable(FluidValues.NUGGET, FluidValues.GLASS_PANE, FluidValues.NUGGET, FluidValues.GEM_SHARD)
                .byproduct(tcMolten("glass"), (FluidValues.GLASS_BLOCK))
                .component(netheriteHelmet));
        public static final MeltingInfo netheriteSpaceSuit = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("netherite_space_suit"))
                .setResult(TCFluids.moltenOstrum, FluidValues.INGOT * 4).setDamagable(FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.GEM_SHARD)
                .components(netheriteChestplate, oxygenGear, largeGasTank.copyWithCount(2)));
        public static final MeltingInfo netheriteSpacePants = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("netherite_space_pants"))
                .setResult(TCFluids.moltenOstrum, FluidValues.INGOT * 4).setDamagable(FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.GEM_SHARD, FluidValues.NUGGET)
                .byproduct(TCFluids.moltenDesh.result(FluidValues.INGOT * 2))
                .component(netheriteLeggings));
        public static final MeltingInfo netheriteSpaceBoots = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("netherite_space_boots"))
                .setResult(TCFluids.moltenOstrum, FluidValues.INGOT * 2).setDamagable(FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.GEM_SHARD, FluidValues.NUGGET)
                .byproduct(TCFluids.moltenDesh.result(FluidValues.INGOT * 2))
                .component(netheriteBoots));
        public static final MeltingInfo jetSuitHelmet = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("jet_suit_helmet"))
                .setResult(TCFluids.moltenCalorite, FluidValues.INGOT * 4).setDamagable(FluidValues.NUGGET, FluidValues.GEM_SHARD, FluidValues.GLASS_PANE, FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.GEM_SHARD)
                .byproduct(tcMolten("amethyst"), (FluidValues.GEM * 2))
                .byproduct(tcMolten("glass"), (FluidValues.GLASS_PANE * 2))
                .component(netheriteSpaceHelmet));
        public static final MeltingInfo jetSuit = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("jet_suit"))
                .setResult(TCFluids.moltenCalorite, FluidValues.INGOT * 2).setDamagable(FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.GEM_SHARD, FluidValues.NUGGET)
                .components(caloriteTank.copyWithCount(2), netheriteSpaceSuit, caloriteEngine, etrionicCapacitor.copyWithCount(2)));
        public static final MeltingInfo jetSuitPants = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("jet_suit_pants"))
                .setResult(TCFluids.moltenCalorite, (FluidValues.INGOT * 4) + (FluidValues.METAL_BLOCK * 2)).setDamagable(FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.GEM_SHARD, FluidValues.NUGGET)
                .component(netheriteSpacePants));
        public static final MeltingInfo jetSuitBoots = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("jet_suit_boots"))
                .setResult(TCFluids.moltenCalorite, (FluidValues.INGOT * 2) + (FluidValues.METAL_BLOCK * 2)).setDamagable(FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.NUGGET, FluidValues.GEM_SHARD, FluidValues.NUGGET)
                .component(netheriteSpaceBoots));

        //Machines
        public static final MeltingInfo launchPad = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("launch_pad"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 7));
        public static final MeltingInfo radio = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("radio"))
                .setResult(TinkerFluids.moltenSteel, (FluidValues.INGOT * 6) + rod));
        public static final MeltingInfo coalGenerator = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("coal_generator"))
                .setResult(TinkerFluids.moltenIron, FluidValues.INGOT * 6));
        public static final MeltingInfo compressor = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("compressor"))
                .setResult(TinkerFluids.moltenIron, FluidValues.INGOT * 8));
        public static final MeltingInfo etrionicBlastFurnace = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("etrionic_blast_furnace"))
                .setResult(TinkerFluids.moltenIron, FluidValues.INGOT * 11));
        public static final MeltingInfo nasaWorkbench = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("nasa_workbench"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 12)
                .byproduct(tcMolten("iron"), (rod * 2)));
        public static final MeltingInfo fuelRefinery = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("fuel_refinery"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 6)
                .byproduct(tcMolten("iron"), (FluidValues.INGOT * 6)));
        public static final MeltingInfo oxygenLoader = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("oxygen_loader"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 4)
                .byproduct(tcMolten("copper"), (FluidValues.INGOT * 3))
                .components(gasTank.copyWithCount(2), fan));
        public static final MeltingInfo solarPanel = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("solar_panel"))
                .setResult(TCFluids.moltenDesh, FluidValues.INGOT * 4)
                .byproduct(tcMolten("steel"), (FluidValues.INGOT * 2))
                .components(photovoltaicEtriumCell.copyWithCount(3)));
        public static final MeltingInfo waterPump = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("water_pump"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.INGOT * 4)
                .byproduct(TCFluids.moltenDesh.result(FluidValues.INGOT * 3))
                .byproduct(tcMolten("iron"), (FluidValues.INGOT * 5))
                .components(gasTank));
        public static final MeltingInfo oxygenDistributor = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("oxygen_distributor"))
                .setResult(TinkerFluids.moltenSteel, (FluidValues.INGOT * 16) + (rod * 4))
                .byproduct(TCFluids.moltenDesh.result(FluidValues.INGOT * 2))
                .components(largeGasTank, oxygenLoader, oxygenGear));
        public static final MeltingInfo gravityNormalizer = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("gravity_normalizer"))
                .setResult(TinkerFluids.moltenDiamond, FluidValues.LARGE_GEM_BLOCK)
                .byproduct(TCFluids.moltenDesh.result(FluidValues.INGOT * 3))
                .components(etrionicCapacitor.copyWithCount(2)));
        public static final MeltingInfo energizer = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("energizer"))
                .setResult(TCFluids.moltenOstrum,  FluidValues.INGOT * 22)
                .byproduct(tcMolten("diamond"), (FluidValues.LARGE_GEM_BLOCK))
                .component(etrionicCapacitor.copyWithCount(2)));
        public static final MeltingInfo cryoFreezer = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("cryo_freezer"))
                .setResult(TCFluids.moltenOstrum, FluidValues.INGOT * 6)
                .byproduct(FluidOutput.fromFluid(Fluids.WATER, FluidValues.GLASS_BLOCK * 162))
                .component(largeGasTank));
        public static final MeltingInfo oxygenSensor = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("oxygen_sensor"))
                .setResult(TCFluids.moltenOstrum, FluidValues.INGOT * 7)
                .component(fan));

        //Vehicles
        public static final MeltingInfo rover = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("tier_1_rover"))
                .setResult(TCFluids.moltenDesh, FluidValues.INGOT * 10)
                .byproduct(tcMolten("steel"), (rod))
                .components(wheel.copyWithCount(2), deshEngine, radio, largeGasTank));
        public static final MeltingInfo rocket1 = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("tier_1_rocket"))
                .setResult(TinkerFluids.moltenSteel, FluidValues.METAL_BLOCK * 6)
                .components(noseCone, rocketFin.copyWithCount(4), steelTank.copyWithCount(2), steelEngine));
        public static final MeltingInfo rocket2 = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("tier_2_rocket"))
                .setResult(TCFluids.moltenDesh, FluidValues.METAL_BLOCK * 6)
                .components(noseCone, rocketFin.copyWithCount(4), deshTank.copyWithCount(2), deshEngine));
        public static final MeltingInfo rocket3 = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("tier_3_rocket"))
                .setResult(TCFluids.moltenOstrum, FluidValues.METAL_BLOCK * 6)
                .components(noseCone, rocketFin.copyWithCount(4), ostrumTank.copyWithCount(2), ostrumEngine));
        public static final MeltingInfo rocket4 = adAstraGroup.create(MeltingInfo.create(adAstraId.apply("tier_4_rocket"))
                .setResult(TCFluids.moltenCalorite, FluidValues.METAL_BLOCK * 6)
                .components(noseCone, rocketFin.copyWithCount(4), caloriteTank.copyWithCount(2), caloriteEngine));
    }

    public static class InfoGroup {
        private final List<Entry> meltingInfos = new ArrayList<>();

        public MeltingInfo create(MeltingInfo meltingInfo) {
            return create("metal", meltingInfo);
        }

        public MeltingInfo create(String type, MeltingInfo meltingInfo) {
            this.meltingInfos.add(new Entry(type, meltingInfo));
            return meltingInfo;
        }

        public void saveAll(Consumer<FinishedRecipe> consumer) {
            for (Entry entry : meltingInfos) {
                entry.meltingInfo.save(consumer, entry.type);
            }
        }

        record Entry(String type, MeltingInfo meltingInfo) { }
    }
}
