package com.agadar.bettervanilla.events;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockReed;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class BonemealHook {
	@SubscribeEvent 
	public void onBoneMealUse(BonemealEvent event) {
		Block block = event.world.getBlock(event.x, event.y, event.z);
		if (block instanceof BlockReed || block instanceof BlockCactus) {
			if (event.world.isAirBlock(event.x, event.y + 1, event.z)) {
				if (event.world.getBlock(event.x, event.y - 2, event.z) != block) {
					event.world.setBlock(event.x, event.y + 1, event.z, block);
					event.world.setBlockMetadataWithNotify(event.x, event.y, event.z, 0, 4);
					event.setResult(Result.ALLOW);
				}
			} else if (event.world.isAirBlock(event.x, event.y + 2, event.z)) {
				if (event.world.getBlock(event.x, event.y + 1, event.z) == block
						&& event.world.getBlock(event.x, event.y - 1, event.z) != block) {
					event.world.setBlock(event.x, event.y + 2, event.z, block);
					event.world.setBlockMetadataWithNotify(event.x, event.y + 1, event.z, 0, 4);
					event.setResult(Result.ALLOW);
				}
			}
		} else if (block == Blocks.nether_wart) {
			int l = event.world.getBlockMetadata(event.x, event.y, event.z);
	        if (l < 3)
	        {
	            ++l;
	            event.world.setBlockMetadataWithNotify(event.x, event.y, event.z, l, 2);
	            event.setResult(Result.ALLOW);
	        }
		}
	}
}
