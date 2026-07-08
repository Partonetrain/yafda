package info.partonetrain.yafda;

import info.partonetrain.yafda.integration.*;
import info.partonetrain.yafda.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import vectorwing.farmersdelight.common.registry.ModCreativeTabs;

@Mod(Constants.MOD_ID)
@EventBusSubscriber(modid = Constants.MOD_ID)
public class YafdaNeoForge {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            BuiltInRegistries.ITEM,
            Constants.MOD_ID
    );
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            BuiltInRegistries.BLOCK,
            Constants.MOD_ID
    );
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
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
        if(Services.PLATFORM.isModLoaded("immersiveengineering")){
            ImmersiveEngineeringIntegration.load();
        }
        if(Services.PLATFORM.isModLoaded("malum")){
            MalumIntegration.load();
        }
        if(Services.PLATFORM.isModLoaded("divinerpg")){
            DivineRPGIntegration.load();
        }
        if(Services.PLATFORM.isModLoaded("millenaire")){
            MillenaireIntegration.load();
        }

        if(Services.PLATFORM.isModLoaded("ars_elemental")){
            if(ModList.get().getModContainerById("arsdelight").isPresent()){
                ArtifactVersion arsdelightVersion = ModList.get().getModContainerById("arsdelight").get().getModInfo().getVersion();
                final ArtifactVersion MAX_ARS_DELIGHT_VERSION_FOR_ORIGINAL_COMPAT = new DefaultArtifactVersion("2.2.1");
                if(arsdelightVersion.compareTo(MAX_ARS_DELIGHT_VERSION_FOR_ORIGINAL_COMPAT) > 0){ //if the version is greater than 2.2.1
                    UpdatedArsDelightElementalIntegration.load();
                }
                else{
                    ArsElementalDelightIntegration.load();
                }
            }

        }

        eventBus.addListener(this::buildCreativeTabContants);
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
        BLOCK_ENTITY_TYPES.register(eventBus);
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
        CommonClass.init();
    }

    public void buildCreativeTabContants(BuildCreativeModeTabContentsEvent event) {
        //Constants.LOG.info(event.getTabKey().toString());
        if (event.getTab() == ModCreativeTabs.TAB_FARMERS_DELIGHT.get()) {
            for (DeferredHolder<Item, ? extends Item> i : YafdaNeoForge.ITEMS.getEntries()) {
                event.accept(i.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        if(Services.PLATFORM.isModLoaded("millenaire")){
            MillenaireIntegration.deployCustomFolder(event.getServer());
        }
    }
}