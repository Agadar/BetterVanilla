package com.agadar.bettervanilla.eventhandler;

import com.agadar.bettervanilla.block.ModBlocks;
import com.agadar.bettervanilla.help.ModConfigurations;
import com.agadar.bettervanilla.item.ModItems;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;

/** Manages this mod's event handlers.  */
public class ModEventHandlers 
{
	/** Registers this mod's event handlers. */
	public static void registerModEventHandlers() 
	{
		// Register the event hook for increasing the drop rate of apples from leaves.
		if (ModConfigurations.Apples) MinecraftForge.EVENT_BUS.register(new HandlerApplesDrop());
		
		// Register the event hook for using bonemeal on reeds, cacti, and warts.
		if (ModConfigurations.BoneMeal) MinecraftForge.EVENT_BUS.register(new HandlerOnBonemeal());
		
		// Register the event hook for bookshelves dropping bookshelves instead of books.
		if (ModConfigurations.BookShelves) MinecraftForge.EVENT_BUS.register(new HandlerOnHarvestDrop(Blocks.bookshelf, Blocks.bookshelf));
		
		// Register the event hook for cacti dropping BetterVanilla cacti instead of vanilla cacti.
		if (ModConfigurations.Cacti) MinecraftForge.EVENT_BUS.register(new HandlerOnHarvestDrop(Blocks.cactus, ModBlocks.cactus));
		
		// Register the event hook for using clay or wool on a cauldron filled with water.
		if (ModConfigurations.CauldronsWash) MinecraftForge.EVENT_BUS.register(new HandlerCauldronWash());

		// Register the event hook for making vanilla Cauldrons drop WaterCauldrons.
		if (ModConfigurations.CauldronsContent) MinecraftForge.EVENT_BUS.register(new HandlerOnHarvestDrop2(Blocks.cauldron, ModItems.water_cauldron, 0));
		
		// Register the event hook for making vanilla Beds drop red ColoredBeds.
		if (ModConfigurations.ColoredBeds) MinecraftForge.EVENT_BUS.register(new HandlerOnHarvestDrop2(Blocks.bed, ModItems.colored_bed, 14));
		
		// Register the event hook for ender chests dropping ender chests instead of obsidian blocks.
		if (ModConfigurations.EnderChests) MinecraftForge.EVENT_BUS.register(new HandlerOnHarvestDrop(Blocks.ender_chest, Blocks.ender_chest));
		
		// Register the event hook for altering the ice block's item drop behavior.
		if (ModConfigurations.Ice) MinecraftForge.EVENT_BUS.register(new HandlerOnIceBreak());
		
		// Register the event hook for using an empty bottle on lava.
		if (ModConfigurations.MorePotions) MinecraftForge.EVENT_BUS.register(new HandlerLavaBottle());
		
		// Register the event hook for intercepting mob spawning for the mob filter module.
		if (ModConfigurations.MobFilter) MinecraftForge.EVENT_BUS.register(new HandlerMobFilter());
		
		// Register the event hook for intercepting chickens spawning for the pluckable chickens module.
		if (ModConfigurations.PluckableChickens) MinecraftForge.EVENT_BUS.register(new HandlerChickenIntercept());
	}
}
