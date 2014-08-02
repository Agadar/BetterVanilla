package com.agadar.bettervanilla.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

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
}
