package com.agadar.bettervanilla.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

/**
 * This event is fired whenever a block has been harvested.
 * If the harvested block equals 'block', then the dropped
 * item(s) is replaced by 'newDrops'.
 *
 */
public class EventHarvestDrop2
{
	private final Block block;
	private final Item newDrops;
	
	public EventHarvestDrop2(Block block, Item newDrops)
	{
		this.block = block;
		this.newDrops = newDrops;
	}
	
	@SubscribeEvent
	public void onHarvestDrops(HarvestDropsEvent event) 
	{
		if (event.block == this.block) 
		{
			event.drops.clear();
			event.drops.add(new ItemStack(newDrops));
			event.dropChance = 1.0f;
		}
	}
}
