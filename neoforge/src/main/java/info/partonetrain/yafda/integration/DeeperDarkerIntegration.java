package info.partonetrain.yafda.integration;

import com.kyanite.deeperdarker.content.DDEffects;
import com.kyanite.deeperdarker.util.DDTiers;
import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaNeoForge;
import info.partonetrain.yafda.item.YafdaKnifeItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.EffectCures;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModItems;

public class DeeperDarkerIntegration {
    public static final DeferredHolder<Item, Item> RESONARIUM_KNIFE = YafdaNeoForge.ITEMS.register(
            "resonarium_knife",
            () -> new YafdaKnifeItem(DDTiers.RESONARIUM)
    );
    public static final DeferredHolder<Item, Item> WARDEN_KNIFE = YafdaNeoForge.ITEMS.register(
            "warden_knife",
            () -> new YafdaKnifeItem(DDTiers.WARDEN)
    );

    public static final FoodProperties SOULFFLE_PROPERTIES = (new FoodProperties.Builder()).nutrition(10).saturationModifier(0.6F).alwaysEdible().effect(() -> {
        return new MobEffectInstance(DDEffects.SCULK_AFFINITY, 1200, 0);
    }, 1.0F).build();
    public static final DeferredHolder<Item, Item> SOULFFLE = YafdaNeoForge.ITEMS.register(
            "soulffle",
            () -> new SoulffleItem(ModItems.bowlFoodItem(SOULFFLE_PROPERTIES))
    );

    public static void load(){
        Constants.LOG.info("DeeperDarker integration loaded");
    }

    public static class SoulffleItem extends ConsumableItem{
        public SoulffleItem(Properties properties) {
            super(properties, true, true);
        }
        @Override
        public void affectConsumer(@NotNull ItemStack stack, @NotNull Level level, LivingEntity consumer) {
            consumer.removeEffectsCuredBy(EffectCures.HONEY);
        }
    }
}
