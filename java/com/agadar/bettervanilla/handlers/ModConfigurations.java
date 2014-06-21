package com.agadar.bettervanilla.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ModConfigurations 
{
	// Retrieved values from the configuration file
	public static boolean Apples;
	public static double ApplesRate;
	public static boolean BoneMeal;
	public static boolean BookShelves;
	public static boolean Cacti;
	public static boolean CauldronsWash;
	public static boolean CauldronsLava;
	public static boolean CheaperHoppers;
	public static boolean ColoredBeds;
	public static boolean CraftableBottleOEnchant;
	public static boolean CraftableClay;
	public static boolean CraftableCobwebs;
	public static boolean CraftableEndstone;
	public static boolean CraftableFlint;
	public static boolean CraftableGrass;
	public static boolean CraftableRottenFlesh;
	public static boolean CraftableSlimeBalls;
	public static boolean Dispensers;
	public static boolean Doors;
	public static boolean EnderChests;
	public static boolean EnderPotions;
	public static boolean HorseArmor;
	public static boolean Ice;
	public static boolean MilkBottles;
	public static boolean MobFilter;
	public static String[] MobFilterList;
	public static boolean MoreArmor;
	public static boolean MossStone;
	public static boolean Nametags;
	public static boolean PluckableChickens;
	public static boolean RottenFleshToLeather;
	public static boolean HardLeatherRecipe;
	public static boolean OtherMeats;
	public static boolean Saddles;
	public static boolean SmeltableItems;
	
	public static void loadConfigurations(File file)
	{
		Configuration config = new Configuration(file);
		config.load();
		
		// Define the categories
		String animals = "Animals";
		String applesTweak = "Apples tweak";
		String crafting = "Crafting";
		String dropTweaks = "Drop tweaks";
		String pots = "Potions & Cauldrons";
		String misc = "Miscellaneous";
		String mobFil = "Mob filter";
		String moarArmor = "More armor";
		String rotToLeath = "Rotten Flesh to Leather";
		
		// Load/create the configuration properties
		Property apples = config.get(applesTweak, "Enabled", true);
		Property applesRate = config.get(applesTweak, "Drop rate", 5);
		Property boneMeal = config.get(misc, "Bonemeal tweak", true);
		Property bookShelves = config.get(dropTweaks, "Bookshelves drop tweak", true);
		Property cacti = config.get(misc, "Cacti placement tweak", true);
		Property cauldronsWash = config.get(pots, "Washable wool and clay", true);
		Property cauldronsLava = config.get(pots, "Lava inside cauldrons", true);
		Property cheaperHoppers = config.get(crafting, "Cheaper hoppers", true);
		Property coloredBeds = config.get(misc, "Colored beds", true);
		Property craftableBottleOEnchant = config.get(crafting, "Craftable Bottle o' Enchanting", true);
		Property craftableClay = config.get(crafting, "Craftable clay", true);
		Property craftableCobwebs = config.get(crafting, "Craftable cobwebs", true);
		Property craftableEndstone = config.get(crafting, "Craftable endstone", true);
		Property craftableFlint = config.get(crafting, "Craftable flint", true);
		Property craftableGrass = config.get(crafting, "Craftable grass", true);
		Property craftableRottenFlesh = config.get(crafting, "Craftable rotten flesh", true);
		Property craftableSlimeBalls = config.get(crafting, "Craftable slimeballs", true);
		Property dispensers = config.get(misc, "Dispenser overhaul", true);
		Property doors = config.get(misc, "Stackable doors", true);
		Property enderChests = config.get(dropTweaks, "Ender chest drop tweak", true);
		Property enderPotions = config.get(pots, "Potions of Ender", true);
		Property horseArmor = config.get(crafting, "Craftable horse armor", true);
		Property ice = config.get(dropTweaks, "Ice drop tweak", true);
		Property milkBottles = config.get(pots, "Milk bottles", true);
		Property mobFilter = config.get(mobFil, "Enabled", false);
		Property mobFilterList = config.get(mobFil, "Filter list", new String[] { "Example1", "Example2", "Example3" });
		Property moreArmor = config.get(moarArmor, "Enabled", true);
		Property mossStone = config.get(crafting, "Craftable moss stone", true);
		Property nametags = config.get(crafting, "Craftable nametags", true);
		Property pluckableChickens = config.get(animals, "Pluckable chickens", true);
		Property rottenFleshToLeather = config.get(rotToLeath, "Enabled", true);
		Property hardLeatherRecipe = config.get(rotToLeath, "Hard leather recipe", true);
		Property otherMeats = config.get(rotToLeath, "Other meats", true);
		Property saddles = config.get(crafting, "Craftable saddles", true);
		Property smeltableItems = config.get(crafting, "Smeltable Items", true);
		
		// Set the comments of the configuration properties
		apples.comment = "Set to 'true' to alter the drop rate of apples.";
		applesRate.comment = "The new drop rate of apples. Vanilla default is 0.5%. BetterVanilla default is 5%.";
		boneMeal.comment = "Set to 'true' to allow bonemeal to be used on cacti, sugar canes, and nether warts.";
		bookShelves.comment = "Set to 'true' to make bookshelves drop a book shelf upon destruction instead of books.";
		cacti.comment = "Set to 'true' to allow cacti to be placed beside solid blocks without breaking.";
		cauldronsWash.comment = "Set to 'true' to allow players to wash away the dye from dyed wool and clay using a cauldron.";
		cauldronsLava.comment = "Set to 'true' to allow lava to be placed within cauldrons, and to add the Lava Bottle into the game.";
		cheaperHoppers.comment = "Set to 'true' to replace the vanilla hopper recipe with a cheaper and more sensible one.";
		coloredBeds.comment = "Set to 'true' to allow the crafting of colored beds (adds 15 new beds to the game, each with a different wool color).";
		craftableBottleOEnchant.comment = "Set to 'true' to allow the crafting of Bottles o' Enchanting.";
		craftableClay.comment = "Set to 'true' to allow the crafting of clay.";
		craftableCobwebs.comment = "Set to 'true' to allow the crafting of cobwebs.";
		craftableEndstone.comment = "Set to 'true' to allow the crafting of endstone.";
		craftableFlint.comment = "Set to 'true' to allow the crafting of flint.";
		craftableGrass.comment = "Set to 'true' to allow the crafting of grass blocks and mycelium.";
		craftableRottenFlesh.comment = "Set to 'true' to allow the crafting of rotten flesh.";
		craftableSlimeBalls.comment = "Set to 'true' to allow the crafting of slimeballs. Requires the harder variant of the Rotten Flesh to Leather recipe to be enabled, as this recipe uses Fleshy Hide.";		
		dispensers.comment = "Set to 'true' to make dispensers place blocks, plant seeds, and use hoes and shears instead of dropping them as items.";
		doors.comment = "Set to 'true' to increase the maximum stack size of doors from 1 to 16.";	
		enderChests.comment = "Set to 'true' to make ender chests drop an ender chest upon destruction instead of obsidian blocks.";
		enderPotions.comment = "Set to 'true' to introduce Potions of Ender into the game.";
		horseArmor.comment = "Set to 'true' to allow the crafting of horse armors.";		
		ice.comment = "Set to 'true' to make ice blocks drop an ice block upon destruction instead of creating a water source when in a snowy biome.";
		milkBottles.comment = "Set to 'true' to allow the crafting of milk bottles, which, just like buckets of milk, cure poison effects.";		
		mobFilter.comment = "Set to 'true' to prevent the mobs of which the names are entered into the mob filter list from spawning naturally.";
		mobFilterList.comment = "Insert into this list the names of mobs you wish to stop from spawning naturally. "
				+ "Invalid or wrongly-typed mob names are ignored";
		moreArmor.comment = "Set to 'true' to allow the crafting of several new armor types. All new armor types have the same stats as leather armor.";
		mossStone.comment = "Set to 'true' to allow the crafting of moss stone, cracked stone bricks, mossy stone bricks, and chiseled stone bricks.";
		nametags.comment = "Set to 'true' to allow the crafting of nametags.";
		pluckableChickens.comment = "Set to 'true' to allow players to pluck chickens using shears.";
		rottenFleshToLeather.comment = "Set to 'true' to allow rotten flesh to be smelted into leather or crafted into Fleshy Hides, depending on other settings.";
		hardLeatherRecipe.comment = "Set to 'true' to disable directly smelting rotten flesh into leather, instead introducing an intermediate product (Fleshy Hide).";
		otherMeats.comment = "Set to 'true' to allow other meats to be crafted into Fleshy Hides as well.";
		saddles.comment = "Set to 'true' to allow the crafting of saddles.";
		smeltableItems.comment = "Set to 'true' to allow most iron and golden items to be smelted back into ingots.";
	
		// Retrieve the values of the configuration properties
		Apples = apples.getBoolean(true);
		ApplesRate = applesRate.getDouble(5) * 2;
		BoneMeal = boneMeal.getBoolean(true);
		BookShelves = bookShelves.getBoolean(true);
		Cacti = cacti.getBoolean(true);
		CauldronsWash = cauldronsWash.getBoolean(true);
		CauldronsLava = cauldronsLava.getBoolean(true);
		CheaperHoppers = cheaperHoppers.getBoolean(true);
		ColoredBeds = coloredBeds.getBoolean(true);
		CraftableBottleOEnchant = craftableBottleOEnchant.getBoolean(true);
		CraftableClay = craftableClay.getBoolean(true);
		CraftableCobwebs = craftableCobwebs.getBoolean(true);
		CraftableEndstone = craftableEndstone.getBoolean(true);
		CraftableFlint = craftableFlint.getBoolean(true);
		CraftableGrass = craftableGrass.getBoolean(true);
		CraftableRottenFlesh = craftableRottenFlesh.getBoolean(true);
		CraftableSlimeBalls = craftableSlimeBalls.getBoolean(true);
		Dispensers = dispensers.getBoolean(true);
		Doors = doors.getBoolean(true);
		EnderChests = enderChests.getBoolean(true);
		EnderPotions = enderPotions.getBoolean(true);
		HorseArmor = horseArmor.getBoolean(true);
		Ice = ice.getBoolean(true);
		MilkBottles = milkBottles.getBoolean(true);
		MobFilter = mobFilter.getBoolean(false);
		MobFilterList = mobFilterList.getStringList();
		MoreArmor = moreArmor.getBoolean(true);
		MossStone = mossStone.getBoolean(true);
		Nametags = nametags.getBoolean(true);
		PluckableChickens = pluckableChickens.getBoolean(true);
		RottenFleshToLeather = rottenFleshToLeather.getBoolean(true);
		HardLeatherRecipe = hardLeatherRecipe.getBoolean(true);
		OtherMeats = otherMeats.getBoolean(true);
		Saddles = saddles.getBoolean(true);
		SmeltableItems = smeltableItems.getBoolean(true);
		
		config.save();
	}
}
