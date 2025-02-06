package info.partonetrain.yafda.integration;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.kyanite.deeperdarker.util.DDTiers;
import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaNeoForge;
import info.partonetrain.yafda.item.YafdaKnifeItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import vectorwing.farmersdelight.common.item.HotCocoaItem;
import vectorwing.farmersdelight.common.registry.ModItems;

public class AetherIntegration {
    public static final DeferredHolder<Item, Item> HOLYSTONE_KNIFE = YafdaNeoForge.ITEMS.register(
            "holystone_knife",
            () -> new YafdaKnifeItem(AetherItemTiers.HOLYSTONE)
    );
    public static final DeferredHolder<Item, Item> ZANITE_KNIFE = YafdaNeoForge.ITEMS.register(
            "zanite_knife",
            () -> new YafdaKnifeItem(AetherItemTiers.ZANITE)
    );
    public static final DeferredHolder<Item, Item> GRAVITITE_KNIFE = YafdaNeoForge.ITEMS.register(
            "gravitite_knife",
            () -> new YafdaKnifeItem(AetherItemTiers.GRAVITITE)
    );
    public static final FoodProperties SKY_BLUE_SHAKE_PROPERTIES = (new FoodProperties.Builder()).nutrition(5).saturationModifier(0.4F).alwaysEdible().build();

    public static final DeferredHolder<Item, Item> SKY_BLUE_SHAKE = YafdaNeoForge.ITEMS.register(
            "sky_blue_shake",
            () -> new HotCocoaItem(ModItems.drinkItem().food(SKY_BLUE_SHAKE_PROPERTIES))
    );

    public static void load(){
        Constants.LOG.info("Aether integration loaded");
    }
}
