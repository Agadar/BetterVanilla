package com.agadar.bettervanilla.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.agadar.bettervanilla.item.ModItems;
import com.agadar.bettervanilla.potion.ModPotions;
import com.agadar.brewingapi.BrewingRecipes;

public class BlockMilkCauldron extends BlockWaterCauldron 
{
	public BlockMilkCauldron() 
	{
		super();
	}
	
	@Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }
        
        ItemStack itemstack = par5EntityPlayer.inventory.getCurrentItem();
        
        if (itemstack == null)
        {
        	return true;
        }
        
        int i1 = par1World.getBlockMetadata(par2, par3, par4);
        int j1 = func_150027_b(i1);

        if (itemstack.getItem() == Items.milk_bucket && j1 < 3)
        {      	
        	if (!par5EntityPlayer.capabilities.isCreativeMode)
        	{
        		par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(Items.bucket));
        	}

        	this.func_150024_a(par1World, par2, par3, par4, 3);      	
        }
        else if (itemstack.getItem() == Items.bucket && j1 >= 3)
        {
        	if (!par5EntityPlayer.capabilities.isCreativeMode)
        	{
        		par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(Items.milk_bucket));
        	}

        	par1World.setBlock(par2, par3, par4, ModBlocks.water_cauldron, 0, 2);
        	par1World.func_147453_f(par2, par3, par4, ModBlocks.water_cauldron);       	
        }
        else if (itemstack.getItem() == Items.glass_bottle && j1 > 0)
        {		
        	ItemStack milkBottleStack = new ItemStack(ModItems.itemPotionBase, 1, 1);
			List<PotionEffect> effects = new ArrayList<PotionEffect>();
			effects.add(new PotionEffect(ModPotions.cure.id, 1, 0));		
			BrewingRecipes.brewing().setEffects(milkBottleStack, effects);

        	if (!par5EntityPlayer.inventory.addItemStackToInventory(milkBottleStack))
        	{
        		par1World.spawnEntityInWorld(new EntityItem(par1World, (double)par2 + 0.5D, (double)par3 + 1.5D, (double)par4 + 0.5D, milkBottleStack));
        	}
        	else if (par5EntityPlayer instanceof EntityPlayerMP)
        	{
        		((EntityPlayerMP)par5EntityPlayer).sendContainerToPlayer(par5EntityPlayer.inventoryContainer);
        	}

        	--itemstack.stackSize;

        	if (itemstack.stackSize <= 0)
        	{
        		par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, (ItemStack)null);
        	}

        	j1--;
        	
        	if (j1 > 0)
        	{
        		this.func_150024_a(par1World, par2, par3, par4, j1);     
        	}
        	else
        	{
        		par1World.setBlock(par2, par3, par4, ModBlocks.water_cauldron, 0, 2);
            	par1World.func_147453_f(par2, par3, par4, ModBlocks.water_cauldron);
        	}
        }
        return true;
    }
	
	@Override
    public void fillWithRain(World par1World, int par2, int par3, int par4) { }
	
    @Override
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
    {
    	if (!(p_149670_5_ instanceof EntityPlayer))
    	{
    		return;
    	}
    	
        int l = func_150027_b(p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_));
        float f = (float)p_149670_3_ + (6.0F + (float)(3 * l)) / 16.0F;

        if (!p_149670_1_.isRemote && l > 0 && p_149670_5_.boundingBox.minY <= (double)f)
        {
        	((EntityPlayer) p_149670_5_).curePotionEffects(new ItemStack(Items.milk_bucket));
        }
    }
}
