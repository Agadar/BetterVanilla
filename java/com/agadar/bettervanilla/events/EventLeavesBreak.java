package com.agadar.bettervanilla.events;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

import com.agadar.bettervanilla.handlers.ModConfigurations;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventLeavesBreak 
{
	@SubscribeEvent
	public void onBlockBreak(BreakEvent event) 
	{
		if (event.block == Blocks.leaves  && (event.blockMetadata & 3) == 0)
		{
			World world = event.getPlayer().worldObj;
			
			if (world.rand.nextInt(200) <= ModConfigurations.ApplesRate)
			{
				this.dropBlockAsItem(event.world, event.x, event.y, event.z, new ItemStack(Items.apple));
			}
		}
	}
	
	private void dropBlockAsItem(World par1World, int par2, int par3, int par4, ItemStack par5ItemStack)
    {
        if (!par1World.isRemote && par1World.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
            float f = 0.7F;
            double d0 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(par1World, (double)par2 + d0, (double)par3 + d1, (double)par4 + d2, par5ItemStack);
            entityitem.delayBeforeCanPickup = 10;
            par1World.spawnEntityInWorld(entityitem);
        }
    }
}
