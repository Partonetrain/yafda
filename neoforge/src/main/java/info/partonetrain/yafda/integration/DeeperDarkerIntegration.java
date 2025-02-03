package info.partonetrain.yafda.integration;

import com.kyanite.deeperdarker.util.DDTiers;
import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaNeoForge;
import info.partonetrain.yafda.item.YafdaKnifeItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DeeperDarkerIntegration {
    public static final DeferredHolder<Item, Item> RESONARIUM_KNIFE = YafdaNeoForge.ITEMS.register(
            "resonarium_knife",
            () -> new YafdaKnifeItem(DDTiers.RESONARIUM)
    );
    public static final DeferredHolder<Item, Item> WARDEN_KNIFE = YafdaNeoForge.ITEMS.register(
            "warden_knife",
            () -> new YafdaKnifeItem(DDTiers.WARDEN)
    );

    public static void load(){
        Constants.LOG.info("DeeperDarker integration loaded");
    }
}
