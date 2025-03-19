package info.partonetrain.yafda.integration;

import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.platform.Services;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;

public class ThirstWasTakenIntegration {

    public static void load(){
        NeoForge.EVENT_BUS.register(ThirstWasTakenIntegration.class);
        Constants.LOG.info("ThirstWasTaken integration loaded");
    }

    @SubscribeEvent
    public static void compat(RegisterThirstValueEvent event) {
        if(Services.PLATFORM.isModLoaded("aether")){
            event.addDrink(AetherIntegration.SKY_BLUE_SHAKE.get(), 6, 8);
        }

        if(Services.PLATFORM.isModLoaded("brewinandchewin")){
            if(Services.PLATFORM.isModLoaded("deeperdarker")){
                event.addDrink(BrewinAndChewinIntegration.bloomBrandyItem.get(), 10, 14);
            }
        }
    }
}
