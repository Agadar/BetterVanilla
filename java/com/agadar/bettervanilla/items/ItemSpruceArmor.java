package com.agadar.bettervanilla.items;

import com.agadar.bettervanilla.BetterVanilla;
import com.agadar.bettervanilla.help.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemSpruceArmor extends ItemArmor {
	
	public ItemSpruceArmor(ArmorMaterial material, int armorType, String unlocalizedName)
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
		if(stack.getItem() != BetterVanilla.legsSpruce)
		{
			return Reference.MODID + ":textures/models/armor/spruce_layer_1.png";
		}
		return Reference.MODID + ":textures/models/armor/spruce_layer_2.png";
	}
	
	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return par2ItemStack.getItem() == Item.getItemFromBlock(Blocks.log) && par2ItemStack.getItemDamage() == 1;
    }
}
