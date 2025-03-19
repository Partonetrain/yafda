package info.partonetrain.yafda.integration;

import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaNeoForge;
import info.partonetrain.yafda.platform.Services;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import umpaz.brewinandchewin.common.item.BoozeItem;
import umpaz.brewinandchewin.common.item.JamJarItem;
import umpaz.brewinandchewin.common.registry.BnCEffects;
import umpaz.brewinandchewin.common.registry.BnCFoods;
import umpaz.brewinandchewin.common.registry.BnCItems;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.function.Supplier;

public class BrewinAndChewinIntegration {

    public static final int BLOOM_BRANDY_COLOR = 0xFFFBB117;
    public static Supplier<FlowingFluid> bloomBrandy = null;
    public static Supplier<FlowingFluid> bloomBrandyFlowing = null;
    public static Supplier<FluidType> bloomBrandyFluidType = null;
    static DeferredHolder<Item, Item> bloomBrandyItem;

    private static BaseFlowingFluid.Properties getBrandyProperties() {
        return new BaseFlowingFluid.Properties(bloomBrandyFluidType, bloomBrandy, bloomBrandyFlowing);
    }

    static {
        if(Services.PLATFORM.isModLoaded("aether")){
            final DeferredHolder<Item, Item> AETHER_BLUE_BERRY_JAM = YafdaNeoForge.ITEMS.register(
                    "blue_berry_jam",
                    () -> new JamJarItem((new Item.Properties()).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE).food(BnCFoods.SWEET_BERRY_JAM))
            );
        }
        if(Services.PLATFORM.isModLoaded("deeperdarker")){
            bloomBrandyFluidType = YafdaNeoForge.FLUID_TYPES.register("bloom_brandy",  () -> new FluidType(FluidType.Properties.create().lightLevel(0).density(800).viscosity(1500)));

            bloomBrandy = YafdaNeoForge.FLUIDS.register("bloom_brandy", () -> new BaseFlowingFluid.Source(getBrandyProperties()));
            bloomBrandyFlowing = YafdaNeoForge.FLUIDS.register("bloom_brandy_flowing", () -> new BaseFlowingFluid.Source(getBrandyProperties()));

            bloomBrandyItem = YafdaNeoForge.ITEMS.register(
                    "bloom_brandy",
                    () -> new BoozeItem(() -> bloomBrandyFlowing.get(), new Item.Properties()
                            .stacksTo(16).craftRemainder(BnCItems.TANKARD).food(
                                    new FoodProperties.Builder()
                                .effect(new MobEffectInstance(BnCEffects.TIPSY, 2400, 0), 1.0F)
                                .effect(new MobEffectInstance(BnCEffects.INTOXICATION, 1800, 0, false, false), 1.0F)
                                .effect(new MobEffectInstance(MobEffects.GLOWING, 12000, 0), 1.0F)
                                .alwaysEdible()
                                .build()
                            )
                ));
        }
    }


    public static void load(){
        Constants.LOG.info("BrewinAndChewin integration loaded");
    }
}
