package info.partonetrain.yafda.integration;

import divinerpg.enums.ToolStats;
import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaNeoForge;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.Unbreakable;
import net.neoforged.neoforge.registries.DeferredHolder;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.registry.ModItems;

public class DivineRPGIntegration {

    public static final DeferredHolder<Item, Item> DIVINE_KNIFE = YafdaNeoForge.ITEMS.register(
            "divine_knife",
            () -> new DivineKnifeItem(ToolStats.DIVINE_SHICKAXE)
    );
    public static final DeferredHolder<Item, Item> EMPOWERED_MEAT_SLICE = YafdaNeoForge.ITEMS.register(
            "empowered_meat_slice",() -> new ConsumableItem(ModItems.foodItem(FoodValues.MINCED_BEEF)
            ));
    public static final DeferredHolder<Item, Item> COOKED_EMPOWERED_MEAT_SLICE = YafdaNeoForge.ITEMS.register(
            "cooked_empowered_meat_slice",() -> new ConsumableItem(ModItems.foodItem(FoodValues.BEEF_PATTY)
            ));

//    public static final FoodProperties EVERYTHING_BAGEL_PROPERTIES = (new FoodProperties.Builder()).nutrition(14).saturationModifier(0.75F).effect(() -> {
//        return FoodValues.nourishment(6000);
//    }, 1.0F).effect(() -> {
//        return new MobEffectInstance(MobEffects.ABSORPTION, 6000, 2, false, false);
//    }, 1.0F).effect(() -> {
//        return new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 0, false, false);
//    }, 1.0F).alwaysEdible().build();
//
//    public static final DeferredHolder<Item, Item> EVERYTHING_BAGEL = YafdaNeoForge.ITEMS.register(
//            "everything_bagel",() -> new ConsumableItem(ModItems.foodItem(EVERYTHING_BAGEL_PROPERTIES).rarity(Rarity.UNCOMMON)
//            , true));

    public static void load() {
        Constants.LOG.info("DivineRPG integration loaded");
    }

    public static class DivineKnifeItem extends KnifeItem {
        public DivineKnifeItem(Tier tier) {
            super(tier, CreateKnifeProperties(tier));
        }

        public static Item.Properties CreateKnifeProperties(Tier tier) {
            return (new Item.Properties().component(DataComponents.UNBREAKABLE, new Unbreakable(true))).attributes(KnifeItem.createAttributes(tier, -1.0F, -2.0F));
        }
    }
}
