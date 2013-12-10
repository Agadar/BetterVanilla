package bettervanilla.events;

import java.util.ArrayList;
import java.util.Random;

import bettervanilla.BetterVanilla;
import bettervanilla.entities.PluckableChicken;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class EntityInteractHook {
	@ForgeSubscribe
	public void onEntityInteract(EntityInteractEvent event) 
	{
		if (event.entityPlayer.getCurrentEquippedItem() != null) 
		{
			ItemStack currentItem = event.entityPlayer.getCurrentEquippedItem();
			
			if (currentItem.itemID == Item.glassBottle.itemID && event.target.getClass() == EntityCow.class) 
			{
				event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
				
				if (!event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(BetterVanilla.milkBottle))) 
				{
					event.entityPlayer.dropPlayerItem(new ItemStack(BetterVanilla.milkBottle));
				}
			}
		}
	}
}
