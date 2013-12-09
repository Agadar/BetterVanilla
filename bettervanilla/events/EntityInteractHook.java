package bettervanilla.events;

import java.util.ArrayList;
import java.util.Random;

import bettervanilla.BetterVanilla;
import bettervanilla.entities.PluckedChicken;
import net.minecraft.block.Block;
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
			else if (currentItem.itemID == Item.shears.itemID && event.target.getClass() == EntityChicken.class && !event.entityPlayer.worldObj.isRemote)
			{
				// Kill the chicken dead, and spawn a plucked chicken in its place.
				event.target.setDead();
		        PluckedChicken pluckedChicken = new PluckedChicken(event.entityPlayer.worldObj);
		        pluckedChicken.setLocationAndAngles(event.target.posX, event.target.posY, event.target.posZ, event.target.rotationYaw, event.target.rotationPitch);
		        pluckedChicken.setHealth(((EntityLivingBase) event.target).getHealth());
		        pluckedChicken.renderYawOffset = ((EntityLivingBase)event.target).renderYawOffset;
		        event.entityPlayer.worldObj.spawnEntityInWorld(pluckedChicken);
		        
		        // Drop some feathers.
		        Random rand = new Random();
		        int feathersAmount = rand.nextInt(3) + 2;
		        ItemStack feathers = new ItemStack(Item.feather);
		        for (int x = 0; x < feathersAmount; x++)
		        {
		        	EntityItem ent = event.target.entityDropItem(feathers, 1.0F);
                    ent.motionY += rand.nextFloat() * 0.05F;
                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
		        }
			}
		}
	}
}
