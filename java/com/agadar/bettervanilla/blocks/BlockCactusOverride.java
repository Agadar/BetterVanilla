package com.agadar.bettervanilla.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCactusOverride extends BlockCactus {

	public BlockCactusOverride(int par1) {
		super();
		this.setHardness(0.4F);
		this.setStepSound(Block.soundTypeCloth);
		this.setBlockName("cactus");
		this.setBlockTextureName("cactus");
	}

	@Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
		Block b = par1World.getBlock(par2, par3 - 1, par4);
		return b.canSustainPlant(par1World, par2, par3 - 1, par4, ForgeDirection.UP, this);
    }
}
