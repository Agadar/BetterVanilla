package bettervanilla.events;

import bettervanilla.BetterVanilla;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class PlayerInteractHook {

	@ForgeSubscribe
	public void onPlayerInteract(PlayerInteractEvent event) 
	{
		World world = event.entityPlayer.worldObj;

		if (event.action == Action.RIGHT_CLICK_BLOCK) 
		{			
			int id = world.getBlockId(event.x, event.y, event.z);
			
			if (id == Block.cauldron.blockID)
			{
				ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
				int metadata = world.getBlockMetadata(event.x, event.y, event.z);

				if (stack.itemID == Block.stainedClay.blockID && metadata > 0) 
				{
					int numberToWash = 16;

					if (numberToWash > stack.stackSize) 
					{
						numberToWash = stack.stackSize;
					}
					if (event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Block.hardenedClay, numberToWash))) 
					{
						stack.stackSize -= numberToWash;
						event.entityPlayer.inventoryContainer.detectAndSendChanges();
						world.setBlockMetadataWithNotify(event.x, event.y,event.z, metadata - 1, 2);
						world.func_96440_m(event.x, event.y, event.z, Block.cauldron.blockID);
					}
				}
				else if (stack.itemID == Block.cloth.blockID && stack.getItemDamage() != 0 && metadata > 0) 
				{
					int numberToWash = 16;

					if (numberToWash > stack.stackSize) 
					{
						numberToWash = stack.stackSize;
					}
					if (event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Block.cloth,numberToWash, 0))) 
					{
						stack.stackSize -= numberToWash;
						event.entityPlayer.inventoryContainer.detectAndSendChanges();
						world.setBlockMetadataWithNotify(event.x, event.y, event.z, metadata - 1, 2);
						world.func_96440_m(event.x, event.y, event.z, Block.cauldron.blockID);
					}
				}		
			}
		}
	}
}
