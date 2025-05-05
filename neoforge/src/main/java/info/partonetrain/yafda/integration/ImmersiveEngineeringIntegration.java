package info.partonetrain.yafda.integration;

import blusunrize.immersiveengineering.api.Lib;
import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaNeoForge;
import info.partonetrain.yafda.item.YafdaKnifeItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ImmersiveEngineeringIntegration {

    public static final DeferredHolder<Item, Item> STEEL_KNIFE = YafdaNeoForge.ITEMS.register(
            "steel_knife",
            () -> new YafdaKnifeItem(Lib.MATERIAL_Steel)
    );
    public static void load() {
        Constants.LOG.info("ImmersiveEngineering integration loaded");
    }
}
