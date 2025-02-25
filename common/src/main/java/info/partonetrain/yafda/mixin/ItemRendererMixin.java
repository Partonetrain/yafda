package info.partonetrain.yafda.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import info.partonetrain.yafda.CommonClientClass;
import info.partonetrain.yafda.Constants;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Holder;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
//    @Unique
//    Predicate<TypedDataComponent<?>> yafda$backstabbingEffect = new Predicate<TypedDataComponent<?>>() {
//        @Override
//        public boolean test(TypedDataComponent<?> enchantmentEffectComponentsTypedDataComponent) {
//            return enchantmentEffectComponentsTypedDataComponent.toString().contains("farmersdelight:backstabbing");
//        }
//    };
//
//    @Inject(method = "render", at= @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"))
//    public void yafda$render(ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BakedModel model, CallbackInfo ci){
//        if(itemStack.is(Constants.KNIFE_TAG) && !itemStack.getEnchantments().isEmpty()){
//            for(Object2IntMap.Entry<Holder<Enchantment>> enchantment : itemStack.getEnchantments().entrySet()){
//                if(enchantment.getKey().value().effects().stream().anyMatch(yafda$backstabbingEffect)){
//                    CommonClientClass.flipRenderIfBackstabbable(poseStack);
//                }
//            }
//        }
//    }
}
