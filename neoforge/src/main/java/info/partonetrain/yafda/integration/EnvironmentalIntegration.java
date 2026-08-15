package info.partonetrain.yafda.integration;

import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaNeoForge;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import vectorwing.farmersdelight.common.item.HotCocoaItem;
import vectorwing.farmersdelight.common.registry.ModItems;

public class EnvironmentalIntegration {

    public static final FoodProperties CHERRY_PLUM_SHAKE_PROPS = (new FoodProperties.Builder()).nutrition(5).saturationModifier(0.4F).alwaysEdible().build();

    public static final DeferredHolder<Item, Item> CHERRY_PLUM_SHAKE = YafdaNeoForge.ITEMS.register(
            "cherry_plum_shake",
            () -> new HotCocoaItem(ModItems.drinkItem().food(CHERRY_PLUM_SHAKE_PROPS))
    );

    public static void load(){
        Constants.LOG.info("Environmental integration loaded");
    }
}
