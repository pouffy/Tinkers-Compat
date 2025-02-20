package com.pouffydev.tcompat.modifier.malum.rune;

//@RequiredArgsConstructor
//public class TotemicRuneModifierModel implements IBakedModifierModel {
//
//    public static final IUnbakedModifierModel UNBAKED_INSTANCE = new Unbaked(-1, 0);
//
//    /** Textures to show */
//    @Nullable
//    private final Material small;
//    @Nullable
//    private final Material large;
//    /** Color to apply to the texture */
//    private final int color;
//    /** Luminosity to apply to the texture */
//    private final int luminosity;
//
//    public TotemicRuneModifierModel(@Nullable Material smallTexture, @Nullable Material largeTexture) {
//        this(smallTexture, largeTexture, -1, 0);
//    }
//
//    @Override
//    public void addQuads(IToolStackView tool, ModifierEntry entry, Function<Material,TextureAtlasSprite> spriteGetter, Transformation transforms, boolean isLarge, int startTintIndex, Consumer<Collection<BakedQuad>> quadConsumer, @Nullable ItemLayerPixels pixels) {
//        String runeEffectId = tool.getPersistentData().getString(TotemicRuneModifier.TOTEMIC_RITE);
//        Material spriteLarge = new Material(large.atlasLocation().withSuffix(getRiteSprite(runeEffectId)), large.texture().withSuffix(getRiteSprite(runeEffectId)));
//        Material spriteSmall = new Material(small.atlasLocation().withSuffix(getRiteSprite(runeEffectId)), small.texture().withSuffix(getRiteSprite(runeEffectId)));
//
//        Material spriteName = isLarge ? spriteLarge : spriteSmall;
//        if (spriteName != null) {
//            quadConsumer.accept(MantleItemLayerModel.getQuadsForSprite(color, -1, spriteGetter.apply(spriteName), transforms, luminosity, pixels));
//        }
//    }
//
//    private String getRiteSprite(String runeEffectId) {
//        Holder.Reference<MobEffect> toTest = BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(ResourceKey.create(Registries.MOB_EFFECT, new ResourceLocation(runeEffectId)));
//        StringBuilder toReturn = new StringBuilder("/");
//        for (TotemicRiteType riteType : SpiritRiteRegistry.RITES) {
//            if (!(riteType.effect instanceof PotionRiteEffect potionRiteEffect) || !(riteType.corruptedEffect instanceof PotionRiteEffect potionRiteCorruptedEffect)) {
//                throw new IllegalArgumentException("Supplied rite type must have an aura effect");
//            }
//            boolean isRite = toTest.equals(potionRiteEffect.mobEffectSupplier);
//            boolean isCorruptedRite = toTest.equals(potionRiteCorruptedEffect.mobEffectSupplier);
//            if (isRite || isCorruptedRite) {
//                toReturn.append(riteType.identifier);
//                if (isCorruptedRite) toReturn.append("_corrupted");
//            }
//        }
//        return toReturn.toString().equals("/") ? "" : toReturn.toString();
//    }
//
//    private record Unbaked(int color, int luminosity) implements IUnbakedModifierModel {
//        @Nullable
//        @Override
//        public IBakedModifierModel forTool(Function<String,Material> smallGetter, Function<String,Material> largeGetter) {
//            Material smallTexture = smallGetter.apply("");
//            Material largeTexture = largeGetter.apply("");
//            if (smallTexture != null || largeTexture != null) {
//                return new TotemicRuneModifierModel(smallTexture, largeTexture, color, luminosity);
//            }
//            return null;
//        }
//
//        @Override
//        public IUnbakedModifierModel configure(JsonObject data) {
//            // parse the two keys, if we ended up with something new create an instance
//            int color = ColorLoadable.ALPHA.getOrDefault(data, "color", -1);
//            int luminosity = GsonHelper.getAsInt(data, "luminosity");
//            if (color != this.color || luminosity != this.luminosity) {
//                return new Unbaked(color, luminosity);
//            }
//            return this;
//        }
//    }
//}
