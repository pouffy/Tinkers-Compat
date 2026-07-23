package io.github.pouffy.tcompat.datagen.lang;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.data.TCTags;
import io.github.pouffy.tcompat.common.fluid.TCFluids;
import io.github.pouffy.tcompat.common.material.MaterialBuilder;
import io.github.pouffy.tcompat.common.material.TCRocks;
import io.github.pouffy.tcompat.common.material.TCWoods;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
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
        tags();
        items();
        fluids();
        misc();
        modifiers();
    }

    private void materials() {
        MaterialBuilder.materialBuilders.forEach((builder) -> {
            this.add(builder.getTranslationKey(), builder.getEnglishName());
            if (builder.getFlavorText() != null) {
                this.add(builder.getTranslationKey() + ".flavor", builder.getFlavorText());
            }
        });
    }

    private void tags() {
        for (TCWoods wood : TCWoods.values()) {
            if (wood.hasPlanks() && !wood.hasPlankRedirect()) {
                tag(wood.plankTag(), toEngStr(wood.getSerializedName()+"_planks"));
            }
            if (wood.hasLogs()) {
                tag(wood.logTag(), toEngStr(wood.getSerializedName()+"_"+wood.builder.logType));
            }
        }
        for (TCRocks rock : TCRocks.values()) {
            if (!rock.hasRockRedirect()) {
                tag(rock.rockTag(), toEngStr(rock.langName+"_rocks"));
            }
        }
        for (var fluid : TCFluids.tags.entrySet()) {
            String translatedName = toEnglishName(fluid.getKey().getId().getPath());
            for (var tag : fluid.getValue()) {
                tagFluid(tag, translatedName);
            }
        }
        tag(TCTags.Items.MAGIC_MODIFIABLE, "Magic Modifiable");
        tag(TCTags.Items.JEWELERY_MODIFIABLE, "Jewelry Modifiable");
        tag(TCTags.Items.HANDHELD_CURIO, "Handheld Curio");

        tag(TCTags.Items.SENTRITE_STONE_BLOCKS, "Sentrite Stone Blocks");
        tag(TCTags.Items.SENTRITE_STONE_SLABS, "Sentrite Stone Slabs");

        tag(TCTags.Items.THALLASIUM_BULB_LANTERNS, "Thallasium Bulb Lanterns");
        tag(TCTags.Items.TERMINITE_BULB_LANTERNS, "Terminite Bulb Lanterns");
        tag(TCTags.Items.IRON_BULB_LANTERNS, "Iron Bulb Lanterns");

        tag(TCTags.Items.QUARTZ_GLASS, "Quartz Glass");
        tag(TCTags.Items.QUARTZ_GLASS_PANES, "Quartz Glass Panes");
        tag(TCTags.Items.FRAMED_QUARTZ_GLASS, "Framed Quartz Glass");
        tag(TCTags.Items.FRAMED_QUARTZ_GLASS_PANES, "Framed Quartz Glass Panes");

        tag(TCTags.Items.AUTOCHANT_LEFT, "Left Autochant Input");
        tag(TCTags.Items.AUTOCHANT_RIGHT, "Right Autochant Input");
        tag(TCTags.Items.AUTOFREEZE_LEFT, "Left Autofreeze Input");
        tag(TCTags.Items.AUTOFREEZE_RIGHT, "Right Autofreeze Input");

        tag(TCTags.Items.DIAMOND_SMITHING_TEMPLATES, "Diamond Smithing Templates");
        tag(TCTags.Items.VALKYRUM_SMITHING_TEMPLATES, "Valkyrum Smithing Templates");
        tag(TCTags.Items.TREASURE_SMITHING_TEMPLATES, "Unmeltable Smithing Templates");
        tag(TCTags.Items.NON_AETHER_WOODEN_RODS, "Non-Aether Wooden Rods");
        tag(TCTags.Items.AETHER_WOODEN_RODS, "Aether Wooden Rods");

        tag(TCTags.Items.ALTERNATIVE_VINES, "Alternative Vines");
        tag(TCTags.Items.FIRE_DRAGON_SCALES, "Fire Dragon Scales");
        tag(TCTags.Items.ICE_DRAGON_SCALES, "Ice Dragon Scales");
        tag(TCTags.Items.LIGHTNING_DRAGON_SCALES, "Lightning Dragon Scales");

        tagFluid(TCTags.Fluids.SMALLER_GEM_TOOLTIPS, "Smaller Gem");
    }

    private void items() {
        toEng("item.tcompat.glaive");
        add("item.tcompat.glaive.description", "The Glaive is a high reach, large radius polearm. Good for crowd control and long distance targets.");
        toEng("item.tcompat.lightnum_ingot");
        toEng("item.tcompat.draculite_ingot");
        toEng("item.tcompat.stormforged_steel_ingot");
        toEng("item.tcompat.lightnum_nugget");
        toEng("item.tcompat.draculite_nugget");
        toEng("item.tcompat.stormforged_steel_nugget");
        toEng("item.tcompat.fire_dragonsteel_nugget");
        toEng("item.tcompat.ice_dragonsteel_nugget");
        toEng("item.tcompat.lightning_dragonsteel_nugget");
        add("item.tconstruct.slime_helmet.material.tcompat.dragon_bone", "Dragon Slimeskull");
        add("item.tcompat.sky_slime_in_a_bucket", "Sky Slime in a Bucket");
        add("item.tcompat.ender_slime_in_a_bucket", "Ender Slime in a Bucket");
        add("item.tcompat.terracube_in_a_bucket", "Terracube in a Bucket");
    }

    private void fluids() {
        TCFluids.registeredFluids.forEach((key, fluid) -> forFluid(fluid.fluid(), fluid.lang()));
    }

    private void misc() {
        add("itemGroup.tcompat.tcompat", "Tinkers' Compatibility");

        add("gui.tcompat.fluid.gem", "%s gems");
        add("gui.tcompat.fluid.block", "%s blocks");
        add("gui.tcompat.fluid.shard", "%s fragments");

        add("modifier.tcompat.flamed.attack_bonus", "Ice Dragon Damage Bonus");
        add("modifier.tcompat.iced.attack_bonus", "Fire Dragon Damage Bonus");
        add("modifier.tcompat.lightning.attack_bonus", "Fire & Ice Dragon Damage Bonus");
        add("modifier.tcompat.kinetic.tooltip.damage", "Stored Damage: %s / 40");
        add("recipe.tconstruct.ambrofusion.at_capacity", "Tool has no more space for ambrosium");
        add("subtitles.tcompat.void_touched.activate", "Void appears");
        add("subtitles.tcompat.void_touched.deactivate", "Void dissipates");
        add("recipe.tcompat.modifier.dampening.level_2", "Dampening requires a different recipe for the first level.");
        add("recipe.tcompat.modifier.dampening.level_3", "Dampening requires a different recipe for the first two levels.");
        add("modifier.tcompat.dampening.attack_damage", "Dampening Melee Damage");
        add("modifier.tcompat.dampening.projectile_damage", "Dampening Ranged Damage");
        add("modifier.tcompat.dreadbane.attack_damage", "Dreadbane Melee Damage");
        add("modifier.tcompat.dreadbane.projectile_damage", "Dreadbane Ranged Damage");

        add("notification.tcompat.modifier_cooldown", "Modifier: %s is on cooldown");
        add("notification.tcompat.dampens_vibrations", "Dampens Vibrations");

        add("modifier.tcompat.scorchborn.attack_damage", "Lightning & Ice Dragon Melee Damage");
        add("modifier.tcompat.scorchborn.projectile_damage", "Lightning & Ice Dragon Ranged Damage");
        add("modifier.tcompat.scorchborn.resistance", "Fire Dragon Breath Protection");
        add("modifier.tcompat.frostborn.attack_damage", "Fire & Lightning Dragon Melee Damage");
        add("modifier.tcompat.frostborn.projectile_damage", "Fire & Lightning Dragon Ranged Damage");
        add("modifier.tcompat.frostborn.resistance", "Ice Dragon Breath Protection");
        add("modifier.tcompat.voltborn.attack_damage", "Fire & Ice Dragon Melee Damage");
        add("modifier.tcompat.voltborn.projectile_damage", "Fire & Ice Dragon Ranged Damage");
        add("modifier.tcompat.voltborn.resistance", "Lightning Dragon Breath Protection");
        add("modifier.tcompat.petrifying.attack_damage", "Petrified Melee Damage");
        add("modifier.tcompat.petrifying.projectile_damage", "Petrified Ranged Damage");
        add("modifier.tcompat.petrifying.resistance", "Petrified Protection");
        add("modifier.tcompat.allythropod.attack_damage", "Allythropod Melee Damage");
        add("modifier.tcompat.allythropod.projectile_damage", "Allythropod Ranged Damage");
        add("modifier.tcompat.allythropod.resistance", "Allythropod Protection");
        add("modifier.tcompat.tide_guardian.attack_damage", "Tide Guardian Melee Damage");

        add("modifier.tcompat.archaeologist.attack_damage", "Archaeology Melee Damage");
        add("modifier.tcompat.archaeologist.projectile_damage", "Archaeology Ranged Damage");
        add("modifier.tcompat.archaeologist.resistance", "Archaeology Protection");

        add("modifier.tcompat.zanite.attack_damage", "Zanite Melee Damage");
        add("modifier.tcompat.zanite.projectile_damage", "Zanite Ranged Damage");
        add("modifier.tcompat.zanite.mining_speed", "Zanite Mining Speed");
        add("modifier.tcompat.zanite.armor", "Zanite Armor");
        add("modifier.tcompat.skyjade.attack_damage", "Skyjade Melee Damage");
        add("modifier.tcompat.skyjade.projectile_damage", "Skyjade Ranged Damage");
        add("modifier.tcompat.skyjade.mining_speed", "Skyjade Mining Speed");
        add("modifier.tcompat.skyjade.armor", "Skyjade Armor");

        add("modifier.tcompat.discharge.current", "Dischargable: ");

        add("item.tconstruct.creative_slot.rune", "Creative Rune Slot");
        add("stat.tconstruct.slot.prefix.rune", "Rune Slots: ");
        add("stat.tconstruct.slot.display.rune", "rune");
        add("modifier.tconstruct.rebalanced.rune", "Rune");
        add("recipe.tconstruct.remove_modifier.rune", "Extract Rune");
        add("recipe.tcompat.requirement.ignitium", "Ignitium requires Netherite to apply");
    }

    private void modifiers() {
        forModifier("modifier.tcompat.escarstay", "Tough as Snails", "Large flat increase in knockback resistance.");
        forModifier("modifier.tcompat.aether_forged", "Heavenly!", "Allows your tool to use it's full effectiveness in The Aether.");
        forModifier("modifier.tcompat.phoenix_touched", "Flamin' Hot", "Launched projectiles are lit with an immortal flame.");
        forModifier("modifier.tcompat.ambrogen", "Snack Generator", "Small chance to dislodge Ambrosium from broken blocks.");
        forModifier("modifier.tcompat.ascension", "Up You Go!", "Interacting with a block will cause it to ascend to a higher plane.");
        forModifier("modifier.tcompat.blighted", "Intoxicating", "Targets and attackers are inflicted with Inebriation");
        forModifier("modifier.tcompat.ambrofusion", "Fuel For Thought", "Tool can have it's stats buffed with Ambrosium but durability depletes faster.");
        forModifier("modifier.tcompat.dulling", "Dull their Sparkle", "Gradually loses effectiveness.");
        forModifier("modifier.tcompat.skyjade", "Dull their Sparkle", "Gradually loses effectiveness.");
        forModifier("modifier.tcompat.acclimatization", "In For a Promotion", "Gradually becomes more effective.");
        forModifier("modifier.tcompat.zanite", "In For a Promotion", "Gradually becomes more effective.");
        forModifier("modifier.tcompat.autochant", "Where's the book?","Tool automatically enchants all drops.");
        forModifier("modifier.tcompat.autofreeze", "Freeze!", "Tool automatically freezes all drops.");
        forModifier("modifier.tcompat.thunderstruck", "Strike me down!", "Charged attacks smite targets with lightning.");
        forModifier("modifier.tcompat.bloodsucking", "Revitalizing", "Charged attacks heal the user.");
        forModifier("modifier.tcompat.gale", "Blown away", "Charged attacks push targets away.");
        //Betternether
        forModifier("modifier.tcompat.obsidian_breaker", "Shattering", "Increased mining speed against obsidian and similar blocks.");
        forModifier("modifier.tcompat.rubys_fire", "Ruby's Fire", "Heatstroke on demand", "Causes targets to ignite and drops to be blasted.");
        //Species
        forModifier("modifier.tcompat.ricoshield", "It's gonna blow!", "Lower your shield to release a burst of absorbed damage.");
        forModifier("modifier.tcompat.wicked", "Look to the western sky", "Inflict targets with a stacking Combustion effect.");
        forModifier("modifier.tcompat.swapping", "Take their place", "Struck targets will switch places with the shooter.");
        forModifier("modifier.tcompat.birt", "Birt", "Birt");
        //Ad Astra
        forModifier("modifier.tcompat.discharge", "It's electrifying", "Using the tool generates energy that can be discharged into blocks or items.");
        forModifier("modifier.tcompat.oxygenated", "Breathe in...", "Using the tool replenishes air by consuming Oxygen.");
        //Betterend
        forModifier("modifier.tcompat.void_touched", "All Consuming", "Increased efficiency against end stone blocks.\nApplies a stacking damage multiplier to targets.");
        //Ice and Fire
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
        forModifier("modifier.tcompat.tide_guardian", "Sponge Muscles", "Water Breathing and increased strength when wet.");
        forModifier("modifier.tcompat.aqua_shot", "Sopping Wet", "Does extra damage when shooter is wet");
        forModifier("modifier.tcompat.leeching", "What's yours is mine", "Poisons and leeches life from target");
        forModifier("modifier.tcompat.amphitheric", "Go Away", "Knocks all mobs back with the power of an amphithere's wings");
        forModifier("modifier.tcompat.stymphalian", "Soar", "The metallic fletching enables the arrow to soar like a bird");
        forModifier("modifier.tcompat.petrifying", "Pretty Solid", "The troll's hide petrifies in sunlight, strengthening your tool");
        forModifier("modifier.tcompat.allythropod", "Why not be friends?", "Bonus damage to non-arthropods and deathworms");
        //Cataclysm
        forModifier("modifier.tcompat.cataclysmic", "Nigh Unstoppable", "The spirits of ancient gods try to hold your tool together.");
        forModifier("modifier.tcompat.void_scatter", "Scatter!", "Upon hitting a target, the arrow shatters into multiple void shards.");
        forModifier("modifier.tcompat.aquatic", "Sleeping with the fishes", "Your tool stats are buffed based on the amount of aquatic creatures nearby.");
        forModifier("modifier.tcompat.sandstorm", "Curse of Ra", "Charged strikes summon a sandstorm.");
        forModifier("modifier.tcompat.phantasmic", "Who ya gonna call?", "Shooting an arrow summons three phantom arrows.");
        forModifier("modifier.tcompat.ghostly", "Missed me!", "Small chance to completely ignore an incoming attack. Chance increases if the attack is a projectile.");
        forModifier("modifier.tcompat.tidal", "Washed away", "When used while sneaking, it summons Waves in a Fan pattern.");
        forModifier("modifier.tcompat.archaeologist", "Belongs in a museum", "Bonus damage and protection against ancient remnants.");
        forModifier("modifier.tcompat.standstill", "Stay right there", "Removes all knockback.");
        forModifier("modifier.tcompat.fluxed", "Industrial Grade Combat", "Fully charged shots fire a heavy-duty Wither Rocket.");
        forModifier("modifier.tcompat.ignitium", "Flamin' Hot", "Ignis' rage fills your armor, strengthening stats and providing special abilities.");
        //Malum
        forModifier("modifier.tcompat.stained", "Iron's cooler sibling", "Tool deals extra magic damage.");
        forModifier("modifier.tcompat.warded", "Safe enough", "Increased Soul Ward capacity and recovery rate.");
        forModifier("modifier.tcompat.hallowed", "It's just a tribute", "Adds one bonus Rune slot to the tool.");
        forModifier("modifier.tcompat.stronghold", "Extra safe", "Increased Malignant Conversion.");
        forModifier("modifier.tcompat.certainty", "Don't be grim", "A successful slaughter will sometimes double the force of the next swing.");
        forModifier("modifier.tcompat.magic_proficiency", "My potions are too strong for you, traveller...", "Increases Magic Proficiency, and, in turn, magic damage.");
        forModifier("modifier.tcompat.armor_toughness_gem", "Armor Toughness", "Tough", "Adds additional armor toughness");
        forModifier("modifier.tcompat.cloaking", "Now you see me", "Reduces enemy aggression");
        forModifier("modifier.tcompat.arcane_resonance", "Extra potent", "Bonus potency for spirit-collection effects.");
        forModifier("modifier.tcompat.spirit_harvester", "Feeling exposed?", "Attacking exposes the target's soul");
        forModifier("modifier.tcompat.runic", "It's just a tribute", "Adds one bonus Rune slot to the tool.");
        forModifier("modifier.tcompat.spirit_repair", "Late stage capitalism", "Collecting spirits repairs the tool.");
        forModifier("modifier.tcompat.deliverance", "2-3 business days", "Deal a weakened attack in order for your next attack to be doubled");
        forModifier("modifier.tcompat.mnemonic", "Gonna cast a spell on you", "Charge up and launch hexed bolts.");
        forModifier("modifier.tcompat.erosion", "You probably learned about this in geography class", "Charge up and launch draining bolts.");
        forModifier("modifier.tcompat.auric_flame", "Fire", "Charge up and launch a volley of auric flame bolts.");
        forModifier("modifier.tcompat.spoiled", "Can I have some more?", "Increased Spirit Spoils");
        forModifier("modifier.tcompat.integral", "Well Warded", "Increased Soul Ward Integrity");

        malumRune("motion", "Motion", "The zephyr propels you forward, increasing your movement speed.");
        malumRune("loyalty", "Loyalty", "You reach out for further power, increasing your reach and item pickup distance.");
        malumRune("warding", "Warding", "An earthen carapace surrounds your body, functioning as extra armor.");
        malumRune("haste", "Haste", "Your tools are bolstered with radiance, increasing your mining and attack speed.");
        malumRune("the_aether", "the Aether", "The heavens call for you, increasing jump height and decreasing gravity.");
        malumRune("the_seas", "the Seas", "Let any fish who meets my gaze learn the true meaning of fear; for I am the harbinger of death. The bane of creatures sub-aqueous, my rod is true and unwavering as I cast into the aquatic abyss. A man, scorned by this uncaring Earth, finds solace in the sea. My only friend, the worm upon my hook. Wriggling, writhing, struggling to surmount the mortal pointlessness that permeates this barren world. I am alone. I am empty. And yet, I fish.");
        malumRune("the_arena", "the Arena", "Your fists and tools are reinforced with earth, increasing your strength.");
        malumRune("the_hells", "the Hells", "The warm embrace of fire coats your soul, mending your seared scars.");
        malumRune("idle_restoration", "Idle Restoration", "Passively heals the user.");
        malumRune("culling", "Culling", "Increased Magic Proficiency and Spell Power.");
        malumRune("reinforcement", "Reinforcement", "Increased Soul Ward Capacity and Integrity.");
        malumRune("volatile_distortion", "Volatile Distortion", "Critical Strikes and Erratic Damage Output.");
        malumRune("dexterity", "Dexterity", "Increased Speed at low health.");
        malumRune("aliment_cleansing", "Aliment Cleansing", "Shortens negative effects.");
        malumRune("reactive_shielding", "Reactive Shielding", "Damage resistance when attacked.");
        malumRune("fervor", "Fervor", "Increased mining speed.");
        malumRune("bolstering", "Bolstering", "Increased max health.");
        malumRune("sacrificial_empowerment", "Sacrificial Empowerment", "Enables scythe kill chaining.");
        malumRune("spell_mastery", "Spell Mastery", "Bonus Staff Charges, increased Magic Proficiency and Spell Power.");
        malumRune("the_heretic", "the Heretic", "Silences attackers");
        malumRune("unnatural_stamina", "Unnatural Stamina", "Increased speed and sprinting is always available");
        malumRune("twinned_duration", "Twinned Duration", "Extends positive effects.");
        malumRune("toughness", "Toughness", "Increased armor toughness.");
        malumRune("igneous_solace", "Igneous Solace", "Damage resistance while burning.");

        forModifier("modifier.tcompat.warden", "Silence", "Utilise the power of the Warden to dampen vibrations and make the tool stronger.");
        forModifier("modifier.tcompat.brightness", "Now I See You", "Pushes away the dark fog brought on by the warden's presence.");
        forModifier("modifier.tcompat.heartbeat", "I'm Feeling Romantical", "Something within the tool occasionally beats.");
        forModifier("modifier.tcompat.sonorous", "Did you hear that?", "Charge up and release a sonic boom.");
        forModifier("modifier.tcompat.sculk_affinity", "One with the tendrils", "Using the tool inflicts Sculk Affinity.");
    }

    private void tag(TagKey<Item> tag, String name) {
        ResourceLocation tagId = tag.location();
        String tagNamespace = tagId.getNamespace().equals("forge") ? "c" : tagId.getNamespace();
        super.add("tag.item.%s.%s".formatted(tagNamespace, tagId.getPath().replace('/', '.')), name);
    }

    private void tagFluid(TagKey<Fluid> tag, String name) {
        ResourceLocation tagId = tag.location();
        String tagNamespace = tagId.getNamespace().equals("forge") ? "c" : tagId.getNamespace();
        super.add("tag.fluid.%s.%s".formatted(tagNamespace, tagId.getPath().replace('/', '.')), name);
    }

    private void malumRune(String type, String name, String description) {
        String key = "modifier.tcompat.rune_of_%s".formatted(type);
        add(key, "Rune of %s".formatted(name));
        add(key+".flavor", "Runic");
        add(key+".description", description);
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
    private void forFluid(FluidObject<?> fluid, String lang) {
        String fluidKey = fluid.getId().toLanguageKey("fluid");
        String blockKey = fluid.getId().toLanguageKey("block")+"_fluid";
        String itemKey = fluid.getId().toLanguageKey("item")+"_bucket";
        this.add(fluidKey, lang);
        this.add(blockKey, lang);
        this.add(itemKey, lang + " Bucket");
    }


    private void forFluid(FluidObject<?> fluid) {
        toEng(fluid.getId().toLanguageKey("fluid"));
        this.add(fluid.getId().toLanguageKey("block")+"_fluid", "%s".formatted(toEngStr(fluid.getId().toLanguageKey("fluid"))));
        toEng(fluid.getId().toLanguageKey("item")+"_bucket");
    }

    private void toEng(String name) {
        // get the last part of the translation key to use as the english name
        String[] parts = name.split("\\.");
        String toTranslate = parts[parts.length - 1];
        this.add(name, "%s".formatted(toEnglishName(toTranslate)));
    }

    public static String toEngStr(Object internalName) {
        String[] parts = internalName.toString().split("\\.");
        String toTranslate = parts[parts.length - 1];
        return toEnglishName(toTranslate);
    }

    public static String toEnglishName(Object internalName) {
        return Arrays.stream(internalName.toString().toLowerCase(Locale.ROOT).split("_"))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining(" "));
    }
}
