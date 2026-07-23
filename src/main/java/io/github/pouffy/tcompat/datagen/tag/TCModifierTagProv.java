package io.github.pouffy.tcompat.datagen.tag;

import io.github.pouffy.tcompat.TCompat;
import io.github.pouffy.tcompat.common.modifier.TCModifiers;
import io.github.pouffy.tcompat.compat.aether.AetherInit;
import io.github.pouffy.tcompat.compat.aether_redux.AetherReduxInit;
import io.github.pouffy.tcompat.compat.betternether.BetternetherInit;
import io.github.pouffy.tcompat.compat.deeperdarker.DarkerInit;
import io.github.pouffy.tcompat.compat.ice_and_fire.IFInit;
import io.github.pouffy.tcompat.compat.malum.MalumInit;
import io.github.pouffy.tcompat.compat.species.SpeciesInit;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

import slimeknights.tconstruct.library.data.tinkering.AbstractModifierTagProvider;

import static slimeknights.tconstruct.common.TinkerTags.Modifiers.*;
import static io.github.pouffy.tcompat.common.data.TCTags.Modifiers.*;

public class TCModifierTagProv extends AbstractModifierTagProvider {
    public TCModifierTagProv(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, TCompat.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(CHARGE_EMPTY_BOW_WITH_DRAWTIME)
                .addOptional(MalumInit.erosion.getId(), MalumInit.auricFlame.getId(), MalumInit.mnemonic.getId(), DarkerInit.sonorous.getId());
        this.tag(GEMS).addOptional(TCModifiers.zanite, TCModifiers.skyjade, TCModifiers.spoiled, AetherReduxInit.ambrofusion.getId());
        this.tag(ARMOR_UPGRADES).addOptional(TCModifiers.integral, TCModifiers.ignitium);
        this.tag(GENERAL_UPGRADES).addOptional(TCModifiers.zanite, TCModifiers.skyjade, TCModifiers.warden);
        this.tag(DAMAGE_UPGRADES).addOptional(TCModifiers.dreadbane);
        this.tag(HARVEST_UPGRADES).addOptional(TCModifiers.spoiled);
        this.tag(INTERACTION_ABILITIES).addOptional(MalumInit.erosion.getId(), MalumInit.auricFlame.getId(), MalumInit.mnemonic.getId(), DarkerInit.sonorous.getId());
        this.tag(HELMET_ABILITIES).addOptional(TCModifiers.brightness);
        this.tag(RANGED_ABILITIES)
                .addOptional(MalumInit.erosion.getId(), MalumInit.auricFlame.getId(), MalumInit.mnemonic.getId(), DarkerInit.sonorous.getId(), SpeciesInit.swapping.getId())
                .addOptional(IFInit.flamed.getId(), IFInit.iced.getId(), IFInit.lightning.getId())
                .addOptional(TCModifiers.dampening);
        this.tag(MELEE_ABILITIES)
                .addOptional(MalumInit.deliverance.getId())
                .addOptional(IFInit.flamed.getId(), IFInit.iced.getId(), IFInit.lightning.getId())
                .addOptional(TCModifiers.dampening)
                .addOptional(SpeciesInit.birt.getId());
        this.tag(HARVEST_ABILITIES)
                .addOptional(BetternetherInit.rubysFire.getId())
                .addOptional(AetherInit.autochant.getId(), AetherInit.autofreeze.getId());
        this.tag(PROTECTION_DEFENSE)
                .addOptional(IFInit.scorchThorns.getId(), IFInit.frostThorns.getId(), IFInit.voltThorns.getId());
        this.tag(SPECIAL_DEFENSE)
                .addOptional(SpeciesInit.ricoshield.getId());
        this.tag(GENERAL_SLOTLESS).addOptional(TCModifiers.aetherForged);

        this.tag(RUNES).addTag(DEFENCE_RUNES, HARVEST_RUNES, COMBAT_RUNES, SPECIAL_RUNES);
        this.tag(DEFENCE_RUNES).addOptional(
                TCModifiers.runeOfAlimentCleansing, MalumInit.runeOfBolstering.getId(), TCModifiers.runeOfIdleRestoration,
                TCModifiers.runeOfIgneousSolace, TCModifiers.runeOfReactiveShielding, MalumInit.runeOfReinforcement.getId(),
                MalumInit.runeOfTheHells.getId(), TCModifiers.runeOfTheHeretic, MalumInit.runeOfToughness.getId(),
                MalumInit.runeOfWarding.getId()
        );
        this.tag(COMBAT_RUNES).addOptional(
                MalumInit.runeOfCulling.getId(), MalumInit.runeOfHaste.getId(), TCModifiers.runeOfSacrificialEmpowerment,
                MalumInit.runeOfSpellMastery.getId(), MalumInit.runeOfTheArena.getId(), TCModifiers.runeOfVolatileDistortion
        );
        this.tag(HARVEST_RUNES).addOptional(
                TCModifiers.runeOfFervor, MalumInit.runeOfHaste.getId()
        );
        this.tag(SPECIAL_RUNES).addOptional(
                MalumInit.runeOfDexterity.getId(), MalumInit.runeOfLoyalty.getId(), MalumInit.runeOfMotion.getId(),
                MalumInit.runeOfTheAether.getId(), MalumInit.runeOfTheSeas.getId(), TCModifiers.runeOfTwinnedDuration,
                MalumInit.runeOfUnnaturalStamina.getId()
        );
    }

    @Override
    public String getName() {
        return "Tinkers' Compatibility Modifier Tag Provider";
    }
}
