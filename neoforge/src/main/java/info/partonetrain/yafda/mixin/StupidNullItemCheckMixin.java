package info.partonetrain.yafda.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MappedRegistry.class)
public class StupidNullItemCheckMixin<T> {

//    @Final
//    @Shadow
//    private ObjectList<Holder.Reference<T>> byId;
//
//    @WrapOperation(method = "clear(Z)V", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectList;clear()V", ordinal = 0))
//    public void yafda$stupidcheck0(ObjectList instance, Operation<Void> original){
//        System.out.println("byId.clear() called.");
//        original.call(instance);
//    }
//
//    @WrapOperation(method = "register(ILnet/minecraft/resources/ResourceKey;Ljava/lang/Object;Lnet/minecraft/core/RegistrationInfo;)Lnet/minecraft/core/Holder$Reference;", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectList;add(Ljava/lang/Object;)Z"))
//    public boolean yafda$stupidcheck1(ObjectList instance, Object o, Operation<Boolean> original, @Local Holder.Reference<T> reference, @Local(argsOnly = true) ResourceKey<T> key){
//        if (reference == null) {
//            String keyString = String.valueOf(key);
//            System.out.println("null reference detected!!!: " + keyString);
//            printO(1, o, key);
//        }
//        return original.call(instance, o);
//    }
//
//    @WrapOperation(method = "registerIdMapping(Lnet/minecraft/resources/ResourceKey;I)V", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectList;add(Ljava/lang/Object;)Z"))
//    public boolean yafda$stupidcheck2(ObjectList instance, Object o, Operation<Boolean> original, @Local(argsOnly = true) ResourceKey<T> key, @Local(argsOnly = true) int id){
//        String keyString = String.valueOf(key);
//        System.out.println("null object detected!!!: " + keyString + " id: " + id);
//        System.out.println("byId.size(): " + byId.size() + " id + 1: " + (id + 1) + " (this.byId.size() < id + 1): " + String.valueOf(this.byId.size() < id + 1));
//        printO(2, o, key);
//        return original.call(instance, o);
//    }
//
//    @WrapOperation(method = "registerIdMapping", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectList;set(ILjava/lang/Object;)Ljava/lang/Object;", ordinal = 0))
//    public Object yafda$stupidcheck3(ObjectList instance, int i, Object o, Operation<Object> original, @Local Holder.Reference<T> holder){
//        System.out.println("byId.set(id, holder) called.");
//        printO(3, o,  holder.key());
//        return original.call(instance, i, o);
//    }
//
//    @Unique
//    public void printO(int checkNum, Object o, ResourceKey<T> key){
//        if(o == null){
//            System.out.println("check " + checkNum + ": " + "O IS NULL!!! key:" + key.toString());
//        }
//        else{
//            if(o instanceof Holder.Reference<?> ref){
//                System.out.println("check " + checkNum + ": o is a Holder.Reference: ref.key: " + ref.key().toString() + ", " + "key: " + key.toString() + ", equivalent: " + ref.key().toString().equals(key.toString()));
//            }
//            else{
//                System.out.println("check " + checkNum + ": " + "o class: " + o.getClass());
//            }
//        }
//    }
}
