package com.agadar.bettervanilla.item;

import com.agadar.bettervanilla.help.References;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemOakArmor extends ItemArmor {
	
	public ItemOakArmor(int armorType, String unlocalizedName)
	{
		super(ModItems.WOOD, 0, armorType);
		setUnlocalizedName(unlocalizedName);
		setTextureName(References.MODID + ":" + getUnlocalizedName().substring(5));
	}

    /**
     * This method returns what file MC should use, to render the armor (make it visible from above).
     */
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		if(stack.getItem() != ModItems.oak_leggings)
		{
			return References.MODID + ":textures/models/armor/oak_layer_1.png";
		}
		return References.MODID + ":textures/models/armor/oak_layer_2.png";
	}
	
	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return par2ItemStack.getItem() == Item.getItemFromBlock(Blocks.log) && par2ItemStack.getItemDamage() == 0;
    }
}
