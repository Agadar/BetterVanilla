package com.agadar.bettervanilla.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

// We need this class in order to access BlockStair's constructor, which is protected.
public class BlockStairs2 extends BlockStairs {

	public BlockStairs2(int par1, Block par2Block, int par3) 
	{
		super(par2Block, par3);
	}
}
