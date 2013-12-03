package bettervanilla;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import bettervanilla.blocks.BlockBedOverride;
import bettervanilla.blocks.BlockCactusOverride;
import bettervanilla.dispenserbehaviors.DispenserBehaviorShears;
import bettervanilla.dispenserbehaviors.DispenserBehaviorUniversal;
import bettervanilla.events.BonemealHook;
import bettervanilla.events.BreakHook;
import bettervanilla.events.HarvestDropsHook;
import bettervanilla.events.PlayerInteractHook;
import bettervanilla.items.BirchArmor;
import bettervanilla.items.ItemBedOverride;
import bettervanilla.items.JungleArmor;
import bettervanilla.items.OakArmor;
import bettervanilla.items.SpruceArmor;
import bettervanilla.tileentities.BedDirection;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "BetterVanilla", name = "BetterVanilla", version = "0.1.8")
@NetworkMod(clientSideRequired = true)
public class BetterVanilla 
{	
	// Values from the configuration file.
	public static boolean Apples;
	public static double ApplesRate;
	public static boolean Beds;
	public static boolean BoneMeal;
	public static boolean BookShelves;
	public static boolean Cacti;
	public static boolean Cauldrons;
	public static boolean Dispensers;
	public static boolean Doors;
	public static boolean EnderChests;
	public static boolean HorseArmor;
	public static boolean Ice;
	public static boolean MobFilter;
	public static String[] MobFilterList;
	public static boolean MossStone;
	public static boolean Nametags;
	public static boolean Saddles;
	public static boolean WoodArmor;
	public static int WoodArmorID;
	
	@Instance(value = "BetterVanilla")
	public static BetterVanilla instance;

	@SidedProxy(clientSide = "bettervanilla.client.ClientProxy", serverSide = "bettervanilla.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{		
		Configuration config = new Configuration(
				event.getSuggestedConfigurationFile());
		config.load();

		// Load/create the configuration properties.
		Property apples = config.get(Configuration.CATEGORY_BLOCK, "Apples tweak", true);
		Property applesRate = config.get(Configuration.CATEGORY_BLOCK, "Apples drop rate", 5);
		Property beds = config.get(Configuration.CATEGORY_BLOCK, "Colored beds", false);
		Property boneMeal = config.get(Configuration.CATEGORY_ITEM, "Bonemeal tweak", true);
		Property bookShelves = config.get(Configuration.CATEGORY_BLOCK, "Bookshelves drop tweak", true);
		Property cacti = config.get(Configuration.CATEGORY_BLOCK, "Cacti placement tweak", true);
		Property cauldrons = config.get(Configuration.CATEGORY_BLOCK, "Cauldron tweak", true);
		Property dispensers = config.get(Configuration.CATEGORY_BLOCK, "Dispenser overhaul", true);
		Property doors = config.get(Configuration.CATEGORY_ITEM, "Stackable doors", true);
		Property enderChests = config.get(Configuration.CATEGORY_BLOCK, "Ender chest drop tweak", true);
		Property horseArmor = config.get(Configuration.CATEGORY_ITEM, "Craftable horse armor", true);
		Property ice = config.get(Configuration.CATEGORY_BLOCK, "Ice drop tweak", true);
		Property mobFilter = config.get(Configuration.CATEGORY_GENERAL, "Mob filter", false);
		Property mobFilterList = config.get(Configuration.CATEGORY_GENERAL, "Mob filter list", new String[] { "Example1", "Example2", "Example3" });
		Property mossStone = config.get(Configuration.CATEGORY_ITEM, "Craftable moss stone", true);
		Property nametags = config.get(Configuration.CATEGORY_ITEM, "Craftable nametags", true);
		Property saddles = config.get(Configuration.CATEGORY_ITEM, "Craftable saddles", true);
		Property woodArmor = config.get(Configuration.CATEGORY_ITEM, "Wood Armor", true);
		Property woodArmorID = config.get(Configuration.CATEGORY_ITEM, "Starting ID", 1011);

		// Set the comments of the configuration properties.
		apples.comment = "Set to 'true' to alter the drop rate of apples.";
		applesRate.comment = "The new drop rate of apples. Vanilla default is 0.5%. BetterVanilla default is 5%.";
		beds.comment = "Set to 'true' to allow the crafting of colored beds (adds 15 new beds to the game, each with a different wool color).";
		boneMeal.comment = "Set to 'true' to allow bonemeal to be used on cacti, sugar canes, and nether warts.";
		bookShelves.comment = "Set to 'true' to make bookshelves drop a book shelf upon destruction instead of books.";
		cacti.comment = "Set to 'true' to allow cacti to be placed beside solid blocks without breaking.";
		cauldrons.comment = "Set to 'true' to allow players to wash away the dye from dyed wool and clay using a cauldron.";
		dispensers.comment = "Set to 'true' to make dispensers place blocks, plant seeds, and use hoes and shears instead of dropping them as items.";
		doors.comment = "Set to 'true' to increase the maximum stack size of doors from 1 to 16.";
		enderChests.comment = "Set to 'true' to make ender chests drop an ender chest upon destruction instead of obsidian blocks.";
		horseArmor.comment = "Set to 'true' to allow the crafting of horse armors.";
		ice.comment = "Set to 'true' to make ice blocks drop an ice block upon destruction instead of creating a water source when in a snowy biome.";
		mobFilter.comment = "Set to 'true' to prevent the mobs of which the names are entered into the mob filter list from spawning naturally.";
		mobFilterList.comment = "Insert into this list the names of mobs you wish to stop from spawning naturally. "
				+ "Invalid or wrongly-typed mob names are ignored. Ensure that this mod is loaded last if you want to prevent mobs added by "
				+ "other mods from spawning.";
		mossStone.comment = "Set to 'true' to allow the crafting of moss stone, cracked stone bricks, mossy stone bricks, and chiseled stone bricks.";
		nametags.comment = "Set to 'true' to allow the crafting of nametags.";
		saddles.comment = "Set to 'true' to allow the crafting of saddles.";
		woodArmor.comment = "Set to 'true' to allow the crafting of several wood armor types. Wood armor is equal in all respects to leather armor.";
		woodArmorID.comment = "Sets the starting ID of the Wood Armor ID range. As there are a total of 16 items in this range, the last ID in the range is this value plus 15.";
				
		// Get the values of the configuration properties.
		Apples = apples.getBoolean(true);
		ApplesRate = applesRate.getDouble(5) * 2;
		Beds = beds.getBoolean(false);
		BoneMeal = boneMeal.getBoolean(true);
		BookShelves = bookShelves.getBoolean(true);
		Cacti = cacti.getBoolean(true);
		Cauldrons = cauldrons.getBoolean(true);
		Dispensers = dispensers.getBoolean(true);
		Doors = doors.getBoolean(true);
		EnderChests = enderChests.getBoolean(true);
		HorseArmor = horseArmor.getBoolean(true);
		Ice = ice.getBoolean(true);
		MobFilter = mobFilter.getBoolean(false);
		MobFilterList = mobFilterList.getStringList();
		MossStone = mossStone.getBoolean(true);
		Nametags = nametags.getBoolean(true);
		Saddles = saddles.getBoolean(true);
		WoodArmor = woodArmor.getBoolean(true);
		WoodArmorID = woodArmorID.getInt(); 
		
		config.save();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) 
	{
		if (Apples || Ice) {
			// Register the event hook for increasing the drop rate of apples from leaves and altering the ice block's item drop behavior.
			MinecraftForge.EVENT_BUS.register(new BreakHook());
		}
		if (Beds) {
			// Replace the original bed item with our overridden bed item.
			int itemBedId = Item.bed.itemID;
			Item.itemsList[itemBedId] = null;
			Item itemBedOverride = (new ItemBedOverride(itemBedId)).setMaxStackSize(1).setUnlocalizedName("bedItem").setTextureName("bettervanilla:bed");

			// Add all of the names for the bed item's subitems.
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 0), "White Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 1), "Orange Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 2), "Magenta Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 3), "Light Blue Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 4), "Yellow Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 5), "Lime Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 6), "Pink Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 7), "Gray Gray Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 8), "Light Gray Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 9), "Cyan Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 10), "Purple Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 11), "Blue Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 12), "Brown Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 13), "Green Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 14), "Red Bed");
			LanguageRegistry.addName(new ItemStack(itemBedOverride, 1, 15), "Black Bed");
			
			// Remove the recipe for the original bed item and add the recipes for our overridden bed item's subitems.
			removeRecipe(new ItemStack(Item.bed));		
			for (int i = 0; i < 16; i++) CraftingManager.getInstance().addRecipe(new ItemStack(itemBedOverride, 1, i), 
					"xxx", "yyy", 'x', new ItemStack(Block.cloth, 1, i), 'y', Block.planks);			
			
			// Replace the original bed block with our overridden bed block.
			int blockBedId = Block.bed.blockID;
			Block.blocksList[blockBedId] = null;
			(new BlockBedOverride(blockBedId)).setHardness(0.2F).setUnlocalizedName("bedBlock").setTextureName("bettervanilla:bed");
			
			// Register the tile entity that is responsible for storing the bed's direction.
			GameRegistry.registerTileEntity(BedDirection.class, "BedDirection");
		}
		if (BoneMeal) {
			// Register the event hook for using bonemeal on reeds, cacti, and warts.
			MinecraftForge.EVENT_BUS.register(new BonemealHook());
		}
		if (BookShelves || EnderChests) {
			// Register the event hook for bookshelves and ender chests dropping bookshelves and ender chests, respectively.
			MinecraftForge.EVENT_BUS.register(new HarvestDropsHook());
		}
		if (Cacti) {
			// Replace the original cactus with our overridden cactus.
			int cactusId = Block.cactus.blockID;
			Block.blocksList[cactusId] = null;
			(new BlockCactusOverride(cactusId)).setHardness(0.4F)
					.setStepSound(Block.soundClothFootstep)
					.setUnlocalizedName("cactus").setTextureName("cactus");
		}
		if (Cauldrons) {
			// Register the event hook for using clay and wool on a cauldron.
			MinecraftForge.EVENT_BUS.register(new PlayerInteractHook());
		}
		if (Dispensers) {
			// Register our dispenser behaviors
			BlockDispenser.dispenseBehaviorRegistry.putObject(Item.shears,
					new DispenserBehaviorShears());
			BlockDispenser.dispenseBehaviorRegistry.putObject(Item.reed,
					new DispenserBehaviorUniversal());

			for (Item item : Item.itemsList) {
				if (item instanceof ItemBlock || item instanceof IPlantable
						|| item instanceof ItemHoe) {
					BlockDispenser.dispenseBehaviorRegistry.putObject(item,
							new DispenserBehaviorUniversal());
				}
			}
		}
		if (Doors) {
			// Increase the maximum stack size from 1 to 16.
			Item.doorWood.setMaxStackSize(16);
			Item.doorIron.setMaxStackSize(16);
		}
		if (HorseArmor) {
			// Add the horse armor, saddle, and name-tag recipes.
			GameRegistry.addRecipe(new ItemStack(Item.horseArmorGold), "  x",
					"xyx", "xxx", 'x', Item.ingotGold, 'y', new ItemStack(
							Block.cloth, 1, 14));
			GameRegistry.addRecipe(new ItemStack(Item.horseArmorIron), "  x",
					"xyx", "xxx", 'x', Item.ingotIron, 'y', new ItemStack(
							Block.cloth, 1, 15));
			GameRegistry.addRecipe(new ItemStack(Item.horseArmorDiamond),
					"  x", "xyx", "xxx", 'x', Item.diamond, 'y', new ItemStack(
							Block.cloth, 1, 11));
		}
		if (MossStone) {
			GameRegistry.addRecipe(new ItemStack(Block.cobblestoneMossy, 8), "xxx", "xyx",
					"xxx", 'x', Block.cobblestone, 'y', Block.vine);
			GameRegistry.addRecipe(new ItemStack(Block.stoneBrick, 8, 1), "xxx", "xyx",
					"xxx", 'x', new ItemStack(Block.stoneBrick, 1, 0), 'y', Block.vine);
			GameRegistry.addRecipe(new ItemStack(Block.stoneBrick, 4, 2), "xx",
					"xx", 'x', new ItemStack(Block.stoneBrick, 1, 0));
			GameRegistry.addRecipe(new ItemStack(Block.stoneBrick, 1, 3), "x", "x",
					'x', new ItemStack(Block.stoneSingleSlab, 1, 5));
		}
		if (Nametags) {
			GameRegistry.addRecipe(new ItemStack(Item.nameTag), "  x", " y ",
					"y  ", 'x', Item.silk, 'y', Item.paper);
		}
		if (Saddles) {
			GameRegistry.addRecipe(new ItemStack(Item.saddle), "xxx", "yxy",
					"z z", 'x', Item.leather, 'y', Item.silk, 'z',
					Item.ingotIron);
		}
		if (WoodArmor) {
			// Create our new armor material.
			EnumArmorMaterial armorWOOD = net.minecraftforge.common.EnumHelper.addArmorMaterial("WOOD", 5, new int[]{1, 3, 2, 1}, 15);
			
			// Register whatever it is these integers represent.
			int oak = proxy.addArmor("oak");
			int spruce = proxy.addArmor("spruce");
			int birch = proxy.addArmor("birch");
			int jungle = proxy.addArmor("jungle");
			
			// Create our new armor items.
			OakArmor helmetOak = (OakArmor) new OakArmor(WoodArmorID, armorWOOD, oak, 0).setUnlocalizedName("helmetOak").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:oak_helmet");
			OakArmor plateOak = (OakArmor) new OakArmor(WoodArmorID += 1, armorWOOD, oak, 1).setUnlocalizedName("plateOak").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:oak_chestplate");
			OakArmor legsOak = (OakArmor) new OakArmor(WoodArmorID += 1, armorWOOD, oak, 2).setUnlocalizedName("legsOak").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:oak_leggings");
			OakArmor bootsOak = (OakArmor) new OakArmor(WoodArmorID += 1, armorWOOD, oak, 3).setUnlocalizedName("bootsOak").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:oak_boots");
			SpruceArmor helmetSpruce = (SpruceArmor) new SpruceArmor(WoodArmorID += 1, armorWOOD, spruce, 0).setUnlocalizedName("helmetSpruce").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:spruce_helmet");
			SpruceArmor plateSpruce = (SpruceArmor) new SpruceArmor(WoodArmorID += 1, armorWOOD, spruce, 1).setUnlocalizedName("plateSpruce").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:spruce_chestplate");
			SpruceArmor legsSpruce = (SpruceArmor) new SpruceArmor(WoodArmorID += 1, armorWOOD, spruce, 2).setUnlocalizedName("legsSpruce").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:spruce_leggings");
			SpruceArmor bootsSpruce = (SpruceArmor) new SpruceArmor(WoodArmorID += 1, armorWOOD, spruce, 3).setUnlocalizedName("bootsSpruce").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:spruce_boots");
			BirchArmor helmetBirch = (BirchArmor) new BirchArmor(WoodArmorID += 1, armorWOOD, birch, 0).setUnlocalizedName("helmetBirch").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:birch_helmet");
			BirchArmor plateBirch = (BirchArmor) new BirchArmor(WoodArmorID += 1, armorWOOD, birch, 1).setUnlocalizedName("plateBirch").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:birch_chestplate");
			BirchArmor legsBirch = (BirchArmor) new BirchArmor(WoodArmorID += 1, armorWOOD, birch, 2).setUnlocalizedName("legsBirch").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:birch_leggings");
			BirchArmor bootsBirch = (BirchArmor) new BirchArmor(WoodArmorID += 1, armorWOOD, birch, 3).setUnlocalizedName("bootsBirch").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:birch_boots");	
			JungleArmor helmetJungle = (JungleArmor) new JungleArmor(WoodArmorID += 1, armorWOOD, jungle, 0).setUnlocalizedName("helmetJungle").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:jungle_helmet");
			JungleArmor plateJungle = (JungleArmor) new JungleArmor(WoodArmorID += 1, armorWOOD, jungle, 1).setUnlocalizedName("plateJungle").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:jungle_chestplate");
			JungleArmor legsJungle = (JungleArmor) new JungleArmor(WoodArmorID += 1, armorWOOD, jungle, 2).setUnlocalizedName("legsJungle").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:jungle_leggings");
			JungleArmor bootsJungle = (JungleArmor) new JungleArmor(WoodArmorID += 1, armorWOOD, jungle, 3).setUnlocalizedName("bootsJungle").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:jungle_boots");
			
			// Register out items in the LanguageRegistry.
			LanguageRegistry.addName(helmetOak, "Oak Helmet");
			LanguageRegistry.addName(plateOak, "Oak Chestplate");
			LanguageRegistry.addName(legsOak, "Oak Leggings");
			LanguageRegistry.addName(bootsOak, "Oak Boots");
			LanguageRegistry.addName(helmetSpruce, "Spruce Helmet");
			LanguageRegistry.addName(plateSpruce, "Spruce Chestplate");
			LanguageRegistry.addName(legsSpruce, "Spruce Leggings");
			LanguageRegistry.addName(bootsSpruce, "Spruce Boots");
			LanguageRegistry.addName(helmetBirch, "Birch Helmet");
			LanguageRegistry.addName(plateBirch, "Birch Chestplate");
			LanguageRegistry.addName(legsBirch, "Birch Leggings");
			LanguageRegistry.addName(bootsBirch, "Birch Boots");
			LanguageRegistry.addName(helmetJungle, "Jungle Helmet");
			LanguageRegistry.addName(plateJungle, "Jungle Chestplate");
			LanguageRegistry.addName(legsJungle, "Jungle Leggings");
			LanguageRegistry.addName(bootsJungle, "Jungle Boots");
			
			// Add the recipes of our items to the GameRegistry.
			GameRegistry.addRecipe(new ItemStack(helmetOak), "xxx", "x x", 'x', new ItemStack(Block.wood, 1, 0));
			GameRegistry.addRecipe(new ItemStack(plateOak), "x x", "xxx", "xxx", 'x', new ItemStack(Block.wood, 1, 0));
			GameRegistry.addRecipe(new ItemStack(legsOak), "xxx", "x x", "x x", 'x', new ItemStack(Block.wood, 1, 0));
			GameRegistry.addRecipe(new ItemStack(bootsOak), "x x", "x x", 'x', new ItemStack(Block.wood, 1, 0));
			GameRegistry.addRecipe(new ItemStack(helmetSpruce), "xxx", "x x", 'x', new ItemStack(Block.wood, 1, 1));
			GameRegistry.addRecipe(new ItemStack(plateSpruce), "x x", "xxx", "xxx", 'x', new ItemStack(Block.wood, 1, 1));
			GameRegistry.addRecipe(new ItemStack(legsSpruce), "xxx", "x x", "x x", 'x', new ItemStack(Block.wood, 1, 1));
			GameRegistry.addRecipe(new ItemStack(bootsSpruce), "x x", "x x", 'x', new ItemStack(Block.wood, 1, 1));
			GameRegistry.addRecipe(new ItemStack(helmetBirch), "xxx", "x x", 'x', new ItemStack(Block.wood, 1, 2));
			GameRegistry.addRecipe(new ItemStack(plateBirch), "x x", "xxx", "xxx", 'x', new ItemStack(Block.wood, 1, 2));
			GameRegistry.addRecipe(new ItemStack(legsBirch), "xxx", "x x", "x x", 'x', new ItemStack(Block.wood, 1, 2));
			GameRegistry.addRecipe(new ItemStack(bootsBirch), "x x", "x x", 'x', new ItemStack(Block.wood, 1, 2));
			GameRegistry.addRecipe(new ItemStack(helmetJungle), "xxx", "x x", 'x', new ItemStack(Block.wood, 1, 3));
			GameRegistry.addRecipe(new ItemStack(plateJungle), "x x", "xxx", "xxx", 'x', new ItemStack(Block.wood, 1, 3));
			GameRegistry.addRecipe(new ItemStack(legsJungle), "xxx", "x x", "x x", 'x', new ItemStack(Block.wood, 1, 3));
			GameRegistry.addRecipe(new ItemStack(bootsJungle), "x x", "x x", 'x', new ItemStack(Block.wood, 1, 3));
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		/*
		 * We are forced to check whether the biomes we're trying to remove a
		 * spawning behavior from are not null, because there are a ton of null
		 * values in BiomeGenBase.biomeList, and so we would get a
		 * NullPointerException otherwise. We are also forced to wrap the
		 * EntityRegistry.removeSpawn-calls in an ugly try-catch statement with
		 * an empty catch, because calling this method with a mob name that is
		 * not known in the registry throws, you guessed it, a
		 * NullPointerException, because Mojang's code doesn't properly check
		 * for null values.
		 */
		if (MobFilter) {
			for (String mobName : MobFilterList) {
				for (BiomeGenBase biome : BiomeGenBase.biomeList) {
					if (biome != null) {
						try {
							EntityRegistry.removeSpawn(mobName,
									EnumCreatureType.ambient, biome);
							EntityRegistry.removeSpawn(mobName,
									EnumCreatureType.creature, biome);
							EntityRegistry.removeSpawn(mobName,
									EnumCreatureType.monster, biome);
							EntityRegistry.removeSpawn(mobName,
									EnumCreatureType.waterCreature, biome);
						} catch (NullPointerException e) {
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Removes all recipes from the CraftingManager's recipe list that have an
	 * output equal to resultItem.
	 * 
	 * @param resultItem
	 */
	private static void removeRecipe(ItemStack resultItem) 
	{
		ItemStack recipeResult;
		ArrayList<?> recipes = (ArrayList<?>) CraftingManager.getInstance().getRecipeList();

		for (int scan = 0; scan < recipes.size(); scan++) 
		{
			IRecipe tmpRecipe = (IRecipe) recipes.get(scan);
			recipeResult = tmpRecipe.getRecipeOutput();

			if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) 
			{
				recipes.remove(scan);
			}
		}
	}
}
