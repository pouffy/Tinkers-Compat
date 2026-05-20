package io.github.pouffy.tcompat.common.module;

import io.github.pouffy.tcompat.compat.GlobalInit;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.data.loadable.record.SingletonLoader;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public enum SoulExposureModule implements ModifierModule, SoulExposureModifierHook {
    INSTANCE;

    public static final RecordLoadable<SoulExposureModule> LOADER = new SingletonLoader<>(INSTANCE);
    private static final List<ModuleHook<?>> DEFAULT_HOOKS = HookProvider.defaultHooks(GlobalInit.SOUL_EXPOSURE);

    @Override
    public boolean canUse(IToolStackView toolStackView, ModifierEntry modifierEntry) {
        return true;
    }

    @Override
    public RecordLoadable<SoulExposureModule> getLoader() {
        return LOADER;
    }

    @Override
    public List<ModuleHook<?>> getDefaultHooks() {
        return DEFAULT_HOOKS;
    }
}
