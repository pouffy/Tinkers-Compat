package io.github.pouffy.tcompat.datagen.mantle;

import com.google.gson.JsonObject;
import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.compat.ad_astra.AdAstraInit;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRInit;
import io.github.pouffy.tcompat.compat.betterend.BetterendInit;
import io.github.pouffy.tcompat.compat.betternether.BetternetherInit;
import io.github.pouffy.tcompat.compat.cataclysm.CataclysmInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import io.github.pouffy.tcompat.compat.malum.MalumInit;
import io.github.pouffy.tcompat.compat.species.SpeciesInit;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import slimeknights.mantle.data.GenericDataProvider;
import slimeknights.mantle.util.JsonHelper;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.SlotType;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MantleColorsProvider extends GenericDataProvider {
    private final Map<String, List<ColorEntry>> modifierColors = new HashMap<>();
    private final Map<String, List<ColorEntry>> materialColors = new HashMap<>();
    private final Map<String, List<ColorEntry>> slotColors = new HashMap<>();

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
        addColor(MalumInit.RUNE_SLOT,                       0xd200cb);
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
        addColor(AetherReduxInit.blighted.getId(),          0x8058f6);
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
        addColor(TCModifiers.petrifying,                    0x50606b);
        addColor(TCModifiers.allythropod,                   0xac6111);
        addColor(IFInit.flamed.getId(),                     0xff581c);
        addColor(IFInit.iced.getId(),                       0x5effff);
        addColor(IFInit.lightning.getId(),                  0x974fff);
        addColor(IFInit.scorchThorns.getId(),               0xff581c);
        addColor(IFInit.frostThorns.getId(),                0x5effff);
        addColor(IFInit.voltThorns.getId(),                 0x974fff);
        addColor(IFInit.breathless.getId(),                 0xffc796);
        addColor(IFInit.leeching.getId(),                   0xb2bea0);
        addColor(IFInit.amphitheric.getId(),                0x1a8b80);
        addColor(IFInit.stymphalian.getId(),                0x896a55);
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
        //Malum
        addColor(TCModifiers.armorToughness_gem,            0x8a5eae);
        addColor(TCModifiers.stained,                       0xce7cee);
        addColor(TCModifiers.warded,                        0xce7cee);
        addColor(TCModifiers.hallowed,                      0xe4ab38);
        addColor(TCModifiers.stronghold,                    0x5e4870);
        addColor(TCModifiers.magicProficiency,              0xa32cbf);
        addColor(TCModifiers.cloaking,                      0xbbf1f4);
        addColor(TCModifiers.arcaneResonance,               0xffaa7d);
        addColor(TCModifiers.spiritHarvester,               0xeccef6);
        addColor(TCModifiers.runic,                         0x643b3b);
        addColor(TCModifiers.spoiled,                       0x874483);
        addColor(TCModifiers.integral,                      0xe92fec);
        addColor(MalumInit.spiritRepair.getId(),            0x918496);
        addColor(MalumInit.certainty.getId(),               0x4d2450);
        addColor(MalumInit.deliverance.getId(),             0x8108e5);
        addColor(MalumInit.mnemonic.getId(),                0x792cec);
        addColor(MalumInit.erosion.getId(),                 0x440B3D);
        addColor(MalumInit.auricFlame.getId(),              0xefd74b);
        addColor(MalumInit.runeOfMotion.getId(),            0x7df0e7);
        addColor(MalumInit.runeOfLoyalty.getId(),           0x63b1cc);
        addColor(MalumInit.runeOfWarding.getId(),           0xa2cc66);
        addColor(MalumInit.runeOfHaste.getId(),             0xffb437);
        addColor(MalumInit.runeOfTheAether.getId(),         0x7df0e7);
        addColor(MalumInit.runeOfTheSeas.getId(),           0x63b1cc);
        addColor(MalumInit.runeOfTheArena.getId(),          0xa2cc66);
        addColor(MalumInit.runeOfTheHells.getId(),          0xffb437);
        addColor(TCModifiers.runeOfIdleRestoration,         0xf74594);
        addColor(MalumInit.runeOfCulling.getId(),           0xb125e6);
        addColor(MalumInit.runeOfReinforcement.getId(),     0xf887ee);
        addColor(TCModifiers.runeOfVolatileDistortion,      0x8108e5);
        addColor(MalumInit.runeOfDexterity.getId(),         0xaef5ef);
        addColor(TCModifiers.runeOfAlimentCleansing,        0x95f6f9);
        addColor(TCModifiers.runeOfReactiveShielding,       0xd9ea75);
        addColor(TCModifiers.runeOfFervor,                  0xffe65d);
        addColor(MalumInit.runeOfBolstering.getId(),        0xf74594);
        addColor(TCModifiers.runeOfSacrificialEmpowerment,  0xca32d4);
        addColor(MalumInit.runeOfSpellMastery.getId(),      0xf887ee);
        addColor(TCModifiers.runeOfTheHeretic,              0xe857de);
        addColor(MalumInit.runeOfUnnaturalStamina.getId(),  0x7fc1ec);
        addColor(TCModifiers.runeOfTwinnedDuration,         0x6a76c5);
        addColor(MalumInit.runeOfToughness.getId(),         0x6c9853);
        addColor(TCModifiers.runeOfIgneousSolace,           0xeb6a61);
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

    public void addColor(SlotType slotType, int color) {
        String path = slotType.getName();
        this.slotColors.compute("tconstruct", (n, l) -> {
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
        for (String namespace : this.slotColors.keySet()) {
            JsonObject namespaced = new JsonObject();
            for (ColorEntry entry : this.slotColors.get(namespace)) {
                namespaced.addProperty(entry.path, hexString(entry.color));
            }
            json.add("stat.%s.slot".formatted(namespace), namespaced);
        }
        return json;
    }

    public static String hexString(int colour) {
        return String.format("#%06X", (0xFFFFFF & colour));
    }
}
