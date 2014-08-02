package com.agadar.bettervanilla;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import com.agadar.bettervanilla.CommonProxy;
import com.agadar.bettervanilla.help.ModConfigurations;
import com.agadar.bettervanilla.help.ModRecipes;
import com.agadar.bettervanilla.help.References;
import com.agadar.bettervanilla.block.ModBlocks;
import com.agadar.bettervanilla.dispenserbehavior.ModDispenserBehaviors;
import com.agadar.bettervanilla.entity.ModEntities;
import com.agadar.bettervanilla.eventhandler.ModEventHandlers;
import com.agadar.bettervanilla.item.ModItems;
import com.agadar.bettervanilla.potion.ModPotions;
import com.agadar.bettervanilla.tileentity.ModTileEntities;
import com.agadar.brewingapi.BrewingRecipes;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = References.MODID, version = References.VERSION, name = References.NAME, useMetadata = true)
public class BetterVanilla 
{		
	@Instance(value = References.NAME)
	public static BetterVanilla instance;

	@SidedProxy(clientSide = References.CLIENTSIDE, serverSide = References.SERVERSIDE)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{		
		ModConfigurations.loadConfigurations(event.getSuggestedConfigurationFile());
		
		ModBlocks.registerModBlocks();
		
		ModTileEntities.registerModTileEntities();
		
		ModItems.registerModItems();
		
		ModRecipes.registerModRecipes();
		
		ModDispenserBehaviors.registerModDispenserBehaviors();
		
		ModEntities.registerModEntities();
		
		ModEventHandlers.registerModEventHandlers();
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event)
	{
		/** Check whether Brewing-API is present. */
		if (!Loader.isModLoaded("brewingapi"))
		{
			System.err.println(References.NAME + ": Error while initializing the mod - Brewing-API could not be found!");
			return;
		}
		
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
	}
}
