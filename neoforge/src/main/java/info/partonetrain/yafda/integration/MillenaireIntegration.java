package info.partonetrain.yafda.integration;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaNeoForge;
import info.partonetrain.yafda.item.YafdaKnifeItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforgespi.locating.IModFile;
import org.millenaire.content.ContentDirectoryManager;
import org.millenaire.item.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class MillenaireIntegration {

    static final Tier normanKnifeTier = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 1561, 10.0F, 4.0F, 10, () -> Ingredient.of(new ItemLike[]{Items.IRON_INGOT})); //NormanMaterials.NORMAN_TOOL;
    //final Tier indianKnifeTier = Tiers.WOOD;
    static final Tier mayanKnifeTier = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 6.0F, 2.0F, 25, () -> Ingredient.of(new ItemLike[]{Items.DIAMOND})); //MayanMaterials.OBSIDIAN_TOOL;
    static final Tier byzantineKnifeTier = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(new ItemLike[]{Items.IRON_INGOT})); //ByzantineMaterials.BYZANTINE_TOOL;
    static final Tier japaneseKnifetier = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 6.0F, 2.0F, 25, () -> Ingredient.of(new ItemLike[]{Items.DIAMOND})); //JapaneseMaterials.JAPANESE_TOOL;
    static final Tier seljukKnifetier = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(new ItemLike[]{Items.IRON_INGOT})); //SeljukMaterials.SELJUK_TOOL;
    //final Tier inuitKinfeTier = Tiers.WOOD;

    public static final DeferredHolder<Item, Item> NORMAN_KNIFE = YafdaNeoForge.ITEMS.register(
            "norman_knife",
            () -> new YafdaKnifeItem(normanKnifeTier)
    );

    public static void load() {
        Constants.LOG.info("Millenaire integration loaded");
    }

    //right now this deploys every time
    //should probably only deploy on first run, write to a version file or something, and check that
    public static void deployCustomFolder(ServerStartedEvent event){
        ContentDirectoryManager.init(event.getServer());
        Path customDir = ContentDirectoryManager.getCustomDir();
        IModFile thisJar = ModList.get().getModFileById(Constants.MOD_ID).getFile();
        Path customToDeploy = thisJar.findResource("deployable", "millenaire_custom");
        try{
            copyFromTo(customToDeploy, customDir);
        }
        catch (Exception e){
            Constants.LOG.error("Error while attempting to deploy to millenaire_custom: " + e);
        }

    }

    private static void copyFromTo(Path source, Path target) throws IOException {
        Files.walk(source).forEach(sourcePath -> {
            try {
                Path targetPath = target.resolve(source.relativize(sourcePath).toString());

                if (Files.isDirectory(sourcePath)) {
                    if (!Files.exists(targetPath)) {
                        Files.createDirectories(targetPath);
                    }
                } else {
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to copy: " + sourcePath, e);
            }
        });
    }

}
