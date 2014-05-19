package com.agadar.bettervanilla;

import java.awt.Color;
import java.util.ArrayList;

import com.agadar.bettervanilla.CommonProxy;
import com.agadar.bettervanilla.handlers.ModConfigurations;
import com.agadar.bettervanilla.handlers.ModRecipes;
import com.agadar.bettervanilla.help.Reference;
import com.agadar.bettervanilla.blocks.BlockColoredBed;
import com.agadar.bettervanilla.blocks.BlockCactus2;
import com.agadar.bettervanilla.blocks.BlockLavaCauldron;
import com.agadar.bettervanilla.blocks.BlockWaterCauldron;
import com.agadar.bettervanilla.blocks.ModBlocks;
import com.agadar.bettervanilla.blocks.RenderWaterCauldron;
import com.agadar.bettervanilla.dispenserbehaviors.DispenserBehaviorShears;
import com.agadar.bettervanilla.dispenserbehaviors.DispenserBehaviorUniversal;
import com.agadar.bettervanilla.dispenserbehaviors.ModDispenserBehaviors;
import com.agadar.bettervanilla.entities.ModEntities;
import com.agadar.bettervanilla.events.EventBonemeal;
import com.agadar.bettervanilla.events.EventBreak;
import com.agadar.bettervanilla.events.EventEntityInteract;
import com.agadar.bettervanilla.events.EventEntityJoinWorld;
import com.agadar.bettervanilla.events.EventHarvestDrops;
import com.agadar.bettervanilla.events.ModEvents;
import com.agadar.bettervanilla.events.EventPlayerInteract;
import com.agadar.bettervanilla.items.ItemBirchArmor;
import com.agadar.bettervanilla.items.ItemCactusArmor;
import com.agadar.bettervanilla.items.ItemEnderPotion;
import com.agadar.bettervanilla.items.ItemColoredBed;
import com.agadar.bettervanilla.items.ItemJungleArmor;
import com.agadar.bettervanilla.items.ItemMelonArmor;
import com.agadar.bettervanilla.items.ItemMilkBottle;
import com.agadar.bettervanilla.items.ModItems;
import com.agadar.bettervanilla.items.ItemOakArmor;
import com.agadar.bettervanilla.items.ItemPumpkinArmor;
import com.agadar.bettervanilla.items.ItemSpruceArmor;
import com.agadar.bettervanilla.tileentities.ModTileEntities;
import com.agadar.bettervanilla.tileentities.TileEntityBedColor;

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
		
		ModTileEntities.registerTileEntities();
		
		ModItems.loadItems();
		
		ModRecipes.addRecipes();
		
		ModDispenserBehaviors.setDispenserBehaviors();
		
		ModEntities.registerEntities();
		
		ModEvents.subscribeEvents();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) 
	{	
		/*if (MoreStairs)
		{
			for (String blockName : StairsMaterials) 
			{
				for (Block block : Blocks.blocksList)
				{
					if (block != null && block.getLocalizedName().equals(blockName)) 
					{
						Block stairs = (new BlockStairs2(MoreStairsID++, block, 0)).setBlockName(blockName + "Stairs");
						GameRegistry.registerBlock(stairs, blockName + "Stairs");
						GameRegistry.addRecipe(new ItemStack(stairs, 4), "x  ", "xx ", "xxx", 'x', block);
					}
				}
			}
		}*/
		
		if (PluckableChickens)
		{
			// Register our pluckable chicken and its renderer.
			EntityRegistry.registerGlobalEntityID(EntityPluckableChicken.class, "Chicken", EntityRegistry.findGlobalUniqueEntityId());
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
}
