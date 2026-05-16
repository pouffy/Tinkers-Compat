package io.github.pouffy.tcompat.datagen.mantle;

import com.google.gson.JsonObject;
import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCModifiers;
import io.github.pouffy.tcompat.compat.ad_astra.AdAstraInit;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRInit;
import io.github.pouffy.tcompat.compat.betterend.BetterendInit;
import io.github.pouffy.tcompat.compat.betternether.BetternetherInit;
import io.github.pouffy.tcompat.compat.cataclysm.CataclysmInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import io.github.pouffy.tcompat.compat.species.SpeciesInit;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import slimeknights.mantle.data.GenericDataProvider;
import slimeknights.mantle.util.JsonHelper;
import slimeknights.tconstruct.library.modifiers.ModifierId;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MantleColorsProvider extends GenericDataProvider {
    private final Map<String, List<ColorEntry>> modifierColors = new HashMap<>();
    private final Map<String, List<ColorEntry>> materialColors = new HashMap<>();

    public MantleColorsProvider(PackOutput packOutput) {
        super(packOutput, PackOutput.Target.RESOURCE_PACK, "mantle", JsonHelper.DEFAULT_GSON);
    }

    public final CompletableFuture<?> run(CachedOutput cache) {
        ensureColorsAdded();
        return allOf(Collections.singleton(this.saveJson(cache, TCompat.getResource("colors"), this.serialise())));
    }

    private void ensureColorsAdded() {
        if (this.modifierColors.isEmpty() && this.materialColors.isEmpty()) {
            this.addColors();
        }
    }

    private void addColors() {
        for (MaterialBuilder builder : MaterialBuilder.materialBuilders) {
            addColor(builder);
        }
        //Ad Astra
        addColor(AdAstraInit.oxygenated.getId(),            0xa9b4ed);
        //Aether
        addColor(TCModifiers.aetherForged,                  0x7df0e7);
        addColor(TCModifiers.zanite,                        0xaf7ff6);
        addColor(AetherInit.ambrogen.getId(),               0xe4de73);
        addColor(AetherInit.ascension.getId(),              0xf195ef);
        addColor(AetherInit.thunderstruck.getId(),          0x5a9bdb);
        addColor(AetherInit.bloodsucking.getId(),           0xd11e1e);
        addColor(AetherInit.autochant.getId(),              0xe5eb62);
        addColor(AetherInit.autofreeze.getId(),             0xc6e1e1);
        //Aether Redux
        addColor(TCModifiers.blighted,                      0x8058f6);
        addColor(TCModifiers.escarstay,                     0x7178c2);
        addColor(AetherReduxInit.ambrofusion.getId(),       0x7fbedc);
        //Aether Treasure Reforging
        addColor(AetherTRInit.phoenixTouched.getId(),       0xd86a0b);
        //Betterend
        addColor(BetterendInit.voidTouched.getId(),         0x740696);
        //Betternether
        addColor(BetternetherInit.obsidianBreaker.getId(),  0x240f44);
        addColor(BetternetherInit.rubysFire.getId(),        0x6d0404);
        addColor(BetternetherInit.cinderspine.getId(),      0xaa581c);
        //Deep Aether
        addColor(TCModifiers.skyjade,                       0x9ada5b);
        addColor(DeepAetherInit.gale.getId(),               0x9cd9e6);
        //Ice & Fire
        addColor(TCModifiers.dreadbane,                     0x263941);
        addColor(TCModifiers.dampening,                     0xb7dfdd);
        addColor(TCModifiers.scorchborn,                    0xff581c);
        addColor(TCModifiers.frostborn,                     0x5effff);
        addColor(TCModifiers.voltborn,                      0x974fff);
        addColor(TCModifiers.tideGuardian,                  0x2aa6fd);
        addColor(TCModifiers.aquaShot,                      0xdbccb5);
        addColor(IFInit.flamed.getId(),                     0xff581c);
        addColor(IFInit.iced.getId(),                       0x5effff);
        addColor(IFInit.lightning.getId(),                  0x974fff);
        addColor(IFInit.scorchThorns.getId(),               0xff581c);
        addColor(IFInit.frostThorns.getId(),                0x5effff);
        addColor(IFInit.voltThorns.getId(),                 0x974fff);
        addColor(IFInit.breathless.getId(),                 0xffc796);
        addColor(IFInit.leeching.getId(),                   0xb2bea0);
        //Species
        addColor(SpeciesInit.ricoshield.getId(),            0x59002b);
        addColor(SpeciesInit.wicked.getId(),                0xffa342);
        addColor(SpeciesInit.swapping.getId(),              0xe72a8b);
        addColor(SpeciesInit.birt.getId(),                  0x3ac2d4);
        //Cataclysm
        addColor(TCModifiers.cataclysmic,                   0xfb6b1d);
        addColor(TCModifiers.ghostly,                       0x56eccc);
        addColor(TCModifiers.standstill,                    0x6e7786);
        addColor(CataclysmInit.voidScatter.getId(),         0x8219ff);
        addColor(CataclysmInit.aquatic.getId(),             0xc62a37);
        addColor(CataclysmInit.sandstorm.getId(),           0xdbb86a);
        addColor(CataclysmInit.phantasmic.getId(),          0x56eccc);
        addColor(CataclysmInit.tidal.getId(),               0x6071c1);
        addColor(CataclysmInit.fluxed.getId(),              0x6e7786);
    }

    public void addColor(MaterialBuilder material) {
        String namespace = material.getId().getId().getNamespace();
        String path = material.getId().getLocation('.').getPath();
        this.materialColors.compute(namespace, (n, l) -> {
            if (l == null) {
                l = new ArrayList<>();
            }
            ColorEntry entry = new ColorEntry(path, material.getRenderInfo().getColor());
            l.add(entry);
            return l;
        });
    }

    public void addColor(ModifierId modifier, int color) {
        String namespace = modifier.getNamespace();
        String path = modifier.getPath();
        this.modifierColors.compute(namespace, (n, l) -> {
            if (l == null) {
                l = new ArrayList<>();
            }
            ColorEntry entry = new ColorEntry(path, color);
            l.add(entry);
            return l;
        });
    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Color Provider";
    }

    private record ColorEntry(String path, int color) {}

    private JsonObject serialise() {
        JsonObject json = new JsonObject();
        for (String namespace : this.modifierColors.keySet()) {
            JsonObject namespaced = new JsonObject();
            for (ColorEntry entry : this.modifierColors.get(namespace)) {
                namespaced.addProperty(entry.path, hexString(entry.color));
            }
            json.add("modifier.%s".formatted(namespace), namespaced);
        }
        for (String namespace : this.materialColors.keySet()) {
            JsonObject namespaced = new JsonObject();
            for (ColorEntry entry : this.materialColors.get(namespace)) {
                namespaced.addProperty(entry.path, hexString(entry.color));
            }
            json.add("material.%s".formatted(namespace), namespaced);
        }
        return json;
    }

    public static String hexString(int colour) {
        return String.format("#%06X", (0xFFFFFF & colour));
    }
}
