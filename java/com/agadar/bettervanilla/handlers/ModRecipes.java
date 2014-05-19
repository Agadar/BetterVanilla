package com.agadar.bettervanilla.handlers;

import java.util.ArrayList;

import com.agadar.bettervanilla.blocks.ModBlocks;
import com.agadar.bettervanilla.items.ModItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class ModRecipes
{
    /**
     * Adds the recipes.
     * Called in the preInit
     */
	public static void addRecipes() 
	{
        if (ModConfigurations.ColoredBeds)
        {
        	// Remove the recipe for the original bed item and add the recipes for our colored bed item's subitems.
        	removeRecipe(new ItemStack(Items.bed));	
			for (int i = 0; i < 16; i++) 
			{
				GameRegistry.addRecipe(new ItemStack(ModItems.colored_bed, 1, i),
						"xxx", "yyy", 'x', new ItemStack(Blocks.wool, 1, i), 'y', Blocks.planks);
			}
        }
        
        if (ModConfigurations.CauldronsLava)
        {
        	// Add a crafting recipe for Lava Bottle.
        	GameRegistry.addShapelessRecipe(new ItemStack(ModItems.lava_bottle, 3), Items.lava_bucket, Items.glass_bottle, Items.glass_bottle, Items.glass_bottle);
        }
        
        if (ModConfigurations.CheaperHoppers)
		{
			// Remove the old recipe for hoppers, and add the new one.
			removeRecipe(new ItemStack(Blocks.hopper));
			GameRegistry.addRecipe(new ItemStack(Blocks.hopper), "x x", "xyx", " x ", 'x', Blocks.stone, 'y', Items.redstone);
		}
        
		if (ModConfigurations.CraftableBottleOEnchant)
		{
			// Add the recipe for Bottle o' Enchanting.
			GameRegistry.addRecipe(new ItemStack(Items.experience_bottle), "xxx", "xyx", "xxx", 'x', Items.gold_nugget, 'y', new ItemStack(Items.potionitem, 1, 16));
		}
		
		if (ModConfigurations.CraftableClay)
		{
			// Add the recipe for clay.
			GameRegistry.addShapelessRecipe(new ItemStack(Items.clay_ball, 4), Blocks.dirt, Blocks.sand, Blocks.gravel, Items.water_bucket);
		}
		
		if (ModConfigurations.CraftableCobwebs) 
		{
			// Add the recipe for cobwebs.
			GameRegistry.addRecipe(new ItemStack(Blocks.web), "x x", " x ", "x x", 'x', Items.string);
		}
		
		if (ModConfigurations.CraftableEndstone)
		{
			// Add the recipe for endstone.
			GameRegistry.addRecipe(new ItemStack(Blocks.end_stone, 8), "xxx", "xyx", "xxx", 'x', Blocks.cobblestone, 'y', Items.ender_pearl);
		}
		
		if (ModConfigurations.CraftableFlint)
		{
			// Add the recipe for flint.
			GameRegistry.addShapelessRecipe(new ItemStack(Items.flint), Blocks.gravel);
		}
		
		if (ModConfigurations.CraftableGrass)
		{
			// Add the recipes for the grass block and mycelium.
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.grass), Blocks.dirt, Items.wheat_seeds);
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.mycelium), Blocks.dirt, Blocks.brown_mushroom);
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.mycelium), Blocks.dirt, Blocks.red_mushroom);
		}
		
		if (ModConfigurations.CraftableRottenFlesh)
		{
			// Add the recipes for rotten flesh.
			GameRegistry.addShapelessRecipe(new ItemStack(Items.rotten_flesh, 2), Items.fermented_spider_eye, Items.beef);
			GameRegistry.addShapelessRecipe(new ItemStack(Items.rotten_flesh, 2), Items.fermented_spider_eye, Items.porkchop);
			GameRegistry.addShapelessRecipe(new ItemStack(Items.rotten_flesh, 2), Items.fermented_spider_eye, Items.chicken);
		}
		
		if (ModConfigurations.HorseArmor) 
		{
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
		
		if (ModConfigurations.MilkBottles)
		{
			// Add a crafting recipe for Milk Bottle.
			GameRegistry.addShapelessRecipe(new ItemStack(ModItems.milk_bottle, 3), Items.milk_bucket, Items.glass_bottle, Items.glass_bottle, Items.glass_bottle);
		}
		
		if (ModConfigurations.MoreArmor)
		{
			// Add the crafting recipes for the armors.
			GameRegistry.addRecipe(new ItemStack(ModItems.oak_helmet), "xxx", "x x", 'x', new ItemStack(Blocks.log, 1, 0));
			GameRegistry.addRecipe(new ItemStack(ModItems.oak_chestplate), "x x", "xxx", "xxx", 'x', new ItemStack(Blocks.log, 1, 0));
			GameRegistry.addRecipe(new ItemStack(ModItems.oak_leggings), "xxx", "x x", "x x", 'x', new ItemStack(Blocks.log, 1, 0));
			GameRegistry.addRecipe(new ItemStack(ModItems.oak_boots), "x x", "x x", 'x', new ItemStack(Blocks.log, 1, 0));
			GameRegistry.addRecipe(new ItemStack(ModItems.spruce_helmet), "xxx", "x x", 'x', new ItemStack(Blocks.log, 1, 1));
			GameRegistry.addRecipe(new ItemStack(ModItems.spruce_chestplate), "x x", "xxx", "xxx", 'x', new ItemStack(Blocks.log, 1, 1));
			GameRegistry.addRecipe(new ItemStack(ModItems.spruce_leggings), "xxx", "x x", "x x", 'x', new ItemStack(Blocks.log, 1, 1));
			GameRegistry.addRecipe(new ItemStack(ModItems.spruce_boots), "x x", "x x", 'x', new ItemStack(Blocks.log, 1, 1));
			GameRegistry.addRecipe(new ItemStack(ModItems.birch_helmet), "xxx", "x x", 'x', new ItemStack(Blocks.log, 1, 2));
			GameRegistry.addRecipe(new ItemStack(ModItems.birch_chestplate), "x x", "xxx", "xxx", 'x', new ItemStack(Blocks.log, 1, 2));
			GameRegistry.addRecipe(new ItemStack(ModItems.birch_leggings), "xxx", "x x", "x x", 'x', new ItemStack(Blocks.log, 1, 2));
			GameRegistry.addRecipe(new ItemStack(ModItems.birch_boots), "x x", "x x", 'x', new ItemStack(Blocks.log, 1, 2));
			GameRegistry.addRecipe(new ItemStack(ModItems.jungle_helmet), "xxx", "x x", 'x', new ItemStack(Blocks.log, 1, 3));
			GameRegistry.addRecipe(new ItemStack(ModItems.jungle_chestplate), "x x", "xxx", "xxx", 'x', new ItemStack(Blocks.log, 1, 3));
			GameRegistry.addRecipe(new ItemStack(ModItems.jungle_leggings), "xxx", "x x", "x x", 'x', new ItemStack(Blocks.log, 1, 3));
			GameRegistry.addRecipe(new ItemStack(ModItems.jungle_boots), "x x", "x x", 'x', new ItemStack(Blocks.pumpkin));
			GameRegistry.addRecipe(new ItemStack(ModItems.pumpkin_helmet), "xxx", "x x", 'x', new ItemStack(Blocks.pumpkin));
			GameRegistry.addRecipe(new ItemStack(ModItems.pumpkin_chestplate), "x x", "xxx", "xxx", 'x', new ItemStack(Blocks.pumpkin));
			GameRegistry.addRecipe(new ItemStack(ModItems.pumpkin_leggings), "xxx", "x x", "x x", 'x', new ItemStack(Blocks.pumpkin));
			GameRegistry.addRecipe(new ItemStack(ModItems.pumpkin_boots), "x x", "x x", 'x', new ItemStack(Blocks.pumpkin));
			GameRegistry.addRecipe(new ItemStack(ModItems.melon_helmet), "xxx", "x x", 'x', new ItemStack(Blocks.melon_block));
			GameRegistry.addRecipe(new ItemStack(ModItems.melon_chestplate), "x x", "xxx", "xxx", 'x', new ItemStack(Blocks.melon_block));
			GameRegistry.addRecipe(new ItemStack(ModItems.melon_leggings), "xxx", "x x", "x x", 'x', new ItemStack(Blocks.melon_block));
			GameRegistry.addRecipe(new ItemStack(ModItems.melon_boots), "x x", "x x", 'x', new ItemStack(Blocks.melon_block));
			
			if (ModConfigurations.Cacti)
			{
				GameRegistry.addRecipe(new ItemStack(ModItems.cactus_helmet), "xxx", "x x", 'x', new ItemStack(ModBlocks.cactus));
				GameRegistry.addRecipe(new ItemStack(ModItems.cactus_chestplate), "x x", "xxx", "xxx", 'x', new ItemStack(ModBlocks.cactus));
				GameRegistry.addRecipe(new ItemStack(ModItems.cactus_leggings), "xxx", "x x", "x x", 'x', new ItemStack(ModBlocks.cactus));
				GameRegistry.addRecipe(new ItemStack(ModItems.cactus_boots), "x x", "x x", 'x', new ItemStack(ModBlocks.cactus));
			}
			else 
			{
				GameRegistry.addRecipe(new ItemStack(ModItems.cactus_helmet), "xxx", "x x", 'x', new ItemStack(Blocks.cactus));
				GameRegistry.addRecipe(new ItemStack(ModItems.cactus_chestplate), "x x", "xxx", "xxx", 'x', new ItemStack(Blocks.cactus));
				GameRegistry.addRecipe(new ItemStack(ModItems.cactus_leggings), "xxx", "x x", "x x", 'x', new ItemStack(Blocks.cactus));
				GameRegistry.addRecipe(new ItemStack(ModItems.cactus_boots), "x x", "x x", 'x', new ItemStack(Blocks.cactus));
			}
		}
		
		if (ModConfigurations.MossStone) 
		{
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
		
		if (ModConfigurations.Nametags) 
		{
			// Add the name-tag recipe.
			GameRegistry.addRecipe(new ItemStack(Items.name_tag), "  x", " y ",
					"y  ", 'x', Items.string, 'y', Items.paper);
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
