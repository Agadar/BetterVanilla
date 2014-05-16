package com.agadar.bettervanilla.events;

import com.agadar.bettervanilla.BetterVanilla;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class EntityInteractHook {
	@SubscribeEvent
	public void onEntityInteract(EntityInteractEvent event) 
	{
		if (event.entityPlayer.getCurrentEquippedItem() != null) 
		{
			ItemStack currentItem = event.entityPlayer.getCurrentEquippedItem();
			
			if (currentItem.getItem() == Items.glass_bottle && event.target.getClass() == EntityCow.class) 
			{
				event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
				
				if (!event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(BetterVanilla.milkBottle))) 
				{
					event.entityPlayer.dropItem(BetterVanilla.milkBottle, 1);
				}
			}
		}
	}
}
