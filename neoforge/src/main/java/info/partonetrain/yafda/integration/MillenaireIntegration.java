package info.partonetrain.yafda.integration;

import info.partonetrain.yafda.Constants;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import org.millenaire.item.*;

public class MillenaireIntegration {

    final Tier normanKnifeTier = NormanMaterials.NORMAN_TOOL;
    final Tier indianKnifeTier = Tiers.WOOD;
    final Tier mayanKnifeTier = MayanMaterials.OBSIDIAN_TOOL;
    final Tier byzantinesKnifeTier = ByzantineMaterials.BYZANTINE_TOOL;
    final Tier japaneseKnifetier = JapaneseMaterials.JAPANESE_TOOL;
    final Tier seljukKnifetier = SeljukMaterials.SELJUK_TOOL;
    final Tier inuitKinfeTier = Tiers.WOOD;

    public static void load() {
        //Constants.LOG.info("Millenaire integration loaded");
    }

}
