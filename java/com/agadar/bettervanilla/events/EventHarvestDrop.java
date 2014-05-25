package com.agadar.bettervanilla.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class EventHarvestDrop 
{
	private final Block block;
	
	public EventHarvestDrop(Block block)
	{
		this.block = block;
	}
	
	@SubscribeEvent
	public void onHarvestDrops(HarvestDropsEvent event) 
	{
		if (event.block == this.block) 
		{
			event.drops.clear();
			event.drops.add(new ItemStack(event.block));
			event.dropChance = 1.0f;
		}
	}
}
