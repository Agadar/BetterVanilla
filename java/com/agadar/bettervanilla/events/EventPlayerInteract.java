package com.agadar.bettervanilla.events;

import com.agadar.bettervanilla.blocks.ModBlocks;
import com.agadar.bettervanilla.handlers.ModConfigurations;
import com.agadar.bettervanilla.items.ModItems;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class EventPlayerInteract {

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) 
	{
		World world = event.entityPlayer.worldObj;
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, event.entityPlayer, true);

		if (event.action == Action.RIGHT_CLICK_BLOCK && movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectType.BLOCK)
		{
			int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
            Block block = world.getBlock(i, j, k);
    		ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
            
            if (block.getMaterial() == Material.lava && stack.getItem() == Items.glass_bottle && ModConfigurations.CauldronsLava)
            {
            	world.setBlockToAir(i, j, k);        	
    			event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
    			
    			if (!event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(ModItems.lava_bottle))) 
    			{
    				event.entityPlayer.dropItem(ModItems.lava_bottle, 1);
    			}
            }
            else if ((block == Blocks.cauldron || block == ModBlocks.water_cauldron) && ModConfigurations.CauldronsWash)
			{
				int metadata = world.getBlockMetadata(i, j, k);

				if (stack.getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay) && metadata > 0) 
				{
					int numberToWash = 16;

					if (numberToWash > stack.stackSize) 
					{
						numberToWash = stack.stackSize;
					}
					if (event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Blocks.hardened_clay, numberToWash))) 
					{
						stack.stackSize -= numberToWash;
						event.entityPlayer.inventoryContainer.detectAndSendChanges();
						world.setBlockMetadataWithNotify(i, j, k, metadata - 1, 2);
						event.useBlock = Result.DENY;
					}
				}
				else if (stack.getItem() == Item.getItemFromBlock(Blocks.wool) && stack.getItemDamage() != 0 && metadata > 0) 
				{
					int numberToWash = 16;

					if (numberToWash > stack.stackSize) 
					{
						numberToWash = stack.stackSize;
					}
					if (event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(Blocks.wool,numberToWash, 0))) 
					{
						stack.stackSize -= numberToWash;
						event.entityPlayer.inventoryContainer.detectAndSendChanges();
						world.setBlockMetadataWithNotify(i, j, k, metadata - 1, 2);
						event.useBlock = Result.DENY;
					}
				}		
			}
		}
	}
	
	// Directly copied from the Item class, is needed in order to properly discover whether a player clicked a fluid.
	private MovingObjectPosition getMovingObjectPositionFromPlayer(World par1World, EntityPlayer par2EntityPlayer, boolean par3)
    {
		float f = 1.0F;
        float f1 = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * f;
        float f2 = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * f;
        double d0 = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * (double)f;
        double d1 = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * (double)f + (double)(par1World.isRemote ? par2EntityPlayer.getEyeHeight() - par2EntityPlayer.getDefaultEyeHeight() : par2EntityPlayer.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
        double d2 = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * (double)f;
        Vec3 vec3 = par1World.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        if (par2EntityPlayer instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP)par2EntityPlayer).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        return par1World.func_147447_a(vec3, vec31, par3, !par3, false);
    }
}
