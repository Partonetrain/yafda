package info.partonetrain.yafda.integration;

import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaNeoForge;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.HotCocoaItem;
import vectorwing.farmersdelight.common.registry.ModItems;

public class EnvironmentalIntegration {

    public static final FoodProperties CHERRY_PLUM_SHAKE_PROPS = (new FoodProperties.Builder()).nutrition(5).saturationModifier(0.4F).alwaysEdible().build();

    public static final FoodProperties RAW_VENISON_SLICE_PROPS = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.2F).build();
    public static final FoodProperties COOKED_VENISON_SLICE_PROPS = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.8F).build();

    public static final DeferredHolder<Item, Item> CHERRY_PLUM_SHAKE = YafdaNeoForge.ITEMS.register(
            "cherry_plum_shake",
            () -> new HotCocoaItem(ModItems.drinkItem().food(CHERRY_PLUM_SHAKE_PROPS))
    );

    public static final DeferredHolder<Item, Item> RAW_VENISON_SLICE = YafdaNeoForge.ITEMS.register(
            "raw_venison_slice",
            () -> new ConsumableItem(ModItems.foodItem(RAW_VENISON_SLICE_PROPS))
    );
    public static final DeferredHolder<Item, Item> COOKED_VENISON_SLICE = YafdaNeoForge.ITEMS.register(
            "cooked_venison_slice",
            () -> new ConsumableItem(ModItems.foodItem(COOKED_VENISON_SLICE_PROPS))
    );

    public static void load(){
        Constants.LOG.info("Environmental integration loaded");
    }
}
