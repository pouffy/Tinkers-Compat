package com.pouffydev.tcompat.modifier.malum.rune;

//public class TotemicRuneRecipe implements ITinkerStationRecipe, IMultiRecipe<IDisplayModifierRecipe> {
//    protected static final String KEY_INVALID_RUNE = TCompat.makeTranslationKey("recipe", "modifier.totemic_rune.invalid_rune");
//
//    @Getter
//    private final ResourceLocation id;
//
//    public TotemicRuneRecipe(ResourceLocation id) {
//        this.id = id;
//        ModifierRecipeLookup.addRecipeModifier(null, TComModifiers.totemicRune);
//    }
//
//    private record RuneItems(ItemStack rune) {}
//
//    @Nullable
//    private static RuneItems findInputs(ITinkerStationContainer inv) {
//        ItemStack rune = ItemStack.EMPTY;
//        for (int i = 0; i < inv.getInputCount(); i++) {
//            ItemStack stack = inv.getInput(i);
//            if (!stack.isEmpty()) {
//                // find the two matching tags, but ensure no duplicates
//                if (stack.is(itemTag("malum:totemic_runes"))) {
//                    if (!rune.isEmpty()) {
//                        return null;
//                    }
//                    rune = stack;
//                }
//            }
//        }
//        if (!rune.isEmpty()) {
//            return new RuneItems(rune);
//        }
//        return null;
//    }
//
//    @Override
//    public boolean matches(ITinkerStationContainer inv, Level world) {
//        // ensure this modifier can be applied
//        boolean malumPresent = TCompat.isClassFound("com.sammy.malum.MalumMod");
//        if (!malumPresent) {
//            return false;
//        }
//        if (!inv.getTinkerableStack().is(TinkerTags.Items.CHESTPLATES)) {
//            return false;
//        }
//        // need to locate the rune
//        return findInputs(inv) != null;
//    }
//
//    @Override
//    public RecipeResult<LazyToolStack> getValidatedResult(ITinkerStationContainer inv, RegistryAccess access) {
//        // first need to find our rune instance
//        boolean malumPresent = TCompat.isClassFound("com.sammy.malum.MalumMod");
//        RuneItems runeItems = findInputs(inv);
//        // should never happen
//        if (runeItems == null || !malumPresent) {
//            return RecipeResult.pass();
//        }
//        Optional<TotemicRuneCurioItem> runeItem = getRune(runeItems.rune);
//        if (runeItem.isEmpty()) {
//            return RecipeResult.failure(KEY_INVALID_RUNE, runeItems.rune.getDisplayName());
//        }
//
//        ToolStack tool = inv.getTinkerable().copy();
//        ModDataNBT persistentData = tool.getPersistentData();
//        String mobEffect = Objects.requireNonNull(BuiltInRegistries.MOB_EFFECT.getKey(runeItem.get().mobEffectSupplier.get())).toString();
//        persistentData.putString(TotemicRuneModifier.TOTEMIC_RITE, mobEffect);
//
//        ModifierId modifier = TComModifiers.totemicRune.getId();
//        if (tool.getModifierLevel(modifier) == 0) {
//            tool.addModifier(modifier, 1);
//        }
//        return ITinkerStationRecipe.success(tool, inv);
//    }
//
//    private static Optional<TotemicRuneCurioItem> getRune(ItemStack stack) {
//        Item item = stack.getItem();
//        boolean malumPresent = TCompat.isClassFound("com.sammy.malum.MalumMod");
//        if (malumPresent && item instanceof TotemicRuneCurioItem totemicRuneCurioItem) {
//            return Optional.of(totemicRuneCurioItem);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public RecipeSerializer<?> getSerializer() {
//        return TComModifiers.totemicRuneSerializer.get();
//    }
//
//    private List<IDisplayModifierRecipe> displayRecipes = null;
//
//    public List<IDisplayModifierRecipe> getRecipes(RegistryAccess access) {
//        if (displayRecipes == null) {
//            List<ItemStack> runes = RegistryHelper.getTagValueStream(BuiltInRegistries.ITEM, itemTag("malum:totemic_runes"))
//                    .map(ItemStack::new).toList();
//            List<ItemStack> toolInputs = RegistryHelper.getTagValueStream(BuiltInRegistries.ITEM, TinkerTags.Items.CHESTPLATES)
//                    .map(IModifiableDisplay::getDisplayStack).toList();
//            displayRecipes = Collections.singletonList(new DisplayRecipe(toolInputs, runes));
//        }
//        return displayRecipes;
//    }
//
//    private static class DisplayRecipe implements IDisplayModifierRecipe {
//        private static final IntRange LEVELS = new IntRange(1, 1);
//        private final ModifierEntry RESULT = new ModifierEntry(TComModifiers.totemicRune, 1);
//
//        @Getter
//        private final List<ItemStack> toolWithoutModifier;
//        @Getter
//        private final List<ItemStack> toolWithModifier;
//        private final List<ItemStack> rune;
//
//        public DisplayRecipe(List<ItemStack> tools, List<ItemStack> rune) {
//            toolWithoutModifier = tools;
//            this.rune = rune;
//
//            List<ModifierEntry> results = List.of(RESULT);
//            toolWithModifier = tools.stream().map(stack -> IDisplayModifierRecipe.withModifiers(stack, results, data -> {})).toList();
//
//        }
//
//        @Override
//        public int getInputCount() {
//            return 1;
//        }
//
//        @Override
//        public List<ItemStack> getDisplayItems(int slot) {
//            return switch (slot) {
//                case 0 -> rune;
//                default -> List.of();
//            };
//        }
//
//        @Override
//        public ModifierEntry getDisplayResult() {
//            return RESULT;
//        }
//
//        @Override
//        public IntRange getLevel() {
//            return LEVELS;
//        }
//    }
//
//    private static TagKey<Item> itemTag(String name) {
//        return TagKey.create(Registries.ITEM, new ResourceLocation(name));
//    }
//}
