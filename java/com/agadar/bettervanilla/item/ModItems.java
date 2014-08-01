package com.agadar.bettervanilla.item;

import com.agadar.bettervanilla.help.ModConfigurations;
import com.agadar.bettervanilla.help.RegisterHelper;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

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
	public static Item acacia_helmet;
	public static Item acacia_chestplate;
	public static Item acacia_leggings;
	public static Item acacia_boots;
	public static Item big_oak_helmet;
	public static Item big_oak_chestplate;
	public static Item big_oak_leggings;
	public static Item big_oak_boots;
	
	// Items (Miscellaneous)
	public static Item ender_potion;
	public static Item milk_bottle;
	public static Item colored_bed;
	public static Item lava_bottle;
	public static Item fleshy_hide;
	public static Item water_cauldron;
	
	public static void registerModItems()
	{
		if (ModConfigurations.EnderPotions)
		{
			ender_potion = new ItemEnderPotion();
			RegisterHelper.registerItem(ender_potion);
		}
		
		if (ModConfigurations.ColoredBeds)
		{
			colored_bed = new ItemColoredBed();
			RegisterHelper.registerItem(colored_bed);
		}
		
		if (ModConfigurations.CauldronsLava)
		{
			lava_bottle = new ItemFireBottle();
			RegisterHelper.registerItem(lava_bottle);
		}
		
		if (ModConfigurations.Doors) 
		{
			// Increase the maximum stack size from 1 to 16.
			Items.wooden_door.setMaxStackSize(16);
			Items.iron_door.setMaxStackSize(16);
		}
		
		if (ModConfigurations.MilkBottles)
		{
			milk_bottle = new ItemMilkBottle();
			RegisterHelper.registerItem(milk_bottle);
		}
		
		if (ModConfigurations.MoreArmor)
		{
			// Create our new armor materials.
			WOOD = EnumHelper.addArmorMaterial("WOOD", 5, new int[]{1, 3, 2, 1}, 15);
			PUMPKIN = EnumHelper.addArmorMaterial("PUMPKIN", 5, new int[]{1, 3, 2, 1}, 15);
			CACTUS = EnumHelper.addArmorMaterial("CACTUS", 5, new int[]{1, 3, 2, 1}, 15);
			MELON = EnumHelper.addArmorMaterial("MELON", 5, new int[]{1, 3, 2, 1}, 15);
			
			// Create our new armor items.
			oak_helmet = new ItemOakArmor(0, "oak_helmet");
			oak_chestplate = new ItemOakArmor(1, "oak_chestplate");
			oak_leggings = new ItemOakArmor(2, "oak_leggings");
			oak_boots = new ItemOakArmor(3, "oak_boots");
			spruce_helmet = new ItemSpruceArmor(0, "spruce_helmet");
			spruce_chestplate = new ItemSpruceArmor(1, "spruce_chestplate");
			spruce_leggings = new ItemSpruceArmor(2, "spruce_leggings");
			spruce_boots = new ItemSpruceArmor(3, "spruce_boots");
			birch_helmet = new ItemBirchArmor(0, "birch_helmet");
			birch_chestplate = new ItemBirchArmor(1, "birch_chestplate");
			birch_leggings = new ItemBirchArmor(2, "birch_leggings");
			birch_boots = new ItemBirchArmor(3,"birch_boots");
			jungle_helmet = new ItemJungleArmor(0, "jungle_helmet");
			jungle_chestplate = new ItemJungleArmor(1, "jungle_chestplate");
			jungle_leggings = new ItemJungleArmor(2, "jungle_leggings");
			jungle_boots = new ItemJungleArmor(3, "jungle_boots");
			pumpkin_helmet = new ItemPumpkinArmor(0, "pumpkin_helmet");
			pumpkin_chestplate = new ItemPumpkinArmor(1, "pumpkin_chestplate");
			pumpkin_leggings = new ItemPumpkinArmor(2, "pumpkin_leggings");
			pumpkin_boots = new ItemPumpkinArmor(3, "pumpkin_boots");
			cactus_helmet = new ItemCactusArmor(0, "cactus_helmet");
			cactus_chestplate = new ItemCactusArmor(1, "cactus_chestplate");
			cactus_leggings = new ItemCactusArmor(2, "cactus_leggings");
			cactus_boots = new ItemCactusArmor(3, "cactus_boots");
			melon_helmet = new ItemMelonArmor(0, "melon_helmet");
			melon_chestplate = new ItemMelonArmor(1, "melon_chestplate");
			melon_leggings = new ItemMelonArmor(2, "melon_leggings");
			melon_boots = new ItemMelonArmor(3, "melon_boots");
			acacia_helmet = new ItemAcaciaArmor(0, "acacia_helmet");
			acacia_chestplate = new ItemAcaciaArmor(1, "acacia_chestplate");
			acacia_leggings = new ItemAcaciaArmor(2, "acacia_leggings");
			acacia_boots = new ItemAcaciaArmor(3, "acacia_boots");
			big_oak_helmet = new ItemBigOakArmor(0, "big_oak_helmet");
			big_oak_chestplate = new ItemBigOakArmor(1, "big_oak_chestplate");
			big_oak_leggings = new ItemBigOakArmor(2, "big_oak_leggings");
			big_oak_boots = new ItemBigOakArmor(3, "big_oak_boots");
			
			// Register our new armor items.
			RegisterHelper.registerItem(oak_helmet);
			RegisterHelper.registerItem(oak_chestplate);
			RegisterHelper.registerItem(oak_leggings);
			RegisterHelper.registerItem(oak_boots);
			RegisterHelper.registerItem(spruce_helmet);
			RegisterHelper.registerItem(spruce_chestplate);
			RegisterHelper.registerItem(spruce_leggings);
			RegisterHelper.registerItem(spruce_boots);
			RegisterHelper.registerItem(birch_helmet);
			RegisterHelper.registerItem(birch_chestplate);
			RegisterHelper.registerItem(birch_leggings);
			RegisterHelper.registerItem(birch_boots);
			RegisterHelper.registerItem(jungle_helmet);
			RegisterHelper.registerItem(jungle_chestplate);
			RegisterHelper.registerItem(jungle_leggings);
			RegisterHelper.registerItem(jungle_boots);
			RegisterHelper.registerItem(pumpkin_helmet);
			RegisterHelper.registerItem(pumpkin_chestplate);
			RegisterHelper.registerItem(pumpkin_leggings);
			RegisterHelper.registerItem(pumpkin_boots);
			RegisterHelper.registerItem(cactus_helmet);
			RegisterHelper.registerItem(cactus_chestplate);
			RegisterHelper.registerItem(cactus_leggings);
			RegisterHelper.registerItem(cactus_boots);
			RegisterHelper.registerItem(melon_helmet);
			RegisterHelper.registerItem(melon_chestplate);
			RegisterHelper.registerItem(melon_leggings);
			RegisterHelper.registerItem(melon_boots);
			RegisterHelper.registerItem(acacia_helmet);
			RegisterHelper.registerItem(acacia_chestplate);
			RegisterHelper.registerItem(acacia_leggings);
			RegisterHelper.registerItem(acacia_boots);
			RegisterHelper.registerItem(big_oak_helmet);
			RegisterHelper.registerItem(big_oak_chestplate);
			RegisterHelper.registerItem(big_oak_leggings);
			RegisterHelper.registerItem(big_oak_boots);
		}
		
		if (ModConfigurations.RottenFleshToLeather && ModConfigurations.HardLeatherRecipe)
		{
			fleshy_hide = new ItemFleshyHide();
			RegisterHelper.registerItem(fleshy_hide);
		}
		
		if (ModConfigurations.CauldronsLava)
		{
			water_cauldron = new ItemWaterCauldron();
			RegisterHelper.registerItem(water_cauldron);
		}
	}
}
