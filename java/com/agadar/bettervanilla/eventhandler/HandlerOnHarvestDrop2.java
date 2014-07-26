package com.agadar.bettervanilla.eventhandler;

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
public class HandlerOnHarvestDrop2
{
	private final Block block;
	private final Item newDrops;
	private final int itemDamage;
	
	public HandlerOnHarvestDrop2(Block block, Item newDrops, int itemDamage)
	{
		this.block = block;
		this.newDrops = newDrops;
		this.itemDamage = itemDamage;
	}
	
	@SubscribeEvent
	public void onHarvestDrops(HarvestDropsEvent event) 
	{
		if (event.block == this.block) 
		{
			event.drops.clear();
			event.drops.add(new ItemStack(newDrops, 1, itemDamage));
			event.dropChance = 1.0f;
		}
	}
}
