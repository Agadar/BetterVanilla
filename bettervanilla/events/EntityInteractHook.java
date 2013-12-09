package bettervanilla.events;

import bettervanilla.BetterVanilla;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class EntityInteractHook 
{
	@ForgeSubscribe
	public void onEntityInteract(EntityInteractEvent event) 
	{
		if (!event.entityPlayer.worldObj.isRemote && event.entityPlayer.getCurrentEquippedItem() != null && 
				event.entityPlayer.getCurrentEquippedItem().itemID == Item.glassBottle.itemID && 
				event.target.getClass() == EntityCow.class) 
		{
			event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
			if (!event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(BetterVanilla.milkBottle)))
            {
				event.entityPlayer.dropPlayerItem(new ItemStack(BetterVanilla.milkBottle));
            } 
			else
            {
				event.entityPlayer.inventoryContainer.detectAndSendChanges();	
            }			
		}
	}
}
