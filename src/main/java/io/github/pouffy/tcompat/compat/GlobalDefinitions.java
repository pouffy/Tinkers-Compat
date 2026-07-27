package io.github.pouffy.tcompat.compat;

import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import java.util.ArrayList;
import java.util.List;

import static io.github.pouffy.tcompat.TCompat.getResource;

@SuppressWarnings("removal")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalDefinitions {
    public static final ToolDefinition glaive = ToolDefinition.create(GlobalInit.glaive);

    public static final ToolDefinition dartShooter = ToolDefinition.create(GlobalInit.dartShooter);
    public static final ToolDefinition dart = ToolDefinition.create(GlobalInit.dart);


    public static final List<MaterialVariantId> dartBarrelMaterials = new ArrayList<>();
    public static final List<MaterialVariantId> lipGuardMaterials = new ArrayList<>();

    static {
        //Existing Materials
        //Pull requests for these would be considered, but I'd have to examine them carefully.
        dartBarrelMaterials.add(MaterialIds.wood);
        dartBarrelMaterials.add(MaterialIds.treatedWood);
        dartBarrelMaterials.add(MaterialIds.ironwood);
        dartBarrelMaterials.add(MaterialIds.platedSlimewood);
        dartBarrelMaterials.add(MaterialIds.slimewood);
        dartBarrelMaterials.add(MaterialIds.rock);
        dartBarrelMaterials.add(MaterialIds.flint);
        dartBarrelMaterials.add(MaterialIds.scorchedStone);
        dartBarrelMaterials.add(MaterialIds.searedStone);
        dartBarrelMaterials.add(MaterialIds.whitestone);
        dartBarrelMaterials.add(MaterialIds.bone);
        dartBarrelMaterials.add(MaterialIds.blazingBone);
        dartBarrelMaterials.add(MaterialIds.necroticBone);
        dartBarrelMaterials.add(MaterialIds.venombone);

        //My variants
        MaterialBuilder.woodMaterials.forEach((builder, wood) -> dartBarrelMaterials.add(builder.variantId()));
        MaterialBuilder.rockMaterials.forEach((builder, wood) -> dartBarrelMaterials.add(builder.variantId()));

        //Integration (might add more here?)
        //Use this as a template for any non-tinkers existing variants.
        lipGuardMaterials.add(new MaterialId(getResource("constructs_casting", "pyrium")));
        lipGuardMaterials.add(new MaterialId(getResource("constructs_casting", "mithril")));

        //Existing variants (will add more as I come across them)
        //Pull requests are welcome here

        //Wood
        dartBarrelMaterials.add(MaterialIds.oak);
        dartBarrelMaterials.add(MaterialIds.spruce);
        dartBarrelMaterials.add(MaterialIds.birch);
        dartBarrelMaterials.add(MaterialIds.jungle);
        dartBarrelMaterials.add(MaterialIds.acacia);
        dartBarrelMaterials.add(MaterialIds.darkOak);
        dartBarrelMaterials.add(MaterialIds.mangrove);
        dartBarrelMaterials.add(MaterialIds.cherry);
        dartBarrelMaterials.add(MaterialIds.crimson);
        dartBarrelMaterials.add(MaterialIds.warped);

        //Slimewood
        dartBarrelMaterials.add(MaterialIds.slimewoodComposite);
        dartBarrelMaterials.add(MaterialIds.greenheart);
        dartBarrelMaterials.add(MaterialIds.skyroot);
        dartBarrelMaterials.add(MaterialIds.bloodshroom);
        dartBarrelMaterials.add(MaterialIds.enderbark);

        //Rock
        dartBarrelMaterials.add(MaterialIds.stone);
        dartBarrelMaterials.add(MaterialIds.andesite);
        dartBarrelMaterials.add(MaterialIds.diorite);
        dartBarrelMaterials.add(MaterialIds.granite);
        dartBarrelMaterials.add(MaterialIds.calcite);
        dartBarrelMaterials.add(MaterialIds.blackstone);

        //Flint
        dartBarrelMaterials.add(MaterialIds.basalt);
        dartBarrelMaterials.add(MaterialIds.deepslate);

        //Whitestone
        dartBarrelMaterials.add(MaterialIds.endstone);
        dartBarrelMaterials.add(MaterialIds.whitestoneComposite);
        dartBarrelMaterials.add(MaterialIds.whitestoneAluminum);
        dartBarrelMaterials.add(MaterialIds.whitestoneTin);
        dartBarrelMaterials.add(MaterialIds.whitestoneZinc);
    }
}
