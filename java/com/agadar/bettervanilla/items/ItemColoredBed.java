package com.agadar.bettervanilla.items;

import java.util.List;

import com.agadar.bettervanilla.blocks.BlockColoredBed;
import com.agadar.bettervanilla.blocks.ModBlocks;
import com.agadar.bettervanilla.help.References;
import com.agadar.bettervanilla.tileentities.TileEntityBedColor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemColoredBed extends ItemBed 
{
	public static final String[] bedNames = new String[] {"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};
	@SideOnly(Side.CLIENT)
    private IIcon[] bedIcons;
	
	public ItemColoredBed() 
	{
		super();
		this.setHasSubtypes(true);	// This allows the item to be marked as a metadata item.
        this.setMaxDamage(0);  		// This makes it so the item doesn't have the damage bar at the bottom of its icon.
        this.setMaxStackSize(1);
        this.setUnlocalizedName("colored_bed");
        this.setTextureName(References.MODID + ":" + getUnlocalizedName().substring(5));
	}	
	
	@Override
    public IIcon getIconFromDamage(int par1) 
	{
		int j = MathHelper.clamp_int(par1, 0, 15);
        return this.bedIcons[j];
    }
	
	@Override
	public void registerIcons(IIconRegister par1IconRegister)
    {
		bedIcons = new IIcon[bedNames.length];	
		
		for (int i = 0; i < bedNames.length; i++)
        {
            this.bedIcons[i] = par1IconRegister.registerIcon(this.getIconString() + "_" + bedNames[i]);
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item par1Item, CreativeTabs par2CreativeTabs, @SuppressWarnings("rawtypes") List par3List)
    {
        for (int j = 0; j < 16; ++j)
        {
            par3List.add(new ItemStack(par1Item, 1, j));
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
            BlockColoredBed blockbed = (BlockColoredBed)ModBlocks.colored_bed;
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
                if (par3World.isAirBlock(par4, par5, par6) && par3World.isAirBlock(par4 + b0, par5, par6 + b1) && World.doesBlockHaveSolidTopSurface(par3World, par4, par5 - 1, par6) && World.doesBlockHaveSolidTopSurface(par3World, par4 + b0, par5 - 1, par6 + b1))
                {
                    par3World.setBlock(par4, par5, par6, blockbed, i1, 3);
                    ((TileEntityBedColor)par3World.getTileEntity(par4, par5, par6)).setColor(par1ItemStack.getItemDamage());

                    if (par3World.getBlock(par4, par5, par6) == blockbed)
                    {
                        par3World.setBlock(par4 + b0, par5, par6 + b1, blockbed, i1 + 8, 3);
                        ((TileEntityBedColor)par3World.getTileEntity(par4 + b0, par5, par6 + b1)).setColor(par1ItemStack.getItemDamage());
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
