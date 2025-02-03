package info.partonetrain.yafda.integration;

import com.kyanite.deeperdarker.util.DDTiers;
import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.item.YafdaKnifeItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import vectorwing.farmersdelight.common.registry.ModCreativeTabs;

public class DeeperDarkerIntegration {
    public static final YafdaKnifeItem RESONARIUM_KNIFE = registerItem(
            "resonarium_knife",
            new YafdaKnifeItem(DDTiers.RESONARIUM)
    );
    public static final YafdaKnifeItem WARDEN_KNIFE = registerItem(
            "warden_knife",
            new YafdaKnifeItem(DDTiers.WARDEN)
    );

    public static void load(){
        Constants.LOG.info("DeeperDarker integration loaded");
    }

    public static <T extends Item> T registerItem(String name, T item){
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.parse("farmersdelight"))).register(entries -> entries.accept(item));
        return item;
    }
}
