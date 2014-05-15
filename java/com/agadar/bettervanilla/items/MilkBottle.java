package com.agadar.bettervanilla.items;

import com.agadar.bettervanilla.BetterVanilla;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MilkBottle extends Item 
{
	public MilkBottle(int par1) 
	{
		super(par1);
        this.setMaxStackSize(4);
        this.setCreativeTab(CreativeTabs.tabBrewing);
	}
	
	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par2World.isRemote)
        {
            par3EntityPlayer.curePotionEffects(new ItemStack(Item.bucketMilk));
        }

        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
        	--par1ItemStack.stackSize;
        	
        	if (par1ItemStack.stackSize <= 0) return new ItemStack(Item.glassBottle);
        	
            if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle)))
            	par3EntityPlayer.dropPlayerItem(new ItemStack(Item.glassBottle));
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
}
