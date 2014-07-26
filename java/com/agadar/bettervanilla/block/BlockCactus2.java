package com.agadar.bettervanilla.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockCactus2 extends BlockCactus {

	public BlockCactus2() {
		super();
		this.setHardness(0.4F);
		this.setStepSound(Block.soundTypeCloth);
		this.setBlockName("cactus2");
		this.setBlockTextureName("cactus");
	}

	@Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
		Block b = par1World.getBlock(par2, par3 - 1, par4);
		return b == ModBlocks.cactus || b == Blocks.sand;
    }
}
