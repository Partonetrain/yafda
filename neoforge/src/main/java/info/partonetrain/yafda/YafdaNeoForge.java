package info.partonetrain.yafda;


import info.partonetrain.yafda.integration.DeeperDarkerIntegration;
import info.partonetrain.yafda.item.YafdaKnifeItem;
import info.partonetrain.yafda.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.item.KnifeItem;

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
}