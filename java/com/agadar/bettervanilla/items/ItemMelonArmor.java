package com.agadar.bettervanilla.items;

import com.agadar.bettervanilla.BetterVanilla;
import com.agadar.bettervanilla.help.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemMelonArmor extends ItemArmor {
	
	public ItemMelonArmor(ArmorMaterial material, int armorType, String unlocalizedName)
	{
		super(material, 0, armorType);
		setUnlocalizedName(unlocalizedName);
		setTextureName(Reference.MODID + ":" + getUnlocalizedName().substring(5));
	}

    /**
     * This method returns what file MC should use, to render the armor (make it visible from above).
     */
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		if(stack.getItem() != BetterVanilla.legsMelon)
		{
			return Reference.MODID + ":textures/models/armor/melon_layer_1.png";
		}
		return Reference.MODID + ":textures/models/armor/melon_layer_2.png";
	}
	
	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return par2ItemStack.getItem() == Item.getItemFromBlock(Blocks.melon_block);
    }
}