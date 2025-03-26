package info.partonetrain.yafda;

import info.partonetrain.yafda.integration.*;
import info.partonetrain.yafda.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import vectorwing.farmersdelight.common.registry.ModCreativeTabs;
import vectorwing.farmersdelight.common.registry.ModItems;

@Mod(Constants.MOD_ID)
public class YafdaNeoForge {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            BuiltInRegistries.ITEM,
            Constants.MOD_ID
    );
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(
            BuiltInRegistries.FLUID,
            Constants.MOD_ID
    );

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(
            NeoForgeRegistries.Keys.FLUID_TYPES,
            Constants.MOD_ID
    );

    public YafdaNeoForge(IEventBus eventBus) {

        if(Services.PLATFORM.isModLoaded("brewinandchewin")){
            BrewinAndChewinIntegration.load();
        }
        if(Services.PLATFORM.isModLoaded("deeperdarker")){
            DeeperDarkerIntegration.load();
        }
        if(Services.PLATFORM.isModLoaded("aether")){
            AetherIntegration.load();
            if(Services.PLATFORM.isModLoaded("deep_aether")) {
                DeepAetherIntegration.load();
            }
        }
        if(Services.PLATFORM.isModLoaded("thirst")){
            ThirstWasTakenIntegration.load();
        }

        eventBus.addListener(this::buildCreativeTabContants);
        ITEMS.register(eventBus);
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
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