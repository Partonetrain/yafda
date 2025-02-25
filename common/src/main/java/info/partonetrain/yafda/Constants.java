package info.partonetrain.yafda;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MOD_ID = "yafda";
	public static final String MOD_NAME = "YAFDA";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static final ResourceLocation FD_BACKSTABBING_ENCHANTMENT_EFFECT = ResourceLocation.fromNamespaceAndPath("farmersdelight","backstabbing");
	public static final TagKey<Item> KNIFE_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/knife"));
}