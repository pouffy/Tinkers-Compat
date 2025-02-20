package com.pouffydev.tcompat.mixin.malum;

import com.pouffydev.tcompat.modifier.TComModifiers;
import com.pouffydev.tcompat.modifier.malum.rune.TotemicRuneModifier;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.visual_effects.ScreenParticleEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.item.armor.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import team.lodestar.lodestone.handlers.screenparticle.ParticleEmitterHandler;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleHolder;

@Mixin(ModifiableArmorItem.class)
public class ModifiableArmorItemLodestoneMixin implements ParticleEmitterHandler.ItemParticleSupplier {


    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        IToolStackView toolStackView = ToolStack.from(stack);
        String riteEffect = ModifierUtil.getPersistentString(stack, TotemicRuneModifier.TOTEMIC_RITE);
        ResourceLocation riteEffectLoc = new ResourceLocation(riteEffect);
        MalumSpiritType spiritType = switch (riteEffectLoc.getPath()) {
            case "zephyrs_courage", "aethers_charm" -> SpiritTypeRegistry.AERIAL_SPIRIT;
            case "poseidons_grasp", "anglers_lure" -> SpiritTypeRegistry.AQUEOUS_SPIRIT;
            case "gaias_bulwark", "earthen_might" -> SpiritTypeRegistry.EARTHEN_SPIRIT;
            case "miners_rage", "ifrits_embrace" -> SpiritTypeRegistry.INFERNAL_SPIRIT;
            default -> SpiritTypeRegistry.UMBRAL_SPIRIT;
        };
        if (toolStackView.getModifierLevel(TComModifiers.totemicRune.get()) > -1)
            ScreenParticleEffects.spawnRuneParticles(target, spiritType);
    }
}
