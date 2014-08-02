package com.agadar.bettervanilla.item;

import java.util.List;

import com.agadar.bettervanilla.block.ModBlocks;
import com.agadar.bettervanilla.help.References;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemStack;

public class ItemWaterCauldron extends ItemReed 
{
	public ItemWaterCauldron() 
	{
		super(ModBlocks.water_cauldron);
		this.setUnlocalizedName("water_cauldron");
		this.setCreativeTab(CreativeTabs.tabBrewing);
		this.setTextureName("cauldron");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add(References.NAME);
	}
}
