package info.partonetrain.yafda.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class YafdaKnifeItem extends KnifeItem {
    public YafdaKnifeItem(Tier tier) {
        super(tier, CreateKnifeProperties(tier));
    }

    public static Item.Properties CreateKnifeProperties(Tier tier) {
        return (new Item.Properties()).attributes(KnifeItem.createAttributes(tier, 0.5F, -2.0F));
    }
}
