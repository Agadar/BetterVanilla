package com.agadar.bettervanilla.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EnderPotion extends Item
{
	public EnderPotion() 
	{
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabBrewing);
	}
	
	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		this.teleportPlayerRandomly(par2World, par3EntityPlayer);
		
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
        	--par1ItemStack.stackSize;
        	
        	if (par1ItemStack.stackSize <= 0) return new ItemStack(Items.glass_bottle);
        	
            if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle)))
	            par3EntityPlayer.dropItem(Items.glass_bottle, 1);
        }       
                
        return par1ItemStack;
    }

	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }

	@Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }

	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
	
	private void teleportPlayerRandomly(World par1World, EntityPlayer par2EntityPlayer)
	{       
        boolean flag = false;
        
        double x0 = par2EntityPlayer.posX;
        double y0 = par2EntityPlayer.posY;
        double z0 = par2EntityPlayer.posZ;
        
        while (!par1World.isRemote && !flag)
        {
        	double x1 = x0 + (par1World.rand.nextDouble() - 0.5D) * 64.0D;
        	double y1 = y0 + (double)(par1World.rand.nextInt(64) - 32);
        	double z1 = z0 + (par1World.rand.nextDouble() - 0.5D) * 64.0D;  

        	int i = MathHelper.floor_double(x1);
        	int j = MathHelper.floor_double(y1);
        	int k = MathHelper.floor_double(z1);
        	Block l;

        	if (par1World.blockExists(i, j, k))
        	{
        		boolean flag1 = false;

        		while (!flag1 && j > 0)
        		{
        			l = par1World.getBlock(i, j - 1, k);

        			if (l != Blocks.air && l.getMaterial().blocksMovement())
        			{
        				flag1 = true;
        			}
        			else
        			{
        				--y1;
        				--j;
        			}
        		}

        		if (flag1)
        		{
        			par2EntityPlayer.setPositionAndUpdate(x1, y1, z1);
        			
        			if (par1World.getCollidingBoundingBoxes(par2EntityPlayer, par2EntityPlayer.boundingBox).isEmpty())
        			{      				
        				flag = true;    				
        			}
        			else
        			{
        				par2EntityPlayer.setPositionAndUpdate(x0, y0, z0);
        			}
        		}
        	}
        }
        
        short short1 = 128;

        for (int l = 0; l < short1; ++l)
        {
            double d6 = (double)l / ((double)short1 - 1.0D);
            float f = (par1World.rand.nextFloat() - 0.5F) * 0.2F;
            float f1 = (par1World.rand.nextFloat() - 0.5F) * 0.2F;
            float f2 = (par1World.rand.nextFloat() - 0.5F) * 0.2F;
            double d7 = x0 + (par2EntityPlayer.posX - x0) * d6 + (par1World.rand.nextDouble() - 0.5D) * (double)par2EntityPlayer.width * 2.0D;
            double d8 = y0 - 2 + (par2EntityPlayer.posY - y0 + 2) * d6 + par1World.rand.nextDouble() * (double)par2EntityPlayer.height;
            double d9 = z0 + (par2EntityPlayer.posZ - z0) * d6 + (par1World.rand.nextDouble() - 0.5D) * (double)par2EntityPlayer.width * 2.0D;
            par1World.spawnParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
        }
        
        par2EntityPlayer.playSound("mob.endermen.portal", 1.0F, 1.0F);
	}
}
