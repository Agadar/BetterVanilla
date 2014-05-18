package com.agadar.bettervanilla.items;

import com.agadar.bettervanilla.handlers.ModConfigurations;
import com.agadar.bettervanilla.help.RegisterHelper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;

public class ModItems 
{
	// Armor Materials
	public static ArmorMaterial WOOD; 
	public static ArmorMaterial PUMPKIN; 
	public static ArmorMaterial CACTUS; 
	public static ArmorMaterial MELON;
		
	// Items (Armor)
	public static Item oak_helmet;
	public static Item oak_chestplate;
	public static Item oak_leggings;
	public static Item oak_boots;
	public static Item spruce_helmet;
	public static Item spruce_chestplate;
	public static Item spruce_leggings;
	public static Item spruce_boots;
	public static Item birch_helmet;
	public static Item birch_chestplate;
	public static Item birch_leggings;
	public static Item birch_boots;
	public static Item jungle_helmet;
	public static Item jungle_chestplate;
	public static Item jungle_leggings;
	public static Item jungle_boots;
	public static Item pumpkin_helmet;
	public static Item pumpkin_chestplate;
	public static Item pumpkin_leggings;
	public static Item pumpkin_boots;
	public static Item cactus_helmet;
	public static Item cactus_chestplate;
	public static Item cactus_leggings;
	public static Item cactus_boots;
	public static Item melon_helmet;
	public static Item melon_chestplate;
	public static Item melon_leggings;
	public static Item melon_boots;
	
	// Items (Miscellaneous)
	public static Item ender_potion;
	public static Item milk_bottle;
	public static Item bed;
	public static Item lava_bottle;
	
	public static void loadItems()
	{
		if (ModConfigurations.EnderPotions)
		{
			ender_potion = new EnderPotion();
			RegisterHelper.registerItem(ender_potion);
		}
	}
}
