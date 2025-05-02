package info.partonetrain.yafda.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.HoneyBottleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.function.Supplier;

import static vectorwing.farmersdelight.common.registry.ModItems.drinkItem;

public class ConsumableEffectDrinkItem extends DrinkableItem {
    boolean makesHoneySound = false;
    public ConsumableEffectDrinkItem(FoodProperties properties, boolean makesHoneySound) {
        super(drinkItem().food(properties), true);
        this.makesHoneySound = makesHoneySound;
    }

    public ConsumableEffectDrinkItem(FoodProperties properties, boolean makesHoneySound, Item container) {
        super(drinkItem().food(properties).craftRemainder(container), true);
        this.makesHoneySound = makesHoneySound;
    }

    //not sure where this method is used
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return this.getFoodProperties(stack, entity).eatDurationTicks();
    }

    @Override
    public @NotNull SoundEvent getDrinkingSound() {
        return makesHoneySound ? SoundEvents.HONEY_DRINK : SoundEvents.GENERIC_DRINK;
    }

}
