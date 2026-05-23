package io.github.pouffy.tcompat.common.modifier.base;

import net.minecraft.ChatFormatting;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public abstract class AbstractTeamUpModifier extends NoLevelsModifier {

    protected abstract Predicate<IMaterial> getRequiredMaterials();

    public boolean isValid(IToolStackView tool) {
        boolean supportsModifier = false;
        for (var material : tool.getMaterials()) {
            if (getRequiredMaterials().test(material.get())) {
                supportsModifier = true;
                break;
            }
        }
        return supportsModifier;
    }

    public Component getDisplayName(IToolStackView tool, ModifierEntry entry, @Nullable RegistryAccess access) {
        MutableComponent display = entry.getDisplayName().copy();
        if (!isValid(tool)) {
            return display.withStyle(ChatFormatting.STRIKETHROUGH);
        }
        return display;
    }
}
