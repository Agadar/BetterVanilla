package com.agadar.bettervanilla.blocks;

import java.util.Random;

import com.agadar.bettervanilla.BetterVanilla;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class CauldronLava extends CauldronWater
{
	public CauldronLava() 
	{
		super();
		this.setLightLevel(1.0F);
	}
	
	@Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }
        
        ItemStack itemstack = par5EntityPlayer.inventory.getCurrentItem();
        
        if (itemstack == null)
        {
        	return true;
        }
        
        int i1 = par1World.getBlockMetadata(par2, par3, par4);
        int j1 = func_150027_b(i1);

        if (itemstack.getItem() == Items.lava_bucket && j1 < 3)
        {      	
        	if (!par5EntityPlayer.capabilities.isCreativeMode)
        	{
        		par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(Items.bucket));
        	}

        	this.func_150024_a(par1World, par2, par3, par4, 3);      	
        }
        else if (itemstack.getItem() == Items.bucket && j1 >= 3)
        {
        	if (!par5EntityPlayer.capabilities.isCreativeMode)
        	{
        		par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(Items.lava_bucket));
        	}

        	par1World.setBlock(par2, par3, par4, BetterVanilla.cauldronWater, 0, 2);
        	par1World.func_147453_f(par2, par3, par4, BetterVanilla.cauldronWater);       	
        }
        else if (itemstack.getItem() == Items.glass_bottle && j1 > 0)
        {		
        	ItemStack itemstack1 = new ItemStack(BetterVanilla.lavaBottle);

        	if (!par5EntityPlayer.inventory.addItemStackToInventory(itemstack1))
        	{
        		par1World.spawnEntityInWorld(new EntityItem(par1World, (double)par2 + 0.5D, (double)par3 + 1.5D, (double)par4 + 0.5D, itemstack1));
        	}
        	else if (par5EntityPlayer instanceof EntityPlayerMP)
        	{
        		((EntityPlayerMP)par5EntityPlayer).sendContainerToPlayer(par5EntityPlayer.inventoryContainer);
        	}

        	--itemstack.stackSize;

        	if (itemstack.stackSize <= 0)
        	{
        		par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, (ItemStack)null);
        	}

        	j1--;
        	
        	if (j1 > 0)
        	{
        		this.func_150024_a(par1World, par2, par3, par4, j1);     
        	}
        	else
        	{
        		par1World.setBlock(par2, par3, par4, BetterVanilla.cauldronWater, 0, 2);
            	par1World.func_147453_f(par2, par3, par4, BetterVanilla.cauldronWater);
        	}
        }
        return true;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	 {
		 if (par1World.getBlock(par2, par3 + 1, par4).getMaterial() == Material.air && !par1World.getBlock(par2, par3 + 1, par4).isOpaqueCube())
	        {
	            if (par5Random.nextInt(100) == 0)
	            {
	                double d5 = (double)((float)par2 + par5Random.nextFloat());
	                double d7 = (double)par3 + this.maxY;
	                double d6 = (double)((float)par4 + par5Random.nextFloat());
	                par1World.spawnParticle("lava", d5, d7, d6, 0.0D, 0.0D, 0.0D);
	                par1World.playSound(d5, d7, d6, "liquid.lavapop", 0.2F + par5Random.nextFloat() * 0.2F, 0.9F + par5Random.nextFloat() * 0.15F, false);
	            }

	            if (par5Random.nextInt(200) == 0)
	            {
	                par1World.playSound((double)par2, (double)par3, (double)par4, "liquid.lava", 0.2F + par5Random.nextFloat() * 0.2F, 0.9F + par5Random.nextFloat() * 0.15F, false);
	            }
	        }
	 }
	
	@Override
    public void fillWithRain(World par1World, int par2, int par3, int par4)
    {
    }
	
	/**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    @Override
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
    {
        int l = func_150027_b(p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_));
        float f = (float)p_149670_3_ + (6.0F + (float)(3 * l)) / 16.0F;

        if (!p_149670_1_.isRemote && l > 0 && p_149670_5_.boundingBox.minY <= (double)f)
        {
        	p_149670_5_.attackEntityFrom(DamageSource.lava, 4.0F);
        	p_149670_5_.setFire(15);
        }
    }
}
