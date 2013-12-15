package bettervanilla.blocks;

import bettervanilla.BetterVanilla;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CauldronLava extends CauldronWater
{
	public CauldronLava(int par1) 
	{
		super(par1);
		this.setLightValue(1.0f);
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
        int j1 = func_111045_h_(i1);

        if (itemstack.itemID == Item.bucketLava.itemID && j1 < 3)
        {      	
        	if (!par5EntityPlayer.capabilities.isCreativeMode)
        	{
        		par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(Item.bucketEmpty));
        	}

        	par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
        	par1World.func_96440_m(par2, par3, par4, this.blockID);       	
        }
        else if (itemstack.itemID == Item.bucketEmpty.itemID && j1 >= 3)
        {
        	if (!par5EntityPlayer.capabilities.isCreativeMode)
        	{
        		par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(Item.bucketLava));
        	}

        	par1World.setBlock(par2, par3, par4, BetterVanilla.cauldronWater.blockID, 0, 2);
        	par1World.func_96440_m(par2, par3, par4, BetterVanilla.cauldronWater.blockID);       	
        }
        return true;
    }
	
	@Override
    public void fillWithRain(World par1World, int par2, int par3, int par4)
    {
    }
}
