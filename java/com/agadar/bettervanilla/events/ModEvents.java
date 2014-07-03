package com.agadar.bettervanilla.events;

import com.agadar.bettervanilla.blocks.ModBlocks;
import com.agadar.bettervanilla.handlers.ModConfigurations;
import com.agadar.bettervanilla.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;

public class ModEvents 
{
	/**
     * Subscribes all events.
     * Called in the preInit
     */
	public static void subscribeEvents() 
	{
		// Register the event hook for increasing the drop rate of apples from leaves.
		if (ModConfigurations.Apples) MinecraftForge.EVENT_BUS.register(new EventLeavesBreak());
		
		// Register the event hook for using bonemeal on reeds, cacti, and warts.
		if (ModConfigurations.BoneMeal) MinecraftForge.EVENT_BUS.register(new EventBonemeal());
		
		// Register the event hook for bookshelves dropping bookshelves instead of books.
		if (ModConfigurations.BookShelves) MinecraftForge.EVENT_BUS.register(new EventHarvestDrop(Blocks.bookshelf, Blocks.bookshelf));
		
		// Register the event hook for cacti dropping BetterVanilla cacti instead of vanilla cacti.
		if (ModConfigurations.Cacti) MinecraftForge.EVENT_BUS.register(new EventHarvestDrop(Blocks.cactus, ModBlocks.cactus));
		
		// Register the event hook for using clay or wool on a cauldron filled with water.
		if (ModConfigurations.CauldronsWash) MinecraftForge.EVENT_BUS.register(new EventCauldronWash());

		if (ModConfigurations.CauldronsLava)
		{
			// Register the event hook for using an empty bottle on lava.
			MinecraftForge.EVENT_BUS.register(new EventLavaBottle());
			
			// Register the event hook for making vanilla Cauldrons drop WaterCauldrons.
			MinecraftForge.EVENT_BUS.register(new EventHarvestDrop2(Blocks.cauldron, ModItems.water_cauldron, 0));
		}
		
		if (ModConfigurations.ColoredBeds)
		{
			// Register the event hook for making vanilla Beds drop red ColoredBeds.
			MinecraftForge.EVENT_BUS.register(new EventHarvestDrop2(Blocks.bed, ModItems.colored_bed, 14));
		}
		
		// Register the event hook for ender chests dropping ender chests instead of obsidian blocks.
		if (ModConfigurations.EnderChests) MinecraftForge.EVENT_BUS.register(new EventHarvestDrop(Blocks.ender_chest, Blocks.ender_chest));
		
		// Register the event hook for altering the ice block's item drop behavior.
		if (ModConfigurations.Ice) MinecraftForge.EVENT_BUS.register(new EventIceBreak());
		
		// Register the event hook for intercepting mob spawning for the mob filter module.
		if (ModConfigurations.MobFilter) MinecraftForge.EVENT_BUS.register(new EventMobFilter());
		
		// Register the event hook for intercepting chickens spawning for the pluckable chickens module.
		if (ModConfigurations.PluckableChickens) MinecraftForge.EVENT_BUS.register(new EventChickenIntercept());
	}
}
