package com.agadar.bettervanilla.blocks;

import net.minecraft.block.BlockCactus;
import net.minecraft.world.World;
//import net.minecraftforge.common.ForgeDirection;

public class BlockCactusOverride extends BlockCactus {

	public BlockCactusOverride(int par1) {
		super(par1);
	}

	@Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        int l = par1World.getBlockId(par2, par3 - 1, par4);
        return blocksList[l] != null && blocksList[l].canSustainPlant(par1World, par2, par3 - 1, par4, ForgeDirection.UP, this);
    }
}
