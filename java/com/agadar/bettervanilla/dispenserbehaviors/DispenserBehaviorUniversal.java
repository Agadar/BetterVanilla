package com.agadar.bettervanilla.dispenserbehaviors;

import com.agadar.bettervanilla.misc.FakeEntityPlayer;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;


public class DispenserBehaviorUniversal extends BehaviorDefaultDispenseItem 
{
	/**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {        
    	World world = par1IBlockSource.getWorld(); 
    	
    	EnumFacing enumfacing = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        int i = par1IBlockSource.getXInt() + enumfacing.getFrontOffsetX();
        int j = par1IBlockSource.getYInt() + enumfacing.getFrontOffsetY();
        int k = par1IBlockSource.getZInt() + enumfacing.getFrontOffsetZ();    
        
    	FakeEntityPlayer fakePlayerEntity = new FakeEntityPlayer(world, null);  
    	par2ItemStack.getItem().onItemUse(par2ItemStack, fakePlayerEntity, world, i, j - 1, k, 1, 0, 0, 0);
    	return par2ItemStack;
    }
}