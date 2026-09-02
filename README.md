Yet Another Farmer's Delight addon is a mod that adds Farmer's Delight knives and some foods for the mods listed below.
It arose from a discussion on the Violet Moon discord about there being too many Farmer's Delight addons, and the lack of a "general material compatibility" mod for knives.

YAFDA adds content for the following mods:
- Aether (incl. Altar repair recipes)
- Deep Aether
- Deeper and Darker
- Thirst was Taken
- Brewin' and Chewin
- Immersive Engineering
- Malum
- Millenaire 9
- Environmental
- Ars Elemental & Ars Nouveau Flavors and Delight (newer versions of Flavors and Delight have Flashpine foods included, so YAFDA will not register its own)

For Millenaire, YAFDA deploys what files it can to the millenaire_custom folder. However, you will have to manually copy over the shop and villager type JSON definitions from the millenaire folder that match the names of the ones in the .shopsToMerge and .villagersToMerge folders and add the entries. I know this is cumbersome, but it seems Millenaire's shops and villager types are not mergeable by design.

All items and their dependent mods:
![All Items](https://raw.githubusercontent.com/Partonetrain/yafda/refs/heads/1.21.1/all_items.png)

I am open to suggestions about other mods to add support to.
For cupboards and wood-related cutting recipes, I highly recommend EveryCompat (Wood Good).

### Fabric?
The development repository supports Fabric, but unfortunately due to the large amount of cross-mod support and the lack of common APIs for said mods,
YAFDA on Fabric isn't able to use any code from the NeoForge version. Therefore, YAFDA development will target NeoForge.
If you would like to port YAFDA to Fabric, you may submit a pull request.