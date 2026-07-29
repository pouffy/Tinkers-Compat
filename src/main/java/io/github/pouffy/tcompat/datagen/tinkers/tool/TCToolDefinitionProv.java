package io.github.pouffy.tcompat.datagen.tinkers.tool;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.compat.GlobalDefinitions;
import io.github.pouffy.tcompat.compat.GlobalInit;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ToolActions;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.materials.RandomMaterial;
import slimeknights.tconstruct.library.tools.definition.module.ToolModule;
import slimeknights.tconstruct.library.tools.definition.module.build.MultiplyStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.SetStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.ToolActionsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.ToolTraitsModule;
import slimeknights.tconstruct.library.tools.definition.module.display.FixedMaterialToolName;
import slimeknights.tconstruct.library.tools.definition.module.display.MaterialToolNameModule;
import slimeknights.tconstruct.library.tools.definition.module.material.DefaultMaterialsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.PartStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.mining.IsEffectiveModule;
import slimeknights.tconstruct.library.tools.definition.module.mining.MiningSpeedModifierModule;
import slimeknights.tconstruct.library.tools.definition.module.weapon.SweepWeaponAttack;
import slimeknights.tconstruct.library.tools.nbt.MultiplierNBT;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerToolParts;
import slimeknights.tconstruct.tools.data.ModifierIds;

import static slimeknights.tconstruct.tools.TinkerToolParts.*;

public class TCToolDefinitionProv extends AbstractToolDefinitionDataProvider {
    public TCToolDefinitionProv(PackOutput packOutput) {
        super(packOutput, TCompat.MOD_ID);
    }


    @Override
    protected void addToolDefinitions() {
        RandomMaterial tier1Material = RandomMaterial.random().tier(1).build();
        RandomMaterial nonHiddenMaterial = RandomMaterial.random().build();
        DefaultMaterialsModule defaultFourParts = DefaultMaterialsModule.builder().material(tier1Material, tier1Material, tier1Material, tier1Material).build();
        DefaultMaterialsModule defaultTwoParts = DefaultMaterialsModule.builder().material(tier1Material, tier1Material).build();
        ToolModule[] swordHarvest = {
                IsEffectiveModule.tag(TinkerTags.Blocks.MINABLE_WITH_SWORD),
                MiningSpeedModifierModule.blocks(7.5f, Blocks.COBWEB)
        };
        define(GlobalDefinitions.glaive)
                // parts
                .module(PartStatsModule.parts()
                        .part(smallBlade)
                        .part(toughHandle)
                        .part(toughHandle)
                        .part(toolBinding)
                        .build())
                .module(defaultFourParts)
                // stats
                .module(new SetStatsModule(StatsNBT.builder()
                        .set(ToolStats.ATTACK_DAMAGE, 3.0f)
                        .set(ToolStats.ATTACK_SPEED, 1.1f).build()))
                .module(new MultiplyStatsModule(MultiplierNBT.builder()
                        .set(ToolStats.MINING_SPEED, 0.25f)
                        .set(ToolStats.DURABILITY, 1.2f).build()))
                .largeToolStartingSlots()
                // traits
                .module(ToolTraitsModule.builder()
                        .trait(ModifierIds.reach, 2)
                        .trait(TinkerModifiers.aoeSilkyShears).build())
                // behavior
                .module(ToolActionsModule.of(ToolActions.SWORD_DIG))
                .module(swordHarvest)
                .module(new SweepWeaponAttack(4));
        define(GlobalDefinitions.dartShooter)
                .module(PartStatsModule.parts()
                        .part(GlobalInit.lipGuard)
                        .part(GlobalInit.dartBarrel)
                        .build())
                .module(defaultTwoParts)
                .module(new SetStatsModule(StatsNBT.builder()
                        .set(ToolStats.DURABILITY, 120.0F)
                        .set(ToolStats.ATTACK_DAMAGE, 0.0F)
                        .set(ToolStats.ATTACK_SPEED, 1.0F)
                        .build()))
                .module(new MultiplyStatsModule(MultiplierNBT.builder()
                        .set(ToolStats.DURABILITY, 1.5F)
                        .build()))
                .module(MaterialToolNameModule.ALL)
                .smallToolStartingSlots();

        define(GlobalDefinitions.dart)
                .module(PartStatsModule.parts()
                        .part(TinkerToolParts.arrowHead)
                        .part(TinkerToolParts.arrowShaft)
                        .part(TinkerToolParts.fletching)
                        .build())
                .module(DefaultMaterialsModule.builder()
                        .material(new RandomMaterial[]{nonHiddenMaterial, nonHiddenMaterial, nonHiddenMaterial})
                        .build())
                .module(FixedMaterialToolName.FIRST);

    }

    @Override
    public String getName() {
        return "Tinker's Compatability Tool Definitions";
    }
}
