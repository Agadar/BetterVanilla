package com.agadar.bettervanilla.potion;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class PotionBase extends Potion 
{
	public PotionBase(int par1Id, boolean par2IsBadEffect, int par3LiquidColor) 
	{
		super(par1Id, par2IsBadEffect, par3LiquidColor);
	}

	@Override
    public boolean isInstant()
    {
        return true;
    }

    @Override
    public boolean isReady(int par1, int par2)
    {
        return par1 >= 1;
    }
    
    @Override
    public void performEffect(EntityLivingBase par1EntityLivingBase, int par2)
    {
    	if (this.id == ModPotions.fire.id)
    	{
    		par1EntityLivingBase.setFire(15);
    	}
    	else if (this.id == ModPotions.ender.id)
    	{
    		this.teleportEntityRandomly(par1EntityLivingBase.worldObj, par1EntityLivingBase);
    	}
    	else
    	{
    		super.performEffect(par1EntityLivingBase, par2);
    	}
    }
    
    @Override
    public void affectEntity(EntityLivingBase par1EntityLivingBase, EntityLivingBase par2EntityLivingBase, int par3, double par4)
    {
    	if (this.id == ModPotions.fire.id)
    	{
    		par2EntityLivingBase.setFire(15);
    	}
    	else if (this.id == ModPotions.cure.id)
    	{
    		par2EntityLivingBase.curePotionEffects(new ItemStack(Items.milk_bucket));
    	}
    	else if (this.id == ModPotions.ender.id)
    	{
    		this.teleportEntityRandomly(par2EntityLivingBase.worldObj, par2EntityLivingBase);
    	}
    	else
    	{
    		super.affectEntity(par1EntityLivingBase, par2EntityLivingBase, par3, par4);
    	}
    }
    
    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3)
    {
    	if (this.id == ModPotions.cure.id)
    	{
    		par1EntityLivingBase.curePotionEffects(new ItemStack(Items.milk_bucket));
    	}
    	else
    	{
    		super.removeAttributesModifiersFromEntity(par1EntityLivingBase, par2BaseAttributeMap, par3);
    	}
    }
    
    /** Teleports par2EntityLivingBase to a random nearby location. Used by the Ender Potion. */
    private void teleportEntityRandomly(World par1World, EntityLivingBase par2EntityLivingBase)
	{       
        boolean flag = false;
        
        double x0 = par2EntityLivingBase.posX;
        double y0 = par2EntityLivingBase.posY;
        double z0 = par2EntityLivingBase.posZ;
        
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
        			par2EntityLivingBase.setPositionAndUpdate(x1, y1, z1);
        			
        			if (par1World.getCollidingBoundingBoxes(par2EntityLivingBase, par2EntityLivingBase.boundingBox).isEmpty())
        			{      				
        				flag = true;    				
        			}
        			else
        			{
        				par2EntityLivingBase.setPositionAndUpdate(x0, y0, z0);
        			}
        		}
        	}
        }
        
        par2EntityLivingBase.playSound("mob.endermen.portal", 1.0F, 1.0F);
	}
}
