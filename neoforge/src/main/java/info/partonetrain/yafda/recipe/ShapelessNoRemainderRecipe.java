package info.partonetrain.yafda.recipe;

import info.partonetrain.yafda.YafdaNeoForge;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

public class ShapelessNoRemainderRecipe extends ShapelessRecipe {

    public ShapelessNoRemainderRecipe(String group, CraftingBookCategory category, ItemStack result, NonNullList<Ingredient> ingredients) {
        super(group, category, result, ingredients);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        return NonNullList.withSize(input.size(), ItemStack.EMPTY);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return YafdaNeoForge.SHAPELESS_NO_REMAINDER_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return YafdaNeoForge.SHAPELESS_NO_REMAINDER.get();
    }
}
