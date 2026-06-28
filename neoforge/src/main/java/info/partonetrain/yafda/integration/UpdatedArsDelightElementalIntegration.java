package info.partonetrain.yafda.integration;

import dev.xkmc.arsdelight.compat.elemental.ElementalCompat;
import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaNeoForge;
import info.partonetrain.yafda.item.ConsumableEffectItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class UpdatedArsDelightElementalIntegration {

    public static final FoodProperties FLASHPINE_FISH_PROPERTIES = (new FoodProperties.Builder()).nutrition(10).saturationModifier(0.8F)
            .effect(
                    () -> {
                        return new MobEffectInstance(MobEffects.NIGHT_VISION, 2400, 0);
                    }, 1.0F)
            .effect(
                    () -> {
                        return new MobEffectInstance(ElementalCompat.LIGHTNING_CURSE, 2400, 0);
                    }, 1.0F)
            .effect(() -> {
                return new MobEffectInstance(ModEffects.NOURISHMENT, 3600, 0);
            }, 1.0F).build();

    public static final DeferredHolder<Item, Item> FLASHPINE_FISH = YafdaNeoForge.ITEMS.register(
            "flashpine_fish",
            () -> new ConsumableEffectItem(vectorwing.farmersdelight.common.registry.ModItems.bowlFoodItem(FLASHPINE_FISH_PROPERTIES))
    ); //not deprecated

    public static void load() {
        Constants.LOG.info("UpdatedArsDelightElementalIntegration integration loaded (for arsdelight >2.2.1)");
    }
}
