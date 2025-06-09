package info.partonetrain.yafda.integration;

import com.sammy.malum.registry.common.item.MalumItems;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

public class MalumIntegration {

    

    public static boolean isWearingTophat(Player player) {
        Optional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(player);
        if (!curios.isEmpty()) {
            return curios.get().isEquipped(MalumItems.TOPHAT.get());
        }
        return false;
    }
}
