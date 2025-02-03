package info.partonetrain.yafda;

import info.partonetrain.yafda.integration.DeeperDarkerIntegration;
import info.partonetrain.yafda.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.registry.ModCreativeTabs;

@Mod(Constants.MOD_ID)
public class YafdaNeoForge {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            BuiltInRegistries.ITEM,
            Constants.MOD_ID
    );

    public YafdaNeoForge(IEventBus eventBus) {

        if(Services.PLATFORM.isModLoaded("deeperdarker")){
            DeeperDarkerIntegration.load();
        }

        ITEMS.register(eventBus);
        CommonClass.init();

    }

    @SubscribeEvent
    public static void buildCreativeTabContants(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == ModCreativeTabs.TAB_FARMERS_DELIGHT) {
            for (DeferredHolder<Item, ? extends Item> i : ITEMS.getEntries()) {
                event.accept(i.get());
            }
        }
    }
}