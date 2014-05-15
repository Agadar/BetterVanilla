package com.agadar.bettervanilla;

import java.awt.Color;
import java.util.ArrayList;

import com.agadar.bettervanilla.CommonProxy;
import com.agadar.bettervanilla.blocks.BlockBedOverride;
import com.agadar.bettervanilla.blocks.BlockCactusOverride;
import com.agadar.bettervanilla.blocks.CauldronLava;
import com.agadar.bettervanilla.blocks.CauldronWater;
import com.agadar.bettervanilla.dispenserbehaviors.DispenserBehaviorShears;
import com.agadar.bettervanilla.dispenserbehaviors.DispenserBehaviorUniversal;
import com.agadar.bettervanilla.events.BonemealHook;
import com.agadar.bettervanilla.events.BreakHook;
import com.agadar.bettervanilla.events.EntityInteractHook;
import com.agadar.bettervanilla.events.EntityJoinWorldHook;
import com.agadar.bettervanilla.events.HarvestDropsHook;
import com.agadar.bettervanilla.events.PlayerInteractHook;
import com.agadar.bettervanilla.items.BirchArmor;
import com.agadar.bettervanilla.items.CactusArmor;
import com.agadar.bettervanilla.items.EnderPotion;
import com.agadar.bettervanilla.items.ItemBedOverride;
import com.agadar.bettervanilla.items.JungleArmor;
import com.agadar.bettervanilla.items.MelonArmor;
import com.agadar.bettervanilla.items.MilkBottle;
import com.agadar.bettervanilla.items.OakArmor;
import com.agadar.bettervanilla.items.PumpkinArmor;
import com.agadar.bettervanilla.items.SpruceArmor;
import com.agadar.bettervanilla.renderers.CauldronWaterRenderer;
import com.agadar.bettervanilla.tileentities.BedColor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = BetterVanilla.MODID, version = BetterVanilla.VERSION)
public class BetterVanilla 
{	
	// Mod data
	public static final String MODID = "BetterVanilla";
    public static final String VERSION = "0.2.1";	
	
	// Values from the configuration file.
	public static boolean Apples;
	public static double ApplesRate;
	public static boolean Beds;
	public static boolean BoneMeal;
	public static boolean BookShelves;
	public static boolean Cacti;
	public static boolean CauldronsWash;
	public static boolean CauldronsLava;
	public static int CauldronLavaID;
	public static int LavaPotionID;
	public static boolean CheaperHoppers;
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
	public static boolean HorseArmor;
	public static boolean Ice;
	public static boolean MilkBottles;
	public static int MilkBottlesID;
	public static boolean MobFilter;
	public static String[] MobFilterList;
	public static boolean MoreArmor;
	public static int MoreArmorID;
	public static boolean MoreStairs;
	public static int MoreStairsID;
	public static String[] StairsMaterials;
	public static boolean MossStone;
	public static boolean Nametags;
	public static boolean PluckableChickens;
	public static boolean RottenFleshToLeather;
	public static boolean HardLeatherRecipe;
	public static boolean OtherMeats;
	public static int FleshyHideID;
	public static boolean Saddles;
	public static boolean SmeltableItems;
	
	// Items
	public static Item milkBottle;
	public static Item itemBedOverride;
	public static Item lavaBottle;
	
	// Blocks
	public static Block blockBedOverride;
	public static CauldronWater cauldronWater;
	public static CauldronLava cauldronLava;
	
	// Renderers
	public static ISimpleBlockRenderingHandler cauldronWaterRenderer;
	
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
		
		// Some standard category names.
		String animals = "Animals";
		String applesTweak = "Apples tweak";
		String crafting = "Crafting";
		String dropTweaks = "Drop tweaks";
		String pots = "Potions & Cauldrons";
		String misc = "Miscellaneous";
		String mobFil = "Mob filter";
		String moarArmor = "More armor";
		String moarStairs = "More stairs";
		String rotToLeath = "Rotten Flesh to Leather";
		
		// Load/create the configuration properties.
		Property apples = config.get(applesTweak, "Enabled", true);
		Property applesRate = config.get(applesTweak, "Drop rate", 5);
		Property beds = config.get(misc, "Colored beds", true);
		Property boneMeal = config.get(misc, "Bonemeal tweak", true);
		Property bookShelves = config.get(dropTweaks, "Bookshelves drop tweak", true);
		Property cacti = config.get(misc, "Cacti placement tweak", true);
		Property cauldronsWash = config.get(pots, "Washable wool and clay", true);
		Property cauldronsLava = config.get(pots, "Lava inside cauldrons", true);
		Property cauldronLavaID = config.get(pots, "Lava Cauldron ID", 1070);
		Property lavaPotionID = config.get(pots, "Lava Bottle ID", 1071);
		Property cheaperHoppers = config.get(crafting, "Cheaper hoppers", true);
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
		Property horseArmor = config.get(crafting, "Craftable horse armor", true);
		Property ice = config.get(dropTweaks, "Ice drop tweak", true);
		Property milkBottles = config.get(pots, "Milk bottles", true);
		Property milkBottlesID = config.get(pots, "Milk Bottle ID", 1060);
		Property mobFilter = config.get(mobFil, "Enabled", false);
		Property mobFilterList = config.get(mobFil, "Filter list", new String[] { "Example1", "Example2", "Example3" });
		Property moreArmor = config.get(moarArmor, "Enabled", true);
		Property moreArmorID = config.get(moarArmor, "Starting ID", 1011);
		Property moreStairs = config.get(moarStairs, "Enabled", true);
		Property moreStairsID = config.get(moarStairs, "Starting ID", 1050);
		Property stairsMaterials = config.get(moarStairs, "Materials", new String[] { "Stone", "Bookshelf", "Glowstone" });
		Property mossStone = config.get(crafting, "Craftable moss stone", true);
		Property nametags = config.get(crafting, "Craftable nametags", true);
		Property pluckableChickens = config.get(animals, "Pluckable chickens", true);
		Property rottenFleshToLeather = config.get(rotToLeath, "Enabled", true);
		Property hardLeatherRecipe = config.get(rotToLeath, "Hard leather recipe", true);
		Property otherMeats = config.get(rotToLeath, "Other meats", true);
		Property fleshyHideID = config.get(rotToLeath, "Fleshy Hide ID", 1010);
		Property saddles = config.get(crafting, "Craftable saddles", true);
		Property smeltableItems = config.get(crafting, "Smeltable Items", true);

		// Set the comments of the configuration properties.
		apples.comment = "Set to 'true' to alter the drop rate of apples.";
		applesRate.comment = "The new drop rate of apples. Vanilla default is 0.5%. BetterVanilla default is 5%.";
		beds.comment = "Set to 'true' to allow the crafting of colored beds (adds 15 new beds to the game, each with a different wool color).";
		boneMeal.comment = "Set to 'true' to allow bonemeal to be used on cacti, sugar canes, and nether warts.";
		bookShelves.comment = "Set to 'true' to make bookshelves drop a book shelf upon destruction instead of books.";
		cacti.comment = "Set to 'true' to allow cacti to be placed beside solid blocks without breaking.";
		cauldronsWash.comment = "Set to 'true' to allow players to wash away the dye from dyed wool and clay using a cauldron.";
		cauldronsLava.comment = "Set to 'true' to allow lava to be placed within cauldrons, and to add the Lava Bottle into the game.";
		cauldronLavaID.comment = "Sets the block ID of the lava cauldron.";
		lavaPotionID.comment = "Sets the item ID of Lava Bottle.";
		cheaperHoppers.comment = "Set to 'true' to replace the vanilla hopper recipe with a cheaper and more sensible one.";
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
		horseArmor.comment = "Set to 'true' to allow the crafting of horse armors.";
		ice.comment = "Set to 'true' to make ice blocks drop an ice block upon destruction instead of creating a water source when in a snowy biome.";
		milkBottles.comment = "Set to 'true' to allow the crafting of milk bottles, which, just like buckets of milk, cure poison effects.";
		milkBottlesID.comment = "Sets the item ID of Milk Bottle.";
		mobFilter.comment = "Set to 'true' to prevent the mobs of which the names are entered into the mob filter list from spawning naturally.";
		mobFilterList.comment = "Insert into this list the names of mobs you wish to stop from spawning naturally. "
				+ "Invalid or wrongly-typed mob names are ignored. Ensure that this mod is loaded last if you want to prevent mobs added by "
				+ "other mods from spawning.";
		moreArmor.comment = "Set to 'true' to allow the crafting of several new armor types. All new armor types have the same stats as leather armor.";
		moreArmorID.comment = "Sets the starting ID of the More Armor ID range. As there are a total of 28 items in this range, the last ID in the range is this value plus 27.";
		moreStairs.comment = "Set to 'true' to allow the crafting of stairs made from the materials as written in the materials list in this module.";
		moreStairsID.comment = "Sets the starting ID of the More Stairs ID range. The last ID in the range is the starting ID plus the number of materials minus 1.";
		stairsMaterials.comment = "Insert into this list the names of blocks which you want to be able to craft into stairs. Invalid or wrongly-typed block names are ignored. Ensure that this mod is loaded last if you want to craft stairs out of blocks added by other mods.";
		mossStone.comment = "Set to 'true' to allow the crafting of moss stone, cracked stone bricks, mossy stone bricks, and chiseled stone bricks.";
		nametags.comment = "Set to 'true' to allow the crafting of nametags.";
		pluckableChickens.comment = "Set to 'true' to allow players to pluck chickens using shears.";
		rottenFleshToLeather.comment = "Set to 'true' to allow rotten flesh to be smelted into leather or crafted into Fleshy Hides, depending on other settings.";
		hardLeatherRecipe.comment = "Set to 'true' to disable directly smelting rotten flesh into leather, instead introducing an intermediate product (Fleshy Hide).";
		otherMeats.comment = "Set to 'true' to allow other meats to be crafted into Fleshy Hides as well.";
		fleshyHideID.comment = "Sets the item ID of Fleshy Hide.";
		saddles.comment = "Set to 'true' to allow the crafting of saddles.";
		smeltableItems.comment = "Set to 'true' to allow most iron and golden items to be smelted back into ingots.";
				
		// Get the values of the configuration properties.
		Apples = apples.getBoolean(true);
		ApplesRate = applesRate.getDouble(5) * 2;
		Beds = beds.getBoolean(true);
		BoneMeal = boneMeal.getBoolean(true);
		BookShelves = bookShelves.getBoolean(true);
		Cacti = cacti.getBoolean(true);
		CauldronsWash = cauldronsWash.getBoolean(true);
		CauldronsLava = cauldronsLava.getBoolean(true);
		CauldronLavaID = cauldronLavaID.getInt();
		LavaPotionID = lavaPotionID.getInt();
		CheaperHoppers = cheaperHoppers.getBoolean(true);
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
		HorseArmor = horseArmor.getBoolean(true);
		Ice = ice.getBoolean(true);
		MilkBottles = milkBottles.getBoolean(true);
		MilkBottlesID = milkBottlesID.getInt();
		MobFilter = mobFilter.getBoolean(false);
		MobFilterList = mobFilterList.getStringList();
		MoreArmor = moreArmor.getBoolean(true);
		MoreArmorID = moreArmorID.getInt(); 
		MoreStairs = moreStairs.getBoolean(true);
		MoreStairsID = moreStairsID.getInt();
		StairsMaterials = stairsMaterials.getStringList();
		MossStone = mossStone.getBoolean(true);
		Nametags = nametags.getBoolean(true);
		PluckableChickens = pluckableChickens.getBoolean(true);
		RottenFleshToLeather = rottenFleshToLeather.getBoolean(true);
		HardLeatherRecipe = hardLeatherRecipe.getBoolean(true);
		OtherMeats = otherMeats.getBoolean(true);
		FleshyHideID = fleshyHideID.getInt();
		Saddles = saddles.getBoolean(true);
		SmeltableItems = smeltableItems.getBoolean(true);
		
		config.save();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) 
	{	
		// testing ender potion
		Item enderPotion = (new EnderPotion(1100)).setUnlocalizedName("ender_potion").setTextureName("bettervanilla:ender_potion");
		GameRegistry.registerItem(enderPotion, enderPotion.getUnlocalizedName());
			
		// Register the event hook for using clay and wool on a cauldron, and for using a glass bottle on lava.
		// It is checked in the hook itself whether these modules are enabled. 
		MinecraftForge.EVENT_BUS.register(new PlayerInteractHook());
				
		// Register the event hook for intercepting chickens spawning for the pluckable chickens module, as well
		// as ALL spawning for the mob filter module. It is checked in the hook itself whether these modules are enabled.
		MinecraftForge.EVENT_BUS.register(new EntityJoinWorldHook());
		
        if (Apples || Ice) {
			// Register the event hook for increasing the drop rate of apples from leaves and altering the ice block's item drop behavior.
			MinecraftForge.EVENT_BUS.register(new BreakHook());
		}
		if (Beds) {
			// Replace the original bed item with our overridden bed item.
			int itemBedId = Items.bed.itemID;
			Items.itemsList[itemBedId] = null;
			itemBedOverride = (new ItemBedOverride(itemBedId)).setMaxStackSize(1).setUnlocalizedName("bed").setTextureName("bettervanilla:bed");
			
			// Remove the recipe for the original bed item and add the recipes for our overridden bed item's subitems.
			removeRecipe(new ItemStack(Items.bed));		
			for (int i = 0; i < 16; i++) CraftingManager.getInstance().addRecipe(new ItemStack(itemBedOverride, 1, i), 
					"xxx", "yyy", 'x', new ItemStack(Blocks.wool, 1, i), 'y', Blocks.planks);			
			
			// Replace the original bed block with our overridden bed block.
			int blockBedId = Blocks.bed.blockID;
			Blocks.blocksList[blockBedId] = null;
			blockBedOverride = (new BlockBedOverride()).setHardness(0.2F).setBlockTextureName("bettervanilla:bed");
			
			// Register the tile entity that is responsible for storing the bed's direction.
			GameRegistry.registerTileEntity(BedColor.class, "BedColor");
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
			int cactusId = Blocks.cactus.blockID;
			Blocks.blocksList[cactusId] = null;
			(new BlockCactusOverride(cactusId)).setHardness(0.4F)
					.setStepSound(Block.soundTypeCloth)
					.setBlockName("cactus").setBlockTextureName("cactus");
		}
		if (CauldronsLava)
		{
			// Replace the original cauldron block with our water cauldron block.
			int blockCauldronId = Blocks.cauldron.blockID;
			Blocks.blocksList[blockCauldronId] = null;
			cauldronWater = (CauldronWater) (new CauldronWater(blockCauldronId)).setHardness(2.0F).setBlockName("cauldron").setBlockTextureName("cauldron");
			
			// Add the lava cauldron block.
			cauldronLava = (CauldronLava) (new CauldronLava(CauldronLavaID)).setHardness(2.0F).setBlockName("cauldron").setBlockTextureName("cauldron");
			GameRegistry.registerBlock(cauldronLava, "cauldronLava");
			
			// Register the renderer for our cauldrons.
			cauldronWaterRenderer = new CauldronWaterRenderer();
			RenderingRegistry.registerBlockHandler(cauldronWaterRenderer);
			
			// Register the Lava Bottle in the appropriate registers.
			lavaBottle = (new LavaBottle(LavaPotionID)).setUnlocalizedName("lavaBottle").setTextureName("bettervanilla:lava_bottle");
			GameRegistry.registerItem(lavaBottle, lavaBottle.getUnlocalizedName());
			//LanguageRegistry.addName(lavaBottle, "Lava Bottle");
			
			// Add a crafting recipe for Lava Bottle.
			GameRegistry.addShapelessRecipe(new ItemStack(lavaBottle, 3), Items.lava_bucket, Items.glass_bottle, Items.glass_bottle, Items.glass_bottle);
		}
		if (CheaperHoppers)
		{
			// Remove the old recipe for hoppers, and add the new one.
			removeRecipe(new ItemStack(Blocks.hopper));
			GameRegistry.addRecipe(new ItemStack(Blocks.hopper), "x x", "xyx", " x ", 'x', Blocks.stone, 'y', Items.redstone);
		}
		if (CraftableBottleOEnchant)
		{
			// Add the recipe for Bottle o' Enchanting.
			GameRegistry.addRecipe(new ItemStack(Items.experience_bottle), "xxx", "xyx", "xxx", 'x', Items.gold_nugget, 'y', new ItemStack(Items.potionitem, 1, 16));
		}
		if (CraftableClay)
		{
			// Add the recipe for clay.
			GameRegistry.addShapelessRecipe(new ItemStack(Items.clay_ball, 4), Blocks.dirt, Blocks.sand, Blocks.gravel, Items.water_bucket);
		}
		if (CraftableCobwebs) 
		{
			// Add the recipe for cobwebs.
			GameRegistry.addRecipe(new ItemStack(Blocks.web), "x x", " x ", "x x", 'x', Items.string);
		}
		if (CraftableEndstone)
		{
			// Add the recipe for endstone.
			GameRegistry.addRecipe(new ItemStack(Blocks.end_stone, 8), "xxx", "xyx", "xxx", 'x', Blocks.cobblestone, 'y', Items.ender_pearl);
		}
		if (CraftableFlint)
		{
			// Add the recipe for flint.
			GameRegistry.addShapelessRecipe(new ItemStack(Items.flint), Blocks.gravel);
		}
		if (CraftableGrass)
		{
			// Add the recipes for the grass block and mycelium.
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.grass), Blocks.dirt, Items.wheat_seeds);
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.mycelium), Blocks.dirt, Blocks.brown_mushroom);
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.mycelium), Blocks.dirt, Blocks.red_mushroom);
		}
		if (CraftableRottenFlesh)
		{
			// Add the recipes for rotten flesh.
			GameRegistry.addShapelessRecipe(new ItemStack(Items.rotten_flesh, 2), Items.fermented_spider_eye, Items.beef);
			GameRegistry.addShapelessRecipe(new ItemStack(Items.rotten_flesh, 2), Items.fermented_spider_eye, Items.porkchop);
			GameRegistry.addShapelessRecipe(new ItemStack(Items.rotten_flesh, 2), Items.fermented_spider_eye, Items.chicken);
		}
		if (Dispensers) {
			// Register our dispenser behaviors
			BlockDispenser.dispenseBehaviorRegistry.putObject(Items.shears,
					new DispenserBehaviorShears());
			BlockDispenser.dispenseBehaviorRegistry.putObject(Items.reeds,
					new DispenserBehaviorUniversal());

			for (Item item : Items.itemsList) {
				if (item instanceof ItemBlock || item instanceof IPlantable
						|| item instanceof ItemHoe) {
					BlockDispenser.dispenseBehaviorRegistry.putObject(item,
							new DispenserBehaviorUniversal());
				}
			}
		}
		if (Doors) {
			// Increase the maximum stack size from 1 to 16.
			Items.wooden_door.setMaxStackSize(16);
			Items.iron_door.setMaxStackSize(16);
		}
		if (HorseArmor) {
			// Add the horse armor recipes.
			GameRegistry.addRecipe(new ItemStack(Items.golden_horse_armor), "  x",
					"xyx", "xxx", 'x', Items.gold_ingot, 'y', new ItemStack(
							Blocks.wool, 1, 14));
			GameRegistry.addRecipe(new ItemStack(Items.iron_horse_armor), "  x",
					"xyx", "xxx", 'x', Items.iron_ingot, 'y', new ItemStack(
							Blocks.wool, 1, 15));
			GameRegistry.addRecipe(new ItemStack(Items.diamond_horse_armor),
					"  x", "xyx", "xxx", 'x', Items.diamond, 'y', new ItemStack(
							Blocks.wool, 1, 11));
		}
		if (MilkBottles)
		{
			// Register the Milk Bottle in the appropriate registers.
			milkBottle = (new MilkBottle(MilkBottlesID)).setUnlocalizedName("milkBottle").setTextureName("bettervanilla:milk_bottle");
			GameRegistry.registerItem(milkBottle, milkBottle.getUnlocalizedName());
			//LanguageRegistry.addName(milkBottle, "Milk Bottle");
			
			// Register the event hook for using an empty bottle on a cow.
			MinecraftForge.EVENT_BUS.register(new EntityInteractHook());
			
			// Add a crafting recipe for Milk Bottle.
			GameRegistry.addShapelessRecipe(new ItemStack(milkBottle, 3), Items.milk_bucket, Items.glass_bottle, Items.glass_bottle, Items.glass_bottle);
		}
		if (MoreArmor) {
			// Create our new armor material.
			EnumArmorMaterial armorWOOD = net.minecraftforge.common.EnumHelper.addArmorMaterial("WOOD", 5, new int[]{1, 3, 2, 1}, 15);
			EnumArmorMaterial armorPUMPKIN = net.minecraftforge.common.EnumHelper.addArmorMaterial("PUMPKIN", 5, new int[]{1, 3, 2, 1}, 15);
			EnumArmorMaterial armorCACTUS = net.minecraftforge.common.EnumHelper.addArmorMaterial("CACTUS", 5, new int[]{1, 3, 2, 1}, 15);
			EnumArmorMaterial armorMELON = net.minecraftforge.common.EnumHelper.addArmorMaterial("MELON", 5, new int[]{1, 3, 2, 1}, 15);
			
			// Register whatever it is these integers represent.
			int oak = proxy.addArmor("oak");
			int spruce = proxy.addArmor("spruce");
			int birch = proxy.addArmor("birch");
			int jungle = proxy.addArmor("jungle");
			int pumpkin = proxy.addArmor("pumpkin");
			int cactus = proxy.addArmor("cactus");
			int melon = proxy.addArmor("melon");

			// Create our new armor items.
			OakArmor helmetOak = (OakArmor) new OakArmor(MoreArmorID++, armorWOOD, oak, 0).setUnlocalizedName("oak_helmet").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:oak_helmet");
			OakArmor plateOak = (OakArmor) new OakArmor(MoreArmorID++, armorWOOD, oak, 1).setUnlocalizedName("oak_chestplate").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:oak_chestplate");
			OakArmor legsOak = (OakArmor) new OakArmor(MoreArmorID++, armorWOOD, oak, 2).setUnlocalizedName("oak_leggings").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:oak_leggings");
			OakArmor bootsOak = (OakArmor) new OakArmor(MoreArmorID++, armorWOOD, oak, 3).setUnlocalizedName("oak_boots").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:oak_boots");
			SpruceArmor helmetSpruce = (SpruceArmor) new SpruceArmor(MoreArmorID++, armorWOOD, spruce, 0).setUnlocalizedName("spruce_helmet").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:spruce_helmet");
			SpruceArmor plateSpruce = (SpruceArmor) new SpruceArmor(MoreArmorID++, armorWOOD, spruce, 1).setUnlocalizedName("spruce_chestplate").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:spruce_chestplate");
			SpruceArmor legsSpruce = (SpruceArmor) new SpruceArmor(MoreArmorID++, armorWOOD, spruce, 2).setUnlocalizedName("spruce_leggings").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:spruce_leggings");
			SpruceArmor bootsSpruce = (SpruceArmor) new SpruceArmor(MoreArmorID++, armorWOOD, spruce, 3).setUnlocalizedName("spruce_boots").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:spruce_boots");
			BirchArmor helmetBirch = (BirchArmor) new BirchArmor(MoreArmorID++, armorWOOD, birch, 0).setUnlocalizedName("birch_helmet").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:birch_helmet");
			BirchArmor plateBirch = (BirchArmor) new BirchArmor(MoreArmorID++, armorWOOD, birch, 1).setUnlocalizedName("birch_chestplate").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:birch_chestplate");
			BirchArmor legsBirch = (BirchArmor) new BirchArmor(MoreArmorID++, armorWOOD, birch, 2).setUnlocalizedName("birch_leggings").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:birch_leggings");
			BirchArmor bootsBirch = (BirchArmor) new BirchArmor(MoreArmorID++, armorWOOD, birch, 3).setUnlocalizedName("birch_boots").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:birch_boots");	
			JungleArmor helmetJungle = (JungleArmor) new JungleArmor(MoreArmorID++, armorWOOD, jungle, 0).setUnlocalizedName("jungle_helmet").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:jungle_helmet");
			JungleArmor plateJungle = (JungleArmor) new JungleArmor(MoreArmorID++, armorWOOD, jungle, 1).setUnlocalizedName("jungle_chestplate").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:jungle_chestplate");
			JungleArmor legsJungle = (JungleArmor) new JungleArmor(MoreArmorID++, armorWOOD, jungle, 2).setUnlocalizedName("jungle_leggings").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:jungle_leggings");
			JungleArmor bootsJungle = (JungleArmor) new JungleArmor(MoreArmorID++, armorWOOD, jungle, 3).setUnlocalizedName("jungle_boots").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:jungle_boots");
			PumpkinArmor helmetPumpkin = (PumpkinArmor) new PumpkinArmor(MoreArmorID++, armorPUMPKIN, pumpkin, 0).setUnlocalizedName("pumpkin_helmet").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:pumpkin_helmet");
			PumpkinArmor platePumpkin = (PumpkinArmor) new PumpkinArmor(MoreArmorID++, armorPUMPKIN, pumpkin, 1).setUnlocalizedName("pumpkin_chestplate").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:pumpkin_chestplate");
			PumpkinArmor legsPumpkin = (PumpkinArmor) new PumpkinArmor(MoreArmorID++, armorPUMPKIN, pumpkin, 2).setUnlocalizedName("pumpkin_leggings").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:pumpkin_leggings");
			PumpkinArmor bootPumpkin = (PumpkinArmor) new PumpkinArmor(MoreArmorID++, armorPUMPKIN, pumpkin, 3).setUnlocalizedName("pumpkin_boots").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:pumpkin_boots");
			CactusArmor helmetCactus = (CactusArmor) new CactusArmor(MoreArmorID++, armorCACTUS, cactus, 0).setUnlocalizedName("cactus_helmet").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:cactus_helmet");
			CactusArmor plateCactus = (CactusArmor) new CactusArmor(MoreArmorID++, armorCACTUS, cactus, 1).setUnlocalizedName("cactus_chestplate").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:cactus_chestplate");
			CactusArmor legsCactus = (CactusArmor) new CactusArmor(MoreArmorID++, armorCACTUS, cactus, 2).setUnlocalizedName("cactus_leggings").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:cactus_leggings");
			CactusArmor bootCactus = (CactusArmor) new CactusArmor(MoreArmorID++, armorCACTUS, cactus, 3).setUnlocalizedName("cactus_boots").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:cactus_boots");
			MelonArmor helmetMelon = (MelonArmor) new MelonArmor(MoreArmorID++, armorMELON, melon, 0).setUnlocalizedName("melon_helmet").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:melon_helmet");
			MelonArmor plateMelon = (MelonArmor) new MelonArmor(MoreArmorID++, armorMELON, melon, 1).setUnlocalizedName("melon_chestplate").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:melon_chestplate");
			MelonArmor legsMelon = (MelonArmor) new MelonArmor(MoreArmorID++, armorMELON, melon, 2).setUnlocalizedName("melon_leggings").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:melon_leggings");
			MelonArmor bootMelon = (MelonArmor) new MelonArmor(MoreArmorID++, armorMELON, melon, 3).setUnlocalizedName("melon_boots").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:melon_boots");			
			
			// Add the recipes of our items to the GameRegistry.
			GameRegistry.addRecipe(new ItemStack(helmetOak), "xxx", "x x", 'x', new ItemStack(Blocks.log, 1, 0));
			GameRegistry.addRecipe(new ItemStack(plateOak), "x x", "xxx", "xxx", 'x', new ItemStack(Blocks.log, 1, 0));
			GameRegistry.addRecipe(new ItemStack(legsOak), "xxx", "x x", "x x", 'x', new ItemStack(Blocks.log, 1, 0));
			GameRegistry.addRecipe(new ItemStack(bootsOak), "x x", "x x", 'x', new ItemStack(Blocks.log, 1, 0));
			GameRegistry.addRecipe(new ItemStack(helmetSpruce), "xxx", "x x", 'x', new ItemStack(Blocks.log, 1, 1));
			GameRegistry.addRecipe(new ItemStack(plateSpruce), "x x", "xxx", "xxx", 'x', new ItemStack(Blocks.log, 1, 1));
			GameRegistry.addRecipe(new ItemStack(legsSpruce), "xxx", "x x", "x x", 'x', new ItemStack(Blocks.log, 1, 1));
			GameRegistry.addRecipe(new ItemStack(bootsSpruce), "x x", "x x", 'x', new ItemStack(Blocks.log, 1, 1));
			GameRegistry.addRecipe(new ItemStack(helmetBirch), "xxx", "x x", 'x', new ItemStack(Blocks.log, 1, 2));
			GameRegistry.addRecipe(new ItemStack(plateBirch), "x x", "xxx", "xxx", 'x', new ItemStack(Blocks.log, 1, 2));
			GameRegistry.addRecipe(new ItemStack(legsBirch), "xxx", "x x", "x x", 'x', new ItemStack(Blocks.log, 1, 2));
			GameRegistry.addRecipe(new ItemStack(bootsBirch), "x x", "x x", 'x', new ItemStack(Blocks.log, 1, 2));
			GameRegistry.addRecipe(new ItemStack(helmetJungle), "xxx", "x x", 'x', new ItemStack(Blocks.log, 1, 3));
			GameRegistry.addRecipe(new ItemStack(plateJungle), "x x", "xxx", "xxx", 'x', new ItemStack(Blocks.log, 1, 3));
			GameRegistry.addRecipe(new ItemStack(legsJungle), "xxx", "x x", "x x", 'x', new ItemStack(Blocks.log, 1, 3));
			GameRegistry.addRecipe(new ItemStack(bootsJungle), "x x", "x x", 'x', new ItemStack(Blocks.pumpkin));
			GameRegistry.addRecipe(new ItemStack(helmetPumpkin), "xxx", "x x", 'x', new ItemStack(Blocks.pumpkin));
			GameRegistry.addRecipe(new ItemStack(platePumpkin), "x x", "xxx", "xxx", 'x', new ItemStack(Blocks.pumpkin));
			GameRegistry.addRecipe(new ItemStack(legsPumpkin), "xxx", "x x", "x x", 'x', new ItemStack(Blocks.pumpkin));
			GameRegistry.addRecipe(new ItemStack(bootPumpkin), "x x", "x x", 'x', new ItemStack(Blocks.pumpkin));
			GameRegistry.addRecipe(new ItemStack(helmetCactus), "xxx", "x x", 'x', new ItemStack(Blocks.cactus));
			GameRegistry.addRecipe(new ItemStack(plateCactus), "x x", "xxx", "xxx", 'x', new ItemStack(Blocks.cactus));
			GameRegistry.addRecipe(new ItemStack(legsCactus), "xxx", "x x", "x x", 'x', new ItemStack(Blocks.cactus));
			GameRegistry.addRecipe(new ItemStack(bootCactus), "x x", "x x", 'x', new ItemStack(Blocks.cactus));
			GameRegistry.addRecipe(new ItemStack(helmetMelon), "xxx", "x x", 'x', new ItemStack(Blocks.melon_block));
			GameRegistry.addRecipe(new ItemStack(plateMelon), "x x", "xxx", "xxx", 'x', new ItemStack(Blocks.melon_block));
			GameRegistry.addRecipe(new ItemStack(legsMelon), "xxx", "x x", "x x", 'x', new ItemStack(Blocks.melon_block));
			GameRegistry.addRecipe(new ItemStack(bootMelon), "x x", "x x", 'x', new ItemStack(Blocks.melon_block));
		}
		if (MoreStairs)
		{
			for (String blockName : StairsMaterials) 
			{
				for (Block block : Blocks.blocksList)
				{
					if (block != null && block.getLocalizedName().equals(blockName)) 
					{
						Block stairs = (new BlockStairsCustom(MoreStairsID++, block, 0)).setBlockName(blockName + "Stairs");
						GameRegistry.registerBlock(stairs, blockName + "Stairs");
						GameRegistry.addRecipe(new ItemStack(stairs, 4), "x  ", "xx ", "xxx", 'x', block);
					}
				}
			}
		}
		if (MossStone) {
			// Add the moss stone, mossy bricks, chiseled bricks, and cracked bricks recipes.
			GameRegistry.addRecipe(new ItemStack(Blocks.mossy_cobblestone, 8), "xxx", "xyx",
					"xxx", 'x', Blocks.cobblestone, 'y', Blocks.vine);
			GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick, 8, 1), "xxx", "xyx",
					"xxx", 'x', new ItemStack(Blocks.stonebrick, 1, 0), 'y', Blocks.vine);
			GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick, 4, 2), "xx",
					"xx", 'x', new ItemStack(Blocks.stonebrick, 1, 0));
			GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick, 1, 3), "x", "x",
					'x', new ItemStack(Blocks.stone_slab, 1, 5));
		}
		if (Nametags) {
			// Add the name-tag recipe.
			GameRegistry.addRecipe(new ItemStack(Items.name_tag), "  x", " y ",
					"y  ", 'x', Items.string, 'y', Items.paper);
		}
		if (PluckableChickens)
		{
			// Register our pluckable chicken and its renderer.
			EntityRegistry.registerGlobalEntityID(PluckableChicken.class, "Chicken", EntityRegistry.findGlobalUniqueEntityId());
			proxy.registerPluckableChicken();
		}
		if (Saddles) {
			// Add the saddle recipe.
			GameRegistry.addRecipe(new ItemStack(Items.saddle), "xxx", "yxy",
					"z z", 'x', Items.leather, 'y', Items.string, 'z',
					Items.iron_ingot);
		}
		if (SmeltableItems)
		{
			// Register all the smeltable recipes for iron.
			GameRegistry.addSmelting(Items.iron_door, new ItemStack(Items.iron_ingot, 6), 0);
			GameRegistry.addSmelting(Items.bucket, new ItemStack(Items.iron_ingot, 3), 0);
			GameRegistry.addSmelting(new ItemStack(Items.shears, 1, 0), new ItemStack(Items.iron_ingot, 2), 0);
			GameRegistry.addSmelting(Blocks.heavy_weighted_pressure_plate, new ItemStack(Items.iron_ingot, 2), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_helmet, 1, 0), new ItemStack(Items.iron_ingot, 5), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_chestplate, 1, 0), new ItemStack(Items.iron_ingot, 8), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_leggings, 1, 0), new ItemStack(Items.iron_ingot, 7), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_boots, 1, 0), new ItemStack(Items.iron_ingot, 4), 0);
			GameRegistry.addSmelting(Items.minecart, new ItemStack(Items.iron_ingot, 5), 0);
			GameRegistry.addSmelting(Items.cauldron, new ItemStack(Items.iron_ingot, 7), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_sword, 1, 0), new ItemStack(Items.iron_ingot, 2), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_shovel, 1, 0), new ItemStack(Items.iron_ingot, 1), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_pickaxe, 1, 0), new ItemStack(Items.iron_ingot, 3), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_hoe, 1, 0), new ItemStack(Items.iron_ingot, 2), 0);
			GameRegistry.addSmelting(new ItemStack(Items.iron_axe, 1, 0), new ItemStack(Items.iron_ingot, 3), 0);
			GameRegistry.addSmelting(Blocks.detector_rail, new ItemStack(Items.iron_ingot, 1), 0);
			GameRegistry.addSmelting(Blocks.activator_rail, new ItemStack(Items.iron_ingot, 1), 0);
			GameRegistry.addSmelting(new ItemStack(Items.flint_and_steel, 1, 0), new ItemStack(Items.iron_ingot, 1), 0);
			GameRegistry.addSmelting(Items.compass, new ItemStack(Items.iron_ingot, 4), 0);
			GameRegistry.addSmelting(new ItemStack(Blocks.anvil, 1, 0), new ItemStack(Items.iron_ingot, 31), 0);
			GameRegistry.addSmelting(Blocks.piston, new ItemStack(Items.iron_ingot, 1), 0);
			GameRegistry.addSmelting(Blocks.sticky_piston, new ItemStack(Items.iron_ingot, 5), 0);
			GameRegistry.addSmelting(new ItemStack(Items.chainmail_helmet, 1, 0), new ItemStack(Items.iron_ingot, 5), 0);
			GameRegistry.addSmelting(new ItemStack(Items.chainmail_chestplate, 1, 0), new ItemStack(Items.iron_ingot, 8), 0);
			GameRegistry.addSmelting(new ItemStack(Items.chainmail_leggings, 1, 0), new ItemStack(Items.iron_ingot, 7), 0);
			GameRegistry.addSmelting(new ItemStack(Items.chainmail_boots, 1, 0), new ItemStack(Items.iron_ingot, 4), 0);
			GameRegistry.addSmelting(Items.iron_horse_armor, new ItemStack(Items.iron_ingot, 6), 0);
			if (!CheaperHoppers) GameRegistry.addSmelting(Blocks.hopper, new ItemStack(Items.iron_ingot, 5), 0);
			
			// Register all the smeltable recipes for gold.
			GameRegistry.addSmelting(Items.clock, new ItemStack(Items.gold_ingot, 4), 0);
			GameRegistry.addSmelting(Blocks.golden_rail, new ItemStack(Items.gold_ingot, 1), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_apple, 1, 0), new ItemStack(Items.gold_ingot, 8), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_apple, 1, 1), new ItemStack(Blocks.gold_block, 8), 0);
			GameRegistry.addSmelting(Blocks.light_weighted_pressure_plate, new ItemStack(Items.gold_ingot, 2), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_helmet, 1, 0), new ItemStack(Items.gold_ingot, 5), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_chestplate, 1, 0), new ItemStack(Items.gold_ingot, 8), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_leggings, 1, 0), new ItemStack(Items.gold_ingot, 7), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_boots, 1, 0), new ItemStack(Items.gold_ingot, 4), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_sword, 1, 0), new ItemStack(Items.gold_ingot, 2), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_shovel, 1, 0), new ItemStack(Items.gold_ingot, 1), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_pickaxe, 1, 0), new ItemStack(Items.gold_ingot, 3), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_hoe, 1, 0), new ItemStack(Items.gold_ingot, 2), 0);
			GameRegistry.addSmelting(new ItemStack(Items.golden_axe, 1, 0), new ItemStack(Items.gold_ingot, 3), 0);
			GameRegistry.addSmelting(Items.golden_horse_armor, new ItemStack(Items.gold_ingot, 6), 0);
		}
		if (RottenFleshToLeather) 
		{
			// Add the rotten flesh to leather recipe.
			if (HardLeatherRecipe) 
			{
				Item fleshyHide = (new Item()).setUnlocalizedName("fleshy_hide").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("bettervanilla:fleshy_hide");
				GameRegistry.addRecipe(new ItemStack(fleshyHide), "xx", "xx", 'x', Items.rotten_flesh);
				GameRegistry.addSmelting(fleshyHide, new ItemStack(Items.leather), 0.1F);
				if (OtherMeats) 
				{
					GameRegistry.addRecipe(new ItemStack(fleshyHide), "xx", "xx", 'x', Items.beef);
					GameRegistry.addRecipe(new ItemStack(fleshyHide), "xx", "xx", 'x', Items.porkchop);
					GameRegistry.addRecipe(new ItemStack(fleshyHide), "xx", "xx", 'x', Items.chicken);
				}
				// Add the slimeballs recipe.
				if (CraftableSlimeBalls)
				{
					GameRegistry.addShapelessRecipe(new ItemStack(Items.slime_ball), fleshyHide, Items.water_bucket, new ItemStack(Items.dye, 1, 2));
				}
			} 
			else 
			{
				GameRegistry.addSmelting(Items.rotten_flesh, new ItemStack(Items.leather), 0.1F);
			}
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
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
