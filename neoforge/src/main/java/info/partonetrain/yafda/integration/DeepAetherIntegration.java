package info.partonetrain.yafda.integration;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaNeoForge;
import info.partonetrain.yafda.item.YafdaKnifeItem;
import io.github.razordevs.deep_aether.init.DATiers;
import io.github.razordevs.deep_aether.item.gear.skyjade.SkyjadeWeapon;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.registries.DeferredHolder;
import vectorwing.farmersdelight.common.item.HotCocoaItem;
import vectorwing.farmersdelight.common.registry.ModItems;

public class DeepAetherIntegration {
    public static final DeferredHolder<Item, Item> SKYJADE_KNIFE = YafdaNeoForge.ITEMS.register(
            "skyjade_knife",
            () -> new SkyjadeKnifeItem(DATiers.SKYJADE)
    );
    public static final DeferredHolder<Item, Item> STRATUS_KNIFE = YafdaNeoForge.ITEMS.register(
            "stratus_knife",
            () -> new AetherIntegration.GravititeKnifeItem(DATiers.STRATUS)
    );

    public static void load(){
        Constants.LOG.info("Deep Aether integration loaded");
    }

    public static class SkyjadeKnifeItem extends YafdaKnifeItem implements SkyjadeWeapon{
        public SkyjadeKnifeItem(Tier tier) {
            super(tier);
        }
    }
}
