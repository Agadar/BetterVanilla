package com.agadar.bettervanilla.events;

import com.agadar.bettervanilla.BetterVanilla;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class BreakHook {

	@ForgeSubscribe
	public void onBlockBreak(BreakEvent event) 
	{
		if (event.block.blockID == Block.leaves.blockID  && (event.blockMetadata & 3) == 0 && BetterVanilla.Apples)
		{
			World world = event.getPlayer().worldObj;
			if (world.rand.nextInt(200) <= BetterVanilla.ApplesRate)
			{
				this.dropBlockAsItem(event.world, event.x, event.y, event.z, new ItemStack(Item.appleRed));
			}
		}
		else if (event.block.blockID == Block.ice.blockID && BetterVanilla.Ice)
		{
			event.setCanceled(true);
			
			EntityPlayer player = event.getPlayer();			
			player.addStat(StatList.mineBlockStatArray[Block.ice.blockID], 1);
			player.addExhaustion(0.025F);
	        
	        ChunkCoordinates position = player.getPlayerCoordinates();
	        String biome = event.world.getBiomeGenForCoords(position.posX, position.posZ).toString().toLowerCase();
	        
	        if ((Block.ice.canSilkHarvest(event.world, player, event.x, event.y, event.z, event.blockMetadata) 
	        		&& EnchantmentHelper.getSilkTouchModifier(player)) || biome.contains("taiga") || 
	        		biome.contains("frozen") || biome.contains("ice"))
	        {
	            this.dropBlockAsItem(event.world, event.x, event.y, event.z, new ItemStack(Block.ice));
	            event.world.setBlockToAir(event.x, event.y, event.z);
	        }
	        else
	        {
	            if (event.world.provider.isHellWorld)
	            {
	            	event.world.setBlockToAir(event.x, event.y, event.z);
	                return;
	            }
	            
	            Material material = event.world.getBlockMaterial(event.x, event.y - 1, event.z);
	            if (material.blocksMovement() || material.isLiquid())
	            {
	            	event.world.setBlock(event.x, event.y, event.z, Block.waterMoving.blockID);
	            }
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