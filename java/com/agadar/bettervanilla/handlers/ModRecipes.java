package com.agadar.bettervanilla.handlers;

import java.util.ArrayList;

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
