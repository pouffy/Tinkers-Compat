package io.github.pouffy.tcompat.datagen.lang;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.material.TCMaterials;
import io.github.pouffy.tcompat.compat.ad_astra.AdAstraInit;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.aether_treasure_reforging.AetherTRInit;
import io.github.pouffy.tcompat.compat.betterend.BetterendInit;
import io.github.pouffy.tcompat.compat.betternether.BetternetherInit;
import io.github.pouffy.tcompat.compat.deep_aether.DeepAetherInit;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.commons.lang3.StringUtils;
import slimeknights.mantle.registration.object.FluidObject;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class TCLangProv extends LanguageProvider {
    public TCLangProv(PackOutput output) {
        super(output, TCompat.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        materials();
        items();
        fluids();
        misc();
        modifiers();
    }

    private void materials() {
        TCMaterials.translationKeys.forEach((key) -> {
            if (key.contains("_dragon_scale.")) {
                String[] parts = key.split("\\.");
                String toTranslate = parts[parts.length - 1];
                this.add(key, "%s".formatted(toEnglishName(toTranslate)) + " Dragon Scale");
            } else if (key.contains("material.tconstruct.wood")) {
                String[] parts = key.split("\\.");
                String toTranslate = parts[parts.length - 1];
                this.add(key, "%s".formatted(toEnglishName(toTranslate)) + " Wood");
            } else {
                toEng(key);
            }
        });
    }

    private void items() {
        toEng("item.tcompat.glaive");
        add("item.tcompat.glaive.description", "The Glaive is a high reach, large radius polearm. Good for crowd control and long distance targets.");
        toEng("item.tcompat.lightnum_ingot");
        toEng("item.tcompat.draculite_ingot");
        toEng("item.tcompat.stormforged_steel_ingot");
        toEng("item.tcompat.fire_dragonsteel_nugget");
        toEng("item.tcompat.ice_dragonsteel_nugget");
        toEng("item.tcompat.lightning_dragonsteel_nugget");
        add("item.tconstruct.slime_helmet.material.tcompat.dragon_bone", "Dragon Slimeskull");
    }

    private void fluids() {
        forFluid(DeepAetherInit.moltenSkyjade);
        forFluid(DeepAetherInit.moltenStratus);
        forFluid(AetherInit.moltenZanite);
        forFluid(AetherInit.moltenGravitite);
        forFluid(AetherReduxInit.moltenRefinedSentrite);
        forFluid(AetherReduxInit.moltenVeridium);
        forFluid(AetherTRInit.moltenPyral);
        forFluid(AetherTRInit.moltenNeptune);
        forFluid(AetherTRInit.moltenValkyrum);
        forFluid(AdAstraInit.moltenDesh);
        forFluid(AdAstraInit.moltenCalorite);
        forFluid(AdAstraInit.moltenOstrum);
        forFluid(BetterendInit.moltenThallasium);
        forFluid(BetterendInit.moltenTerminite);
        forFluid(BetterendInit.moltenAeternium);
        forFluid(AetherInit.moltenLightnum);
        forFluid(AetherInit.moltenDraculite);
        forFluid(DeepAetherInit.moltenStormforgedSteel);
        forFluid(BetternetherInit.moltenCincinnasite);
        forFluid(BetternetherInit.moltenNetherRuby);
        forFluid(IFInit.fireBlood);
        forFluid(IFInit.iceBlood);
        forFluid(IFInit.lightningBlood);
        forFluid(IFInit.moltenFireDragonsteel);
        forFluid(IFInit.moltenIceDragonsteel);
        forFluid(IFInit.moltenLightningDragonsteel);
    }

    private void misc() {
        add("modifier.tcompat.flamed.attack_bonus", "Damage against Ice Dragons");
        add("modifier.tcompat.iced.attack_bonus", "Damage against Fire Dragons");
        add("modifier.tcompat.lightning.attack_bonus", "Damage against Fire Dragons & Ice Dragons");
        add("modifier.tcompat.kinetic.tooltip.damage", "Stored Damage: %s / 40");
        add("recipe.tconstruct.ambrofusion.at_capacity", "Tool has no more space for ambrosium");
        add("subtitles.tcompat.void_touched.activate", "Void appears");
        add("subtitles.tcompat.void_touched.deactivate", "Void dissipates");
        add("recipe.tcompat.modifier.dampening.level_2", "Dampening requires a different recipe for the first level.");
        add("recipe.tcompat.modifier.dampening.level_3", "Dampening requires a different recipe for the first two levels.");
        add("modifier.tcompat.dampening.attack_damage", "Damage against Mythical Creatures");
        add("modifier.tcompat.dreadbane.attack_damage", "Damage against Dread Creatures");

        add("modifier.tcompat.scorchborn.attack_damage", "Melee Damage against Damage against Lightning & Ice Dragons");
        add("modifier.tcompat.scorchborn.projectile_damage", "Ranged Damage against Lightning & Ice Dragons");
        add("modifier.tcompat.scorchborn.resistance", "Protection against Fire Dragon Breath");
        add("modifier.tcompat.frostborn.attack_damage", "Melee Damage against Fire & Lightning Dragons");
        add("modifier.tcompat.frostborn.projectile_damage", "Ranged Damage against Fire & Lightning Dragons");
        add("modifier.tcompat.frostborn.resistance", "Protection against Ice Dragon Breath");
        add("modifier.tcompat.voltborn.attack_damage", "Melee Damage against Fire & Ice Dragons");
        add("modifier.tcompat.voltborn.projectile_damage", "Ranged Damage against Fire & Ice Dragons");
        add("modifier.tcompat.voltborn.resistance", "Protection against Lightning Dragon Breath");
    }

    private void modifiers() {
        forModifier("modifier.tcompat.escarstay", "Tough as Snails", "Large flat increase in knockback resistance.");
        forModifier("modifier.tcompat.aether_forged", "Heavenly!", "Allows your tool to use it's full mining speed in The Aether.");
        forModifier("modifier.tcompat.phoenix_touched", "Flamin' Hot", "Launched projectiles are lit with an immortal flame.");
        forModifier("modifier.tcompat.ambrogen", "Snack Generator", "Small chance to dislodge Ambrosium from broken blocks.");
        forModifier("modifier.tcompat.ascension", "Up You Go!", "Interacting with a block will cause it to ascend to a higher plane.");
        forModifier("modifier.tcompat.blighted", "Intoxicating", "Hitting targets inflicts them with Inebriation");
        forModifier("modifier.tcompat.ambrofusion", "Fuel For Thought", "Tool can have it's stats buffed with Ambrosium but durability depletes faster.");
        forModifier("modifier.tcompat.dulling", "Dull their Sparkle", "Gradually loses effectiveness.");
        forModifier("modifier.tcompat.acclimatization", "In For a Promotion", "Gradually becomes more effective.");
        forModifier("modifier.tcompat.autochant", "Where's the book?","Tool automatically enchants all drops.");
        forModifier("modifier.tcompat.autofreeze", "Freeze!", "Tool automatically freezes all drops.");
        forModifier("modifier.tcompat.thunderstruck", "Strike me down!", "Charged attacks smite targets with lightning.");
        forModifier("modifier.tcompat.bloodsucking", "Revitalizing", "Charged attacks heal the user.");
        forModifier("modifier.tcompat.gale", "Blown away", "Charged attacks push targets away.");
        forModifier("modifier.tcompat.obsidian_breaker", "Shattering", "Increased mining speed against obsidian and similar blocks.");
        forModifier("modifier.tcompat.rubys_fire", "Ruby's Fire", "Heatstroke on demand", "Causes targets to ignite and drops to be blasted.");
        forModifier("modifier.tcompat.ricoshield", "It's gonna blow!", "Lower your shield to release a burst of absorbed damage.");
        forModifier("modifier.tcompat.wicked", "Look to the western sky", "Inflict targets with a stacking Combustion effect.");
        forModifier("modifier.tcompat.swapping", "Take their place", "Struck targets will switch places with the shooter.");
        forModifier("modifier.tcompat.birt", "Birt", "Birt");
        forModifier("modifier.tcompat.oxygenated", "Breathe in...", "Using the tool replenishes air by consuming Oxygen.");
        forModifier("modifier.tcompat.void_touched", "All Consuming", "Increased efficiency against end stone blocks.\nApplies a stacking damage multiplier to targets.");
        forModifier("modifier.tcompat.scorch_thorns", "Burns with the fires of hell!", "Ignites and knocks back attackers.");
        forModifier("modifier.tcompat.flamed", "Burns with the fires of hell!", "Ignites and knocks back targets.");
        forModifier("modifier.tcompat.frost_thorns", "Freezes the blood in your veins!", "Freezes attackers.");
        forModifier("modifier.tcompat.iced", "Freezes the blood in your veins!", "Freezes targets.");
        forModifier("modifier.tcompat.volt_thorns", "Strikes with the power of the gods!", "Strikes attackers with lightning.");
        forModifier("modifier.tcompat.lightning", "Strikes with the power of the gods!", "Strikes targets with lightning.");
        forModifier("modifier.tcompat.breathless", "Really takes your breath away", "Increased protection from dragon breath attacks.");
        forModifier("modifier.tcompat.dreadbane", "Dreadful", "Deals extra damage to Dread Creatures.");
        forModifier("modifier.tcompat.dampening", "Begone foul magic!", "Deals extra damage to mystical creatures.");
        forModifier("modifier.tcompat.scorchborn", "One with the dragon", "Bonus damage against other dragon types and bonus protection against Fire Dragons.");
        forModifier("modifier.tcompat.frostborn", "One with the dragon", "Bonus damage against other dragon types and bonus protection against Ice Dragons.");
        forModifier("modifier.tcompat.voltborn", "One with the dragon", "Bonus damage against other dragon types and bonus protection against Lightning Dragons.");
    }

    private void forModifier(String key, String name, String flavor, String description) {
        add(key, name);
        add(key+".flavor", flavor);
        add(key+".description", description);
    }

    private void forModifier(String key, String flavor, String description) {
        toEng(key);
        add(key+".flavor", flavor);
        add(key+".description", description);
    }

    private void forFluid(FluidObject<?> fluid) {
        toEng(fluid.getId().toLanguageKey("fluid"));
        this.add(fluid.getId().toLanguageKey("block")+"_fluid", "%s".formatted(toEnglishName(fluid.getId().toLanguageKey("fluid"))));
        toEng(fluid.getId().toLanguageKey("item")+"_bucket");
    }

    private void toEng(String name) {
        // get the last part of the translation key to use as the english name
        String[] parts = name.split("\\.");
        String toTranslate = parts[parts.length - 1];
        this.add(name, "%s".formatted(toEnglishName(toTranslate)));
    }

    public static String toEnglishName(Object internalName) {
        return Arrays.stream(internalName.toString().toLowerCase(Locale.ROOT).split("_"))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining(" "));
    }
}
