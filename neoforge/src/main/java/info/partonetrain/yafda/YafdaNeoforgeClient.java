package info.partonetrain.yafda;

import dev.xkmc.arsdelight.content.jelly.JellyBlockEntityRenderer;
import info.partonetrain.yafda.integration.ArsElementalDelightIntegration;
import info.partonetrain.yafda.platform.Services;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class YafdaNeoforgeClient {

    public YafdaNeoforgeClient(IEventBus modEventBus, ModContainer container) {
        modEventBus.addListener(this::registerEntityRenderers);
    }

    public void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        if(Services.PLATFORM.isModLoaded("ars_elemental") && Services.PLATFORM.isModLoaded("arsdelight")) {
            event.registerBlockEntityRenderer(
                    ArsElementalDelightIntegration.FLASHPINE_JELLY_BE.get(),
                    JellyBlockEntityRenderer::new
            );
        }
    }
}
