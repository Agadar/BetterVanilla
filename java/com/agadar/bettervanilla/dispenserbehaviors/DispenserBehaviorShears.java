package com.agadar.bettervanilla.dispenserbehaviors;

import java.util.List;

import com.agadar.bettervanilla.misc.FakeEntityPlayer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class DispenserBehaviorShears extends BehaviorDefaultDispenseItem 
{
	/**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {        
    	World world = par1IBlockSource.getWorld(); 
    	
    	EnumFacing enumfacing = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        int i = par1IBlockSource.getXInt() + enumfacing.getFrontOffsetX();
        int j = par1IBlockSource.getYInt() + enumfacing.getFrontOffsetY();
        int k = par1IBlockSource.getZInt() + enumfacing.getFrontOffsetZ();
        
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+1, k+1);
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(IShearable.class, box);
		FakeEntityPlayer fakePlayerEntity = new FakeEntityPlayer(world, null);
		ItemShears shears = (ItemShears) par2ItemStack.getItem();
		if (entities.size() > 0)
		{
			for (EntityLivingBase entity : entities)
			{
				if (((IShearable) entity).isShearable(par2ItemStack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ))
				{
					shears.itemInteractionForEntity(par2ItemStack, fakePlayerEntity, entity);
					break;
				}
			}
        } 
		else 
        {       
        	int blockId = world.getBlockId(i, j, k);
        	Block block = Block.blocksList[blockId];
        	if (block instanceof IShearable) 
        	{
        		shears.onBlockStartBreak(par2ItemStack, i, j, k, fakePlayerEntity);
        		world.destroyBlock(i, j, k, false);
        	}
        }
    	return par2ItemStack;   
    }
}
