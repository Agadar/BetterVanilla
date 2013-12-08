package bettervanilla.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

// We need this class in order to access BlockStair's constructor, which is protected.
public class BlockStairsCustom extends BlockStairs {

	public BlockStairsCustom(int par1, Block par2Block, int par3) 
	{
		super(par1, par2Block, par3);
	}
}
