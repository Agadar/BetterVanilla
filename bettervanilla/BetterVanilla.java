package bettervanilla;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockGlowStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.src.ModLoader;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import bettervanilla.blocks.BlockBedOverride;
import bettervanilla.blocks.BlockCactusOverride;
import bettervanilla.blocks.BlockStairsCustom;
import bettervanilla.dispenserbehaviors.DispenserBehaviorShears;
import bettervanilla.dispenserbehaviors.DispenserBehaviorUniversal;
import bettervanilla.entities.PluckableChicken;
import bettervanilla.events.BonemealHook;
import bettervanilla.events.BreakHook;
import bettervanilla.events.EntityInteractHook;
import bettervanilla.events.HarvestDropsHook;
import bettervanilla.events.PlayerInteractHook;
import bettervanilla.items.BirchArmor;
import bettervanilla.items.CactusArmor;
import bettervanilla.items.ItemBedOverride;
import bettervanilla.items.JungleArmor;
import bettervanilla.items.MelonArmor;
import bettervanilla.items.MilkBottle;
import bettervanilla.items.OakArmor;
import bettervanilla.items.PumpkinArmor;
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

@Mod(modid = "BetterVanilla", name = "BetterVanilla", version = "0.1.9")
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
	public static boolean CheaperHoppers;
	public static boolean CraftableBottleOEnchant;
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
	public static boolean RottenFleshToLeather;
	public static boolean HardLeatherRecipe;
	public static boolean OtherMeats;
	public static int FleshyHideID;
	public static boolean Saddles;
	public static boolean SmeltableItems;
	
	// Blocks & Items
	public static Item milkBottle;
	
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
		String applesTweak = "Apples tweak";
		String crafting = "Crafting";
		String dropTweaks = "Drop tweaks";
		String milk = "Milk bottles";
		String misc = "Miscellaneous";
		String mobFil = "Mob filter";
		String moarArmor = "More armor";
		String moarStairs = "More stairs";
		String rotToLeath = "Rotten Flesh to Leather";
		
		// Load/create the configuration properties.
		Property apples = config.get(applesTweak, "Enabled", true);
		Property applesRate = config.get(applesTweak, "Drop rate", 5);
		Property beds = config.get(misc, "Colored beds", false);
		Property boneMeal = config.get(misc, "Bonemeal tweak", true);
		Property bookShelves = config.get(dropTweaks, "Bookshelves drop tweak", true);
		Property cacti = config.get(misc, "Cacti placement tweak", true);
		Property cauldrons = config.get(misc, "Cauldron tweak", true);
		Property cheaperHoppers = config.get(crafting, "Cheaper hoppers", true);
		Property craftableBottleOEnchant = config.get(crafting, "Craftable Bottle o' Enchanting", true);
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
		Property milkBottles = config.get(milk, "Milk bottles", true);
		Property milkBottlesID = config.get(milk, "Milk Bottle ID", 1060);
		Property mobFilter = config.get(mobFil, "Enabled", false);
		Property mobFilterList = config.get(mobFil, "Filter list", new String[] { "Example1", "Example2", "Example3" });
		Property moreArmor = config.get(moarArmor, "Enabled", true);
		Property moreArmorID = config.get(moarArmor, "Starting ID", 1011);
		Property moreStairs = config.get(moarStairs, "Enabled", true);
		Property moreStairsID = config.get(moarStairs, "Starting ID", 1050);
		Property stairsMaterials = config.get(moarStairs, "Materials", new String[] { "Stone", "Bookshelf" });
		Property mossStone = config.get(crafting, "Craftable moss stone", true);
		Property nametags = config.get(crafting, "Craftable nametags", true);
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
		cauldrons.comment = "Set to 'true' to allow players to wash away the dye from dyed wool and clay using a cauldron.";
		cheaperHoppers.comment = "Set to 'true' to replace the vanilla hopper recipe with a cheaper and more sensible one.";
		craftableBottleOEnchant.comment = "Set to 'true' to allow the crafting of Bottles o' Enchanting.";
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
		rottenFleshToLeather.comment = "Set to 'true' to allow rotten flesh to be smelted into leather or crafted into Fleshy Hides, depending on other settings.";
		hardLeatherRecipe.comment = "Set to 'true' to disable directly smelting rotten flesh into leather, instead introducing an intermediate product (Fleshy Hide).";
		otherMeats.comment = "Set to 'true' to allow other meats to be crafted into Fleshy Hides as well.";
		fleshyHideID.comment = "Sets the item ID of Fleshy Hide.";
		saddles.comment = "Set to 'true' to allow the crafting of saddles.";
		smeltableItems.comment = "Set to 'true' to allow most iron and golden items to be smelted back into ingots.";
				
		// Get the values of the configuration properties.
		Apples = apples.getBoolean(true);
		ApplesRate = applesRate.getDouble(5) * 2;
		Beds = beds.getBoolean(false);
		BoneMeal = boneMeal.getBoolean(true);
		BookShelves = bookShelves.getBoolean(true);
		Cacti = cacti.getBoolean(true);
		Cauldrons = cauldrons.getBoolean(true);
		CheaperHoppers = cheaperHoppers.getBoolean(true);
		CraftableBottleOEnchant = craftableBottleOEnchant.getBoolean(true);
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
		
		
		// Register our pluckable chicken and its spawn egg, and remove the normal chicken's spawn egg.
		//EntityList.entityEggs.remove(93);
		EntityRegistry.registerGlobalEntityID(PluckableChicken.class, "Chicken", EntityRegistry.findGlobalUniqueEntityId());//, 10592673, 16711680);
		
		MinecraftForge.EVENT_BUS.register(this);
		
		//EntityRegistry.removeSpawn(EntityChicken.class, EnumCreatureType.ambient, biomes);
		
		// Register rendering and sound stuff on the client side.
		proxy.registerRenderThings();
        proxy.registerSound();
        
        
		
        
        
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
		if (CheaperHoppers)
		{
			// Remove the old recipe for hoppers, and add the new one.
			removeRecipe(new ItemStack(Block.hopperBlock));
			GameRegistry.addRecipe(new ItemStack(Block.hopperBlock), "x x", "xyx", " x ", 'x', Block.stone, 'y', Item.redstone);
		}
		if (CraftableBottleOEnchant)
		{
			// Add the recipe for Bottle o' Enchanting.
			GameRegistry.addRecipe(new ItemStack(Item.expBottle), "xxx", "xyx", "xxx", 'x', Item.goldNugget, 'y', new ItemStack(Item.potion, 1, 16));
		}
		if (CraftableCobwebs) 
		{
			// Add the recipe for cobwebs.
			GameRegistry.addRecipe(new ItemStack(Block.web), "x x", " x ", "x x", 'x', Item.silk);
		}
		if (CraftableEndstone)
		{
			// Add the recipe for endstone.
			GameRegistry.addRecipe(new ItemStack(Block.whiteStone, 8), "xxx", "xyx", "xxx", 'x', Block.cobblestone, 'y', Item.enderPearl);
		}
		if (CraftableFlint)
		{
			// Add the recipe for flint.
			GameRegistry.addShapelessRecipe(new ItemStack(Item.flint), Block.gravel);
		}
		if (CraftableGrass)
		{
			// Add the recipes for the grass block and mycelium.
			GameRegistry.addShapelessRecipe(new ItemStack(Block.grass), Block.dirt, Item.seeds);
			GameRegistry.addShapelessRecipe(new ItemStack(Block.mycelium), Block.dirt, Block.mushroomBrown);
			GameRegistry.addShapelessRecipe(new ItemStack(Block.mycelium), Block.dirt, Block.mushroomRed);
		}
		if (CraftableRottenFlesh)
		{
			// Add the recipes for rotten flesh.
			GameRegistry.addShapelessRecipe(new ItemStack(Item.rottenFlesh, 2), Item.fermentedSpiderEye, Item.beefRaw);
			GameRegistry.addShapelessRecipe(new ItemStack(Item.rottenFlesh, 2), Item.fermentedSpiderEye, Item.porkRaw);
			GameRegistry.addShapelessRecipe(new ItemStack(Item.rottenFlesh, 2), Item.fermentedSpiderEye, Item.chickenRaw);
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
			// Add the horse armor recipes.
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
		if (MilkBottles)
		{
			// Register the Milk Bottle in the appropriate registers.
			milkBottle = (new MilkBottle(MilkBottlesID)).setUnlocalizedName("milkBottle").setTextureName("bettervanilla:milk_bottle");
			GameRegistry.registerItem(milkBottle, milkBottle.getUnlocalizedName());
			LanguageRegistry.addName(milkBottle, "Milk Bottle");
			
			// Register the event hook for using an empty bottle on a cow.
			MinecraftForge.EVENT_BUS.register(new EntityInteractHook());
			
			// Add a crafting recipe for Milk Bottle.
			GameRegistry.addShapelessRecipe(new ItemStack(milkBottle, 3), Item.bucketMilk, Item.glassBottle, Item.glassBottle, Item.glassBottle);
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
			OakArmor helmetOak = (OakArmor) new OakArmor(MoreArmorID++, armorWOOD, oak, 0).setUnlocalizedName("helmetOak").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:oak_helmet");
			OakArmor plateOak = (OakArmor) new OakArmor(MoreArmorID++, armorWOOD, oak, 1).setUnlocalizedName("plateOak").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:oak_chestplate");
			OakArmor legsOak = (OakArmor) new OakArmor(MoreArmorID++, armorWOOD, oak, 2).setUnlocalizedName("legsOak").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:oak_leggings");
			OakArmor bootsOak = (OakArmor) new OakArmor(MoreArmorID++, armorWOOD, oak, 3).setUnlocalizedName("bootsOak").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:oak_boots");
			SpruceArmor helmetSpruce = (SpruceArmor) new SpruceArmor(MoreArmorID++, armorWOOD, spruce, 0).setUnlocalizedName("helmetSpruce").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:spruce_helmet");
			SpruceArmor plateSpruce = (SpruceArmor) new SpruceArmor(MoreArmorID++, armorWOOD, spruce, 1).setUnlocalizedName("plateSpruce").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:spruce_chestplate");
			SpruceArmor legsSpruce = (SpruceArmor) new SpruceArmor(MoreArmorID++, armorWOOD, spruce, 2).setUnlocalizedName("legsSpruce").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:spruce_leggings");
			SpruceArmor bootsSpruce = (SpruceArmor) new SpruceArmor(MoreArmorID++, armorWOOD, spruce, 3).setUnlocalizedName("bootsSpruce").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:spruce_boots");
			BirchArmor helmetBirch = (BirchArmor) new BirchArmor(MoreArmorID++, armorWOOD, birch, 0).setUnlocalizedName("helmetBirch").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:birch_helmet");
			BirchArmor plateBirch = (BirchArmor) new BirchArmor(MoreArmorID++, armorWOOD, birch, 1).setUnlocalizedName("plateBirch").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:birch_chestplate");
			BirchArmor legsBirch = (BirchArmor) new BirchArmor(MoreArmorID++, armorWOOD, birch, 2).setUnlocalizedName("legsBirch").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:birch_leggings");
			BirchArmor bootsBirch = (BirchArmor) new BirchArmor(MoreArmorID++, armorWOOD, birch, 3).setUnlocalizedName("bootsBirch").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:birch_boots");	
			JungleArmor helmetJungle = (JungleArmor) new JungleArmor(MoreArmorID++, armorWOOD, jungle, 0).setUnlocalizedName("helmetJungle").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:jungle_helmet");
			JungleArmor plateJungle = (JungleArmor) new JungleArmor(MoreArmorID++, armorWOOD, jungle, 1).setUnlocalizedName("plateJungle").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:jungle_chestplate");
			JungleArmor legsJungle = (JungleArmor) new JungleArmor(MoreArmorID++, armorWOOD, jungle, 2).setUnlocalizedName("legsJungle").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:jungle_leggings");
			JungleArmor bootsJungle = (JungleArmor) new JungleArmor(MoreArmorID++, armorWOOD, jungle, 3).setUnlocalizedName("bootsJungle").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:jungle_boots");
			PumpkinArmor helmetPumpkin = (PumpkinArmor) new PumpkinArmor(MoreArmorID++, armorPUMPKIN, pumpkin, 0).setUnlocalizedName("helmetPumpkin").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:pumpkin_helmet");
			PumpkinArmor platePumpkin = (PumpkinArmor) new PumpkinArmor(MoreArmorID++, armorPUMPKIN, pumpkin, 1).setUnlocalizedName("platePumpkin").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:pumpkin_chestplate");
			PumpkinArmor legsPumpkin = (PumpkinArmor) new PumpkinArmor(MoreArmorID++, armorPUMPKIN, pumpkin, 2).setUnlocalizedName("legsPumpkin").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:pumpkin_leggings");
			PumpkinArmor bootPumpkin = (PumpkinArmor) new PumpkinArmor(MoreArmorID++, armorPUMPKIN, pumpkin, 3).setUnlocalizedName("bootsPumpkin").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:pumpkin_boots");
			CactusArmor helmetCactus = (CactusArmor) new CactusArmor(MoreArmorID++, armorCACTUS, cactus, 0).setUnlocalizedName("helmetCactus").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:cactus_helmet");
			CactusArmor plateCactus = (CactusArmor) new CactusArmor(MoreArmorID++, armorCACTUS, cactus, 1).setUnlocalizedName("plateCactus").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:cactus_chestplate");
			CactusArmor legsCactus = (CactusArmor) new CactusArmor(MoreArmorID++, armorCACTUS, cactus, 2).setUnlocalizedName("legsCactus").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:cactus_leggings");
			CactusArmor bootCactus = (CactusArmor) new CactusArmor(MoreArmorID++, armorCACTUS, cactus, 3).setUnlocalizedName("bootsCactus").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:cactus_boots");
			MelonArmor helmetMelon = (MelonArmor) new MelonArmor(MoreArmorID++, armorMELON, melon, 0).setUnlocalizedName("helmetMelon").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:melon_helmet");
			MelonArmor plateMelon = (MelonArmor) new MelonArmor(MoreArmorID++, armorMELON, melon, 1).setUnlocalizedName("plateMelon").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:melon_chestplate");
			MelonArmor legsMelon = (MelonArmor) new MelonArmor(MoreArmorID++, armorMELON, melon, 2).setUnlocalizedName("legsMelon").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:melon_leggings");
			MelonArmor bootMelon = (MelonArmor) new MelonArmor(MoreArmorID++, armorMELON, melon, 3).setUnlocalizedName("bootsMelon").setCreativeTab(CreativeTabs.tabCombat).setTextureName("bettervanilla:melon_boots");
			
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
			LanguageRegistry.addName(helmetPumpkin, "Pumpkin Helmet");
			LanguageRegistry.addName(platePumpkin, "Pumpkin Chestplate");
			LanguageRegistry.addName(legsPumpkin, "Pumpkin Leggings");
			LanguageRegistry.addName(bootPumpkin, "Pumpkin Boots");
			LanguageRegistry.addName(helmetCactus, "Cactus Helmet");
			LanguageRegistry.addName(plateCactus, "Cactus Chestplate");
			LanguageRegistry.addName(legsCactus, "Cactus Leggings");
			LanguageRegistry.addName(bootCactus, "Cactus Boots");
			LanguageRegistry.addName(helmetMelon, "Melon Helmet");
			LanguageRegistry.addName(plateMelon, "Melon Chestplate");
			LanguageRegistry.addName(legsMelon, "Melon Leggings");
			LanguageRegistry.addName(bootMelon, "Melon Boots");
			
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
			GameRegistry.addRecipe(new ItemStack(bootsJungle), "x x", "x x", 'x', new ItemStack(Block.pumpkin));
			GameRegistry.addRecipe(new ItemStack(helmetPumpkin), "xxx", "x x", 'x', new ItemStack(Block.pumpkin));
			GameRegistry.addRecipe(new ItemStack(platePumpkin), "x x", "xxx", "xxx", 'x', new ItemStack(Block.pumpkin));
			GameRegistry.addRecipe(new ItemStack(legsPumpkin), "xxx", "x x", "x x", 'x', new ItemStack(Block.pumpkin));
			GameRegistry.addRecipe(new ItemStack(bootPumpkin), "x x", "x x", 'x', new ItemStack(Block.pumpkin));
			GameRegistry.addRecipe(new ItemStack(helmetCactus), "xxx", "x x", 'x', new ItemStack(Block.cactus));
			GameRegistry.addRecipe(new ItemStack(plateCactus), "x x", "xxx", "xxx", 'x', new ItemStack(Block.cactus));
			GameRegistry.addRecipe(new ItemStack(legsCactus), "xxx", "x x", "x x", 'x', new ItemStack(Block.cactus));
			GameRegistry.addRecipe(new ItemStack(bootCactus), "x x", "x x", 'x', new ItemStack(Block.cactus));
			GameRegistry.addRecipe(new ItemStack(helmetMelon), "xxx", "x x", 'x', new ItemStack(Block.melon));
			GameRegistry.addRecipe(new ItemStack(plateMelon), "x x", "xxx", "xxx", 'x', new ItemStack(Block.melon));
			GameRegistry.addRecipe(new ItemStack(legsMelon), "xxx", "x x", "x x", 'x', new ItemStack(Block.melon));
			GameRegistry.addRecipe(new ItemStack(bootMelon), "x x", "x x", 'x', new ItemStack(Block.melon));
		}
		if (MoreStairs)
		{
			// Add the new stairs styles to the appropiate registries.
			LanguageRegistry.addName(Block.stairsCobblestone, "Cobblestone Stairs");
			for (String blockName : StairsMaterials) 
			{
				for (Block block : Block.blocksList)
				{
					if (block != null && block.getLocalizedName().equals(blockName)) 
					{
						Block stairs = (new BlockStairsCustom(MoreStairsID++, block, 0)).setUnlocalizedName(blockName + "Stairs");
						GameRegistry.registerBlock(stairs, blockName + "Stairs");
						LanguageRegistry.addName(stairs, blockName + " Stairs");
						GameRegistry.addRecipe(new ItemStack(stairs, 4), "x  ", "xx ", "xxx", 'x', block);
					}
				}
			}
		}
		if (MossStone) {
			// Add the moss stone, mossy bricks, chiseled bricks, and cracked bricks recipes.
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
			// Add the name-tag recipe.
			GameRegistry.addRecipe(new ItemStack(Item.nameTag), "  x", " y ",
					"y  ", 'x', Item.silk, 'y', Item.paper);
		}
		if (Saddles) {
			// Add the saddle recipe.
			GameRegistry.addRecipe(new ItemStack(Item.saddle), "xxx", "yxy",
					"z z", 'x', Item.leather, 'y', Item.silk, 'z',
					Item.ingotIron);
		}
		if (SmeltableItems)
		{
			// Register all the smeltable recipes for iron.
			GameRegistry.addSmelting(Item.doorIron.itemID, new ItemStack(Item.ingotIron, 6), 0);
			GameRegistry.addSmelting(Item.bucketEmpty.itemID, new ItemStack(Item.ingotIron, 3), 0);
			FurnaceRecipes.smelting().addSmelting(Item.shears.itemID, 0, new ItemStack(Item.ingotIron, 2), 0);
			GameRegistry.addSmelting(Block.pressurePlateIron.blockID, new ItemStack(Item.ingotIron, 2), 0);
			FurnaceRecipes.smelting().addSmelting(Item.helmetIron.itemID, 0, new ItemStack(Item.ingotIron, 5), 0);
			FurnaceRecipes.smelting().addSmelting(Item.plateIron.itemID, 0, new ItemStack(Item.ingotIron, 8), 0);
			FurnaceRecipes.smelting().addSmelting(Item.legsIron.itemID, 0, new ItemStack(Item.ingotIron, 7), 0);
			FurnaceRecipes.smelting().addSmelting(Item.bootsIron.itemID, 0, new ItemStack(Item.ingotIron, 4), 0);
			GameRegistry.addSmelting(Item.minecartEmpty.itemID, new ItemStack(Item.ingotIron, 5), 0);
			GameRegistry.addSmelting(Item.cauldron.itemID, new ItemStack(Item.ingotIron, 7), 0);
			FurnaceRecipes.smelting().addSmelting(Item.swordIron.itemID, 0, new ItemStack(Item.ingotIron, 2), 0);
			FurnaceRecipes.smelting().addSmelting(Item.shovelIron.itemID, 0, new ItemStack(Item.ingotIron, 1), 0);
			FurnaceRecipes.smelting().addSmelting(Item.pickaxeIron.itemID, 0, new ItemStack(Item.ingotIron, 3), 0);
			FurnaceRecipes.smelting().addSmelting(Item.hoeIron.itemID, 0, new ItemStack(Item.ingotIron, 2), 0);
			FurnaceRecipes.smelting().addSmelting(Item.axeIron.itemID, 0, new ItemStack(Item.ingotIron, 3), 0);
			GameRegistry.addSmelting(Block.railDetector.blockID, new ItemStack(Item.ingotIron, 1), 0);
			GameRegistry.addSmelting(Block.railActivator.blockID, new ItemStack(Item.ingotIron, 1), 0);
			FurnaceRecipes.smelting().addSmelting(Item.flintAndSteel.itemID, 0, new ItemStack(Item.ingotIron, 1), 0);
			GameRegistry.addSmelting(Item.compass.itemID, new ItemStack(Item.ingotIron, 4), 0);
			FurnaceRecipes.smelting().addSmelting(Block.anvil.blockID, 0, new ItemStack(Item.ingotIron, 31), 0);
			GameRegistry.addSmelting(Block.pistonBase.blockID, new ItemStack(Item.ingotIron, 1), 0);
			GameRegistry.addSmelting(Block.pistonStickyBase.blockID, new ItemStack(Item.ingotIron, 5), 0);
			FurnaceRecipes.smelting().addSmelting(Item.helmetChain.itemID, 0, new ItemStack(Item.ingotIron, 5), 0);
			FurnaceRecipes.smelting().addSmelting(Item.plateChain.itemID, 0, new ItemStack(Item.ingotIron, 8), 0);
			FurnaceRecipes.smelting().addSmelting(Item.legsChain.itemID, 0, new ItemStack(Item.ingotIron, 7), 0);
			FurnaceRecipes.smelting().addSmelting(Item.bootsChain.itemID, 0, new ItemStack(Item.ingotIron, 4), 0);
			GameRegistry.addSmelting(Item.horseArmorIron.itemID, new ItemStack(Item.ingotIron, 6), 0);
			if (!CheaperHoppers) GameRegistry.addSmelting(Block.hopperBlock.blockID, new ItemStack(Item.ingotIron, 5), 0);
			
			// Register all the smeltable recipes for gold.
			GameRegistry.addSmelting(Item.pocketSundial.itemID, new ItemStack(Item.ingotGold, 4), 0);
			GameRegistry.addSmelting(Block.railPowered.blockID, new ItemStack(Item.ingotGold, 1), 0);
			FurnaceRecipes.smelting().addSmelting(Item.appleGold.itemID, 0, new ItemStack(Item.ingotGold, 8), 0);
			FurnaceRecipes.smelting().addSmelting(Item.appleGold.itemID, 1, new ItemStack(Block.blockGold, 8), 0);
			GameRegistry.addSmelting(Block.pressurePlateGold.blockID, new ItemStack(Item.ingotGold, 2), 0);
			FurnaceRecipes.smelting().addSmelting(Item.helmetGold.itemID, 0, new ItemStack(Item.ingotGold, 5), 0);
			FurnaceRecipes.smelting().addSmelting(Item.plateGold.itemID, 0, new ItemStack(Item.ingotGold, 8), 0);
			FurnaceRecipes.smelting().addSmelting(Item.legsGold.itemID, 0, new ItemStack(Item.ingotGold, 7), 0);
			FurnaceRecipes.smelting().addSmelting(Item.bootsGold.itemID, 0, new ItemStack(Item.ingotGold, 4), 0);
			FurnaceRecipes.smelting().addSmelting(Item.swordGold.itemID, 0, new ItemStack(Item.ingotGold, 2), 0);
			FurnaceRecipes.smelting().addSmelting(Item.shovelGold.itemID, 0, new ItemStack(Item.ingotGold, 1), 0);
			FurnaceRecipes.smelting().addSmelting(Item.pickaxeGold.itemID, 0, new ItemStack(Item.ingotGold, 3), 0);
			FurnaceRecipes.smelting().addSmelting(Item.hoeGold.itemID, 0, new ItemStack(Item.ingotGold, 2), 0);
			FurnaceRecipes.smelting().addSmelting(Item.axeGold.itemID, 0, new ItemStack(Item.ingotGold, 3), 0);
			GameRegistry.addSmelting(Item.horseArmorGold.itemID, new ItemStack(Item.ingotGold, 6), 0);
		}
		if (RottenFleshToLeather) 
		{
			// Add the rotten flesh to leather recipe.
			if (HardLeatherRecipe) 
			{
				Item fleshyHide = (new Item(FleshyHideID)).setUnlocalizedName("fleshy_hide").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("bettervanilla:fleshy_hide");
				LanguageRegistry.addName(fleshyHide, "Fleshy Hide");
				GameRegistry.addRecipe(new ItemStack(fleshyHide), "xx", "xx", 'x', Item.rottenFlesh);
				GameRegistry.addSmelting(fleshyHide.itemID, new ItemStack(Item.leather), 0.1F);
				if (OtherMeats) 
				{
					GameRegistry.addRecipe(new ItemStack(fleshyHide), "xx", "xx", 'x', Item.beefRaw);
					GameRegistry.addRecipe(new ItemStack(fleshyHide), "xx", "xx", 'x', Item.porkRaw);
					GameRegistry.addRecipe(new ItemStack(fleshyHide), "xx", "xx", 'x', Item.chickenRaw);
				}
				// Add the slimeballs recipe.
				if (CraftableSlimeBalls)
				{
					GameRegistry.addShapelessRecipe(new ItemStack(Item.slimeBall), fleshyHide, Item.bucketWater, new ItemStack(Item.dyePowder, 1, 2));
				}
			} 
			else 
			{
				GameRegistry.addSmelting(Item.rottenFlesh.itemID, new ItemStack(Item.leather), 0.1F);
			}
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

	@ForgeSubscribe
	public void onSpecialSpawn(EntityJoinWorldEvent event)
	{
		if (event.entity.getClass() == EntityChicken.class)
		{
			PluckableChicken chicken = new PluckableChicken(event.world);
			chicken.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.rotationYaw, event.entity.rotationPitch);
			chicken.setHealth(((EntityLivingBase) event.entity).getHealth());
			chicken.renderYawOffset = ((EntityLivingBase) event.entity).renderYawOffset;
	        event.world.spawnEntityInWorld(chicken);
			event.setCanceled(true);
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
