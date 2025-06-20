package info.partonetrain.yafda.integration;

import info.partonetrain.yafda.YafdaNeoForge;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.registry.ModItems;

public class MalumIntegration {

    public static final FoodProperties SUS_SAP_PROPERTIES = (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.6F).build();

    public static final DeferredHolder<Item, Item> SUSPISCIOUS_SAP = YafdaNeoForge.ITEMS.register(
            "suspicious_sap",
            () -> new SusSapItem(ModItems.drinkItem().food(SUS_SAP_PROPERTIES))

    );



    public static void load() {
        System.out.println("Malum integration loaded");
    }

    public static class SusSapItem extends SuspiciousStewItem {
        public SusSapItem(Properties properties) {
            super(properties);
        }

        @Override
        public int getUseDuration(ItemStack pStack, LivingEntity livingEntity) {
            return 60;
        }

        @Override
        public UseAnim getUseAnimation(ItemStack pStack) {
            return UseAnim.DRINK;
        }

        @Override
        public SoundEvent getDrinkingSound() {
            return SoundEvents.HONEY_DRINK;
        }

        @Override
        public SoundEvent getEatingSound() {
            return SoundEvents.HONEY_DRINK;
        }

        @Override
        public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
            return Items.GLASS_BOTTLE.getDefaultInstance();
        }

        @Override
        public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
            super.finishUsingItem(stack, level, entityLiving);
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            } else {
                if (entityLiving instanceof Player player && !player.hasInfiniteMaterials()) {
                    ItemStack itemstack = new ItemStack(Items.GLASS_BOTTLE);
                    if (!player.getInventory().add(itemstack)) {
                        player.drop(itemstack, false);
                    }
                }
            }
            return stack;
        }

    }
}
