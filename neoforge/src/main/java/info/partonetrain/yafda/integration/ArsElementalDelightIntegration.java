package info.partonetrain.yafda.integration;

import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaNeoForge;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import vectorwing.farmersdelight.common.item.FuelItem;

//Integration for Ars Elemental + Ars Delight
public class ArsElementalDelightIntegration {
    public static final DeferredHolder<Item, Item> FLASHING_BARK = YafdaNeoForge.ITEMS.register(
            "flashing_bark",
            () -> new FuelItem(new Item.Properties(), 200)
    );

    public static final DeferredHolder<Item, Item> FLASHPINE_TEA = YafdaNeoForge.ITEMS.register(
            "flashing_bark",
            () -> new FuelItem(new Item.Properties(), 200)
    );

    public static void load(){
        Constants.LOG.info("ArsElementalDelight integration loaded");
    }
}
