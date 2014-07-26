package com.agadar.bettervanilla.item;

import com.agadar.bettervanilla.block.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemReed;

public class ItemWaterCauldron extends ItemReed {

	public ItemWaterCauldron() 
	{
		super(ModBlocks.water_cauldron);
		this.setUnlocalizedName("water_cauldron");
		this.setCreativeTab(CreativeTabs.tabBrewing);
		this.setTextureName("cauldron");
	}
}
