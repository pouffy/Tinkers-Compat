package io.github.pouffy.tcompat.datagen.tinkers.tool;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFToolDefinitions;
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
import slimeknights.tconstruct.library.tools.definition.module.material.DefaultMaterialsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.PartStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.mining.IsEffectiveModule;
import slimeknights.tconstruct.library.tools.definition.module.mining.MiningSpeedModifierModule;
import slimeknights.tconstruct.library.tools.definition.module.weapon.SweepWeaponAttack;
import slimeknights.tconstruct.library.tools.nbt.MultiplierNBT;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;

import static slimeknights.tconstruct.tools.TinkerToolParts.*;

public class TCToolDefinitionProv extends AbstractToolDefinitionDataProvider {
    public TCToolDefinitionProv(PackOutput packOutput) {
        super(packOutput, TCompat.MOD_ID);
    }


    @Override
    protected void addToolDefinitions() {
        RandomMaterial tier1Material = RandomMaterial.random().tier(1).build();
        DefaultMaterialsModule defaultFourParts = DefaultMaterialsModule.builder().material(tier1Material, tier1Material, tier1Material, tier1Material).build();
        ToolModule[] swordHarvest = {
                IsEffectiveModule.tag(TinkerTags.Blocks.MINABLE_WITH_SWORD),
                MiningSpeedModifierModule.blocks(7.5f, Blocks.COBWEB)
        };
        define(IFToolDefinitions.glaive)
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
                        .set(ToolStats.ATTACK_SPEED, 1.0f).build()))
                .module(new MultiplyStatsModule(MultiplierNBT.builder()
                        .set(ToolStats.ATTACK_DAMAGE, 1.5f)
                        .set(ToolStats.MINING_SPEED, 0.25f)
                        .set(ToolStats.DURABILITY, 3.5f).build()))
                .largeToolStartingSlots()
                // traits
                .module(ToolTraitsModule.builder()
                        .trait(ModifierIds.reach, 2)
                        .trait(TinkerModifiers.aoeSilkyShears).build())
                // behavior
                .module(ToolActionsModule.of(ToolActions.SWORD_DIG))
                .module(swordHarvest)
                .module(new SweepWeaponAttack(3));
    }

    @Override
    public String getName() {
        return "Tinker's Compatability Tool Definitions";
    }
}
