package info.partonetrain.yafda.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class WateryFluid extends FluidType {
    int color;

    public WateryFluid(Properties properties, int color) {
        super(FluidType.Properties.create()
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
        );
        this.color = color;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {

            @Override
            public @NotNull ResourceLocation getStillTexture() {
                return ResourceLocation.withDefaultNamespace("block/water_still");
            }

            @Override
            public @NotNull ResourceLocation getFlowingTexture() {
                return ResourceLocation.withDefaultNamespace("block/water_flow");
            }

            @Override
            public int getTintColor() {
                return color;
            }
        });
    }

}
