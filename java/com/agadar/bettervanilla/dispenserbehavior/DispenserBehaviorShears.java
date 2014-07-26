package com.agadar.bettervanilla.dispenserbehavior;

import java.lang.ref.WeakReference;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class DispenserBehaviorShears extends BehaviorDefaultDispenseItem 
{
	/**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {        
    	World world = par1IBlockSource.getWorld(); 
    	
    	EnumFacing enumfacing = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
        int i = par1IBlockSource.getXInt() + enumfacing.getFrontOffsetX();
        int j = par1IBlockSource.getYInt() + enumfacing.getFrontOffsetY();
        int k = par1IBlockSource.getZInt() + enumfacing.getFrontOffsetZ();
        
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+1, k+1);
		@SuppressWarnings("unchecked")
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(IShearable.class, box);
		WeakReference<FakePlayer> fakePlayerEntity = new WeakReference<FakePlayer>(FakePlayerFactory.getMinecraft((WorldServer) world));
		ItemShears shears = (ItemShears) par2ItemStack.getItem();
		if (entities.size() > 0)
		{
			for (EntityLivingBase entity : entities)
			{
				if (((IShearable) entity).isShearable(par2ItemStack, entity.worldObj, (int)entity.posX, (int)entity.posY, (int)entity.posZ))
				{
					shears.itemInteractionForEntity(par2ItemStack, fakePlayerEntity.get(), entity);
					break;
				}
			}
        } 
		else 
        {     
			Block block = world.getBlock(i, j, k);
        	if (block instanceof IShearable) 
        	{
        		shears.onBlockStartBreak(par2ItemStack, i, j, k, fakePlayerEntity.get());
        		world.func_147480_a(i, j, k, false);
        	}
        }
    	return par2ItemStack;   
    }
}
