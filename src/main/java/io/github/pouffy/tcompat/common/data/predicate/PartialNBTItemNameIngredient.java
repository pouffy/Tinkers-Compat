package io.github.pouffy.tcompat.common.data.predicate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.crafting.PartialNBTIngredient;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.recipe.data.ItemNameIngredient;

import java.util.Arrays;
import java.util.List;

public class PartialNBTItemNameIngredient extends AbstractIngredient {
    private final List<ResourceLocation> names;
    private final CompoundTag nbt;

    protected PartialNBTItemNameIngredient(List<ResourceLocation> names, CompoundTag nbt) {
        super(names.stream().map(ItemNameIngredient.NamedValue::new));
        this.names = names;
        this.nbt = nbt;
    }

    public static PartialNBTItemNameIngredient from(List<ResourceLocation> names, CompoundTag nbt) {
        return new PartialNBTItemNameIngredient(names, nbt);
    }

    public static PartialNBTItemNameIngredient from(CompoundTag nbt, ResourceLocation... names) {
        return new PartialNBTItemNameIngredient(Arrays.asList(names), nbt);
    }

    public boolean test(@Nullable ItemStack input) {
        throw new UnsupportedOperationException();
    }

    public boolean isSimple() {
        return false;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return PartialNBTIngredient.Serializer.INSTANCE;
    }

    private static void forName(JsonObject json, ResourceLocation name) {
        json.addProperty("item", name.toString());
    }

    public JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "forge:partial_nbt");
        if (this.names.size() == 1) {
            forName(json, this.names.get(0));
        } else {
            JsonArray array = new JsonArray();

            for(ResourceLocation name : this.names) {
                array.add(name.toString());
            }
            json.add("items", array);
        }
        json.addProperty("nbt", this.nbt.toString());
        return json;
    }
}
