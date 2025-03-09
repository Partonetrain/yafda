package info.partonetrain.yafda.integration;

import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaFluidType;
import info.partonetrain.yafda.YafdaNeoForge;
import info.partonetrain.yafda.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import umpaz.brewinandchewin.common.item.BoozeItem;
import umpaz.brewinandchewin.common.item.JamJarItem;
import umpaz.brewinandchewin.common.registry.BnCEffects;
import umpaz.brewinandchewin.common.registry.BnCFluids;
import umpaz.brewinandchewin.common.registry.BnCFoods;
import umpaz.brewinandchewin.common.registry.BnCItems;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class BrewinAndChewinIntegration {

    public static final int BLOOM_BRANDY_COLOR = 0xFFFBB117;
    public static FlowingFluid BLOOMING_BRANDY = null;
    public static FlowingFluid FLOWING_BLOOMING_BRANDY = null;

    static {
        if(Services.PLATFORM.isModLoaded("aether")){
            final DeferredHolder<Item, Item> AETHER_BLUE_BERRY_JAM = YafdaNeoForge.ITEMS.register(
                    "blue_berry_jam",
                    () -> new JamJarItem((new Item.Properties()).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE).food(BnCFoods.SWEET_BERRY_JAM))
            );
        }
        if(Services.PLATFORM.isModLoaded("deeperdarker")){

//            final FluidType BLOOM_BRANDY_FLUID_TYPE = YafdaNeoForge.FLUID_TYPES.register("bloom_brandy",  new YafdaFluidType());
//            final BaseFlowingFluid.Properties BLOOM_BRANDY_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(() -> BLOOM_BRANDY_FLUID_TYPE, () -> BLOOMING_BRANDY, () -> FLOWING_BLOOMING_BRANDY);
//
//            BLOOMING_BRANDY = new BaseFlowingFluid.Source(BLOOM_BRANDY_FLUID_PROPERTIES);
//            FLOWING_BLOOMING_BRANDY = new BaseFlowingFluid.Source(BLOOM_BRANDY_FLUID_PROPERTIES);
//
//            final DeferredHolder<Ite m, Item> DEEPERDARKER_BLOOM_BRANDY = YafdaNeoForge.ITEMS.register(
//                    "bloom_brandy",
//                    () -> new BoozeItem(() -> BLOOMING_BRANDY, new Item.Properties()
//                            .stacksTo(16).craftRemainder(BnCItems.TANKARD).food(
//                                    new FoodProperties.Builder()
//                                .effect(new MobEffectInstance(BnCEffects.TIPSY, 2400, 0), 1.0F)
//                                .effect(new MobEffectInstance(BnCEffects.INTOXICATION, 1800, 0, false, false), 1.0F)
//                                .effect(new MobEffectInstance(ModEffects.COMFORT, 1200, 0), 1.0F)
//                                .alwaysEdible()
//                                .build()
//                            )
//                ));
        }
    }


    public static void load(){
        Constants.LOG.info("BrewinAndChewin integration loaded");
    }
}
