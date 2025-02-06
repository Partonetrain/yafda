package info.partonetrain.yafda;

import info.partonetrain.yafda.integration.AetherIntegration;
import info.partonetrain.yafda.integration.DeeperDarkerIntegration;
import info.partonetrain.yafda.integration.ThirstWasTakenIntegration;
import info.partonetrain.yafda.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.registry.ModCreativeTabs;
import vectorwing.farmersdelight.common.registry.ModItems;

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
        if(Services.PLATFORM.isModLoaded("aether")){
            AetherIntegration.load();
        }
        if(Services.PLATFORM.isModLoaded("thirst")){
            ThirstWasTakenIntegration.load();
        }

        eventBus.addListener(this::buildCreativeTabContants);
        ITEMS.register(eventBus);
        CommonClass.init();
    }

    public void buildCreativeTabContants(BuildCreativeModeTabContentsEvent event) {
        Constants.LOG.info(event.getTabKey().toString());
        if (event.getTab() == ModCreativeTabs.TAB_FARMERS_DELIGHT.get()) {
            for (DeferredHolder<Item, ? extends Item> i : YafdaNeoForge.ITEMS.getEntries()) {
                event.accept(i.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }
}