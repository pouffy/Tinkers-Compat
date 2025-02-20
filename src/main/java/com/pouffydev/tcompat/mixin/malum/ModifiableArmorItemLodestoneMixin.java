package com.pouffydev.tcompat.mixin.malum;

//@Mixin(ModifiableArmorItem.class)
//public class ModifiableArmorItemLodestoneMixin implements ParticleEmitterHandler.ItemParticleSupplier {
//
//
//    @OnlyIn(Dist.CLIENT)
//    @Override
//    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
//        IToolStackView toolStackView = ToolStack.from(stack);
//        String riteEffect = ModifierUtil.getPersistentString(stack, TotemicRuneModifier.TOTEMIC_RITE);
//        ResourceLocation riteEffectLoc = new ResourceLocation(riteEffect);
//        MalumSpiritType spiritType = switch (riteEffectLoc.getPath()) {
//            case "zephyrs_courage", "aethers_charm" -> SpiritTypeRegistry.AERIAL_SPIRIT;
//            case "poseidons_grasp", "anglers_lure" -> SpiritTypeRegistry.AQUEOUS_SPIRIT;
//            case "gaias_bulwark", "earthen_might" -> SpiritTypeRegistry.EARTHEN_SPIRIT;
//            case "miners_rage", "ifrits_embrace" -> SpiritTypeRegistry.INFERNAL_SPIRIT;
//            default -> SpiritTypeRegistry.UMBRAL_SPIRIT;
//        };
//        if (toolStackView.getModifierLevel(TComModifiers.totemicRune.get()) > -1)
//            ScreenParticleEffects.spawnRuneParticles(target, spiritType);
//    }
//}
