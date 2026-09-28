package info.partonetrain.yafda;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.apache.maven.artifact.versioning.ArtifactVersion;

public class YafdaConditions {

    //neoforge:registered condition doesn't exist in 1.21.1 :( only neoforge:item_exists
    //we only need it for this one specific case for infernal sus sap so everything is hardcoded
    public record TrainsTweaksDexterityExistsCondition() implements ICondition {
        public static final MapCodec<TrainsTweaksDexterityExistsCondition> CODEC =
                MapCodec.unit(new TrainsTweaksDexterityExistsCondition()); //no fields

        @Override
        public boolean test(ICondition.IContext context) {
            return BuiltInRegistries.MOB_EFFECT.containsKey(ResourceKey.create(BuiltInRegistries.MOB_EFFECT.key(), ResourceLocation.fromNamespaceAndPath("trains_tweaks", "dexterity")));
        }

        @Override
        public MapCodec<? extends ICondition> codec() {
            return CODEC;
        }
    }

    public static ArtifactVersion cachedArsdelightVersion;

    //true if ars delight is old version
    public record OldArsDelightCondition() implements ICondition {
        public static final MapCodec<OldArsDelightCondition> CODEC =
                MapCodec.unit(new OldArsDelightCondition()); //no fields

        @Override
        public boolean test(ICondition.IContext context) {
            if(cachedArsdelightVersion == null) {
                cachedArsdelightVersion = ModList.get().getModContainerById("arsdelight").get().getModInfo().getVersion();
            }
            return !(cachedArsdelightVersion.compareTo(YafdaNeoForge.MAX_ARS_DELIGHT_VERSION_FOR_ORIGINAL_COMPAT) > 0);
        }

        @Override
        public MapCodec<? extends ICondition> codec() {
            return CODEC;
        }
    }
}
