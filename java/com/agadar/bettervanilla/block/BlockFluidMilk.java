package com.agadar.bettervanilla.block;

import com.agadar.bettervanilla.help.References;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidMilk extends BlockFluidClassic 
{
	@SideOnly(Side.CLIENT)
	protected IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon flowingIcon;

	public BlockFluidMilk(Fluid fluid, Material material) 
	{
		super(fluid, material);
		this.setBlockName("milk");
	}

	@Override
	public IIcon getIcon(int side, int meta) 
	{
		return (side == 0 || side == 1) ? stillIcon : flowingIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) 
	{
		stillIcon = register.registerIcon(References.MODID + ":milk_still");
		flowingIcon = register.registerIcon(References.MODID + ":milk_flow");
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) 
	{
		if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) 
	{
		if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
		return super.displaceIfPossible(world, x, y, z);
	}    
	
	@Override
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) 
	{
		if (!(p_149670_5_ instanceof EntityPlayer))
    	{
    		return;
    	}
    	
        if (!p_149670_1_.isRemote)
        {
        	((EntityPlayer) p_149670_5_).curePotionEffects(new ItemStack(Items.milk_bucket));
        }
	}
}
