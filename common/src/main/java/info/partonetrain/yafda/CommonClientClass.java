package info.partonetrain.yafda;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;

public class CommonClientClass {
    public static void flipRenderIfBackstabbable(PoseStack poseStack){
        poseStack.scale(-1, -1, -1);
    }

    public boolean isAttackIndicatorShowing(){
        Minecraft minecraftInstance = Minecraft.getInstance();
        float f = minecraftInstance.player.getAttackStrengthScale(0.0F);
        boolean attackIndicator = false;
        if (minecraftInstance.crosshairPickEntity instanceof LivingEntity && f >= 1.0F) {
            attackIndicator = minecraftInstance.player.getCurrentItemAttackStrengthDelay() > 5.0F;
            attackIndicator &= minecraftInstance.crosshairPickEntity.isAlive();
        }
        return attackIndicator;
    }
}
