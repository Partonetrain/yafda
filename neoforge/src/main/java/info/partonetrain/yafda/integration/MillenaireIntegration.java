package info.partonetrain.yafda.integration;

import info.partonetrain.yafda.Constants;
import info.partonetrain.yafda.YafdaNeoForge;
import info.partonetrain.yafda.item.YafdaKnifeItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforgespi.locating.IModFile;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.millenaire.content.ContentDirectoryManager;
import org.millenaire.item.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.stream.Stream;

public class MillenaireIntegration {

    static final int INTEGRATION_VERSION = 2; //up this every time a change happens

    static final Tier normanKnifeTier = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 1561, 10.0F, 4.0F, 10, () -> Ingredient.of(new ItemLike[]{Items.IRON_INGOT})); //NormanMaterials.NORMAN_TOOL;
    //static final Tier indianKnifeTier = Tiers.WOOD;
    static final Tier mayanKnifeTier = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 6.0F, 2.0F, 25, () -> Ingredient.of(new ItemLike[]{Items.DIAMOND})); //MayanMaterials.OBSIDIAN_TOOL;
    static final Tier byzantineKnifeTier = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(new ItemLike[]{Items.IRON_INGOT})); //ByzantineMaterials.BYZANTINE_TOOL;
    //static final Tier japaneseKnifetier = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 6.0F, 2.0F, 25, () -> Ingredient.of(new ItemLike[]{Items.DIAMOND})); //JapaneseMaterials.JAPANESE_TOOL;
    //static final Tier seljukKnifetier = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 6.0F, 2.0F, 14, () -> Ingredient.of(new ItemLike[]{Items.IRON_INGOT})); SeljukMaterials.SELJUK_TOOL;
    //static final Tier inuitKinfeTier = Tiers.WOOD;

    public static final DeferredHolder<Item, Item> NORMAN_KNIFE = YafdaNeoForge.ITEMS.register(
            "norman_knife",
            () -> new YafdaKnifeItem(normanKnifeTier)
    );

    public static final DeferredHolder<Item, Item> MAYAN_KNIFE = YafdaNeoForge.ITEMS.register(
            "mayan_knife",
            () -> new YafdaKnifeItem(mayanKnifeTier)
    );

    public static final DeferredHolder<Item, Item> BYZANTINE_KNIFE = YafdaNeoForge.ITEMS.register(
            "byzantine_knife",
            () -> new YafdaKnifeItem(byzantineKnifeTier)
    );

    public static void load() {
        Constants.LOG.info("Millenaire integration loaded");
    }

    public static void deployCustomFolder(MinecraftServer server){
        Constants.LOG.info("Checking /millenaire-custom/ deployed content...");
        ContentDirectoryManager.init(server);
        Path customDir = ContentDirectoryManager.getCustomDir();
        Path yafdaDir = customDir.resolve(Constants.MOD_ID);
        Path versionFile = yafdaDir.resolve("version.txt");

        //first check if it's already there and up to date.
        int currentlyDeployedVersion = 0;
        if(Files.exists(versionFile)) {
            try (Stream<@NotNull String> lines = Files.lines(versionFile)) {
                Optional<String> firstLine = lines.findFirst();
                if(firstLine.isPresent()){
                    currentlyDeployedVersion = Integer.parseInt(firstLine.get());
                }
                else{
                    Constants.LOG.error("Found /millenaire-custom/" + Constants.MOD_ID + "/version.txt, but it was empty! Assuming version " + currentlyDeployedVersion);
                }
            } catch (IOException e) {
                Constants.LOG.error("Error while attempting to read /millenaire-custom/" + Constants.MOD_ID + "/version.txt: " + e);
            }
        }

        if(currentlyDeployedVersion == -1){
            Constants.LOG.info("Deployed /millenaire-custom/" + Constants.MOD_ID + "/ is set to -1, ignoring");
            return;
        }
        if(currentlyDeployedVersion == INTEGRATION_VERSION){
            Constants.LOG.info("Deployed /millenaire-custom/" + Constants.MOD_ID + "/ is up-to-date.");
            return;
        }
        else if(currentlyDeployedVersion < INTEGRATION_VERSION){
            //now actually deploy.
            IModFile thisJar = ModList.get().getModFileById(Constants.MOD_ID).getFile();
            Path customToDeploy = thisJar.findResource("deployable", "millenaire-custom");
            try{
                Constants.LOG.info("Deployed /millenaire-custom/" + Constants.MOD_ID + "/ is out-of-date. Deleting old data.");
                nukeOldDeployed(yafdaDir);
                copyFromTo(customToDeploy, customDir);
                writeVersionFile(versionFile);
            }
            catch (Exception e){
                Constants.LOG.error("Error while attempting to deploy to /millenaire-custom/: " + e);
            }
        }
    }

    private static void nukeOldDeployed(Path path) throws IOException{
        FileUtils.deleteDirectory(path.toFile()); //returns safely if directory does not exist.
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

    private static void writeVersionFile(Path path) throws IOException{
        try {
            Files.writeString(path,
                    INTEGRATION_VERSION + System.lineSeparator() +
                    "#This file is used by " + Constants.MOD_ID +  " to determine integration version. Please do not edit it, unless want to prevent deployment (set it to -1)." + System.lineSeparator()
            );
        } catch (IOException e) {
            Constants.LOG.error("Error while attempting to write version.txt to /millenaire-custom/" + Constants.MOD_ID + "/: " + e);
        }
    }

}
