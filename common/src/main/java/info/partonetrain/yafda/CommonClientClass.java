package info.partonetrain.yafda;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class CommonClientClass {
    public static void flipRenderIfBackstabbable(PoseStack poseStack){
        Minecraft minecraftInstance = Minecraft.getInstance();
        poseStack.scale(-1, -1, -1);
        if(isAttackIndicatorShowing(minecraftInstance) && isBehindTarget(minecraftInstance)){
            Constants.LOG.info("yes");
        }
        else{
            Constants.LOG.info("no");
        }
    }

    public static boolean isAttackIndicatorShowing(Minecraft minecraftInstance){

        float f = minecraftInstance.player.getAttackStrengthScale(0.0F);
        boolean attackIndicator = false;
        if (minecraftInstance.crosshairPickEntity instanceof LivingEntity && f >= 1.0F) {
            attackIndicator = minecraftInstance.player.getCurrentItemAttackStrengthDelay() > 5.0F;
            attackIndicator &= minecraftInstance.crosshairPickEntity.isAlive();
        }
        return attackIndicator;
    }

    //FD backstab check adapted to clientside
    public static boolean isBehindTarget(Minecraft minecraftInstance) {
        LivingEntity target = (LivingEntity) minecraftInstance.crosshairPickEntity;
        Vec3 attackerLocation = minecraftInstance.player.position();
        Vec3 lookingVector = target.getViewVector(1.0F);
        Vec3 attackAngleVector = attackerLocation.subtract(target.position()).normalize();
        attackAngleVector = new Vec3(attackAngleVector.x, 0.0D, attackAngleVector.z);
        return attackAngleVector.dot(lookingVector) < -0.5D;
    }
}
