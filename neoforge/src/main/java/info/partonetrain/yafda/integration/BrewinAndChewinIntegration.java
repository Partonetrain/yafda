package info.partonetrain.yafda.integration;

import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.fluid.WateryFluid;
import info.partonetrain.yafda.YafdaNeoForge;
import info.partonetrain.yafda.item.ConsumableEffectItem;
import info.partonetrain.yafda.platform.Services;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
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
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.Supplier;

public class BrewinAndChewinIntegration {

    public static final int BLOOM_BRANDY_COLOR = 0xEED98800;
    public static Supplier<FlowingFluid> bloomBrandy = null;
    public static Supplier<FlowingFluid> bloomBrandyFlowing = null;
    public static Supplier<FluidType> bloomBrandyFluidType = null;
    static DeferredHolder<Item, Item> bloomBrandyTankard;
    public static final FoodProperties MARMALADE_SANDWICH_PROPERTIES = (new FoodProperties.Builder()).nutrition(10).saturationModifier(0.8F).effect(() -> {
        return new MobEffectInstance(BnCEffects.SWEET_HEART.getDelegate(), 1200, 0);
    }, 1.0F).build();

    public static final DeferredHolder<Item, Item> MARMALADE_SANDWICH = YafdaNeoForge.ITEMS.register(
            "marmalade_sandwich",() -> new ConsumableEffectItem(ModItems.foodItem(MARMALADE_SANDWICH_PROPERTIES)
    ));

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
            bloomBrandyFluidType = YafdaNeoForge.FLUID_TYPES.register("bloom_brandy",  () -> new WateryFluid(FluidType.Properties.create(), BLOOM_BRANDY_COLOR));

            bloomBrandy = YafdaNeoForge.FLUIDS.register("bloom_brandy", () -> new BaseFlowingFluid.Source(getBrandyProperties()));
            bloomBrandyFlowing = YafdaNeoForge.FLUIDS.register("bloom_brandy_flowing", () -> new BaseFlowingFluid.Source(getBrandyProperties()));

            bloomBrandyTankard = YafdaNeoForge.ITEMS.register(
                    "bloom_brandy_tankard",
                    () -> new BoozeItem(() -> bloomBrandyFlowing.get(), new Item.Properties()
                            .stacksTo(16).craftRemainder(BnCItems.TANKARD).food(
                                    new FoodProperties.Builder()
                                .effect(new MobEffectInstance(BnCEffects.TIPSY, 2400, 0), 1.0F)
                                .effect(new MobEffectInstance(BnCEffects.INTOXICATION, 1800, 0, false, false), 1.0F)
                                .effect(new MobEffectInstance(MobEffects.GLOWING, 6000, 0), 1.0F)
                                .alwaysEdible()
                                .build()
                            )
                ));
        }
    }

    //simulates drinking bnc beer
    public static void tipsyStacking(LivingEntity consumer){
        BoozeItem dummyBooze = (BoozeItem) BnCItems.BEER;
        dummyBooze.affectConsumer(consumer, 2400, 0);
    }

    public static void load(){
        Constants.LOG.info("BrewinAndChewin integration loaded");
    }
}
