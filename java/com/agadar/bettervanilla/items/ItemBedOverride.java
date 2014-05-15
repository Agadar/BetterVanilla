package com.agadar.bettervanilla.items;

import java.util.List;

import com.agadar.bettervanilla.BetterVanilla;
import com.agadar.bettervanilla.blocks.BlockBedOverride;
import com.agadar.bettervanilla.tileentities.BedColor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemBedOverride extends ItemBed 
{
	public static final String[] bedNames = new String[] {"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};
	@SideOnly(Side.CLIENT)
    private Icon[] bedIcons;
	
	public ItemBedOverride(int par1) 
	{
		super(par1);
		this.setHasSubtypes(true);	// This allows the item to be marked as a metadata item.
        this.setMaxDamage(0);  		// This makes it so the item doesn't have the damage bar at the bottom of its icon.
	}	
	
	@Override
    public Icon getIconFromDamage(int par1) 
	{
		int j = MathHelper.clamp_int(par1, 0, 15);
        return this.bedIcons[j];
    }
	
	@Override
	public void registerIcons(IconRegister par1IconRegister)
    {
		bedIcons = new Icon[bedNames.length];	
		
		for (int i = 0; i < bedNames.length; i++)
        {
            this.bedIcons[i] = par1IconRegister.registerIcon(this.getIconString() + "_" + bedNames[i]);
        }
    }

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int j = 0; j < 16; ++j)
        {
            par3List.add(new ItemStack(par1, 1, j));
        }
    }
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
        return super.getUnlocalizedName() + "." + bedNames[i];
    }
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.isRemote)
        {
            return true;
        }
        else if (par7 != 1)
        {
            return false;
        }
        else
        {
            ++par5;
            BlockBedOverride blockbed = (BlockBedOverride)BetterVanilla.blockBedOverride;
            int i1 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            byte b0 = 0;
            byte b1 = 0;

            if (i1 == 0)
            {
                b1 = 1;
            }

            if (i1 == 1)
            {
                b0 = -1;
            }

            if (i1 == 2)
            {
                b1 = -1;
            }

            if (i1 == 3)
            {
                b0 = 1;
            }

            if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4 + b0, par5, par6 + b1, par7, par1ItemStack))
            {
                if (par3World.isAirBlock(par4, par5, par6) && par3World.isAirBlock(par4 + b0, par5, par6 + b1) && par3World.doesBlockHaveSolidTopSurface(par4, par5 - 1, par6) && par3World.doesBlockHaveSolidTopSurface(par4 + b0, par5 - 1, par6 + b1))
                {
                    par3World.setBlock(par4, par5, par6, blockbed.blockID, i1, 3);
                    ((BedColor)par3World.getBlockTileEntity(par4, par5, par6)).setColor(par1ItemStack.getItemDamage());

                    if (par3World.getBlockId(par4, par5, par6) == blockbed.blockID)
                    {
                        par3World.setBlock(par4 + b0, par5, par6 + b1, blockbed.blockID, i1 + 8, 3);
                        ((BedColor)par3World.getBlockTileEntity(par4 + b0, par5, par6 + b1)).setColor(par1ItemStack.getItemDamage());
                    }

                    --par1ItemStack.stackSize;
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
    }
}
