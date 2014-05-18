package com.agadar.bettervanilla.events;

import com.agadar.bettervanilla.handlers.ModConfigurations;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class EventHarvestDrops {
	
	@SubscribeEvent
	public void onHarvestDrops(HarvestDropsEvent event) 
	{
		if ((event.block == Blocks.ender_chest && ModConfigurations.EnderChests) ||
				((event.block == Blocks.bookshelf && ModConfigurations.BookShelves))) 
		{
			event.drops.clear();
			event.drops.add(new ItemStack(event.block));
			event.dropChance = 1.0f;
		}
	}
}
