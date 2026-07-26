package io.github.pouffy.tcompat.compat;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalDefinitions {
    public static final ToolDefinition glaive = ToolDefinition.create(GlobalInit.glaive);

    public static final ToolDefinition dartShooter = ToolDefinition.create(GlobalInit.dartShooter);
    public static final ToolDefinition dart = ToolDefinition.create(GlobalInit.dart);
}
