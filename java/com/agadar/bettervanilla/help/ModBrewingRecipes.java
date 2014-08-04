package com.agadar.bettervanilla.help;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import com.agadar.bettervanilla.item.ModItems;
import com.agadar.bettervanilla.potion.ModPotions;
import com.agadar.brewingapi.BrewingRecipes;

/** Manages this mod's brewing recipes. */
public class ModBrewingRecipes 
{
	/** Registers this mod's brewing recipes. */
	public static void registerModBrewingRecipes() 
	{
		if (ModConfigurations.MorePotions)
		{
			/** Splash Lava Bottle recipe. */
			ItemStack inputStack = new ItemStack(ModItems.itemPotionBase, 1, 1);			
			List<PotionEffect> effects = new ArrayList<PotionEffect>();
			effects.add(new PotionEffect(ModPotions.fire.id, 1, 0));	
			BrewingRecipes.brewing().setEffects(inputStack, effects);
			ItemStack ingredientStack = new ItemStack(Items.gunpowder, 1, 0);
			ItemStack outputStack = inputStack.copy();
			outputStack.setItemDamage(16384);
			BrewingRecipes.brewing().addBrewing(inputStack, ingredientStack, outputStack);

			/** Splash Milk Bottle recipe. */
			inputStack = new ItemStack(ModItems.itemPotionBase, 1, 1);			
			effects = new ArrayList<PotionEffect>();
			effects.add(new PotionEffect(ModPotions.cure.id, 1, 0));	
			BrewingRecipes.brewing().setEffects(inputStack, effects);
			ingredientStack = new ItemStack(Items.gunpowder, 1, 0);
			outputStack = inputStack.copy();
			outputStack.setItemDamage(16384);
			BrewingRecipes.brewing().addBrewing(inputStack, ingredientStack, outputStack);

			/** Ender Potion recipe. */
			inputStack = new ItemStack(Items.potionitem, 1, 16);
			ingredientStack = new ItemStack(Items.ender_pearl, 1, 0);
			outputStack = new ItemStack(ModItems.itemPotionBase, 1, 1);
			effects = new ArrayList<PotionEffect>();
			effects.add(new PotionEffect(ModPotions.ender.id, 1, 0));	
			BrewingRecipes.brewing().setEffects(outputStack, effects);
			BrewingRecipes.brewing().addBrewing(inputStack, ingredientStack, outputStack);

			/** Splash Ender Potion recipe. */
			inputStack = new ItemStack(ModItems.itemPotionBase, 1, 1);		
			effects = new ArrayList<PotionEffect>();
			effects.add(new PotionEffect(ModPotions.ender.id, 1, 0));		
			BrewingRecipes.brewing().setEffects(inputStack, effects);	
			ingredientStack = new ItemStack(Items.gunpowder, 1, 0);
			outputStack = inputStack.copy();
			outputStack.setItemDamage(16384);
			BrewingRecipes.brewing().addBrewing(inputStack, ingredientStack, outputStack);
		}
	}
}
