package com.agadar.bettervanilla.items;

import com.agadar.bettervanilla.help.References;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemFleshyHide extends Item 
{
	public ItemFleshyHide()
	{
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setUnlocalizedName("fleshy_hide");
		this.setTextureName(References.MODID + ":" + getUnlocalizedName().substring(5));
	}
}
