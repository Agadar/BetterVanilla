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

public class ItemPotionBase extends ItemPotion 
{
	@SideOnly(Side.CLIENT)
    private IIcon fireOverlayIcon;
	@SideOnly(Side.CLIENT)
    private IIcon milkOverlayIcon;
	@SideOnly(Side.CLIENT)
    private IIcon enderOverlayIcon;
    
	public ItemPotionBase()
	{
		super();
		this.setUnlocalizedName("potion_base");
		this.setTextureName("potion");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, List par3List)
    {
		/** (Splash) Lava Bottle. */
		ItemStack drinkable = new ItemStack(par1Item, 1, 1);		
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.fire.id, 1, 0));		
		BrewingRecipes.brewing().setEffects(drinkable, effects);	
		ItemStack splash = drinkable.copy();
		splash.setItemDamage(16384);
		par3List.add(drinkable);
		par3List.add(splash);
		
		/** (Splash) Milk Bottle. */
		drinkable = new ItemStack(par1Item, 1, 1);		
		effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.cure.id, 1, 0));		
		BrewingRecipes.brewing().setEffects(drinkable, effects);	
		splash = drinkable.copy();
		splash.setItemDamage(16384);
		par3List.add(drinkable);
		par3List.add(splash);
		
		/** (Splash) Ender Potion. */
		drinkable = new ItemStack(par1Item, 1, 1);		
		effects = new ArrayList<PotionEffect>();
		effects.add(new PotionEffect(ModPotions.ender.id, 1, 0));		
		BrewingRecipes.brewing().setEffects(drinkable, effects);	
		splash = drinkable.copy();
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
	
	@SuppressWarnings("unchecked")
	@Override
	public IIcon getIcon(ItemStack par1ItemStack, int par2Pass)
	{
		if (par2Pass == 0)
		{
			List<PotionEffect> effects = this.getEffects(par1ItemStack);
			
			if (effects.size() > 0)
			{
				int potionId = effects.get(0).getPotionID();
				
				if (potionId == ModPotions.fire.id)
				{
					return this.fireOverlayIcon;
				}
				else if (potionId == ModPotions.cure.id)
				{
					return this.milkOverlayIcon;
				}
				else if (potionId == ModPotions.ender.id)
				{
					return this.enderOverlayIcon;
				}
			}
		}
		
		return super.getIcon(par1ItemStack, par2Pass);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
		super.registerIcons(par1IconRegister);
        this.fireOverlayIcon = par1IconRegister.registerIcon(References.MODID + ":fire_potion_overlay");
        this.milkOverlayIcon = par1IconRegister.registerIcon(References.MODID + ":milk_potion_overlay");
        this.enderOverlayIcon = par1IconRegister.registerIcon(References.MODID + ":ender_potion_overlay");
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return false;
    }
}
