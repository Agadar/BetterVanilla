package com.agadar.bettervanilla;

import java.awt.Color;
import java.util.ArrayList;

import com.agadar.bettervanilla.CommonProxy;
import com.agadar.bettervanilla.handlers.ModConfigurations;
import com.agadar.bettervanilla.handlers.ModRecipes;
import com.agadar.bettervanilla.help.Reference;
import com.agadar.bettervanilla.blocks.BlockBed;
import com.agadar.bettervanilla.blocks.BlockCactusOverride;
import com.agadar.bettervanilla.blocks.CauldronLava;
import com.agadar.bettervanilla.blocks.CauldronWater;
import com.agadar.bettervanilla.blocks.ModBlocks;
import com.agadar.bettervanilla.dispenserbehaviors.DispenserBehaviorShears;
import com.agadar.bettervanilla.dispenserbehaviors.DispenserBehaviorUniversal;
import com.agadar.bettervanilla.entities.ModEntities;
import com.agadar.bettervanilla.events.BonemealHook;
import com.agadar.bettervanilla.events.BreakHook;
import com.agadar.bettervanilla.events.EntityInteractHook;
import com.agadar.bettervanilla.events.EntityJoinWorldHook;
import com.agadar.bettervanilla.events.HarvestDropsHook;
import com.agadar.bettervanilla.events.ModEvents;
import com.agadar.bettervanilla.events.PlayerInteractHook;
import com.agadar.bettervanilla.items.BirchArmor;
import com.agadar.bettervanilla.items.CactusArmor;
import com.agadar.bettervanilla.items.EnderPotion;
import com.agadar.bettervanilla.items.ItemBedOverride;
import com.agadar.bettervanilla.items.JungleArmor;
import com.agadar.bettervanilla.items.MelonArmor;
import com.agadar.bettervanilla.items.MilkBottle;
import com.agadar.bettervanilla.items.ModItems;
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
import net.minecraft.item.ItemArmor;
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
import net.minecraftforge.common.util.EnumHelper;
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

@Mod(modid = Reference.MODID, version = Reference.VERSION)
public class BetterVanilla 
{		
	@Instance(value = "BetterVanilla")
	public static BetterVanilla instance;

	@SidedProxy(clientSide = "bettervanilla.client.ClientProxy", serverSide = "bettervanilla.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{		
		ModConfigurations.loadConfigurations(event.getSuggestedConfigurationFile());
		
		ModBlocks.loadBlocks();
		
		ModItems.loadItems();
		
		ModRecipes.addRecipes();
		
		ModEntities.registerEntities();
		
		ModEvents.subscribeEvents();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) 
	{	

		
        
		if (Beds) {
			// Replace the original bed item with our overridden bed item.
			Item.itemRegistry.putObject(par1Obj, par2Obj);;
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
			blockBedOverride = (new BlockBed()).setHardness(0.2F).setBlockTextureName("bettervanilla:bed");
			
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
			armorWOOD = EnumHelper.addArmorMaterial("WOOD", 5, new int[]{1, 3, 2, 1}, 15);
			armorPUMPKIN = EnumHelper.addArmorMaterial("PUMPKIN", 5, new int[]{1, 3, 2, 1}, 15);
			armorCACTUS = EnumHelper.addArmorMaterial("CACTUS", 5, new int[]{1, 3, 2, 1}, 15);
			armorMELON = EnumHelper.addArmorMaterial("MELON", 5, new int[]{1, 3, 2, 1}, 15);
			
			// Register whatever it is these integers represent.
			int oak = proxy.addArmor("oak");
			int spruce = proxy.addArmor("spruce");
			int birch = proxy.addArmor("birch");
			int jungle = proxy.addArmor("jungle");
			int pumpkin = proxy.addArmor("pumpkin");
			int cactus = proxy.addArmor("cactus");
			int melon = proxy.addArmor("melon");

			// Create our new armor items.
			OakArmor helmetOak = (OakArmor) new OakArmor(oak, 0).setUnlocalizedName("oak_helmet").setTextureName("bettervanilla:oak_helmet");
			OakArmor plateOak = (OakArmor) new OakArmor(oak, 1).setUnlocalizedName("oak_chestplate").setTextureName("bettervanilla:oak_chestplate");
			OakArmor legsOak = (OakArmor) new OakArmor(oak, 2).setUnlocalizedName("oak_leggings").setTextureName("bettervanilla:oak_leggings");
			OakArmor bootsOak = (OakArmor) new OakArmor(oak, 3).setUnlocalizedName("oak_boots").setTextureName("bettervanilla:oak_boots");
			SpruceArmor helmetSpruce = (SpruceArmor) new SpruceArmor(spruce, 0).setUnlocalizedName("spruce_helmet").setTextureName("bettervanilla:spruce_helmet");
			SpruceArmor plateSpruce = (SpruceArmor) new SpruceArmor(spruce, 1).setUnlocalizedName("spruce_chestplate").setTextureName("bettervanilla:spruce_chestplate");
			SpruceArmor legsSpruce = (SpruceArmor) new SpruceArmor(spruce, 2).setUnlocalizedName("spruce_leggings").setTextureName("bettervanilla:spruce_leggings");
			SpruceArmor bootsSpruce = (SpruceArmor) new SpruceArmor(spruce, 3).setUnlocalizedName("spruce_boots").setTextureName("bettervanilla:spruce_boots");
			BirchArmor helmetBirch = (BirchArmor) new BirchArmor(birch, 0).setUnlocalizedName("birch_helmet").setTextureName("bettervanilla:birch_helmet");
			BirchArmor plateBirch = (BirchArmor) new BirchArmor(birch, 1).setUnlocalizedName("birch_chestplate").setTextureName("bettervanilla:birch_chestplate");
			BirchArmor legsBirch = (BirchArmor) new BirchArmor(birch, 2).setUnlocalizedName("birch_leggings").setTextureName("bettervanilla:birch_leggings");
			BirchArmor bootsBirch = (BirchArmor) new BirchArmor(birch, 3).setUnlocalizedName("birch_boots").setTextureName("bettervanilla:birch_boots");	
			JungleArmor helmetJungle = (JungleArmor) new JungleArmor(jungle, 0).setUnlocalizedName("jungle_helmet").setTextureName("bettervanilla:jungle_helmet");
			JungleArmor plateJungle = (JungleArmor) new JungleArmor(jungle, 1).setUnlocalizedName("jungle_chestplate").setTextureName("bettervanilla:jungle_chestplate");
			JungleArmor legsJungle = (JungleArmor) new JungleArmor(jungle, 2).setUnlocalizedName("jungle_leggings").setTextureName("bettervanilla:jungle_leggings");
			JungleArmor bootsJungle = (JungleArmor) new JungleArmor(jungle, 3).setUnlocalizedName("jungle_boots").setTextureName("bettervanilla:jungle_boots");
			PumpkinArmor helmetPumpkin = (PumpkinArmor) new PumpkinArmor(pumpkin, 0).setUnlocalizedName("pumpkin_helmet").setTextureName("bettervanilla:pumpkin_helmet");
			PumpkinArmor platePumpkin = (PumpkinArmor) new PumpkinArmor(pumpkin, 1).setUnlocalizedName("pumpkin_chestplate").setTextureName("bettervanilla:pumpkin_chestplate");
			PumpkinArmor legsPumpkin = (PumpkinArmor) new PumpkinArmor(pumpkin, 2).setUnlocalizedName("pumpkin_leggings").setTextureName("bettervanilla:pumpkin_leggings");
			PumpkinArmor bootPumpkin = (PumpkinArmor) new PumpkinArmor(pumpkin, 3).setUnlocalizedName("pumpkin_boots").setTextureName("bettervanilla:pumpkin_boots");
			CactusArmor helmetCactus = (CactusArmor) new CactusArmor(cactus, 0).setUnlocalizedName("cactus_helmet").setTextureName("bettervanilla:cactus_helmet");
			CactusArmor plateCactus = (CactusArmor) new CactusArmor(cactus, 1).setUnlocalizedName("cactus_chestplate").setTextureName("bettervanilla:cactus_chestplate");
			CactusArmor legsCactus = (CactusArmor) new CactusArmor(cactus, 2).setUnlocalizedName("cactus_leggings").setTextureName("bettervanilla:cactus_leggings");
			CactusArmor bootCactus = (CactusArmor) new CactusArmor(cactus, 3).setUnlocalizedName("cactus_boots").setTextureName("bettervanilla:cactus_boots");
			MelonArmor helmetMelon = (MelonArmor) new MelonArmor(melon, 0).setUnlocalizedName("melon_helmet").setTextureName("bettervanilla:melon_helmet");
			MelonArmor plateMelon = (MelonArmor) new MelonArmor(melon, 1).setUnlocalizedName("melon_chestplate").setTextureName("bettervanilla:melon_chestplate");
			MelonArmor legsMelon = (MelonArmor) new MelonArmor(melon, 2).setUnlocalizedName("melon_leggings").setTextureName("bettervanilla:melon_leggings");
			MelonArmor bootMelon = (MelonArmor) new MelonArmor(melon, 3).setUnlocalizedName("melon_boots").setTextureName("bettervanilla:melon_boots");			
			
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
