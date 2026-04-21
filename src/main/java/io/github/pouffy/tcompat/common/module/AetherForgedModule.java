package io.github.pouffy.tcompat.common.module;

import io.github.pouffy.tcompat.compat.GlobalInit;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.data.loadable.record.SingletonLoader;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public enum AetherForgedModule implements ModifierModule, AetherForgedModifierHook {
    INSTANCE;

    public static final RecordLoadable<AetherForgedModule> LOADER = new SingletonLoader<>(INSTANCE);
    private static final List<ModuleHook<?>> DEFAULT_HOOKS = HookProvider.defaultHooks(GlobalInit.AETHER_FORGED);

    @Override
    public boolean canUse(IToolStackView toolStackView, ModifierEntry modifierEntry) {
        return true;
    }

    @Override
    public RecordLoadable<AetherForgedModule> getLoader() {
        return LOADER;
    }

    @Override
    public List<ModuleHook<?>> getDefaultHooks() {
        return DEFAULT_HOOKS;
    }
}
