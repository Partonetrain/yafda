package info.partonetrain.yafda.integration;

import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.platform.Services;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

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
                event.addDrink(BrewinAndChewinIntegration.bloomBrandyTankard.get(), 10, 14);
            }
        }

        if(Services.PLATFORM.isModLoaded("arsdelight") && Services.PLATFORM.isModLoaded("ars_elemental")){
            ArtifactVersion arsdelightVersion = ModList.get().getModContainerById("arsdelight").get().getModInfo().getVersion();
            final ArtifactVersion MAX_ARS_DELIGHT_VERSION_FOR_ORIGINAL_COMPAT = new DefaultArtifactVersion("2.2.1");
            if(!(arsdelightVersion.compareTo(MAX_ARS_DELIGHT_VERSION_FOR_ORIGINAL_COMPAT) > 0)) {
                event.addDrink(ArsElementalDelightIntegration.FLASHPINE_TEA.get(), 8, 13);
                event.addDrink(ArsElementalDelightIntegration.FLASHPINE_HORNBEER.get(), 8, 13);
            }
        }

        if(Services.PLATFORM.isModLoaded("environmental")){
            event.addDrink(EnvironmentalIntegration.CHERRY_PLUM_SHAKE.get(), 6, 8);
        }
    }
}
