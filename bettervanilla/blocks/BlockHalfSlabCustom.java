package bettervanilla.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHalfSlabCustom extends BlockHalfSlab
{
	/** The block that is used as model for the slab. */
    private final Block modelBlock;
    
	public BlockHalfSlabCustom(int par1, boolean par2, Block par2Block) 
	{
		super(par1, par2, par2Block.blockMaterial);
		this.modelBlock = par2Block;
		this.setHardness(par2Block.blockHardness);
        this.setResistance(par2Block.blockResistance / 3.0F);
        this.setStepSound(par2Block.stepSound);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setLightOpacity(lightOpacity[modelBlock.blockID]);
        this.setLightValue(lightValue[modelBlock.blockID] / 15.0F);
	}
	
	@SideOnly(Side.CLIENT)
    private static boolean isBlockSingleSlab(int par0)
    {
        return true;
    }
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
    {
        return this.blockID;
    }
	
	@Override
	public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return this.blockID;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2)
    {
        return modelBlock.getIcon(par1, par2);
    }

	@Override
	public String getFullSlabName(int i) 
	{
		return this.getUnlocalizedName();
	}
}
