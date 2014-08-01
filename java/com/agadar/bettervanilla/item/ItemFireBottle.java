package com.agadar.bettervanilla.item;

import java.util.ArrayList;
import java.util.List;

import com.agadar.bettervanilla.help.References;
import com.agadar.bettervanilla.potion.ModPotions;
import com.agadar.brewingapi.BrewingRecipes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;

public class ItemFireBottle extends ItemPotion 
{
	@SideOnly(Side.CLIENT)
    private IIcon overlayIcon;
    
	public ItemFireBottle()
	{
		super();
		this.setUnlocalizedName("fire_potion");
		this.setTextureName("potion");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, List par3List)
    {
		ItemStack drinkable = new ItemStack(par1Item, 1, 1);		
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.fire.id, 1, 0));		
		BrewingRecipes.brewing().setEffects(drinkable, effects);	
		ItemStack splash = drinkable.copy();
		splash.setItemDamage(16384);
		par3List.add(drinkable);
		par3List.add(splash);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return 16777215;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return par2 == 0 ? this.overlayIcon : super.getIconFromDamageForRenderPass(par1, par2);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
		super.registerIcons(par1IconRegister);
        this.overlayIcon = par1IconRegister.registerIcon(References.MODID + ":fire_potion_overlay");
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return false;
    }
}
